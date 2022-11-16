package co.icanteach.apps.android.droidhub.features.feedcomponents.composables

import androidx.compose.foundation.Image
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
    modifier: Modifier = Modifier, image: Painter, contentDescription: String
) {

    Image(
        contentScale = ContentScale.Crop,
        painter = image,
        contentDescription = contentDescription,
        modifier = modifier
    )
}