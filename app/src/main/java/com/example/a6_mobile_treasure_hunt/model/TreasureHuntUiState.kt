package com.example.a6_mobile_treasure_hunt.model

import android.location.Location

data class TreasureHuntUiState(
    val currentLocation: Location? = null,
    val permissionGranted: Boolean = false
)
