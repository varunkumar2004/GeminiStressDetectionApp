package com.varunkumar.geminiapi.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = darkPrimary,
    secondary = darkSecondary,
    tertiary = darkTertiary,
    surface = darkSurface
)

private val LightColorScheme = lightColorScheme(
    primary = lightPrimary,
    secondary = lightSecondary,
    tertiary = lightTertiary,
    surface = lightSurface
)

@Composable
fun GeminiApiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun customTopAppBar(modifier: Modifier = Modifier): TopAppBarColors {
    return TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.secondary,
        titleContentColor = MaterialTheme.colorScheme.surface,
        navigationIconContentColor = MaterialTheme.colorScheme.surface
    )
}

@Composable
fun customNavigationBarItemColors(modifier: Modifier = Modifier): NavigationBarItemColors {
    return NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.surface,
        unselectedIconColor = MaterialTheme.colorScheme.tertiary,
        indicatorColor = Color.Transparent
    )
}

@Composable
fun customTextFieldColors(modifier: Modifier = Modifier): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.secondary,
        unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
        focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
        unfocusedIndicatorColor = Color.Transparent,
        cursorColor = MaterialTheme.colorScheme.tertiary,
        focusedTrailingIconColor = MaterialTheme.colorScheme.tertiary,
        focusedTextColor = MaterialTheme.colorScheme.surface
    )
}