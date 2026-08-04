package com.example.a6_mobile_treasure_hunt.ui

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.a6_mobile_treasure_hunt.model.TreasureHuntUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


//REF: https://programmingheadache.com/2024/08/25/how-to-easily-integrate-gps-location-updates-in-jetpack-compose-a-5-step-guide/
    // Google AI Summary "How to integrate a fusedLocation in a viewmodel"
    // Using Android Location Services Codelab

class TreasureHuntViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TreasureHuntUiState())

    val uiSTate: StateFlow<TreasureHuntUiState> = _uiState.asStateFlow()

    fun updatePermissionStatus(permissionGranted: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(permissionGranted = permissionGranted)
        }

    }

}