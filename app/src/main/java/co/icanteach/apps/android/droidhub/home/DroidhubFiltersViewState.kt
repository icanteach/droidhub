package co.icanteach.apps.android.droidhub.home

import co.icanteach.apps.android.droidhub.core.Status

class DroidhubFiltersViewState constructor(
    val filters: List<String> = mutableListOf(),
    val status: Status
) {

    fun shouldShowFilterButton() = status == Status.Content
}