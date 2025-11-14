package ui.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.malawipoliceapp.ui.theme.Gray
import com.example.malawipoliceapp.ui.theme.MalawiPoliceAppTheme
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


    val isFormValid by remember {
        derivedStateOf {
            firstName.isNotBlank() && lastName.isNotBlank() &&
                    password.isNotBlank() && confirmPassword.isNotBlank() &&
                    !firstNameError && !lastNameError && !passwordError && !confirmPasswordError &&
                    password == confirmPassword
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Header
            Text(
                text = "Create an Account",
                fontFamily = mograFontFamily,
                fontSize = 24.sp,
                lineHeight = 36.sp,
                color = primaryColor,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(primaryColor)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(40.dp))

                // First Name
                TextField(
                    value = firstName,
                    onValueChange = {
                        firstName = it
                        firstNameError = it.isBlank()
                    },
                    placeholder = { Text("First Name", fontSize = 16.sp) },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    isError = firstNameError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primaryColor,
                        unfocusedBorderColor = Gray.copy(alpha = 0.4f),
                        cursorColor = primaryColor,
                        focusedTextColor = Black,
                        unfocusedTextColor = Black,
                        focusedContainerColor = White,
                        unfocusedContainerColor = White,
                        errorBorderColor = Color.Red,
                        errorLabelColor = Color.Red
                    ),
                )

                // Last Name
                TextField(
                    value = lastName,
                    onValueChange = {
                        lastName = it
                        lastNameError = it.isBlank()
                    },
                    placeholder = { Text("Last Name", fontSize = 16.sp) },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    isError = lastNameError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primaryColor,
                        unfocusedBorderColor = Gray.copy(alpha = 0.4f),
                        cursorColor = primaryColor,
                        focusedTextColor = Black,
                        unfocusedTextColor = Black,
                        focusedContainerColor = White,
                        unfocusedContainerColor = White,
                        errorBorderColor = Color.Red,
                        errorLabelColor = Color.Red
                    ),
                )

                // Password
                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = it.isBlank() || it.length < 6
                    },
                    placeholder = { Text("Password (min 6 chars)", fontSize = 16.sp) },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    isError = passwordError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primaryColor,
                        unfocusedBorderColor = Gray.copy(alpha = 0.4f),
                        cursorColor = primaryColor,
                        focusedTextColor = Black,
                        unfocusedTextColor = Black,
                        focusedContainerColor = White,
                        unfocusedContainerColor = White,
                        errorBorderColor = Color.Red,
                        errorLabelColor = Color.Red
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = null)
                        }
                    }
                )

                // Confirm Password
                TextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        confirmPasswordError = it != password
                    },
                    placeholder = { Text("Confirm Password", fontSize = 16.sp) },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    isError = confirmPasswordError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primaryColor,
                        unfocusedBorderColor = Gray.copy(alpha = 0.4f),
                        cursorColor = primaryColor,
                        focusedTextColor = Black,
                        unfocusedTextColor = Black,
                        focusedContainerColor = White,
                        unfocusedContainerColor = White,
                        errorBorderColor = Color.Red,
                        errorLabelColor = Color.Red
                    ),
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(imageVector = image, contentDescription = null)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Sign Up Button
                Button(
                    onClick = {
                        navController.navigate("enter_phone_number")
                    },
                    //enabled = isFormValid,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        disabledContainerColor = White.copy(alpha = 0.5f)
                    )
                ) {
                    Text(
                        text = "Sign Up",
                        color = primaryColor,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}










