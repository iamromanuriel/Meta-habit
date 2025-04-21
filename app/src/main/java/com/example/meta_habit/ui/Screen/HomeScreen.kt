package com.example.meta_habit.ui.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.meta_habit.ui.components.CardNoteBasic
import com.example.meta_habit.ui.components.DialogFullScreen
import com.example.meta_habit.ui.components.LayoutCreateDetailNote
import com.example.meta_habit.ui.components.LayoutOptions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val showButtomSheet = remember { mutableStateOf(false) }
    val showDialogCreateNote = remember { mutableStateOf(false) }
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
                        showButtomSheet.value = true
                    }
                )
            }
        }

        if(showButtomSheet.value){
            ModalBottomSheet(
                onDismissRequest = {
                    println("onDismissRequest bottomSheet")
                    showButtomSheet.value = false
                                   },
                sheetState = sheetState
            ) {
                LayoutCreateDetailNote()
            }
        }


        if(showDialogCreateNote.value){
            DialogFullScreen(
                onDismiss = {
                    showDialogCreateNote.value = false
                },
                onCreate = {
                    showDialogCreateNote.value = false
                },
                layout = {
                    LayoutCreateDetailNote()
                }
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}