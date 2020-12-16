package co.icanteach.apps.android.droidfeeds.bookmark

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidfeeds.bookmark.domain.FetchBookmarksUseCase
import co.icanteach.apps.android.droidfeeds.core.Resource
import co.icanteach.apps.android.droidfeeds.home.domain.HomeFeedListing
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BookmarkViewModel @ViewModelInject constructor(
    private val useCase: FetchBookmarksUseCase
) : ViewModel() {


    private val homeFeedListing = MutableLiveData<HomeFeedListing>()
    val homeFeedListing_: LiveData<HomeFeedListing> = homeFeedListing

    init {
        fetchHomeFeed()
    }

    private fun fetchHomeFeed() {
        viewModelScope.launch {
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
}