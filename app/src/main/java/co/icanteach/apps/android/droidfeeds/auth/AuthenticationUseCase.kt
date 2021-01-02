package co.icanteach.apps.android.droidfeeds.auth

import co.icanteach.apps.android.droidfeeds.core.Resource
import co.icanteach.apps.android.droidfeeds.core.extensions.doOnSuccess
import co.icanteach.apps.android.droidfeeds.data.repository.AuthenticationDataRepository
import co.icanteach.apps.android.droidfeeds.data.repository.BookmarkRepository
import co.icanteach.apps.android.droidfeeds.data.repository.model.UserResponse
import kotlinx.coroutines.flow.Flow
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
    fun authenticate(): Flow<Resource<UserResponse>> {
        return authenticationRepository
            .authenticate()
            .doOnSuccess { userResponse ->
                bookmarkRepository.createBookmarkDocument(userResponse.userId)
            }
    }

    fun getUserId(): String {
        return authenticationRepository.getUserId()
    }
}