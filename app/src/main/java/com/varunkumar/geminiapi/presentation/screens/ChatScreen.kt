package com.varunkumar.geminiapi.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.varunkumar.geminiapi.R
import com.varunkumar.geminiapi.model.ChatMessage
import com.varunkumar.geminiapi.presentation.UiState
import com.varunkumar.geminiapi.presentation.viewModels.ChatState
import com.varunkumar.geminiapi.presentation.viewModels.ChatViewModel
import com.varunkumar.geminiapi.ui.theme.secondary
import com.varunkumar.geminiapi.utils.appendSuspend
import com.varunkumar.geminiapi.utils.formatTimeStamp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel,
    onBackButtonClick: () -> Unit
) {
    val listState = rememberLazyListState()
    val state = viewModel.state.collectAsState(ChatState()).value
    val shape = RoundedCornerShape(20.dp)
    val focusRequester = remember { FocusRequester() }
    val fModifier = Modifier.fillMaxWidth()
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val bgColor = Color(0xffF7DFF8)
    val dividerColor = Color(0xFFD7D2DD)

    LaunchedEffect(state) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.lastIndex)
        }
    }

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = bgColor,
        topBar = {
            Column {
                TopAppBar(
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.Black,
                        scrolledContainerColor = Color.Transparent
                    ),
                    navigationIcon = {
                        IconButton(onClick = { onBackButtonClick() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                    },
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.squiggle),
                                modifier = Modifier.size(30.dp),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "Gemini")
                        }
                    }
                )

                HorizontalDivider(color = dividerColor)
            }
        },
        bottomBar = {
            Column {
                HorizontalDivider(color = dividerColor)

                OutlinedTextField(
                    modifier = fModifier
                        .focusRequester(focusRequester),
                    shape = shape,
                    value = state.message,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTrailingIconColor = Color.Black,
                        cursorColor = Color.Black
                    ),
                    placeholder = { Text("enter message") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions {
                        viewModel.sendPrompt()
                        keyboardController?.hide()
                        focusRequester.freeFocus()
                    },
                    onValueChange = { viewModel.onMessageChange(it) },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                viewModel.sendPrompt()
                                keyboardController?.hide()
                                focusRequester.freeFocus()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = null
                            )
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyMedium
                )

//            BottomAppBar(
//                modifier = fModifier,
//                contentPadding = PaddingValues(horizontal = 0.dp),
//                windowInsets = WindowInsets(bottom = 16.dp, top = 0.dp),
//                containerColor = Color.Transparent
//            ) {
////                Column {
////
////                }
//                OutlinedTextField(
//                    shape = shape,
//                    modifier = fModifier,
//                    value = state.message,
//                    colors = customTextFieldColors(),
//                    placeholder = { Text("enter message") },
//                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
//                    keyboardActions = KeyboardActions {
//                        viewModel.sendPrompt()
//                        keyboardController?.hide()
//                    },
//                    onValueChange = { viewModel.onMessageChange(it) },
//                    trailingIcon = {
//                        IconButton(
//                            onClick = {
//                                viewModel.sendPrompt()
//                                keyboardController?.hide()
//                            }
//                        ) {
//                            Icon(imageVector = Icons.Default.Send, contentDescription = null)
//                        }
//                    },
//                    textStyle = MaterialTheme.typography.bodyMedium
//                )
//            }
//            Column(
//                modifier = fModifier
//                    .padding(bottom = 10.dp)
//                    .padding(horizontal = 10.dp)
//                    .clip(radius)
//                    .background(secondaryTertiary)
//            ) {
//                LazyColumn(
//                    modifier = Modifier
//                        .heightIn(max = screenHeightInDp * 0.3f),
//                ) {
//                    itemsIndexed(state.questions) { index, question ->
//                        QuestionSelectItem(
//                            modifier = fModifier,
//                            question = question
//                        ) {
//                            viewModel.onMessageChange(question)
//                            focusRequester.captureFocus()
//                            //TODO change cursor position after click to the end of the text
//                        }
////
////                            if (index != state.questions.size - 1) HorizontalDivider(color = tertiary)
//
//                        if (index == state.questions.size - 1) Spacer(modifier = Modifier.height(2.dp))
//                    }
//                }
//
            }
        }
    ) {
        Column(
            modifier = fModifier
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn(
                modifier = Modifier,
                state = listState,
            ) {
                itemsIndexed(state.messages) { index, msg ->
                    val height = 5.dp
                    val msgColor = if (msg.isBot) Color(0xff4A4458) else Color(0xff3B383E)

                    if (index == 0) Spacer(modifier = Modifier.height(10.dp))

                    ChatItemMessage(
                        modifier = fModifier,
                        message = msg,
                        index = index,
                        viewModel = viewModel,
                        color = msgColor
                    )

                    Spacer(modifier = Modifier.height(height))
                }

                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    ProgressText(modifier = fModifier, state = state.uiState)
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}

@Composable
fun ChatItemMessage(
    modifier: Modifier = Modifier,
    message: ChatMessage,
    color: Color,
    index: Int,
    viewModel: ChatViewModel
) {
    val maxWidth = (LocalConfiguration.current.screenWidthDp).dp

    val textModifier = Modifier
        .clip(RoundedCornerShape(30.dp))
        .widthIn(max = maxWidth * 0.75f)
        .background(color)
        .padding(15.dp, 10.dp)

    Box(
        modifier = modifier,
        contentAlignment = if (message.isBot) Alignment.CenterStart else Alignment.CenterEnd
    ) {
        if (message.isBot && index != 0) {
            val spanned = remember {
                viewModel.createSpannedText(message.data)
            }

            Text(
                modifier = textModifier,
                text = buildAnnotatedString {
                    appendSuspend(spanned = spanned)
                },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        } else {
            Text(
                modifier = textModifier,
                text = message.data,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

@Composable
fun QuestionSelectItem(
    modifier: Modifier = Modifier,
    question: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable {
                onClick()
            }
            .padding(horizontal = 10.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(10.dp),
            text = question,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ProgressText(
    modifier: Modifier = Modifier,
    state: UiState
) {
    when (state) {
        is UiState.Loading -> {
            Text(
                modifier = modifier,
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                text = "Loading..."
            )
        }

        is UiState.Error -> {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.ErrorOutline,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = secondary
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    color = secondary,
                    style = MaterialTheme.typography.bodySmall,
                    text = state.errorMessage ?: "There was some Error in response"
                )
            }
        }

        else -> {
            val time = when (state) {
                is UiState.Success -> state.timestamp
                else -> System.currentTimeMillis()
            }

            Text(
                modifier = modifier,
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                text = formatTimeStamp(time)
            )
        }
    }
}

@Preview
@Composable
private fun ChatPrev() {
//    LoadingItem()
}