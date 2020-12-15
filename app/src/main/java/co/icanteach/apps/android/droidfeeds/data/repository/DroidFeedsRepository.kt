package co.icanteach.apps.android.droidfeeds.data.repository

import co.icanteach.apps.android.droidfeeds.core.Resource
import co.icanteach.apps.android.droidfeeds.data.repository.model.NewsResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DroidFeedsRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private val mPostsCollection =
        firestore.collection("")

    fun fetchHomeFeed() = flow<Resource<List<NewsResponse>>> {

        // Emit loading state
        emit(Resource.Loading)

        val snapshot = mPostsCollection.get().await()
        val homeFeed = snapshot.toObjects(NewsResponse::class.java)

        // Emit success state with data
        emit(Resource.Success(homeFeed))

    }.catch { exception ->
        emit(Resource.Error(exception))
    }.flowOn(Dispatchers.IO)

}