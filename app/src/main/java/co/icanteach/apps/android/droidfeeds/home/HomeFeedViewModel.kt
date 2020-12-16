package co.icanteach.apps.android.droidfeeds.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidfeeds.bookmark.domain.BookmarkActionsUseCase
import co.icanteach.apps.android.droidfeeds.core.*
import co.icanteach.apps.android.droidfeeds.home.domain.FetchHomeFeedUseCase
import co.icanteach.apps.android.droidfeeds.home.domain.HomeFeedListing
import co.icanteach.apps.android.droidfeeds.news.NewsItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class HomeFeedViewModel @ViewModelInject constructor(
    private val useCase: FetchHomeFeedUseCase,
    private val bookmarkActionsUseCase: BookmarkActionsUseCase
) : ViewModel() {


    private val homeFeedListing = MutableLiveData<HomeFeedListing>()
    val homeFeedListing_: LiveData<HomeFeedListing> = homeFeedListing

    private val statusState = MutableLiveData<StatusViewState>()
    val status_: LiveData<StatusViewState> = statusState

    init {
        fetchHomeFeed()
    }

    private fun fetchHomeFeed() {

        useCase
            .fetchContent()
            .doOnSuccess { data ->
                homeFeedListing.value = data
            }
            .doOnStatusChanged { status ->
                statusState.value = StatusViewState(status)
            }
            .launchIn(viewModelScope)
    }

    fun addBookmark(newsItem: NewsItem) {
        viewModelScope.launch {
            bookmarkActionsUseCase
                .addBookmark(
                    originUrl = newsItem.originUrl,
                    title = newsItem.title,
                    description = newsItem.description,
                    image = newsItem.image
                ).doOnLoading {
                    statusState.value = StatusViewState(Status.ContentWithLoading)
                }.doOnSuccess {
                    statusState.value = StatusViewState(Status.Content)
                }.doOnError {
                    statusState.value = StatusViewState(Status.Content)
                }
                .launchIn(viewModelScope)
        }
    }
}