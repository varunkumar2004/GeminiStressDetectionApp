package com.varunkumar.geminiapi.presentation.features.sign_in_feature

data class SignInState (
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)