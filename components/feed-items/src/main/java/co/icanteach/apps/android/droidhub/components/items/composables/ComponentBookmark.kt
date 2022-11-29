package co.icanteach.apps.android.droidhub.components.items.composables

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import co.icanteach.apps.android.droidhub.components.items.R
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme


@Composable
fun ComponentBookmark(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    contentDescription: String,
    onClick: () -> (Unit)
) {

    val painterResource = if (isSelected) {
        painterResource(id = R.drawable.ic_already_bookmark)
    } else {
        painterResource(id = R.drawable.ic_add_bookmark)
    }
    Box(
        modifier = modifier.background(Color.White, shape = CircleShape)
    ) {
        ComponentImage(
            image = painterResource,
            contentDescription = contentDescription,
            modifier = modifier,
            onClick = onClick
        )
    }
}

@Preview
@Composable
fun AddBookmarkPreview() {
    ComponentBookmark(
        isSelected = false, contentDescription = "Add Bookmark"
    ) {}
}

@Preview
@Composable
fun AlreadyBookmarkPreview() {
    ComponentBookmark(
        isSelected = true, contentDescription = "Content Desc."
    ) {}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddBookmarkPreview_Dark() {
    DroidhubTheme {
        ComponentBookmark(
            isSelected = false, contentDescription = "Content Desc."
        ) {}
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AlreadyBookmarkPreview_Dark() {
    DroidhubTheme {
        ComponentBookmark(
            isSelected = true, contentDescription = "Content Desc."
        ) {}
    }
}