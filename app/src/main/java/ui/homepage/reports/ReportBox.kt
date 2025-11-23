import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.malawipoliceapp.ui.theme.White
import com.example.malawipoliceapp.ui.theme.complementoryColor
import com.example.malawipoliceapp.ui.theme.primaryColor
import ui.TruncatedText
import ui.reports.ReportBottomSheet
import ui.reports.typesOfReports.MinorAccident

@Composable
fun ReportBox(
    navController: NavController,
    title: String,
    description: String = "Report minor incident...",
    icon: ImageVector,
    content: @Composable (NavController) -> Unit // composable lambda
) {
    // State to control the bottom sheet
    var isSheetOpen by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon Box
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(primaryColor.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Report Icon",
                tint = primaryColor,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Details + Button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Title
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.Black
            )

            // Description + Button Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Description text
                TruncatedText(
                    text = description,
                    maxChar = 18,
                    modifier = Modifier.padding(top = 4.dp)
                )

                // Open button
                Button(
                    onClick = { isSheetOpen = true },
                    colors = ButtonDefaults.buttonColors(containerColor = White),
                    modifier = Modifier
                        .width(80.dp)
                        .height(36.dp)
                        .shadow(2.dp, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Text(
                        text = "Open",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }

    // Bottom sheet - Pass the lambda to ReportBottomSheet
    if (isSheetOpen) {
        ReportBottomSheet(
            navController = navController,
            onDismiss = { isSheetOpen = false },
            content = content // Pass the lambda here
        )
    }
}