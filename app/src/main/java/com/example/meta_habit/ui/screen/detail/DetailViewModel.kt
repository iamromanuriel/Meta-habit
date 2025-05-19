package com.example.meta_habit.ui.screen.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel: ViewModel() {

    private val _listTask = MutableStateFlow(listOf<String>("Investigar","Estudiar","Go Gym","Agregar ofertas","Asignar"))
    val listTask = _listTask.asStateFlow()

    fun onConfirmSaveEdit(){}

    fun onEditTask(){}
}