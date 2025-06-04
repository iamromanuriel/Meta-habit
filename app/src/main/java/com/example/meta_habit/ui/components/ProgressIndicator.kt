package com.example.meta_habit.ui.components

import android.content.ClipData.Item
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.meta_habit.ui.utils.getCurrentWeekDays
import com.example.meta_habit.ui.utils.getDayNameFromDate
import com.example.meta_habit.ui.utils.getDayNumMonthFromDate
import java.util.Date

@Composable
fun ItemDay(
    day: Date,
    isToday: Boolean = false
){
    Card(
        modifier = Modifier
            .width(75.dp)
            .height(80.dp)
            .padding(horizontal = 6.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Text(text = day.getDayNumMonthFromDate().toString(), color = if(isToday) Color.Black else Color.Gray, fontWeight = FontWeight.Bold)
            Text(text = day.getDayNameFromDate().substring(0, 3), fontWeight = FontWeight.Bold, color = if(isToday) Color.Black else Color.Gray)
        }
    }
}

@Composable
fun ListWeekDays(){
    LazyRow(
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(items = getCurrentWeekDays()){ day ->
            val isToday = day.getDayNameFromDate() == Date().getDayNameFromDate()
            ItemDay(day, isToday)
        }
    }
}

@Preview
@Composable
fun ItemDayPreview(){
    ItemDay(day = Date())
}

@Preview(showBackground = true)
@Composable
fun ListWeekDaysPreview(){
    ListWeekDays()
}