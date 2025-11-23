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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.malawipoliceapp.ui.theme.primaryColor
import com.example.malawipoliceapp.ui.theme.Gray
import com.example.malawipoliceapp.ui.theme.White
import com.example.malawipoliceapp.ui.theme.MalawiPoliceAppTheme

@Composable
fun PhoneNumberScreen(
    navController: NavController
) {
    var phoneNumber by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf(false) }

    // Validate phone number
    val isPhoneValid = phoneNumber.length <=10 && phoneNumber.all { it.isDigit() }

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
            text = "What's your phone number",
            color = White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Phone input field
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                if (it.all(Char::isDigit) && it.length <= 10) {
                    phoneNumber = it
                    phoneError = false
                } else if (it.isNotEmpty()) {
                    phoneError = true
                }
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

                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Gray.copy(alpha = 0.5f),
                errorBorderColor = Color.Red.copy(alpha = 0.5f),
                cursorColor = Color.White
            )
        )

        // Error message
        if (phoneError) {
            Text(
                text = "Phone number must be 10 digits",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Info text
        Text(
            text = "We will send you an SMS to verify if it's really you",
            color = White,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 10.dp),
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Continue Button
        Button(
            onClick = {
                if (isPhoneValid) {
                    navController.navigate("otp_verification/$phoneNumber")
                }
            },
            enabled = isPhoneValid,
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
            Text(
                text = "Sign Up",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Length indicator
        if (phoneNumber.isNotEmpty()) {
            Text(
                text = "${phoneNumber.length}/10",
                color = White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
        }
    }
}

//////////////////////////////////////////////////
// PREVIEW
//////////////////////////////////////////////////

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PhoneNumberScreenPreview() {
//    MalawiPoliceAppTheme {
//        PhoneNumberScreen(navController = rememberNavController())
//    }
//}
