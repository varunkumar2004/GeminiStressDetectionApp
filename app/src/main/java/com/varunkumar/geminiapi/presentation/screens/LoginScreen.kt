package com.varunkumar.geminiapi.presentation.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.varunkumar.geminiapi.R
import com.varunkumar.geminiapi.presentation.viewModels.LoginViewModel
import com.varunkumar.geminiapi.presentation.viewModels.ScreenMode

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    onLoginButtonClick: () -> Unit
) {
    val textFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedIndicatorColor = Color(0xFF527DAF),
        unfocusedIndicatorColor = Color.Gray,
        focusedTrailingIconColor = Color(0xFF527DAF),
        unfocusedTrailingIconColor = Color.Transparent,
        cursorColor = Color.Black
    )

    val state = viewModel.state.collectAsState().value
    val shape = RoundedCornerShape(10.dp)
    val fModifier = Modifier.fillMaxWidth()

    val isRegister = when (state.mode) {
        is ScreenMode.Register -> true
        else -> false
    }

    val bgColor = Color(0xffE4EBFB)

    Scaffold(
        containerColor = bgColor,
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent
            ) {
                AnimatedContent(
                    targetState = state.mode,
                    label = "",
                ) { mode ->
                    TextButton(
                        modifier = fModifier,
                        onClick = viewModel::onLoginModeChange
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = mode.messageForUser,
                                color = Color.Black,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = mode.highLight,
                                color = Color(0xFF527DAF),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.squiggle),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Enter Details",
                    textAlign = TextAlign.Left,
                    modifier = fModifier,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    shape = shape,
                    modifier = fModifier,
                    value = state.email,
                    colors = textFieldColors,
                    onValueChange = { viewModel.onEmailChange(it) },
                    trailingIcon = {
                        if (state.email.isNotEmpty() && state.email.isNotBlank()) {
                            IconButton(onClick = { viewModel.onEmailChange("") }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = null)
                            }
                        }
                    },
                    placeholder = { Text(text = "email") },
                    singleLine = true,
                    maxLines = 1
                )

                AnimatedVisibility(
                    visible = isRegister
                ) {
                    OutlinedTextField(
                        shape = shape,
                        modifier = fModifier,
                        value = state.name,
                        colors = textFieldColors,
                        onValueChange = { viewModel.onNameChange(it) },
                        trailingIcon = {
                            if (state.name.isNotEmpty() && state.name.isNotBlank()) {
                                IconButton(onClick = { viewModel.onNameChange("") }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = null
                                    )
                                }
                            }
                        },
                        placeholder = { Text(text = "name") },
                        singleLine = true,
                        maxLines = 1
                    )
                }

                OutlinedTextField(
                    shape = shape,
                    modifier = fModifier,
                    value = state.password,
                    colors = textFieldColors,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    placeholder = { Text(text = "password") },
                    trailingIcon = {
                        IconButton(onClick = viewModel::onShowingPasswordChange) {
                            Icon(
                                imageVector = if (!state.showingPassword) Icons.Filled.Lock else Icons.Filled.LockOpen,
                                contentDescription = "Password"
                            )
                        }
                    },
                    visualTransformation = if (state.showingPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedButton(
                modifier = fModifier
                    .height(TextFieldDefaults.MinHeight),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Black,
                ),
                onClick = { onLoginButtonClick() }
            ) {
                Text(
                    text = state.mode.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginPre() {
//    LoginScreen(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(20.dp),
//        corner = 40.dp
//    )
}