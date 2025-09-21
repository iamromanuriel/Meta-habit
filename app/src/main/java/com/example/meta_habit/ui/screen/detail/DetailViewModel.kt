package com.example.meta_habit.ui.screen.detail

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meta_habit.data.db.entity.HabitTaskEntity
import com.example.meta_habit.data.db.entity.HabitWithTaskAndLog
import com.example.meta_habit.data.db.entity.HabitWithTasks
import com.example.meta_habit.data.repository.HabitRepository
import com.example.meta_habit.ui.state.DayIsChecked
import com.example.meta_habit.ui.state.HabitScreenState
import com.example.meta_habit.utils.ColorType
import com.example.meta_habit.utils.LabelTypes
import com.example.meta_habit.utils.RepeatType
import com.example.meta_habit.utils.getColorToOrdinalEnum
import com.example.meta_habit.utils.getCurrentWeekDays
import com.example.meta_habit.utils.getLocalDate
import com.example.meta_habit.utils.getRepeatType
import com.example.meta_habit.utils.toDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.serializer
import java.util.Date
import kotlin.math.log

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
                habitRepository.getHabitWithTaskAndLogs()
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

    fun onEditTitle(title: String){
        viewModelScope.launch {
            val editTitleHabitDeferred = async(Dispatchers.IO) {
                habitRepository.editTitleHabit(title)
            }
            val resultEditTitleHabit = editTitleHabitDeferred.await()
        }
    }

    fun onEditDescription(description: String){
        viewModelScope.launch {
            val editDescriptionHabitDeferred = async(Dispatchers.IO) {
                habitRepository.editDescriptionHabit(description)
            }
            val resultEditDescriptionHabit = editDescriptionHabitDeferred.await()
        }
    }


    fun onSelectedRepeat(repeatType: RepeatType?) {
        viewModelScope.launch {
            val selectRepeatDeferred = async(Dispatchers.IO) { habitRepository.editRepeatHabit(repeatType?: RepeatType.DAILY) }
            val resultSelectRepeat = selectRepeatDeferred.await()
            Log.d("onSuccess repeatEdit", resultSelectRepeat.toString())
            _selectedRepeat.value = resultSelectRepeat.getOrNull()
        }

    }

    fun onSelectedLabel(labelType: LabelTypes?) {
        viewModelScope.launch {
            val selectLabelDeferred = async(Dispatchers.IO) { habitRepository.editLabelHabit(labelType?: LabelTypes.WORK) }
            val resultSelectLabel = selectLabelDeferred.await()
            _selectedLabel.value = resultSelectLabel.getOrNull()
        }
    }

    fun onSelectedColor(color: ColorType) {
        viewModelScope.launch {
            val selectColorDeferred = async(Dispatchers.IO) { habitRepository.selectColor(color) }
            val resultSelectColor = selectColorDeferred.await()
            _selectedColor.value = resultSelectColor.getOrNull()
        }
    }

    fun onAddNewTaskToList(newTask: String) {
        viewModelScope.launch {
            val addTaskDeferred = async(Dispatchers.IO) { habitRepository.addTaskToHabit(newTask) }
            val resultAddTask = addTaskDeferred.await()
        }
    }

    fun onEnableReminder(isCheck: Boolean) {
        viewModelScope.launch {
            val enableReminderDeferred = async(Dispatchers.IO) { habitRepository.enableReminderWitNotification(isCheck) }
            val resultEnableReminder = enableReminderDeferred.await()
            _enableReminder.value = resultEnableReminder.getOrNull()?: false
        }
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


    private fun Flow<HabitWithTaskAndLog?>.prepareDateForUI(): Flow<Pair<HabitScreenState, Triple<ColorType?, Boolean, RepeatType?>>?> {
         return this.map { habitWithTask ->
             habitWithTask?.let {
                val color  = (habitWithTask.habit.color ?: 0).getColorToOrdinalEnum()
                val reminder = habitWithTask.habit.hasReminder ?: false
                val repeat = getRepeatType(
                    (habitWithTask.habit.repetition ?: RepeatType.DAILY.ordinal)
                )
                val listDaysChecked = getCurrentWeekDays().map { day ->

                    val isCheckedLog = it.task.flatMap { it.logs }.any { log ->
                        log.date?.toDate()?.getLocalDate() == day.getLocalDate() && log.isCompleted
                    }

                    val isChecked = it.task.any { task ->
                        task.task.dateCheck?.toDate()?.getLocalDate() == day.getLocalDate() && task.task.isCheck
                    }
                    DayIsChecked(day, isChecked || isCheckedLog)
                }

                Pair(
                    HabitScreenState(
                        habit = HabitWithTasks(
                            habit = habitWithTask.habit,
                            task = habitWithTask.task.map { it.task }
                        ),
                        listDaysChecked = listDaysChecked
                    ),
                    Triple(color, reminder, repeat)
                )
            }
        }
    }

}