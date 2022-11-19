package co.icanteach.apps.android.droidhub.features.user.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao,
    private val interestsDao: InterestsDao,
    private val bookmarksDao: BookmarksDao
) : UserRepository {
    override fun getUser(): Flow<UserEntity> {
        return dao.getUser()
    }

    override suspend fun getInterests(): Flow<List<InterestEntity>> {
        return interestsDao.getInterests()
    }

    override fun insertUser(user: UserEntity) {
        dao.insertUser(user)
    }

    override suspend fun insertInterest(interest: InterestEntity) {
        interestsDao.insert(interest)
    }

    override suspend fun deleteInterest(interest: InterestEntity) {
        interestsDao.delete(interest)
    }

    override suspend fun insertAllInterests(interests: List<InterestEntity>) {
        interestsDao.insertAll(interests)
    }

    override suspend fun insertBookmark(bookmark: BookmarkEntity) {
        bookmarksDao.insert(bookmark)
    }

    override suspend fun deleteBookmark(bookmark: BookmarkEntity) {
        bookmarksDao.delete(bookmark)
    }

    override suspend fun insertAllBookmarks(bookmars: List<BookmarkEntity>) {
        bookmarksDao.insertAll(bookmars)
    }

    override suspend fun getBookmarks(): Flow<List<BookmarkEntity>> {
        return bookmarksDao.getBookmarks()
    }
}