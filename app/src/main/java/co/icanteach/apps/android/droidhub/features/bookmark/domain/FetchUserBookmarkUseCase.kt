package co.icanteach.apps.android.droidhub.features.bookmark.domain

import co.icanteach.apps.android.droidhub.components.core.*
import co.icanteach.apps.android.droidhub.features.user.data.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val USERS_BOOKMARKS = "user_bookmarks"
private const val USERS_BOOKMARK_ITEMS = "bookmark_items"

class FetchUserBookmarkUseCase @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userRepository: UserRepository,
    private val mapper: ComponentMapper
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun fetchBookmarks(): Flow<List<ComponentItem>> {

        val user = userRepository.getUser().firstOrNull()

        return userRepository.getBookmarks().mapLatest { bookmarks ->

            val userBookmarks =
                firestore.collection(USERS_BOOKMARKS).document((user?.id ?: 0) as String).get()
                    .await()

            val bookMarkResult =
                (userBookmarks.get(USERS_BOOKMARK_ITEMS) as List<*>).mapNotNull { result ->
                    val bookmarkMap = result as HashMap<*, *>
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
                }
            mapper.mapTo(bookMarkResult, bookmarks.map { it.id })
        }
    }
}