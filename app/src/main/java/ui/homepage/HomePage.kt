package ui.homepage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.malawipoliceapp.ui.theme.Black
import com.example.malawipoliceapp.ui.theme.White
import com.example.malawipoliceapp.ui.theme.primaryColor
import kotlinx.coroutines.launch
import ui.homepage.search.SearchScreen
import ui.profile.UserProfileScreen
import ui.reports.ReportsMainScreen

// Bottom nav items
data class NavItem(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

@Composable
fun HomePage(navController: NavController) {

    val navItems = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Search", Icons.Default.Search),
        NavItem("Reports", Icons.Default.ModeComment),
        NavItem("Profile", Icons.Default.PersonOutline)
    )

    var bottomNavIndex by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {

        // --- Main content area ---
        Column(modifier = Modifier.fillMaxSize()) {
            when (bottomNavIndex) {
                0 -> HomeContent(navController)       // Home page with tabs
                1 -> SearchScreen(navController)
                2 -> ReportsMainScreen(navController)
                3 -> UserProfileScreen(navController)
            }
        }

        // --- Bottom nav ---
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(55.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-20).dp)
                .clip(RoundedCornerShape(25.dp))
                .background(primaryColor)
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEachIndexed { index, item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            scope.launch { bottomNavIndex = index }
                        }

                        .padding(vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (bottomNavIndex == index) White else White.copy(alpha = 0.4f)
                    )
                    AnimatedVisibility(visible = bottomNavIndex == index) {
//                        Text(
//                            text = item.label,
//                            color = White,
//                            fontSize = 10.sp
//                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReportsScreen() { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Reports Screen", color = primaryColor, fontSize = 24.sp) } }
