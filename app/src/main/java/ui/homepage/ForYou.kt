package ui.homepage

import ReportBox
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ui.homepage.reports.ReportCard
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ui.homepage.news.WeeklyUpdatesSlideshow
import ui.reports.typesOfReports.UnkownAccidents
import ui.reports.typesOfReports.MinorAccident

@Composable
fun ForYou(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(12.dp)
    ) {
        WeeklyUpdatesSlideshow()

        Spacer(modifier = Modifier.height(15.dp))

        ReportCard(
            navController = navController,
            "Gender based violence",
        )

        Spacer(modifier = Modifier.height(50.dp))

        Column {
            Box(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "Report accidents",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            // Minor Accidents
            ReportBox(
                navController = navController,
                title = "Minor Accidents",
                description = "Report minor accident",
                icon = Icons.Default.CarCrash
            ) { nav ->
                MinorAccident(nav)
            }

            // Unknown Accidents
            ReportBox(
                navController = navController,
                title = "Unknown Accidents",
                description = "Report accident caused by unknown",
                icon = Icons.Default.QuestionMark
            ) { nav ->
                UnkownAccidents(nav)
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        MostUsed(navController)

        Spacer(modifier = Modifier.height(100.dp))
    }
}











//package ui.homepage
//
//import ReportBox
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import ui.homepage.reports.ReportCard
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.CarCrash
//import androidx.compose.material.icons.filled.QuestionMark
//import androidx.compose.material3.Text
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.sp
//import ui.homepage.news.WeeklyUpdatesSlideshow
//import ui.reports.typesOfReports.UnkownAccidents
//import ui.reports.typesOfReports.MinorAccident
//
//@Composable
//fun ForYou(navController: NavController) {
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(12.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp),
//    ) {
//        item {
//            WeeklyUpdatesSlideshow()
//        }
//        item {
//            Spacer(modifier = Modifier.height(15.dp))
//        }
//
//        item {
//            ReportCard(navController = navController, "Gender based violence", "gbv_report_details")
//        }
//
//        item {
//            Spacer(modifier = Modifier.height(50.dp))
//        }
//        item {
//            Column {
//                Box(modifier = Modifier.padding(10.dp)) {
//                    Text(
//                        text = "Report accidents",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 20.sp
//                    )
//                }
//
//                // Minor Accidents
//                ReportBox(
//                    navController = navController,
//                    title = "Minor Accidents",
//                    description = "Report minor accident",
//                    icon = Icons.Default.CarCrash
//                ) { nav ->
//                    // This lambda will be called within the composable context of ReportBottomSheet
//                    MinorAccident(nav)
//                }
//
//                // Unknown Accidents
//                ReportBox(
//                    navController = navController,
//                    title = "Unknown Accidents",
//                    description = "Report accident caused by unknown",
//                    icon = Icons.Default.QuestionMark
//                ) { nav ->
//                    // This lambda will be called within the composable context of ReportBottomSheet
//                    UnkownAccidents(nav)
//                }
//            }
//        }
//
//        item {
//            Spacer(modifier = Modifier.height(50.dp))
//        }
//        item {
//            MostUsed(navController)
//        }
//
//        item {
//            Spacer(modifier = Modifier.height(100.dp))
//        }
//    }
//}

