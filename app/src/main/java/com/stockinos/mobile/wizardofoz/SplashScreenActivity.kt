package com.stockinos.mobile.wizardofoz

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stockinos.mobile.wizardofoz.ui.splash.SplashScreenViewModel
import com.stockinos.mobile.wizardofoz.ui.theme.WizardOfOzTheme
import com.stockinos.mobile.wizardofoz.utils.assetsToBitmap
import com.stockinos.mobile.wizardofoz.utils.splash_ic_main_image

class SplashScreenActivity : ComponentActivity() {

    private val splashScreenViewModel: SplashScreenViewModel by viewModels { SplashScreenViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Set the layout for the content view.
        setContent {
            WizardOfOzTheme {
                SplashScreenContent()

                val uiState by splashScreenViewModel.uiState.collectAsStateWithLifecycle()
                if (uiState.isValid != null) {
                    afterSplashScreen(
                        this,
                        uiState.isValid!!
                    )
                    finish()
                }
            }
        }

        splashScreen.setKeepOnScreenCondition { true }
    }


    private fun afterSplashScreen(
        context: Context,
        isTokenValid: Boolean
    ) {
        if (!isTokenValid) {
            MainActivity.startActivity(context)
        } else {
            HomeActivity.startActivity(context)
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