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

import com.example.malawipoliceapp.ui.theme.complementoryColor
import ui.TruncatedText
import ui.reports.ReportService

@Composable
fun ReportDetailsScreen(navController: NavController, report: ReportService,navigateTo:String,icon: ImageVector) {
    val title = report.title
    val description = report.description
    val eligibility = report.eligibility
    val requiredDocuments = report.requiredDocuments
    val conditions = report.conditions


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(rememberScrollState())
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Icon Section
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight(0.7f)
                    .clip(RoundedCornerShape(5))
                    .background(complementoryColor/*.copy(alpha = 0.9f)*/),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector =icon,
                    contentDescription = "Report Type Icon",
                    tint = White,
                    modifier = Modifier.size(60.dp)
                )
            }

            // Right Text Section
            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxHeight()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    TruncatedText(
                        text = description,
                        maxChar = 50,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        // 🔹 Description Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(16.dp)
        ) {
            Text(
                text = description,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = Color.Black
            )
        }


        // 🔹 Eligibility Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(1.dp, Color.Black.copy(alpha = 0.2f)),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Icon + Title (centered)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.People,
                        contentDescription = "Eligibility Icon",
                        tint = White,
                        modifier = Modifier
                            .size(36.dp)

                            .clip(RoundedCornerShape(50))
                            .background(primaryColor)
                            .padding(8.dp),


                        )
                    Text(
                        text = "Who can report",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                // Eligibility list (left-aligned)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {

                    Text(
                        text = "Who is eligible to apply for the service",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    eligibility.forEach { item ->
                        Text(
                            text = "• $item",
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(50.dp))










        // documents  Section
        if (requiredDocuments.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        BorderStroke(1.dp, Color.Black.copy(alpha = 0.2f)),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Icon + Title (centered)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.FilePresent,
                            contentDescription = "condition Icon",
                            tint = White,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(50))
                                .background(primaryColor)
                                .padding(8.dp)
                        )
                        Text(
                            text = "Documents Required",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    // Description + List
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Required documents for applying for the service",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )

                        requiredDocuments.forEach { item ->
                            Text(
                                text = "• $item",
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }



        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "Conditions",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
            Column(

                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                conditions.forEach { item ->
                    Text(
                        text = "• $item",
                        fontSize = 14.sp,
                        color = Color.Black
                    )

                }

            }
        }

        Spacer(modifier = Modifier.height(100.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    navController.navigate(navigateTo)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = complementoryColor // yellow color
                ),
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)   // button width is 80% of the screen
                    .height(50.dp)
            ) {
                Text(
                    text = "Apply",
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                )
            }
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun Prev() {
//    ReportDetailsScreen(rememberNavController())
//}
