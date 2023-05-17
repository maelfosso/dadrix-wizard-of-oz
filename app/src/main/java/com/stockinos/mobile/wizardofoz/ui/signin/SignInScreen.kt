package com.stockinos.mobile.wizardofoz.ui.signin

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stockinos.mobile.wizardofoz.navigation.Routes
import com.stockinos.mobile.wizardofoz.ui.theme.WizardOfOzTheme
import com.stockinos.mobile.wizardofoz.utils.boldTextStyle
import com.stockinos.mobile.wizardofoz.utils.height
import com.stockinos.mobile.wizardofoz.utils.secondaryTextStyle

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavController) {
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    // IconButton(onClick = { navController.popBackStack() } ) {
                    //     Icon(
                    //         imageVector = Icons.Filled.ArrowBack,
                    //         contentDescription = null,
                    //         modifier = Modifier.padding(horizontal = 8.dp),
                    //         tint = MaterialTheme.colorScheme.primary
                    //     )
                    // }
                },

            )
        },
        content = {
            Box(
                modifier = Modifier.padding(16.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier.padding(it),
                    content = {
                        item {
                            Text(
                                "Easy to learn,\ndiscover more\nskills",
                                style = boldTextStyle(fontSize = 24.sp)
                            )
                            10.height()
                            Text(
                                "Sign in to your account",
                                style = secondaryTextStyle(fontSize = 16.sp)
                            )
                            24.height()
                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                value = phoneNumber,
                                onValueChange = {
                                    phoneNumber = it
                                }
                            )
                            26.height()
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                onClick = {
                                    navController.navigate(Routes.SignInOTP.route)
                                }
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = "Sign In",
                                    style = boldTextStyle(
                                        color = Color.White
                                    )
                                )
                            }
                        }
                    }
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyOTPScreen(phoneNumber: String) {
    Column {
        Text(
            text = "Please enter the 6-digit verification code that was sent to $phoneNumber. The code is valid for 30 minutes"
        )

        Column {
            Text(text = "Verification code - OTP")
            TextField(
                value = "",
                onValueChange = {},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Verify code")
        }

        Column {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Resend the code")
            }
        }
    }
}

@Preview
@Composable
fun AuthScreenPreview() {
    WizardOfOzTheme {
        VerifyOTPScreen("689234322")
    }
}