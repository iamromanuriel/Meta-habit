package com.example.meta_habit.ui.screen.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meta_habit.data.db.model.NotificationDetail
import com.example.meta_habit.data.repository.HabitRepository
import com.example.meta_habit.data.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class SelectedHabitState {
    data object Waiting : SelectedHabitState()
    data object Success : SelectedHabitState()
    data class Error(val message: String) : SelectedHabitState()
}

class NotificationViewModel(
    private val notificationRepository: NotificationRepository,
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val _listNotification = MutableStateFlow<List<NotificationDetail>>(emptyList())
    val stateUi = _listNotification

    private val _selectedHabit = MutableStateFlow<SelectedHabitState>(SelectedHabitState.Waiting)
    val selectedHabit = _selectedHabit

    init {
        viewModelScope.launch {
            notificationRepository.getListNotificationDetail().collect {
                _listNotification.value = it
            }
        }
    }

    fun onSelectHabit(idHabit: Long) {
        viewModelScope.launch {
            _selectedHabit.value = SelectedHabitState.Waiting
            val resultDeferred = async(Dispatchers.IO) { habitRepository.setSelectedHabit(idHabit) }
            val result = resultDeferred.await()

            if (result.isSuccess) {
                _selectedHabit.value = SelectedHabitState.Success
            } else {
                _selectedHabit.value = SelectedHabitState.Error(result.exceptionOrNull()?.message ?: "Error")
            }
        }
    }

    fun makeNotificationSeen(idNotification: Long){
        viewModelScope.launch {
            val makeDeferred = async(Dispatchers.IO) { notificationRepository.makeNotificationSeen(idNotification) }
            val makeResult = makeDeferred.await()

        }
    }
}