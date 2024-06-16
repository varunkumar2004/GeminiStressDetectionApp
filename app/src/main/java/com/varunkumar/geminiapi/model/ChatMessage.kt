package com.varunkumar.geminiapi.model

import java.lang.Error

data class ChatMessage (
    val data: String,
    val isBot: Boolean,
    val isError: Boolean = false
)