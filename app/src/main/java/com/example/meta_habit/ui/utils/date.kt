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
fun getNextThreeDayReminderDate(baseDate: LocalDate): LocalDate {
    val today = LocalDate.now()

    val daysBetween = today.toEpochDay() - baseDate.toEpochDay()

    val offset = if (daysBetween >= 0) {
        val remainder = daysBetween % 3
        val daysUntilNext = if (remainder == 0L) 3 else 3 - remainder
        daysUntilNext
    } else {
        abs(daysBetween) % 3
    }

    return today.plusDays(offset)
}


@RequiresApi(Build.VERSION_CODES.O)
fun isValidateDateThreeDaysReminder(date: LocalDate): Boolean{

    val today = LocalDate.now()
    if(date == today) return true

    println("dateRecorder ${date}")

    val countDay =  today.toEpochDay() - date.toEpochDay()

    val dateNext = getNextThreeDayReminderDate(date)

    println("NextDateRecorder ${dateNext}")

    val dateReminder = today.plusDays(countDay)



    println("dateRecorderConverter ${dateReminder}")

    if(!isThreeDaysLater(dateReminder, today)) return false

    return (1 until 4).map {
        today.plusDays(it.toLong()) }.contains(dateReminder)
}


@RequiresApi(Build.VERSION_CODES.O)
fun isValidateDateThreeDaysReminderTest(startDate: LocalDate): Boolean {
    val today = LocalDate.now()

    for (i in 0..2) {
        val dateToCheck = today.plusDays(i.toLong())
        val daysBetween = dateToCheck.toEpochDay() - startDate.toEpochDay()

        if (daysBetween >= 0 && daysBetween % 3 == 0L) {
            return true
        }
    }

    return false
}



@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val today = LocalDate.now()
    val remindersTime = listOf(1747699200000, 1747785600000, 1747872000000, 1747872000000, 1747958400000, 1747872000000, 1747872000000, 1747872000000, 1747872000000, 1747958400000, 1748044800000, 1748304000000)
    val remindersDate = remindersTime.map { it.toDate() }.map { it.getLocalDate() }

    remindersDate.forEach {
        print("fecha :: ${isValidateDateThreeDaysReminder(it)} $it \n")
    }

}






