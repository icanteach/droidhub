package co.icanteach.apps.android.droidhub.features.user.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = false) val id: String
)