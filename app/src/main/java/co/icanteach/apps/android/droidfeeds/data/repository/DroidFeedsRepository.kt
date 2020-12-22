package co.icanteach.apps.android.droidfeeds.data.repository

import co.icanteach.apps.android.droidfeeds.core.Resource
import co.icanteach.apps.android.droidfeeds.data.repository.model.HomeFeedDocument
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
        firestore.collection("home-feed")

    fun fetchHomeFeed() = flow {

        // Emit loading state
        emit(Resource.Loading)

        val snapshot = mPostsCollection.document("main-feed").get().await()
        val homeFeed = snapshot.toObject(HomeFeedDocument::class.java)

        // Emit success state with data
        homeFeed?.let {
            emit(Resource.Success(homeFeed.contents))
        }
    }.catch { exception ->
        emit(Resource.Error(exception))
    }.flowOn(Dispatchers.IO)
}