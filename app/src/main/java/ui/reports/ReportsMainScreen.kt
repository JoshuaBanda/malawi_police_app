package ui.reports

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.malawipoliceapp.ui.theme.Black
import com.example.malawipoliceapp.ui.theme.White
import com.example.malawipoliceapp.ui.theme.primaryColor

data class ReportType(
    val title: String,
    val navigateTo: String,
    val icon: ImageVector      // 🌟 ICON instead of photo
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsMainScreen(
    navController: NavController? = null,
    onReportClick: (String) -> Unit = {}
) {

    val typesOfReportsList = listOf(
        ReportType("Report case", "report_case_screen", Icons.Default.ReportProblem),
        ReportType("Track case", "track_cases_screen", Icons.Default.Assignment),
        ReportType("Completed cases", "completed_cases_screen", Icons.Default.CheckCircle),
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Services",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = Black
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.shadow(8.dp),       // ⭐ Adds shadow
            )
        }
    ) { innerPadding ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(24.dp),
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(typesOfReportsList) { item ->
                Report(
                    item = item,
                    onClick = {
                        onReportClick(item.navigateTo)
                        navController?.navigate(item.navigateTo)
                    }
                )
            }
        }
    }
}

@Composable
fun Report(
    item: ReportType,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .height(140.dp)
            .fillMaxWidth()

            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .background(White, RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                modifier = Modifier.size(30.dp),
                tint = primaryColor
            )

            Text(
                text = item.title,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 10.dp),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportsPreview() {
    ReportsMainScreen()
}
