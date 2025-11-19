package ui.reports.typesOfReports

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ui.reports.ReportService

@Composable
fun UnkownAccidents(navController: NavController){
    val report = ReportService(
        title = "Unknown accidents",
        description = "Allows vehicle owner or driver whose vehicle has been damaged by unknown party to obtain an accident report",
        eligibility = mutableListOf("Drivers","Business sector","Governmental entities"),
        requiredDocuments = mutableListOf(
            "Vehicle photos from four sides, including plate number ( to verify data included in the report )"
        ),
        conditions = mutableListOf(
            "It is not possible to attach or upload photos from the phone album, photos of the damaged must be taken directly",
            "Should occur within Malawi"
        )
    )
    ReportDetailsScreen(
        navController,
        report,
        "gbv_report_form",
        icon =  Icons.Default.QuestionMark
    )

}