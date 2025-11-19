package ui.welcoming

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.malawipoliceapp.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import com.example.malawipoliceapp.ui.theme.mograFontFamily
import androidx.compose.ui.unit.sp
import com.example.malawipoliceapp.ui.theme.complementoryColor
import androidx.compose.ui.graphics.Color
import com.example.malawipoliceapp.ui.theme.primaryColor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.BorderStroke
import androidx.navigation.NavController


@Composable
fun SignUpSignIn(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(
            text = "MPS",
            fontFamily = mograFontFamily,
            fontSize = 24.sp,
            color = primaryColor,
            lineHeight = 36.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Image occupies 3/4 of the screen height
        Image(
            painter = painterResource(R.drawable.welcome_report_police),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()           // full width
                .fillMaxHeight(0.75f)     // 75% of screen height
                .clip(RoundedCornerShape(24.dp))
        )

        Spacer(modifier = Modifier.height(20.dp))
        //two button here
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { navController.navigate("sign_up") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor // use the actual Color variable
                )
            ) {
                Text(
                    text = "New here? Create an account",
                    color = Color.White
                )
            }
            Button(
                onClick = { navController.navigate("sign_in") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, complementoryColor), // yellow border
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent // ensures no fill
                )
            ) {
                Text(
                    text = "Already have an account?",
                    color = primaryColor // use actual Color object
                )
            }
        }

    }
}
