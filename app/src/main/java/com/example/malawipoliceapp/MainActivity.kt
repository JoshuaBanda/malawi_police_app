package com.example.malawipoliceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.malawipoliceapp.ui.theme.MalawiPoliceAppTheme
import ui.SuccessScreen
import ui.authentication.OtpVerificationScreen
import ui.authentication.PhoneNumberScreen
import ui.authentication.SignUp
import ui.homepage.HomePage
import ui.reports.reportForms.GenderBasedViolenceForm
import ui.reports.typesOfReports.GenderBasedViolenceReportScreen
import ui.welcoming.SwipablePagerScreen

import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.runtime.SideEffect
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import ui.authentication.ForgetPasswordScreen
import ui.authentication.KBAScreen
import ui.authentication.PasswordOtpVerification
import ui.authentication.ResetPasswordScreen
import ui.authentication.SingIn
import ui.profile.UserProfileScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Allow content to draw behind the system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Set transparent status bar for edge-to-edge
        window.statusBarColor = Color.Transparent.toArgb()

        // Compose content
        setContent {
            MalawiPoliceAppTheme { // Make sure you have your theme here
                val navController = rememberNavController()

                // This will handle system bar colors for all screens
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !isSystemInDarkTheme()

                val backgroundColor = MaterialTheme.colorScheme.background

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.Transparent,
                        darkIcons = useDarkIcons
                    )
                }

                Box (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(backgroundColor) // theme-based background
                ){
                    NavHost(navController = navController, startDestination = "forgot_password") {
                        composable("welcome") {
                            SwipablePagerScreen(navController)
                        }
                        composable("sign_up") {
                            SignUp(navController)
                        }
                        composable("home_page") {
                            HomePage(navController)
                        }
                        composable("gbv_report_details") {
                            GenderBasedViolenceReportScreen(navController)
                        }
                        composable("gbv_report_form") {
                            GenderBasedViolenceForm(navController)
                        }
                        composable("enter_phone_number") {
                            PhoneNumberScreen(navController)
                        }
                        composable("otp_verification/{phone}") { backStackEntry ->
                            val phone = backStackEntry.arguments?.getString("phone") ?: ""
                            OtpVerificationScreen(navController, phone)
                        }
                        composable("success_screen") {
                            SuccessScreen(navController)
                        }


                        composable("kba_screen/{phone}") { backStackEntry ->
                            val phone = backStackEntry.arguments?.getString("phone") ?: ""
                            KBAScreen(navController, phone)
                        }
                        composable("reset_password/{phone}") { backStackEntry ->
                            val phone = backStackEntry.arguments?.getString("phone") ?: ""
                            ResetPasswordScreen(navController, phone)
                        }


                        composable("profile_screen") {
                            UserProfileScreen(navController)
                        }

                        composable("sign_in") {
                            SingIn(navController)
                        }


                        composable("forgot_password") {
                            ForgetPasswordScreen(navController)
                        }

                        composable("password_otp_verification/{phone}") { backStackEntry ->
                            val phone = backStackEntry.arguments?.getString("phone") ?: ""
                            PasswordOtpVerification(navController, phone)
                        }

                    }
                }
            }
        }
    }
}