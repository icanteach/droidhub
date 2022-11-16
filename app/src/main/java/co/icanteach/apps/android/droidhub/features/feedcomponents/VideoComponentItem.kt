package co.icanteach.apps.android.droidhub.features.feedcomponents

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.icanteach.apps.android.droidhub.R
import co.icanteach.apps.android.droidhub.design.composables.VerticalSpacer
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme
import co.icanteach.apps.android.droidhub.features.feedcomponents.composables.ComponentChip
import co.icanteach.apps.android.droidhub.features.feedcomponents.composables.ComponentImage
import co.icanteach.apps.android.droidhub.features.feedcomponents.composables.ComponentTitle

data class VideoComponentItem(
    override val id: String,
    val category: String,
    val title: String,
    val source: String,
    val imageUrl: String,
    val sharedBy: String
) : ComponentItem

@Composable
fun VideoComponent(
    item: VideoComponentItem, modifier: Modifier = Modifier
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp), elevation = 4.dp
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {

                val imageModifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()

                ComponentImage(
                    modifier = imageModifier,
                    imageUrl = item.imageUrl,
                    contentDescription = item.title
                )

                VerticalSpacer(value = 8.dp)

                ComponentChip(
                    text = item.source
                )

                Image(
                    colorFilter = ColorFilter.tint(Color.White),
                    painter = painterResource(R.drawable.ic_play),
                    contentDescription = item.title,
                    modifier = modifier
                        .clip(MaterialTheme.shapes.small)
                        .align(Alignment.Center)
                        .size(48.dp),
                )
            }
            VerticalSpacer(value = 4.dp)
            ComponentTitle(item.title)

            VerticalSpacer(value = 4.dp)

            Text(
                modifier = Modifier.padding(horizontal = 8.dp), text = stringResource(
                    id = R.string.feed_post_content_and_posted_by, item.category, item.sharedBy
                ), style = MaterialTheme.typography.caption
            )

            VerticalSpacer(value = 8.dp)
        }
    }


}

@Preview
@Composable
fun VideoComponent() {
    VideoComponent(ComponentFactory.createVideoComponentItem())
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun VideoComponent_DarkModePreview() {
    DroidhubTheme {
        Surface {
            VideoComponent(ComponentFactory.createVideoComponentItem())
        }
    }
}