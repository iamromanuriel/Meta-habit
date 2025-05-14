package com.example.meta_habit.ui.screen.create

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.meta_habit.ui.utils.LabelTypes
import com.example.meta_habit.ui.utils.RepeatType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateViewModel: ViewModel() {

    private val _selectedStateRepeat = MutableStateFlow(RepeatType.DAILY)
    val selectedStateRepeat = _selectedStateRepeat.asStateFlow()

    private val _selectedLabel = MutableStateFlow(LabelTypes.LECTURE)
    val selectedLabel = _selectedLabel.asStateFlow()

    fun onSelectedRepeat(repeatType: RepeatType){
        _selectedStateRepeat.value = repeatType
    }

    fun onSelectedLabel(labelType: LabelTypes){
        _selectedLabel.value = labelType

    }
}