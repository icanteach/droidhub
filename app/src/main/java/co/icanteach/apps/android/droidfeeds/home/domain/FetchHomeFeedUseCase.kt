package co.icanteach.apps.android.droidfeeds.home.domain

import co.icanteach.apps.android.droidfeeds.core.Resource
import co.icanteach.apps.android.droidfeeds.core.map
import co.icanteach.apps.android.droidfeeds.data.repository.DroidFeedsRepository
import co.icanteach.apps.android.droidfeeds.news.NewsItemMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchHomeFeedUseCase(
    val repository: DroidFeedsRepository,
    val mapper: NewsItemMapper
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