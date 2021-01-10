package co.icanteach.apps.android.droidhub.home.domain

import co.icanteach.apps.android.droidhub.core.Resource
import co.icanteach.apps.android.droidhub.core.map
import co.icanteach.apps.android.droidhub.data.repository.DroidFeedsRepository
import co.icanteach.apps.android.droidhub.news.FeedItemMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchHomeFeedUseCase @Inject constructor(
    private val repository: DroidFeedsRepository,
    private val mapper: FeedItemMapper
) {

    fun fetchHomeFeed(): Flow<Resource<HomeFeedListing>> {
        return repository
            .fetchHomeFeed()
            .map {
                it.map { response ->
                    mapper.mapFrom(response)
                }
            }
    }

    fun fetchHomeFeed(filterContent: String): Flow<Resource<HomeFeedListing>> {
        return repository
            .fetchHomeFeedByFilter(filterContent)
            .map {
                it.map { response ->
                    mapper.mapFrom(response)
                }
            }
    }
}