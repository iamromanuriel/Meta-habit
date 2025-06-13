package com.example.meta_habit.ui.screen.detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.meta_habit.ui.components.CustomCircularCheckbox
import com.example.meta_habit.ui.components.DialogBasic
import com.example.meta_habit.ui.components.ItemLazyCheck
import com.example.meta_habit.ui.components.ItemListCheckEditable
import com.example.meta_habit.ui.components.LayoutCreateDetailNote
import com.example.meta_habit.ui.components.LayoutOptionRepeat
import com.example.meta_habit.ui.components.ListWeekDays
import com.example.meta_habit.ui.components.TaskEditable
import com.example.meta_habit.ui.components.TextFieldBasic
import com.example.meta_habit.ui.components.TopBarDialogBasic
import com.example.meta_habit.ui.utils.ColorType
import com.example.meta_habit.ui.utils.LabelTypes
import com.example.meta_habit.ui.utils.RepeatType
import com.example.meta_habit.ui.utils.getColorToOrdinalEnum
import com.example.meta_habit.ui.utils.getDayNameFromDate
import com.example.meta_habit.ui.utils.getLabelType
import com.example.meta_habit.ui.utils.getReminderDay
import com.example.meta_habit.ui.utils.getRepeatType
import com.example.meta_habit.ui.utils.rememberRestrictedDatePickerState
import com.example.meta_habit.ui.utils.toDate
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBack: () -> Unit = {},
    viewModel: DetailViewModel = koinViewModel<DetailViewModel>()
){
    val coroutineScope = rememberCoroutineScope()
    var isShowDialogEdit by remember { mutableStateOf(false) }
    var isShowDialogOptionRepeat by remember { mutableStateOf(false) }
    var isShowDialogOptionLabel by remember { mutableStateOf(false) }
    var isShowButtonAdd by remember { mutableStateOf(true) }
    var taskDescription by remember { mutableStateOf("") }

    val snackBarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val stateAction by viewModel.stateAction.collectAsStateWithLifecycle()

    val selectColor by viewModel.selectedColor.collectAsStateWithLifecycle()
    val enableReminder by viewModel.enableReminder.collectAsStateWithLifecycle()
    val selectedRepeat by viewModel.selectedRepeat.collectAsStateWithLifecycle()
    val selectedLabel by viewModel.selectedLabel.collectAsStateWithLifecycle()


    LaunchedEffect(stateAction) {
        stateAction?.let {
            if (it.isSuccess) {
                isShowDialogEdit = false
            } else {
                snackBarHostState.showSnackbar("Error")
            }
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "")
                    }
                },
                actions = {
                    IconButton(onClick = { isShowDialogEdit = true }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "")
                    }
                },
                title = { Text(text = state.habit?.habit?.title ?: "Detalle") }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ){ innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    state.habit?.habit?.dateCreate?.getReminderDay().let {
                        Text(text = "Creacion: ${it}")
                    }

                    state.habit?.habit?.dateUpdate?.getReminderDay().let {
                        Text(text = "Ultima modificacion: ${it}")
                    }

                }
                ListWeekDays(
                    listDaysChecked = state.listDaysChecked,
                )
                Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
            }

            items(state.habit?.task?: emptyList()){ task ->
                TaskEditable(
                    modifier = Modifier.padding(6.dp),
                    habitTask = task,
                    onChangeTaskCheck = { viewModel.onCheckTask(task, it) },
                    onChangeTaskDescription = { viewModel.onEditDescriptionTask(task, it) },
                )

            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = taskDescription,
                        label = { Text("Tareas") },
                        onValueChange = { text -> taskDescription = text },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            viewModel.onAddNewTaskToList(taskDescription)
                            taskDescription = ""
                        }), shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )

                }
                OutlinedButton (
                    onClick = {
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar("Delete habit")
                        }
                    },//viewModel::onDeleteHabit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                    ) {
                    Text(text = "Eliminar")
                }
            }
        }
    }


    if(isShowDialogEdit){
        DialogBasic(
            modifier = Modifier.fillMaxWidth(),
            onDismiss = {
                isShowDialogEdit = false
            },
            content = {
                Card (
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TopBarDialogBasic(
                        onClose = { isShowDialogEdit = false },
                        onDone = viewModel::onConfirmSaveEdit
                    )
                    LayoutCreateDetailNote(
                        stateIsRepeat = enableReminder,
                        colorSelected = selectColor?:ColorType.PURPLE,
                        stateTitle = state.habit?.habit?.title ?: "",
                        stateLabel = selectedLabel?: LabelTypes.WORK, //getLabelType(state.habit?.habit?.tag?:0)?: LabelTypes.WORK,
                        stateRepeat = selectedRepeat?: RepeatType.DAILY, //getRepeatType(state.habit?.habit?.repetition?:0)?: RepeatType.DAILY,
                        stateDescription = "",
                        onShowDialogRepeat = { isShowDialogOptionRepeat = true },
                        onShowDialogPicker = {},
                        onShowDialogLabel = { isShowDialogOptionLabel = true},
                        onSelectedColor = {
                            viewModel.onSelectedColor(it)
                        },
                        onCreatedNewTask = {},
                        onChangeTitle = {},
                        onChangeDescription = {},
                        dateReminder = rememberRestrictedDatePickerState(),
                        onEditTask = { _, _ -> },
                        onRemoveTask = {},
                        onChangeRepeat = viewModel::onEnableReminder
                    )
                }
            }
        )
    }

    if(isShowDialogOptionRepeat){
        DialogBasic(
            onDismiss = {
                isShowDialogOptionRepeat = false
            },
            content = {
                LayoutOptionRepeat(
                    title = "Repetir",
                    options = RepeatType.entries.toTypedArray(),
                    selected = selectedRepeat,
                    onSelected = {
                        viewModel.onSelectedRepeat(it)
                        isShowDialogOptionRepeat = false
                    },
                    itemToString = { it?.value?:"" }
                )
            }
        )
    }

    if(isShowDialogOptionLabel){
        DialogBasic(
            onDismiss = {
                isShowDialogOptionLabel = false
            },
            content = {
                LayoutOptionRepeat<LabelTypes>(
                    title = "Etiqueta",
                    options = LabelTypes.entries.toTypedArray(),
                    selected = selectedLabel ?: LabelTypes.WORK,
                    onSelected = {
                        viewModel.onSelectedLabel(it)
                        isShowDialogOptionLabel = false
                    },
                    itemToString = { it.value }
                )
            }

        )
    }

}
