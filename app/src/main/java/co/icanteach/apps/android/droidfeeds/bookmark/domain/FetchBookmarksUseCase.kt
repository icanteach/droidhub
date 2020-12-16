package co.icanteach.apps.android.droidfeeds.bookmark.domain

import co.icanteach.apps.android.droidfeeds.auth.AuthenticationUseCase
import co.icanteach.apps.android.droidfeeds.core.Resource
import co.icanteach.apps.android.droidfeeds.core.map
import co.icanteach.apps.android.droidfeeds.data.repository.BookmarkRepository
import co.icanteach.apps.android.droidfeeds.home.domain.HomeFeedListing
import co.icanteach.apps.android.droidfeeds.news.NewsItemMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchBookmarksUseCase @Inject constructor(
    private val repository: BookmarkRepository,
    private val authenticationUseCase: AuthenticationUseCase,
    private val mapper: NewsItemMapper
) {

    fun fetchContent(): Flow<Resource<HomeFeedListing>> {

        val documentId = authenticationUseCase.getUserId()

        return repository
            .fetchUserBookmarks(documentId)
            .map {
                it.map { response ->
                    mapper.mapFrom(response)
                }
            }
    }
}