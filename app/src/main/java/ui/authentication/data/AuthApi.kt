package ui.authentication.data

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface AuthApi {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}
