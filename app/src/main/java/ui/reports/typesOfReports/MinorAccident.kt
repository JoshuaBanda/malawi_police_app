package ui.reports.typesOfReports

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ui.reports.ReportService

@Composable
fun MinorAccident(navController: NavController){
    val report = ReportService(
        title = "Minor accidents",
        description = "Report a minor or moderate traffic accident without injuries",
        eligibility = mutableListOf("Drivers","Passenger","Pedestrian","Witness"),
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
        "minor_accident",
        icon =  Icons.Default.CarCrash,
    )

}