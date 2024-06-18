package com.varunkumar.geminiapi.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Routes(val route: String, val title: String, val icon: ImageVector) {
    data object Login : Routes("login", "Login", Icons.Default.Login)
    data object Home : Routes("home", "Home", Icons.Default.Home)
    data object Chat : Routes("chat", "Chat", Icons.Default.Chat)
    data object Stats : Routes("stats", "Stats", Icons.Default.QueryStats)
    data object Profile : Routes("profile", "Profile", Icons.Default.Chat)
}