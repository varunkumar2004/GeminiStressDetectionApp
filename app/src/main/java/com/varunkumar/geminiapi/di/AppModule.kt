package com.varunkumar.geminiapi.di

import android.content.Context
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import com.varunkumar.geminiapi.BuildConfig
import com.varunkumar.geminiapi.model.ModelApis.STRESS_MODEL_BASE_URL
import com.varunkumar.geminiapi.model.StressModelApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import io.noties.markwon.Markwon
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

    @Provides
    @Singleton
    fun provideMarkdown(@ApplicationContext context: Context): Markwon {
        return Markwon.create(context)
    }
}