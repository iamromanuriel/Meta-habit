package com.example.meta_habit.di

import com.example.meta_habit.ui.screen.home.HomeViewModel
import com.example.meta_habit.ui.screen.create.CreateViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::CreateViewModel)
}

fun initKoin(config: KoinAppDeclaration ? = null){
    startKoin{
        config?.invoke(this)
        modules(viewModelModule)
    }
}