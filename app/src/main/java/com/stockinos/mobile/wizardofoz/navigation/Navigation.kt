package com.stockinos.mobile.wizardofoz.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stockinos.mobile.wizardofoz.WoZApplication
import com.stockinos.mobile.wizardofoz.ui.home.HomeScreen
import com.stockinos.mobile.wizardofoz.ui.signin.SignInScreen
import com.stockinos.mobile.wizardofoz.ui.signin.SignInViewModel
import com.stockinos.mobile.wizardofoz.ui.signotp.SignInOTPScreen
import com.stockinos.mobile.wizardofoz.ui.signotp.SignInOTPViewModel

@Composable
fun Navigation(navController: NavHostController) {
    val TAG = "Navigation"

    NavHost(navController = navController, startDestination = Routes.SignIn.route) {
        composable(route = Routes.SignIn.route) {
            val signInViewModel = SignInViewModel(
                authRepository = WoZApplication.getAppInstance().authRepository
            )
            SignInScreen(
                navController = navController,
                signInViewModel = signInViewModel
            )
        }
        composable(
            route = Routes.SignInOTP.route + "/{phoneNumber}",
            arguments = listOf(
                navArgument("phoneNumber") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Log.d(TAG, )
            val signInOTPViewModel = SignInOTPViewModel(
                savedStateHandle = backStackEntry.savedStateHandle,
                authRepository = WoZApplication.getAppInstance().authRepository
            )
            SignInOTPScreen(
                navController = navController,
                signInOTPViewModel = signInOTPViewModel
            )
        }
        composable(route = Routes.Home.route) {
            HomeScreen(navController = navController)
        }
    }
}