package com.varunkumar.geminiapi.presentation.features.chat_feature

import com.varunkumar.geminiapi.model.ChatMessage
import com.varunkumar.geminiapi.presentation.UiState

data class ChatState(
    val message: String = "",
    val uiState: UiState = UiState.Initial,
    val isSpeaking: Boolean = false,
    val speakText: ChatMessage? = null,
    val messages: List<ChatMessage> = listOf(
        ChatMessage(data = "Hello! How can i help you?", isBot = true)
    )
)