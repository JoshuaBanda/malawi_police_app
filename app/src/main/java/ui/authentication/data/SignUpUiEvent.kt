package ui.authentication.data

sealed class SignUpUiEvent {
    data class Error(val message: String) : SignUpUiEvent()
    object Success : SignUpUiEvent()
}
