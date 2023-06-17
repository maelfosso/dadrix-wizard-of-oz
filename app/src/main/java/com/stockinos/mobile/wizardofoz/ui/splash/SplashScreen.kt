package com.tschwaa.mobile


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stockinos.mobile.wizardofoz.navigation.Routes
import com.stockinos.mobile.wizardofoz.ui.viewmodels.TokenViewModel
import com.stockinos.mobile.wizardofoz.utils.assetsToBitmap
import com.stockinos.mobile.wizardofoz.utils.splash_ic_main_image
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    tokenViewModel: TokenViewModel
) {
    Log.d("SplashScreen", "TOken: ${tokenViewModel.token.value}")
    SplashScreenContent()
    LaunchedEffect(Unit) {
        delay(100)
        if (tokenViewModel.token.value != null) {
            navController.navigate(Routes.Messages.route)
        } else {
            navController.navigate(Routes.SignIn.route)
        }
    }
}

@Composable
fun SplashScreenContent() {
    val context = LocalContext.current
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                bitmap = context.assetsToBitmap(splash_ic_main_image)!!.asImageBitmap(),
                contentDescription = "",
                modifier = Modifier.height(250.dp)
            )
        }
    }
}