package ui.reports.typesOfReports

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ui.reports.ReportService

@Composable
fun GenderBasedViolenceReportScreen(navController: NavController){
    val report = ReportService(
        title = "Gender based violence",
        description = "Report cases of domestic abuse, sexual harassment, rape, or any form of gender-based violence.",
        eligibility = mutableListOf("Victim of GBV",
            "Witness"),
        requiredDocuments = mutableListOf(),
        conditions = mutableListOf("Should occur within Malawi")
    )
    ReportDetailsScreen(
        navController,
        report,
        "gbv_report_form"
    )

}