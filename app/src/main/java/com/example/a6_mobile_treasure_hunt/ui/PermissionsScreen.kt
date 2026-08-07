/*
    Erik Stone, stoneer@oregonstate.edu
    CS492 Summer 2026, Oregon State University
    Assignment 6 - Mobile Treasure Hunt

    References:
    - Assignment 5 - Building a multiscreen app II
    - Android Location Services Codelab
 */
package com.example.a6_mobile_treasure_hunt.ui

import android.Manifest
import android.content.pm.PackageManager
import android.provider.Settings.Global.getString
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a6_mobile_treasure_hunt.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionScreen(
    treasureHuntViewModel: TreasureHuntViewModel = viewModel(),
    onPermissionGranted: () -> Unit


) {

    val treasureHuntUiState by treasureHuntViewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

//request permission REF: Codelab, but moving location init to viewModel
    val permissionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { permissionGranted: Boolean ->
        treasureHuntViewModel.updatePermissionStatus(permissionGranted)
        if (permissionGranted) {
            onPermissionGranted()
        }

    }

    val context = LocalContext.current

    /*
    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                treasureHuntViewModel.updatePermissionStatus(true)

            }

            else -> {
                permissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }*/
    LifecycleResumeEffect(Unit) {
        val isPermissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if(isPermissionGranted) {
            treasureHuntViewModel.updatePermissionStatus(true)
            onPermissionGranted()
        }
        else {
            permissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        onPauseOrDispose {
        //Needed by LifecycleResumeEffect for cleanup if needed REF: https://developer.android.com/topic/libraries/architecture/lifecycle
         }
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
                        text = stringResource(R.string.requesting_permissions),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {


                Row(
                    modifier = Modifier
                        .height(75.dp)
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly

                ) {
                /*
                    Button(
                        onClick = { permissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION) }

                    ) {
                        Text(
                            text = "Re-Request"
                        )
                    }*/
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
                    //.height(200.dp)
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .border(width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.TopCenter
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        fontSize = 24.sp,
                        lineHeight = 36.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.permissions_are_needed)

                    )

                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        fontSize = 24.sp,
                        lineHeight = 36.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.current_permissions,treasureHuntUiState.permissionGranted.toString())


                    )

                }

            }



        }

    }
}
/*
@Preview(showBackground = true)
@Composable
fun PermissionScreenPreview() {

    PermissonScreen()

}*/