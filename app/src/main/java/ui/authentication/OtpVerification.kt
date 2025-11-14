

@file:OptIn(ExperimentalMaterial3Api::class)

package ui.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.navigation.NavController
import com.example.malawipoliceapp.ui.theme.primaryColor
import com.example.malawipoliceapp.ui.theme.Gray
import com.example.malawipoliceapp.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(navController: NavController, phoneNumber: String) {
    var otpCode by remember { mutableStateOf("") }
    var otpError by remember { mutableStateOf(false) }

    val isOtpValid = otpCode.length == 6 && otpCode.all { it.isDigit() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        // Title
        Text(
            text = "Enter OTP",
            color = White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Info text
        Text(
            text = "We sent a 6-digit code to +265 $phoneNumber",
            color = White,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 20.dp),
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // OTP Input
        TextField(
            value = otpCode,
            onValueChange = {
                if (it.length <= 6 && it.all { char -> char.isDigit() }) {
                    otpCode = it
                }
            },
            placeholder = {
                Text(
                    text = "Enter OTP",
                    fontSize = 16.sp,
                    color = Gray.copy(alpha = 0.7f)
                )
            },
            singleLine = true,
            isError = otpError,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(56.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = White,
                unfocusedIndicatorColor = Gray.copy(alpha = 0.4f),
                cursorColor = White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                containerColor = Color.White,
                errorIndicatorColor = Color.Red,
                errorLabelColor = Color.Red,
                errorCursorColor = Color.Red
            ),
        )

        if (otpError) {
            Text(
                text = "OTP must be 6 digits",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Verify Button
        Button(
            onClick = {
                if (isOtpValid) {
                    // Navigate to next screen (e.g., HomePage)
                    navController.navigate("success_screen")
                } else {
                    otpError = true
                }
            },
            enabled = isOtpValid,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = White,
                disabledContainerColor = White.copy(alpha = 0.3f),
                contentColor = primaryColor,
                disabledContentColor = primaryColor.copy(alpha = 0.5f)
            )
        ) {
            Text(
                text = "Verify",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show remaining characters
        if (otpCode.isNotEmpty()) {
            Text(
                text = "${otpCode.length}/6",
                color = White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Resend OTP
        TextButton(onClick = {
            // handle resend logic
        }) {
            Text(
                text = "Resend OTP",
                color = White,
                fontSize = 14.sp
            )
        }
    }
}
