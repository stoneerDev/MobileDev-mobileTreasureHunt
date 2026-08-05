package com.example.a6_mobile_treasure_hunt.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a6_mobile_treasure_hunt.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreasureHuntCompleteScreen(
    treasureHuntViewModel: TreasureHuntViewModel = viewModel(),
    onHomeClick: () -> Unit
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
                        text = stringResource(R.string.treasure_hunt_success),
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
                        onClick = onHomeClick

                    ) {
                        Text(
                            text = "Home"
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
                    .border(width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    fontSize = 24.sp,
                    lineHeight = 36.sp,
                    textAlign = TextAlign.Center,
                    text = "Congratulations, you completed the mobile treasure hunt"

                )
            }


                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .border(width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        fontSize = 24.sp,
                        lineHeight = 36.sp,
                        textAlign = TextAlign.Center,
                        text = "Clue Hint"
                    )

                }



            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Total Hunting Time"
            )
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .padding(top= 1.dp, bottom = 16.dp, start= 8.dp, end = 8.dp)
                    .border(width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    modifier = Modifier
                        .padding(2.dp),
                    fontSize = 24.sp,
                    lineHeight = 36.sp,
                    textAlign = TextAlign.Center,
                    text = "00:00:00"
                )




            }

        }

    }
}
/*
@Preview(showBackground = true)
@Composable
fun TreasureHuntCompletePreview() {

        TreasureHuntCompleteScreen()

}*/