package co.icanteach.apps.android.droidhub.data.repository

import co.icanteach.apps.android.droidhub.core.Resource
import co.icanteach.apps.android.droidhub.data.IoDispatcher
import co.icanteach.apps.android.droidhub.data.repository.model.UserResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationDataRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) {

    /**
     * authenticate user via FirebaseAuth signInAnonymously.
     *
     * If the authentication process is successful,
     * return Resource.Success(UserResponse()),
     * otherwise return Resource.Error(exception)
     */
    fun authenticate() = flow {
        try {
            val user = firebaseAuth.signInAnonymously().await()
            emit(Resource.Success(UserResponse(user.user?.uid ?: "")))
        } catch (exception: Exception) {
            emit(Resource.Error(exception))
        }
    }.flowOn(dispatcher)

    fun getUserId() = firebaseAuth.currentUser?.uid ?: ""
}