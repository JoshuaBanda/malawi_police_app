package ui.authentication.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class SignUpCredentials(
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)

data class SignUpUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)
data class OtpUiState(
    val otp: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val resendSecondsLeft: Int = 0,
    val canResend: Boolean = true
)


data class OtpRequest(
    val phoneNumber: String
)

data class OtpResponse(
    val message: String
)

data class VerifyOtpRequest(
    val phoneNumber: String,
    val otp: String
)

data class VerifyOtpResponse(
    val message: String,
    val success: Boolean
)

private const val OTP_LENGTH = 6
private const val RESEND_TIMEOUT = 30 // seconds

class SignUpViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    init {
        Log.d("SignUpViewModel", "Created: ${this.hashCode()}")
    }


    companion object {
        private const val DEFAULT_ROLE = "CITIZEN"
    }



    private val _credentials = MutableStateFlow(SignUpCredentials())
    val credentials: StateFlow<SignUpCredentials> = _credentials

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState

    private val _uiEvent = MutableSharedFlow<SignUpUiEvent>()
    val uiEvent: SharedFlow<SignUpUiEvent> = _uiEvent

    private val _otpState = MutableStateFlow(OtpUiState())
    val otpState: StateFlow<OtpUiState> = _otpState


    /* ---------------- OTP ---------------- */

    fun onOtpChanged(value: String, onAutoSubmit: () -> Unit) {
        if (value.length <= OTP_LENGTH && value.all(Char::isDigit)) {
            _otpState.update {
                it.copy(otp = value, errorMessage = null)
            }

            if (value.length == OTP_LENGTH) {
                onAutoSubmit()
            }
        }
    }


    fun verifyOtp(onSuccess: () -> Unit) {
        val otp = _otpState.value.otp
        val phone = _credentials.value.phoneNumber
        Log.d("Sign up veiw model ","otp verification: $otp for phone: $phone\"")

        if (otp.length != 6) {
            _otpState.update { it.copy(errorMessage = "Invalid OTP code") }
            return
        }

        viewModelScope.launch {
            _otpState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                repository.verifyOtp(phone, otp)
                onSuccess()
            } catch (e: Exception) {
                _otpState.update { it.copy(errorMessage = e.message ?: "OTP verification failed") }
            } finally {
                _otpState.update { it.copy(isLoading = false) }
            }
        }
    }




    /* ---------------- SIGN UP ---------------- */

    fun handleFirstNameChanged(value: String) {
        _credentials.value = _credentials.value.copy(firstName = value)
    }

    fun handleLastNameChanged(value: String) {
        _credentials.value = _credentials.value.copy(lastName = value)
    }

    fun handlePhoneChanged(value: String) {
        _credentials.value = _credentials.value.copy(phoneNumber = value)
    }

    fun handlePasswordChanged(value: String) {
        _credentials.value = _credentials.value.copy(password = value)
    }

    fun handleConfirmPasswordChanged(value: String) {
        _credentials.value = _credentials.value.copy(confirmPassword = value)
    }

    val isFormValid: StateFlow<Boolean> =
        credentials.map {
            it.firstName.isNotBlank() &&
                    it.lastName.isNotBlank() &&
//                    it.phoneNumber.length >= 9 &&
                    it.password.length >= 6 &&
                    it.password == it.confirmPassword
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )



    fun requestOtp() {
        val phone = _credentials.value.phoneNumber

        if (phone.length < 9) {
            emitError("Invalid phone number")
            return
        }

        viewModelScope.launch {

            Log.d("Sign up veiw model ","otp request for phone: $phone\"")
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                repository.requestOtp(phone)
                startResendCountdown()
            } catch (e: Exception) {
                emitError("OTP request failed")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }

    }

    private fun startResendCountdown() {
        viewModelScope.launch {
            for (second in RESEND_TIMEOUT downTo 0) {
                _otpState.update {
                    it.copy(
                        resendSecondsLeft = second,
                        canResend = second == 0
                    )
                }
                delay(1_000)
            }
        }
    }


    fun register() {
        val (firstName, lastName, phone, password) = _credentials.value

        if (!isValid(firstName, lastName, phone, password)) {
            emitError("Invalid registration details")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                repository.register(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phone,
                    password = password,
                    role = DEFAULT_ROLE
                )

                _uiState.value = SignUpUiState(isSuccess = true)
                _uiEvent.emit(SignUpUiEvent.Success)

            } catch (e: Exception) {
                Log.e("SignUpViewModel", "Registration failed", e)
                _uiState.value = _uiState.value.copy(isLoading = false)
                emitError("Registration failed. Please try again.")
            }
        }
    }

    private fun emitError(message: String) {
        viewModelScope.launch {
            _uiEvent.emit(SignUpUiEvent.Error(message))
        }
    }

    private fun isValid(
        firstName: String,
        lastName: String,
        phone: String,
        password: String
    ): Boolean {
        return firstName.isNotBlank() &&
                lastName.isNotBlank() &&
                phone.length >= 9 &&
                password.length >= 6
    }
}
