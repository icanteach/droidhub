package co.icanteach.apps.android.droidhub.features.account.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidhub.features.account.domain.User
import co.icanteach.apps.android.droidhub.features.user.data.UserEntity
import co.icanteach.apps.android.droidhub.features.user.data.UserRepository
import co.icanteach.apps.android.droidhub.features.userpref.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    var accountScreenState by mutableStateOf(
        AccountScreenUiState.idle()
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
        userPreferencesRepository
            .userPreferencesFlow.combine(userRepository.getUser()) { userPreferences, userEntity ->
                accountScreenState =
                    accountScreenState.copy(isDarkThemeSelected = userPreferences.isDarkThemeSelected,
                        isUserLoggedIn = userEntity != null,
                        user = userEntity?.let { mapTo(it) } ?: User())
            }.onEach {
                println(it)
            }.launchIn(viewModelScope)
    }

    private fun onDarkThemeChanged(isDarkThemeSelected: Boolean) {
        onUpdateAccountScreenState(accountScreenState.onDarkThemeChanged(isDarkThemeSelected = isDarkThemeSelected))
        viewModelScope.launch {
            userPreferencesRepository.updateOnDarkThemeSelection(isDarkThemeSelected)
        }
    }

    private fun onUpdateAccountScreenState(screenState: AccountScreenUiState) {
        accountScreenState = screenState
    }

    private fun mapTo(userEntity: UserEntity): User {
        return User(name = userEntity.name, email = userEntity.email)
    }
}