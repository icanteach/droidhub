package co.icanteach.apps.android.droidhub.features.bookmark.domain

import co.icanteach.apps.android.droidhub.components.core.*
import co.icanteach.apps.android.droidhub.features.user.data.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val USERS_BOOKMARKS = "user_bookmarks"
private const val USERS_BOOKMARK_ITEMS = "bookmark_items"

class FetchUserBookmarkUseCase @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userRepository: UserRepository,
    private val mapper: ComponentMapper
) {

    suspend fun fetchBookmarks(
    ): List<ComponentItem> {

        val user = userRepository.getUser().firstOrNull()
        val userBookmarks =
            firestore.collection(USERS_BOOKMARKS)
                .document((user?.id ?: 0) as String).get().await()
        val result = (userBookmarks.get(USERS_BOOKMARK_ITEMS) as List<*>).mapNotNull { result ->
            val bookmarkMap = result as HashMap<*, *>
            mapper.mapTo(
                ComponentItemResponse(
                    category = bookmarkMap[CATEGORY_FIELD].toString(),
                    title = bookmarkMap[TITLE_FIELD].toString(),
                    description = bookmarkMap[DESC_FIELD].toString(),
                    image = bookmarkMap[IMAGE_FIELD].toString(),
                    originUrl = bookmarkMap[TITLE_FIELD].toString(),
                    type = bookmarkMap[TYPE_FIELD].toString(),
                    sharedBy = bookmarkMap[SHARED_BY_FIELD].toString(),
                    source = bookmarkMap[SOURCE_FIELD].toString(),
                    id = bookmarkMap[ID_FIELD].toString(),
                )
            )

        }
        return result
    }
}