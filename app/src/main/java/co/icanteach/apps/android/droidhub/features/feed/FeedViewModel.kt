package co.icanteach.apps.android.droidhub.features.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidhub.components.core.ComponentItem
import co.icanteach.apps.android.droidhub.features.bookmark.AddToBookmarkUseCase
import co.icanteach.apps.android.droidhub.features.bookmark.RemoveFromBookmarkUseCase
import co.icanteach.apps.android.droidhub.features.feed.domain.FetchFeedUseCase
import co.icanteach.apps.android.droidhub.features.feed.domain.FetchFiltersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val fetchFeedUseCase: FetchFeedUseCase,
    private val fetchFiltersUseCase: FetchFiltersUseCase,
    private val addToBookmarkUseCase: AddToBookmarkUseCase,
    private val removeFromBookmarkUseCase: RemoveFromBookmarkUseCase
) : ViewModel() {

    var feedScreenState by mutableStateOf(
        FeedScreenUiState()
    )
        private set

    init {
        fetchFeed()
        fetchFilters()
    }

    private fun fetchFilters() {
        viewModelScope.launch {
            val result = fetchFiltersUseCase.fetchFilters()
            feedScreenState = feedScreenState.copy(filters = result.toMutableList())
        }
    }

    private fun fetchFeed() {
        viewModelScope.launch {
            fetchFeedUseCase.fetchFeed().collect { result ->
                feedScreenState = feedScreenState.copy(components = result)

            }
        }
    }

    private fun fetchFeedBy(queryValues: List<String>) {
        viewModelScope.launch {
            val result = fetchFeedUseCase.fetchFeedBy(queryValues)
            feedScreenState = feedScreenState.copy(components = result)
        }
    }

    fun onSelectedFilterChanged(selectedFilter: String) {
        onUpdateSelectedFilterItem(selectedFilter)

        val selectedFilters = feedScreenState.filters.filter { it.isSelected }

        if (selectedFilters.isEmpty()) {
            fetchFeed()
            return
        } else {
            fetchFeedBy(selectedFilters.map { it.id })
        }
    }

    private fun onUpdateSelectedFilterItem(selectedFilter: String) {
        feedScreenState = feedScreenState.copy(
            filters = feedScreenState.onSelectedFilterChanged(
                selectedFilter
            )
        )
    }

    fun onBookmarkItemClicked(component: ComponentItem) {
        viewModelScope.launch {
            if (component.addedToBookmark) {
                removeFromBookmarkUseCase.removeFromBookmark(component.toMap())
            } else {
                addToBookmarkUseCase.addToBookmark(component.toMap())
            }
        }
    }
}