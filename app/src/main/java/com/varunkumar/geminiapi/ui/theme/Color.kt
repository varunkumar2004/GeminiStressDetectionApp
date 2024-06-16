package com.varunkumar.geminiapi.ui.theme

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


//custom colors for the app
val primary = Color(0xff361d32)
val primarySecondary = Color(0xff543c52)
val secondary = Color(0xfff55951)
val secondaryTertiary = Color(0xffedd2cb)
val tertiary = Color(0xfff1e8e6)

@Composable
fun customTextFieldColors(): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedContainerColor = secondaryTertiary,
        unfocusedContainerColor = secondaryTertiary,
        unfocusedIndicatorColor = tertiary,
        focusedIndicatorColor = primary,
        focusedLabelColor = primary,
        focusedTrailingIconColor = primary,
        unfocusedTrailingIconColor = secondaryTertiary,
        cursorColor = primarySecondary
    )
}

@Composable
fun customButtonColors(): ButtonColors {
    return ButtonDefaults.buttonColors(
        containerColor = secondary,
        contentColor = tertiary
    )
}