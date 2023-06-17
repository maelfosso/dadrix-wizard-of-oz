package com.stockinos.mobile.wizardofoz.ui.signin

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.stockinos.mobile.wizardofoz.CoroutinesHandler
import com.stockinos.mobile.wizardofoz.models.GetError
import com.stockinos.mobile.wizardofoz.navigation.Routes
import com.stockinos.mobile.wizardofoz.ui.theme.WizardOfOzTheme
import com.stockinos.mobile.wizardofoz.utils.boldTextStyle
import com.stockinos.mobile.wizardofoz.utils.errorTextStyle
import com.stockinos.mobile.wizardofoz.utils.height
import com.stockinos.mobile.wizardofoz.utils.secondaryTextStyle

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navController: NavController,
    signInViewModel: SignInViewModel
) {
    val TAG: String = "SignInScreen"
    val signInUiState by signInViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {},
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                SignInScreenContent(
                    signInUiState,
                    onPhoneNumberChange = { phoneNumber ->
                        signInViewModel.handlePhoneNumberChanges(phoneNumber)
                    },
                    onSignIn = {
                        signInViewModel.signIn(
                            onSuccess = { phoneNumber ->
                                Log.d("SignInScreen", "onSuccess : $phoneNumber - ${Routes.SignInOTP.route + "/$phoneNumber"}")
                                navController.navigate(
                                    Routes.SignInOTP.route + "/$phoneNumber"
                                )
                            }
                        )
                    }
                )
            }
        }
    )
}

@Composable
fun SignInScreenContent(
    signInUiState: SignInUiState,
    onPhoneNumberChange: (phoneNumber: String) -> Unit,
    onSignIn: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 8.dp,
            bottom = 16.dp
        ),
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
                    value = signInUiState.phoneNumber,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    ),
                    onValueChange = {
                        onPhoneNumberChange(it)
                    }
                )
                26.height()
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        onSignIn()
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

val getErrors: Map<String, GetError> = mapOf(
    "UNKNOWN" to GetError(message = "an unknown error happens, kindly restart the app and try again"),
    "ERR_COTP_150" to GetError(message = "error when sending the OTP via WhatsApp"), // Nothing To Do - No Action Needed NAN
    "ERR_COTP_151" to GetError(message = "error when creating the user account", cta = "Try again"),
    "ERR_COTP_152" to GetError(message = "error when saving the generated OTP"), // NAN
)

@Composable
fun Error(error: String) {
    if (error.isNotBlank() || error.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            8.height()
            Text(
                com.stockinos.mobile.wizardofoz.ui.signotp.getErrors[error]!!.message,
                style = errorTextStyle() // (fontSize = 16.sp)
            )
            24.height()
        }
    }
}

@Preview
@Composable
fun AuthScreenPreview() {
    WizardOfOzTheme {
        SignInScreen(
            navController = rememberNavController(),
            signInViewModel = viewModel(factory = SignInViewModel.Factory)
        )
    }
}