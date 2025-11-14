package ui.reports

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.malawipoliceapp.R
import com.example.malawipoliceapp.ui.theme.White
import com.example.malawipoliceapp.ui.theme.Black

data class ReportType(val title: String, val navigateTo: String, val photo: Int)

@Composable
fun ReportsMainScreen(
    navController: NavController? = null,
    onReportClick: (String) -> Unit = {}
) {

    val typesOfReportsList = listOf(
        ReportType("Report incident\n/crime", "incident_crime_screen", R.drawable.incident_icon),
        ReportType("Track cases", "track_cases_screen", R.drawable.track_case_icon),
        ReportType("Completed cases", "completed_cases_screen", R.drawable.complete_cases_icon),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 20.dp)
    ) {
        Text(
            text = "Reports",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = Black,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(typesOfReportsList) { item ->
                Report(
                    item = item,
                    navController = navController,
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
    navController: NavController?,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .height(180.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(White, RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current  // safe ripple
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    )
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(id = item.photo),
                contentDescription = item.title,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Text(
                text = item.title,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 10.dp),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportsPreview() {
    ReportsMainScreen(navController = null)
}
