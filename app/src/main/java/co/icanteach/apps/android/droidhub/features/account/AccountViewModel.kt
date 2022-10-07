package co.icanteach.apps.android.droidhub.features.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidhub.features.userpref.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    var accountScreenState by mutableStateOf(
        AccountScreenUiState(
            isDarkThemeSelected = false
        )
    )
        private set

    init {
        initAccountScreen()
    }

    fun onEvent(event: AccountScreenEvent) {
        when (event) {
            is AccountScreenEvent.OnDarkThemeChanged -> {
                onDarkThemeChanged(event.isDarkThemeEnabled)
            }
        }
    }

    private fun initAccountScreen() {
        userPreferencesRepository.userPreferencesFlow.onEach { result ->
            onUpdateAccountScreenState(
                accountScreenState.copy(isDarkThemeSelected = result.isDarkThemeSelected)
            )
        }.launchIn(viewModelScope)
    }

    private fun onDarkThemeChanged(isDarkThemeSelected: Boolean) {
        onUpdateAccountScreenState(accountScreenState.copy(isDarkThemeSelected = isDarkThemeSelected))
        viewModelScope.launch {
            userPreferencesRepository.updateOnDarkThemeSelection(isDarkThemeSelected)
        }
    }

    private fun onUpdateAccountScreenState(screenState: AccountScreenUiState) {
        accountScreenState = screenState
    }
}