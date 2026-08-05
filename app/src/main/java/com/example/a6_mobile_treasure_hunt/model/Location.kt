package com.example.a6_mobile_treasure_hunt.model

import androidx.annotation.StringRes

data class TreasureHuntLocation(
    @StringRes val locationNameId: Int,
    @StringRes val locationTextId: Int,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0


    )
