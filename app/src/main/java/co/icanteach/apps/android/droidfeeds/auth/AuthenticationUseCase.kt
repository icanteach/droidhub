package co.icanteach.apps.android.droidfeeds.auth

import co.icanteach.apps.android.droidfeeds.core.Resource
import co.icanteach.apps.android.droidfeeds.data.repository.AuthenticationDataRepository
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationDataRepository
) {


    fun authenticate(): Flow<Resource<AuthResult>> {
        return authenticationRepository
            .authenticate()
    }
}