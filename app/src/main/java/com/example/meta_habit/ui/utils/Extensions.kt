package com.example.meta_habit.ui.utils


import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


/**
 * This remember saved data of today in millis
 * Before to call Date(calendar.timeInMillis)
 * Result :: Fri May 16 00:00:00 CST 2025
 */
@Composable
fun rememberTodayMillis(): Long {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    val millis = calendar.timeInMillis
    return remember { millis }
}


@Composable
fun rememberYesterdayMillis(): Long {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
        add(Calendar.DATE, -1)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return remember {
        calendar.timeInMillis
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberRestrictedDatePickerState(
    minDateMillis: Long = rememberYesterdayMillis(),
    initialSelectedDateMillis: Long? = rememberTodayMillis()
): DatePickerState {
    return rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = initialSelectedDateMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis > minDateMillis
            }
        }
    )
}


fun main() {

    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    println(calendar.timeInMillis)
    println(Date(calendar.timeInMillis))
}