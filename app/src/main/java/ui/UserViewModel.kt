package ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class UserState(
    val firstName: String? = null,
    val lastName: String? = null,
    val token: String? = null,
    val role: String? = null
)

class UserViewModel : ViewModel() {

    private val _user = MutableStateFlow(UserState())
    val user: StateFlow<UserState> = _user

    fun updateUser(
        firstName: String,
        lastName: String,
        token: String,
        role: String
    ) {
        _user.value = UserState(firstName, lastName, token, role)
    }
}


