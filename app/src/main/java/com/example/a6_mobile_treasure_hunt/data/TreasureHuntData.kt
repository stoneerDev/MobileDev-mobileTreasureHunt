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
            latitude = 47.6061001, //Seattle Great Wheel for Now
            longitude = -122.3425001
        ),
        TreasureHuntLocation(
            locationNameId = R.string.clue2_location_name,
            locationTextId = R.string.clue2_location_description,
            locationHintId = R.string.clue2_location_hint,
            latitude = 47.6510617, //The Fremont Troll for Now
            longitude = -122.3474983
        ),
        TreasureHuntLocation(
            locationNameId = R.string.final_location_name,
            locationTextId = R.string.final_location_description,
            locationHintId = R.string.blank,
            latitude = 47.620505, //Space Needle for Now
            longitude = -122.3492767
        )

    )


}