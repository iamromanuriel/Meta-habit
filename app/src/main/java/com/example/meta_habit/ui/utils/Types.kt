package com.example.meta_habit.ui.utils

import androidx.compose.material3.CardColors
import androidx.compose.ui.graphics.Color
import com.example.meta_habit.ui.theme.BlueLight
import com.example.meta_habit.ui.theme.GreenLight
import com.example.meta_habit.ui.theme.OrangeLight
import com.example.meta_habit.ui.theme.Purple80
import com.example.meta_habit.ui.theme.RedLight

enum class FilterType {
    TODAY, WEEK, TREE_DAYS
}

enum class NoteType{
    DAILY, WEEKLY, CUSTOM, SIMPLE
}

enum class RepeatType (var value: String){
    DAILY("Diario"), WEEKLY("Semanal"), MONTHLY("Mensual"), THREE_DAYS("3 dias")
}

enum class LabelTypes(var value: String){
    LECTURE("Clase"), WORK("Trabajo"), STUDY("Estudio")
}

enum class NotificationTypes(var value: String){
    Remember("Recordatorio"),
    NewFeature("Nueva funcionalidad"),
    TASK_DELAYED("Tarea atrasada")
}


enum class ColorType(val value: Color){
    RED(RedLight),
    ORANGE(OrangeLight),
    GREEN(GreenLight),
    Blue(BlueLight),
    PURPLE(Purple80)
}



fun Int.getColorToOrdinalEnum(): ColorType?{
    return ColorType.entries.find {
        it.ordinal == this
    }
}

fun ColorType.buildColorCard(): CardColors{
    return CardColors(
        containerColor = this.value,
        contentColor = Color.White,
        disabledContainerColor = Color.Gray,
        disabledContentColor = Color.Black
    )
}

fun getRepeatType(ordinal: Int): RepeatType?{
    return RepeatType.entries.find { it.ordinal == ordinal }

}


fun main(){
    val reminderType = getRepeatType(0)
    println(reminderType)
}