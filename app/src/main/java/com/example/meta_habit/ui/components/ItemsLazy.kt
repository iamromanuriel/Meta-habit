package com.example.meta_habit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ItemLazyCheck(
    modifier: Modifier = Modifier,
    description: String = "",
    onChangeTask: (String) -> Unit = {},
    enabled: Boolean = false
){
    var isChecked by remember { mutableStateOf(false) }
    var stateDescription by remember { mutableStateOf(description) }

    Row (
        modifier = modifier
    ){
        if(enabled){
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = !isChecked
                }
            )
        }

        TextField(
            modifier = modifier.fillMaxWidth(),
            value = stateDescription,
            onValueChange = {
                stateDescription = it
            },
        )
    }
}

@Composable
fun ItemNotification(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
            .clickable { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(30.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Blue.copy(alpha = 0.2F))
        ){
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "",
                tint = Color.Blue.copy(alpha = 0.5F),
                modifier = Modifier.padding())
        }

        Column(
            modifier = Modifier
                .weight(1F)
                .padding(horizontal = 10.dp, vertical = 12.dp)
        ) {
            Text("Clase de ingles", fontWeight = FontWeight.Bold)
            Text("Tu clase de ingles comienza en 4 horas", style = TextStyle(fontWeight = FontWeight.Light))

        }

        Box(
            modifier = Modifier.size(10.dp)
                .clip(CircleShape)
                .background(Color.Blue)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemLazyCheckPreview(){
    ItemNotification()
}