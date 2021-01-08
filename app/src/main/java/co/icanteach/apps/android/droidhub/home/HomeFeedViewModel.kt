package co.icanteach.apps.android.droidhub.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidhub.analytics.AnalyticsKeys
import co.icanteach.apps.android.droidhub.analytics.AnalyticsUseCase
import co.icanteach.apps.android.droidhub.bookmark.domain.BookmarkActionsUseCase
import co.icanteach.apps.android.droidhub.core.ActionEvent
import co.icanteach.apps.android.droidhub.core.extensions.doOnSuccess
import co.icanteach.apps.android.droidhub.core.extensions.doOnStatusChanged
import co.icanteach.apps.android.droidhub.core.extensions.doOnLoading
import co.icanteach.apps.android.droidhub.core.extensions.doOnError
import co.icanteach.apps.android.droidhub.core.Status
import co.icanteach.apps.android.droidhub.core.StatusViewState
import co.icanteach.apps.android.droidhub.home.domain.FetchFiltersUseCase
import co.icanteach.apps.android.droidhub.home.domain.FetchHomeFeedUseCase
import co.icanteach.apps.android.droidhub.home.domain.HomeFeedListing
import co.icanteach.apps.android.droidhub.news.FeedItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class HomeFeedViewModel @ViewModelInject constructor(
    private val fetchHomeFeedUseCase: FetchHomeFeedUseCase,
    private val bookmarkActionsUseCase: BookmarkActionsUseCase,
    private val analyticsUseCase: AnalyticsUseCase,
    private val fetchFiltersUseCase: FetchFiltersUseCase
) : ViewModel() {

    private val homeFeedListing = MutableLiveData<HomeFeedListing>()
    val homeFeedListing_: LiveData<HomeFeedListing> = homeFeedListing

    private val statusState = MutableLiveData<StatusViewState>()
    val status_: LiveData<StatusViewState> = statusState

    private val filtersViewState = MutableLiveData<DroidhubFiltersViewState>()
    val filtersViewState_: LiveData<DroidhubFiltersViewState> = filtersViewState

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
                fetchFilters()
            }
            .doOnStatusChanged { status ->
                statusState.value = StatusViewState(status)
            }
            .launchIn(viewModelScope)
    }

    private fun fetchFilters() {

        fetchFiltersUseCase
            .fetchFilters()
            .doOnSuccess { data ->
                filtersViewState.value = DroidhubFiltersViewState(
                    filters = data,
                    status = Status.Content
                )
            }
            .doOnLoading {
                filtersViewState.value = DroidhubFiltersViewState(status = Status.Loading)
            }
            .doOnError {
                filtersViewState.value = DroidhubFiltersViewState(status = Status.Error(it))
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

    fun getFilters(): List<String> {
        return filtersViewState.value?.filters ?: mutableListOf()
    }
}