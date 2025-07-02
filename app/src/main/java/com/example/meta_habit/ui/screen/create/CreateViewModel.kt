package com.example.meta_habit.ui.screen.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meta_habit.data.repository.HabitRepository
import com.example.meta_habit.ui.utils.ColorType
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

    private val _selectedStateRepeat = MutableStateFlow(RepeatType.ONLY_ONE)
    val selectedStateRepeat = _selectedStateRepeat.asStateFlow()

    private val _selectedLabel = MutableStateFlow(LabelTypes.LECTURE)
    val selectedLabel = _selectedLabel.asStateFlow()

    private val _selectedColor = MutableStateFlow(ColorType.Blue)
    val selectedColor = _selectedColor.asStateFlow()

    private val _listTask = MutableStateFlow(listOf<String>())
    val listTask = _listTask.asStateFlow()

    private val _result = MutableStateFlow(null as Result<Unit>?)
    val result = _result.asStateFlow()

    fun onSelectedRepeat(repeatType: RepeatType){
        _selectedStateRepeat.value = repeatType
    }

    fun onSelectedLabel(labelType: LabelTypes){
        _selectedLabel.value = labelType
    }

    fun onSelectedColor(color: ColorType){
        _selectedColor.value = color
    }

    fun onAddNewTaskToList(newTask: String){
        _listTask.value += newTask
    }

    fun onEditTask( taskDescription: String, index: Int,){
        _listTask.value = _listTask.value.toMutableList().apply {
            this[index] = taskDescription
        }
    }

    fun onRemoveItemTask(index: Int){
        _listTask.value = _listTask.value.toMutableList().apply {
            Log.d("REMOVETASKLIST",index.toString())
            this.removeAt(index)
        }
    }


    fun onSaveNote(title: String, enableReminder: Boolean, millisDate: Long, description: String){
        viewModelScope.launch {
            val savedResultHabit = async(Dispatchers.IO){
                habitRepository.onSaveHabit(
                    title = title,
                    repetition = _selectedStateRepeat.value.ordinal,
                    hasReminder = enableReminder,
                    dateReminder = millisDate,
                    description = description,
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
            _result.value = savedResultTask.await()
        }
    }

}