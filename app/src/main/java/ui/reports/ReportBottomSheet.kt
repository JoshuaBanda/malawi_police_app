package ui.reports

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportBottomSheet(
    navController: NavController,
    onDismiss: () -> Unit,
    content: @Composable (NavController) -> Unit // composable lambda
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val backgroundColor = MaterialTheme.colorScheme.surface

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = backgroundColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
        ) {
            content(navController) // call the passed composable
        }
    }
}
