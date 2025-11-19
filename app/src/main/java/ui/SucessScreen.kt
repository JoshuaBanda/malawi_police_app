package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.malawipoliceapp.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SuccessScreen(navController: NavController) {
    var countdown by remember { mutableStateOf(5) }

    LaunchedEffect(Unit) {
        while (countdown > 0) {
            delay(1000L)
            countdown--
        }
        navController.currentBackStackEntry?.let {
            navController.navigate("home_page") { popUpTo("success_screen") { inclusive = true } }
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

        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(White)
                .border(2.dp, complementoryColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.CheckCircle, contentDescription = "Successful", tint = complementoryColor, modifier = Modifier.size(80.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("Successful", color = primaryColor, fontSize = 24.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
       // Text("Account created successfully", color = Black, fontSize = 16.sp, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.weight(1f))
    }
}
