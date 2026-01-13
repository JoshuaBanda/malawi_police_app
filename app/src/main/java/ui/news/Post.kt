package ui.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ModeComment
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import com.example.malawipoliceapp.ui.theme.Black

// Custom Shape for Post

@Composable
fun LastNewsImage(photoId: Int, modifier: Modifier = Modifier,
                  onCommentClick: () -> Unit = {}) {
    Box(
        modifier = modifier // Add modifier to outer Box to inherit size constraints
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(400.dp)
                .clip(PostShape)
        ) {
            Image(
                painter = painterResource(id = photoId),
                contentDescription = "Post Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Black.copy(alpha = 0.61f), // Dark at bottom
                                Color.Transparent          // Transparent at top
                            ),
                            startY = Float.POSITIVE_INFINITY, // Start from bottom
                            endY = 0f                         // End at top
                        )
                    )
            )
        }

        Row(
            modifier = Modifier
                .width(195.dp)
                .height(50.dp)
                .align(Alignment.BottomEnd),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Like button
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(70.dp), // Increased width to fit content
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "like",
                    tint = Black
                )
                Text(
                    text = "16 K",
                    fontSize = 14.sp, // Slightly smaller font to fit
                    fontWeight = FontWeight.SemiBold,
                    color = Black,
                    modifier = Modifier.padding(start = 4.dp) // Space between icon and text
                )
            }

            // Comment button
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(70.dp).clickable { onCommentClick() },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.ModeComment,
                    contentDescription = "comment",
                    tint = Black
                )
                Text(
                    text = "2 M",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Black,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            // Share button
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(70.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "share",
                    tint = Black
                )
                Text(
                    text = "20",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Black,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

