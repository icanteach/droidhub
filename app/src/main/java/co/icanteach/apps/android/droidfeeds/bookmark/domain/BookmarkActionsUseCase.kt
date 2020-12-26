package co.icanteach.apps.android.droidfeeds.bookmark.domain

import co.icanteach.apps.android.droidfeeds.auth.AuthenticationUseCase
import co.icanteach.apps.android.droidfeeds.core.Resource
import co.icanteach.apps.android.droidfeeds.core.map
import co.icanteach.apps.android.droidfeeds.data.repository.BookmarkRepository
import co.icanteach.apps.android.droidfeeds.home.domain.HomeFeedListing
import co.icanteach.apps.android.droidfeeds.news.NewsItemMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val PARAM_ORIGIN_URL = "originUrl"
private const val PARAM_IMAGE = "image"
private const val PARAM_TITLE = "title"
private const val PARAM_DESCRIPTION = "description"

class BookmarkActionsUseCase @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase,
    private val bookmarkRepository: BookmarkRepository,
    private val mapper: NewsItemMapper
) {

    fun addBookmark(
        originUrl: String,
        image: String,
        title: String,
        description: String
    ): Flow<Resource<Boolean>> {
        val bookmarkItem = hashMapOf(
            PARAM_ORIGIN_URL to originUrl,
            PARAM_IMAGE to image,
            PARAM_TITLE to title,
            PARAM_DESCRIPTION to description
        )

        val documentId = authenticationUseCase.getUserId()

        return bookmarkRepository.addBookmark(bookmarkItem, documentId)
    }

    @ExperimentalCoroutinesApi
    fun removeBookmark(
        originUrl: String,
        title: String,
        description: String,
        image: String
    ): Flow<Resource<HomeFeedListing>> {

        val bookmarkItem = hashMapOf(
            PARAM_ORIGIN_URL to originUrl,
            PARAM_IMAGE to image,
            PARAM_TITLE to title,
            PARAM_DESCRIPTION to description
        )

        val documentId = authenticationUseCase.getUserId()
        return bookmarkRepository.removeBookmark(bookmarkItem, documentId).map {
            it.map { response ->
                mapper.mapFrom(response)
            }
        }
    }
}