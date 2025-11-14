package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.navigation.NavController
import com.example.malawipoliceapp.ui.theme.White
import com.example.malawipoliceapp.ui.theme.Black
import com.example.malawipoliceapp.ui.theme.complementoryColor
import com.example.malawipoliceapp.ui.theme.mograFontFamily
import com.example.malawipoliceapp.ui.theme.primaryColor
import kotlinx.coroutines.delay

@Composable
fun SuccessScreen(navController: NavController) {

    var countdown by remember { mutableStateOf(5) }

    // 5-second timeout to navigate to home page with countdown
    LaunchedEffect(Unit) {
        while (countdown > 0) {
            delay(1000L)
            countdown--
        }
        navController.navigate("home_page") {
            // Clear the back stack so user can't go back to success screen
            popUpTo("success_screen") { inclusive = true }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // Success icon with circular border
        Box(
            modifier = Modifier
                .size(150.dp)
                .border(2.dp, complementoryColor, CircleShape)
                .clip(CircleShape)
                .background(White)
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Successful",
                tint = complementoryColor,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Successful",
            color = primaryColor,
            fontSize = 24.sp,
            fontFamily = mograFontFamily,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Account created successfully",
            color = Black,
            fontSize = 16.sp,
            fontFamily = mograFontFamily,
            textAlign = TextAlign.Center
        )

        // Countdown text to show user the timeout
        Spacer(modifier = Modifier.height(16.dp))
        

        Spacer(modifier = Modifier.weight(1f))
    }
}

// Preview without NavController
@Preview
@Composable
fun SuccessScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(150.dp)
                .border(2.dp, complementoryColor, CircleShape)
                .clip(CircleShape)
                .background(White)
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Successful",
                tint = complementoryColor,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Successful",
            color = primaryColor,
            fontSize = 24.sp,
            fontFamily = mograFontFamily,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Account created successfully",
            color = Black,
            fontSize = 16.sp,
            fontFamily = mograFontFamily,
            textAlign = TextAlign.Center
        )



        Spacer(modifier = Modifier.weight(1f))
    }
}