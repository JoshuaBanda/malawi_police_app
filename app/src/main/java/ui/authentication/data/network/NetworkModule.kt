package ui.authentication.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ui.authentication.data.UserSessionDataSource
import ui.authentication.data.userSessionDataStore

object NetworkModule {

    fun provideOkHttpClient(context: Context): OkHttpClient {
        val sessionDataSource =
            UserSessionDataSource(context.userSessionDataStore)

        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(sessionDataSource))
            .build()
    }

    fun provideRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://your.api.base.url/") // CHANGE THIS
            .client(provideOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
