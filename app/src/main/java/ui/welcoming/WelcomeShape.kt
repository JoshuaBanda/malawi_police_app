package ui.welcoming

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.malawipoliceapp.R
import com.example.malawipoliceapp.ui.theme.MalawiPoliceAppTheme
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Brush
import com.example.malawipoliceapp.ui.theme.baseColor
import com.example.malawipoliceapp.ui.theme.mograFontFamily
import androidx.compose.ui.unit.sp
import com.example.malawipoliceapp.ui.theme.complementoryColor
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.malawipoliceapp.ui.theme.primaryColor


@Composable
fun WelcomeShape(info: PageInfo, showLogo: Boolean, signUpSignInPrompt: Boolean,navController:NavController) {
    MalawiPoliceAppTheme {

        WelcomeScreenWithLayout(
            info = info,
            showLogo,
            signUpSignInPrompt,
            navController
        )

    }
}

data class PageInfo(val title: String, val body: String, val photo: Int)

@Composable
fun WelcomeScreenWithLayout(info: PageInfo, showLogo: Boolean, signUpSignInPrompt: Boolean,navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        if (!signUpSignInPrompt) {

            ImageClippedByShape(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                photoId = info.photo
            )
            BrandSection(info.title, info.body, showLogo)
        }else{
         SignUpSignIn(navController)
        }
    }
}

// Custom Shape class
private val customShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val width = size.width
            val height = size.height

            // Starting point - equivalent to M379 813
            moveTo(width, height * 813f / 843f)

            // First curve: C379 829.569 365.569 843 349 843
            cubicTo(
                x1 = width,
                y1 = height * 829.569f / 843f,
                x2 = width * 365.569f / 379f,
                y2 = height,
                x3 = width * 349f / 379f,
                y3 = height
            )

            // Line to: H335.397
            lineTo(width * 335.397f / 379f, height)

            // Second curve: C318.829 843 305.397 829.569 305.397 813
            cubicTo(
                x1 = width * 318.829f / 379f,
                y1 = height,
                x2 = width * 305.397f / 379f,
                y2 = height * 829.569f / 843f,
                x3 = width * 305.397f / 379f,
                y3 = height * 813f / 843f
            )

            // Line to: V798
            lineTo(width * 305.397f / 379f, height * 798f / 843f)

            // Third curve: C305.397 781.431 291.966 768 275.397 768
            cubicTo(
                x1 = width * 305.397f / 379f,
                y1 = height * 781.431f / 843f,
                x2 = width * 291.966f / 379f,
                y2 = height * 768f / 843f,
                x3 = width * 275.397f / 379f,
                y3 = height * 768f / 843f
            )

            // Line to: H30
            lineTo(width * 30f / 379f, height * 768f / 843f)

            // Fourth curve: C13.4315 768 0 754.569 0 738
            cubicTo(
                x1 = width * 13.4315f / 379f,
                y1 = height * 768f / 843f,
                x2 = 0f,
                y2 = height * 754.569f / 843f,
                x3 = 0f,
                y3 = height * 738f / 843f
            )

            // Line to: V30
            lineTo(0f, height * 30f / 843f)

            // Fifth curve: C0 13.4315 13.4315 0 30 0
            cubicTo(
                x1 = 0f,
                y1 = height * 13.4315f / 843f,
                x2 = width * 13.4315f / 379f,
                y2 = 0f,
                x3 = width * 30f / 379f,
                y3 = 0f
            )

            // Line to: H349
            lineTo(width * 349f / 379f, 0f)

            // Sixth curve: C365.569 0 379 13.4315 379 30
            cubicTo(
                x1 = width * 365.569f / 379f,
                y1 = 0f,
                x2 = width,
                y2 = height * 13.4315f / 843f,
                x3 = width,
                y3 = height * 30f / 843f
            )

            // Close back to starting point: V813Z
            lineTo(width, height * 813f / 843f)

            close()
        }
        return Outline.Generic(path)
    }
}

@Composable
fun ImageClippedByShape(
    modifier: Modifier = Modifier,
    translationX: Float = 0f,
    translationY: Float = 0f,
    photoId: Int,
) {
    Box(
        modifier = modifier
            .clip(customShape) // Clip applied to the Box
            .fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = photoId),
            contentDescription = "Clipped background image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    this.translationX = translationX
                    this.translationY = translationY
                }
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            primaryColor.copy(alpha = 0.9f) // 50% opacity
                        )
                    )
                )
        )
    }

}

@Composable
fun BrandSection(title: String, body: String, showLogo: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (showLogo) {
            Image(
                painter = painterResource(R.drawable.police_logo),
                contentDescription = "logo",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally) // only the image is centered
            )

            Spacer(modifier = Modifier.height(20.dp))

        }
        // Text aligned to start (left)
        Text(
            text = title,
            fontFamily = mograFontFamily,
            fontSize = 36.sp,
            color = baseColor,
            lineHeight = 36.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = body,
            fontSize = 16.sp,
            color = complementoryColor,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 16.dp)

        )
    }

}