package com.example.meta_habit.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meta_habit.ui.Screen.home.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.viewModelOf

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
}

fun initKoin(config: KoinAppDeclaration ? = null){
    startKoin{
        config?.invoke(this)
        modules(viewModelModule)
    }
}