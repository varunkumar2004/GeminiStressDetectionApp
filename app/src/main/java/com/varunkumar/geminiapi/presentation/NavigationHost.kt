package com.varunkumar.geminiapi.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.varunkumar.geminiapi.presentation.screens.ChatScreen
import com.varunkumar.geminiapi.presentation.screens.LoginScreen
import com.varunkumar.geminiapi.presentation.viewModels.ChatViewModel
import com.varunkumar.geminiapi.presentation.viewModels.LoginViewModel

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val loginViewModel = hiltViewModel<LoginViewModel>()
    val chatViewModel = hiltViewModel<ChatViewModel>()

    NavHost(navController = navController, startDestination = Routes.Chat.route) {
        composable(Routes.Login.route) {
            LoginScreen(
                modifier = modifier,
                paddingValues = 30.dp,
                viewModel = loginViewModel,
                onLoginButtonClick = {
                    navController.navigate(Routes.Chat.route)
                }
            )
        }

        composable(Routes.Chat.route) {
            ChatScreen(
                modifier = modifier,
                viewModel = chatViewModel,
                onBackButtonClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}