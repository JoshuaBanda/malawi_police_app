package ui.authentication.data

import androidx.datastore.core.Serializer
import com.example.malawipoliceapp.UserSession
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object UserSessionSerializer : Serializer<UserSession> {

    override val defaultValue: UserSession =
        UserSession.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserSession {
        return try {
            UserSession.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            defaultValue
        }
    }

    override suspend fun writeTo(
        t: UserSession,
        output: OutputStream
    ) {
        t.writeTo(output)
    }
}