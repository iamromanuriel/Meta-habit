package com.example.meta_habit.ui.screen.create

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.twotone.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.meta_habit.ui.components.LayoutCreateCheckList
import com.example.meta_habit.ui.components.LayoutOptionRepeat
import com.example.meta_habit.ui.components.SelectionColor
import com.example.meta_habit.ui.components.TextFieldBasic
import com.example.meta_habit.ui.utils.ColorType
import com.example.meta_habit.ui.utils.LabelTypes
import com.example.meta_habit.ui.utils.RepeatType
import com.example.meta_habit.ui.utils.getReminderDay
import com.example.meta_habit.ui.utils.rememberRestrictedDatePickerState

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateViewModel,
    onDismiss: () -> Unit = {}
) {
    val showDialogPicker = remember { mutableStateOf(false) }

    var stateTitle by remember { mutableStateOf("") }
    var stateDescription by remember { mutableStateOf("") }
    val enableRemember by remember { mutableStateOf(false) }
    val datePickerState = rememberRestrictedDatePickerState()
    val selectedRepeat by viewModel.selectedStateRepeat.collectAsStateWithLifecycle()
    val selectedLabel by viewModel.selectedLabel.collectAsStateWithLifecycle()
    val selectedColor by viewModel.selectedColor.collectAsStateWithLifecycle()
    val stateListTask by viewModel.listTask.collectAsStateWithLifecycle()
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

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "")
                    }
                },
                title = { Text("Crear habito") },
                actions ={
                    TextButton(
                        onClick = {
                            viewModel.onSaveNote(
                                title = stateTitle,
                                enableReminder = enableRemember,
                                millisDate = datePickerState.selectedDateMillis ?: 0,
                                description = stateDescription
                            )}
                    ) {  Text("Crear") }
                }
            )
        }
    ) {
        LayoutCreateDetailNote(
            stateIsRepeat = enableRemember,
            colorSelected = selectedColor,
            listTask = stateListTask,
            stateTitle = stateTitle,
            stateDescription = stateDescription,
            dateReminder = datePickerState,
            stateLabel = selectedLabel,
            stateRepeat = selectedRepeat,
            onChangeTitle = { title -> stateTitle = title },
            onShowDialogPicker = {
                showDialogPicker.value = true
            },
            onSelectedColor = { color ->
                viewModel.onSelectedColor(color)
            },
            onCreatedNewTask = { task ->
                viewModel.onAddNewTaskToList(task)
            },
            onChangeDescription = { text -> stateDescription = text },
            onEditTask = { item, index ->
                viewModel.onEditTask(item, index)
            },
            onRemoveTask = { index ->
                viewModel.onRemoveItemTask(index)
            },
            onChangeRepeat = {},
            onSelectedLabel = { viewModel.onSelectedLabel(it) },
            onSelectedRepeat = { viewModel.onSelectedRepeat(it) },
            modifier = Modifier.padding(it),
        )
    }

    if (showDialogPicker.value) {
        DatePickerDialog(
            onDismissRequest = { showDialogPicker.value = false },
            confirmButton = {

                Button(
                    onClick = {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutCreateDetailNote(
    modifier: Modifier = Modifier,
    stateIsRepeat: Boolean = false,
    colorSelected: ColorType,
    dateReminder: DatePickerState,
    stateTitle: String,
    stateDescription: String,
    listTask: List<String>? = null,
    stateLabel: LabelTypes,
    stateRepeat: RepeatType,
    onShowDialogPicker: () -> Unit,
    onSelectedColor: (ColorType) -> Unit,
    onSelectedLabel: (LabelTypes) -> Unit,
    onSelectedRepeat: (RepeatType) -> Unit,
    onCreatedNewTask: (String) -> Unit,
    onChangeTitle: (String) -> Unit,
    onChangeDescription: (String) -> Unit,
    onEditTask: (String, Int) -> Unit,
    onRemoveTask: (Int) -> Unit,
    onChangeRepeat : (Boolean) -> Unit
) {

    var isCheck by remember { mutableStateOf(stateIsRepeat) }
    var optionsRepeat by remember { mutableStateOf(false) }
    var optionsLabel by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LayoutCreateCheckList(
            onCreateNewTask = onCreatedNewTask,
            onEditTask = onEditTask,
            listTask = listTask,
            onRemoveTask = onRemoveTask,
            content = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Icon(
                        imageVector = Icons.TwoTone.MailOutline,
                        contentDescription = "",
                        modifier = Modifier.size(60.dp),
                        tint = Color.Black.copy(alpha = 0.8F)
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    TextFieldBasic(
                        value = stateTitle,
                        onValueChange = onChangeTitle,
                        isCenterText = true
                    )
                }
                Column {

                    TextButton (
                        onClick = { onShowDialogPicker() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black,
                            disabledContentColor = Color.Blue,
                            disabledContainerColor = Color.Transparent
                        )
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
                                    "Fecha: ",
                                    modifier = Modifier.padding(horizontal = 6.dp),
                                    color = Color.Gray,
                                )

                                dateReminder.selectedDateMillis?.getReminderDay()?.let {
                                    Text(
                                        it,
                                        modifier = Modifier.padding(horizontal = 6.dp)
                                    )
                                }
                            }
                            Row {

                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = ""
                                )
                            }
                        }
                    }

                    TextButton(
                        onClick = { optionsRepeat = optionsRepeat.not() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black,
                            disabledContentColor = Color.Blue,
                            disabledContainerColor = Color.Transparent
                        )
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
                                    "Repetir:",
                                    modifier = Modifier.padding(horizontal = 6.dp),
                                    color = Color.Gray
                                )
                                Text(stateRepeat.value, modifier = Modifier.padding(horizontal = 6.dp))
                            }
                            Row {

                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                    AnimatedVisibility(optionsRepeat) {
                        LayoutOptionRepeat<RepeatType>(
                            modifier = Modifier.padding(start = 16.dp),
                            title = "Repetir",
                            options = RepeatType.entries.toTypedArray(),
                            selected = stateRepeat,
                            onSelected = {
                                //showDialogOptionRepeat.value = false
                                //viewModel.onSelectedRepeat(it)
                                onSelectedRepeat(it)
                                optionsRepeat = false
                            },
                            itemToString = { it.value },
                        )
                    }

                    TextButton(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black,
                            disabledContentColor = Color.Blue,
                            disabledContainerColor = Color.Transparent
                        )
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
                                    "Recordar con notificaciòn: ",
                                    modifier = Modifier.padding(horizontal = 6.dp),
                                    color = Color.Gray
                                )
                            }
                            Row {
                                Switch(
                                    checked = isCheck,
                                    onCheckedChange = {
                                        isCheck = it
                                        onChangeRepeat(it)
                                    }
                                )
                            }
                        }
                    }



                    TextButton(
                        onClick = {
                            optionsLabel = optionsLabel.not()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black,
                            disabledContentColor = Color.Blue,
                            disabledContainerColor = Color.Transparent
                        )
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
                                    "Etiqueta",
                                    modifier = Modifier.padding(horizontal = 6.dp),
                                    color = Color.Gray
                                )

                                Text(
                                    stateLabel.value,
                                    modifier = Modifier.padding(horizontal = 6.dp)
                                )
                            }
                            Row {

                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = ""
                                )
                            }
                        }
                    }

                    AnimatedVisibility(optionsLabel) {
                        LayoutOptionRepeat(
                            title = "Etiquetas",
                            options = LabelTypes.entries.toTypedArray(),
                            selected = stateLabel,
                            modifier = Modifier.padding(start = 16.dp),
                            onSelected = {
                                onSelectedLabel(it)
                                optionsLabel = false
                            },
                            itemToString = { it.value }
                        )
                    }

                    TextFieldBasic(
                        value = stateDescription,
                        onValueChange = onChangeDescription,
                        label = "Descripcion"
                    )

                    SelectionColor(
                        onSelected = { color ->
                            onSelectedColor(color)
                        },
                        stateColorSelected = colorSelected
                    )
                }
            }
        )

    }
}


@Preview(showBackground = true)
@Composable
fun CreateScreen() {

}