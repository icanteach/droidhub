package co.icanteach.apps.android.droidhub.features.bookmark

import co.icanteach.apps.android.droidhub.components.core.ComponentItem
import co.icanteach.apps.android.droidhub.components.core.ComponentItemResponse
import co.icanteach.apps.android.droidhub.components.core.ComponentMapper
import co.icanteach.apps.android.droidhub.features.user.data.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val USERS_BOOKMARKS = "user_bookmarks"
private const val CATEGORY_FIELD = "category"
private const val TITLE_FIELD = "title"
private const val DESC_FIELD = "description"
private const val TYPE_FIELD = "type"
private const val IMAGE_FIELD = "image"
private const val SHARED_BY_FIELD = "sharedBy"
private const val SOURCE_FIELD = "source"
private const val ID_FIELD = "id"

class FetchUserBookmarkUseCase @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userRepository: UserRepository,
    private val mapper: ComponentMapper
) {

    suspend fun fetchBookmarks(
    ): List<ComponentItem> {

        val user = userRepository.getUser().firstOrNull()
        val userBookmarks =
            firestore.collection(USERS_BOOKMARKS).document((user?.id ?: 0) as String).get().await()

        return userBookmarks.data?.mapNotNull { result ->
            val bookmarkResult = result.value as HashMap<*, *>
            mapper.mapTo(
                ComponentItemResponse(
                    category = bookmarkResult[CATEGORY_FIELD].toString(),
                    title = bookmarkResult[TITLE_FIELD].toString(),
                    description = bookmarkResult[DESC_FIELD].toString(),
                    image = bookmarkResult[IMAGE_FIELD].toString(),
                    originUrl = bookmarkResult[TITLE_FIELD].toString(),
                    type = bookmarkResult[TYPE_FIELD].toString(),
                    sharedBy = bookmarkResult[SHARED_BY_FIELD].toString(),
                    source = bookmarkResult[SOURCE_FIELD].toString(),
                    id = bookmarkResult[ID_FIELD].toString(),
                )
            )
        } ?: listOf()
    }
}