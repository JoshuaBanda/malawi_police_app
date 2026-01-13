package ui.news

data class NewsData(
    val headline: String,
    val summary: String,
    val content: String,
    val category: String,
    val author: String,
    val createdDate: String,
    val updatedAt: String,
    val isUrgent: Boolean,
    val views: Int,
    val taggedEntities: String,
    val photos: List<Int>,
)
