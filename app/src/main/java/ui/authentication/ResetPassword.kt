@file:OptIn(ExperimentalMaterial3Api::class)

package ui.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.malawipoliceapp.ui.theme.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    navController: NavController,
    phoneNumber: String
) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    val isFormValid = newPassword.isNotEmpty() &&
            confirmPassword.isNotEmpty() &&
            newPassword == confirmPassword &&
            newPassword.length >= 6

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
            text = "Reset Password",
            color = White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Info
        Text(
            text = "Create a new password for your account",
            color = White,
            fontSize = 14.sp,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // New Password Field
        OutlinedTextField(
            value = newPassword,
            onValueChange = {
                newPassword = it
                passwordError = ""
            },
            placeholder = {
                Text(
                    "New Password",
                    fontSize = 16.sp,
                    color = Gray.copy(alpha = 0.7f)
                )
            },
            singleLine = true,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                        tint = White
                    )
                }
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Gray.copy(alpha = 0.5f),
                errorBorderColor = Color.Red,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password Field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                passwordError = ""
            },
            placeholder = {
                Text(
                    "Confirm Password",
                    fontSize = 16.sp,
                    color = Gray.copy(alpha = 0.7f)
                )
            },
            singleLine = true,
            visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                    Icon(
                        imageVector = if (isConfirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (isConfirmPasswordVisible) "Hide password" else "Show password",
                        tint = White
                    )
                }
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Gray.copy(alpha = 0.5f),
                errorBorderColor = Color.Red,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        // Error Message
        if (passwordError.isNotEmpty()) {
            Text(
                text = passwordError,
                color = Color.Red.copy(alpha = 0.8f),
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )
        }

        // Password requirements hint
        if (newPassword.isNotEmpty() && newPassword.length < 6) {
            Text(
                text = "Password must be at least 6 characters",
                color = Color.Yellow.copy(alpha = 0.7f),
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )
        }

        // Password match indicator
        if (confirmPassword.isNotEmpty() && newPassword != confirmPassword) {
            Text(
                text = "Passwords do not match",
                color = Color.Red.copy(alpha = 0.8f),
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Reset Password Button
        Button(
            onClick = {
                when {
                    newPassword.isEmpty() || confirmPassword.isEmpty() -> {
                        passwordError = "Please fill in all fields"
                    }
                    newPassword.length < 6 -> {
                        passwordError = "Password must be at least 6 characters"
                    }
                    newPassword != confirmPassword -> {
                        passwordError = "Passwords do not match"
                    }
                    else -> {
                        // Success - perform password reset logic here
                        // You can make API call to reset password
                        passwordError = ""
                        // Navigate to success screen or login screen
                        navController.navigate("success_screen") {
                            popUpTo("reset_password") { inclusive = true }
                        }
                    }
                }
            },
            enabled = isFormValid,
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
            interactionSource = remember { MutableInteractionSource() }
        ) {
            Text(
                text = "Reset Password",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Back to login option
        TextButton(
            onClick = {
                navController.popBackStack()
            },
            interactionSource = remember { MutableInteractionSource() }
        ) {
            Text(
                text = "Back to Login",
                color = White,
                fontSize = 14.sp
            )
        }
    }
}

@Preview
@Composable
fun PreviewResetPasswordScreen() {
    val navController = rememberNavController()
    ResetPasswordScreen(navController = navController, phoneNumber = "991234567")
}