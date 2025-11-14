package ui.homepage.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.malawipoliceapp.ui.theme.Black
import com.example.malawipoliceapp.ui.theme.White
import com.example.malawipoliceapp.ui.theme.complementoryColor
import com.example.malawipoliceapp.ui.theme.primaryColor


@Composable
fun ReportCard(navController: NavController, title: String, navigateTo: String) {
    Box(
        modifier = Modifier
            .height(350.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(350.dp)
                .shadow(1.dp, ReportCardShape) // optional shadow like SVG
                .clip(ReportCardShape)
                .background(White)
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // ✅ Icon placeholder
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f)
                        .clip(RoundedCornerShape(16.dp))
                        .padding(
                            top = 20.dp
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .fillMaxHeight(1f)
                            .clip(RoundedCornerShape(16.dp))
                        //.background(primaryColor.copy(alpha = 0.2f))
                    ) {

                        // Center icon
                        Icon(
                            imageVector = Icons.Default.FamilyRestroom,
                            contentDescription = "Favorite",
                            tint = primaryColor,
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.Center)
                        )

                    }
                }

                Spacer(modifier = Modifier.height(70.dp))

                // ✅ Title placeholder
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(15.dp)
                )


                Spacer(modifier = Modifier.weight(1f))


            }
        }

        Box(
            modifier = Modifier
                .width(80.dp)
                .height(40.dp)
                .offset(x = (-20).dp, y = (-2).dp)
                .align(Alignment.BottomEnd)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .clickable {
                    // Replace with your NavController navigation
                    //navController.navigate(navigateTo)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Open",
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = Color.Black
            )
        }

    }
}













//@Preview(showBackground = true)
//@Composable
//fun Prev(){
//    ReportCard(rememberNavController())
//}