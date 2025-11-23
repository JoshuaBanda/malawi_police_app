package ui.reports.group.reportCase

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.outlined.Lock
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
import com.example.malawipoliceapp.ui.theme.Black
import com.example.malawipoliceapp.ui.theme.primaryColor
import ui.TruncatedText
import ui.reports.ReportBottomSheet
import ui.reports.typesOfReports.CyberCrime
import ui.reports.typesOfReports.GenderBasedViolence
import ui.reports.typesOfReports.MinorAccident

// --------------------
// Data class for report items
// --------------------
data class ReportItemData(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val content: @Composable (NavController) -> Unit
)

// --------------------
// Main screen
// --------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportCaseScreen(navController: NavController) {

    // List of report items (data only)
    val reportItems = listOf(
        ReportItemData(
            title = "Minor accident",
            description = "Report a minor or moderate traffic accident without injuries",
            icon = Icons.Default.CarCrash,
        ) { nav -> MinorAccident(nav) },

        ReportItemData(
            title = "Cyber crime",
            description = "Report online fraud, hacking, mobile money scam or any cyber crime",
            icon = Icons.Outlined.Lock
        ) { nav -> CyberCrime(nav) },

        ReportItemData(
            title = "Gender based violence",
            description = "Report Gender Based Violence",
            icon = Icons.Default.FamilyRestroom,
        ) { nav -> GenderBasedViolence(nav) }

        // Add more items here
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Reports",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = Black
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.shadow(4.dp),
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(reportItems) { item ->
                ReportCaseItem(
                    navController = navController,
                    title = item.title,
                    description = item.description,
                    icon = item.icon,
                    content = item.content
                )
            }
        }
    }
}

// --------------------
// Composable for each item
// --------------------
@Composable
fun ReportCaseItem(
    navController: NavController,
    title: String,
    description: String = "Report minor incident...",
    icon: ImageVector,
    content: @Composable (NavController) -> Unit
) {
    var isSheetOpen by remember { mutableStateOf(false) }

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

        // Title & description
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.Black
            )

            TruncatedText(
                text = description,
                maxChar = 25,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Icon(
            imageVector = Icons.Filled.ChevronRight,
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
