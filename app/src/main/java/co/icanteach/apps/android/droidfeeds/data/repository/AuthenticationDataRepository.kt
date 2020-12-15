package co.icanteach.apps.android.droidfeeds.data.repository

import co.icanteach.apps.android.droidfeeds.core.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class AuthenticationDataRepository constructor(
    private val firebaseAuth: FirebaseAuth

) {

    fun authenticate() = flow {
        try {
            val user = firebaseAuth.signInAnonymously().await()
            emit(Resource.Success(user))
        } catch (exception: Exception) {
            emit(Resource.Error(exception))
        }
    }.flowOn(Dispatchers.IO)

}