package ui.reports.typesOfReports


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ui.reports.ReportService

@Composable
fun WitnessTipOff(navController: NavController){
    val report = ReportService(
        title = "Witness / Tip off",
        description = "Report any crime or suspicious activities you witnessed, but where not directly involved in",
        eligibility = mutableListOf("Concerned citizen"),
        requiredDocuments = mutableListOf(
            "Include any photos that supports your claim ( optional )"
        ),
        conditions = mutableListOf(
            "Should occur within Malawi"
        )
    )
    ReportDetailsScreen(
        navController,
        report,
        "gbv_report_form",
        icon =  Icons.Default.RemoveRedEye
    )

}