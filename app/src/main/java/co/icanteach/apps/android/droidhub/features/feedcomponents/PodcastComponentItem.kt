package co.icanteach.apps.android.droidhub.features.feedcomponents

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import co.icanteach.apps.android.droidhub.R
import co.icanteach.apps.android.droidhub.design.composables.VerticalSpacer
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme
import co.icanteach.apps.android.droidhub.features.feedcomponents.composables.ComponentCaption
import co.icanteach.apps.android.droidhub.features.feedcomponents.composables.ComponentChip
import co.icanteach.apps.android.droidhub.features.feedcomponents.composables.ComponentImage
import co.icanteach.apps.android.droidhub.features.feedcomponents.composables.ComponentTitle

data class PodcastComponentItem(
    override val id: String, val title: String, val source: String, val category: String
) : ComponentItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PodcastComponent(
    item: PodcastComponentItem
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp), elevation = 4.dp
    ) {
        Column {
            Box {
                val imageModifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                val imageSource: Painter = painterResource(id = R.drawable.ic_launcher_background)
                ComponentImage(
                    modifier = imageModifier, image = imageSource, contentDescription = item.source
                )
                VerticalSpacer(value = 8.dp)
                ComponentChip(item.source)
            }
            VerticalSpacer(value = 4.dp)
            ComponentTitle(item.title)
            VerticalSpacer(value = 4.dp)
            ComponentCaption(
                text = stringResource(
                    id = R.string.feed_post_content_and_on, item.category, item.source
                )
            )
            VerticalSpacer(value = 8.dp)
        }
    }
}

@Preview
@Composable
fun PodcastComponent() {
    PodcastComponent(ComponentFactory.createPodcastComponentItem())
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PodcastComponent_DarkModePreview() {
    DroidhubTheme {
        Surface {
            PodcastComponent(ComponentFactory.createPodcastComponentItem())
        }
    }
}