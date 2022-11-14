package co.icanteach.apps.android.droidhub.features.feed.domain

import co.icanteach.apps.android.droidhub.features.feedcomponents.ComponentItem
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
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

class FetchFeedUseCase @Inject constructor(
    private val firestore: FirebaseFirestore, private val componentMapper: FeedComponentMapper
) {

    suspend fun fetchFeed(): List<ComponentItem> {

        val feedCollection = firestore.collection(HOME_FEED_PATH)
        val allFeedDocuments = feedCollection.get().await()

        val feedResponse = allFeedDocuments.documents.map {
            readFeedItemResponse(it)
        }
        return componentMapper.mapTo(feedResponse)
    }

    suspend fun fetchFeedBy(queryValues: List<String>): List<ComponentItem> {
        val feedCollection = firestore.collection(HOME_FEED_PATH)
        val allFeedDocuments = feedCollection.whereIn("category-id", queryValues).get().await()
        val feedResponse = allFeedDocuments.documents.map {
            readFeedItemResponse(it)
        }
        return componentMapper.mapTo(feedResponse)
    }

    private fun readFeedItemResponse(documentSnapshot: DocumentSnapshot): FeedItemResponse {
        return FeedItemResponse(
            category = (documentSnapshot.data?.get(CATEGORY_FIELD) ?: "") as String,
            title = (documentSnapshot.data?.get(TITLE_FIELD) ?: "") as String,
            description = (documentSnapshot.data?.get(DESC_FIELD) ?: "") as String,
            image = (documentSnapshot.data?.get(IMAGE_FIELD) ?: "") as String,
            originUrl = (documentSnapshot.data?.get(TITLE_FIELD) ?: "") as String,
            type = (documentSnapshot.data?.get(TYPE_FIELD) ?: "") as String,
            sharedBy = (documentSnapshot.data?.get(SHARED_BY_FIELD) ?: "") as String,
            source = (documentSnapshot.data?.get(SOURCE_FIELD) ?: "") as String
        )
    }
}


data class FeedItemResponse(
    val type: String,
    val category: String,
    val description: String,
    val title: String,
    val image: String,
    val originUrl: String,
    val sharedBy: String,
    val source: String,
)