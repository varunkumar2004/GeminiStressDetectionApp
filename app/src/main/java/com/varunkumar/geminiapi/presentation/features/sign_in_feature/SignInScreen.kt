package com.varunkumar.geminiapi.presentation.features.sign_in_feature

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.varunkumar.geminiapi.R

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    state: SignInState,
    onSignInClick: () -> Unit,
) {
    val context = LocalContext.current
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(state.signInError) {
        state.signInError?.let {error ->
            snackBarHostState.showSnackbar(message = error, duration = SnackbarDuration.Short)
        }
    }

    Scaffold(
        snackbarHost = {
            snackBarHostState.currentSnackbarData?.let { Snackbar(snackbarData = it) }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent,
                contentColor = Color.Black,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                ElevatedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(TextFieldDefaults.MinHeight),
                    onClick = onSignInClick
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "Sign In", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    ) {
        Column(
            modifier = modifier
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.ic_launcher), contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInPrev() {
    SignInScreen(
        modifier = Modifier.fillMaxSize(),
        state = SignInState()
    ) {

    }
}