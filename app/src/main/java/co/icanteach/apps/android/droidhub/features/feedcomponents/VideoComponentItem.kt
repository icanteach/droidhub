package co.icanteach.apps.android.droidhub.features.feedcomponents

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

data class VideoComponentItem(
    override val id: String,
    val category: String,
    val title: String,
    val source: String,
    val imageUrl: String
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
        Column(
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {

                val imageModifier = Modifier
                    .heightIn(min = 160.dp)
                    .fillMaxWidth()

                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = item.imageUrl,
                    contentDescription = item.title,
                    modifier = imageModifier
                )

                Image(
                    painter = painterResource(R.drawable.ic_play),
                    contentDescription = item.title,
                    modifier = modifier
                        .clip(MaterialTheme.shapes.small)
                        .align(Alignment.Center)
                        .size(48.dp),
                )
            }
            VerticalSpacer(value = 4.dp)
            Text(
                color = MaterialTheme.colors.onBackground,
                text = item.title,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )

            VerticalSpacer(value = 4.dp)
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
    VideoComponent(ComponentFactory.createVideoComponentItem())
}