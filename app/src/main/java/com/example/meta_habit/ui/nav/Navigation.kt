package com.example.meta_habit.ui.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.meta_habit.ui.screen.create.CreateScreen
import com.example.meta_habit.ui.screen.create.CreateViewModel
import com.example.meta_habit.ui.screen.detail.DetailScreen
import com.example.meta_habit.ui.screen.home.HomeScreen
import com.example.meta_habit.ui.screen.notification.NotificationScreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import java.util.Objects

@Serializable
object Home
@Serializable
object Create
@Serializable
object Detail
@Serializable
object Notification
const val TRANSFORM_KEY = "ANIMATE"


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(){
    val navController = rememberNavController()

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = Home,
        ){

            composable<Home>(

            ) { backStackEntry ->
                HomeScreen(
                    animatedVisibilityScope = this,
                    onNavigateToDetail = {
                        navigateToDetail(backStackEntry){
                            navController.navigate(Detail)
                        }
                    },
                    onNavigateToCreate = {
                        navigateToDetail(backStackEntry){
                            navController.navigate(Create)
                        }
                    },
                    onNavigateToNotification = {
                        navigateToDetail(backStackEntry){
                            navController.navigate(Notification)
                        }
                    }
                )
            }

            composable<Create>() { backStackEntry ->
                val viewModel: CreateViewModel = koinViewModel()
                CreateScreen(
                    modifier = Modifier.sharedBounds(
                        animatedVisibilityScope = this,
                        sharedContentState = rememberSharedContentState(TRANSFORM_KEY)
                    ),
                    viewModel = viewModel,
                    onDismiss = {
                        navController.popBackStack()
                    }

                )
            }
            composable<Detail>(
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideIntoContainer(
                        animationSpec = tween(300, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                },
                popExitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideOutOfContainer(
                        animationSpec = tween(300, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.End
                    )
                }
            ) { backStackEntry ->
                DetailScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable<Notification>(
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideIntoContainer(
                        animationSpec = tween(300, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                },
                popExitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideOutOfContainer(
                        animationSpec = tween(300, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.End
                    )
                }
            ){ backStackEntry ->
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

}

fun navigateToDetail(from: NavBackStackEntry, onNav: () -> Unit){
    if(from.lifecycle.currentState == Lifecycle.State.RESUMED){
        onNav()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview()
@Composable
fun NavigationPreview(){
    Navigation()
}