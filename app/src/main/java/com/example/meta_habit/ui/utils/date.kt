package com.example.meta_habit.ui.utils


import android.annotation.SuppressLint
import android.os.Build
import android.text.format.DateFormat
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.time.Duration.Companion.days


@RequiresApi(Build.VERSION_CODES.O)
fun Long.toLocalDateTime(): LocalDateTime {
    return this.toDate()
        .toInstant()
        .atZone(ZoneOffset.UTC)
        .toLocalDateTime()
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toLocalDate(): LocalDate {
    return this.toDate()
        .toInstant()
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
}

fun Long.getNextReminderWeek(): String{
    val calendar = Calendar.getInstance()
    calendar.time = this.toDate()
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val normalizedBaseDateMillis = calendar.timeInMillis


    val now = Date()
    calendar.time = now
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val normalizedNowMillis = calendar.timeInMillis

    val diffMillis = normalizedNowMillis - normalizedBaseDateMillis

    val ONE_DAY_IN_MILLIS = TimeUnit.DAYS.toMillis(1)
    val daysBetween = diffMillis / ONE_DAY_IN_MILLIS

    val format = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

    if(daysBetween <= 7){
        calendar.time = this.toDate()
        return format.format(calendar.time)
    }

    val remainder = daysBetween % 3
    val daysUntilNext = if(remainder == 0L){
        0L
    } else {
        3L
    }

    calendar.time = now
    calendar.add(Calendar.DAY_OF_YEAR, daysUntilNext.toInt())
    return format.format(calendar.time)
}

fun Long.getNextReminderThreeDays(): String{
    val calendar = Calendar.getInstance()
    calendar.time = this.toDate()
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val normalizedBaseDateMillis = calendar.timeInMillis


    val now = Date()
    calendar.time = now
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val normalizedNowMillis = calendar.timeInMillis

    val diffMillis = normalizedNowMillis - normalizedBaseDateMillis

    val ONE_DAY_IN_MILLIS = TimeUnit.DAYS.toMillis(1)
    val daysBetween = diffMillis / ONE_DAY_IN_MILLIS

    val format = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

    if(daysBetween <= 0){
        calendar.time = this.toDate()
        return format.format(calendar.time)
    }

    val remainder = daysBetween % 3
    val daysUntilNext = if(remainder == 0L){
        0L
    } else {
        3L
    }

    calendar.time = now
    calendar.add(Calendar.DAY_OF_YEAR, daysUntilNext.toInt())
    return format.format(calendar.time)
}

/**
 * Get string date format past
 */

fun Long.getAgoTime(): String {
    val date = Date(this)
    val now = Date()

    val diffInMillis = now.time - date.time

    return when {
        diffInMillis < TimeUnit.MINUTES.toMillis(1) -> "Justo ahora"
        diffInMillis < TimeUnit.HOURS.toMillis(1) -> {
            val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
            "Hace $minutes minuto${if (minutes > 1) "s" else ""}"
        }
        diffInMillis < TimeUnit.DAYS.toMillis(1) -> {
            val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
            "Hace $hours hora${if (hours > 1) "s" else ""}"
        }
        else -> {
            val days = TimeUnit.MILLISECONDS.toDays(diffInMillis)
            "Hace $days día${if (days > 1) "s" else ""}"
        }
    }
}

fun Long.getDateDDMMYYYY(): String{
    val date = this.toDate()
    val format = SimpleDateFormat("yyyy/dd/MM/", Locale.getDefault())
    return format.format(date)
}

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

fun Long.getScheduleAt(){
    val date = this.toDate()

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
fun isValidateDateThreeDays(date: LocalDate, threeDays: RepeatType): Boolean {
    val today = LocalDate.now()
    if (date == today) return true
    return isThreeDaysLater(date, today)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getNextThreeDayReminderDate(baseDate: LocalDate): LocalDate {
    val today = LocalDate.now()

    val daysBetween = today.toEpochDay() - baseDate.toEpochDay()
    if (daysBetween <= 0) {
        return baseDate
    }
    val remainder = daysBetween % 3
    val daysUntilNext = if (remainder == 0L) 3 else 3 - remainder

    return today.plusDays(daysUntilNext)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getNextAWeek(baseDate: LocalDate): LocalDate {
    val today = LocalDate.now()

    val dayBetween = today.toEpochDay() - baseDate.toEpochDay()
    if(dayBetween < 0) return baseDate

    val remainder = dayBetween % 7
    val daysUntilNext = if (remainder == 0L) 7 else 7 - remainder

    return today.plusDays(daysUntilNext)
}




@RequiresApi(Build.VERSION_CODES.O)
fun isValidateDateThreeDaysReminder(date: LocalDate, type: RepeatType?): Boolean {
    if(type == RepeatType.DAILY) return true
    val today = LocalDate.now()
    if (date == today) return true


    val dateNext = when (type) {
        RepeatType.THREE_DAYS -> getNextThreeDayReminderDate(date)
        RepeatType.ONLY_ONE -> date
        RepeatType.WEEKLY -> getNextAWeek(date)
        RepeatType.MONTHLY -> nextDayMonth(date)

        else -> null
    }


    return (1 until 4).map {
        today.plusDays(it.toLong())
    }.contains(dateNext)
}

@RequiresApi(Build.VERSION_CODES.O)
fun nextDayMonth(baseDate: LocalDate): LocalDate{
    val today = LocalDate.now()

    val dayBetween = today.toEpochDay() - baseDate.toEpochDay()
    if(dayBetween < 0) return baseDate

    return today.withMonth(today.monthValue +  1)
}




@RequiresApi(Build.VERSION_CODES.O)
fun isValidateDateWeekReminder(date: LocalDate, type: RepeatType?): Boolean {
    if(type == RepeatType.DAILY) return true
    val today = LocalDate.now()

    if (date == today) return true

    val dateNext = when (type) {
        RepeatType.MONTHLY -> nextDayMonth(date)
        RepeatType.THREE_DAYS -> getNextThreeDayReminderDate(date)
        else -> null
    }

    return getCurrentWeekDays().map {
        it.toInstant()
            .atZone(ZoneOffset.UTC)
            .toLocalDate()
    }.contains(dateNext)
}

@RequiresApi(Build.VERSION_CODES.O)
fun isValidateDateTodayReminder(baseDate: LocalDate, type: RepeatType?): Boolean{
    if(type == RepeatType.DAILY) return true
    val  today = LocalDate.now()
    if(baseDate == today) return true

    val dateNext = when(type){
        RepeatType.MONTHLY -> nextDayMonth(baseDate)
        RepeatType.THREE_DAYS -> getNextThreeDayReminderDate(baseDate)
        else -> null
    }

    return today == dateNext
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.getDateReminderThreeDaysString(): String{
    val now = LocalDate.now()
    if(this == now) return "Hoy"
    return getNextThreeDayReminderDate(baseDate = this).toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun main() {

    val date = Date(1754234206845)
    val agoTime = date.time.getAgoTime()
    println(date)
    println(agoTime)
}






