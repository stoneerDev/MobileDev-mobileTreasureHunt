package com.example.a6_mobile_treasure_hunt

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a6_mobile_treasure_hunt.ui.ClueScreen
import com.example.a6_mobile_treasure_hunt.ui.StartScreen
import com.example.a6_mobile_treasure_hunt.ui.TreasureHuntViewModel

@Composable
fun MobileTreasureHuntApp(
    treasureHuntViewModel: TreasureHuntViewModel = viewModel()
) {
    ClueScreen()
}