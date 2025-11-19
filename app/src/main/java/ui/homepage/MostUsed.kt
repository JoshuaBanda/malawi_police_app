package ui.homepage


import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.malawipoliceapp.ui.theme.complementoryColor
import com.example.malawipoliceapp.ui.theme.primaryColor


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.malawipoliceapp.R
import com.example.malawipoliceapp.ui.theme.White
import ui.TruncatedText
import ui.reports.ReportBottomSheet
import ui.reports.typesOfReports.CyberCrime
import ui.reports.typesOfReports.MinorAccident
import ui.reports.typesOfReports.UnkownAccidents
import ui.reports.typesOfReports.WitnessTipOff


@Composable
fun MostUsed(navController: NavController) {
    Row (
        modifier = Modifier
            .fillMaxSize(),
            horizontalArrangement = Arrangement.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .shadow(4.dp, RoundedCornerShape(16.dp))
                .background(
                    Color.White,
                    RoundedCornerShape(16.dp)
                ) // Added background for shadow to be visible
                .clip(RoundedCornerShape(16.dp))
        ) {
            // Header Row
            Row(
                modifier = Modifier
                    .height(60.dp)
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Most used",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "See all",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = primaryColor
                )
            }

            // Content Area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    //.padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            ) {
                MostUsedItem(
                    navController = navController,
                    title = "Road accident",
                    description = "Report any form of traffic accident",
                    icon = Icons.Default.CarCrash
                ){ nav ->
                    // This lambda will be called within the composable context of ReportBottomSheet
                    MinorAccident(nav)
                }


                MostUsedItem(
                    navController = navController,
                    title = "Witness/Tip off",
                    description = "Report any crime or suspicious activities you witnessed, but where not directly involved in",
                    icon = Icons.Default.RemoveRedEye
                ){ nav ->
                    // This lambda will be called within the composable context of ReportBottomSheet
                    WitnessTipOff(nav)
                }


                MostUsedItem(
                    navController = navController,
                    title = "Cyber crime",
                    description = "Report online fraud, hacking, mobile money scam or any cyber crime",
                    icon = Icons.Outlined.Lock
                ){ nav ->
                    // This lambda will be called within the composable context of ReportBottomSheet
                    CyberCrime(nav)
                }

            }
        }
    }
}



@Composable
fun MostUsedItem(
    navController: NavController,
    title: String,
    description: String = "Report minor incident...",
    icon: ImageVector,
    content: @Composable (NavController) -> Unit // composable lambda
) {
    // State to control the bottom sheet
    var isSheetOpen by remember { mutableStateOf(false) }

    // Make the entire row clickable with visual feedback
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current
            ) { isSheetOpen = true }
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(8.dp),
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

        // Details
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

            // Description
            TruncatedText(
                text = description,
                maxChar = 25,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // Optional: Add a chevron or arrow to indicate it's clickable
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Open",
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }

    // Bottom sheet
    if (isSheetOpen) {
        ReportBottomSheet(
            navController = navController,
            onDismiss = { isSheetOpen = false },
            content = content
        )
    }
}








