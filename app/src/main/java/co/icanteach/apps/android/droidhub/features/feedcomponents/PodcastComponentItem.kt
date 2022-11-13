package co.icanteach.apps.android.droidhub.features.feedcomponents

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.icanteach.apps.android.droidhub.R
import co.icanteach.apps.android.droidhub.design.composables.VerticalSpacer
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme

data class PodcastComponentItem(
    override val id: String, val title: String, val source: String, val category: String
) : ComponentItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PodcastComponent(
    item: PodcastComponentItem, modifier: Modifier = Modifier
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp), elevation = 4.dp
    ) {
        Column {
            VerticalSpacer(value = 8.dp)
            Chip(
                modifier = Modifier.padding(horizontal = 8.dp),
                onClick = {},
                border = ChipDefaults.outlinedBorder,
                colors = ChipDefaults.outlinedChipColors(),
            ) {

                Text(
                    style = MaterialTheme.typography.caption,
                    maxLines = 1,
                    color = MaterialTheme.colors.onBackground,
                    text = item.category,
                )
            }

            VerticalSpacer(value = 4.dp)
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                color = MaterialTheme.colors.onBackground,
                text = item.title,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )

            VerticalSpacer(value = 4.dp)

            Text(
                modifier = Modifier.padding(horizontal = 8.dp), text = stringResource(
                    id = R.string.feed_post_date_and_posted_by, "item.date", "item.sharedBy"
                ), style = MaterialTheme.typography.caption
            )

            VerticalSpacer(value = 4.dp)

            Text(
                modifier = Modifier.padding(horizontal = 8.dp), text = stringResource(
                    id = R.string.feed_post_on, item.source
                ), style = MaterialTheme.typography.caption
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