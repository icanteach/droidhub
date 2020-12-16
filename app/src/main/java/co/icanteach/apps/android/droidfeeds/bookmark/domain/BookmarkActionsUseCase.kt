package co.icanteach.apps.android.droidfeeds.bookmark.domain

import co.icanteach.apps.android.droidfeeds.auth.AuthenticationUseCase
import co.icanteach.apps.android.droidfeeds.core.Resource
import co.icanteach.apps.android.droidfeeds.data.repository.BookmarkRepository
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkActionsUseCase @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase,
    private val bookmarkRepository: BookmarkRepository
) {

    fun addBookmark(
        originUrl: String,
        image: String,
        title: String,
        description: String
    ): Flow<Resource<Void>> {
        val bookmarkItem = hashMapOf(
            "originUrl" to originUrl,
            "image" to image,
            "title" to title,
            "description" to description
        )

        val documentId = authenticationUseCase.getUserId()

        return bookmarkRepository.addBookmark(bookmarkItem, documentId)
    }

    fun removeBookmark(originUrl: String, title: String, description: String, image: String): Flow<Resource<Void>> {
        val bookmarkItem = hashMapOf(
            "originUrl" to originUrl,
            "image" to image,
            "title" to title,
            "description" to description
        )

        val documentId = authenticationUseCase.getUserId()
        return bookmarkRepository.removeBookmark(bookmarkItem, documentId)
    }
}