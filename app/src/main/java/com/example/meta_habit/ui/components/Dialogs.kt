package com.example.meta_habit.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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

@Preview
@Composable
fun LayoutOptionsPreview(){
    Scaffold { innerPadding ->
        LayoutOptions(modifier = Modifier.padding(innerPadding))
    }
}