package co.icanteach.apps.android.droidhub.features.user.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarksDao {

    @Query("SELECT * FROM bookmarks")
    fun getBookmarks(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: BookmarkEntity)

    @Delete
    suspend fun delete(entity: BookmarkEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<BookmarkEntity>)
}