package com.varunkumar.geminiapi.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.LineWeight
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.NightlightRound
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.varunkumar.geminiapi.R
import com.varunkumar.geminiapi.model.Metric
import com.varunkumar.geminiapi.presentation.Routes
import com.varunkumar.geminiapi.presentation.viewModels.AppViewModel
import com.varunkumar.geminiapi.ui.theme.GeminiApiTheme
import com.varunkumar.geminiapi.ui.theme.customTopAppBar
import com.varunkumar.geminiapi.utils.generateRandomColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    appViewModel: AppViewModel
) {
    val fModifier = Modifier.fillMaxWidth()

    val metrics = listOf(
        Metric(Icons.Outlined.DirectionsRun, "Steps", 10000f, "Steps"),
        Metric(Icons.Outlined.MonitorHeart, "Heart Rate", 100f, "BPM"),
        Metric(Icons.Outlined.NightlightRound, "Sleep", 10f, "Hrs"),
        Metric(Icons.Outlined.LocalFireDepartment, "Calories", 10000f, "Cal"),
        Metric(Icons.Outlined.LineWeight, "BMI", 23f, ""),
    )

    GeminiApiTheme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.primary,
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = "Summary", fontWeight = FontWeight.Bold) },
                    colors = customTopAppBar()
                )
            },
            bottomBar = {
                CustomBottomNavigation(
                    navController = navController,
                    appViewModel = appViewModel
                )
            }
        ) {
            Column(
                modifier = modifier
                    .padding(it)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ProfileHeader(modifier = fModifier) {
                    navController.navigate(Routes.Login.route)
                }

                ProfileStats(modifier = fModifier, metrics = metrics)
            }
        }
    }
}

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    onLoginIconClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(40.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(80.dp),
                    painter = painterResource(id = R.drawable.squiggle),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(
                        text = "Good Morning!",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )

                    Text(
                        text = "Varun Kumar",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }

            IconButton(onClick = { onLoginIconClick() }) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    tint = MaterialTheme.colorScheme.surface,
                    contentDescription = null
                )
            }
        }

        HorizontalDivider()

        Column {
            ProfileCardRow(
                modifier = modifier,
                key = "Sex",
                value = "Male"
            )
            ProfileCardRow(
                modifier = modifier,
                key = "Age",
                value = "20 Years"
            )
            ProfileCardRow(
                modifier = modifier,
                key = "Weight",
                value = "80 Kg"
            )
            ProfileCardRow(
                modifier = modifier,
                key = "Height",
                value = "173 cm"
            )
        }
    }
}

@Composable
fun ProfileStats(
    modifier: Modifier = Modifier,
    metrics: List<Metric>
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 10.dp,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        itemsIndexed(metrics) { index, metric ->
            val randomColor = generateRandomColor()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(30.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = metric.icon, contentDescription = null, tint = randomColor)
                    Text(
                        text = metric.name,
                        style = MaterialTheme.typography.titleSmall,
                        color = randomColor,
                    )
                }

                Text(
                    text = "${metric.value} ${metric.unit}",
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun ProfileCardRow(
    modifier: Modifier = Modifier,
    key: String,
    value: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifier.weight(0.3f),
            text = key,
            color = MaterialTheme.colorScheme.surface,
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            modifier = modifier.weight(0.7f),
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.surface,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun ProfilePre() {
//    DashboardScreen(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        navController = rememberNavController()
//    )
}