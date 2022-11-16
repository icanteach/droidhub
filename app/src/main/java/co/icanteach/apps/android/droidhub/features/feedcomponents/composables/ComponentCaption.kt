package co.icanteach.apps.android.droidhub.features.feedcomponents.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme

@Composable
fun ComponentCaption(
    text: String
) {
    Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = text,
        style = MaterialTheme.typography.caption
    )
}

@Preview
@Composable
fun ComponentCaption() {
    ComponentCaption("Modular Apps - Shared by Droidhub")
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ComponentCaption_Dark() {
    DroidhubTheme {
        ComponentCaption("Modular Apps - Shared by Droidhub")
    }
}