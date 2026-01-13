package ui.news.comments
data class Comment(
    val id: String,
    val newsId: String,
    val userId: String,
    val userName: String,
    val content: String,
    val createdAt: String,
    val replies: List<Comment> = emptyList()
)
