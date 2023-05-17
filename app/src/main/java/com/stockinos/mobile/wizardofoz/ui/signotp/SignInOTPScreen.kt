package com.stockinos.mobile.wizardofoz.ui.signotp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stockinos.mobile.wizardofoz.navigation.Routes
import com.stockinos.mobile.wizardofoz.utils.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInOTPScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        content = {
            Box(
                modifier = Modifier.padding(it)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
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
                                "Verify Account",
                                style = boldTextStyle(fontSize = 24.sp)
                            )
                            8.height()
                            Text(
                                "Please, fill verification code that have been " +
                                        "\nsent to your WhatsApp account",
                                style = secondaryTextStyle(fontSize = 16.sp)
                            )
                            24.height()
                            OTPCodeTextFields(
                                modifier = Modifier.fillMaxWidth(),
                                onFilled = {}
                            )
                            32.height()
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Didn't receive a code?",
                                style = secondaryTextStyle(textAlign = TextAlign.Center)
                            )
                            8.height()
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "RESEND",
                                style = boldTextStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp
                                )
                            )
                            50.height()
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                onClick = {
                                    navController.navigate(Routes.Home.route)
                                },
                                content = {
                                    Text(
                                        modifier = Modifier.padding(8.dp),
                                        text = "Send",
                                        style = boldTextStyle(color = Color.White)
                                    )
                                }
                            )
                        }
                    }
                )
            }
        }
    )
}

@JvmOverloads
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OTPCodeTextFields(
    modifier: Modifier = Modifier,
    length: Int = 4,
    onFilled: (code: String) -> Unit
) {
    var code: List<Char> by remember {
        mutableStateOf(listOf())
    }
    val focusRequesters: List<FocusRequester> = remember {
        val temp = mutableListOf<FocusRequester>()
        repeat(length) {
            temp.add(FocusRequester())
        }
        temp
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        (0 until length).forEach { index ->
            TextField(
                modifier  = Modifier
                    .width(70.dp)
                    .height(90.dp)
                    .padding(vertical = 2.dp)
                    .focusRequester(focusRequesters[index])
                ,
                textStyle = primaryTextStyle(textAlign = TextAlign.Center),
                value = code.getOrNull(index)?.takeIf { it.isDigit() }?.toString() ?: "",
                onValueChange = { value: String ->
                    if (focusRequesters[index].freeFocus()) {
                        val temp = code.toMutableList()
                        if (value == "") {
                            if (temp.size > index) {
                                temp.removeAt(index)
                                code = temp
                                focusRequesters.getOrNull(index - 1)?.requestFocus()
                            }
                        } else {
                            if (code.size > index) {
                                temp[index] = value.getOrNull(0) ?: ' '
                            } else if (value.getOrNull(0)?.isDigit() == true) {
                                temp.add(value.getOrNull(0) ?: ' ')
                                code = temp
                                focusRequesters.getOrNull(index + 1)?.requestFocus() ?: onFilled(
                                    code.joinToString(separator = "")
                                )
                            }
                        }
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            16.width()

        }
    }
}
