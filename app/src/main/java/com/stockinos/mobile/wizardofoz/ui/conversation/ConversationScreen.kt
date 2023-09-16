package com.stockinos.mobile.wizardofoz.ui.conversation

import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stockinos.mobile.wizardofoz.R
import com.stockinos.mobile.wizardofoz.models.*
import com.stockinos.mobile.wizardofoz.ui.theme.WizardOfOzTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(
    navController: NavController,
    conversationViewModel: ConversationViewModel,
    modifier: Modifier = Modifier,
    navigationToProfile: (String) -> Unit = {},
    onNavIconPressed: () -> Unit = {}
) {
    val conversationUiState by conversationViewModel.uiState.collectAsState()
    val phoneNumber = conversationUiState.user

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier,
                title = {
                    Text(
                        text = phoneNumber,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "ContentDescription")
                    }
                },
            )
        },

        content = { innerPadding ->
            ConversationScreenContent(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .wrapContentSize(),
                conversationUiState,
                sendMessage = { message -> conversationViewModel.sendMessage(message) }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreenContent(
    modifier: Modifier = Modifier,
    conversationUiState: ConversationUiState,
    sendMessage: (String) -> Unit = {}
) {
    var scrollState = rememberLazyListState()
    var topBarState = rememberTopAppBarState()
    var scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)
    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            Messages(
                modifier = Modifier.weight(1f),
                author = conversationUiState.user,
                messages = conversationUiState.messagesItems,
                scrollState = scrollState
            )
            ResponseInput(
                onMessageSent = {
                    sendMessage(it)
                },
                resetScroll = {
                    scope.launch {
                        scrollState.scrollToItem(0)
                    }
                },
                // Use navigationBarsPadding() imePadding() and , to move the input panel above both the
                // navigation bar, and on-screen keyboard (IME)
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding()
            )
        }
    }
}

@Composable
fun Messages(
    messages: List<MessageWithUser>,
    author: String,
    scrollState: LazyListState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    Box(modifier = modifier) {
        // val author = "me"
        LazyColumn(
            reverseLayout = true,
            state = scrollState,
            contentPadding = WindowInsets.statusBars.add(WindowInsets(top = 90.dp)).asPaddingValues(),
            modifier = Modifier
                // .testTag()
                .fillMaxSize()
        ) {
            Log.d("Messsages ", messages.indices.joinToString())
            for (index in messages.indices) {
                val prevAuthor = messages.getOrNull(index - 1)?.from
                val nextAuthor = messages.getOrNull(index + 1)?.from
                val content = messages[index]
                val isFirstMessageByAuthor = prevAuthor != content.from
                val isLastMessageByAuthor = nextAuthor != content.from
                Log.d("Messsages ", "${index.toString()}, ${messages[index]}, ${messages[index].from}, $isFirstMessageByAuthor, $isLastMessageByAuthor")

                // Hardcode day dividers for simplicity
//                if (index == messages.size - 1) {
//                    item {
//                        DayHeader("20 Aug")
//                    }
//                } else if (index == 2) {
//                    item {
//                        DayHeader("Today")
//                    }
//                }

                item {
                    Message(
                        onAuthorClick = { name -> },
                        message = content,
                        isUserMe = content.from.phoneNumber == author,
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
    message: MessageWithUser,
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
    CompositionLocalProvider(LocalLayoutDirection provides if (isUserMe) LayoutDirection.Ltr else LayoutDirection.Rtl ) {
        Row(
            modifier = spaceBetweenAuthors
        ) {
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
                    painter = painterResource(id = if (isUserMe) R.drawable.ali else R.drawable.someone_else ),
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
}

@Composable
fun AuthorAndTextMessage(
    message: MessageWithUser,
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
private fun AuthorNameTimestamp(message: MessageWithUser) {
    // Combine author and timestamp for a11y
    Row(modifier = Modifier.semantics(mergeDescendants = true) {}) {
        Text(
            text = message.from.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .alignBy(LastBaseline)
                .paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Text(
//            text = message.timestamp,
//            style = MaterialTheme.typography.bodySmall,
//            modifier = Modifier.alignBy(LastBaseline),
//            color = MaterialTheme.colorScheme.onSurfaceVariant
//        )
    }
}

private val ChatBubbleShape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)

@Composable
fun ChatItemBubble(
    message: MessageWithUser,
    isUserMe: Boolean,
    authorClicked: (String) -> Unit
) {
    val backgroundBubbleColor = if (isUserMe) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val sdf = SimpleDateFormat("HH:mm")
    val netDate = "" // Date(message.message.timestamp.toLong() * 1000)
//    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Surface(
            color = backgroundBubbleColor,
            shape = ChatBubbleShape
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                if (message.message.type == MESSAGE_TYPE_TEXT) { // textId != null) {
                    ClickableMessage(
                        message = message.message,
                        isUserMe = isUserMe,
                        authorClicked = authorClicked
                    )
                }

                if (message.message.type == MESSAGE_TYPE_IMAGE) { // imageId != null) {
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
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Row(
                        modifier = Modifier
                    ) {
                        Text(
                            text = sdf.format(netDate),
                            style = MaterialTheme.typography.bodySmall,
                        )
                        if (!isUserMe) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "", // message.message.state.toString(),
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }
                }
            }
        }
//    }
}


// @Preview
// @Composable
// fun ChatItemBubblePreview() {
//     WizardOfOzTheme {
//         // Messages(messages = exampleConversationUiState.messagesItems)
//         ChatItemBubble(
//             message = exampleConversationUiState.messagesItems[0],
//             isUserMe = false,
//             authorClicked = {}
//         )
//     }
// }

@Composable
fun ClickableMessage(
    message: Message,
    isUserMe: Boolean,
    authorClicked: (String) -> Unit
) {
    val uriHandler = LocalUriHandler.current

    val styledMessage = messageFormatter(
        text = (message as MessageText).body.toString(),
        primary = isUserMe
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        ClickableText(
            text = styledMessage,
            style = MaterialTheme.typography.bodyLarge.copy(color = LocalContentColor.current),
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