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
import androidx.lifecycle.viewModelScope
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


    //Per research keeping separate to avoid copying the whole uiState frequently
    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime = _elapsedTime.asStateFlow()
    private var timerJob: Job? = null

    //Location Info
    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)


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

        //return Miles
        return EARTH_RADIUS_KM * c * 0.621371192
    }


    fun checkLocation(): Boolean {
       //TODO

        return true
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