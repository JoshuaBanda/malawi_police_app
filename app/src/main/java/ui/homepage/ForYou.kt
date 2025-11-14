package ui.homepage

import WeeklyUpdatesSlideshow
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ui.homepage.reports.ReportCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.graphics.Color
import ui.homepage.reports.ReportBox


@Composable
fun ForYou(navController: NavController) {


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),

        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            WeeklyUpdatesSlideshow()
        }
        item {
            Spacer(modifier = Modifier.height(15.dp))
        }

        item {
            ReportCard(navController = navController, "Gender based violence", "gbv_report_details")
        }

        item {
            Spacer(modifier = Modifier.height(50.dp))
        }
        item {
            Column (){

                ReportBox(navController,"minor accidents","gbv_report_details")

                ReportBox(navController,"unknown accidents","gbv_report_details")
            }
        }

        item {
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}
