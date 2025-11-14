package ui.homepage.reports

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

object ReportCardShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            // Start at top-right
            moveTo(width * 344f / 348, height * 287f / 358)

            // Top-right curve
            cubicTo(
                width * 344f / 348, height * 298.046f / 358,
                width * 335.046f / 348, height * 307f / 358,
                width * 324f / 348, height * 307f / 358
            )

            // Line left
            lineTo(width * 263f / 348, height * 307f / 358)

            // Right-middle curve
            cubicTo(
                width * 251.954f / 348, height * 307f / 358,
                width * 243f / 348, height * 315.954f / 358,
                width * 243f / 348, height * 327f / 358
            )

            lineTo(width * 243f / 348, height * 332f / 358)

            // Bottom-right curve
            cubicTo(
                width * 243f / 348, height * 343.046f / 358,
                width * 234.046f / 348, height,
                width * 223f / 348, height
            )

            // Bottom line
            lineTo(width * 24f / 348, height)

            // Bottom-left curve
            cubicTo(
                width * 12.954f / 348, height,
                0f, height * 343.046f / 358,
                0f, height * 332f / 358
            )

            lineTo(0f, height * 22f / 358)

            // Top-left curve
            cubicTo(
                0f, height * 10.954f / 358,
                width * 12.954f / 348, 0f,
                width * 24f / 348, 0f
            )

            // Top line
            lineTo(width * 324f / 348, 0f)

            // Top-right curve
            cubicTo(
                width * 335.046f / 348, 0f,
                width * 344f / 348, height * 10.954f / 358,
                width * 344f / 348, height * 22f / 358
            )

            close()
        }

        return Outline.Generic(path)
    }
}
