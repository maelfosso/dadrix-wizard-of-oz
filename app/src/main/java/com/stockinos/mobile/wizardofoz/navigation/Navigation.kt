package com.stockinos.mobile.wizardofoz.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stockinos.mobile.wizardofoz.ui.home.HomeScreen
import com.stockinos.mobile.wizardofoz.ui.signin.SignInScreen
import com.stockinos.mobile.wizardofoz.ui.signotp.SignInOTPScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.SignIn.route) {
        composable(route = Routes.SignIn.route) {
            SignInScreen(navController = navController)
        }
        composable(route = Routes.SignInOTP.route) {
            SignInOTPScreen(navController = navController)
        }
        composable(route = Routes.Home.route) {
            HomeScreen(navController = navController)
        }
    }
}