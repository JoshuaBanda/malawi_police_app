package com.example.malawipoliceapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ui.authentication.data.AuthApi
import ui.authentication.data.UserSessionDataSource
import ui.authentication.data.userSessionDataStore
import ui.authentication.network.AuthInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideUserSessionDataSource(
        @ApplicationContext context: Context
    ): UserSessionDataSource {
        return UserSessionDataSource(context.userSessionDataStore)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        sessionDataSource: UserSessionDataSource
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(sessionDataSource))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://your.api.base.url/") // CHANGE THIS
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(
        retrofit: Retrofit
    ): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}