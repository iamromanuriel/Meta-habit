package com.example.meta_habit.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.meta_habit.data.db.entity.HabitEntity
import com.example.meta_habit.data.db.entity.HabitTaskEntity
import com.example.meta_habit.data.db.entity.HabitWithTasks
import com.example.meta_habit.ui.utils.getReminderDay

@Composable
fun CardNoteBasic(
    modifier: Modifier = Modifier,
    habit: HabitWithTasks,
    onClick: () -> Unit = {}
){
    Card(
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, Color.Gray),
        onClick = onClick
    ) {
        Column(modifier = modifier.padding(8.dp)) {
            Text(
                text = habit.habit.title ?: "",
                modifier = modifier.padding(vertical = 6.dp),
                fontWeight = FontWeight.Bold,
            )
            habit.task.take(3).forEach { task ->
                Row(
                    modifier = modifier.padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "", modifier.scale(0.8F))
                    Text(text= task.description)
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "")
                habit.habit.dateReminder?.getReminderDay()?.let { Text(it) }
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
                habit = HabitWithTasks(habit = HabitEntity(dateUpdate = 0, dateCreate = 0), task =  listOf())
            )
        }
    }
}