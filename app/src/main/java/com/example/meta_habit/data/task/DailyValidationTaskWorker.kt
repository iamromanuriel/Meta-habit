package com.example.meta_habit.data.task

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.meta_habit.data.db.AppDatabase
import com.example.meta_habit.data.db.entity.HabitEntity
import com.example.meta_habit.data.db.entity.HabitLogEntity
import com.example.meta_habit.data.db.entity.HabitWithTasks
import com.example.meta_habit.ui.utils.RepeatType
import com.example.meta_habit.ui.utils.getNextAWeek
import com.example.meta_habit.ui.utils.getRepeatType
import com.example.meta_habit.ui.utils.nextDayMonth
import com.example.meta_habit.ui.utils.toLocalDate
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class MyRepository{
    fun getMessage(): String = "!<Hola Mundo>"
}

class DailyValidationTaskWorker(appContext: Context, workerParams: WorkerParameters, private val appDatabase: AppDatabase) :
    CoroutineWorker(appContext, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {

        return try {
            saveHabitTaskLogger()
            Result.success()
        }catch (e: Exception){
            Result.failure()
        }

    }


    private suspend fun registerTest(){
        val habit = HabitEntity(
            title = "Prueba de registro",
            repetition = RepeatType.DAILY.ordinal,
            hasReminder = true,
            dateReminder = System.currentTimeMillis(),
            description = "Esto es una prueba",
            isPinned = false,
            isCompleted = false,
            tag = 0,
            color = 0,
            dateCreate = System.currentTimeMillis(),
            dateUpdate = 0
        )

        appDatabase.habitDao().insertGetId(habit)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun saveHabitTaskLogger(){
        appDatabase.habitDao().getListOfHabitWithTasks()
            .map { habits ->
                habits.filter { getRepeatType((it.habit.repetition ?: 0)) != RepeatType.ONLY_ONE }.forEach { habitWithTask ->
                    when(getRepeatType(habitWithTask.habit.repetition?:0)){
                        RepeatType.DAILY -> {
                            restoreHabitTask(habitWithTask)
                        }
                        RepeatType.WEEKLY -> {
                            val nextDay = getNextAWeek((habitWithTask.habit.dateReminder?:0).toLocalDate())
                            val now = LocalDate.now()

                            if(nextDay == now){
                                restoreHabitTask(habitWithTask)
                            }
                        }
                        RepeatType.MONTHLY -> {
                            val nextDay = nextDayMonth((habitWithTask.habit.dateReminder?:0).toLocalDate())
                            val now = LocalDate.now()

                            if(nextDay == now){
                                restoreHabitTask(habitWithTask)
                            }
                        }
                        else -> {

                        }
                    }
                }
            }
    }

    private suspend fun restoreHabitTask(habitWithTasks: HabitWithTasks): kotlin.Result<Unit> {
        return try {
            habitWithTasks.task.forEach { habitTask ->

                val habitTaskLog = HabitLogEntity(
                    habitTaskId = habitTask.id,
                    date = habitTask.dateCheck,
                    isCompleted = habitTask.isCheck
                )
                appDatabase.habitTaskLogger().insertHabitLog(habitTaskLog)

                habitTask.isCheck = false
                habitTask.dateCheck = null
                appDatabase.habitTaskDao().updateHabitTask(habitTask)

            }
            kotlin.Result.success(Unit)
        }catch (e: Exception){
            kotlin.Result.failure(e)
        }
    }
}