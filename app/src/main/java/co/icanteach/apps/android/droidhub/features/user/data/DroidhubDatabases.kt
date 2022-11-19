package co.icanteach.apps.android.droidhub.features.user.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import co.icanteach.apps.android.droidhub.features.user.ListStringConverter


@Database(
    entities = [UserEntity::class], version = 1, exportSchema = false
)
@TypeConverters(ListStringConverter::class)
abstract class UserDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    companion object {
        const val DATABASE_NAME = "droidhub_user_db"
    }
}

@Database(
    entities = [InterestEntity::class], version = 1, exportSchema = false
)
abstract class InterestsDatabase : RoomDatabase() {

    abstract val interestsDao: InterestsDao

    companion object {
        const val DATABASE_NAME = "droidhub_interests_db"
    }
}

@Database(
    entities = [BookmarkEntity::class], version = 1, exportSchema = false
)
abstract class BookmarksDatabase : RoomDatabase() {

    abstract val bookmarksDao: BookmarksDao

    companion object {
        const val DATABASE_NAME = "droidhub_bookmarks_db"
    }
}