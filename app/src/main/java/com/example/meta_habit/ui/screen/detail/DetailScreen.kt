package com.example.meta_habit.ui.screen.detail

import android.os.Build
import android.widget.Toast
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.meta_habit.ui.components.DialogBasic
import com.example.meta_habit.ui.components.LayoutOptionRepeat
import com.example.meta_habit.ui.components.ListWeekDays
import com.example.meta_habit.ui.components.TaskEditable
import com.example.meta_habit.ui.components.TopBarDialogBasic
import com.example.meta_habit.ui.screen.create.LayoutCreateDetailNote
import com.example.meta_habit.ui.theme.bluePrimary
import com.example.meta_habit.ui.utils.ColorType
import com.example.meta_habit.ui.utils.LabelTypes
import com.example.meta_habit.ui.utils.RepeatType
import com.example.meta_habit.ui.utils.getReminderDay
import com.example.meta_habit.ui.utils.rememberRestrictedDatePickerState
import org.koin.compose.viewmodel.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBack: () -> Unit = {},
    viewModel: DetailViewModel = koinViewModel<DetailViewModel>()
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var isShowDialogEdit by remember { mutableStateOf(false) }
    var isShowDialogOptionRepeat by remember { mutableStateOf(false) }
    var isShowDialogOptionLabel by remember { mutableStateOf(false) }
    var isShowDialogDelete by remember { mutableStateOf(false) }
    var taskDescription by remember { mutableStateOf("") }
    var enableNewTask by remember { mutableStateOf(false) }

    val snackBarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val stateAction by viewModel.stateAction.collectAsStateWithLifecycle()
    val stateDelete by viewModel.stateDelete.collectAsStateWithLifecycle()

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

    LaunchedEffect(stateDelete) {
        stateDelete?.let {
            if (it.isSuccess) {
                onBack()
            } else {
                snackBarHostState.showSnackbar(it.exceptionOrNull()?.message ?: "Error")
            }
        }
    }

    LaunchedEffect(state.errorMessage){
        state.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "")
                    }
                },
                actions = {


                    IconButton(onClick = { isShowDialogEdit = true }) {
                        Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = "", tint = Color.Gray)
                    }
                },
                title = { Text(text = state.habit?.habit?.title ?: "Detalle", maxLines = 1) }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            item {

                /*ListWeekDays(
                    listDaysChecked = state.listDaysChecked,
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp))*/

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                ) {
                    state.habit?.habit?.dateUpdate?.getReminderDay().let {
                        Text(text = "Ultima modificacion:", fontWeight = FontWeight.Bold)
                        Text(text = "${it}")
                    }

                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    AnimatedVisibility(!enableNewTask) {
                        Button(
                            onClick = {
                                enableNewTask = enableNewTask.not()
                            },
                            colors = ButtonColors(
                                containerColor = bluePrimary,
                                contentColor = Color.White,
                                disabledContainerColor = Color.Gray,
                                disabledContentColor = Color.White
                            )
                        ) {
                            Row {
                                Icon(imageVector = Icons.Outlined.Add, contentDescription = "")
                                Text(text = "Agregar")
                            }

                        }
                    }

                    AnimatedVisibility(enableNewTask) {
                        val managerFocus = LocalFocusManager.current
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = taskDescription,
                            label = { Text("Tareas") },
                            onValueChange = { text -> taskDescription = text },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = {

                                if(taskDescription.isEmpty()){
                                    managerFocus.clearFocus()
                                    enableNewTask = enableNewTask.not()
                                }else{
                                    viewModel.onAddNewTaskToList(taskDescription)
                                    taskDescription = ""
                                    enableNewTask = enableNewTask.not()
                                }
                            }), shape = RoundedCornerShape(12.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )
                    }


                }

            }

            items(state.habit?.task ?: emptyList()) { task ->
                TaskEditable(
                    habitTask = task,
                    onChangeTaskCheck = { viewModel.onCheckTask(task, it) },
                    onChangeTaskDescription = { viewModel.onEditDescriptionTask(task, it) },
                )

            }


        }
    }

    AnimatedVisibility(
        visible = isShowDialogEdit,
    ) {
        DialogBasic(
            modifier = Modifier.fillMaxWidth(),
            onDismiss = {
                isShowDialogEdit = false
            },
            content = {
                var titleState by remember { mutableStateOf(state.habit?.habit?.title?:"") }
                Card(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                ) {
                    TopBarDialogBasic(
                        onClose = { isShowDialogEdit = false },
                        onDone = { viewModel.onConfirmSaveEdit(titleState) }
                    )
                    LayoutCreateDetailNote(
                        stateIsRepeat = enableReminder,
                        colorSelected = selectColor ?: ColorType.PURPLE,
                        stateTitle = titleState,
                        stateLabel = selectedLabel ?: LabelTypes.WORK,
                        stateRepeat = selectedRepeat ?: RepeatType.DAILY,
                        stateDescription = "",
                        onShowDialogPicker = {},
                        onSelectedColor = viewModel::onSelectedColor,
                        onCreatedNewTask = {},
                        onChangeTitle = { titleState = it },
                        onChangeDescription = {},
                        dateReminder = rememberRestrictedDatePickerState(),
                        onEditTask = { _, _ -> },
                        onRemoveTask = {},
                        onChangeRepeat = viewModel::onEnableReminder,
                        onSelectedLabel = viewModel::onSelectedLabel,
                        onSelectedRepeat = viewModel::onSelectedRepeat
                    )
                }
            }
        )
    }

    if (isShowDialogOptionRepeat) {
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
                    itemToString = { it?.value ?: "" }
                )
            }
        )
    }

    if (isShowDialogOptionLabel) {
        DialogBasic(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
            onDismiss = {
                isShowDialogOptionLabel = false
            },
            content = {
                LayoutOptionRepeat<LabelTypes>(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
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

    if(isShowDialogDelete){
        AlertDialog(
            title = { Text(text = "Eliminar") },
            text = { Text(text = "Estas seguro que deseas eliminar?") },
            onDismissRequest = { isShowDialogDelete = false },
            confirmButton = {
                Button(onClick = viewModel::onDeleteHabit){
                    Text("Eliminar")
                }
            },
            dismissButton = {
                OutlinedButton (onClick = { isShowDialogDelete = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

}
