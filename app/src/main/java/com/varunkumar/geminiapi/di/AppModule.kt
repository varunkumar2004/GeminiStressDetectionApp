package com.varunkumar.geminiapi.di

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import com.varunkumar.geminiapi.BuildConfig
import com.varunkumar.geminiapi.model.ModelApis.STRESS_MODEL_BASE_URL
import com.varunkumar.geminiapi.model.StressModelApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideStressModel(): StressModelApi {
        return Retrofit.Builder()
            .baseUrl(STRESS_MODEL_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StressModelApi::class.java)
    }

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