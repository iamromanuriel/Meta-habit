package com.example.meta_habit.ui.screen.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meta_habit.data.db.entity.NotificationEntity
import com.example.meta_habit.data.repository.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(private val notificationRepository: NotificationRepository): ViewModel() {
    private val _listNotification = MutableStateFlow<List<NotificationEntity>>(emptyList())
    val stateUi = _listNotification

    init {
        viewModelScope.launch {
            notificationRepository.getListNotification().collect{
                _listNotification.value = it
            }
        }
    }
}