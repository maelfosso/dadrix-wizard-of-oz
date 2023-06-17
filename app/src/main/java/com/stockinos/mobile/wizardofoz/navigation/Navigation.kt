package com.stockinos.mobile.wizardofoz.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stockinos.mobile.wizardofoz.MessagesWidget
import com.stockinos.mobile.wizardofoz.WoZApplication
import com.stockinos.mobile.wizardofoz.ui.conversation.ConversationScreen
import com.stockinos.mobile.wizardofoz.ui.conversation.ConversationViewModel
import com.stockinos.mobile.wizardofoz.ui.home.HomeScreen
import com.stockinos.mobile.wizardofoz.ui.messages.MessagesScreen
import com.stockinos.mobile.wizardofoz.ui.messages.MessagesViewModel
import com.stockinos.mobile.wizardofoz.ui.signin.SignInScreen
import com.stockinos.mobile.wizardofoz.ui.signin.SignInViewModel
import com.stockinos.mobile.wizardofoz.ui.signotp.SignInOTPScreen
import com.stockinos.mobile.wizardofoz.ui.signotp.SignInOTPViewModel
import com.stockinos.mobile.wizardofoz.ui.viewmodels.TokenViewModel
import com.tschwaa.mobile.SplashScreen

@Composable
fun Navigation(navController: NavHostController) {
    val TAG = "Navigation"

    NavHost(navController = navController, startDestination = Routes.Splash.route) {
        composable(route = Routes.Splash.route) {
            SplashScreen(
                navController = navController,
                tokenViewModel = viewModel(factory = TokenViewModel.Factory)
            )
        }
        composable(route = Routes.SignIn.route) {
            SignInScreen(
                navController = navController,
                signInViewModel = viewModel(factory = SignInViewModel.Factory)
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
        composable(route = Routes.Messages.route) {
            MessagesScreen(
                navController = navController,
                messagesViewModel = viewModel(factory = MessagesViewModel.Factory)
            )
        }
        composable(
            route = Routes.Conversation.route,
            arguments = listOf(
                navArgument("phoneNumber") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { navBackStackEntry ->
            ConversationScreen(
                conversationViewModel = viewModel(factory = ConversationViewModel.Factory)
            )
        }
    }
}