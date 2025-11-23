package ui.reports.reportForms

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.malawipoliceapp.ui.theme.primaryColor
import java.io.File
import java.io.FileOutputStream

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
    var vehicle by remember { mutableStateOf(Vehicle()) }
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
                }
            )
        }
    ) { innerPad ->
        Column(
            modifier = Modifier
                .padding(innerPad)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            StepIndicator(step)

            Spacer(Modifier.height(16.dp))

            when (step) {
                1 -> VehicleDetailsStep(vehicle) { vehicle = it; step = 2 }

                2 -> PhotoUploadStep(
                    photos = vehicle.photos,
                    onPhotosChange = { updatedPhotos -> vehicle = vehicle.copy(photos = updatedPhotos) },
                    onNext = { step = 3 }
                )

                3 -> AccidentDetailsStep(
                    desc = accidentDescription,
                    onDescChange = { accidentDescription = it },
                    onSubmit = { /* TODO: submit to backend */ }
                )
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (step > 1) {
                    OutlinedButton(onClick = { step-- }) {
                        Text("Back")
                    }
                }
                Button(
                    onClick = { if (step < 3) step++ else {/* submit */} }
                ) {
                    Text(if (step < 3) "Next" else "Submit")
                }
            }
        }
    }
}

@Composable
fun StepIndicator(step: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StepIcon(Icons.Default.DirectionsCar, "Vehicle", active = step == 1)
        StepIcon(Icons.Default.CameraAlt, "Photos", active = step == 2)
        StepIcon(Icons.Default.LocationOn, "Location", active = step == 3)
    }
}

@Composable
fun StepIcon(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, active: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            icon,
            contentDescription = label,
            tint = if (active) primaryColor else Color.Gray,
            modifier = Modifier.size(40.dp)
        )
        Text(label, fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center)
    }
}

@Composable
fun VehicleDetailsStep(vehicle: Vehicle, onNext: (Vehicle) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Vehicle & Driver Details", style = MaterialTheme.typography.titleMedium)
        OutlinedInputField(vehicle.driverName, "Driver Name") { onNext(vehicle.copy(driverName = it)) }
        OutlinedInputField(vehicle.contact, "Contact Number", KeyboardType.Phone) { onNext(vehicle.copy(contact = it)) }
        OutlinedInputField(vehicle.licenseNumber, "License Number") { onNext(vehicle.copy(licenseNumber = it)) }
        OutlinedInputField(vehicle.vehicleType, "Vehicle Type") { onNext(vehicle.copy(vehicleType = it)) }
        OutlinedInputField(vehicle.numberPlate, "Number Plate") { onNext(vehicle.copy(numberPlate = it)) }
    }
}

@Composable
fun PhotoUploadStep(
    photos: List<Uri>,
    onPhotosChange: (List<Uri>) -> Unit,
    onNext: () -> Unit
) {
    val scroll = rememberScrollState()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        bitmap?.let {
            val uri = saveBitmapToCache(context, it)
            onPhotosChange(photos + uri)
        }
    }

    Column(
        modifier = Modifier.verticalScroll(scroll),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Take Accident Photos", style = MaterialTheme.typography.titleMedium)

        Button(onClick = { launcher.launch(null) }) {
            Icon(Icons.Default.CameraAlt, null)
            Spacer(Modifier.width(8.dp))
            Text("Take Photo")
        }

        photos.forEach { uri ->
            AsyncImage(
                model = uri,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri {
    val cachePath = File(context.cacheDir, "images")
    cachePath.mkdirs()
    val file = File(cachePath, "img_${System.currentTimeMillis()}.png")
    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
    }
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
}

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

@Preview
@Composable
fun PreviewWizard() {
    MinorAccident(rememberNavController())
}
