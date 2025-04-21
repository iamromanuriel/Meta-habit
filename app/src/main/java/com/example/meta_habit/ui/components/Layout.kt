package com.example.meta_habit.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LayoutCreateDetailNote(
    modifier: Modifier = Modifier
){
    var text = remember { mutableStateOf("") }
    val brush = remember {
        Brush.linearGradient(
            colors = listOf(Color.Red, Color.Blue)
        )
    }

    Column {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(top = 20.dp)
        ) {
            Icon(imageVector = Icons.Rounded.CheckCircle, contentDescription = "", modifier = modifier.size(60.dp), tint = Color.Green.copy(alpha = 0.3F))
            TextField(
                modifier = modifier.fillMaxWidth(),
                value = text.value,
                label = { Text("Description") },
                onValueChange = { newValue -> text.value = newValue },
                textStyle = TextStyle(brush = brush),
            )
        }
        Card(
            modifier = modifier.padding(10.dp),
            colors = CardColors(
                containerColor = Color.White,
                contentColor = Color.Black,
                disabledContentColor = MaterialTheme.colorScheme.onSecondary,
                disabledContainerColor = MaterialTheme.colorScheme.secondary
            ),
            border = BorderStroke(width = 1.dp, color = Color.Gray)

        ) {
            Column {

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
                            .padding(horizontal = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "")

                            Text("Fecha", modifier = modifier.padding(horizontal = 6.dp), fontWeight = FontWeight.Bold)
                        }
                        Row {
                            Text("09/02", modifier = modifier.padding(horizontal = 6.dp))
                            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "")
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
                            .padding(horizontal = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "")

                            Text("Repetir", modifier = modifier.padding(horizontal = 6.dp), fontWeight = FontWeight.Bold)
                        }
                        Row {
                            Text("Diario", modifier = modifier.padding(horizontal = 6.dp))
                            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "")
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
                            .padding(horizontal = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = "")

                            Text("Recordatorio", modifier = modifier.padding(horizontal = 6.dp), fontWeight = FontWeight.Bold)
                        }
                        Row {
                            Switch(
                                checked = false,
                                onCheckedChange = {}
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
                            .padding(horizontal = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "")

                            Text("Etiqueta", modifier = modifier.padding(horizontal = 6.dp), fontWeight = FontWeight.Bold)
                        }
                        Row {
                            Text("Elementos", modifier = modifier.padding(horizontal = 6.dp))
                            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "")
                        }
                    }
                }

                TextField(
                    modifier = modifier.fillMaxWidth(),
                    value = text.value,
                    label = { Text("Description") },
                    onValueChange = { newValue -> text.value = newValue },
                    textStyle = TextStyle(brush = brush),
                )

                SelectionColor()
            }
        }

        Card(
            colors = CardColors(
                containerColor = Color.White,
                contentColor = Color.Black,
                disabledContentColor = MaterialTheme.colorScheme.onSecondary,
                disabledContainerColor = MaterialTheme.colorScheme.secondary
            ),
            border = BorderStroke(width = 1.dp, color = Color.Gray),
            modifier = modifier.padding(10.dp)
        ){
            LazyColumn {
                 items(5){
                    ItemLazyCheck()
                 }
            }
        }

    }


}

@Preview
@Composable
fun LayoutCreateDetailNotePreview(){
    Scaffold  { innerPadding ->
        LayoutCreateDetailNote(modifier = Modifier.padding(innerPadding))
    }
}