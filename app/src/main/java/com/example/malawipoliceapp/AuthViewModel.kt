package com.example.malawipoliceapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ui.authentication.data.AuthRepository

class AuthViewModel(
    repository: AuthRepository
) : ViewModel() {

    val isLoggedIn = repository.isLoggedIn
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            false
        )
}
