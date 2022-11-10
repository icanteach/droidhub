package co.icanteach.apps.android.droidhub

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidhub.features.userpref.UserPreferences
import co.icanteach.apps.android.droidhub.features.userpref.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _userThemePreference = MutableLiveData<UserPreferences>()
    val userThemePreference: LiveData<UserPreferences> = _userThemePreference

    private val _openSubmissonPage = MutableLiveData<String>()
    val openSubmissonPage: LiveData<String> = _openSubmissonPage

    init {
        observeUserPreferences()
    }

    private fun observeUserPreferences() {
        userPreferencesRepository.userPreferencesFlow.onEach { result ->
            _userThemePreference.value = result
        }.launchIn(viewModelScope)
    }

    fun onOpenSubmissonPage(incomingUrl: String) {
        _openSubmissonPage.value = incomingUrl
    }
}