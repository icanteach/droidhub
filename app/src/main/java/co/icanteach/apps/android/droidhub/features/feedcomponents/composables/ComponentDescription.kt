package co.icanteach.apps.android.droidhub.features.feedcomponents.composables

import android.content.res.Configuration
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
fun ComponentDescription(
    text: String
) {
    Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = text,
        style = MaterialTheme.typography.subtitle2,
        maxLines = 3,
        color = MaterialTheme.colors.onBackground,
        overflow = TextOverflow.Ellipsis,
    )
}

@Preview
@Composable
fun ComponentDescription() {
    ComponentDescription("lorem ipsum dolor sit amet consectetur adipiscing elit")
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ComponentDescription_Dark() {
    DroidhubTheme {
        ComponentDescription("lorem ipsum dolor sit amet consectetur adipiscing elit")
    }
}