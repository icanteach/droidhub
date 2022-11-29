package co.icanteach.apps.android.droidhub.components.items

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.icanteach.apps.android.droidhub.components.core.ComponentFactory
import co.icanteach.apps.android.droidhub.components.core.PodcastComponentItem
import co.icanteach.apps.android.droidhub.components.items.R.drawable
import co.icanteach.apps.android.droidhub.components.items.R.string
import co.icanteach.apps.android.droidhub.components.items.composables.*
import co.icanteach.apps.android.droidhub.design.composables.VerticalSpacer
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme


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
                ComponentImage(
                    modifier = imageModifier,
                    image = painterResource(id = drawable.ic_add_bookmark),
                    contentDescription = item.source
                ) {}
                VerticalSpacer(value = 8.dp)
                ComponentChip(item.source)

                val modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)

                ComponentBookmark(
                    modifier = modifier,
                    isSelected = item.addedToBookmark,
                    contentDescription = item.source
                ) {

                }
            }
            VerticalSpacer(value = 4.dp)
            ComponentTitle(item.title)
            VerticalSpacer(value = 4.dp)
            ComponentCaption(
                text = stringResource(
                    id = string.component_item_content_and_on, item.category, item.source
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