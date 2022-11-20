package co.icanteach.apps.android.droidhub.components.core

data class PodcastComponentItem(
    override val id: String,
    val title: String,
    val source: String,
    val category: String,
    override val addedToBookmark: Boolean
) : ComponentItem {
    override fun toMap(): Map<String, String> {
        return mapOf(
            ID_FIELD to id,
            CATEGORY_FIELD to category,
            TITLE_FIELD to title,
            SOURCE_FIELD to source,
            TYPE_FIELD to COMPONENT_PODCAST,
        )
    }
}