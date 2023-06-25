package com.stockinos.mobile.wizardofoz.components.drawer

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.material3.DrawerValue.Open
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import com.stockinos.mobile.wizardofoz.models.User
import com.stockinos.mobile.wizardofoz.ui.theme.WizardOfOzTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WoZDrawer(
    drawerState: DrawerState = rememberDrawerState(initialValue = Open),
    user: User,
    content: @Composable () -> Unit
) {
    WizardOfOzTheme() {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    WoZDrawerContent(
                        user
                    )
                }
            },
            content = content
        )
    }
}