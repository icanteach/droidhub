package co.icanteach.apps.android.droidhub.features.feedcomponents

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.icanteach.apps.android.droidhub.R
import co.icanteach.apps.android.droidhub.design.composables.VerticalSpacer
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme
import co.icanteach.apps.android.droidhub.features.feedcomponents.composables.ComponentCaption
import co.icanteach.apps.android.droidhub.features.feedcomponents.composables.ComponentDescription
import co.icanteach.apps.android.droidhub.features.feedcomponents.composables.ComponentImage
import co.icanteach.apps.android.droidhub.features.feedcomponents.composables.ComponentTitle
import coil.compose.AsyncImage


data class ArticleComponentItem(
    override val id: String,
    val category: String,
    val title: String,
    val desc: String,
    val imageUrl: String,
    val sharedBy: String
) : ComponentItem

@Composable
fun ArticleComponent(
    item: ArticleComponentItem
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp), elevation = 4.dp
    ) {
        Column {

            Box {
                val imageModifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()

                ComponentImage(
                    modifier = imageModifier,
                    imageUrl = item.imageUrl,
                    contentDescription = item.title
                )
            }

            VerticalSpacer(value = 8.dp)
            ComponentTitle(item.title)
            VerticalSpacer(value = 4.dp)
            ComponentDescription(item.desc)
            VerticalSpacer(value = 4.dp)
            ComponentCaption(
                text = stringResource(
                    id = R.string.feed_post_content_and_posted_by, item.category, item.sharedBy
                )
            )
            VerticalSpacer(value = 8.dp)
        }
    }


}

@Preview
@Composable
fun ArticleComponent_Preview() {
    ArticleComponent(ComponentFactory.createArticleComponentItem())
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ArticleComponent_DarkModePreview() {
    DroidhubTheme {
        Surface {
            ArticleComponent(ComponentFactory.createArticleComponentItem())
        }
    }
}