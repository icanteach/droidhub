package co.icanteach.apps.android.droidhub.data.repository

import co.icanteach.apps.android.droidhub.core.Resource
import co.icanteach.apps.android.droidhub.data.IoDispatcher
import co.icanteach.apps.android.droidhub.data.repository.model.FeedDocumentResponse
import co.icanteach.apps.android.droidhub.data.repository.model.FilterResponse
import co.icanteach.apps.android.droidhub.data.repository.model.NewsResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class DroidFeedsRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) {


    fun fetchHomeFeed() = flow {

        val homeFeedCollection = firestore.collection("home-feed")
        // Emit loading state
        emit(Resource.Loading)

        val snapshot = homeFeedCollection.get().await()

        val homeFeed = snapshot.documents.mapNotNull {
            it.toObject(NewsResponse::class.java)
        }

        // Emit success state with data
        emit(Resource.Success(homeFeed))
    }.catch { exception ->
        emit(Resource.Error(exception))
    }.flowOn(dispatcher)

    fun fetchHomeFeedByFilter(filterContent: String) = flow {

        val homeFeedCollection = firestore.collection("home-feed")
        // Emit loading state
        emit(Resource.Loading)

        val snapshot = homeFeedCollection.whereEqualTo("category", filterContent).get().await()
        val homeFeed = snapshot.documents.mapNotNull {
            it.toObject(NewsResponse::class.java)
        }

        // Emit success state with data
        emit(Resource.Success(homeFeed))
    }.catch { exception ->
        emit(Resource.Error(exception))
    }.flowOn(dispatcher)

    fun fetchFilters() = flow {

        val filtersCollection = firestore.collection("droidhub-filters")

        // Emit loading state
        emit(Resource.Loading)

        val snapshot = filtersCollection.document("droidhub-main-filters").get().await()
        val filters = snapshot.toObject(FilterResponse::class.java)

        // Emit success state with data
        filters?.let {
            emit(Resource.Success(filters))
        }
    }.catch { exception ->
        emit(Resource.Error(exception))
    }.flowOn(dispatcher)
}