package com.example.meta_habit.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.meta_habit.ui.screen.create.CreateScreen
import com.example.meta_habit.ui.components.CardNoteBasic
import com.example.meta_habit.ui.components.DialogBasic
import com.example.meta_habit.ui.components.LayoutOptionRepeat
import com.example.meta_habit.ui.components.LayoutOptions
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel<HomeViewModel>()
){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val showButtonSheet = remember { mutableStateOf(false) }
    val showDialogCreateNote = remember { mutableStateOf(false) }
    val showDialogOptionRepeat = remember { mutableStateOf(false) }
    val showDialogPicker = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Mis Notas", fontWeight = FontWeight.Medium) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialogCreateNote.value = true
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
    ) { innerPadding ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = innerPadding,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(6){
                CardNoteBasic(
                    onClick = {
                        showButtonSheet.value = true
                    }
                )
            }
        }

        if(showButtonSheet.value){
            ModalBottomSheet(
                onDismissRequest = {
                    println("onDismissRequest bottomSheet")
                    showButtonSheet.value = false },
                sheetState = sheetState
            ) {
                LayoutOptions()
            }
        }

        if(showDialogCreateNote.value){

            CreateScreen(
                sheetState = sheetState,
                onDismiss = { showDialogCreateNote.value = false }
            )

        }


        if(showDialogOptionRepeat.value){
            DialogBasic(
                onSelected = {},
                content= {
                    LayoutOptionRepeat(
                        onSelected = { index ->
                            scope.launch { println("index $index") }
                            showDialogOptionRepeat.value = false
                        }
                    )
                }
            )
        }

        if(showDialogPicker.value){
            DatePickerDialog(
                onDismissRequest = { showDialogPicker.value = false },
                confirmButton = {  }
            ) {
                DatePicker(state = datePickerState)
            }
        }

    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}