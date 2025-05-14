package com.example.meta_habit.ui.utils

enum class NoteType{
    DAILY, WEEKLY, CUSTOM, SIMPLE
}

enum class RepeatType (var value: String){
    DAILY("Diario"), WEEKLY("Semanal"), MONTHLY("Mensual"), THREE_DAYS("3 dias")
}

enum class LabelTypes(var value: String){
    LECTURE("Clase"), WORK("Trabajo"), STUDY("Estudio")
}