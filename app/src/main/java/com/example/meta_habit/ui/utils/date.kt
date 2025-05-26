package com.example.meta_habit.ui.utils


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs

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
fun Date.getNameMouthSpanish(): String{

    val months = listOf<String>("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
    val daysOfWeek = listOf<String>("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo")

    val localDate = this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    return "${daysOfWeek[localDate.dayOfWeek.ordinal]} ${localDate.dayOfMonth} de ${months[localDate.month.ordinal]}"
}


fun currentDate(): Date{
    val calendar = Calendar.getInstance()
    calendar.clearTime()
    return calendar.time
}

@RequiresApi(Build.VERSION_CODES.O)
fun Date.getLocalDate(): LocalDate{
    return this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

@RequiresApi(Build.VERSION_CODES.O)
fun isThreeDaysLater(date: LocalDate, today: LocalDate): Boolean{
    val daysBetween = abs(today.toEpochDay() - date.toEpochDay())
    return daysBetween % 3 == 0L
}

@RequiresApi(Build.VERSION_CODES.O)
fun isValidateDateThreeDays( date: LocalDate): Boolean{
    val today = LocalDate.now()
    if(date == today) return true
    return isThreeDaysLater(date, today)
}






@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val months = listOf<String>("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
    val daysOfWeek = listOf<String>("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo")

    val localDate = Date().toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val threeDay = localDate

    val isThreeDays = isValidateDateThreeDays(threeDay)

    println(isThreeDays)
}






