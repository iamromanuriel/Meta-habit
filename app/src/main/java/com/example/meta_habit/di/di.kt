package com.example.meta_habit.di

import android.content.Context
import androidx.room.Room
import com.example.meta_habit.data.data_store.PREFERENCES_NAME
import com.example.meta_habit.data.data_store.PreferencesDataStoreImp
import com.example.meta_habit.data.db.AppDatabase
import com.example.meta_habit.data.db.databaseName
import com.example.meta_habit.data.repository.HabitRepository
import com.example.meta_habit.data.repository.NotificationRepository
import com.example.meta_habit.ui.screen.home.HomeViewModel
import com.example.meta_habit.ui.screen.create.CreateViewModel
import com.example.meta_habit.ui.screen.detail.DetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::CreateViewModel)
    viewModelOf(::DetailViewModel)
}

val preferencesModule = module {
    single{ PreferencesDataStoreImp(androidContext()) }
}

val databaseModule = module {
    single{
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            databaseName)
            .build()
    }

    single{
        get<AppDatabase>().habitDao()
        get<AppDatabase>().habitTaskDao()
        get<AppDatabase>().habitNotification()
        get<AppDatabase>().habitTaskLogger()
    }
}

val repositoryModule = module {
    single{
        HabitRepository(get())
        //NotificationRepository(get())
    }
}

fun initKoin(config: KoinAppDeclaration ? = null, context: Context){
    startKoin{
        androidContext(context)
        config?.invoke(this)
        modules(viewModelModule, databaseModule, repositoryModule, preferencesModule)
    }
}