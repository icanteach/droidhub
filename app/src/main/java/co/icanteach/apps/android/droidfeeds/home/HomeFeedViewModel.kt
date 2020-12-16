package co.icanteach.apps.android.droidfeeds.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidfeeds.auth.AuthenticationUseCase
import co.icanteach.apps.android.droidfeeds.bookmark.domain.BookmarkActionsUseCase
import co.icanteach.apps.android.droidfeeds.core.Resource
import co.icanteach.apps.android.droidfeeds.home.domain.FetchHomeFeedUseCase
import co.icanteach.apps.android.droidfeeds.home.domain.HomeFeedListing
import co.icanteach.apps.android.droidfeeds.news.NewsItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFeedViewModel @ViewModelInject constructor(
    private val useCase: FetchHomeFeedUseCase,
    private val bookmarkActionsUseCase: BookmarkActionsUseCase,
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {


    private val homeFeedListing = MutableLiveData<HomeFeedListing>()
    val homeFeedListing_: LiveData<HomeFeedListing> = homeFeedListing

    init {
        fetchHomeFeed()
    }

    fun fetchHomeFeed() {

        viewModelScope.launch {

            authenticationUseCase.authenticate().collect {

            }

            useCase.fetchContent().collect { resource ->

                when (resource) {
                    is Resource.Success -> homeFeedListing.value = resource.data
                    is Resource.Error -> {
                    }
                    Resource.Loading -> {
                    }
                }
            }
        }
    }

    fun addBookmark(
        newsItem: NewsItem
    ) {

        viewModelScope.launch {
            bookmarkActionsUseCase
                .addBookmark(
                    originUrl = newsItem.originUrl,
                    title = newsItem.title,
                    description = newsItem.description,
                    image = newsItem.image
                ).collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                        }
                        is Resource.Error -> {
                        }
                        Resource.Loading -> {
                        }
                    }
                }
        }

    }
}