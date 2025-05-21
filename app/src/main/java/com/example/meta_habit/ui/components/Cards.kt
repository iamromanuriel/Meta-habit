package com.example.meta_habit.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.meta_habit.data.db.entity.HabitEntity
import com.example.meta_habit.ui.utils.getReminderDay

@Composable
fun CardNoteBasic(
    modifier: Modifier = Modifier,
    habit: HabitEntity,
    onClick: () -> Unit = {}
){
    Card(
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, Color.Gray),
        onClick = onClick
    ) {
        Column(modifier = modifier.padding(8.dp)) {
            Text(
                text = habit.title?:"",
                modifier = modifier.padding(vertical = 6.dp),
                fontWeight = FontWeight.Bold,
            )
            Text("1: pollo \n2: Arroz \n3: Verduras")
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "")
                habit.dateReminder?.getReminderDay()?.let { Text(it) }
            }
        }
    }
}

@Preview
@Composable
fun CardNoteBasicPreview(){
    Scaffold { innerPadding ->
        Row (modifier = Modifier.padding(innerPadding)){
            CardNoteBasic(
                habit = HabitEntity(dateUpdate = 0, dateCreate = 0)
            )
        }
    }
}