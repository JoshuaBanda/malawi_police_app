import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.malawipoliceapp.R
import com.example.malawipoliceapp.ui.theme.Black
import ui.homepage.news.PostImage


@Composable
fun WhatsNew(navController: NavController) {

    val posts = listOf(
        R.drawable.police_car,
        R.drawable.welcome_report_police,
        R.drawable.welcome_location_map
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        // Uncomment if TopRecommendations is needed
        // item {
        //     TopRecommendations()
        // }

        items(posts) { photo ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                PostImage(
                    photoId = photo,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(430.dp)
                )
            }
        }
    }
}

// Optional TopRecommendations Composable (refactored, clean)
@Composable
fun TopRecommendations() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Top Recommendations",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left large image
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Image(
                    painter = painterResource(R.drawable.welcome_case_tracking),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Right two stacked small images
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.picture1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                )

                Image(
                    painter = painterResource(R.drawable.picture2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWhatsNew() {
    WhatsNew(navController = rememberNavController())
}
