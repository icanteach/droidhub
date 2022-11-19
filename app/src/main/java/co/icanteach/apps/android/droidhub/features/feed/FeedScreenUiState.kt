package co.icanteach.apps.android.droidhub.features.feed

import co.icanteach.apps.android.droidhub.components.core.ComponentItem
import co.icanteach.apps.android.droidhub.features.feed.domain.FilterItem

data class FeedScreenUiState(
    val filters: List<FilterItem> = mutableListOf(),
    val components: List<ComponentItem> = emptyList()
) {
    fun onSelectedFilterChanged(selectedFilter: String): List<FilterItem> {
        // we need to rewrite this method for efficiency
        val tempList = filters.toMutableList()
        filters.forEachIndexed { index, item ->
            item.takeIf { it.id == selectedFilter }?.let {
                tempList[index] = it.copy(isSelected = item.isSelected.not())
            }
        }
        return tempList
    }
}