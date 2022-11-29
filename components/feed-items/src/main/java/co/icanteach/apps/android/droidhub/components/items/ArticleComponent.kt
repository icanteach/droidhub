package co.icanteach.apps.android.droidhub.components.items

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.icanteach.apps.android.droidhub.components.core.ArticleComponentItem
import co.icanteach.apps.android.droidhub.components.core.ComponentFactory
import co.icanteach.apps.android.droidhub.components.items.composables.*
import co.icanteach.apps.android.droidhub.design.composables.VerticalSpacer
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme

@Composable
fun ArticleComponent(
    item: ArticleComponentItem, onBookmarkItemClicked: () -> (Unit)
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

                val modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)

                ComponentBookmark(
                    modifier = modifier,
                    isSelected = item.addedToBookmark,
                    contentDescription = item.title
                ) {
                    onBookmarkItemClicked.invoke()
                }
            }

            VerticalSpacer(value = 8.dp)
            ComponentTitle(item.title)
            VerticalSpacer(value = 4.dp)
            ComponentDescription(item.desc)
            VerticalSpacer(value = 4.dp)
            ComponentCaption(
                text = stringResource(
                    id = R.string.component_item_content_and_posted_by, item.category, item.sharedBy
                )
            )
            VerticalSpacer(value = 8.dp)
        }
    }
}

@Preview
@Composable
fun ArticleComponent_Preview() {
    ArticleComponent(ComponentFactory.createArticleComponentItem()) {}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ArticleComponent_DarkModePreview() {
    DroidhubTheme {
        Surface {
            ArticleComponent(ComponentFactory.createArticleComponentItem()) {}
        }
    }
}