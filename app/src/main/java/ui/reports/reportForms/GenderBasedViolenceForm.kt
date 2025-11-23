package ui.reports.reportForms

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.malawipoliceapp.ui.theme.Black
import com.example.malawipoliceapp.ui.theme.Gray
import com.example.malawipoliceapp.ui.theme.complementoryColor
import com.example.malawipoliceapp.ui.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderBasedViolenceForm(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )

    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        Screen(modifier = Modifier.padding(paddingValues))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors()
) {
    TopAppBar(
        title = {
            Text(
                text = "Report GBV",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = primaryColor
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = primaryColor
                )
            }
        },
        modifier = modifier,
        colors = colors,
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
    modifier: Modifier = Modifier
) {
    // Applicant section
    var applicantName by remember { mutableStateOf("Joshua Banda") }
    var applicantAddress by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("0885249030") }

    val policeStationList =
        listOf("Area 3 Police", "Lilongwe Police", "Mchinji Police", "Blantyre Police")
    var selectedPoliceStation by remember { mutableStateOf("") }
    var stationExpanded by remember { mutableStateOf(false) }

    // Suspect section
    var suspectName by remember { mutableStateOf("") }
    var suspectPhone by remember { mutableStateOf("") }
    var suspectAddress by remember { mutableStateOf("") }
    var relationship by remember { mutableStateOf("") }

    // Report section
    val applicantTypes = listOf("Self", "Witness")
    var selectedApplicantType by remember { mutableStateOf("") }
    var typeExpanded by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(25.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        SectionTitle("Applicant Details")

        InputHeader("Full Name")
        CustomTextField(
            value = applicantName,
            onValueChange = {},
            placeholder = "Applicant Name",
            readOnly = true
        )

        InputHeader("Phone Number")
        CustomTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            keyboardType = KeyboardType.Phone,
            readOnly = true,
        )

        InputHeader("Address")
        CustomTextField(
            value = applicantAddress,
            onValueChange = { applicantAddress = it }
        )

        InputHeader("Police Station")
        ExposedDropdownMenuBox(
            expanded = stationExpanded,
            onExpandedChange = { stationExpanded = !stationExpanded }
        ) {
            OutlinedTextField(
                value = selectedPoliceStation,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Select police station") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = stationExpanded)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    unfocusedBorderColor = Gray.copy(alpha = 0.5f),
                    cursorColor = primaryColor,
                    focusedTextColor = Black,
                    unfocusedTextColor = Black
                )
            )

            ExposedDropdownMenu(
                expanded = stationExpanded,
                onDismissRequest = { stationExpanded = false }
            ) {
                policeStationList.forEach { station ->
                    DropdownMenuItem(
                        text = { Text(station) },
                        onClick = {
                            selectedPoliceStation = station
                            stationExpanded = false
                        }
                    )
                }
            }
        }

        // Suspect Section
        SectionTitle("Suspect Details")

        InputHeader("Suspect Name")
        CustomTextField(value = suspectName, onValueChange = { suspectName = it })

        InputHeader("Phone Number (optional)")
        CustomTextField(
            value = suspectPhone,
            onValueChange = { suspectPhone = it },
            keyboardType = KeyboardType.Phone
        )

        InputHeader("Address (optional)")
        CustomTextField(value = suspectAddress, onValueChange = { suspectAddress = it })

        InputHeader("Relationship with Suspect")
        CustomTextField(value = relationship, onValueChange = { relationship = it })

        SectionTitle("Report Details")

        InputHeader("Applicant Type")

        ExposedDropdownMenuBox(
            expanded = typeExpanded,
            onExpandedChange = { typeExpanded = !typeExpanded }
        ) {
            OutlinedTextField(
                value = selectedApplicantType,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Select applicant type") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeExpanded)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    unfocusedBorderColor = Gray.copy(alpha = 0.5f),
                    cursorColor = primaryColor,
                    focusedTextColor = Black,
                    unfocusedTextColor = Black
                )
            )

            ExposedDropdownMenu(
                expanded = typeExpanded,
                onDismissRequest = { typeExpanded = false }
            ) {
                applicantTypes.forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type) },
                        onClick = {
                            selectedApplicantType = type
                            typeExpanded = false
                        }
                    )
                }
            }
        }

        InputHeader("Description (max 400 words)")
        OutlinedTextField(
            value = description,
            onValueChange = {
                if (it.split("\\s+".toRegex()).size <= 400) description = it
            },
            placeholder = { Text("Briefly describe what happened...") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = Gray.copy(alpha = 0.5f),
                cursorColor = primaryColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )

        Button(
            onClick = { /* submit */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = complementoryColor)
        ) {
            Text("Submit Report", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        title,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Black
    )
}

@Composable
fun InputHeader(text: String) {
    Text(
        text,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Black
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Enter text",
    readOnly: Boolean = false,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        readOnly = readOnly,
        isError = isError,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 4.dp),
        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),

        placeholder = {
            Text(
                placeholder,
                fontSize = 14.sp,
                color = Gray.copy(alpha = 0.7f)
            )
        },

        shape = RoundedCornerShape(12.dp),

        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),

        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primaryColor,
            unfocusedBorderColor = Gray.copy(alpha = 0.5f),
            errorBorderColor = Color.Red.copy(alpha = 0.5f),
            cursorColor = primaryColor,
            focusedTextColor = Black,
            unfocusedTextColor = Black
        )
    )
}

@Preview
@Composable
fun PreviewForm() {
    GenderBasedViolenceForm(rememberNavController())
}
