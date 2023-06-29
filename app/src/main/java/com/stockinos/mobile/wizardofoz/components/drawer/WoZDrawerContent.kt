package com.stockinos.mobile.wizardofoz.components.drawer

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.stockinos.mobile.wizardofoz.models.User
import kotlinx.coroutines.launch

@Composable
fun WoZDrawerContent(
    user: User,
    onNavigationItemClick: (item: ImageVector) -> Unit,
) {
    val context = LocalContext.current

    val items = listOf(
        Icons.Default.Message,
    )
    val selectedItem = remember { mutableStateOf(items[0]) }

    ModalDrawerSheet {
        Spacer(modifier = Modifier.height(12.dp))
        items.forEach { item ->
            NavigationDrawerItem(
                icon = { Icon(item, contentDescription = null) },
                label = {
                    Text(item.name)
                },
                selected = item == selectedItem.value,
                onClick = {
                    onNavigationItemClick(item)
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
        Spacer(modifier = Modifier.weight(1.0f))
        DividerItem()
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Logout, contentDescription = null) },
            label = { Text("Sign Out") },
            selected = false,
            onClick = { Toast.makeText(context, "Sign out", Toast.LENGTH_SHORT) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}

@Composable
private fun DrawerHeader(user: User) {
    Column(
        modifier = Modifier.padding(16.dp),
        // horizontalAlignment = Alignment.Center
    ) {
        Text(text = user.name)
        Text(text = user.phoneNumber)
    }
}

@Composable
private fun DrawerItemHeader(text: String) {
    Box(
       modifier = Modifier
           .heightIn(min = 52.dp)
           .padding(horizontal = 28.dp),
       contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun DividerItem(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    )
}
