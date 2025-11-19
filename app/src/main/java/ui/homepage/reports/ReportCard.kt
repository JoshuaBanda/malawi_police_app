package ui.homepage.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.malawipoliceapp.ui.theme.White
import com.example.malawipoliceapp.ui.theme.primaryColor
import ui.reports.ReportsMainScreen
import ui.reports.typesOfReports.GenderBasedViolenceReportScreen

// Use your existing ReportCardShape object
import ui.reports.ReportBottomSheet


@Composable
fun ReportCard(navController: NavController, title: String, navigateTo: String) {
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .height(350.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        // Main card
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(350.dp)
                .shadow(2.dp, ReportCardShape)
                .clip(ReportCardShape)
                .border(1.dp, Color.Black.copy(alpha = 0.1f), ReportCardShape)
                .background(White)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Icon section
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f)
                        .clip(RoundedCornerShape(16.dp))
                        .padding(top = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FamilyRestroom,
                        contentDescription = null,
                        tint = primaryColor,
                        modifier = Modifier.size(100.dp)
                    )
                }

                Spacer(modifier = Modifier.height(70.dp))

                // Title
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(15.dp)
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        // Bottom-right button to open bottom sheet
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(40.dp)
                .offset(x = (-20).dp, y = (-2).dp)
                .align(Alignment.BottomEnd)
                .shadow(2.dp, RoundedCornerShape(16.dp), clip = false)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { isSheetOpen = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Open",
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
        }


        // Bottom sheet
        if (isSheetOpen) {
            ReportBottomSheet(
                navController = navController,
                onDismiss = { isSheetOpen = false }
            ) { nav ->
                GenderBasedViolenceReportScreen(nav)
            }
        }

    }
}


