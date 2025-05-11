package com.example.meta_habit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun LayoutOptions(
    modifier: Modifier = Modifier
){
    Column {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Action to note",
                fontWeight = FontWeight.Medium,
                modifier = modifier.padding(vertical = 8.dp)
                )
        }

        TextButton(onClick = {

        }) {
            Text(text = "Eliminar", modifier = modifier.fillMaxWidth().padding(vertical = 10.dp))
        }

        TextButton(onClick = {

        }) {
            Text(text = "Detalle", modifier = modifier.fillMaxWidth().padding(vertical = 10.dp))
        }

        TextButton(onClick = {

        }) {
            Text(text = "Anclar", modifier = modifier.fillMaxWidth().padding(vertical = 10.dp))
        }
    }
}

@Composable
fun DialogFullScreen(
    onDismiss: () -> Unit,
    onCreate: () -> Unit,
    layout: @Composable () -> Unit,
    modifier: Modifier = Modifier
){
    Dialog (
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ){
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ){
                    IconButton (
                        onClick = { onDismiss() },
                        modifier = modifier.padding()

                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "")
                    }

                    Text(text="Crea tu nota")

                    TextButton(
                        onClick = { onCreate() },
                        modifier = modifier.padding()
                    ) {
                        Text("Crear")
                    }
                }

                layout()
            }
        }
    }
}

@Composable
fun DialogBasic(
    modifier: Modifier = Modifier,
    onSelected: (Int) -> Unit,
    content: @Composable () -> Unit,

    ){
    Dialog(
        onDismissRequest = {  },
    ){
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LayoutOptionsPreview(){
    MaterialTheme {
        Scaffold { innerPadding ->
            DialogBasic(
                modifier = Modifier.padding(innerPadding),
                onSelected = {},
                content = {

                }
            )
        }
    }

}