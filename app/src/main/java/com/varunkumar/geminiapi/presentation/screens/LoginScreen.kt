package com.varunkumar.geminiapi.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.varunkumar.geminiapi.presentation.viewModels.LoginViewModel
import com.varunkumar.geminiapi.ui.theme.customButtonColors
import com.varunkumar.geminiapi.ui.theme.customTextFieldColors
import com.varunkumar.geminiapi.ui.theme.primarySecondary
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
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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
                    colors = colors,
                    onValueChange = { viewModel.onEmailChange(it) },
                    trailingIcon = {
                        if (state.email.isNotEmpty() && state.email.isNotBlank()) {
                            IconButton(onClick = { viewModel.onEmailChange("") }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = null)
                            }
                        }
                    },
                    label = { Text(text = "email") },
                    singleLine = true,
                    maxLines = 1
                )
                OutlinedTextField(
                    shape = shape,
                    modifier = fModifier,
                    value = state.name,
                    colors = colors,
                    onValueChange = { viewModel.onNameChange(it) },
                    trailingIcon = {
                        if (state.name.isNotEmpty() && state.name.isNotBlank()) {
                            IconButton(onClick = { viewModel.onNameChange("") }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = null)
                            }
                        }
                    },
                    label = { Text(text = "name") },
                    singleLine = true,
                    maxLines = 1
                )
                OutlinedTextField(
                    shape = shape,
                    modifier = fModifier,
                    value = state.password,
                    colors = colors,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = { Text(text = "password") },
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


        Text(
            modifier = fModifier
                .clickable {
                    viewModel.onLoginModeChange()
                },
            textAlign = TextAlign.Center,
            text = state.loginMode.messageForUser,
            color = primarySecondary
        )
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