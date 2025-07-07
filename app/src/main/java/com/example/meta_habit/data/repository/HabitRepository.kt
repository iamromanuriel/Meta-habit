package com.example.meta_habit.data.repository

import com.example.meta_habit.data.db.AppDatabase
import com.example.meta_habit.data.db.entity.HabitEntity
import com.example.meta_habit.data.db.entity.HabitTaskEntity
import com.example.meta_habit.data.db.entity.HabitWithTaskAndLog
import com.example.meta_habit.data.db.entity.HabitWithTasks
import com.example.meta_habit.ui.utils.ColorType
import com.example.meta_habit.ui.utils.LabelTypes
import com.example.meta_habit.ui.utils.RepeatType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

class HabitRepository(
    private val appDatabase: AppDatabase
) {

    private val selectedHabit = MutableStateFlow<HabitEntity?>(null)

    fun setSelectedHabit(habit: HabitEntity){
        selectedHabit.value = habit
    }

    fun getSelectedHabit(): Flow<HabitEntity?>{
        return selectedHabit.asStateFlow()
    }

    suspend fun onSaveHabit(
        title: String,
        repetition: Int,
        hasReminder: Boolean,
        description: String,
        dateReminder: Long,
        tag: Int,
        color: Int

    ): Result<Long> {
        return try {
            val habitEntity = HabitEntity(
                title = title,
                repetition = repetition,
                hasReminder = false,
                dateReminder = dateReminder,
                description = description,
                tag = tag,
                color = color,
                dateCreate = Date().time,
                dateUpdate = Date().time
            )
            val idHabit = appDatabase.habitDao().insertGetId(habitEntity)
            Result.success(idHabit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun onSaveHabitTask(idHabit: Long, listTask: List<String>): Result<Unit>{
        return try {
            listTask.forEach { descriptionTask ->
                val habitTaskEntity = HabitTaskEntity(
                    habitId = idHabit,
                    description = descriptionTask
                )
                appDatabase.habitTaskDao().insertGetId(habitTaskEntity)
            }
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    fun getLIsListOfHabit(): Flow<List<HabitEntity>>{
        return appDatabase.habitDao().getListOfHabit()
    }

    fun getListOfHabitWithTasks(): Flow<List<HabitWithTasks>>{
        return appDatabase.habitDao().getListOfHabitWithTasksFlow()
    }

    suspend fun deleteHabit(habit: HabitEntity): Result<Unit>{
        return try {
            appDatabase.habitDao().deleteHabit(habit)
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun deleteHabit(): Result<Unit>{
        return try {
            if(selectedHabit.value == null){
                Result.failure(Exception("Habit not selected"))
            }else{
                appDatabase.habitDao().deleteHabit(habitEntity = selectedHabit.value!!)
                Result.success(Unit)
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun addTaskToHabit(task: String): Result<Unit>{
        return try {
            val habitTaskEntity = HabitTaskEntity(
                habitId = selectedHabit.value?.id?: 0,
                description = task
            )
            appDatabase.habitTaskDao().insertGetId(habitTaskEntity)
            Result.success(Unit)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun updateHabitToPint(habit: HabitEntity): Result<Unit>{
        return try {
            appDatabase.habitDao().updateHabit(habit)
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    fun getHabitWithTask(): Flow<HabitWithTasks?>{
        return appDatabase.habitDao().getHabitWithTask(selectedHabit.value?.id ?: 0)
    }

    fun getHabitWithTaskAndLogs(): Flow<HabitWithTaskAndLog?>{
        return appDatabase.habitDao().getHabitWithTaskAndLog(selectedHabit.value?.id?:0)
    }

    suspend fun updateHabitTaskCheck(taskEntity: HabitTaskEntity, isChecked: Boolean): Result<Unit>{
        return try {
            taskEntity.isCheck = isChecked
            taskEntity.dateCheck = Date().time
            appDatabase.habitTaskDao().updateHabitTask(taskEntity)
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun updateHabitTaskDescription(taskEntity: HabitTaskEntity, description: String): Result<Unit>{
        return try {
            taskEntity.description = description
            appDatabase.habitTaskDao().updateHabitTask(taskEntity)
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }


    suspend fun updateHabit(
        title: String,
        color: ColorType,
        isReminder: Boolean,
        repeatType: RepeatType?,
        labelType: LabelTypes?
    ): Result<Unit>{
        return try {
            selectedHabit.value?.title = title
            selectedHabit.value?.color = color.ordinal
            selectedHabit.value?.hasReminder = isReminder
            repeatType?.ordinal.let { selectedHabit.value?.repetition = it }
            labelType?.ordinal.let { selectedHabit.value?.tag = it }
            selectedHabit.value?.let { appDatabase.habitDao().updateHabit(it) }
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun editTitleHabit(title: String): Result<Unit>{
        return try {
            selectedHabit.value?.title = title
            selectedHabit.value?.let { appDatabase.habitDao().updateHabit(it) }
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun editDescriptionHabit(description: String): Result<Unit>{
        return try {
            selectedHabit.value?.description = description
            selectedHabit.value?.let { appDatabase.habitDao().updateHabit(it) }
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }


    suspend fun editRepeatHabit(repeatType: RepeatType): Result<RepeatType>{
        return try {
            selectedHabit.value?.repetition = repeatType.ordinal
            selectedHabit.value?.let { appDatabase.habitDao().updateHabit(it) }
            Result.success(repeatType)
            }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun editLabelHabit(labelType: LabelTypes): Result<LabelTypes>{
        return try {
            selectedHabit.value?.tag = labelType.ordinal
            selectedHabit.value?.let { appDatabase.habitDao().updateHabit(it) }
            Result.success(labelType)
            }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun enableReminderWitNotification(enable: Boolean): Result<Boolean>{
        return try {
            selectedHabit.value?.hasReminder = enable
            selectedHabit.value?.let { appDatabase.habitDao().updateHabit(habitEntity = it) }

            Result.success(enable)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun selectColor(
        color: ColorType
    ): Result<ColorType>{
        return  try {
            selectedHabit.value?.color = color.ordinal
            selectedHabit.value?.let { appDatabase.habitDao().updateHabit(it) }
            Result.success(color)
        }catch (e: Exception){
            Result.failure(e)
        }
    }


}