package com.example.meta_habit.ui.screen.create

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.example.meta_habit.ui.components.DialogBasic
import com.example.meta_habit.ui.components.DialogFullScreen
import com.example.meta_habit.ui.components.LayoutCreateDetailNote
import com.example.meta_habit.ui.components.LayoutOptionRepeat
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    viewModel: CreateViewModel = koinViewModel<CreateViewModel>(),
    onDismiss: () -> Unit = {}
) {
    val showDialogOptionRepeat = remember { mutableStateOf(false) }
    val showDialogPicker = remember { mutableStateOf(false) }
    var enableRemember = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)

    DialogFullScreen(
        onDismiss = {
            onDismiss()
        },
        onCreate = {
            onDismiss()
        },
    layout = {
        LayoutCreateDetailNote(
            stateIsRepeat = enableRemember,
            onShowDialogRepeat = {
                showDialogOptionRepeat.value = true
            },
            onShowDialogPicker = {
                showDialogPicker.value = true
            }
        )


        if(showDialogOptionRepeat.value){
            DialogBasic(
                onSelected = {},
                content = {
                    LayoutOptionRepeat(
                        onSelected = {
                            showDialogOptionRepeat.value = false
                        }
                    )
                }
            )
        }

        if(showDialogPicker.value){
            DatePickerDialog(
                onDismissRequest = { showDialogPicker.value = false },
                confirmButton = {
                    Log.d("DATEDialogPicker", datePickerState.selectedDateMillis.toString())
                                }
            ){
                DatePicker(
                    state = datePickerState
                )
            }

        }
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