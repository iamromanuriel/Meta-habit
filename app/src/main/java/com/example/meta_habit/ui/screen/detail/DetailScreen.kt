package com.example.meta_habit.ui.screen.detail

import android.annotation.SuppressLint
import android.os.Build
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.meta_habit.R
import com.example.meta_habit.ui.components.DialogBasic
import com.example.meta_habit.ui.components.LayoutOptionRepeat
import com.example.meta_habit.ui.components.ListWeekDays
import com.example.meta_habit.ui.components.SelectionColor
import com.example.meta_habit.ui.components.TaskEditable
import com.example.meta_habit.ui.components.TextFieldSimple
import com.example.meta_habit.ui.components.TopBarDialogBasic
import com.example.meta_habit.ui.screen.create.LayoutCreateDetailNote
import com.example.meta_habit.ui.theme.bluePrimary
import com.example.meta_habit.ui.theme.danger
import com.example.meta_habit.ui.utils.ColorType
import com.example.meta_habit.ui.utils.LabelTypes
import com.example.meta_habit.ui.utils.RepeatType
import com.example.meta_habit.ui.utils.getLabelType
import com.example.meta_habit.ui.utils.getReminderDay
import com.example.meta_habit.ui.utils.getRepeatType
import com.example.meta_habit.ui.utils.rememberRestrictedDatePickerState
import org.koin.compose.viewmodel.koinViewModel

@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBack: () -> Unit = {},
    viewModel: DetailViewModel = koinViewModel<DetailViewModel>()
) {
    val coroutineScope = rememberCoroutineScope()
    val managerFocus = LocalFocusManager.current
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
    var enableReminder by mutableStateOf(state.habit?.habit?.hasReminder?: false)
    val selectedRepeat by viewModel.selectedRepeat.collectAsStateWithLifecycle()
    val selectedLabel by viewModel.selectedLabel.collectAsStateWithLifecycle()

    var titleState by  mutableStateOf(state.habit?.habit?.title?:"")
    var descriptionState by mutableStateOf(state.habit?.habit?.description?:"")


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
                title = { Text(text = "", maxLines = 1) }
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

                Column (
                    modifier = Modifier.padding(horizontal = 10.dp)
                ){
                    TextFieldSimple(
                        value = titleState,
                        textStyle = MaterialTheme.typography.titleLarge,
                        onValueChange = {
                            titleState = it
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        onDone = {
                            viewModel.onEditTitle(titleState)
                            managerFocus.clearFocus()
                        }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                    ) {
                        state.habit?.habit?.dateUpdate?.getReminderDay().let {
                            Text(text = "${it}")
                        }

                    }

                    TextFieldSimple(
                        value = descriptionState,
                        onValueChange = {
                            descriptionState = it
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        onDone = {
                            viewModel.onEditDescription(descriptionState)
                            managerFocus.clearFocus()
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        AnimatedVisibility(!enableNewTask) {
                            Button(
                                modifier = Modifier.padding(horizontal = 10.dp),
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
                                Row (
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Icon(imageVector = Icons.Outlined.Add, contentDescription = "", modifier = Modifier.padding(horizontal = 5.dp))
                                    Text(text = stringResource(R.string.label_add))
                                }

                            }
                        }

                        AnimatedVisibility(enableNewTask) {

                            TextField(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                                value = taskDescription,
                                label = { Text(stringResource(R.string.label_task)) },
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

    if(isShowDialogEdit){
        ModalBottomSheet(
            onDismissRequest = { isShowDialogEdit = false },
            sheetState = rememberModalBottomSheetState()
        ) {
            Column {

                ListWeekDays(listDaysChecked = state.listDaysChecked)

                TextButton(
                    onClick = { isShowDialogOptionRepeat = isShowDialogOptionRepeat.not() },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {

                            Text(
                                stringResource(R.string.label_repeat),
                                modifier = Modifier.padding(horizontal = 6.dp),
                                color = Color.Gray
                            )
                            Text(getRepeatType(state.habit?.habit?.repetition?:0)?.value?:"",  modifier = Modifier.padding(horizontal = 6.dp))
                        }
                        Row {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = ""
                            )
                        }
                    }
                }

                AnimatedVisibility(isShowDialogOptionRepeat) {
                    LayoutOptionRepeat(
                        options = RepeatType.entries.toTypedArray(),
                        selected = getRepeatType(state.habit?.habit?.repetition ?: 0),
                        onSelected = {
                            viewModel.onSelectedRepeat(it)
                            isShowDialogOptionRepeat = false
                        },
                        itemToString = { it?.value?: "" }
                    )
                }

                TextButton(
                    onClick = { isShowDialogOptionLabel = isShowDialogOptionLabel.not() },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {

                            Text(
                                stringResource(R.string.label_labels),
                                modifier = Modifier.padding(horizontal = 6.dp),
                                color = Color.Gray
                            )
                            Text(getLabelType(state.habit?.habit?.tag?:0)?.value?:"",  modifier = Modifier.padding(horizontal = 6.dp))
                        }
                        Row {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = ""
                            )
                        }
                    }
                }

                AnimatedVisibility(isShowDialogOptionLabel) {
                    LayoutOptionRepeat(
                        options = LabelTypes.entries.toTypedArray(),
                        selected = getLabelType(state.habit?.habit?.tag ?: 0),
                        onSelected = {
                            viewModel.onSelectedLabel(it)
                            isShowDialogOptionLabel = false
                        },
                        itemToString = { it?.value?: "" }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {

                        Text(
                            stringResource(R.string.label_notification_remember),
                            modifier = Modifier.padding(start = 20.dp),
                            color = Color.Gray
                        )
                    }
                    Row {
                        Switch(
                            checked = enableReminder,
                            onCheckedChange = {
                                enableReminder = it
                                viewModel.onEnableReminder(it)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = bluePrimary,
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color.LightGray
                            )
                        )
                    }
                }

                SelectionColor(
                    stateColorSelected = selectColor?:ColorType.PURPLE,
                    onSelected = viewModel::onSelectedColor
                )

                TextButton (
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                    onClick = { isShowDialogDelete = true },
                    colors = ButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = danger,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.White
                    )
                ) {
                    Text(text = "Eliminar")
                }
            }
        }

    }


    if(isShowDialogDelete){
        AlertDialog(
            title = { Text(text = stringResource(R.string.label_delete)) },
            text = { Text(text = stringResource(R.string.quest_confirm_delete)) },
            onDismissRequest = { isShowDialogDelete = false },
            confirmButton = {
                Button(onClick = viewModel::onDeleteHabit){
                    Text(stringResource(R.string.option_ok))
                }
            },
            dismissButton = {
                OutlinedButton (onClick = { isShowDialogDelete = false }) {
                    Text(stringResource(R.string.label_cancel))
                }
            }
        )
    }

}
