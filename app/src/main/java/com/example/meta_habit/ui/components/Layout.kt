package com.example.meta_habit.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.meta_habit.ui.utils.ColorType
import com.example.meta_habit.ui.utils.LabelTypes
import com.example.meta_habit.ui.utils.RepeatType
import com.example.meta_habit.ui.utils.getReminderDay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutCreateDetailNote(
    modifier: Modifier = Modifier,
    stateIsRepeat: MutableState<Boolean>,
    stateColorSelected: State<ColorType>,
    dateReminder: DatePickerState,
    stateTitle: String,
    stateDescription: String,
    listTask: List<String>,
    stateLabel: LabelTypes,
    stateRepeat: RepeatType,
    onShowDialogRepeat: () -> Unit,
    onShowDialogPicker: () -> Unit,
    onShowDialogLabel: () -> Unit,
    onSelectedColor: (ColorType) -> Unit,
    onCreatedNewTask: (String) -> Unit,
    onChangeTitle: (String) -> Unit,
    onChangeDescription: (String) -> Unit
) {


    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LayoutCreateCheckList(
            onCreateNewTask = onCreatedNewTask,
            listTask = listTask,
            content = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Icon(
                        imageVector = Icons.Rounded.CheckCircle,
                        contentDescription = "",
                        modifier = modifier.size(60.dp),
                        tint = Color.Green.copy(alpha = 0.3F)
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
                        modifier = modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black,
                            disabledContentColor = Color.Blue,
                            disabledContainerColor = Color.Transparent
                        )
                    ) {
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row {

                                Text(
                                    "Fecha: ",
                                    modifier = modifier.padding(horizontal = 6.dp),
                                    color = Color.Gray,
                                )

                                dateReminder.selectedDateMillis?.getReminderDay()?.let {
                                    Text(
                                        it,
                                        modifier = modifier.padding(horizontal = 6.dp)
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
                        onClick = { onShowDialogRepeat() },
                        modifier = modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black,
                            disabledContentColor = Color.Blue,
                            disabledContainerColor = Color.Transparent
                        )
                    ) {
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row {

                                Text(
                                    "Repetir:",
                                    modifier = modifier.padding(horizontal = 6.dp),
                                    color = Color.Gray
                                )
                                Text(stateRepeat.value, modifier = modifier.padding(horizontal = 6.dp))
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
                        onClick = {},
                        modifier = modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black,
                            disabledContentColor = Color.Blue,
                            disabledContainerColor = Color.Transparent
                        )
                    ) {
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row {

                                Text(
                                    "Recordatorio: ",
                                    modifier = modifier.padding(horizontal = 6.dp),
                                    color = Color.Gray
                                )
                            }
                            Row {
                                Switch(
                                    checked = stateIsRepeat.value,
                                    onCheckedChange = {
                                        stateIsRepeat.value = it
                                    }
                                )
                            }
                        }
                    }

                    TextButton(
                        onClick = {
                            onShowDialogLabel()
                        },
                        modifier = modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black,
                            disabledContentColor = Color.Blue,
                            disabledContainerColor = Color.Transparent
                        )
                    ) {
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row {

                                Text(
                                    "Etiqueta",
                                    modifier = modifier.padding(horizontal = 6.dp),
                                    color = Color.Gray
                                )

                                Text(
                                    stateLabel.value,
                                    modifier = modifier.padding(horizontal = 6.dp)
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

                    TextFieldBasic(
                        value = stateDescription,
                        onValueChange = onChangeDescription,
                        label = "Descripcion"
                    )

                    SelectionColor(
                        onSelected = { color ->
                            onSelectedColor(color)
                        },
                        stateColorSelected = stateColorSelected
                    )
                }
            }
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutCreateCheckList(
    modifier: Modifier = Modifier,
    listTask: List<String> = emptyList(),
    onCreateNewTask: (String) -> Unit,
    content: @Composable () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    var taskDescription by remember { mutableStateOf("") }
    LazyColumn {
        item {

            content()
            TextField(
                modifier = modifier.fillMaxWidth(),
                value = taskDescription,
                label = { Text("Tareas") },
                onValueChange = { text -> taskDescription = text },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    onCreateNewTask(taskDescription)
                    taskDescription = ""
                }), shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = modifier.height(20.dp))
        }

        items(listTask) {
            ItemLazyCheck(description = it)
        }
    }
}


val optionRepeat = listOf<String>("Diario", "Semanal", "Mensual", "3 dias")

@Composable
fun <T> LayoutOptionRepeat(
    modifier: Modifier = Modifier,
    title: String,
    options: Array<T>,
    selected: T,
    onSelected: (T) -> Unit,
    itemToString: (T) -> String
) {
    val selectedState = remember { mutableStateOf(selected) }

    Card {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier.padding(10.dp)
            )
            LazyColumn {
                items(options) { item ->
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = itemToString(item), fontWeight = FontWeight.Medium)
                        RadioButton(
                            selected = selectedState.value == item,
                            onClick = { onSelected(item) }
                        )
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun LayoutOptionRepeatPreview() {
    Scaffold { innerPadding ->
        LayoutOptionRepeat(
            modifier = Modifier.padding(innerPadding),
            title = "Etiquetas",
            options = LabelTypes.entries.toTypedArray(),
            selected = LabelTypes.WORK,
            onSelected = {},
            itemToString = { it.value }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LayoutCreateDetailNotePreview() {
    Scaffold { innerPadding ->
        LayoutCreateDetailNote(
            modifier = Modifier.padding(innerPadding),
            stateIsRepeat = remember { mutableStateOf(false) },
            stateColorSelected = remember { mutableStateOf(ColorType.PURPLE) },
            dateReminder = rememberDatePickerState(),
            listTask = optionRepeat,
            stateTitle = "",
            stateDescription = "",
            stateLabel = LabelTypes.WORK,
            stateRepeat = RepeatType.DAILY,
            onShowDialogRepeat = {},
            onShowDialogPicker = {},
            onShowDialogLabel = {},
            onSelectedColor = {},
            onCreatedNewTask = {},
            onChangeTitle = {},
            onChangeDescription = {}
        )
    }
}
