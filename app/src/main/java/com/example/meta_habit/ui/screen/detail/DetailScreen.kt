package com.example.meta_habit.ui.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.meta_habit.ui.components.ColorType
import com.example.meta_habit.ui.components.DialogBasic
import com.example.meta_habit.ui.components.ItemLazyCheck
import com.example.meta_habit.ui.components.ItemListCheckEditable
import com.example.meta_habit.ui.components.LayoutCreateDetailNote
import com.example.meta_habit.ui.components.ListWeekDays
import com.example.meta_habit.ui.components.TopBarDialogBasic
import com.example.meta_habit.ui.utils.getDayNameFromDate
import com.example.meta_habit.ui.utils.getReminderDay
import com.example.meta_habit.ui.utils.rememberRestrictedDatePickerState
import com.example.meta_habit.ui.utils.toDate
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBack: () -> Unit = {},
    viewModel: DetailViewModel = koinViewModel<DetailViewModel>()
){

    var isShowDialogEdit by remember { mutableStateOf(false) }
    val habitTask by viewModel.habitWithTask.collectAsStateWithLifecycle()

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                },
                actions = {
                    IconButton(onClick = { isShowDialogEdit = true }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "")
                    }
                },
                title = { Text(text = habitTask?.habit?.title ?: "Detalle") }
            )
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
                    habitTask?.habit?.dateCreate?.getReminderDay().let {
                        Text(text = "Creacion: ${it}")
                    }

                    habitTask?.habit?.dateUpdate?.getReminderDay().let {
                        Text(text = "Ultima modificacion: ${it}")
                    }

                }
                ListWeekDays()
                Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
            }

            items(habitTask?.task?: emptyList()){ task ->
                ItemListCheckEditable(
                    habitTask = task,
                    onChangeTaskCheck = { viewModel.onCheckTask(task, it) },
                    onChangeTaskDescription = { viewModel.onEditDescriptionTask(task, it) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton (
                    onClick = viewModel::onConfirmSaveEdit,
                    modifier = Modifier.fillMaxWidth()
                    ) {
                    Text(text = "Eliminar")
                }
            }
        }
    }


    if(isShowDialogEdit){
        DialogBasic(
            modifier = Modifier.fillMaxWidth(),
            onSelected = {
                isShowDialogEdit = false
            },
            content = {
                Card {
                    TopBarDialogBasic(
                        onClose = { isShowDialogEdit = false },
                        onDone = viewModel::onEditTask
                    )
                    LayoutCreateDetailNote(
                        stateIsRepeat = remember { mutableStateOf(habitTask?.habit?.hasReminder?:false) },
                        stateColorSelected = remember { mutableStateOf(ColorType.Blue) },
                        listTask = emptyList(),
                        stateTitle = habitTask?.habit?.title?:"",
                        stateDescription = "",
                        onShowDialogRepeat = {},
                        onShowDialogPicker = {},
                        onShowDialogLabel = {},
                        onSelectedColor = {},
                        onCreatedNewTask = {},
                        onChangeTitle = {},
                        onChangeDescription = {},
                        dateReminder = rememberRestrictedDatePickerState(),
                    )
                }
            }
        )
    }

}


@Preview
@Composable
fun DetailScreenPreview(){
    DetailScreen()
}