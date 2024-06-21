package com.varunkumar.geminiapi.presentation.screens

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.varunkumar.geminiapi.presentation.Routes
import com.varunkumar.geminiapi.presentation.viewModels.AppViewModel
import com.varunkumar.geminiapi.ui.theme.customNavigationBarItemColors

@Composable
fun CustomBottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    appViewModel: AppViewModel
) {
    val selectedRoute by appViewModel.selectedItem.collectAsState()
    val colors = customNavigationBarItemColors()

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.secondary,
        tonalElevation = 10.dp
    ) {
        NavigationBarItem(
            selected = selectedRoute == Routes.Home,
            colors = colors,
            onClick = {
                if (selectedRoute != Routes.Home) {
                    appViewModel.onChangeRoute(Routes.Home)
                    navController.navigate(Routes.Home.route)
                }
            },
            label = {
                if (selectedRoute == Routes.Home)
                    Text(text = selectedRoute.title)
            },
            icon = {
                Icon(
                    imageVector = Routes.Home.icon,
                    contentDescription = Routes.Home.title
                )
            }
        )
        NavigationBarItem(
            selected = selectedRoute == Routes.Chat,
            onClick = {
                if (selectedRoute != Routes.Chat) {
                    navController.navigate(Routes.Chat.route)
                }
            },
            colors = colors,
            label = {
                if (selectedRoute == Routes.Chat)
                    Text(text = selectedRoute.title)
            },
            icon = {
                Icon(
                    imageVector = Routes.Chat.icon,
                    contentDescription = Routes.Chat.title
                )
            }
        )
        NavigationBarItem(
            selected = selectedRoute == Routes.Profile,
            onClick = {
                if (selectedRoute != Routes.Profile) {
                    appViewModel.onChangeRoute(Routes.Profile)
                    navController.navigate(Routes.Profile.route)
                }
            },
            label = {
                if (selectedRoute == Routes.Profile)
                    Text(text = selectedRoute.title)
            },
            colors = colors,
            icon = {
                Icon(
                    imageVector = Routes.Profile.icon,
                    contentDescription = Routes.Profile.title
                )
            }
        )
    }
}