package ui.authentication.data

import android.content.Context
import com.example.malawipoliceapp.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ui.UserState

class AuthRepository(
    private val context: Context,
    private val api: AuthApi
) {

    private val dataStore = context.userSessionDataStore

    val isLoggedIn: Flow<Boolean> =
        dataStore.data.map { session ->
            session.isLoggedIn
        }

    suspend fun login(
        phone: String,
        password: String
    ): UserState {

        val response = api.login(
            LoginRequest(phone, password)
        )

        if (!response.isSuccessful) {
            throw IllegalStateException(
                "Login failed: ${response.code()} ${response.message()}"
            )
        }

        val body = response.body()
            ?: throw IllegalStateException("Empty response body")

        dataStore.updateData { current ->
            current.toBuilder()
                .setToken(body.token)
                .setFirstName(body.firstName)
                .setLastName(body.lastName)
                .setRole(body.role)
                .setIsLoggedIn(true)
                .build()
        }

        return UserState(
            firstName = body.firstName,
            lastName = body.lastName,
            role = body.role,
            token = body.token
        )
    }

    /**
     * Registration does NOT log the user in
     */
    suspend fun register(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        password: String,
        role: String
    ) {
        val response = api.register(
            RegisterRequest(
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber,
                password = password,
                role = role
            )
        )

        if (!response.isSuccessful) {
            throw IllegalStateException(
                "Registration failed: ${response.code()} ${response.message()}"
            )
        }

        response.body()
            ?: throw IllegalStateException("Empty registration response")
    }

    suspend fun requestOtp(phoneNumber: String) {
        val response = api.requestOtp(
            OtpRequest(phoneNumber = phoneNumber)
        )

        if (!response.isSuccessful) {
            throw IllegalStateException(
                "OTP request failed: ${response.code()} ${response.message()}"
            )
        }

        response.body()
            ?: throw IllegalStateException("Empty OTP response")
    }

    suspend fun logout() {
        dataStore.updateData {
            UserSession.getDefaultInstance()
        }
    }
    suspend fun verifyOtp(phone: String, otp: String) {
        val request = VerifyOtpRequest(phoneNumber = phone, otp = otp)

        val response = api.verifyOtp(request)

        if (!response.isSuccessful) {
            throw IllegalStateException(
                "OTP verification failed: ${response.code()} ${response.message()}"
            )
        }

        val body = response.body()
            ?: throw IllegalStateException("Empty OTP verification response")

        if (!body.success) {
            throw IllegalStateException(body.message)
        }

        // Optionally, store a flag in DataStore or token if needed
        // e.g. _uiState.value = _uiState.value.copy(isVerified = true)
    }
}
