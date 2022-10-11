package co.icanteach.apps.android.droidhub.features.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidhub.features.feed.domain.FetchDroidhubMainFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val fetchMainFeedUseCase: FetchDroidhubMainFeedUseCase
) : ViewModel() {

    var feedScreenState by mutableStateOf(
        FeedScreenUiState()
    )
        private set

    init {
        fetchFeed()
    }

    private fun fetchFeed() {
        viewModelScope.launch {
            val result = fetchMainFeedUseCase.fetchMainFeed()
            feedScreenState = feedScreenState.copy(components = result)
        }
    }
}