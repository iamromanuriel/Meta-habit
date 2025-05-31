package com.example.meta_habit.ui.screen.create

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.meta_habit.ui.components.DialogBasic
import com.example.meta_habit.ui.components.DialogFullScreen
import com.example.meta_habit.ui.components.LayoutCreateDetailNote
import com.example.meta_habit.ui.components.LayoutOptionRepeat
import com.example.meta_habit.ui.utils.LabelTypes
import com.example.meta_habit.ui.utils.RepeatType
import com.example.meta_habit.ui.utils.rememberRestrictedDatePickerState
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    viewModel: CreateViewModel,
    onDismiss: () -> Unit = {}
) {
    val showDialogOptionRepeat = remember { mutableStateOf(false) }
    val showDialogOptionLabel = remember { mutableStateOf(false) }
    val showDialogPicker = remember { mutableStateOf(false) }

    var stateTitle by remember { mutableStateOf("") }
    var stateDescription by remember { mutableStateOf("") }
    val enableRemember = remember { mutableStateOf(false) }
    val datePickerState = rememberRestrictedDatePickerState()
    val selectedRepeat = viewModel.selectedStateRepeat.collectAsStateWithLifecycle()
    val selectedLabel = viewModel.selectedLabel.collectAsStateWithLifecycle()
    val selectedColor = viewModel.selectedColor.collectAsStateWithLifecycle()
    val stateListTask = viewModel.listTask.collectAsStateWithLifecycle()
    val scape = rememberCoroutineScope()


    val stateResult = viewModel.result.collectAsStateWithLifecycle()

    LaunchedEffect(stateResult.value) {
        stateResult.value?.let {
            if (it.isSuccess) {
                Log.d("TAG", "CreateScreen: ${it.getOrNull()}")
                onDismiss()
            } else {
                Log.d("TAG", "CreateScreen: ${it.getOrNull()}")
            }
        }
    }

    DialogFullScreen(
        onDismiss = {
            onDismiss()
        },
        onCreate = {
            viewModel.onSaveNote(
                title = stateTitle,
                enableReminder = enableRemember.value,
                description = stateDescription
            )
        },
        layout = {
            LayoutCreateDetailNote(
                stateIsRepeat = enableRemember,
                stateColorSelected = selectedColor,
                listTask = stateListTask.value,
                stateTitle = stateTitle,
                stateDescription = stateDescription,
                dateReminder = datePickerState,
                stateLabel = selectedLabel.value,
                stateRepeat = selectedRepeat.value,
                onChangeTitle = { title -> stateTitle = title },
                onShowDialogRepeat = {
                    showDialogOptionRepeat.value = true
                },
                onShowDialogPicker = {
                    showDialogPicker.value = true
                },
                onShowDialogLabel = {
                    showDialogOptionLabel.value = true
                },
                onSelectedColor = { color ->
                    viewModel.onSelectedColor(color)
                },
                onCreatedNewTask = { task ->
                    viewModel.onAddNewTaskToList(task)
                },
                onChangeDescription = { text -> stateDescription = text }
            )

            if (showDialogOptionRepeat.value) {
                DialogBasic(
                    onSelected = {},
                    content = {
                        LayoutOptionRepeat(
                            title = "Repetir",
                            options = RepeatType.entries.toTypedArray(),
                            selected = selectedRepeat.value,
                            onSelected = {
                                showDialogOptionRepeat.value = false
                                viewModel.onSelectedRepeat(it)
                            },
                            itemToString = { it.value },
                        )
                    }
                )
            }

            if (showDialogOptionLabel.value) {
                DialogBasic(
                    onSelected = {},
                    content = {
                        LayoutOptionRepeat(
                            title = "Etiquetas",
                            options = LabelTypes.entries.toTypedArray(),
                            selected = selectedLabel.value,
                            onSelected = {
                                showDialogOptionLabel.value = false
                                viewModel.onSelectedLabel(it)
                            },
                            itemToString = { it.value }
                        )
                    }
                )
            }



            if (showDialogPicker.value) {
                DatePickerDialog(
                    onDismissRequest = { showDialogPicker.value = false },
                    confirmButton = {

                        Button(
                            onClick = {
                                viewModel.selectDateMillis(datePickerState.selectedDateMillis)
                                showDialogPicker.value = false
                            }
                        ) {
                            Text("Aceptar")
                        }


                    }
                ) {
                    DatePicker(
                        state = datePickerState
                    )
                }
            }
        }
    )

}
