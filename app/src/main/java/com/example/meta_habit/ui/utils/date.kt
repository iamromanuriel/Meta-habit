package com.example.meta_habit.ui.utils


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs

/**
 * get list date with actual days of week
 *
 */
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
fun Date.getDayNameFromDate(): String {
    val localDate = this.getLocalDate()

    return localDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
}

@SuppressLint("NewApi")
fun Date.getDayNumMonthFromDate(): Int {
    val dayNumMonth = this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    return dayNumMonth.dayOfMonth
}

@SuppressLint("NewApi")
fun Long.getReminderDay(): String {
    val date = this.toDate()
    val dateZoneLocal = date.toInstant()
        .atZone(ZoneId.systemDefault())

    val months = listOf("Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic")

    return "${dateZoneLocal.dayOfMonth} ${months[dateZoneLocal.monthValue -1]}"
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.getReminderDayLocal(): String {
    val formatter = DateTimeFormatter.ofPattern("d MMM", Locale("es"))
        .withZone(ZoneId.of("UTC"))

    return formatter.format(Instant.ofEpochMilli(this))
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.getReminderTimeDay(): String {
    val date = this.toDate()
    val dateZoneLocal = date.toInstant()
        .atZone(ZoneId.systemDefault())

    return "${dateZoneLocal.hour}:${dateZoneLocal.minute}"
}

fun Long.toDate(): Date {
    return Date(this)
}

fun Calendar.clearTime() {
    this.set(Calendar.HOUR_OF_DAY, 0)
    this.set(Calendar.MINUTE, 0)
    this.set(Calendar.SECOND, 0)
}

/**
 * to get string format Jue 5 de jun
 */
@RequiresApi(Build.VERSION_CODES.O)
fun Date.getDayOfWeekDayMonthMontNameSimple(): String {

    val localDate = this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    return "${localDate.dayOfWeek.dayOfWeekSimple()} ${localDate.dayOfMonth} de ${localDate.month.monthNameSimple()}"
}


/**
 * to get string format name day spanish: Jue
 */
fun DayOfWeek.dayOfWeekSimple() : String{
    val daysOfWeek = listOf("Lun", "Mar", "Mie", "Jue", "Vie", "Sab", "Dom")
    return daysOfWeek[ordinal];
}

/**
 * to get string format name month spanish: Jun
 */
fun Month.monthNameSimple(): String{
    val months = listOf(
        "Ene",
        "Feb",
        "Mar",
        "Abr",
        "May",
        "Jun",
        "Jul",
        "Ago",
        "Sep",
        "Oct",
        "Nov",
        "Dic"
    )

    return months[ordinal]
}


fun currentDate(): Date {
    val calendar = Calendar.getInstance()
    calendar.clearTime()
    return calendar.time
}

@RequiresApi(Build.VERSION_CODES.O)
fun Date.getLocalDate(): LocalDate {
    return this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

@RequiresApi(Build.VERSION_CODES.O)
fun isThreeDaysLater(date: LocalDate, today: LocalDate): Boolean {
    val daysBetween = abs(today.toEpochDay() - date.toEpochDay())
    return daysBetween % 3 == 0L
}

@RequiresApi(Build.VERSION_CODES.O)
fun isValidateDateThreeDays(date: LocalDate): Boolean {
    val today = LocalDate.now()
    if (date == today) return true
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
fun getNextAWeek(baseDate: LocalDate): LocalDate {
    val today = LocalDate.now()
    val dayBetween = today.toEpochDay() - baseDate.toEpochDay()
    val offset = if (dayBetween >= 0) {
        val remainder = dayBetween % 7
        val daysUntilNext = if (remainder == 0L) 7 else 7 - remainder
        daysUntilNext
    } else {
        abs(dayBetween) % 7
    }
    return today.plusDays(offset)
}


@RequiresApi(Build.VERSION_CODES.O)
fun isValidateDateThreeDaysReminder(date: LocalDate, type: RepeatType?): Boolean {
    val today = LocalDate.now()
    if (date == today) return true


    val dateNext = when (type) {
        RepeatType.THREE_DAYS -> getNextThreeDayReminderDate(date)
        RepeatType.WEEKLY -> getNextAWeek(date)
        else -> null
    }


    return (1 until 4).map {
        today.plusDays(it.toLong())
    }
        .contains(dateNext)
}


@RequiresApi(Build.VERSION_CODES.O)
fun main() {

    val date = 1750464000000

    println(date.getReminderDay())
    println(Date(date))


}






