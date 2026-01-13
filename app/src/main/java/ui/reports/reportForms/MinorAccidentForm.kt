package ui.reports.reportForms

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.malawipoliceapp.ui.theme.Black
import com.example.malawipoliceapp.ui.theme.complementoryColor
import com.example.malawipoliceapp.ui.theme.primaryColor
import java.io.File
import java.io.FileOutputStream
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.material3.AlertDialog
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.ui.geometry.Offset




// Vehicle data class
data class Vehicle(
    var driverName: String = "",
    var contact: String = "",
    var licenseNumber: String = "",
    var vehicleType: String = "",
    var numberPlate: String = "",
    var photos: List<Uri> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinorAccident(navController: NavController) {
    var step by remember { mutableStateOf(1) }
    var vehicles by remember { mutableStateOf(listOf(Vehicle())) }
    var accidentDescription by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Report Minor Accident") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = primaryColor
                        )
                    }
                },

            )
        }
    ) { innerPad ->
        Column(
            modifier = Modifier
                .padding(innerPad)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
                //.background(primaryColor)
        ) {
            StepIndicator(step) { clickedStep -> step = clickedStep }

            Spacer(Modifier.height(16.dp))

            when (step) {
                1 -> VehicleDetailsStep(
                    vehicles = vehicles,
                    onVehiclesChange = { vehicles = it }
                )
                2 -> PhotoUploadStep(
                    vehicles = vehicles,
                    onVehiclesChange = { vehicles = it }
                )
                3 -> AccidentDetailsStep(
                    desc = accidentDescription,
                    onDescChange = { accidentDescription = it },
                    onSubmit = {
                        // TODO: Submit all data (vehicles + photos + accidentDescription)
                    }
                )
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (step > 1) {
                    OutlinedButton(
                        onClick = { step-- },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = primaryColor,
                            containerColor = Color.White
                        ),
                        border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(
                            brush = SolidColor(primaryColor)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(0.7f)
                    ) {
                        Text("Back")
                    }
                }

                Spacer(modifier = Modifier.width(24.dp))

                Button(
                    onClick = { if (step < 3) step++ else {/* submit */} },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = complementoryColor,
                        contentColor = primaryColor
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(0.75f)
                ) {
                    Text(if (step < 3) "Next" else "Submit")
                }


                Spacer(modifier = Modifier.height(36.dp))
            }
        }
    }
}

// Step Indicator
@Composable
fun StepIndicator(step: Int, onStepClick: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StepIcon(Icons.Default.DirectionsCar, "Vehicle", step == 1) { onStepClick(1) }
        StepIcon(Icons.Default.CameraAlt, "Photos", step == 2) { onStepClick(2) }
        StepIcon(Icons.Default.Newspaper, "Details", step == 3) { onStepClick(3) }
    }
}

@Composable
fun StepIcon(icon: ImageVector, label: String, active: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = if (active) complementoryColor else primaryColor.copy(alpha = 0.5f),
            modifier = Modifier.size(40.dp)
        )
        Text(label, fontSize = 12.sp, color = primaryColor, textAlign = TextAlign.Center)
    }
}

// Vehicle Details Step supporting multiple vehicles
@Composable
fun VehicleDetailsStep(
    vehicles: List<Vehicle>,
    onVehiclesChange: (List<Vehicle>) -> Unit
) {
    val scroll = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scroll),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        vehicles.forEachIndexed { index, vehicle ->
            Text("Vehicle ${index + 1}", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier= Modifier.height(20.dp))

            Text(
                text = "Driver name",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Black.copy(alpha = 0.5f)
            )
            OutlinedInputField(vehicle.driverName, "Driver Name") {
                val updated = vehicles.toMutableList()
                updated[index] = updated[index].copy(driverName = it)
                onVehiclesChange(updated)
            }

            Text(
                text = "Contact number",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Black.copy(alpha = 0.5f)
            )
            OutlinedInputField(vehicle.contact, "Contact Number", KeyboardType.Phone) {
                val updated = vehicles.toMutableList()
                updated[index] = updated[index].copy(contact = it)
                onVehiclesChange(updated)
            }

            Text(
                text = "Licence number",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Black.copy(alpha = 0.5f)
            )
            OutlinedInputField(vehicle.licenseNumber, "License Number") {
                val updated = vehicles.toMutableList()
                updated[index] = updated[index].copy(licenseNumber = it)
                onVehiclesChange(updated)
            }

            Text(
                text = "Vehicle type",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Black.copy(alpha = 0.5f)
            )

            Text(
                text = "Vehicle type e.g ( Cienta )",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Black.copy(alpha = 0.5f)
            )
            OutlinedInputField(vehicle.vehicleType, "Vehicle Type") {
                val updated = vehicles.toMutableList()
                updated[index] = updated[index].copy(vehicleType = it)
                onVehiclesChange(updated)
            }

            Text(
                text = "Number plate",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Black.copy(alpha = 0.5f)
            )
            OutlinedInputField(vehicle.numberPlate, "Number Plate") {
                val updated = vehicles.toMutableList()
                updated[index] = updated[index].copy(numberPlate = it)
                onVehiclesChange(updated)
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        if (vehicles.size < 3) {
            Button(onClick = { onVehiclesChange(vehicles + Vehicle()) }) {
                Text("Add Another Vehicle")
            }
        }
    }
}


@Composable
fun PhotoUploadStep(
    vehicles: List<Vehicle>,
    onVehiclesChange: (List<Vehicle>) -> Unit
) {
    val scroll = rememberScrollState()
    val context = LocalContext.current

    // fullscreen preview data
    var previewData by remember { mutableStateOf<PreviewData?>(null) }

    // delete confirmation
    var deleteTarget by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    Column(
        modifier = Modifier.verticalScroll(scroll),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        vehicles.forEachIndexed { vehicleIndex, vehicle ->

            Text(
                text = "Vehicle ${vehicleIndex + 1} Photos",
                style = MaterialTheme.typography.titleMedium
            )

            // Camera launcher
            val cameraLauncher =
                rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
                    bitmap?.let {
                        val uri = saveBitmapToCache(context, it)
                        val updated = vehicles.toMutableList()
                        updated[vehicleIndex] = vehicle.copy(photos = vehicle.photos + uri)
                        onVehiclesChange(updated)
                    }
                }

            // 4 PHOTO SLOTS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                (0 until 4).forEach { photoIndex ->
                    val photoUri = vehicle.photos.getOrNull(photoIndex)

                    if (photoUri != null) {
                        // EXISTING PHOTO with delete + fullscreen
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp))
                        ) {

                            AsyncImage(
                                model = photoUri,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        previewData = PreviewData(
                                            photos = vehicle.photos,
                                            startIndex = photoIndex
                                        )
                                    }
                            )

                            Icon(
                                imageVector = Icons.Default.DeleteOutline,
                                contentDescription = "Delete",
                                tint = Color.White,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(6.dp)
                                    .size(22.dp)
                                    .clickable {
                                        deleteTarget = vehicleIndex to photoIndex
                                    }
                            )
                        }
                    } else {
                        // EMPTY SLOT → TAKE PHOTO
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.LightGray.copy(alpha = 0.3f))
                                .clickable { cameraLauncher.launch(null) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Add",
                                tint = Color.Gray,
                                modifier = Modifier.size(35.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // DELETE CONFIRMATION DIALOG
    deleteTarget?.let { (vehicleIndex, photoIndex) ->
        AlertDialog(
            onDismissRequest = { deleteTarget = null },
            title = { Text("Delete Photo?") },
            text = { Text("This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    val updated = vehicles.toMutableList()
                    val newPhotos = updated[vehicleIndex].photos.toMutableList()
                    newPhotos.removeAt(photoIndex)
                    updated[vehicleIndex] = updated[vehicleIndex].copy(photos = newPhotos)
                    onVehiclesChange(updated)
                    deleteTarget = null
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { deleteTarget = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    // FULLSCREEN GALLERY
    previewData?.let {
        FullscreenImageGallery(
            photos = it.photos,
            startIndex = it.startIndex
        ) {
            previewData = null
        }
    }
}

// helper data
data class PreviewData(
    val photos: List<Uri>,
    val startIndex: Int
)

// Save bitmap to cache
fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri {
    val cachePath = File(context.cacheDir, "images")
    cachePath.mkdirs()
    val file = File(cachePath, "img_${System.currentTimeMillis()}.png")
    FileOutputStream(file).use { out -> bitmap.compress(Bitmap.CompressFormat.PNG, 100, out) }
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
}

// Accident Details
@Composable
fun AccidentDetailsStep(desc: String, onDescChange: (String) -> Unit, onSubmit: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Accident Details", style = MaterialTheme.typography.titleMedium)
        OutlinedInputField(
            value = desc,
            placeholder = "Describe what happened",
            singleLine = false,
            onValueChange = onDescChange
        )
    }
}

// Reusable OutlinedInputField
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedInputField(
    value: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = if (singleLine) 56.dp else 120.dp),
        placeholder = { Text(placeholder, fontSize = 14.sp) },
        singleLine = singleLine,
        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = keyboardType,
            imeAction = if (singleLine) ImeAction.Next else ImeAction.Done
        ),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primaryColor,
            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
            cursorColor = primaryColor,
            focusedTextColor = Color.Black
        )
    )
}





@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FullscreenImageGallery(
    photos: List<Uri>,
    startIndex: Int,
    onClose: () -> Unit
) {
    // pagerState must see 'photos'
    val pagerState = rememberPagerState { photos.size } // photos is in scope here

    // scroll to initial page
    LaunchedEffect(startIndex) {
        pagerState.scrollToPage(startIndex)
    }

    Dialog(onDismissRequest = onClose) {
        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->

                val photoUri = photos[page] // photos is available here

                var scale by remember { mutableStateOf(1f) }
                var offset by remember { mutableStateOf(Offset.Zero) }
                val transformableState = rememberTransformableState { zoom, pan, _ ->
                    scale = (scale * zoom).coerceIn(1f, 4f)
                    offset += pan
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = photoUri,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
//                            .graphicsLayer(
//                                scaleX = scale,
//                                scaleY = scale,
//                                translationX = offset.x,
//                                translationY = offset.y
//                            )
                            .transformable(transformableState)
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(36.dp)
                    .clickable { onClose() }
            )
        }
    }
}
