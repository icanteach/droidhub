package co.icanteach.apps.android.droidfeeds.auth

import co.icanteach.apps.android.droidfeeds.core.Resource
import co.icanteach.apps.android.droidfeeds.core.doOnSuccess
import co.icanteach.apps.android.droidfeeds.data.repository.AuthenticationDataRepository
import co.icanteach.apps.android.droidfeeds.data.repository.BookmarkRepository
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 *  The AuthenticationUseCase class is responsible for all authentication actions throughout the application.
 */

class AuthenticationUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationDataRepository,
    private val bookmarkRepository: BookmarkRepository
) {


    /**
     * authenticate user via AuthenticationDataRepository.
     *
     * If the authentication process is successful, create an empty bookmark collection for the user.
     */
    fun authenticate(): Flow<Resource<AuthResult>> {
        return authenticationRepository
            .authenticate()
            .doOnSuccess {
                bookmarkRepository.createBookmarkDocument(getUserId()).collect {}
            }
    }

    fun getUserId(): String {
        return authenticationRepository.getUserId()
    }
}