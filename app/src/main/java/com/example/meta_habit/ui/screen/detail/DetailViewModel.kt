package com.example.meta_habit.ui.screen.detail

import androidx.lifecycle.ViewModel
import com.example.meta_habit.ui.components.ColorSelection
import com.example.meta_habit.ui.utils.LabelTypes
import com.example.meta_habit.ui.utils.RepeatType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel: ViewModel() {

    private val _listTask = MutableStateFlow(listOf<String>("Investigar","Estudiar","Go Gym","Agregar ofertas","Asignar"))
    val listTask = _listTask.asStateFlow()

    fun onConfirmSaveEdit(){}

    fun onEditTask(){}

    fun onSelectedRepeat(repeatType: RepeatType){
    }

    fun onSelectedLabel(labelType: LabelTypes){
    }

    fun onSelectedColor(color: ColorSelection){
    }

    fun selectDateMillis(dateMillis: Long?){
    }

    fun onAddNewTaskToList(newTask: String){
    }

}