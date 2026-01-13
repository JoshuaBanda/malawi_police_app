package ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ui.authentication.data.AuthRepository

class SplashViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _destination =
        MutableStateFlow<SplashDestination>(SplashDestination.Loading)

    val destination: StateFlow<SplashDestination> =
        _destination.asStateFlow()

    init {
        observeSession()
    }

    private fun observeSession() {
        viewModelScope.launch {
            repository.isLoggedIn
                .distinctUntilChanged()
                .collect { loggedIn ->
                    _destination.value =
                        if (loggedIn) {
                            SplashDestination.Home
                        } else {
                            SplashDestination.Login
                        }
                }
        }
    }
}
