package co.icanteach.apps.android.droidhub.features.submission.domain

import co.icanteach.apps.android.droidhub.features.core.data.IoDispatcher
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed class SubmissionFormResult
object SubmissionSuccess : SubmissionFormResult()
object SubmissionError : SubmissionFormResult()


class SendSubmissionFormUseCase @Inject constructor(
    val firestore: FirebaseFirestore,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun send(
        url: String, postType: String
    ): SubmissionFormResult {

        // Create a new post
        val post = hashMapOf(
            "url" to url, "postType" to postType
        )
        return withContext(ioDispatcher) {
            try {
                val usersCollection = firestore.collection("user_submitted_posts")
                usersCollection.add(post).await()
                SubmissionSuccess
            } catch (exception: Exception) {
                SubmissionError
            }
        }
    }
}