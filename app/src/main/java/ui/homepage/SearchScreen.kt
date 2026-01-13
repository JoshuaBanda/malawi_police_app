package ui.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.malawipoliceapp.ui.theme.Black

@Composable
fun SearchPageScreen() {   // ✅ Renamed to avoid conflicts

    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 64.dp,
                end = 20.dp,
                start = 20.dp,
                bottom = 0.dp
            )
    ) {

        // --- Search Bar ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF1F1F1)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search...") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Black,
                    unfocusedTextColor = Black,
                    cursorColor = Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search",
                tint = Black,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(26.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // --- Results / Placeholder ---
        if (query.isEmpty()) {
            Text(
                text = "Type something to search...",
                fontSize = 16.sp,
                color = Color.Gray
            )
        } else {
            Text(
                text = "Searching for \"$query\" ...",
                fontSize = 16.sp,
                color = Black
            )
        }
    }
}
