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
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Top Recommendations section
//        item {
//            TopRecommendations()
//            Spacer(modifier = Modifier.height(12.dp)
//            )
//        }

        // Posts list
        items(posts) { photo ->
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                PostImage(
                    photoId = photo,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(430.dp)
                        .padding(vertical = 36.dp)
                )
            }
        }
    }
}

//@Composable
//fun TopRecommendations() {
//    Box(modifier=Modifier.height(50.dp).fillMaxWidth(),
//        contentAlignment = Alignment.BottomStart){
//        Text(
//            text="Top recommendations",
//            fontSize = 20.sp,
//            fontWeight = FontWeight.SemiBold,
//            color = Black,
//            modifier = Modifier
//                .padding(horizontal = 16.dp)
//        )
//    }
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(300.dp)
//            .padding(20.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(300.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            // Left image section (covers box completely)
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth(0.5f)
//                    .height(280.dp)
//                    .clip(RoundedCornerShape(20.dp))
//            ) {
//                Image(
//                    painter = painterResource(R.drawable.welcome_case_tracking),
//                    contentDescription = "Recommendation Image",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.fillMaxSize()
//                )
//            }
//
//            Spacer(Modifier.width(12.dp))
//
//            // Right side: two stacked small images
//            Column(
//                verticalArrangement = Arrangement.SpaceEvenly,
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier
//                    .fillMaxWidth(1f)
//                    .height(300.dp)
//            ) {
//                Image(
//                    painter = painterResource(R.drawable.picture1),
//                    contentDescription = "Report police incident",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .fillMaxWidth(0.9f)
//                        .fillMaxHeight(0.5f)
//                        .clip(RoundedCornerShape(20.dp))
//                )
//
//                Image(
//                    painter = painterResource(R.drawable.picture2),
//                    contentDescription = "Location map",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .fillMaxWidth(0.9f)
//                        .fillMaxHeight(0.7f)
//                        .clip(RoundedCornerShape(20.dp))
//                )
//            }
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    WhatsNew(navController = rememberNavController())
}