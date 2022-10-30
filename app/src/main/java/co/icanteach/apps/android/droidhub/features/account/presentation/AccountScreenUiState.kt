package co.icanteach.apps.android.droidhub.features.account.presentation

import co.icanteach.apps.android.droidhub.features.account.domain.User

data class AccountScreenUiState(
    val isDarkThemeSelected: Boolean,
    val isUserLoggedIn: Boolean,
    val user: User = User()
) {

    fun onDarkThemeChanged(isDarkThemeSelected: Boolean): AccountScreenUiState {
        return this.copy(isDarkThemeSelected = isDarkThemeSelected)
    }

    companion object {

        fun idle(): AccountScreenUiState {
            return AccountScreenUiState(
                isDarkThemeSelected = false,
                isUserLoggedIn = false
            )
        }
    }
}