package co.icanteach.apps.android.droidhub.features.bookmark.domain

import co.icanteach.apps.android.droidhub.components.core.ID_FIELD
import co.icanteach.apps.android.droidhub.features.user.data.BookmarkEntity
import co.icanteach.apps.android.droidhub.features.user.data.UserRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

private const val USERS_BOOKMARKS = "user_bookmarks"

class RemoveFromBookmarkUseCase @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userRepository: UserRepository,
) {

    suspend fun removeFromBookmark(item: Map<String, String>) {
        val user = userRepository.getUser().firstOrNull()
        val userBookmarks =
            firestore.collection(USERS_BOOKMARKS).document((user?.id ?: 0) as String)
        userBookmarks.update("bookmark_items", FieldValue.arrayRemove(item))
        val entity = item[ID_FIELD]?.let { BookmarkEntity(id = it) }
        entity?.let { userRepository.deleteBookmark(it) }
    }
}