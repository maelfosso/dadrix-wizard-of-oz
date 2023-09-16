package com.stockinos.mobile.wizardofoz.ui.messages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stockinos.mobile.wizardofoz.models.User
import com.stockinos.mobile.wizardofoz.utils.width
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MessageItem(
    message: MessagesAboutUser,
    modifier: Modifier = Modifier,
    onClick: (user: String) -> Unit
) {
    val TAG = "MessageItem"
    val context = LocalContext.current

    val user = message.user

    Box(
        modifier = modifier
            .fillMaxWidth()
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
            .clickable(
                onClick = {
                    Toast
                        .makeText(
                            context,
                            "Opening messages from ${user.name.ifEmpty { user.phoneNumber }}",
                            Toast.LENGTH_LONG
                        )
                        .show()
                    Log.v(TAG, "Opening messages from ${message.user}")
                    onClick(user.phoneNumber)
                }
            )
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
                    text = (
                        user.name.ifEmpty { user.phoneNumber }
                    ).uppercase()[0].toString(),
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Author(user = user)
                    LastMessageTime(message.lastMessage.timestamp)
                }

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = message.lastMessage.summary(),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W400,
                            letterSpacing = 0.25.sp,
                            lineHeight = 20.sp,
                            color = Color(0xFF49454F)
                        )
                    )

                    if (message.nbUnreadMessages > 0) {
                        Box(
                            modifier = Modifier
                                .width(34.dp)
                                .height(20.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFB3261E)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = message.nbUnreadMessages.toString(), // message.from.uppercase()[0].toString(),
                                style = TextStyle(
                                    fontWeight = FontWeight.W400,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    letterSpacing = 0.25.sp,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun Author(user: User) {
    if (user.name.isNotEmpty() && user.name.isNotBlank()) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = user.name,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    letterSpacing = 0.5.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF1C1B1F)
                )
            )
            5.width()
            Text(
                text = user.phoneNumber,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W300,
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 0.5.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF1C1B1F)
                )
            )
        }
    } else {
        Text(
            text = user.phoneNumber,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                letterSpacing = 0.5.sp,
                lineHeight = 24.sp,
                color = Color(0xFF1C1B1F)
            )
        )
    }
}

@Composable
fun LastMessageTime(timestamp: String) {
    val time = timestamp.toLongOrNull()
    timeAgo(time!!)?.let {
        Text(
            text = it,
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

fun timestampToMilli(time: Long): Long {
    return if (time < 1000000000000L) {
        // if timestamp given in seconds, convert to millis
        time * 1000
    } else {
        time
    }
}

fun timeAgo(time: Long): String? {
    val mTime = timestampToMilli(time)
    val currentTime = System.currentTimeMillis()
    if (mTime > currentTime || mTime <= 0) return null
    val timeElapsed = currentTime - mTime
    val thisDay: Int = SimpleDateFormat("dd", Locale.US).format(currentTime).toInt()
    val thisMonth: Int = SimpleDateFormat("MM", Locale.US).format(currentTime).toInt()
    val thisYear: Int = SimpleDateFormat("yyy", Locale.US).format(currentTime).toInt()
    val agoDay: Int = SimpleDateFormat("dd", Locale.US).format(mTime).toInt()
    val agoMonth: Int = SimpleDateFormat("MM", Locale.US).format(mTime).toInt()
    val agoYear: Int = SimpleDateFormat("yyy", Locale.US).format(mTime).toInt()

    val seconds = timeElapsed
    val minutes = Math.round((timeElapsed / 60000).toDouble()).toInt()
    val hours = Math.round((timeElapsed / 3600000).toDouble()).toInt()
    val days = Math.round((timeElapsed / 86400000).toDouble()).toInt()
    val weeks = Math.round((timeElapsed / 604800000).toDouble()).toInt()
    val months = Math.round((timeElapsed / 2600640000).toDouble()).toInt()
    val years = Math.round((timeElapsed / 31207680000).toDouble()).toInt()
    // Yesterday
    if ((thisYear - agoYear) >= 1) {
        return SimpleDateFormat("MMM dd, yyyy - hh:ma", Locale.US).format(mTime)
    } else if ((thisMonth - agoMonth) >= 1) {
        return SimpleDateFormat("MMM dd", Locale.US).format(mTime)
    } else if (thisMonth == agoMonth && (thisDay - agoDay) == 1) {
        return "Yesterday"
    } // Seconds
    else if (seconds <= 60) {
        return "just now"
    } // Minutes
    else if (minutes <= 60) {
        return if (minutes == 1) {
            "one minute ago"
        } else {
            "${minutes}min ago"
        }
    } // Hours
    else if (hours <= 24) {
        return if (hours == 1) {
            "an hour and " + (minutes - 60) + "min ago"
        } else {
            "$hours hrs ago"
        }
    } // Days
    else if (days <= 7) {
        return if (days == 1) {
            "$days day and " + (hours - 24) + "hrs ago"
        } else {
            "$days days ago"
        }
    } // Weeks
    else if (weeks <= 4.3) {
        return if (weeks == 1) {
            "a week ago"
        } else {
            "$weeks weeks ago"
        }
    }
    return null
}

// how to use Figma because I am watching you using it with Material
fun messageText() = """
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
        body: "It seems like you know how to use Figma do you think we can help"
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
