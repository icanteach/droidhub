package co.icanteach.apps.android.droidhub.features.userpref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * see @ref = https://github.com/googlecodelabs/android-datastore/blob/preferences_datastore/app/src/main/java/com/codelab/android/datastore/data/UserPreferencesRepository.kt
 */
data class UserPreferences(
    val isDarkThemeSelected: Boolean
)

class UserPreferencesRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {
    private object PreferencesKeys {
        val DARK_THEME = booleanPreferencesKey("dark_theme_selected")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data.catch {
        emit(emptyPreferences())
    }.map { preferences ->
        mapUserPreferences(preferences)
    }

    suspend fun updateOnDarkThemeSelection(isDarkThemeSelected: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_THEME] = isDarkThemeSelected
        }
    }

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val isDarkThemeSelected = preferences[PreferencesKeys.DARK_THEME] ?: false
        return UserPreferences(
            isDarkThemeSelected = isDarkThemeSelected
        )
    }
}