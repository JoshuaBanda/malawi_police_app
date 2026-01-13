package ui.news.comments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CommentItem(
    comment: Comment,
    level: Int = 0 // indentation level for replies
) {
    Column(
        modifier = Modifier
            .padding(start = (level * 16).dp, top = 8.dp, bottom = 8.dp)
    ) {
        Text(text = comment.userName)
        Text(text = comment.content)
        Text(text = comment.createdAt)

        // Render replies recursively
        comment.replies.forEach { reply ->
            CommentItem(comment = reply, level = level + 1)
        }
    }
}
