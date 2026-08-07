/*
    Erik Stone, stoneer@oregonstate.edu
    CS492 Summer 2026, Oregon State University
    Assignment 6 - Mobile Treasure Hunt

    References:
    - Assignment 5 - Building a multiscreen app II
    - Android Location Services Codelab
 */
package com.example.a6_mobile_treasure_hunt.ui

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.a6_mobile_treasure_hunt.model.TreasureHuntUiState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewModelScope
import com.example.a6_mobile_treasure_hunt.R
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.*
import kotlin.time.Duration.Companion.milliseconds


//REF: https://programmingheadache.com/2024/08/25/how-to-easily-integrate-gps-location-updates-in-jetpack-compose-a-5-step-guide/
    // Google AI Summary "How to integrate a fusedLocation in a viewmodel"
    // Using Android Location Services Codelab
    // Timer example: https://medium.com/@TippuFisalSheriff/creating-a-timer-screen-with-kotlin-and-jetpack-compose-in-android-f7c56952d599

private const val EARTH_RADIUS_KM = 6371.0

//class TreasureHuntViewModel : ViewModel() {
class TreasureHuntViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(TreasureHuntUiState())
    val uiState: StateFlow<TreasureHuntUiState> = _uiState.asStateFlow()

    //Get the lat and long values from string resources on startup (Recommended by Google)
    init {
        parseLatLongFromStringRes()
    }

    private fun parseLatLongFromStringRes() {
        val currentContext = getApplication<Application>()

        _uiState.update { currentState ->
            val newLocationList = currentState.locationList.map { location ->
                val numericLatitude = currentContext.getString(location.latitudeId).toDoubleOrNull() ?: 0.0
                val numericLongitude = currentContext.getString(location.longitudeId).toDoubleOrNull() ?: 0.0

                location.copy(
                    latitude = numericLatitude,
                    longitude = numericLongitude
                )

            }

            currentState.copy(locationList = newLocationList)

        }
    }






    //Per research keeping timer separate to avoid copying the whole uiState frequently
    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime = _elapsedTime.asStateFlow()
    private var timerJob: Job? = null

    //Location Info
    //  REF: https://medium.com/@akshaysarapure/realtime-location-in-android-using-jetpack-compose-390411e996ea
    //      Google Ai summaries on how to adapt this approach to a viewModel

    //setup
    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)
    private var pollingStarted = false

    val locationRequest = LocationRequest.Builder(2000L)
        .setIntervalMillis(5000)
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        .build()


    val locationCallback = object: LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result.lastLocation.let {location ->
                if(location == null){
                    //Do Nothing
                } else {

                    _uiState.update { currentState ->
                        currentState.copy(
                            currentLocation = location,
                            lastUpdated = System.currentTimeMillis())

                    }
                }
            }
        }

    }

    @SuppressLint("MissingPermission")
    fun startLocationPolling() {
        if (pollingStarted) {
            return
        }
        else {
            if (_uiState.value.permissionGranted) {
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest, locationCallback, Looper.getMainLooper()
                )
                pollingStarted = true
            }

        }
    }

    fun stopLocationPolling() {
        if (pollingStarted) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            pollingStarted = false
        }

    }




    //timer functions
    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000.milliseconds)
                _elapsedTime.value++

            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
    }

    fun stopTimer() {
        _elapsedTime.value = 0
        timerJob?.cancel()
    }

    //Timer resource cleanup
    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        stopLocationPolling()
    }

    fun updatePermissionStatus(permissionGranted: Boolean) {
        if (permissionGranted) {
            //Request initial location after permissions (so not null)
            getNewLocation()
        }
        _uiState.update { currentState ->
            currentState.copy(permissionGranted = permissionGranted)
        }

    }

    //LookAt: https://medium.com/@akshaysarapure/realtime-location-in-android-using-jetpack-compose-390411e996ea
    @SuppressLint("MissingPermission")
    fun getNewLocation() {
        if (_uiState.value.permissionGranted) {
            fusedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).addOnSuccessListener { loc: Location? ->
                _uiState.update { currentState ->
                    if (loc != null) {

                        val newHaversine = calculateHaversine(
                            startLat = loc.latitude,
                            startLong = loc.longitude,
                            endLat = currentState.locationList[currentState.locationIndex].latitude,
                            endLong = currentState.locationList[currentState.locationIndex].longitude
                        )
                        currentState.copy(
                            currentLocation = loc,
                            currentDistance = newHaversine
                        )
                    } else {
                        currentState
                    }

                }
            }

        }
    }

    //REF: Location Codelab Haversine & https://gist.github.com/jferrao/cb44d09da234698a7feee68ca895f491
    fun calculateHaversine(
        startLat: Double,
        startLong: Double,
        endLat: Double,
        endLong: Double
    ): Double {
        val latDist = Math.toRadians(endLat - startLat)
        val longDist = Math.toRadians(endLong - startLong)

        val a = sin(latDist / 2).pow(2) +
                cos(Math.toRadians(startLat)) * cos(Math.toRadians(endLat)) *
                sin(longDist / 2).pow(2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        //return meters
        return EARTH_RADIUS_KM * c * 1000
    }


    fun checkLocation(): Boolean {
       //getNewLocation()
        val newHaversine = calculateHaversine(
            startLat = _uiState.value.currentLocation?.latitude ?: 0.0,
            startLong = _uiState.value.currentLocation?.longitude ?: 0.0,
            endLat = _uiState.value.locationList[_uiState.value.locationIndex].latitude,
            endLong = _uiState.value.locationList[_uiState.value.locationIndex].longitude
        )
        _uiState.update { currentState ->
            currentState.copy(currentDistance = newHaversine)
        }
       if (newHaversine < 50) {

           return true

       } else {
           return false
       }
    }

    fun toggleHint() {
        _uiState.update { currentState ->
            if (currentState.showHint) {
                currentState.copy(showHint = false)
            } else {
                currentState.copy(showHint = true)
            }
        }
    }
    fun setHint(
        hintStatus: Boolean
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                showHint = hintStatus
            )
        }

    }

    fun setPopup(
        popupStatus: Boolean
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                showPopup = popupStatus
            )
        }

    }

    fun resetGame() {

        _uiState.update { currentState ->
            currentState.copy(
                locationIndex = 0,
                currentLocation = null,
                onLastClue = false,
                showHint = false
            )
        }
        stopTimer()
    }

    fun advanceClues() {
        _uiState.update { currentState ->
            val newIdx = currentState.locationIndex + 1
            val maxIdx = currentState.locationList.size - 1
            if (newIdx == maxIdx) {
                currentState.copy(
                    locationIndex = newIdx,
                    onLastClue = true
                )

            } else {
                currentState.copy(
                    locationIndex = newIdx,
                    onLastClue = false
                )
            }

        }
    }


}