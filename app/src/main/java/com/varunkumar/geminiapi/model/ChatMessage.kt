package com.varunkumar.geminiapi.model

data class ChatMessage (
    val data: String,
    val isBot: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)