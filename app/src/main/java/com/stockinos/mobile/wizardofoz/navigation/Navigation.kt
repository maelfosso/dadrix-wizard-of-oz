package com.stockinos.mobile.wizardofoz.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.stockinos.mobile.wizardofoz.ui.signotp.SignInOTPViewModelFactory
import androidx.activity.viewModels

@Composable
fun Navigation(navController: NavHostController) {
    val TAG = "Navigation"

    NavHost(navController = navController, startDestination = Routes.SignIn.route) {
        composable(route = Routes.SignIn.route) {
            SignInScreen(
                navController = navController,
                signInViewModel = viewModel(factory = SignInOTPViewModel.Factory)
            )
        }
        composable(
            route = Routes.SignInOTP.route + "/{phoneNumber}",
            arguments = listOf(
                navArgument("phoneNumber") { type = NavType.StringType }
            )
        ) {
            SignInOTPScreen(
                navController = navController,
                signInOTPViewModel = viewModel(factory = SignInOTPViewModel.Factory)
            )
        }
        composable(route = Routes.Home.route) {
            HomeScreen(navController = navController)
        }
    }
}