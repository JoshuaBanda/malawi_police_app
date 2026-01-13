package ui.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.malawipoliceapp.ui.theme.Gray
import com.example.malawipoliceapp.ui.theme.White
import com.example.malawipoliceapp.ui.theme.mograFontFamily
import com.example.malawipoliceapp.ui.theme.primaryColor

import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ui.authentication.data.LoginViewModel

@Composable
fun SignIn(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {

    val systemUiController = rememberSystemUiController()

    // Change nav bar and status bar colors
    SideEffect {

        systemUiController.setNavigationBarColor(
            color = primaryColor,
            darkIcons = false
        )
    }

    val credentials by viewModel.credentials.collectAsState()
    val loginState by viewModel.uiState.collectAsState()

    var isPasswordVisible by remember { mutableStateOf(false) }

    val isFormValid =
        credentials.phoneNumber.length >= 9 &&
                credentials.password.isNotBlank()

    // Navigate on success
    LaunchedEffect(loginState.user) {
        if (loginState.user != null) {
            navController.navigate("success_screen") {
                popUpTo("sign_in") { inclusive = true }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .imePadding()              // keyboard
                .navigationBarsPadding()   // system nav
        ) {

            Text(
                text = "Welcome back",
                fontFamily = mograFontFamily,
                fontSize = 24.sp,
                color = primaryColor,
                modifier = Modifier.padding(24.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(primaryColor)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Phone Number
                OutlinedTextField(
                    value = credentials.phoneNumber,
                    onValueChange = {
                        if (it.all(Char::isDigit) && it.length <= 10) {
                            viewModel.handlePhoneChanged(it)
                            viewModel.clearError()
                        }
                    },
                    placeholder = { Text("Phone Number") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = White,
                        unfocusedTextColor = White,
                        cursorColor = White,
                        focusedBorderColor = White,
                        unfocusedBorderColor = Gray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password
                OutlinedTextField(
                    value = credentials.password,
                    onValueChange = {
                        viewModel.handlePasswordChanged(it)
                        viewModel.clearError()
                    },
                    placeholder = { Text("Password") },
                    singleLine = true,
                    visualTransformation =
                        if (isPasswordVisible)
                            VisualTransformation.None
                        else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            isPasswordVisible = !isPasswordVisible
                        }) {
                            Icon(
                                imageVector =
                                    if (isPasswordVisible)
                                        Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = Gray
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = White,
                        unfocusedTextColor = White,
                        cursorColor = White,
                        focusedBorderColor = White,
                        unfocusedBorderColor = Gray
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Error message
                loginState.errorMessage?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Button
                Button(
                    onClick = { viewModel.login() },
                    enabled = isFormValid && !loginState.isLoading,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(56.dp)
                        .alpha(if (isFormValid) 1f else 0.6f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        contentColor = primaryColor,
                        disabledContainerColor = White.copy(alpha = 0.3f),
                        disabledContentColor = White.copy(alpha = 0.7f)
                    )
                ) {
                    Text(
                        text =
                            if (loginState.isLoading)
                                "Signing in..."
                            else
                                "Sign In",
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Don't have an account? Sign Up",
                    color = White,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        navController.navigate("sign_up")
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Forgot password?",
                    color = White,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        navController.navigate("forgot_password")
                    }
                )
            }
        }
    }
}
