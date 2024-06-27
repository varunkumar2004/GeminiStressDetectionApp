package com.varunkumar.geminiapi.presentation.features.chat_feature

import android.content.Context
import android.speech.tts.TextToSpeech
import android.text.Spanned
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.varunkumar.geminiapi.model.ChatMessage
import com.varunkumar.geminiapi.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.noties.markwon.Markwon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val generativeModel: GenerativeModel,
    private val markwon: Markwon,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    // TODO implement savedStateHandle
    private var textToSpeech: TextToSpeech? = null

    private val _state = MutableStateFlow(
        ChatState(
            message = savedStateHandle["message"] ?: ""
        )
    )

    init {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = textToSpeech?.setLanguage(Locale.US)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "The Language specified is not supported!")
                } else {
                    Log.d("TTS", "Initialization succeeded")
                }
            } else {
                Log.e("TTS", "Initialization failed")
            }
        }
    }

    fun speakOutText(message: ChatMessage) {
        textToSpeech?.speak(message.data, TextToSpeech.QUEUE_FLUSH, null, null)
        _state.update { it.copy(speakText = message) }
    }

    fun onStopSpeak() {
        if (textToSpeech?.isSpeaking == true) {
            textToSpeech?.stop()
            _state.update { it.copy(speakText = null) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }

    val state = _state.asStateFlow()

    fun createSpannedText(input: String): Spanned {
        return markwon.toMarkdown(input)
    }

    fun onMessageChange(newMessage: String) {
        _state.update { it.copy(message = newMessage) }
        savedStateHandle["message"] = newMessage
    }

    fun sendPrompt() {
        val message = _state.value.message

        _state.update {
            it.copy(
                uiState = UiState.Loading,
                messages = it.messages + ChatMessage(data = message, isBot = false)
            )
        }

        onMessageChange("")

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel
                    .generateContent(
                        content {
                            text(message)
                        }
                    )

                response.text?.let { outputContent ->
                    val newMessage = ChatMessage(
                        data = outputContent,
                        isBot = true
                    )

                    _state.update {
                        it.copy(
                            messages = it.messages + newMessage,
                            uiState = UiState.Success(outputContent, newMessage.timestamp)
                        )
                    }
                }
            } catch (e: Exception) {
                val errorMessage = e.localizedMessage

                _state.update {
                    it.copy(
                        uiState = UiState.Error(errorMessage)
                    )
                }
            }
        }
    }
}

