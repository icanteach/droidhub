package co.icanteach.apps.android.droidfeeds.home

import co.icanteach.apps.android.droidfeeds.core.Status

class DroidhubFiltersViewState constructor(
    val filters: List<String> = mutableListOf(),
    val status: Status
) {

    fun shouldShowFilterButton() = status == Status.Content
}