package co.icanteach.apps.android.droidhub.features.user.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface InterestsDao {

    @Query("SELECT * FROM interests")
    fun getInterests(): Flow<List<InterestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: InterestEntity)

    @Delete
    suspend fun delete(entity: InterestEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<InterestEntity>)
}