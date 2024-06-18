package com.varunkumar.geminiapi.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.varunkumar.geminiapi.presentation.Routes
import com.varunkumar.geminiapi.presentation.viewModels.HomeViewModel
import com.varunkumar.geminiapi.presentation.viewModels.StatsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: StatsViewModel
) {
    Scaffold {
        it
        Button(onClick = { navController.navigate(Routes.Stats.route) }) {
            Text("Start")
        }
    }




}

@Preview
@Composable
private fun PreHome() {
//    HomeScreen(navController = rememberNavController())
}