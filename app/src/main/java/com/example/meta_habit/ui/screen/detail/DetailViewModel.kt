package com.example.meta_habit.ui.screen.detail

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meta_habit.data.db.entity.HabitTaskEntity
import com.example.meta_habit.data.db.entity.HabitWithTasks
import com.example.meta_habit.data.repository.HabitRepository
import com.example.meta_habit.ui.state.DayIsChecked
import com.example.meta_habit.ui.state.HabitScreenState
import com.example.meta_habit.ui.utils.ColorType
import com.example.meta_habit.ui.utils.LabelTypes
import com.example.meta_habit.ui.utils.RepeatType
import com.example.meta_habit.ui.utils.getColorToOrdinalEnum
import com.example.meta_habit.ui.utils.getCurrentWeekDays
import com.example.meta_habit.ui.utils.getLocalDate
import com.example.meta_habit.ui.utils.getRepeatType
import com.example.meta_habit.ui.utils.toDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
class DetailViewModel(
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _selectedDay = MutableStateFlow<Date?>(null)
    val selectedDay = _selectedDay.asStateFlow()
    private val _selectedColor = MutableStateFlow<ColorType?>(null)
    val selectedColor = _selectedColor.asStateFlow()
    private val _enableReminder = MutableStateFlow(false)
    val enableReminder = _enableReminder.asStateFlow()
    private val _selectedRepeat = MutableStateFlow<RepeatType?>(null)
    val selectedRepeat = _selectedRepeat.asStateFlow()
    private val _selectedLabel = MutableStateFlow<LabelTypes?>(null)
    val selectedLabel = _selectedLabel.asStateFlow()

    private val _savedChangeHabit = MutableStateFlow<Result<Unit>?>(null)
    val stateAction = _savedChangeHabit.asStateFlow()

    private val _deleteHabit = MutableStateFlow<Result<Unit>?>(null)
    val stateDelete = _deleteHabit.asStateFlow()


    private val _state = MutableStateFlow(HabitScreenState())
    val state: StateFlow<HabitScreenState>
        get() = _state

    init {
        viewModelScope.launch {
            launch {
                habitRepository.getHabitWithTask()
                    .prepareDateForUI()
                    .collect { data ->
                        data?.let {
                            _state.value = it.first
                            _selectedColor.value = it.second.first
                            _enableReminder.value = it.second.second
                            _selectedRepeat.value = it.second.third
                        }
                    }
            }

            launch {
                _selectedDay.value = Date()
            }
        }
    }

    fun onConfirmSaveEdit(title: String) {
        viewModelScope.launch {
            val editHabitDeferred = async(Dispatchers.IO) {
                habitRepository.updateHabit(
                    title = title,
                    color = _selectedColor.value ?: ColorType.PURPLE,
                    isReminder = _enableReminder.value,
                    repeatType = _selectedRepeat.value,
                    labelType = _selectedLabel.value,
                )
            }
            val resultEditHabit = editHabitDeferred.await()

            _savedChangeHabit.value = resultEditHabit

            Log.d("ON-EDIT-HABIT", resultEditHabit.toString())
        }
    }


    fun onSelectedRepeat(repeatType: RepeatType?) {
        _selectedRepeat.value = repeatType
    }

    fun onSelectedLabel(labelType: LabelTypes) {
        _selectedLabel.value = labelType
    }

    fun onSelectedColor(color: ColorType) {
        _selectedColor.value = color
        Log.d("ON-EDIT-COLOR", color.toString())
    }

    fun selectDateMillis(dateMillis: Long?) {
    }

    fun onAddNewTaskToList(newTask: String) {
        viewModelScope.launch {
            val addTaskDeferred = async(Dispatchers.IO) { habitRepository.addTaskToHabit(newTask) }
            val resultAddTask = addTaskDeferred.await()
        }
    }

    fun onEnableReminder(isCheck: Boolean) {
        _enableReminder.value = isCheck
    }

    fun onCheckTask(task: HabitTaskEntity, isChecked: Boolean) {
        viewModelScope.launch {
            val checkedTaskDeferred =
                async(Dispatchers.IO) { habitRepository.updateHabitTaskCheck(task, isChecked) }
            val resultCheckTask = checkedTaskDeferred.await()

        }
    }

    fun onEditDescriptionTask(task: HabitTaskEntity, description: String) {
        viewModelScope.launch {
            val editDescriptionDeferred = async(Dispatchers.IO) {
                habitRepository.updateHabitTaskDescription(
                    task,
                    description
                )
            }
            val resultEditDescription = editDescriptionDeferred.await()

        }
    }

    fun onDeleteHabit() {
        viewModelScope.launch {
            val deleteHabitDeferred = async(Dispatchers.IO) { habitRepository.deleteHabit() }
            val resultHabitDelete = deleteHabitDeferred.await()

            _deleteHabit.value = resultHabitDelete
        }
    }


    private fun Flow<HabitWithTasks?>.prepareDateForUI(): Flow<Pair<HabitScreenState, Triple<ColorType?, Boolean, RepeatType?>>?> {
         return this.map { habitWithTask ->
             habitWithTask?.let {
                val color  = (habitWithTask.habit.color ?: 0).getColorToOrdinalEnum()
                val reminder = habitWithTask.habit.hasReminder ?: false
                val repeat = getRepeatType(
                    (habitWithTask.habit.repetition ?: RepeatType.DAILY.ordinal)
                )
                val listDaysChecked = getCurrentWeekDays().map { day ->
                    val isChecked = it.task.any { task ->
                        task.dateCheck?.toDate()?.getLocalDate() == day.getLocalDate() && task.isCheck
                    }
                    DayIsChecked(day, isChecked)
                }

                Pair(
                    HabitScreenState(
                        habit = it,
                        listDaysChecked = listDaysChecked
                    ),
                    Triple(color, reminder, repeat)
                )
            }
        }
    }

}