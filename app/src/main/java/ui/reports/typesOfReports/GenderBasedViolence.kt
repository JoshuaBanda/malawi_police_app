package ui.reports.typesOfReports

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ui.reports.ReportService

@Composable
fun GenderBasedViolence(navController: NavController){
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
        navigateTo = "gbv_report_form",
        icon =  Icons.Default.FamilyRestroom,
    )

}