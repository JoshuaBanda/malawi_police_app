@file:OptIn(ExperimentalMaterial3Api::class)

package ui.authentication

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.navigation.NavController
import com.example.malawipoliceapp.ui.theme.primaryColor
import com.example.malawipoliceapp.ui.theme.Gray
import com.example.malawipoliceapp.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberScreen(navController:NavController) {
    // Define variables inside the function
    var phoneNumber by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf(false) }

    // Calculate phone validation in real-time
    val isPhoneValid = phoneNumber.length in 9..15 && phoneNumber.all { it.isDigit() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        // Title
        Text(
            text = "What's your phone number",
            color = White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Phone Number Input
        TextField(
            value = phoneNumber,
            onValueChange = {
                if (it.all { char -> char.isDigit() } && it.length <= 15) {
                    phoneNumber = it
                }
            },
            placeholder = {
                Text(
                    "Enter phone number",
                    fontSize = 16.sp,
                    color = Gray.copy(alpha = 0.7f)
                )
            },
            prefix = {
                Text(
                    "+265 |",
                    color = if (phoneError) Color.Red else Color.Black
                )
            },
            singleLine = true,
            isError = phoneError,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(56.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = White,
                unfocusedIndicatorColor = Gray.copy(alpha = 0.4f),
                cursorColor = White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                containerColor = Color.White,
                errorIndicatorColor = Color.Red,
                errorLabelColor = Color.Red,
                errorCursorColor = Color.Red
            ),
        )

        // Show error message if needed
        if (phoneError) {
            Text(
                text = "Phone number must be 9-15 digits",
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Info text
        Text(
            text = "We will send you an SMS to verify if it's really you",
            color = White,
            modifier = Modifier.padding(horizontal = 20.dp),
            fontSize = 14.sp,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Sign Up Button
        Button(
            onClick = {
                if (isPhoneValid) {
                    navController.navigate("otp_verification/$phoneNumber")
                }
            },
            enabled = isPhoneValid,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = White,
                disabledContainerColor = White.copy(alpha = 0.3f),
                contentColor = primaryColor,
                disabledContentColor = primaryColor.copy(alpha = 0.5f)
            )
        ) {
            Text(
                text = "Sign Up",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Show remaining characters
        if (phoneNumber.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${phoneNumber.length}/15",
                color = White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
        }
    }
}
