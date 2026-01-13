package ui.authentication.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.malawipoliceapp.UserSession
val Context.userSessionDataStore: DataStore<UserSession> by dataStore(
    fileName = "user_session.pb",
    serializer = UserSessionSerializer
)
