package com.example.meta_habit.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.work.impl.SchedulersCreator
import com.example.meta_habit.data.task.workManager.AlarmReceiver
import com.example.meta_habit.data.task.workManager.WorkScheduler
import com.example.meta_habit.ui.nav.Navigation
import com.example.meta_habit.ui.theme.MetaHabitTheme
import com.example.meta_habit.utils.NotificationHabit

class MainActivity : ComponentActivity() {

    private fun checkAndScheduleExactAlarm() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if (!alarmManager.canScheduleExactAlarms()) {
                // El permiso NO está concedido. ¡Redirigir!
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
            } else {
                // El permiso SÍ está concedido. Programamos la alarma.
                WorkScheduler.onCreateNotification(applicationContext)
            }
        } else {
            // Versiones antiguas no tienen este requisito especial
            WorkScheduler.onCreateNotification(applicationContext)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(permissions().isNotEmpty()){
            ActivityCompat.requestPermissions(this, permissions().toTypedArray(), 0)
        }

        enableEdgeToEdge()
        NotificationHabit.createNotificationChannel(this, "main", "Notification for habit remember")

        checkAndScheduleExactAlarm()
        WorkScheduler.onCreateNotification(applicationContext)


        setContent {
            MetaHabitTheme {
                Navigation()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        checkAndScheduleExactAlarm()
    }

    companion object{
        fun permissions(): List<String>{
            val permissionList = mutableListOf<String>()

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                permissionList.add(Manifest.permission.POST_NOTIFICATIONS)
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                permissionList.add(Manifest.permission.SCHEDULE_EXACT_ALARM)
            }


            return permissionList
        }
    }


}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MetaHabitTheme {
        Greeting("Android")
    }
}


