/*
    Erik Stone, stoneer@oregonstate.edu
    CS492 Summer 2026, Oregon State University
    Assignment 6 - Mobile Treasure Hunt

    References:
    - Assignment 5 - Building a multiscreen app II
    - Android Location Services Codelab
 */

package com.example.a6_mobile_treasure_hunt

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.a6_mobile_treasure_hunt.ui.TreasureHuntViewModel
import com.example.a6_mobile_treasure_hunt.ui.theme.A6_mobile_treasure_huntTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.lifecycle.viewmodel.compose.viewModel

//REF: Using Android Location Services Codelab
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            A6_mobile_treasure_huntTheme {

                //Create viewModel here to save permissions
                val treasureHuntViewModel: TreasureHuntViewModel = viewModel()
                val context = LocalContext.current

                //request permission REF: Codelab, but moving location init to viewModel
                val permissionRequest = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { permissionGranted: Boolean ->
                    treasureHuntViewModel.updatePermissionStatus(permissionGranted)
                }

                LaunchedEffect(Unit) {
                    when {
                        ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED -> {
                            treasureHuntViewModel.updatePermissionStatus(true)
                        }

                        else -> {
                            permissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    }
                }


                MobileTreasureHuntApp(
                    treasureHuntViewModel = treasureHuntViewModel
                )
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun MobileTreasureHuntPreview() {
    A6_mobile_treasure_huntTheme {
        MobileTreasureHuntApp()
    }
}