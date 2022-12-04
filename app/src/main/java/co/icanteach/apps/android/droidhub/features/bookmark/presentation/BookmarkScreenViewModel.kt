package co.icanteach.apps.android.droidhub.features.bookmark.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidhub.components.core.ComponentItem
import co.icanteach.apps.android.droidhub.features.bookmark.BookmarkScreenUiState
import co.icanteach.apps.android.droidhub.features.bookmark.domain.AddToBookmarkUseCase
import co.icanteach.apps.android.droidhub.features.bookmark.domain.FetchUserBookmarkUseCase
import co.icanteach.apps.android.droidhub.features.bookmark.domain.RemoveFromBookmarkUseCase
import co.icanteach.apps.android.droidhub.features.feed.FeedScreenUiState
import co.icanteach.apps.android.droidhub.features.feed.domain.FetchFiltersUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkScreenViewModel @Inject constructor(
    private val fetchUserBookmarksUseCase: FetchUserBookmarkUseCase,
    private val fetchFiltersUseCase: FetchFiltersUseCase,
    private val addToBookmarkUseCase: AddToBookmarkUseCase,
    private val removeFromBookmarkUseCase: RemoveFromBookmarkUseCase
) : ViewModel() {

    private val _bookScreenUiState =
        MutableStateFlow<BookmarkScreenUiState>(BookmarkScreenUiState.Loading)
    val bookScreenUiState: StateFlow<BookmarkScreenUiState> = _bookScreenUiState

    init {
        initFeed()
    }

    private fun initFeed() {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            viewModelScope.launch {
                val filters = fetchFiltersUseCase.fetchFilters()
                fetchUserBookmarksUseCase.fetchBookmarks().collect { components ->
                    if (components.isEmpty()) {
                        onInitBookmarkResult(BookmarkScreenUiState.Empty)
                    } else {
                        onInitBookmarkResult(
                            BookmarkScreenUiState.Success(
                                filters = filters,
                                components = components
                            )
                        )
                    }
                }
            }
        } else {
            onInitBookmarkResult(BookmarkScreenUiState.UserNotLoggedIn)
        }
    }

    private fun onInitBookmarkResult(uiState: BookmarkScreenUiState) {
        _bookScreenUiState.value = uiState
    }

    private fun x() {
        viewModelScope.launch {
            fetchUserBookmarksUseCase.fetchBookmarks().collect { components ->
                _bookScreenUiState.value =
                    BookmarkScreenUiState.Success(components = components)
            }
        }
    }

    fun onSelectedFilterChanged(selectedFilter: String) {
        onUpdateSelectedFilterItem(selectedFilter)
        val selectedFilters =
            (_bookScreenUiState.value as FeedScreenUiState.Success).filters.filter { it.isSelected }

        if (selectedFilters.isEmpty()) {
            //7 fetchFeed()
            return
        } else {
            // fetchFeedBy(selectedFilters.map { it.id })
        }
    }

    private fun onUpdateSelectedFilterItem(selectedFilter: String) {
        val oldScreenUiState = _bookScreenUiState.value as BookmarkScreenUiState.Success
        _bookScreenUiState.value =
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

    fun refreshBookmarkContent() {
        initFeed()
    }
}