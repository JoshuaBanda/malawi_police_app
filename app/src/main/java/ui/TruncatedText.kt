package ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun TruncatedText(
    text: String,
    maxChar: Int,
    modifier: Modifier = Modifier,
    textFontWeight: FontWeight= FontWeight.Normal,
    textColor: Color= Color.Gray,
    textFontSize: TextUnit=14.sp
) {
    Text(
        text = if (text.length > maxChar) "${text.take(maxChar)} ..." else text,
        modifier = modifier,
        fontSize = textFontSize,
        color = textColor,
        fontWeight = textFontWeight,
    )
}
