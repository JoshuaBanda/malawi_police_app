import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import kotlinx.coroutines.delay
import com.example.malawipoliceapp.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeeklyUpdatesSlideshow() {
    val imagePairs = listOf(
        Pair(R.drawable.police_car, "Community clean-up campaign success!"),
        Pair(R.drawable.picture2, "New safety awareness program launched."),
        Pair(R.drawable.picture1, "Police partners with local schools."),
        Pair(R.drawable.picture2, "Traffic safety week in full swing."),
        Pair(R.drawable.welcome_case_tracking, "New emergency hotline operational.")
    )

    val pagerState = rememberPagerState(pageCount = { imagePairs.size })
    var autoScrollEnabled by remember { mutableStateOf(true) }
    var lastUserInteraction by remember { mutableStateOf(System.currentTimeMillis()) }
    var isAnimating by remember { mutableStateOf(false) }

    // Improved auto-scroll with state management
    LaunchedEffect(pagerState.currentPage, autoScrollEnabled) {
        if (autoScrollEnabled && !isAnimating) {
            delay(30000) // 30 seconds delay

            val timeSinceLastInteraction = System.currentTimeMillis() - lastUserInteraction
            if (timeSinceLastInteraction > 25000) {
                isAnimating = true
                val nextPage = if (pagerState.currentPage < imagePairs.size - 1) {
                    pagerState.currentPage + 1
                } else {
                    0
                }

                try {
                    pagerState.animateScrollToPage(
                        page = nextPage,
                        animationSpec = androidx.compose.animation.core.tween(
                            durationMillis = 500, // Smooth 500ms animation
                            easing = androidx.compose.animation.core.FastOutSlowInEasing
                        )
                    )
                } finally {
                    // Reset animation state after a short delay to ensure smooth transitions
                    delay(100)
                    isAnimating = false
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp))
                .pointerInput(Unit) {
                    detectTapGestures {
                        lastUserInteraction = System.currentTimeMillis()
                    }
                },
            userScrollEnabled = true
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures {
                            lastUserInteraction = System.currentTimeMillis()
                        }
                    }
            ) {
                Image(
                    painter = painterResource(id = imagePairs[page].first),
                    contentDescription = "Weekly Update Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )

                // Dark gradient overlay for text readability
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
                                startY = 300f
                            )
                        )
                )

                Text(
                    text = imagePairs[page].second,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                )
            }
        }

        // Small indicator dots
        Row(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(imagePairs.size) { index ->
                val color = if (pagerState.currentPage == index) Color.Black else Color.Gray
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (pagerState.currentPage == index) 10.dp else 8.dp)
                        .background(color, shape = RoundedCornerShape(50))
                )
            }
        }
    }
}