package co.icanteach.apps.android.droidhub.features.user.data

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(): Flow<UserEntity?>
    suspend fun getInterests(): Flow<List<InterestEntity>>
    fun insertUser(user: UserEntity)
    suspend fun insertInterest(interest: InterestEntity)
    suspend fun deleteInterest(interest: InterestEntity)
    suspend fun insertAllInterests(interests: List<InterestEntity>)
}