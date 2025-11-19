package ui.reports.typesOfReports

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ui.reports.ReportService

@Composable
fun CyberCrime(navController: NavController){
    val report = ReportService(
        title = "Cyber crime",
        description = "Report online fraud, hacking, mobile money scam or any cyber crime",
        eligibility = mutableListOf(
            "individual",
            "organisation"
        ),
        requiredDocuments = mutableListOf(
        ),
        conditions = mutableListOf(
            "Should occur within Malawi"
        )
    )
    ReportDetailsScreen(
        navController,
        report,
        "gbv_report_form",
        icon =  Icons.Outlined.Lock
    )

}