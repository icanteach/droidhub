package co.icanteach.apps.android.droidhub.components.items.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComponentChip(
    text: String
) {

    Chip(
        modifier = Modifier.padding(horizontal = 8.dp),
        onClick = {},
        border = ChipDefaults.outlinedBorder,
        colors = ChipDefaults.outlinedChipColors(),
    ) {
        Text(
            style = MaterialTheme.typography.caption,
            maxLines = 1,
            color = MaterialTheme.colors.onBackground,
            text = text
        )
    }
}


@Preview
@Composable
private fun ComponentChip() {
    ComponentChip("Modular Apps")
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ComponentChip_Dark() {
    DroidhubTheme {
        ComponentChip("Modular Apps")
    }
}