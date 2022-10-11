package co.icanteach.apps.android.droidhub.features.feedcomponents

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.icanteach.apps.android.droidhub.R
import co.icanteach.apps.android.droidhub.design.composables.VerticalSpacer
import coil.compose.AsyncImage


data class ArticleComponentItem(
    override val id: String,
    val category: String,
    val title: String,
    val desc: String,
    val imageUrl: String
) : ComponentItem

@Composable
fun ArticleComponent(
    item: ArticleComponentItem, modifier: Modifier = Modifier
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp), elevation = 4.dp
    ) {
        Column {

            val imageModifier = Modifier
                .heightIn(min = 180.dp)
                .fillMaxWidth()

            AsyncImage(
                contentScale = ContentScale.Crop,
                model = item.imageUrl,
                contentDescription = item.title,
                modifier = imageModifier
            )

            VerticalSpacer(value = 4.dp)
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = item.title,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 3,
                color = MaterialTheme.colors.onBackground,
                overflow = TextOverflow.Ellipsis,
            )

            VerticalSpacer(value = 4.dp)

            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = item.desc,
                style = MaterialTheme.typography.subtitle2,
                maxLines = 3,
                color = MaterialTheme.colors.onBackground,
                overflow = TextOverflow.Ellipsis,
            )

            VerticalSpacer(value = 4.dp)
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
    ArticleComponent(ComponentFactory.createArticleComponentItem())
}