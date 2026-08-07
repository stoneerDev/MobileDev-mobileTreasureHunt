/*
    Erik Stone, stoneer@oregonstate.edu
    CS492 Summer 2026, Oregon State University
    Assignment 6 - Mobile Treasure Hunt

    References:
    - Assignment 5 - Building a multiscreen app II
    - Android Location Services Codelab
 */
package com.example.a6_mobile_treasure_hunt.model

import androidx.annotation.StringRes

data class TreasureHuntLocation(
    @StringRes val locationNameId: Int,
    @StringRes val locationTextId: Int,
    @StringRes val locationHintId: Int,
    @StringRes val latitudeId: Int,
    @StringRes val longitudeId: Int,
    @StringRes val clueId: Int,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
    )
