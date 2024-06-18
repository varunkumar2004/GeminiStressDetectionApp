package com.varunkumar.geminiapi.presentation.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(
        LoginState(
            name = savedStateHandle["name"] ?: "",
            email = savedStateHandle["email"] ?: "",
            password = savedStateHandle["password"] ?: "",
            loginMode = LoginMode.Register
        )
    )

    val state = _state

    fun onShowingPasswordChange() {
        _state.update { it.copy(showingPassword = !_state.value.showingPassword) }
    }

    fun onNameChange(newName: String) {
        _state.update { it.copy(name = newName) }
        savedStateHandle["name"] = newName
    }

    fun onEmailChange(newEmail: String) {
        _state.update { it.copy(email = newEmail) }
        savedStateHandle["email"] = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _state.update { it.copy(password = newPassword) }
        savedStateHandle["password"] = newPassword
    }

    fun onLoginModeChange() {
        when (_state.value.loginMode) {
            is LoginMode.Login -> {
                _state.update { it.copy(loginMode = LoginMode.Register) }
            }

            is LoginMode.Register -> {
                _state.update { it.copy(loginMode = LoginMode.Login) }
            }
        }
    }
}

data class LoginState(
    val name: String,
    val email: String,
    val password: String,
    val showingPassword: Boolean = false,
    val loginMode: LoginMode
)

sealed class LoginMode(val title: String, val messageForUser: String) {
    data object Login : LoginMode("Register.", "New User?")
    data object Register : LoginMode("Login.", "Already Have An Account?")
}