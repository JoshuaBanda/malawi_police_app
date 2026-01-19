package ui.authentication.data

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val password: String,
    val role: String
)
