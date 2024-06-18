package com.varunkumar.geminiapi.presentation

sealed interface UiState {
    data object Initial : UiState
    data object Loading : UiState
    data class Success(val outputText: String, val timestamp: Long) : UiState
    data class Error(val errorMessage: String?) : UiState
}