package co.icanteach.apps.android.droidhub.components.items.composables

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme


@Composable
fun ComponentTitle(
    text: String
) {

    Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        color = MaterialTheme.colors.onBackground,
        text = text,
        style = MaterialTheme.typography.subtitle1,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
    )
}

@Preview
@Composable
fun ComponentTitle() {
    ComponentTitle("This is title!")
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ComponentTitle_Dark() {
    DroidhubTheme {
        ComponentTitle("This is title!")
    }
}