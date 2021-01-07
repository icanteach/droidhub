package co.icanteach.apps.android.droidfeeds.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidfeeds.analytics.AnalyticsKeys
import co.icanteach.apps.android.droidfeeds.analytics.AnalyticsUseCase
import co.icanteach.apps.android.droidfeeds.bookmark.domain.BookmarkActionsUseCase
import co.icanteach.apps.android.droidfeeds.core.ActionEvent
import co.icanteach.apps.android.droidfeeds.core.extensions.doOnSuccess
import co.icanteach.apps.android.droidfeeds.core.extensions.doOnStatusChanged
import co.icanteach.apps.android.droidfeeds.core.extensions.doOnLoading
import co.icanteach.apps.android.droidfeeds.core.extensions.doOnError
import co.icanteach.apps.android.droidfeeds.core.Status
import co.icanteach.apps.android.droidfeeds.core.StatusViewState
import co.icanteach.apps.android.droidfeeds.home.domain.FetchHomeFeedUseCase
import co.icanteach.apps.android.droidfeeds.home.domain.HomeFeedListing
import co.icanteach.apps.android.droidfeeds.news.FeedItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class HomeFeedViewModel @ViewModelInject constructor(
    private val fetchHomeFeedUseCase: FetchHomeFeedUseCase,
    private val bookmarkActionsUseCase: BookmarkActionsUseCase,
    private val analyticsUseCase: AnalyticsUseCase
) : ViewModel() {

    private val homeFeedListing = MutableLiveData<HomeFeedListing>()
    val homeFeedListing_: LiveData<HomeFeedListing> = homeFeedListing

    private val statusState = MutableLiveData<StatusViewState>()
    val status_: LiveData<StatusViewState> = statusState

    val bookmarkSuccessResult: ActionEvent = ActionEvent()

    init {
        fetchHomeFeed()
        analyticsUseCase.sendScreenView(AnalyticsKeys.PAGE.HOME)
    }

    private fun fetchHomeFeed() {

        fetchHomeFeedUseCase
            .fetchContent()
            .doOnSuccess { data ->
                homeFeedListing.value = data
            }
            .doOnStatusChanged { status ->
                statusState.value = StatusViewState(status)
            }
            .launchIn(viewModelScope)
    }

    fun addBookmark(newsItem: FeedItem) {

        analyticsUseCase.sendClickEvent(AnalyticsKeys.CLICK.SAVE, AnalyticsKeys.PAGE.HOME)

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
                    bookmarkSuccessResult.call()
                    statusState.value = StatusViewState(Status.Content)
                }.doOnError {
                    statusState.value = StatusViewState(Status.Content)
                }
                .launchIn(viewModelScope)
        }
    }
}