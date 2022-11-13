package co.icanteach.apps.android.droidhub.features.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidhub.features.feed.domain.FetchFeedUseCase
import co.icanteach.apps.android.droidhub.features.feed.domain.FetchFiltersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val fetchFeedUseCase: FetchFeedUseCase,
    private val fetchFiltersUseCase: FetchFiltersUseCase
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
            val result = fetchFeedUseCase.fetchFeed()
            feedScreenState = feedScreenState.copy(components = result)
        }
    }

    fun onSelectedFilterChanged(selectedFilter: String) {
        feedScreenState =
            feedScreenState.copy(filters = feedScreenState.onSelectedFilterChanged(selectedFilter))
    }
}