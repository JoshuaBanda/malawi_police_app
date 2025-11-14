package ui.homepage

import WhatsNew
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.malawipoliceapp.ui.theme.Black
import com.example.malawipoliceapp.ui.theme.White
import com.example.malawipoliceapp.ui.theme.mograFontFamily
import com.example.malawipoliceapp.ui.theme.primaryColor

enum class HomeTab { ForYou, WhatsNew }

@Composable
fun HomeContent(navController: NavController) {

    var selectedTab by remember { mutableStateOf(HomeTab.ForYou) }

    Column(modifier = Modifier.fillMaxSize()) {

        // ✅ Top bar + tabs only in HomeContent
        TopNavBar(
            selectedTab = selectedTab,
            onTabSelected = { tab -> selectedTab = tab }
        )

        // ✅ Crossfade between ForYou and WhatsNew
        Crossfade(targetState = selectedTab) { tab ->
            when (tab) {
                HomeTab.ForYou -> ForYou(navController)
                HomeTab.WhatsNew -> WhatsNew(navController)
            }
        }
    }
}

// --- TopNavBar with Tabs ---
@Composable
fun TopNavBar(selectedTab: HomeTab, onTabSelected: (HomeTab) -> Unit) {
    Column {

        // Title + icons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(White)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Malawi Police App",
                color = primaryColor,
                fontSize = 20.sp,
                fontFamily = mograFontFamily
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.NotificationsNone, contentDescription = "Notifications", tint = Black)
                Spacer(Modifier.width(12.dp))
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Black)
            }
        }

        // Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TabButton("For You", selectedTab == HomeTab.ForYou) { onTabSelected(HomeTab.ForYou) }
            TabButton("What's New", selectedTab == HomeTab.WhatsNew) { onTabSelected(HomeTab.WhatsNew) }
        }
    }
}

// --- TabButton ---
@Composable
fun TabButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current
            ) { onClick() }
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            color = if (selected) primaryColor else Black.copy(alpha = 0.5f),
            fontWeight = if (selected) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal
        )

        Spacer(Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .height(2.dp)
                .width(24.dp)
                .background(if (selected) primaryColor else Color.Transparent)
        )
    }
}
