@file:OptIn(ExperimentalMaterial3Api::class)

package ui.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.navigation.NavController
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.malawipoliceapp.ui.theme.*

@Composable
fun OtpVerificationScreen(
    navController: NavController,
    phoneNumber: String
) {
    var otpCode by remember { mutableStateOf("") }
    var otpError by remember { mutableStateOf(false) }

    val isOtpValid = otpCode.length == 6 && otpCode.all { it.isDigit() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(100.dp))

        // Title
        Text(
            text = "Enter OTP",
            color = White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Info
        Text(
            text = "We sent a 6-digit code to +265 $phoneNumber",
            color = White,
            fontSize = 14.sp,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // OTP Field
        OutlinedTextField(
            value = otpCode,
            onValueChange = {
                if (it.length <= 6 && it.all(Char::isDigit)) {
                    otpCode = it
                    otpError = false
                }
//                else if (it.isNotEmpty()) {
//                    otpError = true
//                }
            },
            placeholder = {
                Text(
                    "Enter OTP",
                    fontSize = 16.sp,
                    color = Gray.copy(alpha = 0.7f)
                )
            },
            singleLine = true,
            isError = otpError,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Gray.copy(alpha = 0.5f),
                errorBorderColor = Color.White,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        // Error
        if (otpError) {
            Text(
                text = "OTP must be 6 digits",
                color = Color.Red.copy(alpha = 0.5f),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Verify Button
        Button(
            onClick = {

                if (isOtpValid) {
                    navController.navigate("success_screen")
                } else {
                    otpError = true
                }
            },
            enabled = isOtpValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = White,
                disabledContainerColor = White.copy(alpha = 0.3f),
                contentColor = primaryColor,
                disabledContentColor = primaryColor.copy(alpha = 0.5f)
            ),
            // ✅ Important: explicitly pass LocalIndication.current if needed
            // This usually fixes the crash if a ripple/indication issue happens
            interactionSource = remember { MutableInteractionSource() }
        ) {
            Text(
                text = "Verify",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Remaining digits indicator
        if (otpCode.isNotEmpty()) {
            Text(
                text = "${otpCode.length}/6",
                color = White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        TextButton(
            onClick = { /* resend OTP */ },
            interactionSource = remember { MutableInteractionSource() } // Fix
        ) {
            Text(
                text = "Resend OTP",
                color = White,
                fontSize = 14.sp
            )
        }

    }
}

//////////////////////////////////////////////////
// PREVIEW
//////////////////////////////////////////////////

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun OtpVerificationScreenPreview() {
//    MalawiPoliceAppTheme {
//        OtpVerificationScreen(
//            navController = rememberNavController(),
//            phoneNumber = "999123456"
//        )
//    }
//}
