package co.icanteach.apps.android.droidhub.home.domain

import co.icanteach.apps.android.droidhub.core.Resource
import co.icanteach.apps.android.droidhub.core.map
import co.icanteach.apps.android.droidhub.data.repository.DroidFeedsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class FetchFiltersUseCase @Inject constructor(
    private val repository: DroidFeedsRepository
) {

    fun fetchFilters(): Flow<Resource<List<String>>> {
        return repository
            .fetchFilters()
            .map {
                it.map { response ->
                    response.filters.sorted()
                }
            }
    }
}