package ui.authentication.data



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ui.UserState

data class LoginUiState(
    val isLoading: Boolean = false,
    val user: UserState? = null,
    val errorMessage: String? = null
)

data class LoginResponse(
    val token: String,
    val firstName: String,
    val lastName: String,
    val role: String
)
data class LoginRequest(
    val phoneNumber: String,
    val password: String
)

data class LoginCredentials(
    val phoneNumber: String = "",
    val password: String = ""
)


class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _credentials =
        MutableStateFlow(LoginCredentials())
    val credentials: StateFlow<LoginCredentials> = _credentials

    private val _uiState =
        MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun handlePhoneChanged(phone: String) {
        _credentials.value =
            _credentials.value.copy(phoneNumber = phone)
    }

    fun handlePasswordChanged(password: String) {
        _credentials.value =
            _credentials.value.copy(password = password)
    }

    fun login() {
        val (phone, password) = _credentials.value

        if (!isValid(phone, password)) {
            _uiState.value =
                LoginUiState(errorMessage = "Invalid credentials")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState(isLoading = true)

            try {
                val user = repository.login(phone, password)
                _uiState.value = LoginUiState(user = user)
            } catch (e: Exception) {
                _uiState.value =
                    LoginUiState(errorMessage = "Login failed")
            }
        }
    }

    private fun isValid(phone: String, password: String): Boolean {
        return phone.length >= 9 && password.isNotBlank()
    }

    fun clearError() {
        _uiState.value =
            _uiState.value.copy(errorMessage = null)
    }
}
