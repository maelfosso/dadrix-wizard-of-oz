package com.stockinos.mobile.wizardofoz.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stockinos.mobile.wizardofoz.models.WhatsappMessage

@Composable
fun MessageItem(
    message: WhatsappMessage,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ) {
        Text(
            text = message.from
        )
        Text(
            text = message.text!!.body
        )
    }
}
