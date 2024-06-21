package com.varunkumar.geminiapi.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.varunkumar.geminiapi.presentation.screens.ChatScreen
import com.varunkumar.geminiapi.presentation.screens.HomeScreen
import com.varunkumar.geminiapi.presentation.screens.LoginScreen
import com.varunkumar.geminiapi.presentation.screens.DashboardScreen
import com.varunkumar.geminiapi.presentation.screens.SliderScreen
import com.varunkumar.geminiapi.presentation.viewModels.AppViewModel
import com.varunkumar.geminiapi.presentation.viewModels.ChatViewModel
import com.varunkumar.geminiapi.presentation.viewModels.LoginViewModel
import com.varunkumar.geminiapi.presentation.viewModels.StatsViewModel

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val loginViewModel = hiltViewModel<LoginViewModel>()
    val chatViewModel = hiltViewModel<ChatViewModel>()
    val statsViewModel = hiltViewModel<StatsViewModel>()
    val appViewModel = hiltViewModel<AppViewModel>()

    NavHost(navController = navController, startDestination = Routes.Login.route) {
        composable(Routes.Home.route) {
            HomeScreen(
                modifier = modifier,
                navController = navController,
                viewModel = statsViewModel,
                appViewModel = appViewModel
            )
        }

        composable(Routes.Stats.route) {
            SliderScreen(
                modifier = modifier,
                viewModel = statsViewModel,
                onSaveButtonClick = {},
                onCancelButtonClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(Routes.Login.route) {
            LoginScreen(
                modifier = modifier,
                paddingValues = 30.dp,
                viewModel = loginViewModel,
                onLoginButtonClick = {
                    navController.navigate(Routes.Home.route)
                }
            )
        }

        composable(Routes.Profile.route) {
            DashboardScreen(
                modifier = modifier,
                navController = navController,
                appViewModel = appViewModel
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