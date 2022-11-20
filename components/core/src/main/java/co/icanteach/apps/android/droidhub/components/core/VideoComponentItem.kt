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
            ID_FIELD to id,
            SHARED_BY_FIELD to sharedBy,
            IMAGE_FIELD to imageUrl,
            CATEGORY_FIELD to category,
            TITLE_FIELD to title,
            SOURCE_FIELD to source,
            TYPE_FIELD to COMPONENT_VIDEO,
        )
    }
}