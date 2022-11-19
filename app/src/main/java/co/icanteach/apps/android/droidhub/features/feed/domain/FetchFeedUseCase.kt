package co.icanteach.apps.android.droidhub.features.feed.domain

import co.icanteach.apps.android.droidhub.components.core.ComponentItem
import co.icanteach.apps.android.droidhub.components.core.ComponentItemResponse
import co.icanteach.apps.android.droidhub.components.core.ComponentMapper
import co.icanteach.apps.android.droidhub.features.interests.InterestItem
import co.icanteach.apps.android.droidhub.features.user.data.UserRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val HOME_FEED_PATH = "home-feed"
private const val CATEGORY_FIELD = "category"
private const val TITLE_FIELD = "title"
private const val DESC_FIELD = "description"
private const val TYPE_FIELD = "type"
private const val IMAGE_FIELD = "image"
private const val SHARED_BY_FIELD = "sharedBy"
private const val SOURCE_FIELD = "source"
private const val ID_FIELD = "id"

class FetchFeedUseCase @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val componentMapper: ComponentMapper,
    private val userRepository: UserRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun fetchFeed(): Flow<List<ComponentItem>> {
        return userRepository.getInterests().mapLatest { bookmarks ->
            val feedCollection = firestore.collection(HOME_FEED_PATH)
            val allFeedDocuments = feedCollection.get().await()

            val feedResponse = allFeedDocuments.documents.map {
                readFeedItemResponse(it)
            }
            componentMapper.mapTo(feedResponse, bookmarks.map { it.id })
        }
    }

    suspend fun fetchFeedBy(queryValues: List<String>): List<ComponentItem> {
        val feedCollection = firestore.collection(HOME_FEED_PATH)
        val allFeedDocuments = feedCollection.whereIn("category-id", queryValues).get().await()
        val feedResponse = allFeedDocuments.documents.map {
            readFeedItemResponse(it)
        }
        return componentMapper.mapTo(feedResponse)
    }

    private fun readFeedItemResponse(documentSnapshot: DocumentSnapshot): ComponentItemResponse {
        return ComponentItemResponse(
            category = (documentSnapshot.data?.get(CATEGORY_FIELD) ?: "") as String,
            title = (documentSnapshot.data?.get(TITLE_FIELD) ?: "") as String,
            description = (documentSnapshot.data?.get(DESC_FIELD) ?: "") as String,
            image = (documentSnapshot.data?.get(IMAGE_FIELD) ?: "") as String,
            originUrl = (documentSnapshot.data?.get(TITLE_FIELD) ?: "") as String,
            type = (documentSnapshot.data?.get(TYPE_FIELD) ?: "") as String,
            sharedBy = (documentSnapshot.data?.get(SHARED_BY_FIELD) ?: "") as String,
            source = (documentSnapshot.data?.get(SOURCE_FIELD) ?: "") as String,
            id = (documentSnapshot.data?.get(ID_FIELD) ?: "") as String
        )
    }
}


