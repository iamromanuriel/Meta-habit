package com.example.meta_habit.ui.Screen.create

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.meta_habit.ui.components.DialogFullScreen
import com.example.meta_habit.ui.components.LayoutCreateDetailNote
import com.example.meta_habit.ui.components.LayoutOptions
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    viewModel: CreateViewModel = koinViewModel()
) {
    val showDialogOptionRepeat = remember { mutableStateOf(false) }
    val showDialogPicker = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)

    DialogFullScreen(
        onDismiss = {  },
        onCreate = {  },
    layout = {
        LayoutCreateDetailNote(
            onShowDialogRepeat = {  },
            onShowDialogPicker = {  }
        )
    }
    )

}



@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CreateScreenPreview(){
    Scaffold { innerPadding ->
        CreateScreen(
            modifier = Modifier.padding(innerPadding),
            sheetState = rememberModalBottomSheetState(),

        )
    }
}