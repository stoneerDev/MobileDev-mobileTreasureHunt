/*
    Erik Stone, stoneer@oregonstate.edu
    CS492 Summer 2026, Oregon State University
    Assignment 6 - Mobile Treasure Hunt

    References:
    - Assignment 5 - Building a multiscreen app II
    - Android Location Services Codelab
 */

package com.example.a6_mobile_treasure_hunt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a6_mobile_treasure_hunt.ui.ClueScreen
import com.example.a6_mobile_treasure_hunt.ui.ClueSolvedScreen
import com.example.a6_mobile_treasure_hunt.ui.PermissionScreen
import com.example.a6_mobile_treasure_hunt.ui.StartScreen
import com.example.a6_mobile_treasure_hunt.ui.TreasureHuntCompleteScreen
import com.example.a6_mobile_treasure_hunt.ui.TreasureHuntViewModel

//Navigation Destinations
enum class MobileTreasureHuntScreens {
    PermissionScreen,
    StartScreen,
    ClueScreen,
    ClueSolvedScreen,
    TreasureHuntCompleteScreen
}


@Composable
fun MobileTreasureHuntApp(
    treasureHuntViewModel: TreasureHuntViewModel = viewModel()

) {
    val treasureHuntUiState by treasureHuntViewModel.uiState.collectAsStateWithLifecycle()

    //Set up Navigation REF: Assignment 5 - Building a multiscreen app II
    val navController: NavHostController = rememberNavController()

    val elapsedTime by treasureHuntViewModel.elapsedTime.collectAsState()


    //Set up realtime location updates (REF: Google where to start location tracking in a viewmodel)
    DisposableEffect(treasureHuntUiState.permissionGranted) {
        if (treasureHuntUiState.permissionGranted) {
            treasureHuntViewModel.startLocationPolling()
        }

        onDispose {
            treasureHuntViewModel.stopLocationPolling()
        }
    }



    NavHost(
        navController = navController,
        startDestination = MobileTreasureHuntScreens.PermissionScreen.name
    ) {
        composable(route = MobileTreasureHuntScreens.PermissionScreen.name) {
            PermissionScreen(
                treasureHuntViewModel = treasureHuntViewModel,
                onPermissionGranted = {
                    navController.navigate(MobileTreasureHuntScreens.StartScreen.name)
                }
            )
        }

        composable(route = MobileTreasureHuntScreens.StartScreen.name) {
            StartScreen(
                treasureHuntViewModel = treasureHuntViewModel,
                onStartClick = {
                    treasureHuntViewModel.startTimer()
                    navController.navigate(MobileTreasureHuntScreens.ClueScreen.name)
                }

            )
        }

        composable(route = MobileTreasureHuntScreens.ClueScreen.name) {
            ClueScreen (
                treasureHuntViewModel = treasureHuntViewModel,
                elapsedTime = elapsedTime,
                onFoundItClick = {
                    //Check if location found, if so route to next clue or treasure hunt complete
                    val found = treasureHuntViewModel.checkLocation()

                    if (found) {
                        treasureHuntViewModel.pauseTimer()
                        treasureHuntViewModel.setHint(false)

                        if(treasureHuntUiState.onLastClue) {
                            navController.navigate(MobileTreasureHuntScreens.TreasureHuntCompleteScreen.name)
                        } else {
                            navController.navigate(MobileTreasureHuntScreens.ClueSolvedScreen.name)
                        }

                    }
                    treasureHuntViewModel.setPopup(true)
                },
                onQuitClick = {
                    //treasureHuntViewModel.resetGame()
                    navController.navigate(MobileTreasureHuntScreens.StartScreen.name)
                }
            )
        }

        composable(route = MobileTreasureHuntScreens.ClueSolvedScreen.name) {
            ClueSolvedScreen (
                treasureHuntViewModel = treasureHuntViewModel,
                elapsedTime = elapsedTime,
                onContinueClick = {

                    //Should have more clues unless ClueScreen logic is bad
                    treasureHuntViewModel.advanceClues()
                    treasureHuntViewModel.startTimer()
                    navController.navigate(MobileTreasureHuntScreens.ClueScreen.name)

                },
                onQuitClick = {
                    //treasureHuntViewModel.resetGame()
                    navController.navigate(MobileTreasureHuntScreens.StartScreen.name)
                }

            )
        }

        composable(route = MobileTreasureHuntScreens.TreasureHuntCompleteScreen.name) {
            TreasureHuntCompleteScreen(
                treasureHuntViewModel = treasureHuntViewModel,
                elapsedTime = elapsedTime,
                onHomeClick = {
                    treasureHuntViewModel.resetGame()
                    navController.navigate(MobileTreasureHuntScreens.StartScreen.name)
                }
            )
        }
    }
}