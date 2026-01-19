@file:OptIn(ExperimentalMaterial3Api::class)

package ui.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.malawipoliceapp.ui.theme.*
import ui.authentication.data.SignUpViewModel

@Composable
fun OtpVerificationScreen(
    navController: NavController,
    phoneNumber: String,
    viewModel: SignUpViewModel
) {
    val otpState by viewModel.otpState.collectAsStateWithLifecycle()

    val isOtpValid =
        otpState.otp.length == 6 && otpState.otp.all(Char::isDigit)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "Enter OTP",
            color = White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "We sent a 6-digit code to +265 $phoneNumber",
            color = White,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = otpState.otp,
            onValueChange = {
                viewModel.onOtpChanged(it) {
                    viewModel.verifyOtp {
                        navController.navigate("success_screen") {
                            popUpTo("signup") { inclusive = true }
                        }
                    }
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        otpState.errorMessage?.let { message ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                color = Color.Red.copy(alpha = 0.8f),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                viewModel.verifyOtp {
                    navController.navigate("success_screen") {
                        popUpTo("sign_up") { inclusive = true }
                    }
                }
            },
            enabled = isOtpValid && !otpState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = White,
                disabledContainerColor = White.copy(alpha = 0.3f),
                contentColor = primaryColor
            ),
            interactionSource = remember { MutableInteractionSource() }
        ) {
            if (otpState.isLoading) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(20.dp),
                    color = primaryColor
                )
            } else {
                Text(
                    text = "Verify",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${otpState.otp.length}/6",
            color = White.copy(alpha = 0.7f),
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Spacer(modifier = Modifier.height(24.dp))

        if (otpState.canResend) {
            TextButton(onClick = { viewModel.requestOtp() }) {
                Text("Resend OTP", color = White)
            }
        } else {
            Text(
                text = "Resend OTP in ${otpState.resendSecondsLeft}s",
                color = White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
        }

    }
}
