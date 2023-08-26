package com.stockinos.mobile.wizardofoz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.stockinos.mobile.wizardofoz.components.BackPressHandler
import com.stockinos.mobile.wizardofoz.components.LocalBackPressedDispatcher
import com.stockinos.mobile.wizardofoz.components.drawer.WoZDrawer
import com.stockinos.mobile.wizardofoz.models.User
import com.stockinos.mobile.wizardofoz.navigation.NavigationAuthenticated
import com.stockinos.mobile.wizardofoz.ui.theme.WizardOfOzTheme
import com.stockinos.mobile.wizardofoz.viewmodels.HomeViewModel
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(
                Intent(context, HomeActivity::class.java)
            )
        }
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CompositionLocalProvider(
                LocalBackPressedDispatcher provides this@HomeActivity.onBackPressedDispatcher
            ) {
                HomeBaseView(viewModel) {
                    val navController = rememberNavController()
                    NavigationAuthenticated(navController = navController, homeViewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun HomeBaseView(
    homeViewModel: HomeViewModel,
    content: @Composable () -> Unit
) {

    WizardOfOzTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {


            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            // val drawerOpen by re
            val drawerOpen by homeViewModel.drawerShouldBeOpened.collectAsStateWithLifecycle()
            Log.d("HomeViewModel", "drawerOpen: $drawerOpen")
            if (drawerOpen) {
                LaunchedEffect(Unit) {
                    try {
                        Log.d("HomeViewModel", "Launched Effect Unit - drawer open()")
                        drawerState.open()
                    } finally {
                        homeViewModel.resetOpenDrawerAction()
                    }
                }
            }
            if (drawerState.isOpen) {
                BackPressHandler {
                    scope.launch { drawerState.close() }
                }
            }
            // val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
            // val selectedItem = remember { mutableStateOf(items[0]) }

            WoZDrawer(
                drawerState = drawerState,
                user = User("Oz", "699002233", "oz"),
                onNavigationItemClick = { it ->
                    scope.launch { drawerState.close() }
                }
            ) {
                content()
            }
        }
    }
}
