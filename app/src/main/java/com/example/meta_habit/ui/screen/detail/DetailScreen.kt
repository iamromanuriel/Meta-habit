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
import com.example.meta_habit.ui.components.LayoutCreateDetailNote
import com.example.meta_habit.ui.components.ListWeekDays
import com.example.meta_habit.ui.components.TopBarDialogBasic
import com.example.meta_habit.ui.utils.rememberRestrictedDatePickerState
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBack: () -> Unit = {},
    viewModel: DetailViewModel = koinViewModel<DetailViewModel>()
){

    var isShowDialogEdit by remember { mutableStateOf(false) }
    val listTask by viewModel.listTask.collectAsStateWithLifecycle()

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
                title = { Text(text = "Detalle") }
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
                    Text(text = "Creacion: 20 Julio")
                    Text(text = "Ultima modificacion: 3 dias")
                }
                ListWeekDays()
                Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
            }

            items(listTask){ task ->
                ItemLazyCheck(description = task, enabled = true)
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
                        stateIsRepeat = remember { mutableStateOf(false) },
                        stateColorSelected = remember { mutableStateOf(ColorType.Blue) },
                        listTask = emptyList(),
                        stateTitle = "",
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