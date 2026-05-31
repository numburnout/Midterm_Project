package com.nature.habittracker.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferencesRepository(private val context: Context) {

    private val SEASON_KEY = stringPreferencesKey("season")
    private val LANGUAGE_KEY = stringPreferencesKey("language")
    private val STREAK_KEY = stringPreferencesKey("streak")

    val season: Flow<Season> = context.dataStore.data.map { prefs ->
        Season.valueOf(prefs[SEASON_KEY] ?: Season.SPRING.name)
    }

    val language: Flow<Language> = context.dataStore.data.map { prefs ->
        Language.valueOf(prefs[LANGUAGE_KEY] ?: Language.EN.name)
    }

    val streak: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[STREAK_KEY]?.toIntOrNull() ?: 7
    }

    suspend fun setSeason(season: Season) {
        context.dataStore.edit { prefs ->
            prefs[SEASON_KEY] = season.name
        }
    }

    suspend fun setLanguage(language: Language) {
        context.dataStore.edit { prefs ->
            prefs[LANGUAGE_KEY] = language.name
        }
    }

    suspend fun setStreak(streak: Int) {
        context.dataStore.edit { prefs ->
            prefs[STREAK_KEY] = streak.toString()
        }
    }
}
