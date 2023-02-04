package com.stockinos.mobile.wizardofoz.ui.conversation

import android.renderscript.ScriptGroup.Input
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stockinos.mobile.wizardofoz.FunctionalityNotAvailablePopup

enum class InputSelector {
    NONE,
    MAP,
    DM,
    EMOJI,
    PHONE,
    PICTURE
}

@ExperimentalFoundationApi
@Composable
private fun ResponseInputText(
    textFieldValue: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit,
    onTextFieldFocused: (Boolean) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardShow: Boolean,
    focusState: Boolean
) {
    Surface {
        Row(
           modifier = Modifier
               .fillMaxWidth()
               .height(64.dp)
               .semantics {
                   contentDescription = "Text input"
                   // keyboardShownProperty = keyboardShow
               },
           horizontalArrangement = Arrangement.End
        ) {
            Surface {
                Box(
                    modifier = Modifier
                        .height(64.dp)
                        .weight(1f)
                        .align(Alignment.Bottom)
                ) {
                    var lastFocusState by remember { mutableStateOf(false) }
                    BasicTextField(
                        value = textFieldValue,
                        onValueChange = { onTextChanged(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp)
                            .align(Alignment.CenterStart)
                            .onFocusChanged { state ->
                                if (lastFocusState != state.isFocused) {
                                    onTextFieldFocused(state.isFocused)
                                }
                                lastFocusState = state.isFocused
                            },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = keyboardType,
                            imeAction = ImeAction.Send
                        ),
                        maxLines = 1,
                        cursorBrush = SolidColor(LocalContentColor.current),
                        textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current)
                    )

                    val disableContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    if (textFieldValue.text.isEmpty() && !focusState) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 32.dp),
                            text = "Message (the name / number of current user)",
                            style = MaterialTheme.typography.bodyLarge.copy(color = disableContentColor)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ResponseInputSelectorButton(
    onClick: () -> Unit = {},
    icon: ImageVector,
    description: String,
    selected: Boolean
) {
    val backgroundModifier = if (selected) {
        Modifier.background(
            color = MaterialTheme.colorScheme.secondary,
            shape = RoundedCornerShape(14.dp)
        )
    } else {
        Modifier
    }

    IconButton(
        modifier = Modifier
            .size(56.dp)
            .then(backgroundModifier),
        onClick = onClick
    ) {
        val tint = if (selected) {
            MaterialTheme.colorScheme.onSecondary
        } else {
            MaterialTheme.colorScheme.secondary
        }

        Icon(
            icon,
            tint = tint,
            modifier = Modifier.padding(16.dp),
            contentDescription = description
        )
    }
}

@Composable
fun ResponseInputSelector(
    modifier: Modifier = Modifier,
    sendMessageEnabled: Boolean,
    currentInputSelector: InputSelector,
    onSelectorChange: (InputSelector) -> Unit,
    onMessageSent: () -> Unit
) {
    Row (
        modifier = modifier
            .height(72.dp)
            .wrapContentHeight()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ResponseInputSelectorButton(
            icon = Icons.Outlined.Mood,
            description = "Show Emoji selector",
            selected = currentInputSelector == InputSelector.EMOJI
        )
        ResponseInputSelectorButton(
            icon = Icons.Outlined.AlternateEmail,
            description = "Direct Message",
            selected = currentInputSelector == InputSelector.EMOJI
        )
        ResponseInputSelectorButton(
            icon = Icons.Outlined.InsertPhoto,
            description = "Attach Photo",
            selected = currentInputSelector == InputSelector.EMOJI
        )
        ResponseInputSelectorButton(
            icon = Icons.Outlined.Place,
            description = "Location selector",
            selected = currentInputSelector == InputSelector.EMOJI
        )
        ResponseInputSelectorButton(
            icon = Icons.Outlined.Duo,
            description = "Start videochat",
            selected = currentInputSelector == InputSelector.EMOJI
        )

        val border = if (!sendMessageEnabled) {
            BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
        } else {
            null
        }
        Spacer(modifier = Modifier.weight(1f))

        val disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)

        val buttonColors = ButtonDefaults.buttonColors(
            disabledContainerColor = Color.Transparent,
            disabledContentColor = disabledContentColor
        )

        // Send button
        Button(
            modifier = Modifier.height(36.dp),
            enabled = sendMessageEnabled,
            onClick = onMessageSent,
            colors = buttonColors,
            border = border,
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                "Send",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun NotAvailablePopup(onDismissed: () -> Unit) {
    FunctionalityNotAvailablePopup(onDismissed)
}

@Composable
private fun FunctionalityNotAvailablePanel() {
    AnimatedVisibility(
        visibleState = remember { MutableTransitionState(false).apply { targetState = true } },
        enter = expandHorizontally() + fadeIn(),
        exit = shrinkHorizontally() + fadeOut()
    ) {
        Column(
            modifier = Modifier
                .height(320.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Functionality currently not available",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Grab a beverage and check back later!",
                modifier = Modifier.paddingFrom(FirstBaseline, before = 32.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SelectorExpanded(
    currentSelector: InputSelector,
    onCloseRequested: () -> Unit,
    onTextAdded: (String) -> Unit
) {
    if (currentSelector == InputSelector.NONE) return

    // Request focus to force the TextField to lose it
    val focusRequester = FocusRequester()
    //  If the selector is shown, always request focus to trigger a TextField.onFocusChange
    SideEffect {
        if (currentSelector == InputSelector.EMOJI) {
            focusRequester.requestFocus()
        }
    }

    Surface(tonalElevation = 8.dp) {
        when (currentSelector) {
            InputSelector.EMOJI -> NotAvailablePopup(onCloseRequested)
            InputSelector.DM -> NotAvailablePopup(onCloseRequested)
            InputSelector.PICTURE -> FunctionalityNotAvailablePanel()
            InputSelector.MAP -> FunctionalityNotAvailablePanel()
            InputSelector.PHONE -> FunctionalityNotAvailablePanel()
            else -> { throw  NotImplementedError() }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ResponseInput(
    modifier: Modifier = Modifier,
    onMessageSent: (String) -> Unit,
    resetScroll: () -> Unit = {}
) {
    var currentInputSelector by rememberSaveable { mutableStateOf(InputSelector.NONE) }
    val dismissKeyboard = { currentInputSelector = InputSelector.NONE }
    var textState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    // Used to decide if the keyboard should be shown
    var textFieldFocusState by remember { mutableStateOf(false) }

    Surface(tonalElevation = 2.dp) {
        Column(modifier = modifier) {
            ResponseInputText(
                textFieldValue = textState,
                onTextChanged = { textState = it},
                // Close extended selector if text field receives focus
                onTextFieldFocused = { focused ->
                    if (focused) {
                        currentInputSelector = InputSelector.NONE
                        resetScroll()
                    }
                    textFieldFocusState = focused
                },
                // Only show the keyboard if there's no input selector and text field has focus
                keyboardShow = currentInputSelector == InputSelector.NONE && textFieldFocusState,
                focusState = textFieldFocusState
            )
            ResponseInputSelector(
                sendMessageEnabled = textState.text.isNotBlank(),
                currentInputSelector = currentInputSelector,
                onSelectorChange = { currentInputSelector = it},
                onMessageSent = {
                    onMessageSent(textState.text)
                    // Reset text field and close keyboard
                    textState = TextFieldValue()
                    // Move scroll to bottom
                    resetScroll()
                    dismissKeyboard()
                }
            )
            SelectorExpanded(
                currentSelector = currentInputSelector,
                onCloseRequested = dismissKeyboard,
                onTextAdded = { textState = textState.addText(it) },
            )
        }
    }
}

private fun TextFieldValue.addText(newString: String): TextFieldValue {
    val newText = this.text.replaceRange(
        this.selection.start,
        this.selection.end,
        newString
    )
    val newSelection = TextRange(
        start = newText.length,
        end = newText.length
    )

    return this.copy(text = newText, selection = newSelection)
}

@Preview
@Composable
fun ResponseInputPreview() {
    ResponseInput(
        onMessageSent = {}
    )
}