package ui.homepage

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ModeComment
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.malawipoliceapp.ui.theme.White
import com.example.malawipoliceapp.ui.theme.primaryColor
import ui.profile.UserProfileScreen
import ui.reports.ReportsMainScreen

data class NavItem(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

@Composable
fun HomePage(navController: NavController) {

    var selectedIndex by remember { mutableStateOf(0) }

    val navItems = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Reports", Icons.Default.ModeComment),
        NavItem("Profile", Icons.Default.PersonOutline)
    )

    Scaffold(
        topBar = {}, // No top bar
        contentWindowInsets = WindowInsets(0), // Remove system insets
        bottomBar = {
            NavigationBar(
                containerColor = primaryColor,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .height(65.dp)
                    .clip(RoundedCornerShape(30.dp))
            ) {
                navItems.forEachIndexed { index, item ->

                    val scale by animateFloatAsState(targetValue = if (selectedIndex == index) 1.2f else 1f)

                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                modifier = Modifier.scale(scale),
                                tint = if (selectedIndex == index) White else White.copy(alpha = 0.4f)
                            )
                        },
                        label = { Text(item.label, fontSize = 11.sp, color = White) },
                        alwaysShowLabel = false,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = White,
                            unselectedIconColor = White.copy(alpha = 0.4f),
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
        ) {
            when (selectedIndex) {
                0 -> HomeContent(navController)
                1 -> ReportsMainScreen(navController)
                2 -> UserProfileScreen(navController)
            }
        }
    }
}
