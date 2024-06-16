package com.varunkumar.geminiapi.presentation

sealed class Routes(val route: String, val title: String) {
    data object Login : Routes("login", "Login")
    data object Home : Routes("home", "Home")
    data object Chat : Routes("chat", "Chat")
    data object Stats : Routes("stats", "Stats")
}