package ui.hardware

import android.content.Context
import android.hardware.camera2.CameraManager
import android.view.TextureView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CameraScreen() {
    val context = LocalContext.current
    var hasCamera by remember { mutableStateOf(false) }

    // Check camera availability through HAL
    LaunchedEffect(Unit) {
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        hasCamera = cameraManager.cameraIdList.isNotEmpty()
    }

    if (hasCamera) {
        AndroidView(
            factory = { context ->
                TextureView(context).apply {
                    // This eventually calls into Camera HAL
                }
            }
        )
    }
}