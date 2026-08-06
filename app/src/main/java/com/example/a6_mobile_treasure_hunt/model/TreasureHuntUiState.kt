package com.example.a6_mobile_treasure_hunt.model

import android.location.Location
import com.example.a6_mobile_treasure_hunt.data.TreasureHuntData

data class TreasureHuntUiState(
    val currentLocation: Location? = null,
    val permissionGranted: Boolean = false,
    val locationList: List<TreasureHuntLocation> = TreasureHuntData.locationList,
    val locationIndex: Int = 0,
    val currentDistance: Double = Double.POSITIVE_INFINITY,
    val onLastClue: Boolean = false,
    val showHint: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis()
)
