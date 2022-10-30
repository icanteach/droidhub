package co.icanteach.apps.android.droidhub.features.account.presentation

sealed class AccountScreenEvent {
    data class OnDarkThemeChanged(val isDarkThemeEnabled: Boolean) : AccountScreenEvent()
}
