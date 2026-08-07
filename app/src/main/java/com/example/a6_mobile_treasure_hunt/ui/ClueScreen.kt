/*
    Erik Stone, stoneer@oregonstate.edu
    CS492 Summer 2026, Oregon State University
    Assignment 6 - Mobile Treasure Hunt

    References:
    - Assignment 5 - Building a multiscreen app II
    - Android Location Services Codelab
 */
package com.example.a6_mobile_treasure_hunt.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a6_mobile_treasure_hunt.ui.components.KeepSearchingPopup
import com.example.a6_mobile_treasure_hunt.ui.components.LocationViewer
import com.example.a6_mobile_treasure_hunt.ui.components.Timer


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClueScreen(
    treasureHuntViewModel: TreasureHuntViewModel = viewModel(),
    elapsedTime: Long,
    onFoundItClick: () -> Unit,
    onQuitClick: () -> Unit
) {

    val treasureHuntUiState by treasureHuntViewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    LaunchedEffect(treasureHuntUiState.locationIndex) {
        treasureHuntViewModel.setPopup(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = colorScheme.primaryContainer,
                    titleContentColor = colorScheme.onPrimaryContainer,
                ),
                title = {
                    Text(
                        text = "Clue ${treasureHuntUiState.locationIndex+1}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    )
                }
            )
        },
        bottomBar = {


            Surface(
                color = colorScheme.primaryContainer,
                tonalElevation = 3.dp,
                modifier = Modifier.fillMaxWidth().imePadding()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly

                        ) {

                        Button(
                            onClick = onQuitClick

                        ) {
                            Text(
                                text = "Quit"
                            )
                        }
                        Button(
                            onClick = onFoundItClick

                        ) {
                            Text(
                                text = "Found It!"
                            )
                        }
                        Button(
                            onClick = { treasureHuntViewModel.toggleHint() }

                        ) {
                            Text(
                                text = "Hint!"
                            )
                        }

                    }

            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(5.dp)
                    .border(width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(16.dp))
                    .verticalScroll(scrollState),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart),
                    fontSize = 24.sp,
                    lineHeight = 36.sp,
                    textAlign = TextAlign.Center,
                    text = stringResource(treasureHuntUiState
                        .locationList[treasureHuntUiState.locationIndex]
                        .clueId)

                )

            }

            if (treasureHuntUiState.showHint) {
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .padding(5.dp)
                        .border(width = 2.dp,
                            color = Color.Black,
                        shape = RoundedCornerShape(16.dp))
                        .verticalScroll(scrollState),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        fontSize = 24.sp,
                        lineHeight = 36.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(treasureHuntUiState
                            .locationList[treasureHuntUiState.locationIndex]
                            .locationHintId)
                    )

                }


            }
            if(treasureHuntUiState.showPopup){
            KeepSearchingPopup(
                onDismissRequest = {treasureHuntViewModel.setPopup(false)},
                distanceAway = treasureHuntUiState.currentDistance
            )}
            /*
            LocationViewer(
                treasureHuntViewModel = treasureHuntViewModel
            )*/
            Spacer(
                modifier = Modifier.weight(1f)
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Elapsed Time"
            )
            Timer(
                elapsedTime = elapsedTime
            )


        }

    }
}

