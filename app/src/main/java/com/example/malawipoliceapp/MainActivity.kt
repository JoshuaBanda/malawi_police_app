package com.example.malawipoliceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.graphics.ColorUtils
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ui.SuccessScreen
import ui.authentication.OtpVerificationScreen
import ui.authentication.PhoneNumberScreen
import ui.authentication.SignUp
import ui.homepage.HomePage
import ui.reports.reportForms.GenderBasedViolenceForm
import ui.reports.typesOfReports.GenderBasedViolenceReportScreen
import ui.welcoming.SignUpSignIn
import ui.welcoming.SwipablePagerScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Allow content to draw behind the system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Customize status bar color
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        val statusColor = Color(0xFFFFFFFF).toArgb()
        window.statusBarColor = statusColor

        val lightIcons = ColorUtils.calculateLuminance(statusColor) < 0.5
        controller.isAppearanceLightStatusBars = !lightIcons

        // Compose content
        setContent {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "enter_phone_number") {

                    composable("welcome") {
                        SwipablePagerScreen(navController)
                    }
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
                        GenderBasedViolenceForm (navController)
                    }


                    composable("enter_phone_number") {
                        PhoneNumberScreen (navController)
                    }

                    composable("otp_verification/{phone}") { backStackEntry ->
                        val phone = backStackEntry.arguments?.getString("phone") ?: ""
                        OtpVerificationScreen(navController, phone)
                    }


                    composable("success_screen") {
                        SuccessScreen(navController)
                    }
                }

        }
    }
}
