package co.icanteach.apps.android.droidhub.core

sealed class Status {

    object Content : Status()

    data class Error(val exception: Throwable) : Status()

    object Loading : Status()

    object ContentWithLoading : Status()
}