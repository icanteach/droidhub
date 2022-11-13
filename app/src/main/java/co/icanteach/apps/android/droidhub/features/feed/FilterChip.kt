package co.icanteach.apps.android.droidhub.features.feed

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.icanteach.apps.android.droidhub.features.feed.domain.FilterItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterChip(
    item: FilterItem,
    onSelectedCategoryChanged: (String) -> Unit,
) {

    Chip(modifier = Modifier.padding(horizontal = 8.dp), onClick = {
        onSelectedCategoryChanged(item.id)
    }) {
        Text(
            text = item.displayName,
            style = MaterialTheme.typography.body2,
            color = if (item.isSelected) MaterialTheme.colors.secondary else MaterialTheme.colors.primary,
        )
    }
}