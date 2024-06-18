package com.varunkumar.geminiapi.di

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import com.varunkumar.geminiapi.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideGenerativeModel(): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.apiKey,
            generationConfig = generationConfig {
                temperature = 0.9f
                topK = 0
                topP = 1f
                maxOutputTokens = 8192
            }
        )
    }
}