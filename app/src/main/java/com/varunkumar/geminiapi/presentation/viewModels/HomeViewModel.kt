package com.varunkumar.geminiapi.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.varunkumar.geminiapi.presentation.Routes
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(HomeState(Routes.Home))
    val state = _state
}

data class HomeState(
    val selectedRoute: Routes
)