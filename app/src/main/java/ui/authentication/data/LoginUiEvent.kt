package ui.authentication.data

sealed class LoginUiEvent {
    data class Error(val message: String) : LoginUiEvent()
    // You can add more events later, e.g.,
    // object NavigateToHome : LoginUiEvent()
    // object NavigateToLogin : LoginUiEvent()
}
