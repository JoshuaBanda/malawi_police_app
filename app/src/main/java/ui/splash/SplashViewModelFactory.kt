package ui.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ui.authentication.RetrofitProvider
import ui.authentication.data.AuthRepository

class SplashViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            val repo = AuthRepository(
                context = context,
                api = RetrofitProvider.authApi
            )
            @Suppress("UNCHECKED_CAST")
            return SplashViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
