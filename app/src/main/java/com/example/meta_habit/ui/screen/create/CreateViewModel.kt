package com.example.meta_habit.ui.screen.create

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meta_habit.data.repository.HabitRepository
import com.example.meta_habit.ui.components.ColorType
import com.example.meta_habit.ui.utils.LabelTypes
import com.example.meta_habit.ui.utils.RepeatType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateViewModel(
    private val habitRepository: HabitRepository
): ViewModel() {

    private val _selectedStateRepeat = MutableStateFlow(RepeatType.DAILY)
    val selectedStateRepeat = _selectedStateRepeat.asStateFlow()

    private val _selectedLabel = MutableStateFlow(LabelTypes.LECTURE)
    val selectedLabel = _selectedLabel.asStateFlow()

    private val _selectedColor = MutableStateFlow(ColorType.Blue)
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

    fun onSelectedColor(color: ColorType){
        _selectedColor.value = color
    }

    fun selectDateMillis(dateMillis: Long?){
        _selectedDateMillis.value = dateMillis
    }

    fun onAddNewTaskToList(newTask: String){
        _listTask.value += newTask
    }

    fun onSaveNote(title: String, description: String){
        viewModelScope.launch {
            val savedResultHabit = async(Dispatchers.IO){
                habitRepository.onSaveHabit(
                    title = title,
                    repetition = _selectedStateRepeat.value.ordinal,
                    hasReminder = true,
                    dateReminder = _selectedDateMillis.value?:0,
                    tag = _selectedLabel.value.ordinal,
                    color = _selectedColor.value.ordinal
                )
            }

            val habitId = savedResultHabit.await()

            val savedResultTask = async(Dispatchers.IO){
                habitRepository.onSaveHabitTask(
                    idHabit = habitId.getOrNull()?: 0,
                    listTask = _listTask.value
                )
            }

            println("savedResultTask :: ${savedResultTask.await()}")
        }
    }

}