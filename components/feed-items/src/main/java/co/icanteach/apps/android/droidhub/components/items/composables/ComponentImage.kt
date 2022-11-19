package co.icanteach.apps.android.droidhub.components.items.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun ComponentImage(
    modifier: Modifier = Modifier, imageUrl: String, contentDescription: String
) {

    AsyncImage(
        contentScale = ContentScale.Crop,
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@Composable
fun ComponentImage(
    modifier: Modifier = Modifier,
    image: Painter,
    contentDescription: String,
    onClick: () -> (Unit)
) {

    Image(
        contentScale = ContentScale.Crop,
        painter = image,
        contentDescription = contentDescription,
        modifier = modifier.clickable {
            onClick.invoke()
        }
    )
}