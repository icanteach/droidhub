package co.icanteach.apps.android.droidfeeds.data.repository

import co.icanteach.apps.android.droidfeeds.core.Resource
import co.icanteach.apps.android.droidfeeds.data.repository.model.FeedDocumentResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


private const val HOME_FEED_COLLECTION_PATH = "home-feed"
private const val MAIN_FEED_COLLECTION_PATH = "main-feed"

class DroidFeedsRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private val mPostsCollection =
        firestore.collection(HOME_FEED_COLLECTION_PATH)

    fun fetchHomeFeed() = flow {

        // Emit loading state
        emit(Resource.Loading)

        val snapshot = mPostsCollection.document(MAIN_FEED_COLLECTION_PATH).get().await()
        val homeFeed = snapshot.toObject(FeedDocumentResponse::class.java)

        // Emit success state with data
        homeFeed?.let {
            emit(Resource.Success(homeFeed.contents))
        }
    }.catch { exception ->
        emit(Resource.Error(exception))
    }.flowOn(Dispatchers.IO)
}