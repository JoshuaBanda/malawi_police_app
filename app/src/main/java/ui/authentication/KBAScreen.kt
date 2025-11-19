package ui.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
fun KBAScreen(navController: NavController, phoneNumber: String) {
    var motherName by remember { mutableStateOf("") }
    var placeOfBirth by remember { mutableStateOf("") }
    var fatherName by remember { mutableStateOf("") }

    var motherNameError by remember { mutableStateOf(false) }
    var placeOfBirthError by remember { mutableStateOf(false) }
    var fatherNameError by remember { mutableStateOf(false) }

    val isFormValid = remember(motherName, placeOfBirth, fatherName) {
        motherName.isNotBlank() && placeOfBirth.isNotBlank() && fatherName.isNotBlank()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Header
        Text(
            text = "Verify Your Identity",
            fontFamily = mograFontFamily,
            fontSize = 28.sp,
            color = White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Answer these security questions to verify your identity",
            color = White.copy(alpha = 0.8f),
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 40.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        // Mother's Name Field
        Text(
            text = "What is your mother's maiden name?",
            color = White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = motherName,
            onValueChange = {
                motherName = it
                motherNameError = false
            },
            placeholder = {
                Text(
                    "Enter mother's maiden name",
                    color = Gray.copy(alpha = 0.7f),
                    fontSize = 16.sp
                )
            },
            singleLine = true,
            isError = motherNameError,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(bottom = 24.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = White,
                unfocusedBorderColor = Gray.copy(alpha = 0.5f),
                errorBorderColor = Color.Red.copy(alpha = 0.5f),
                cursorColor = White,
                focusedTextColor = White,
                unfocusedTextColor = White
            )
        )

        if (motherNameError) {
            Text(
                text = "Mother's name is required",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }

        // Place of Birth Field
        Text(
            text = "What is your place of birth?",
            color = White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = placeOfBirth,
            onValueChange = {
                placeOfBirth = it
                placeOfBirthError = false
            },
            placeholder = {
                Text(
                    "Enter your place of birth",
                    color = Gray.copy(alpha = 0.7f),
                    fontSize = 16.sp
                )
            },
            singleLine = true,
            isError = placeOfBirthError,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(bottom = 24.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = White,
                unfocusedBorderColor = Gray.copy(alpha = 0.5f),
                errorBorderColor = Color.Red.copy(alpha = 0.5f),
                cursorColor = White,
                focusedTextColor = White,
                unfocusedTextColor = White
            )
        )

        if (placeOfBirthError) {
            Text(
                text = "Place of birth is required",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }

        // Father's Name Field
        Text(
            text = "What is your father's name?",
            color = White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = fatherName,
            onValueChange = {
                fatherName = it
                fatherNameError = false
            },
            placeholder = {
                Text(
                    "Enter father's name",
                    color = Gray.copy(alpha = 0.7f),
                    fontSize = 16.sp
                )
            },
            singleLine = true,
            isError = fatherNameError,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(bottom = 24.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = White,
                unfocusedBorderColor = Gray.copy(alpha = 0.5f),
                errorBorderColor = Color.Red.copy(alpha = 0.5f),
                cursorColor = White,
                focusedTextColor = White,
                unfocusedTextColor = White
            )
        )

        if (fatherNameError) {
            Text(
                text = "Father's name is required",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Verify Identity Button
        Button(
            onClick = {
                val isMotherNameValid = motherName.isNotBlank()
                val isPlaceOfBirthValid = placeOfBirth.isNotBlank()
                val isFatherNameValid = fatherName.isNotBlank()

                motherNameError = !isMotherNameValid
                placeOfBirthError = !isPlaceOfBirthValid
                fatherNameError = !isFatherNameValid

                if (isMotherNameValid && isPlaceOfBirthValid && isFatherNameValid) {
                    // Verify answers and navigate to OTP screen
                    navController.navigate("password_otp_verification/$phoneNumber")
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp)
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
                text = "Verify Identity",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Back option
        Text(
            text = "Back",
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