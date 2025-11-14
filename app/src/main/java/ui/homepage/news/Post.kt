package ui.homepage.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ModeComment
import androidx.compose.material.icons.filled.NotificationsNone
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import com.example.malawipoliceapp.ui.theme.Black
import com.example.malawipoliceapp.ui.theme.primaryColor

// Custom Shape for Post
val PostShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val w = size.width
            val h = size.height

            // Scale factors based on SVG size 349x420
            val scaleX = w / 349f
            val scaleY = h / 420f

            moveTo(349f * scaleX, 347f * scaleY)

            cubicTo(
                349f * scaleX, 358.046f * scaleY,
                340.046f * scaleX, 367f * scaleY,
                329f * scaleX, 367f * scaleY
            )

            lineTo(157f * scaleX, 367f * scaleY)

            cubicTo(
                145.954f * scaleX, 367f * scaleY,
                137f * scaleX, 375.954f * scaleY,
                137f * scaleX, 387f * scaleY
            )

            lineTo(137f * scaleX, 400f * scaleY)

            cubicTo(
                137f * scaleX, 411.046f * scaleY,
                128.046f * scaleX, 420f * scaleY,
                117f * scaleX, 420f * scaleY
            )

            lineTo(20f * scaleX, 420f * scaleY)

            cubicTo(
                8.9543f * scaleX, 420f * scaleY,
                0f * scaleX, 411.046f * scaleY,
                0f * scaleX, 400f * scaleY
            )

            lineTo(0f * scaleX, 20f * scaleY)

            cubicTo(
                0f * scaleX, 8.9543f * scaleY,
                8.9543f * scaleX, 0f * scaleY,
                20f * scaleX, 0f * scaleY
            )

            lineTo(329f * scaleX, 0f * scaleY)

            cubicTo(
                340.046f * scaleX, 0f * scaleY,
                349f * scaleX, 8.9543f * scaleY,
                349f * scaleX, 20f * scaleY
            )

            lineTo(349f * scaleX, 347f * scaleY)

            close()
        }
        return Outline.Generic(path)
    }
}

@Composable
fun PostImage(photoId: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier // Add modifier to outer Box to inherit size constraints
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                //.height(200.dp)
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
                    .width(70.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
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

