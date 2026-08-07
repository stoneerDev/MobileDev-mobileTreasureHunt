/*
    Erik Stone, stoneer@oregonstate.edu
    CS492 Summer 2026, Oregon State University
    Assignment 6 - Mobile Treasure Hunt

    References:
    - Assignment 5 - Building a multiscreen app II
    - Android Location Services Codelab
 */
package com.example.a6_mobile_treasure_hunt.ui.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a6_mobile_treasure_hunt.R
import com.example.a6_mobile_treasure_hunt.ui.TreasureHuntViewModel


@Composable
fun Timer(
    elapsedTime: Long
) {


    Box(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(top = 1.dp, bottom = 16.dp, start = 8.dp, end = 8.dp)
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Text(
            modifier = Modifier
                .padding(2.dp),
            fontSize = 24.sp,
            lineHeight = 36.sp,
            textAlign = TextAlign.Center,
            text = "${elapsedTime}"
        )




    }

}

//REF: https://developer.android.com/develop/ui/compose/quick-guides/content/display-user-input
@Composable
fun KeepSearchingPopup(
    onDismissRequest: () -> Unit,
    distanceAway: Double
) {

    AlertDialog(
        title = {
            Text(text = "Keep Searching")
        },
        text = {
            Text(text = stringResource(R.string.distance_away,distanceAway))
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {

        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

//Composable to test location
@Composable
fun LocationViewer(
    treasureHuntViewModel: TreasureHuntViewModel
) {
    val treasureHuntUiState by treasureHuntViewModel.uiState.collectAsStateWithLifecycle()


    if(treasureHuntUiState.permissionGranted) {
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Location"
        )
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(top = 1.dp, bottom = 16.dp, start = 8.dp, end = 8.dp)
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

            //Null Safe location display
            treasureHuntUiState.currentLocation?.let {
                Text(
                    modifier = Modifier
                        .padding(2.dp),
                    fontSize = 24.sp,
                    lineHeight = 36.sp,
                    textAlign = TextAlign.Center,
                    text = "Latitude: ${treasureHuntUiState.currentLocation?.latitude}, Longitude: ${treasureHuntUiState.currentLocation?.longitude}"
                )
                Text(
                    text = "Distance: ${treasureHuntUiState.currentDistance}"
                )

            } ?:
            Text(
                modifier = Modifier
                    .padding(2.dp),
                fontSize = 24.sp,
                lineHeight = 36.sp,
                textAlign = TextAlign.Center,
                text = "Location not Loaded"
            )


        }
        //new temporary button for now to test location
        Button(
            onClick = { treasureHuntViewModel.getNewLocation() }

        ) {
            Text(
                text = "Location"
            )
        }
    }

}


