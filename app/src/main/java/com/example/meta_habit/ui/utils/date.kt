package com.example.meta_habit.ui.utils


import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getCurrentWeekDays(): List<Date> {
    val calendar = Calendar.getInstance()
    val weekDays = mutableListOf<Date>()

    for (day in Calendar.SUNDAY..Calendar.SATURDAY) {
        calendar.set(Calendar.DAY_OF_WEEK, day)
        weekDays.add(calendar.time)
    }

    return weekDays
}

fun Date.getDayNameFromDate(): String{
    val localDate = this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    return localDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
}

fun Date.getDayNumMonthFromDate(): Int{
    val dayNumMonth = this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    return dayNumMonth.dayOfMonth
}

fun main() {
    val weekDays = getCurrentWeekDays()

    weekDays.forEach { day ->
        println(day.getDayNameFromDate().substring(0, 3))

    }
}






