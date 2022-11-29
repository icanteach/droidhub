package co.icanteach.apps.android.droidhub.features.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidhub.components.core.ComponentItem
import co.icanteach.apps.android.droidhub.features.bookmark.domain.AddToBookmarkUseCase
import co.icanteach.apps.android.droidhub.features.bookmark.domain.RemoveFromBookmarkUseCase
import co.icanteach.apps.android.droidhub.features.feed.domain.FetchFeedUseCase
import co.icanteach.apps.android.droidhub.features.feed.domain.FetchFiltersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val fetchFeedUseCase: FetchFeedUseCase,
    private val fetchFiltersUseCase: FetchFiltersUseCase,
    private val addToBookmarkUseCase: AddToBookmarkUseCase,
    private val removeFromBookmarkUseCase: RemoveFromBookmarkUseCase
) : ViewModel() {

    private val _feedScreenState = MutableStateFlow<FeedScreenUiState>(FeedScreenUiState.Loading)
    val feedScreenState: StateFlow<FeedScreenUiState> = _feedScreenState

    init {
        initFeed()
    }

    private fun initFeed() {
        viewModelScope.launch {
            val filters = fetchFiltersUseCase.fetchFilters()

            fetchFeedUseCase.fetchFeed().collect { components ->
                _feedScreenState.value =
                    FeedScreenUiState.Success(filters = filters, components = components)
            }
        }
    }

    fun onSelectedFilterChanged(selectedFilter: String) {
        onUpdateSelectedFilterItem(selectedFilter)
        val selectedFilters =
            (feedScreenState.value as FeedScreenUiState.Success).filters.filter { it.isSelected }

        if (selectedFilters.isEmpty()) {
            fetchFeed()
            return
        } else {
            fetchFeedBy(selectedFilters.map { it.id })
        }
    }

    private fun fetchFeed() {
        viewModelScope.launch {
            fetchFeedUseCase.fetchFeed().collect { result ->
                val oldScreenUiState = feedScreenState.value as FeedScreenUiState.Success
                _feedScreenState.value = oldScreenUiState.copy(components = result)
            }
        }
    }

    private fun fetchFeedBy(queryValues: List<String>) {
        viewModelScope.launch {
            val result = fetchFeedUseCase.fetchFeedBy(queryValues)
            val oldScreenUiState = feedScreenState.value as FeedScreenUiState.Success
            _feedScreenState.value = oldScreenUiState.copy(components = result)
        }
    }

    private fun onUpdateSelectedFilterItem(selectedFilter: String) {
        val oldScreenUiState = feedScreenState.value as FeedScreenUiState.Success
        _feedScreenState.value =
            oldScreenUiState.copy(filters = oldScreenUiState.onSelectedFilterChanged(selectedFilter))
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