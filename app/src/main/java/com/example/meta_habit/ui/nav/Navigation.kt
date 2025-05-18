package com.example.meta_habit.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.meta_habit.ui.screen.detail.DetailScreen
import com.example.meta_habit.ui.screen.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object Home
@Serializable
object Detail


@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Home){
        composable<Home> { backStackEntry ->
            HomeScreen(
                onNavigateToDetail = {
                    navigateToDetail(backStackEntry){
                        navController.navigate(Detail)
                    }
                }
            )
        }
        composable<Detail> { backStackEntry ->
            DetailScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

fun navigateToDetail(from: NavBackStackEntry, onNav: () -> Unit){
    if(from.lifecycle.currentState == Lifecycle.State.RESUMED){
        onNav()
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationPreview(){
    Navigation()
}