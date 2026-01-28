@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.example.malawipoliceapp.ui.theme.*
import ui.authentication.data.SignUpUiEvent
import ui.authentication.data.SignUpViewModel

@Composable
fun SignUp(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val credentials by viewModel.credentials.collectAsState()
    val isFormValid by viewModel.isFormValid.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setNavigationBarColor(
            color = primaryColor,
            darkIcons = false
        )
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                SignUpUiEvent.Success -> {
                    navController.navigate("phone_number") {
                        popUpTo("signup") { inclusive = true }
                    }
                }
                is SignUpUiEvent.Error -> {
                    // TODO: show snackbar if needed
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {

            Text(
                text = "Create an Account",
                fontFamily = mograFontFamily,
                fontSize = 24.sp,
                color = primaryColor,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(primaryColor)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {

                // First name
                OutlinedTextField(
                    value = credentials.firstName,
                    onValueChange = viewModel::handleFirstNameChanged,
                    placeholder = { Text("First Name", color = Gray) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    colors = textFieldColors()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Last name
                OutlinedTextField(
                    value = credentials.lastName,
                    onValueChange = viewModel::handleLastNameChanged,
                    placeholder = { Text("Last Name", color = Gray) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    colors = textFieldColors()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password
                OutlinedTextField(
                    value = credentials.password,
                    onValueChange = viewModel::handlePasswordChanged,
                    placeholder = { Text("Password (min 6 characters)", color = Gray) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation =
                        if (passwordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible },
                            enabled = credentials.password.isNotEmpty()
                        ) {
                            Icon(
                                imageVector =
                                    if (passwordVisible) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                contentDescription =
                                    if (passwordVisible) "Hide password"
                                    else "Show password",
                                tint = Gray
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    colors = textFieldColors()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Confirm password
                OutlinedTextField(
                    value = credentials.confirmPassword,
                    onValueChange = viewModel::handleConfirmPasswordChanged,
                    placeholder = { Text("Confirm Password", color = Gray) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation =
                        if (confirmPasswordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = { confirmPasswordVisible = !confirmPasswordVisible },
                            enabled = credentials.confirmPassword.isNotEmpty()
                        ) {
                            Icon(
                                imageVector =
                                    if (confirmPasswordVisible) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                contentDescription =
                                    if (confirmPasswordVisible) "Hide password"
                                    else "Show password",
                                tint = Gray
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    colors = textFieldColors()
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Sign up button
                Button(
                    onClick = { navController.navigate("phone_number") },
                    enabled = isFormValid && !uiState.isLoading,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(56.dp)
                        .align(Alignment.CenterHorizontally)
                        .alpha(if (isFormValid) 1f else 0.6f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        contentColor = primaryColor
                    )
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = primaryColor
                        )
                    } else {
                        Text("Sign Up", fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Already have an account? Sign In",
                    color = White.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        navController.navigate("sign_in")
                    }
                )
            }
        }
    }
}

/* ---------- Reusable TextField Colors ---------- */

@Composable
private fun textFieldColors() =
    OutlinedTextFieldDefaults.colors(
        focusedBorderColor = White,
        unfocusedBorderColor = Gray.copy(alpha = 0.5f),
        cursorColor = White,
        focusedTextColor = White,
        unfocusedTextColor = White
    )
