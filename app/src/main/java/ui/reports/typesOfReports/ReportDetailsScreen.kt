package ui.reports.typesOfReports

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.malawipoliceapp.ui.theme.White
import com.example.malawipoliceapp.ui.theme.primaryColor
import androidx.compose.material3.Button
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items


import com.example.malawipoliceapp.ui.theme.complementoryColor
import ui.TruncatedText
import ui.reports.ReportService
@Composable
fun ReportDetailsScreen(
    navController: NavController,
    report: ReportService,
    navigateTo: String,
    icon: ImageVector
) {
    val title = report.title
    val description = report.description
    val eligibility = report.eligibility
    val requiredDocuments = report.requiredDocuments
    val conditions = report.conditions

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header: Icon + Title
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .fillMaxHeight(0.7f)
                        .clip(RoundedCornerShape(5))
                        .background(complementoryColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = "Report Type Icon", tint = White, modifier = Modifier.size(60.dp))
                }

                Box(
                    modifier = Modifier.weight(0.7f).fillMaxHeight().background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(title, fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = Color.Black)
                        TruncatedText(text = description, maxChar = 50, modifier = Modifier.padding(top = 4.dp))
                    }
                }
            }
        }

        // Description Section
        item {
            Column(modifier = Modifier.fillMaxWidth().background(White).padding(16.dp)) {
                Text(text = description, fontSize = 14.sp, lineHeight = 20.sp, color = Color.Black)
            }
        }

        // Eligibility Section
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, Color.Black.copy(alpha = 0.2f)), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.People,
                            contentDescription = "Eligibility Icon",
                            tint = White,
                            modifier = Modifier.size(36.dp).clip(RoundedCornerShape(50)).background(primaryColor).padding(8.dp)
                        )
                        Text("Who can report", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text("Who is eligible to apply for the service", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        eligibility.forEach { item -> Text("• $item", fontSize = 14.sp, color = Color.Black) }
                    }
                }
            }
        }

        // Documents Section
        if (requiredDocuments.isNotEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(1.dp, Color.Black.copy(alpha = 0.2f)), shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.FilePresent,
                                contentDescription = "Documents Icon",
                                tint = White,
                                modifier = Modifier.size(36.dp).clip(RoundedCornerShape(50)).background(primaryColor).padding(8.dp)
                            )
                            Text("Documents Required", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("Required documents for applying for the service", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                            requiredDocuments.forEach { item -> Text("• $item", fontSize = 14.sp, color = Color.Black) }
                        }
                    }
                }
            }
        }

        // Conditions Section
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Conditions", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    conditions.forEach { item -> Text("• $item", fontSize = 14.sp, color = Color.Black) }
                }
            }
        }

        // Apply Button
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    onClick = { navController.navigate(navigateTo) },
                    colors = ButtonDefaults.buttonColors(containerColor = complementoryColor),
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier.fillMaxWidth(0.8f).height(50.dp)
                ) {
                    Text("Apply", fontWeight = FontWeight.Bold, color = primaryColor)
                }
            }
        }

        item { Spacer(modifier = Modifier.height(50.dp)) }
    }
}
