package com.stockinos.mobile.wizardofoz.components.drawer

import android.widget.ImageView
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.material3.DrawerValue.Open
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.stockinos.mobile.wizardofoz.models.User
import com.stockinos.mobile.wizardofoz.ui.theme.WizardOfOzTheme

@Composable
fun WoZDrawer(
    drawerState: DrawerState = rememberDrawerState(initialValue = Closed),
    user: User,
    onNavigationItemClick: (item: ImageVector) -> Unit,
    content: @Composable () -> Unit
) {
    WizardOfOzTheme {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                WoZDrawerContent(
                    user,
                    onNavigationItemClick = { it ->
                        onNavigationItemClick(it)
                    }
                )


            },
            content = content
        )
    }
}