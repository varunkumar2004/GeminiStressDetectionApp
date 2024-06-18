package com.varunkumar.geminiapi.presentation.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.varunkumar.geminiapi.R
import com.varunkumar.geminiapi.presentation.viewModels.LoginMode
import com.varunkumar.geminiapi.presentation.viewModels.LoginViewModel
import com.varunkumar.geminiapi.ui.theme.customButtonColors
import com.varunkumar.geminiapi.ui.theme.customTextFieldColors
import com.varunkumar.geminiapi.ui.theme.primarySecondary
import com.varunkumar.geminiapi.ui.theme.secondary
import com.varunkumar.geminiapi.ui.theme.tertiary

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    paddingValues: Dp = 20.dp,
    corner: Dp = 10.dp,
    viewModel: LoginViewModel,
    onLoginButtonClick: () -> Unit
) {
    val colors = customTextFieldColors()
    val state = viewModel.state.collectAsState().value
    val shape = RoundedCornerShape(corner)
    val fModifier = Modifier.fillMaxWidth()
    val isLogin = when (state.loginMode) {
        is LoginMode.Login -> true
        else -> false
    }

    Box(
        modifier = modifier
            .background(tertiary)
            .padding(paddingValues),
        contentAlignment = Alignment.BottomStart
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.squiggle),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Enter Details",
                    textAlign = TextAlign.Left,
                    modifier = fModifier,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    shape = shape,
                    modifier = fModifier,
                    value = state.email,
                    colors = colors,
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
                    visible = isLogin
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(5.dp))

                        OutlinedTextField(
                            shape = shape,
                            modifier = fModifier,
                            value = state.name,
                            colors = colors,
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

                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }

                if (!isLogin) Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    shape = shape,
                    modifier = fModifier,
                    value = state.password,
                    colors = colors,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    placeholder = { Text(text = "password") },
                    trailingIcon = {
                        IconButton(onClick = viewModel::onShowingPasswordChange) {
                            Icon(
                                imageVector = if (state.showingPassword) Icons.Filled.Lock else Icons.Filled.LockOpen,
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

            Button(
                colors = customButtonColors(),
                modifier = fModifier
                    .height(TextFieldDefaults.MinHeight),
                onClick = { onLoginButtonClick() }
            ) {
                Text(
                    text = state.loginMode.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }


//            var text by animateValueAsState(
//                targetValue = state.name,
//                animationSpec = { tween(durationMillis = 500, easing = LinearEasing) }
//            )

        AnimatedContent(
            targetState = state.loginMode,
            label = "",
        ) { mode ->
            Row(
                modifier = fModifier
                    .clickable {
                        viewModel.onLoginModeChange()
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = mode.messageForUser,
                    color = primarySecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = mode.title,
                    color = secondary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
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