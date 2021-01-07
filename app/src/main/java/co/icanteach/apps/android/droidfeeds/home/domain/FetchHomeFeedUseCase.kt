package co.icanteach.apps.android.droidfeeds.home.domain

import co.icanteach.apps.android.droidfeeds.core.Resource
import co.icanteach.apps.android.droidfeeds.core.map
import co.icanteach.apps.android.droidfeeds.data.repository.DroidFeedsRepository
import co.icanteach.apps.android.droidfeeds.news.FeedItemMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchHomeFeedUseCase @Inject constructor(
    private val repository: DroidFeedsRepository,
    private val mapper: FeedItemMapper
) {

    fun fetchContent(): Flow<Resource<HomeFeedListing>> {
        return repository
            .fetchHomeFeed()
            .map {
                it.map { response ->
                    mapper.mapFrom(response)
                }
            }
    }
}