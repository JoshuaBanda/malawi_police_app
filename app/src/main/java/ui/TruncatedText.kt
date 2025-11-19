package ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun TruncatedText(
    text: String,
    maxChar: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = if (text.length > maxChar) "${text.take(maxChar)} ..." else text,
        modifier = modifier,
        fontSize = 14.sp,
        color = Color.Gray
    )
}
