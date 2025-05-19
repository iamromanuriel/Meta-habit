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
import com.example.meta_habit.ui.screen.notification.NotificationScreen
import kotlinx.serialization.Serializable

@Serializable
object Home
@Serializable
object Detail
@Serializable
object Notification


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
                },
                onNavigateToNotification = {
                    navigateToDetail(backStackEntry){
                        navController.navigate(Notification)
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

        composable<Notification>{ backStackEntry ->
            NotificationScreen(
                onBack = {
                    navController.popBackStack()
                },
                onOpenNotification = {
                    navigateToDetail(backStackEntry){
                        navController.navigate(Detail)
                    }
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

@Preview()
@Composable
fun NavigationPreview(){
    Navigation()
}