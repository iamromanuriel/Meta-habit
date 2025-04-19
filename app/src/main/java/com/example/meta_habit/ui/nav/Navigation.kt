package com.example.meta_habit.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.meta_habit.ui.Screen.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object Home


@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Home){
        composable<Home> { backStackEntry ->
            HomeScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationPreview(){
    Navigation()
}