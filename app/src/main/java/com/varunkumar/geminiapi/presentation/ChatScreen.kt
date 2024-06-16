package com.varunkumar.geminiapi.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.varunkumar.geminiapi.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = viewModel()
) {
    val state = viewModel.state.collectAsState(ChatState()).value
    val radius = RoundedCornerShape(20.dp)
    val (_, screenHeightDp) = getScreenResolutionDp()
    val focusRequester = remember { FocusRequester() }

    Scaffold(
        modifier = modifier,
        topBar = {
            Column {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
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

                HorizontalDivider()
            }

        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(radius)
                    .background(Color(0xfff5f5f5))
            ) {
                LazyColumn(
                    modifier = Modifier
                        .heightIn(max = screenHeightDp.times(0.3f))
                        .padding(horizontal = 10.dp)
                ) {
                    itemsIndexed(state.questions) { index, question ->
                        val topPaddingValue = if (index == 0) 10.dp else 2.dp
                        QuestionSelectItem(
                            question = question,
                            padding = topPaddingValue
                        ) {
                            viewModel.onMessageChange(question)
                            focusRequester.captureFocus()
                            //TODO change cursor position after click to the end of the text
                        }
                        if (index == state.questions.size - 1) {
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                }

                TextField(
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    shape = radius,
                    value = state.message,
                    onValueChange = { viewModel.onMessageChange(it) },
                    placeholder = { Text("Type here") },
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
            modifier = modifier
                .padding(it)
        ) {
            Box(
                modifier = Modifier
                    .weight(0.6f)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                when (state.uiState) {
                    is UiState.Success -> {
                        Text(
                            text = state.uiState.outputText,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    is UiState.Error -> {
                        Text(
                            text = state.uiState.errorMessage,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    is UiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }

                    }

                    else -> {}
                }
            }
        }
    }
}

@Composable
fun QuestionSelectItem(
    modifier: Modifier = Modifier,
    question: String,
    padding: Dp = 2.dp,
    onClick: () -> Unit
) {
    val radius = RoundedCornerShape(30.dp)

    Box(
        modifier = modifier
            .clickable {
                onClick()
            }
            .padding(top = padding)
            .border(1.dp, Color.LightGray, radius)
            .clip(radius)
            .padding(10.dp)
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = question,
                style = MaterialTheme.typography.bodySmall
            )
//            Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.DarkGray)
        }
    }
}

@Composable
fun getScreenResolutionDp(): Pair<Dp, Dp> {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp
    return Pair(
        screenWidthDp,
        screenHeightDp
    )
}

@Preview
@Composable
private fun ChatPrev() {
    ChatScreen(
        modifier = Modifier
            .fillMaxSize()
    )
}