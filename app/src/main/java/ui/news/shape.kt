package ui.news

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

val PostShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val w = size.width
            val h = size.height

            // Scale factors based on SVG size 349x420
            val scaleX = w / 349f
            val scaleY = h / 420f

            moveTo(349f * scaleX, 347f * scaleY)

            cubicTo(
                349f * scaleX, 358.046f * scaleY,
                340.046f * scaleX, 367f * scaleY,
                329f * scaleX, 367f * scaleY
            )

            lineTo(157f * scaleX, 367f * scaleY)

            cubicTo(
                145.954f * scaleX, 367f * scaleY,
                137f * scaleX, 375.954f * scaleY,
                137f * scaleX, 387f * scaleY
            )

            lineTo(137f * scaleX, 400f * scaleY)

            cubicTo(
                137f * scaleX, 411.046f * scaleY,
                128.046f * scaleX, 420f * scaleY,
                117f * scaleX, 420f * scaleY
            )

            lineTo(20f * scaleX, 420f * scaleY)

            cubicTo(
                8.9543f * scaleX, 420f * scaleY,
                0f * scaleX, 411.046f * scaleY,
                0f * scaleX, 400f * scaleY
            )

            lineTo(0f * scaleX, 20f * scaleY)

            cubicTo(
                0f * scaleX, 8.9543f * scaleY,
                8.9543f * scaleX, 0f * scaleY,
                20f * scaleX, 0f * scaleY
            )

            lineTo(329f * scaleX, 0f * scaleY)

            cubicTo(
                340.046f * scaleX, 0f * scaleY,
                349f * scaleX, 8.9543f * scaleY,
                349f * scaleX, 20f * scaleY
            )

            lineTo(349f * scaleX, 347f * scaleY)

            close()
        }
        return Outline.Generic(path)
    }
}
