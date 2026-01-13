package ui.authentication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ui.authentication.data.AuthApi

object RetrofitProvider {


    private const val BASE_URL = "https://api.yourbackend.com/"

    val authApi: AuthApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
}
