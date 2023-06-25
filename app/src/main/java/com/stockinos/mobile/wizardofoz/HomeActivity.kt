package com.stockinos.mobile.wizardofoz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.stockinos.mobile.wizardofoz.components.drawer.WoZDrawer
import com.stockinos.mobile.wizardofoz.models.User
import com.stockinos.mobile.wizardofoz.navigation.NavigationAuthenticated
import com.stockinos.mobile.wizardofoz.ui.theme.WizardOfOzTheme
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(
                Intent(context, HomeActivity::class.java)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HomeBaseView {
                val navController = rememberNavController()
                NavigationAuthenticated(navController = navController)
            }
        }
    }
}

@Composable
fun HomeBaseView(
    content: @Composable () -> Unit
) {
    WizardOfOzTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // MessagesWidget(messagesViewModel = messagesViewModel)
            val drawerState = rememberDrawerState(DrawerValue.Open)
            val scope = rememberCoroutineScope()
            if (drawerState.isOpen) {
                // BackPressHan
            }
            val openDrawer = {
                scope.launch {
                    drawerState.open()
                }
            }

            WoZDrawer(
                drawerState = drawerState,
                user = User("Oz", "699002233")
            ) {
                content()
            }
        }
    }
}