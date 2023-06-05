package com.stockinos.mobile.wizardofoz.navigation

sealed class Routes(val route: String) {
    object SignIn: Routes("signin")
    object SignInOTP: Routes("signin/otp")
    object Home: Routes("home")
    object Messages: Routes("messages")
    object Conversation: Routes("conversations/{phoneNumber}")
}