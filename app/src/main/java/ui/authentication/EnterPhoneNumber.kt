@file:OptIn(ExperimentalMaterial3Api::class)

package ui.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.example.malawipoliceapp.ui.theme.primaryColor
import com.example.malawipoliceapp.ui.theme.Gray
import com.example.malawipoliceapp.ui.theme.White
import ui.authentication.data.SignUpViewModel

@Composable
fun PhoneNumberScreen(
    navController: NavController,
    viewModel: SignUpViewModel
) {
    /* ---------------- System UI ---------------- */

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = primaryColor,
            darkIcons = false
        )
    }

    /* ---------------- State ---------------- */

    val credentials by viewModel.credentials.collectAsStateWithLifecycle()
    val otpState by viewModel.otpState.collectAsStateWithLifecycle()

    // Malawi local number: 9 digits (prefix handled separately as +265)
    val isPhoneValid =
        credentials.phoneNumber.length == 9 &&
                credentials.phoneNumber.all(Char::isDigit) &&
                (credentials.phoneNumber.startsWith("8") ||
                        credentials.phoneNumber.startsWith("9"))

    val phoneError =
        credentials.phoneNumber.isNotEmpty() && !isPhoneValid

    /* ---------------- Navigation after OTP ---------------- */

    LaunchedEffect(otpState.errorMessage) {
        // You can handle OTP errors here if needed
    }

    /* ---------------- UI ---------------- */

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "What's your phone number",
            color = White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = credentials.phoneNumber,
            onValueChange = { newValue ->
                if (newValue.length <= 9 && newValue.all(Char::isDigit)) {
                    viewModel.handlePhoneChanged(newValue)
                }
            },
//            label = {
//                Text("Phone number")
//            },
            placeholder = {
                Text(
                    text = "991234567",
                    color = Gray.copy(alpha = 0.7f)
                )
            },
            prefix = {
                Text(
                    text = "+265 | ",
                    color = if (phoneError) Color.Red.copy(0.5f) else Color.White
                )
            },
            singleLine = true,
            isError = phoneError,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (isPhoneValid && !otpState.isLoading) {
                        viewModel.requestOtp()
                        navController.navigate(
                            "otp_verification/${credentials.phoneNumber}"
                        )
                    }
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Gray.copy(alpha = 0.5f),
                errorBorderColor = Color.Red,
                cursorColor = Color.White
            )
        )

        if (phoneError) {
            Text(
                text = "Phone number must be 9 or 8 digits",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "We will send you an SMS to verify your phone number",
            color = White,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            modifier = Modifier.padding(horizontal = 10.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                viewModel.requestOtp()
                navController.navigate(
                    "otp_verification/${credentials.phoneNumber}"
                )
            },
            enabled = isPhoneValid && !otpState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = White,
                disabledContainerColor = White.copy(alpha = 0.3f),
                contentColor = primaryColor,
                disabledContentColor = primaryColor.copy(alpha = 0.5f)
            )
        ) {
            if (otpState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 2.dp,
                    color = primaryColor
                )
            } else {
                Text(
                    text = "Continue",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (credentials.phoneNumber.isNotEmpty()) {
            Text(
                text = "${credentials.phoneNumber.length}/9",
                color = White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
        }
    }
}
