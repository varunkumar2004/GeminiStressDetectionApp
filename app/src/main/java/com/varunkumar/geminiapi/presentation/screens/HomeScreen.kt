package com.varunkumar.geminiapi.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.varunkumar.geminiapi.presentation.Routes
import com.varunkumar.geminiapi.presentation.viewModels.StatsViewModel
import com.varunkumar.geminiapi.ui.theme.secondary
import com.varunkumar.geminiapi.ui.theme.tertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: StatsViewModel
) {
    Scaffold(
        containerColor = tertiary
    ) {
        Box(
            modifier = modifier
                .padding(it),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate(Routes.Stats.route)
                    }
                    .background(secondary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Start")
            }
        }
    }
}

@Preview
@Composable
private fun PreHome() {
//    HomeScreen(navController = rememberNavController())
}