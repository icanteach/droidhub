package co.icanteach.apps.android.droidhub.features.account

sealed class AccountScreenEvent {
    data class OnDarkThemeChanged(val isDarkThemeEnabled: Boolean) : AccountScreenEvent()
}
