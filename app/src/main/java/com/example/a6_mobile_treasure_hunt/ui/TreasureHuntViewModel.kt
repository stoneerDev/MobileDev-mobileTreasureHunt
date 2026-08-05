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


//REF: https://programmingheadache.com/2024/08/25/how-to-easily-integrate-gps-location-updates-in-jetpack-compose-a-5-step-guide/
    // Google AI Summary "How to integrate a fusedLocation in a viewmodel"
    // Using Android Location Services Codelab

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
                    currentState.copy(currentLocation = loc)
                }
            }
        }
    }



}