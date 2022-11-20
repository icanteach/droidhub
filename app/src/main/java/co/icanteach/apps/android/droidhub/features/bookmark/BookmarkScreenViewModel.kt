package co.icanteach.apps.android.droidhub.features.bookmark

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidhub.components.core.ComponentItem
import co.icanteach.apps.android.droidhub.features.bookmark.domain.AddToBookmarkUseCase
import co.icanteach.apps.android.droidhub.features.bookmark.domain.FetchUserBookmarkUseCase
import co.icanteach.apps.android.droidhub.features.bookmark.domain.RemoveFromBookmarkUseCase
import co.icanteach.apps.android.droidhub.features.feed.domain.FetchFiltersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkScreenViewModel @Inject constructor(
    private val fetchUserBookmarksUseCase: FetchUserBookmarkUseCase,
    private val fetchFiltersUseCase: FetchFiltersUseCase,
    private val addToBookmarkUseCase: AddToBookmarkUseCase,
    private val removeFromBookmarkUseCase: RemoveFromBookmarkUseCase
) : ViewModel() {

    var bookmarkScreenState by mutableStateOf(
        BookmarkScreenUiState()
    )
        private set

    init {
        fetchBookmarks()
        fetchFilters()
    }

    private fun fetchFilters() {
        viewModelScope.launch {
            val result = fetchFiltersUseCase.fetchFilters()
            bookmarkScreenState = bookmarkScreenState.copy(filters = result.toMutableList())
        }
    }

    private fun fetchBookmarks() {
        viewModelScope.launch {
            val result = fetchUserBookmarksUseCase.fetchBookmarks()
            bookmarkScreenState = bookmarkScreenState.copy(components = result)
        }
    }

    private fun fetchFeedBy(queryValues: List<String>) {
        viewModelScope.launch {
            // val result = fetchFeedUseCase.fetchFeedBy(queryValues)
            //bookmarkScreenState = bookmarkScreenState.copy(components = result)
        }
    }

    fun onSelectedFilterChanged(selectedFilter: String) {
        onUpdateSelectedFilterItem(selectedFilter)

        val selectedFilters = bookmarkScreenState.filters.filter { it.isSelected }

        if (selectedFilters.isEmpty()) {
            fetchBookmarks()
            return
        } else {
            fetchFeedBy(selectedFilters.map { it.id })
        }
    }

    private fun onUpdateSelectedFilterItem(selectedFilter: String) {
        bookmarkScreenState = bookmarkScreenState.copy(
            filters = bookmarkScreenState.onSelectedFilterChanged(
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