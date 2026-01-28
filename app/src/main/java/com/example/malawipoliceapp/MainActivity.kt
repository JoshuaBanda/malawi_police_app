package com.example.malawipoliceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.malawipoliceapp.ui.theme.MalawiPoliceAppTheme
import com.example.malawipoliceapp.ui.theme.White
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import ui.SuccessScreen
import ui.authentication.*
import ui.authentication.data.SignUpViewModel
import ui.homepage.HomePage
import ui.news.CurrentNews
import ui.profile.UserProfileScreen
import ui.reports.ReportsMainScreen
import ui.reports.group.reportCase.ReportCaseScreen
import ui.reports.reportForms.GenderBasedViolenceForm
import ui.reports.reportForms.MinorAccident
import ui.reports.typesOfReports.GenderBasedViolence
import ui.splash.SplashRoute
import ui.splash.SplashViewModel
import ui.welcoming.SwipablePagerScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.Transparent.toArgb()

        setContent {
            MalawiPoliceAppTheme {

                val navController = rememberNavController()
                val systemUiController = rememberSystemUiController()

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.Transparent,
                        darkIcons = true
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(White)
                        .systemBarsPadding()
                ) {

                    NavHost(
                        navController = navController,
                        startDestination = "splash"
                    ) {

                        composable("splash") {
                            val splashViewModel: SplashViewModel = hiltViewModel()
                            SplashRoute(
                                viewModel = splashViewModel,
                                onNavigateToLogin = {
                                    navController.navigate("sign_in") {
                                        popUpTo("splash") { inclusive = true }
                                    }
                                },
                                onNavigateToHome = {
                                    navController.navigate("home_page") {
                                        popUpTo("splash") { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable("welcome") {
                            SwipablePagerScreen(navController)
                        }

                        composable("sign_up") {
                            val signUpViewModel: SignUpViewModel = hiltViewModel()
                            SignUp(navController, signUpViewModel)
                        }

                        composable("phone_number") {
                            val signUpViewModel: SignUpViewModel = hiltViewModel()
                            PhoneNumberScreen(navController, signUpViewModel)
                        }

                        composable("sign_in") {
                            SignIn(navController)
                        }

                        composable("home_page") {
                            HomePage(navController)
                        }

                        composable("gbv_report_details") {
                            GenderBasedViolence(navController)
                        }

                        composable("gbv_report_form") {
                            GenderBasedViolenceForm(navController)
                        }

                        composable("minor_accident") {
                            MinorAccident(navController)
                        }

                        composable("forgot_password") {
                            ForgetPasswordScreen(navController)
                        }

                        composable(
                            route = "otp_verification/{phone}",
                            arguments = listOf(
                                navArgument("phone") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val phone = backStackEntry.arguments?.getString("phone").orEmpty()
                            val signUpViewModel: SignUpViewModel = hiltViewModel()
                            OtpVerificationScreen(
                                navController = navController,
                                phoneNumber = phone,
                                viewModel = signUpViewModel
                            )
                        }

                        composable("password_otp_verification/{phone}") {
                            val phone = it.arguments?.getString("phone").orEmpty()
                            PasswordOtpVerification(navController, phone)
                        }

                        composable("kba_screen/{phone}") {
                            val phone = it.arguments?.getString("phone").orEmpty()
                            KBAScreen(navController, phone)
                        }

                        composable("reset_password/{phone}") {
                            val phone = it.arguments?.getString("phone").orEmpty()
                            ResetPasswordScreen(navController, phone)
                        }

                        composable("success_screen") {
                            SuccessScreen(navController)
                        }

                        composable("report_main_screen") {
                            ReportsMainScreen(navController)
                        }

                        composable("report_case_screen") {
                            ReportCaseScreen(navController)
                        }

                        composable("profile_screen") {
                            UserProfileScreen(navController)
                        }

                        composable("current_news") {
                            CurrentNews()
                        }
                    }
                }
            }
        }
    }
}
