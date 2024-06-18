package com.varunkumar.geminiapi.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.LineWeight
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.NightlightRound
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedIconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.varunkumar.geminiapi.R
import com.varunkumar.geminiapi.model.Metric
import com.varunkumar.geminiapi.presentation.Routes
import com.varunkumar.geminiapi.ui.theme.primary
import com.varunkumar.geminiapi.ui.theme.primarySecondary
import com.varunkumar.geminiapi.ui.theme.secondary
import com.varunkumar.geminiapi.ui.theme.tertiary
import com.varunkumar.geminiapi.utils.generateRandomColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val selectedItem by remember { mutableStateOf<Routes>(Routes.Home) }
    val fModifier = Modifier.fillMaxWidth()

    val metrics = listOf(
        Metric(Icons.Outlined.DirectionsRun, "Steps", 10000f, "Steps"),
        Metric(Icons.Outlined.MonitorHeart, "Heart Rate", 100f, "BPM"),
        Metric(Icons.Outlined.NightlightRound, "Sleep", 10f, "Hrs"),
        Metric(Icons.Outlined.LocalFireDepartment, "Calories", 10000f, "Cal"),
        Metric(Icons.Outlined.LineWeight, "BMI", 23f, ""),
    )

    Scaffold(
        modifier = modifier,
        containerColor = tertiary,
        topBar = {
            TopAppBar(
                title = { Text(text = "Summary", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = primary
                )
            )
        },
        bottomBar = {
            Column {
                HorizontalDivider()
                BottomNavigation(navController = navController, selectedItem = selectedItem)
            }
        }
    ) {
        Column(
            modifier = fModifier
                .padding(it)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = fModifier,
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
                            color = primarySecondary
                        )

                        Text(
                            text = "Varun Kumar",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = primary
                        )
                    }
                }

                IconButton(onClick = { navController.navigate(Routes.Login.route) }) {
                    Icon(imageVector = Icons.Default.Logout, contentDescription = null)
                }
            }

            HorizontalDivider()

            Column {
                ProfileCardRow(modifier = fModifier, key = "Sex", value = "Male")
                ProfileCardRow(modifier = fModifier, key = "DOB", value = "July 11, 2004")
                ProfileCardRow(modifier = fModifier, key = "Weight", value = "80 kg")
                ProfileCardRow(modifier = fModifier, key = "Age", value = "20 Yrs")
                ProfileCardRow(modifier = fModifier, key = "Height", value = "183 cm")
            }

            HorizontalDivider()

            LazyColumn {
                itemsIndexed(metrics) { index, metric ->
                    val randomColor = generateRandomColor()

                    MetricCard(
                        modifier = fModifier,
                        icon = metric.icon,
                        name = metric.name,
                        color = randomColor,
                        unit = metric.unit,
                        value = metric.value.toString(),
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Text(
                modifier = modifier,
                color = primary,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                text = "All Data is Stored Locally."
            )
        }
    }
}

@Composable
fun MetricCard(
    modifier: Modifier = Modifier,
    color: Color,
    icon: ImageVector,
    name: String,
    unit: String,
    value: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                tint = primary,
                imageVector = icon,
                contentDescription = name
            )
        }

        Column {
            Text(name)

            Text(
                text = "$value $unit",
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
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
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = key, style = MaterialTheme.typography.titleSmall)
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    selectedItem: Routes
) {
    val selectedColor: (route: Routes) -> Color = { route ->
        if (route == selectedItem) {
            primary
        } else {
            primarySecondary
        }
    }

    NavigationBar(
        modifier = modifier,
        containerColor = tertiary,
        contentColor = primary
    ) {
        NavigationBarItem(
            selected = selectedItem == Routes.Home,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = secondary,
                unselectedIconColor = primarySecondary,
                indicatorColor = Color.Transparent
            ),
            label = {
                Text(
                    text = Routes.Home.title,
                    color = if (selectedItem == Routes.Home) secondary else primarySecondary
                )
            },
            onClick = { navController.navigate(Routes.Home.route) },
            icon = {
                Icon(
                    imageVector = Routes.Home.icon,
                    contentDescription = Routes.Home.title
                )
            }
        )
        NavigationBarItem(
            selected = selectedItem == Routes.Stats,
            onClick = { navController.navigate(Routes.Stats.route) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = secondary,
                unselectedIconColor = primarySecondary,
                indicatorColor = Color.Transparent
            ),
            label = {
                Text(
                    text = Routes.Stats.title,
                    color = if (selectedItem == Routes.Stats) secondary else primarySecondary
                )
            },
            icon = {
                Icon(
                    imageVector = Routes.Stats.icon,
                    contentDescription = Routes.Stats.title
                )
            }
        )
        NavigationBarItem(
            selected = selectedItem == Routes.Chat,
            onClick = { navController.navigate(Routes.Chat.route) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = secondary,
                unselectedIconColor = primarySecondary,
                indicatorColor = Color.Transparent
            ),
            label = {
                Text(
                    text = Routes.Chat.title,
                    color = if (selectedItem == Routes.Chat) secondary else primarySecondary
                )
            },
            icon = {
                Icon(
                    imageVector = Routes.Chat.icon,
                    contentDescription = Routes.Chat.title
                )
            }
        )
    }
}

@Preview
@Composable
private fun ProfilePre() {
    ProfileScreen(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        navController = rememberNavController()
    )
}