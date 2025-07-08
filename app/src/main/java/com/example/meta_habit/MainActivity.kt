package com.example.meta_habit

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.meta_habit.data.task.WorkScheduler
import com.example.meta_habit.ui.nav.Navigation
import com.example.meta_habit.ui.theme.MetaHabitTheme

class MainActivity : ComponentActivity() {

    private val handler = Handler(Looper.getMainLooper())

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if(isGranted){
            scheduleNotification()
        } else {
            println("Permission denied")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()
        enableEdgeToEdge()
        WorkScheduler.saveTaskLogger(this)
        setContent {
            MetaHabitTheme {
                Navigation()
            }
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
                scheduleNotification()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }else{
            showTestNotification()
        }
    }

    @SuppressLint("WrongConstant")
    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Canal de pruebas de notificaciones"
            val descriptionText = "Canal de pruebas de notificaciones"
            val importance = NotificationManagerCompat.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("main", name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(this)
            notificationManager.createNotificationChannel(channel)

        }
    }


    private fun scheduleNotification() {
        handler.postDelayed({
            showTestNotification()
        }, 2000)
    }

    private fun showTestNotification() {
        val builder = NotificationCompat.Builder(this, "main")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("New notification")
            .setContentText("This is a test notification from MetaHabit")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("This is a test notification from MetaHabit. This is a test notification from MetaHabit. This is a test notification from MetaHabit."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(applicationContext)){
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(1, builder.build())
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

fun main(){
    val one = true
    val two = true
    println(one || two)
}