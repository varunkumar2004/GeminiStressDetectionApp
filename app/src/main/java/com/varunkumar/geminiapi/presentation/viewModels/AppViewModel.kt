package com.varunkumar.geminiapi.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.varunkumar.geminiapi.presentation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(): ViewModel() {
    private val _selectedRoute = MutableStateFlow<Routes>(Routes.Home)
    val selectedItem = _selectedRoute.asStateFlow()

    fun onChangeRoute(newRoute: Routes) {
        _selectedRoute.value = newRoute
    }
}