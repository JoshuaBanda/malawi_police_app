package ui.splash

sealed class SplashDestination {
    object Loading : SplashDestination()
    object Login : SplashDestination()
    object Home : SplashDestination()
}
