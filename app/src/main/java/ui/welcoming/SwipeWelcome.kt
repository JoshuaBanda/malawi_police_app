@file:OptIn(ExperimentalFoundationApi::class)

package ui.welcoming

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import com.example.malawipoliceapp.R
import com.example.malawipoliceapp.ui.theme.mograFontFamily

@Composable
fun SwipablePagerScreen(navController: NavController) {

    val pages = listOf(
        PageInfo("Malawi Police \nApp", "A professional police service \nfor a safe and secure Malawi.", R.drawable.police_car),
        PageInfo("Crimes and incident \nreporting", "Report incidents and crimes \nin your neighbourhood.", R.drawable.welcome_report_police),
        PageInfo("Case tracking", "Track the progress of your \nsubmitted reports.", R.drawable.welcome_case_tracking),
        PageInfo("Location based \nServices", "Find location of police stations in your area.", R.drawable.welcome_location_map),
        PageInfo("Let's get started", "\ncreate an account", R.drawable.welcome_case_tracking),
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    var isVisible by remember { mutableStateOf(true) }

    val showLogo by remember { derivedStateOf { pagerState.currentPage == 0 } }
    val signUpSignInPrompt by remember { derivedStateOf { pagerState.currentPage == pages.size - 1 } }

    // Animate arrow when user swipes manually
    LaunchedEffect(pagerState.currentPage) {
        isVisible = false
        delay(150)
        isVisible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 25.dp)
    ) {

        // Pager content
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            WelcomeShape(
                info = pages[page],
                showLogo = showLogo,
                signUpSignInPrompt = signUpSignInPrompt,
                navController = navController
            )
        }

        // Bottom bar with animated ">"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .align(Alignment.BottomCenter)
                .offset(x = (-15).dp)
        ) {

            AnimatedVisibility(
                // Hide arrow on last page
                visible = isVisible && pagerState.currentPage < pagerState.pageCount - 1,
                enter = slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(400)
                ),
                exit = fadeOut(animationSpec = tween(0)),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 20.dp)
            ) {

                Text(
                    text = ">",
                    fontFamily = mograFontFamily,
                    fontSize = 54.sp,
                    color = Color.White,
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = LocalIndication.current
                        ) {
                            scope.launch {
                                isVisible = false

                                val nextPage = (pagerState.currentPage + 1)
                                    .coerceAtMost(pagerState.pageCount - 1)

                                pagerState.animateScrollToPage(
                                    page = nextPage,
                                    animationSpec = tween(600)
                                )

                                isVisible = true
                            }
                        }
                )
            }
        }
    }
}
