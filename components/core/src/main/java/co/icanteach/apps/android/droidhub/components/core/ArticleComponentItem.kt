package co.icanteach.apps.android.droidhub.components.core

data class ArticleComponentItem(
    override val id: String,
    val category: String,
    val title: String,
    val desc: String,
    val imageUrl: String,
    val sharedBy: String,
    override val addedToBookmark: Boolean
) : ComponentItem {

    override fun toMap(): Map<String, String> {
        return mapOf(
            ID_FIELD to id,
            SHARED_BY_FIELD to sharedBy,
            CATEGORY_FIELD to category,
            TITLE_FIELD to title,
            DESC_FIELD to desc,
            IMAGE_FIELD to imageUrl,
            TYPE_FIELD to COMPONENT_ARTICLE_TYPE,
        )
    }
}