package com.example.a6_mobile_treasure_hunt.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClueScreen(
    treasureHuntViewModel: TreasureHuntViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = colorScheme.primaryContainer,
                    titleContentColor = colorScheme.onPrimaryContainer,
                ),
                title = {
                    Text(
                        text = "Example",
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    )
                }
            )
        },
        bottomBar = {
            //Hide if Detail Already There
            //if(currentRecommendationDetail.isBlank()) {

            Surface(
                color = colorScheme.primaryContainer,
                tonalElevation = 3.dp,
                modifier = Modifier.fillMaxWidth().imePadding()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                //Hide if Detail Already There
                if (true) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),

                        ) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "Example Text",

                            )

                        OutlinedTextField(
                            value = "",
                            singleLine = true,
                            shape = shapes.large,
                            modifier = Modifier
                                .fillMaxWidth().heightIn(min = 56.dp),

                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = colorScheme.surface,
                                unfocusedContainerColor = colorScheme.surface,
                                disabledContainerColor = colorScheme.surface,
                            ),
                            onValueChange = { },
                            label = {
                                if (false) {
                                    Text(
                                        text="Text Ex False")
                                } else {
                                    Text(
                                        text="Text Ex True")
                                }


                            },
                            isError = false,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { }

                            ))
                        Button(
                            onClick = { }

                        ) {
                            Text(
                                text = "Save"
                            )
                        }

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
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                fontSize = 24.sp,
                lineHeight = 36.sp,
                textAlign = TextAlign.Center,
                text = "Text Example 3"

            )

        }

    }
}