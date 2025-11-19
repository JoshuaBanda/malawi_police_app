package ui.homepage

import WhatsNew
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.malawipoliceapp.ui.theme.Black
import com.example.malawipoliceapp.ui.theme.White
import com.example.malawipoliceapp.ui.theme.complementoryColor
import com.example.malawipoliceapp.ui.theme.mograFontFamily
import com.example.malawipoliceapp.ui.theme.primaryColor
import kotlinx.coroutines.launch

enum class HomeTab { ForYou, WhatsNew }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var selectedTab by remember { mutableStateOf(HomeTab.ForYou) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {
                // Small Top App Bar that disappears when scrolling
                SmallTopAppBar(
                    title = {
                        Text(
                            text = "Malawi Police App",
                            color = primaryColor,
                            fontSize = 20.sp,
                            fontFamily = mograFontFamily
                        )
                    },
                    actions = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { /* Notifications */ }) {
                                Icon(
                                    Icons.Default.NotificationsNone,
                                    contentDescription = "Notifications",
                                    tint = Black
                                )
                            }
                            IconButton(onClick = { /* Menu */ }) {
                                Icon(
                                    Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    tint = Black
                                )
                            }
                        }
                    },
                    scrollBehavior = scrollBehavior
                )

                // Tabs that always stay pinned at the top
                TabRow(
                    selectedTabIndex = selectedTab.ordinal,
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = White,
                    contentColor = primaryColor,
                    divider = {
                        // Empty lambda - this removes the grey divider completely
                    },
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[selectedTab.ordinal])
                                .width(20.dp)
                                .padding(horizontal = 30.dp),
                            height = 2.dp,
                            color = complementoryColor
                        )
                    }
                ) {
                    HomeTab.values().forEachIndexed { index, tab ->
                        Tab(
                            selected = selectedTab == tab,
                            onClick = { selectedTab = tab },
                            text = {
                                Text(
                                    text = when (tab) {
                                        HomeTab.ForYou -> "For You"
                                        HomeTab.WhatsNew -> "What's New"
                                    },
                                    //fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        // Content based on selected tab
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                HomeTab.ForYou -> ForYou(navController)
                HomeTab.WhatsNew -> WhatsNew(navController)
            }
        }
    }
}