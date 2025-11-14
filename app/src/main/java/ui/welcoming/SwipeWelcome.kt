@file:OptIn(ExperimentalFoundationApi::class)

package ui.welcoming


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

import com.example.malawipoliceapp.R


@Composable
fun SwipablePagerScreen(navController:NavController) {
    val pages = listOf(
        PageInfo("Malawi Police \nApp", "A professional police service \nfor a safe and secure Malawi.", R.drawable.police_car),
        PageInfo("Crimes and incident \n" +
                "reporting", "Report incidents and crimes \n in your neighbourhood.", R.drawable.welcome_report_police),
        PageInfo("Case tracking", "Track the progress of your \n submitted reports.", R.drawable.welcome_case_tracking),
        PageInfo("Location based \nServices", "Find location of police stations in your area.", R.drawable.welcome_location_map),
        PageInfo("Let's get started","\ncrete an account", R.drawable.welcome_case_tracking),
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    var isVisible by remember { mutableStateOf(true) }

    val showLogo by remember { derivedStateOf { pagerState.currentPage == 0 } }

    val signUpSignInPrompt by remember { derivedStateOf { pagerState.currentPage== 4}}


    Box(modifier = Modifier.fillMaxSize()
        .padding(
            top = 25.dp,
            end = 0.dp,
            start = 0.dp,
            bottom = 0.dp
        )) {
        // Pager content
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            WelcomeShape(
                info = pages[page],
                showLogo = showLogo,
                signUpSignInPrompt=signUpSignInPrompt,
                navController,
            )
        }


        // Bottom bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .align(Alignment.BottomCenter)
        ) {
            // Arrow that fades out on click and slides in after scroll
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(durationMillis = 400)
                ),
                exit = androidx.compose.animation.fadeOut(animationSpec = tween(0)), // instant fade out
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 20.dp)
            ) {
                IconButton(
                    onClick = {
                        scope.launch {
                            // Fade out instantly
                            isVisible = false
                            // Animate pager scroll
                            val nextPage = (pagerState.currentPage + 1).coerceAtMost(pagerState.pageCount - 1)
                            pagerState.animateScrollToPage(
                                page = nextPage,
                                animationSpec = tween(durationMillis = 600)
                            )
                            // Show arrow again (slide in from left)
                            isVisible = true
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}
