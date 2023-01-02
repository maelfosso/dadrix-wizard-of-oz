package com.stockinos.mobile.wizardofoz.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.stockinos.mobile.wizardofoz.models.WhatsappMessage

@Composable
fun MessageItem(
    message: WhatsappMessage,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBFE))
            .drawBehind {
                val borderSize = 1.dp.toPx()
                val y = size.height - borderSize / 2
                drawLine(
                    color = Color(0xFFCAC4D0),
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = borderSize
                )
            }
    ) {
        Row(
            modifier = Modifier
                .padding(
                    top = 12.dp,
                    end = 24.dp,
                    bottom = 12.dp,
                    start = 16.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEADDFF)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = message.from.uppercase()[0].toString(),
                    style = TextStyle(
                        fontWeight = FontWeight.W500,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        letterSpacing = 0.15.sp,
                        color = Color(0xFF21005D)
                    )
                )
            }
            Column {
                Text(
                    text = message.from,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        letterSpacing = 0.5.sp,
                        lineHeight = 24.sp,
                        color = Color(0xFF1C1B1F)
                    )
                )
                Text(
                    text = message.text!!.body,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        letterSpacing = 0.25.sp,
                        lineHeight = 20.sp,
                        color = Color(0xFF49454F)
                    )
                )
            }
        }
    }
}

fun whatsappMessageText() = """
{
    id: 'wamid.HBgMMjM3Njc4OTA4OTg5FQIAEhggREZERDlCM0FERTZCRENGNTNFRTY4NzU3MTYxNEI0M0YA',
    from: 'Stock Inos',
    timestamp: '1672344794',
    type: 'text',
    text_id: '00000000-0000-0000-0000-000000000000',
    text: {
        ID: 0,
        CreatedAt: '0001-01-01T00:00:00Z',
        UpdatedAt: '0001-01-01T00:00:00Z',
        DeletedAt: null,
        id: '00000000-0000-0000-0000-000000000000',
        body: "It seems like you know how to use Figma because I am watching you using it with Material"
    },
    image: {
        ID: 0,
        CreatedAt: '0001-01-01T00:00:00Z',
        UpdatedAt: '0001-01-01T00:00:00Z',
        DeletedAt: null,
        caption: '',
        mime_type: '',
        sha256: '',
        id: ''
    },
    audio: {
        ID: 0,
        CreatedAt: '0001-01-01T00:00:00Z',
        UpdatedAt: '0001-01-01T00:00:00Z',
        DeletedAt: null,
        mime_type: '',
        sha256: '',
        id: '',
        voice: false
    }
}
"""


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val message = Gson().fromJson(
        whatsappMessageText(),
        WhatsappMessage::class.java
    )
    MessageItem(
        message
    )
}
