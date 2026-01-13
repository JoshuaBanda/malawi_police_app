package ui.news.comments


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.items




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentBottomSheet(
    navController: NavController,
    onDismiss: () -> Unit,
) {

    val comments = listOf(
    Comment(
        id = "1",
        newsId = "10",
        userId = "101",
        userName = "John Phiri",
        content = "This is very informative!",
        createdAt = "2h ago",
        replies = listOf(
            Comment(
                id = "2",
                newsId = "10",
                userId = "102",
                userName = "Mary Kamanga",
                content = "I agree with you!",
                createdAt = "1h ago"
            )
        )
    ),
    Comment(
        id = "3",
        newsId = "10",
        userId = "103",
        userName = "Peter Banda",
        content = "Police did a good job.",
        createdAt = "4h ago"
    )
    )


    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val backgroundColor = MaterialTheme.colorScheme.surface

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = backgroundColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.65f)
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                items(comments) { comment ->
                    CommentItem(comment, level = 0)
                }
            }
        }
    }
}
