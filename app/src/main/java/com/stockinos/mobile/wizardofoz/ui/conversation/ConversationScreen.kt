package com.stockinos.mobile.wizardofoz.ui.conversation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stockinos.mobile.wizardofoz.R
import com.stockinos.mobile.wizardofoz.models.WhatsappMessage
import com.stockinos.mobile.wizardofoz.ui.theme.WizardOfOzTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(
    conversationViewModel: ConversationViewModel,
    navigationToProfile: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    onNavIconPressed: () -> Unit = {}
) {
    var scrollState = rememberLazyListState()
    var topBarState = rememberTopAppBarState()
    var scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)
    val scope = rememberCoroutineScope()

    val conversationUiState by conversationViewModel.uiState.collectAsState()

    Surface(modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                Messages(
                    messages = conversationUiState.messagesItems,
                    scrollState = scrollState
                )
            }
        }
    }
}

@Composable
fun Messages(
    messages: List<WhatsappMessage>,
    scrollState: LazyListState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    Box(modifier = modifier) {
        val authorMe = "me"
        LazyColumn(
            reverseLayout = true,
            state = scrollState,
            contentPadding = WindowInsets.statusBars.add(WindowInsets(top = 90.dp)).asPaddingValues(),
            modifier = Modifier
                // .testTag()
                .fillMaxSize()
        ) {
            for (index in messages.indices) {
                val prevAuthor = messages.getOrNull(index - 1)?.from
                val nextAuthor = messages.getOrNull(index + 1)?.from
                val content = messages[index]
                val isFirstMessageByAuthor = prevAuthor != content.from
                val isLastMessageByAuthor = nextAuthor != content.from

                // Hardcode day dividers for simplicity
                if (index == messages.size - 1) {
                    item {
                        DayHeader("20 Aug")
                    }
                } else if (index == 2) {
                    item {
                        DayHeader("Today")
                    }
                }

                item {
                    Message(
                        onAuthorClick = { name -> },
                        message = content,
                        isUserMe = content.from == authorMe,
                        isFirstMessageByAuthor = isFirstMessageByAuthor,
                        isLastMessageByAuthor = isLastMessageByAuthor
                    )
                }
            }
        }
    }
}

@Composable
fun Message(
    onAuthorClick: (String) -> Unit,
    message: WhatsappMessage,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean
) {
    val borderColor = if (isUserMe) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.tertiary
    }
    
    val spaceBetweenAuthors = if (isLastMessageByAuthor)  Modifier.padding(top = 8.dp) else Modifier
    Row(modifier = spaceBetweenAuthors) {
        if (isLastMessageByAuthor) {
            // Avatar
            Image(
                modifier = Modifier
                    .clickable(onClick = {})
                    .padding(horizontal = 16.dp)
                    .size(42.dp)
                    .border(1.5.dp, borderColor, CircleShape)
                    .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape)
                    .clip(CircleShape)
                    .align(Alignment.Top),
                painter = painterResource(id = R.drawable.ali),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        } else {
            // Space under avatar
            Spacer(modifier = Modifier.width(74.dp))
        }
        AuthorAndTextMessage(
            message = message,
            isUserMe = isUserMe,
            isFirstMessageByAuthor = isFirstMessageByAuthor,
            isLastMessageByAuthor = isLastMessageByAuthor,
            authorClicked = onAuthorClick,
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f)
        )
    }
}

@Composable
fun AuthorAndTextMessage(
    message: WhatsappMessage,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    authorClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (isLastMessageByAuthor) {
            AuthorNameTimestamp(message)
        }
        ChatItemBubble(message, isUserMe, authorClicked = authorClicked)
        if (isFirstMessageByAuthor) {
            // Last bubblle before next author
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            // Between bubbles
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun AuthorNameTimestamp(message: WhatsappMessage) {
    // Combine author and timestamp for a11y
    Row(modifier = Modifier.semantics(mergeDescendants = true) {}) {
        Text(
            text = message.from,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .alignBy(LastBaseline)
                .paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = message.timestamp,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.alignBy(LastBaseline),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private val ChatBubbleShape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)

@Composable
fun ChatItemBubble(
    message: WhatsappMessage,
    isUserMe: Boolean,
    authorClicked: (String) -> Unit
) {
    val backgroundBubbleColor = if (isUserMe) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    Column {
        Surface(
            color = backgroundBubbleColor,
            shape = ChatBubbleShape
        ) {
            ClickableMessage(
                message = message,
                isUserMe = isUserMe,
                authorClicked = authorClicked
            )
        }

        // message.from
        message.imageId?.let {
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                color = backgroundBubbleColor,
                shape = ChatBubbleShape
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ali),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(160.dp),
                    contentDescription = "Attached image"
                )
            }
        }
    }
}

@Composable
fun ClickableMessage(
    message: WhatsappMessage,
    isUserMe: Boolean,
    authorClicked: (String) -> Unit
) {
    val uriHandler = LocalUriHandler.current

    val styledMessage = messageFormatter(
        text = message.text?.body.toString(),
        primary = isUserMe
    )

    ClickableText(
        text = styledMessage,
        style = MaterialTheme.typography.bodyLarge.copy(color = LocalContentColor.current),
        modifier = Modifier.padding(16.dp),
        onClick = {
            styledMessage
                .getStringAnnotations(start = it, end = it)
                .firstOrNull()
                ?.let { annotation ->
                    when (annotation.tag) {
                        SymbolAnnotationType.LINK.name -> uriHandler.openUri(annotation.item)
                        SymbolAnnotationType.PERSON.name -> authorClicked(annotation.item)
                        else -> Unit
                    }
                }
        }
    )
}

@Composable
fun DayHeader(dayString: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .height(16.dp)
    ) {
        DayHeaderLine()
        Text(
            text = dayString,
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        DayHeaderLine()
    }
}

@Composable
private fun RowScope.DayHeaderLine() {
    Divider(
        modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    )
}

@Preview
@Composable
fun ConversationContentPreview() {
    WizardOfOzTheme {
        // Messages(messages = exampleConversationUiState.messagesItems)
    }
}