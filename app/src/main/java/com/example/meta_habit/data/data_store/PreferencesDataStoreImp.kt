package com.example.meta_habit.data.data_store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


const val PREFERENCES_NAME = "my_preferences"

class PreferencesDataStoreImp(val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

    companion object{
        val FILTER_TYPE = intPreferencesKey(name = "filter_type")
    }

    suspend fun save(name: Int){
        context.dataStore.edit { preferences ->
            preferences[FILTER_TYPE] = name
        }
    }

    fun getInt(): Flow<Int?>{
        return context.dataStore.data.map { preferences ->
            preferences[FILTER_TYPE]
        }
    }
}