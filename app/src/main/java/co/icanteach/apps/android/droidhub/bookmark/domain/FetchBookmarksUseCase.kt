package co.icanteach.apps.android.droidhub.bookmark.domain

import co.icanteach.apps.android.droidhub.auth.AuthenticationUseCase
import co.icanteach.apps.android.droidhub.core.Resource
import co.icanteach.apps.android.droidhub.core.map
import co.icanteach.apps.android.droidhub.data.repository.BookmarkRepository
import co.icanteach.apps.android.droidhub.home.domain.HomeFeedListing
import co.icanteach.apps.android.droidhub.news.FeedItemMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchBookmarksUseCase @Inject constructor(
    private val repository: BookmarkRepository,
    private val authenticationUseCase: AuthenticationUseCase,
    private val mapper: FeedItemMapper
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