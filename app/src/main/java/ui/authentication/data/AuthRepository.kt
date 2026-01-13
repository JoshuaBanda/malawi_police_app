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

    suspend fun logout() {
        dataStore.updateData {
            UserSession.getDefaultInstance()
        }
    }
}
