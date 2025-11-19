package ui.authentication

import androidx.compose.foundation.background
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
fun SignUp(navController: NavController) {
    // Input states
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    // Error states
    var firstNameError by remember { mutableStateOf(false) }
    var lastNameError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    val isFormValid = remember(firstName, lastName, password, confirmPassword) {
        firstName.isNotBlank() && lastName.isNotBlank() &&
                password.isNotBlank() && confirmPassword.isNotBlank() &&
                password.length >= 6 && password == confirmPassword
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            // Header
            Text(
                text = "Create an Account",
                fontFamily = mograFontFamily,
                fontSize = 24.sp,
                color = primaryColor,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
                    .background(primaryColor)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // First Name Field
                OutlinedTextField(
                    value = firstName,
                    onValueChange = {
                        firstName = it
                        firstNameError = it.isBlank()
                    },
                    placeholder = {
                        Text(
                            "First Name",
                            fontSize = 16.sp,
                            color = Gray.copy(alpha = 0.7f)
                        )
                    },
                    singleLine = true,
                    isError = firstNameError,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
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

                if (firstNameError) {
                    Text(
                        text = "First name is required",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Last Name Field
                OutlinedTextField(
                    value = lastName,
                    onValueChange = {
                        lastName = it
                        lastNameError = it.isBlank()
                    },
                    placeholder = {
                        Text(
                            "Last Name",
                            fontSize = 16.sp,
                            color = Gray.copy(alpha = 0.7f)
                        )
                    },
                    singleLine = true,
                    isError = lastNameError,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
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

                if (lastNameError) {
                    Text(
                        text = "Last name is required",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Password Field
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = it.isBlank() || it.length < 6
                    },
                    placeholder = {
                        Text(
                            "Password (min 6 characters)",
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
                    visualTransformation = if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                tint = Gray
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
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

                if (passwordError) {
                    Text(
                        text = "Password must be at least 6 characters",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Confirm Password Field
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        confirmPasswordError = it != password
                    },
                    placeholder = {
                        Text(
                            "Confirm Password",
                            fontSize = 16.sp,
                            color = Gray.copy(alpha = 0.7f)
                        )
                    },
                    singleLine = true,
                    isError = confirmPasswordError,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    visualTransformation = if (confirmPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password",
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

                if (confirmPasswordError) {
                    Text(
                        text = "Passwords do not match",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Sign Up Button
                Button(
                    onClick = {
                        if (isFormValid) {
                            navController.navigate("enter_phone_number")
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
                        text = "Sign Up",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Sign in option
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Already have an account? Sign In",
                        color = White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
//                        modifier = Modifier.clickable {
//                            navController.navigate("signin")
//                        }
                    )
                }
            }
        }
    }
}