package com.example.meta_habit.ui.screen.create

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.meta_habit.ui.components.ColorSelection
import com.example.meta_habit.ui.utils.LabelTypes
import com.example.meta_habit.ui.utils.RepeatType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateViewModel: ViewModel() {

    private val _selectedStateRepeat = MutableStateFlow(RepeatType.DAILY)
    val selectedStateRepeat = _selectedStateRepeat.asStateFlow()

    private val _selectedLabel = MutableStateFlow(LabelTypes.LECTURE)
    val selectedLabel = _selectedLabel.asStateFlow()

    private val _selectedColor = MutableStateFlow(ColorSelection(Color.Blue, false))
    val selectedColor = _selectedColor.asStateFlow()

    private val _selectedDateMillis = MutableStateFlow(null as Long?)
    val selectedDateMillis = _selectedDateMillis.asStateFlow()

    private val _listTask = MutableStateFlow(listOf<String>())
    val listTask = _listTask.asStateFlow()

    fun onSelectedRepeat(repeatType: RepeatType){
        _selectedStateRepeat.value = repeatType
    }

    fun onSelectedLabel(labelType: LabelTypes){
        _selectedLabel.value = labelType
    }

    fun onSelectedColor(color: ColorSelection){
        _selectedColor.value = color
    }

    fun selectDateMillis(dateMillis: Long?){
        _selectedDateMillis.value = dateMillis
    }

    fun onAddNewTaskToList(newTask: String){
        _listTask.value += newTask
    }

    fun onSaveNote(title: String, description: String){

    }

}