package ui.authentication.data

import androidx.datastore.core.DataStore
import com.example.malawipoliceapp.UserSession
import kotlinx.coroutines.flow.first

class UserSessionDataSource(
    private val dataStore: DataStore<UserSession>
) {

    suspend fun getToken(): String? {
        val session = dataStore.data.first()

        return if (session.isLoggedIn && session.token.isNotBlank()) {
            session.token
        } else {
            null
        }
    }

    suspend fun clear() {
        dataStore.updateData {
            UserSession.getDefaultInstance()
        }
    }
}
