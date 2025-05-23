package com.example.meta_habit.ui.utils


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.time.Duration.Companion.days

fun getCurrentWeekDays(): List<Date> {
    val calendar = Calendar.getInstance()
    val weekDays = mutableListOf<Date>()

    for (day in Calendar.SUNDAY..Calendar.SATURDAY) {
        calendar.set(Calendar.DAY_OF_WEEK, day)
        weekDays.add(calendar.time)
    }

    return weekDays
}

@SuppressLint("NewApi")
fun Date.getDayNameFromDate(): String{
    val localDate = this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    return localDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
}

@SuppressLint("NewApi")
fun Date.getDayNumMonthFromDate(): Int{
    val dayNumMonth = this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    return dayNumMonth.dayOfMonth
}

@SuppressLint("NewApi")
fun Long.getReminderDay(): String {
    val  date = this.toDate()
    val dateZoneLocal =  date.toInstant()
        .atZone(ZoneId.systemDefault())

    return "${dateZoneLocal.dayOfMonth} ${dateZoneLocal.month}"
}

fun Long.toDate(): Date{
    return Date(this)
}

fun Calendar.clearTime(){
    this.set(Calendar.HOUR_OF_DAY, 0)
    this.set(Calendar.MINUTE, 0)
    this.set(Calendar.SECOND, 0)
}
@RequiresApi(Build.VERSION_CODES.O)
enum class DayOfWeekSpanish(val day: String? = null, val value: DayOfWeek) {
    LUNES("Lunes", DayOfWeek.MONDAY),
    MARTES("Martes", DayOfWeek.THURSDAY),
    MIERCOLES("Miercoles", DayOfWeek.WEDNESDAY),
    JUEVES("Jueves", DayOfWeek.THURSDAY),
    VIERNES("Viernes", DayOfWeek.FRIDAY),
    SABADO("Sabado", DayOfWeek.SATURDAY),
    DOMINGO("Domingo", DayOfWeek.SUNDAY);
}



fun currentDate(): Date{
    val calendar = Calendar.getInstance()
    calendar.clearTime()
    return calendar.time
}


@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val day = LocalDate.now()
}






