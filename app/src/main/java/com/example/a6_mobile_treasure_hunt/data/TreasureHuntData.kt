/*
    Erik Stone, stoneer@oregonstate.edu
    CS492 Summer 2026, Oregon State University
    Assignment 6 - Mobile Treasure Hunt

    References:
    - Assignment 5 - Building a multiscreen app II
    - Android Location Services Codelab
 */
package com.example.a6_mobile_treasure_hunt.data

import androidx.annotation.StringRes
import com.example.a6_mobile_treasure_hunt.R
import com.example.a6_mobile_treasure_hunt.model.TreasureHuntLocation
import kotlin.Double

//REF:  Assignment 3 Scrollable List

object TreasureHuntData {
    val locationList = listOf(
        TreasureHuntLocation(
            locationNameId = R.string.clue1_location_name,
            locationTextId = R.string.clue1_location_description,
            locationHintId = R.string.clue1_location_hint,
            clueId = R.string.clue1_text,
            latitudeId = R.string.clue1_latitude,
            longitudeId = R.string.clue1_longitude

        ),
        TreasureHuntLocation(
            locationNameId = R.string.clue2_location_name,
            locationTextId = R.string.clue2_location_description,
            locationHintId = R.string.clue2_location_hint,
            clueId = R.string.clue2_text,
            latitudeId = R.string.clue2_latitude,
            longitudeId = R.string.clue2_longitude

        ),
        TreasureHuntLocation(
            locationNameId = R.string.final_location_name,
            locationTextId = R.string.final_location_description,
            locationHintId = R.string.final_location_hint,
            clueId = R.string.final_clue,
            latitudeId = R.string.final_location_latitude,
            longitudeId = R.string.final_location_longitude

        )

    )


}