package com.varunkumar.geminiapi.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Metric (
    val icon: ImageVector,
    val name: String,
    val value: Float,
    val unit: String
)