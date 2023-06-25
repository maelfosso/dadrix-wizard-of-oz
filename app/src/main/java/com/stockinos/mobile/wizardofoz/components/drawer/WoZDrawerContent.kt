package com.stockinos.mobile.wizardofoz.components.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stockinos.mobile.wizardofoz.models.User

@Composable
fun WoZDrawerContent(
    user: User
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        DrawerHeader(user)
        DividerItem()
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
