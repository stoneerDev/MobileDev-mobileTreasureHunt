package com.example.a6_mobile_treasure_hunt.data

import androidx.annotation.StringRes
import com.example.a6_mobile_treasure_hunt.R
import com.example.a6_mobile_treasure_hunt.model.TreasureHuntLocation
import kotlin.Double

//REF:  Assignment 3 Scrollable List

object TreasureHuntData {

    val finalLocation = TreasureHuntLocation(
        locationNameId = R.string.final_location_name,
        locationTextId = R.string.final_location_description,
        latitude = 47.6205099, //Space Needle for Now
        longitude = -122.3518523
    )

    val clue1Location = TreasureHuntLocation(
    locationNameId = R.string.clue1_location_name,
    locationTextId = R.string.clue1_location_description,
    latitude = 47.6061378, //Seattle Great Wheel for Now
    longitude = -122.3450995
    )

}