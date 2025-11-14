package ui.reports

class ReportService(
    val title: String = "",
    val description: String = "",
    val eligibility: MutableList<String> = mutableListOf(),
    val requiredDocuments: MutableList<String> = mutableListOf(),
    val conditions: MutableList<String> = mutableListOf()
)
