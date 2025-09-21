package com.example.meta_habit.ui

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.meta_habit.ui.nav.Navigation
import com.example.meta_habit.ui.theme.MetaHabitTheme
import com.example.meta_habit.utils.NotificationHabit

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(permissions().isNotEmpty()){
            ActivityCompat.requestPermissions(this, permissions().toTypedArray(), 0)
        }

        enableEdgeToEdge()
        NotificationHabit.createNotificationChannel(this, "main", "Notification for habit remember")
        setContent {
            MetaHabitTheme {
                Navigation()
            }
        }

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


