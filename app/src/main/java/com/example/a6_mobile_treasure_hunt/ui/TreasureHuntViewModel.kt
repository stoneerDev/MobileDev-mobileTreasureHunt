package com.example.a6_mobile_treasure_hunt.ui

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.a6_mobile_treasure_hunt.model.TreasureHuntUiState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.google.android.gms.location.FusedLocationProviderClient
import android.location.Location
import kotlin.math.*


//REF: https://programmingheadache.com/2024/08/25/how-to-easily-integrate-gps-location-updates-in-jetpack-compose-a-5-step-guide/
    // Google AI Summary "How to integrate a fusedLocation in a viewmodel"
    // Using Android Location Services Codelab

private const val EARTH_RADIUS_KM = 6371.0

//class TreasureHuntViewModel : ViewModel() {
class TreasureHuntViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(TreasureHuntUiState())
    val uiState: StateFlow<TreasureHuntUiState> = _uiState.asStateFlow()

    //Location Info
    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)

    fun updatePermissionStatus(permissionGranted: Boolean) {
        if(permissionGranted) {
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
                _uiState.update {currentState ->
                    if (loc != null) {
                        val newHaversine = calculateHaversine(
                            startLat = loc.latitude,
                            startLong = loc.longitude,
                            endLat = currentState.finalLocation.latitude,
                            endLong = currentState.finalLocation.longitude
                        )
                        currentState.copy(
                            currentLocation = loc,
                            currentDistance = newHaversine)
                    } else {
                        currentState
                    }

                }
            }

        }
    }
    //REF: Location Codelab Haversine & https://gist.github.com/jferrao/cb44d09da234698a7feee68ca895f491
    fun calculateHaversine(startLat: Double, startLong: Double, endLat: Double, endLong: Double): Double {
        val latDist = Math.toRadians(endLat - startLat)
        val longDist = Math.toRadians(endLong - startLong)

        val a = sin(latDist/2).pow(2) +
                cos(Math.toRadians(startLat)) * cos(Math.toRadians(endLat)) *
                sin(longDist/2).pow(2)

        val c = 2 * atan2(sqrt(a), sqrt(1-a))

        //return Miles
        return EARTH_RADIUS_KM * c * 0.621371192
    }

    fun startTimer() {
        //TODO
    }

    fun pauseTimer() {
        //TODO
    }

    fun checkLocation(): Boolean {
        //TODO
        return true
    }

    fun resetGame() {
        //TODO
    }

    fun advanceClues() {

    }


}