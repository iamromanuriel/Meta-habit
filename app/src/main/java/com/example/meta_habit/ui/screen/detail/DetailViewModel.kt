package com.example.meta_habit.ui.screen.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meta_habit.data.db.entity.HabitTaskEntity
import com.example.meta_habit.data.db.entity.HabitWithTasks
import com.example.meta_habit.data.repository.HabitRepository
import com.example.meta_habit.ui.state.HabitScreenState
import com.example.meta_habit.ui.utils.ColorType
import com.example.meta_habit.ui.utils.LabelTypes
import com.example.meta_habit.ui.utils.RepeatType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class DetailViewModel(
    private val habitRepository: HabitRepository
): ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow(null)

    private val _state = MutableStateFlow(HabitScreenState())
    val state : StateFlow<HabitScreenState>
        get() = _state

    init {
        viewModelScope.launch {
            launch {
                habitRepository.getHabitWithTask().collect{ habitWithTask ->
                    _state.value = HabitScreenState( habit = habitWithTask )
                }
            }
        }
    }

    fun onConfirmSaveEdit(){}

    fun onEditTask(){}

    fun onSelectedRepeat(repeatType: RepeatType){
    }

    fun onSelectedLabel(labelType: LabelTypes){
    }

    fun onSelectedColor(color: ColorType){
    }

    fun selectDateMillis(dateMillis: Long?){
    }

    fun onAddNewTaskToList(newTask: String){
    }

    fun onCheckTask(task: HabitTaskEntity, isChecked: Boolean){
        viewModelScope.launch {
            val checkedTaskDeferred = async(Dispatchers.IO){ habitRepository.updateHabitTaskCheck(task, isChecked) }
            val resultCheckTask = checkedTaskDeferred.await()

        }
    }

    fun onEditDescriptionTask(task: HabitTaskEntity, description: String){
        viewModelScope.launch {
            val editDescriptionDeferred = async(Dispatchers.IO){ habitRepository.updateHabitTaskDescription(task, description) }
            val resultEditDescription = editDescriptionDeferred.await()

        }
    }

    fun onDeleteHabit(){
        viewModelScope.launch {
            val deleteHabitDeferred = async(Dispatchers.IO) { habitRepository.deleteHabit() }
            val resultHabitDelete = deleteHabitDeferred.await()

            Log.d("ON-DELETE",resultHabitDelete.toString())
        }
    }

}