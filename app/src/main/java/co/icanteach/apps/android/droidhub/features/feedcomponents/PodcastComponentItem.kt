package co.icanteach.apps.android.droidhub.features.feedcomponents

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.icanteach.apps.android.droidhub.R
import co.icanteach.apps.android.droidhub.design.composables.HorizontalSpacer
import co.icanteach.apps.android.droidhub.design.composables.VerticalSpacer

data class PodcastComponentItem(
    override val id: String, val title: String, val source: String
) : ComponentItem

@Composable
fun PodcastComponent(
    item: PodcastComponentItem, modifier: Modifier = Modifier
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp), elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.ic_play),
                contentDescription = item.title,
                modifier = modifier
                    .padding(horizontal = 8.dp)
                    .clip(MaterialTheme.shapes.small)
                    .align(Alignment.CenterVertically),
            )
            HorizontalSpacer(value = 8.dp)
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    color = MaterialTheme.colors.onBackground,
                    text = item.title,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )

                VerticalSpacer(value = 4.dp)

                Text(
                    color = MaterialTheme.colors.onBackground,
                    text = item.source,
                    style = MaterialTheme.typography.subtitle2,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }
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
    PodcastComponent(ComponentFactory.createPodcastComponentItem())
}