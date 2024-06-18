package com.varunkumar.geminiapi.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.varunkumar.geminiapi.R
import com.varunkumar.geminiapi.model.ChatMessage
import com.varunkumar.geminiapi.presentation.UiState
import com.varunkumar.geminiapi.presentation.viewModels.ChatState
import com.varunkumar.geminiapi.presentation.viewModels.ChatViewModel
import com.varunkumar.geminiapi.ui.theme.primary
import com.varunkumar.geminiapi.ui.theme.primarySecondary
import com.varunkumar.geminiapi.ui.theme.secondary
import com.varunkumar.geminiapi.ui.theme.secondaryTertiary
import com.varunkumar.geminiapi.ui.theme.tertiary
import com.varunkumar.geminiapi.utils.formatTimeStamp
import com.varunkumar.geminiapi.utils.getScreenResolutionDp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel,
    onBackButtonClick: () -> Unit
) {
    val listState = rememberLazyListState()
    val scrollState = rememberScrollState()
    val state = viewModel.state.collectAsState(ChatState()).value
    val radius = RoundedCornerShape(20.dp)
    val focusRequester = remember { FocusRequester() }
    val (_, screenHeightInDp) = getScreenResolutionDp()
    val fModifier = Modifier.fillMaxWidth()

    LaunchedEffect(state) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.size - 1)
        }
    }

    Scaffold(
        containerColor = tertiary,
        modifier = modifier,
        topBar = {
            Column {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = primary,
                        navigationIconContentColor = primary
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

                HorizontalDivider(color = secondaryTertiary)
            }
        },
        bottomBar = {
            Column(
                modifier = fModifier
                    .padding(bottom = 10.dp)
                    .padding(horizontal = 10.dp)
                    .clip(radius)
                    .background(secondaryTertiary)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .heightIn(max = screenHeightInDp * 0.3f),
                ) {
                    itemsIndexed(state.questions) { index, question ->
                        QuestionSelectItem(
                            modifier = fModifier,
                            question = question
                        ) {
                            viewModel.onMessageChange(question)
                            focusRequester.captureFocus()
                            //TODO change cursor position after click to the end of the text
                        }
//
//                            if (index != state.questions.size - 1) HorizontalDivider(color = tertiary)

                        if (index == state.questions.size - 1) Spacer(modifier = Modifier.height(2.dp))
                    }
                }

                TextField(
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = primary,
                    ),
                    modifier = fModifier
                        .focusRequester(focusRequester),
                    shape = radius,
                    value = state.message,
                    onValueChange = { viewModel.onMessageChange(it) },
                    placeholder = { Text("Message") },
                    trailingIcon = {
                        IconButton(onClick = viewModel::sendPrompt) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = null)
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyMedium
                )
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
                val messages = state.messages
                itemsIndexed(messages) { index, msg ->
                    if (index == 0) Spacer(modifier = Modifier.height(5.dp))
                    val isPrevSame =
                        index != 0 && messages[index - 1].isBot && messages[index].isBot
                    val height = 2.dp

                    AnimatedVisibility(
                        visible = true, // Item is always visible, but we use this for animation
                        enter = slideInHorizontally(
                            animationSpec = tween(durationMillis = 500),
                        ) + fadeIn(
                            animationSpec = tween(durationMillis = 500)
                        )
                    ) {
                        ChatItemMessage(modifier = fModifier, message = msg)
                    }

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
    message: ChatMessage
) {
    val bg = if (message.isBot) primarySecondary else primary
    val maxWidth = (LocalConfiguration.current.screenWidthDp).dp

    Box(
        modifier = modifier,
        contentAlignment = if (message.isBot) Alignment.CenterStart else Alignment.CenterEnd
    ) {
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .widthIn(max = maxWidth * 0.75f)
                .background(bg)
                .padding(15.dp, 10.dp),
            text = message.data,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
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
        is UiState.Success -> {
            Text(
                modifier = modifier,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                text = formatTimeStamp(state.timestamp)
            )
        }

        is UiState.Initial -> {
            Text(
                modifier = modifier,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                text = formatTimeStamp(System.currentTimeMillis())
            )
        }

        is UiState.Loading -> {
            Text(
                modifier = modifier,
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
    }
}

@Preview
@Composable
private fun ChatPrev() {
//    LoadingItem()
}