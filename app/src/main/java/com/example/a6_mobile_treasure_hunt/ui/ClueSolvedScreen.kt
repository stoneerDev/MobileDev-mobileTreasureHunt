package com.example.a6_mobile_treasure_hunt.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a6_mobile_treasure_hunt.MobileTreasureHuntApp
import com.example.a6_mobile_treasure_hunt.ui.components.Timer
import com.example.a6_mobile_treasure_hunt.ui.theme.A6_mobile_treasure_huntTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClueSolvedScreen(
    treasureHuntViewModel: TreasureHuntViewModel = viewModel(),
    elapsedTime: Long,
    onContinueClick: () -> Unit,
    onQuitClick: () -> Unit
) {

    val treasureHuntUiState by treasureHuntViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = colorScheme.primaryContainer,
                    titleContentColor = colorScheme.onPrimaryContainer,
                ),
                title = {
                    Text(
                        text = "Clue ${treasureHuntUiState.locationIndex+1} Solved!",
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
                            onClick = onContinueClick

                        ) {
                            Text(
                                text = "Continue"
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
                    .fillMaxWidth(),
                    //.border(width = 2.dp,
                    //    color = colorScheme.onPrimaryContainer,
                    //    shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart),
                    fontSize = 24.sp,
                    lineHeight = 36.sp,
                    textAlign = TextAlign.Center,
                    text = stringResource(
                        treasureHuntUiState
                            .locationList[treasureHuntUiState.locationIndex]
                            .locationNameId
                    )

                )

                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterStart),
                    fontSize = 18.sp,
                    lineHeight = 36.sp,
                    textAlign = TextAlign.Start,
                    text = stringResource(
                        treasureHuntUiState
                            .locationList[treasureHuntUiState.locationIndex]
                            .locationTextId
                    )

                )
            }
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Elapsed Time"
            )
            Timer(elapsedTime)

        }

    }
}

/*
@Preview(showBackground = true)
@Composable
fun ClueSolvedPreview() {

    ClueSolvedScreen()

}*/

