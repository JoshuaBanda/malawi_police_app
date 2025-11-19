package ui.authentication


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.malawipoliceapp.ui.theme.Gray
import com.example.malawipoliceapp.ui.theme.White
import com.example.malawipoliceapp.ui.theme.mograFontFamily
import com.example.malawipoliceapp.ui.theme.primaryColor

@Composable
fun ForgetPasswordScreen(navController: NavController) {
    var phoneNumber by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf(false) }

    val isFormValid = phoneNumber.isNotEmpty() && phoneNumber.length >= 9

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            // Header section
            Text(
                text = "Reset Password",
                fontFamily = mograFontFamily,
                fontSize = 24.sp,
                color = primaryColor,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Form section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
                    .background(primaryColor)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Instruction text
                Text(
                    text = "Enter your phone number to receive a password reset code",
                    color = White.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 24.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                // Phone Number Field
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        if (it.all(Char::isDigit) && it.length <= 10) {
                            phoneNumber = it
                            phoneError = false
                        }
//                        else if (it.isNotEmpty()) {
//                            phoneError = true
//                        }
                    },
                    placeholder = {
                        Text(
                            "Enter phone number",
                            color = Gray.copy(alpha = 0.7f),
                            fontSize = 16.sp
                        )
                    },
                    prefix = {
                        Text(
                            text = "+265 | ",
                            color = if (phoneError) Color.Red else Color.White
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
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Gray.copy(alpha = 0.5f),
                        errorBorderColor = Color.Red.copy(alpha = 0.5f),
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                // Phone number error
                if (phoneError) {
                    Text(
                        text = "Please enter a valid phone number",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Send Reset Code Button
                Button(
                    onClick = {
                        val isPhoneValid = phoneNumber.isNotEmpty() && phoneNumber.length >= 9
                        phoneError = !isPhoneValid

                        if (isPhoneValid) {
                            navController.navigate("kba_screen/$phoneNumber")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(56.dp)
                        .alpha(if (isFormValid) 1f else 0.9f),
                    shape = RoundedCornerShape(12.dp),
                    enabled = isFormValid,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFormValid) White else White.copy(alpha = 0.5f),
                        contentColor = if (isFormValid) primaryColor else primaryColor.copy(alpha = 0.5f),
                        disabledContainerColor = White.copy(alpha = 0.5f),
                        disabledContentColor = primaryColor.copy(alpha = 0.5f)
                    )
                ) {
                    Text(
                        text = "Continue",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Back to sign in option
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Back to Sign In",
                        color = White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null // No ripple OR replace with Material3 ripple
                        ) {
                            navController.navigate("sign_in")
                        }

                    )
                }
            }
        }
    }
}