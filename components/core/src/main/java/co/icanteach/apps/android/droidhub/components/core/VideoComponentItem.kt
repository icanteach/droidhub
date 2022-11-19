package co.icanteach.apps.android.droidhub.components.core

data class VideoComponentItem(
    override val id: String,
    val category: String,
    val title: String,
    val source: String,
    val imageUrl: String,
    val sharedBy: String,
    override val addedToBookmark: Boolean
) : ComponentItem {
    override fun toMap(): Map<String, String> {
        return mapOf(
            "id" to id,
            "sharedBy" to sharedBy,
            "category" to category,
            "title" to title,
            "source" to source,
            "imageUrl" to imageUrl
        )
    }
}