package co.icanteach.apps.android.droidhub.components.items.composables

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import co.icanteach.apps.android.droidhub.components.items.composables.ComponentImage
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme


@Composable
fun ComponentBookmark(
    modifier: Modifier = Modifier, image: Painter, contentDescription: String, onClick: () -> (Unit)
) {

    Box(
        modifier = modifier.background(Color.White, shape = CircleShape)
    ) {
        ComponentImage(
            image = image,
            contentDescription = contentDescription,
            modifier = modifier,
            onClick = onClick
        )
    }
}

@Preview
@Composable
fun ComponentBookmarkPreview() {
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ComponentBookmarkPreview_Dark() {
    DroidhubTheme {

    }
}