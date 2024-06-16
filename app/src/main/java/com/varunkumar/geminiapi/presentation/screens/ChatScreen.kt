package com.varunkumar.geminiapi.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.varunkumar.geminiapi.R
import com.varunkumar.geminiapi.model.ChatMessage
import com.varunkumar.geminiapi.presentation.viewModels.ChatState
import com.varunkumar.geminiapi.presentation.viewModels.ChatViewModel
import com.varunkumar.geminiapi.ui.theme.customTextFieldColors
import com.varunkumar.geminiapi.ui.theme.primary
import com.varunkumar.geminiapi.ui.theme.primarySecondary
import com.varunkumar.geminiapi.ui.theme.secondaryTertiary
import com.varunkumar.geminiapi.ui.theme.tertiary
import com.varunkumar.geminiapi.utils.getScreenResolutionDp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel,
    onBackButtonClick: () -> Unit
) {
    val listState = rememberLazyListState()
    val state = viewModel.state.collectAsState(ChatState()).value
    val radius = RoundedCornerShape(20.dp)
    val (_, screenHeightDp) = getScreenResolutionDp()
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(state) {
        listState.animateScrollToItem(state.messages.size - 1)
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
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    state = listState,
                ) {
                    val messages = state.messages
                    itemsIndexed(messages) { index, msg ->
                        val isPrevSame =
                            index != 0 && messages[index - 1].isBot && messages[index].isBot
                        val height = 2.dp
                        Spacer(modifier = Modifier.height(height))
                        ChatItemMessage(modifier = Modifier.fillMaxWidth(), message = msg)
                    }
                }



//                when (state.uiState) {
//                    is UiState.Success -> {
//                        Text(
//                            text = state.uiState.outputText,
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                    }
//
//                    is UiState.Error -> {
//                        Text(
//                            text = state.uiState.errorMessage,
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                    }
//
//                    is UiState.Loading -> {
//                        Box(
//                            modifier = Modifier.fillMaxSize(),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            CircularProgressIndicator()
//                        }
//
//                    }
//
//                    else -> {}
//                }
            }

            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxWidth()
                    .clip(radius)
                    .background(secondaryTertiary),
            ) {
                LazyColumn {
                    itemsIndexed(state.questions) { index, question ->
                        Spacer(modifier = Modifier.height(2.dp))

                        QuestionSelectItem(
                            question = question
                        ) {
                            viewModel.onMessageChange(question)
                            focusRequester.captureFocus()
                            //TODO change cursor position after click to the end of the text
                        }

                        if (index == state.questions.size - 1) {
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }
                }

                OutlinedTextField(
                    colors = customTextFieldColors(),
                    modifier = Modifier
                        .fillMaxWidth()
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
                    textStyle = MaterialTheme.typography.bodySmall
                )
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
                .clip(RoundedCornerShape(20.dp))
                .widthIn(max = maxWidth * 0.75f)
                .background(bg)
                .padding(vertical = 10.dp, horizontal = 10.dp),
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
    padding: Dp = 2.dp,
    onClick: () -> Unit
) {
    val radius = RoundedCornerShape(20.dp)

    Column(
        modifier = modifier
            .clickable {
                onClick()
            }
            .padding(top = padding)
            .padding(horizontal = 10.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(10.dp),
            text = question,
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Preview
@Composable
private fun ChatPrev() {
//    ChatScreen(
//        modifier = Modifier
//            .fillMaxSize()
//    )
}