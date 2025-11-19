package ui.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.malawipoliceapp.ui.theme.Gray
import com.example.malawipoliceapp.ui.theme.White
import com.example.malawipoliceapp.ui.theme.mograFontFamily
import com.example.malawipoliceapp.ui.theme.primaryColor

@Composable
fun SingIn(navController: NavController) {
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumberError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    // Calculate if form is valid - SIMPLIFIED APPROACH
    val isFormValid = phoneNumber.isNotEmpty() && phoneNumber.length >= 9 && password.isNotEmpty()

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
                text = "Welcome back",
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
                    .fillMaxHeight(0.65f)
                    .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
                    .background(primaryColor)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Phone Number Field
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() } && it.length <= 10) {
                            phoneNumber = it
                            phoneNumberError = false
                        }
                    },
                    placeholder = {
                        Text(
                            "Phone Number",
                            fontSize = 16.sp,
                            color = Gray.copy(alpha = 0.7f)
                        )
                    },
                    singleLine = true,
                    isError = phoneNumberError,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = White,
                        unfocusedBorderColor = Gray.copy(alpha = 0.5f),
                        errorBorderColor = Color.Red.copy(alpha = 0.5f),
                        cursorColor = White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                // Phone number error
                if (phoneNumberError) {
                    Text(
                        text = "Please enter a valid phone number",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Password Field
                OutlinedTextField(
                    value = password,
                    onValueChange = { newPassword ->
                        if (newPassword.length <= 20) {
                            password = newPassword
                            passwordError = false
                        }
                    },
                    placeholder = {
                        Text(
                            "Password",
                            fontSize = 16.sp,
                            color = Gray.copy(alpha = 0.7f)
                        )
                    },
                    singleLine = true,
                    isError = passwordError,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    visualTransformation = if (isPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                                tint = Gray
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = White,
                        unfocusedBorderColor = Gray.copy(alpha = 0.5f),
                        errorBorderColor = Color.Red.copy(alpha = 0.5f),
                        cursorColor = White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                // Password error
                if (passwordError) {
                    Text(
                        text = "Password is required",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Sign In Button with constraint principle
                Button(
                    onClick = {
                        val isPhoneValid = phoneNumber.isNotEmpty() && phoneNumber.length >= 9
                        val isPasswordValid = password.isNotEmpty()

                        phoneNumberError = !isPhoneValid
                        passwordError = !isPasswordValid

                        if (isPhoneValid && isPasswordValid) {
                            navController.navigate("success_screen")
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
                        text = "Sign In",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Sign up option
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Don't have an account? Sign Up",
                        color = White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null // No ripple OR replace with Material3 ripple
                        ) {
                            navController.navigate("sign_up")
                        }

                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                // Sign up option
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Forgot password?",
                        color = White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            navController.navigate("forgot_password") {
                                launchSingleTop = true
                            }
                        }
                    )
                }

            }
        }
    }
}
