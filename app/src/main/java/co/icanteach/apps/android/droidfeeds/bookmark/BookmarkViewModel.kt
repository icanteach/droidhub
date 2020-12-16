package co.icanteach.apps.android.droidfeeds.bookmark

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidfeeds.bookmark.domain.FetchBookmarksUseCase
import co.icanteach.apps.android.droidfeeds.core.StatusViewState
import co.icanteach.apps.android.droidfeeds.core.doOnStatusChanged
import co.icanteach.apps.android.droidfeeds.core.doOnSuccess
import co.icanteach.apps.android.droidfeeds.home.domain.HomeFeedListing
import kotlinx.coroutines.flow.launchIn

class BookmarkViewModel @ViewModelInject constructor(
    private val useCase: FetchBookmarksUseCase
) : ViewModel() {

    private val homeFeedListing = MutableLiveData<HomeFeedListing>()
    val homeFeedListing_: LiveData<HomeFeedListing> = homeFeedListing

    private val statusState = MutableLiveData<StatusViewState>()
    val status_: LiveData<StatusViewState> = statusState

    init {
        fetchHomeFeed()
    }

    private fun fetchHomeFeed() {

        useCase.fetchContent()
            .doOnSuccess { data ->
                homeFeedListing.value = data
            }
            .doOnStatusChanged { status ->
                statusState.value = StatusViewState(status)
            }
            .launchIn(viewModelScope)
    }
}