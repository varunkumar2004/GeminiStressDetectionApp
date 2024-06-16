package com.varunkumar.geminiapi.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val generativeModel: GenerativeModel,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    // TODO implement savedStateHandle
    private val _state = MutableStateFlow(ChatState())

    private val _questions = mutableListOf(
        "Do I feel constantly on edge or overwhelmed?",
        "Have I been neglecting my sleep or healthy habits lately?",
        "Am I experiencing physical symptoms like headaches, stomachaches, or muscle tension?",
        "Have I become more irritable or withdrawn from social interactions?",
        "What are the biggest stressors in my life right now (work, finances, relationships, etc.)?",
        "Are there any recent changes or events that might be contributing to my stress levels?",
        "Do I have unrealistic expectations of myself or others?",
        "Do I feel like the stress is impacting my daily life or well-being significantly?",
        "Would it be helpful to talk to a therapist or counselor about stress management techniques?",
        "Are there any support groups available for people dealing with similar stressors?"
    )

    private val _message = MutableStateFlow("")

    val state = _state.flatMapLatest { state ->
        Log.d("ChatViewModel", "state: ${state.message}")
        _state.update {
            it.copy(
                questions = _questions.filter { items ->
                    items.contains(state.message)
                }
            )
        }
        _state
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ChatState())

    fun onMessageChange(newMessage: String) {
        _state.update { it.copy(message = newMessage) }
    }

    fun sendPrompt() {
        _state.update {
            it.copy(
                uiState = UiState.Loading
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        text(_message.value)
                    }
                )

                response.text?.let { outputContent ->
                    _state.update {
                        it.copy(uiState = UiState.Success(outputContent))
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(uiState = UiState.Error(e.localizedMessage ?: "There was Some Error"))
                }
            }
        }
    }
}

data class ChatState(
    val message: String = "",
    val questions: List<String> = emptyList(),
    val uiState: UiState = UiState.Initial
)