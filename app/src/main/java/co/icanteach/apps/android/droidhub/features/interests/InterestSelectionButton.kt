package co.icanteach.apps.android.droidhub.features.interests

import androidx.compose.runtime.Composable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.icanteach.apps.android.droidhub.R

/**
 *  see
 *  https://github.com/android/compose-samples/blob/master/JetNews/app/src/main/java/com/example/jetnews/ui/interests/SelectTopicButton.kt
 */

@Composable
fun InterestSelectionButton(
    selected: Boolean = false
) {
    val icon = if (selected) Icons.Filled.Done else Icons.Filled.Add
    val iconColor = if (selected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primary
    val borderColor =
        if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(
            alpha = 0.1f
        )
    val backgroundColor = if (selected) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.onPrimary
    }
    Surface(
        color = backgroundColor,
        shape = CircleShape,
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier.size(36.dp, 36.dp)
    ) {
        Image(
            imageVector = icon,
            colorFilter = ColorFilter.tint(iconColor),
            modifier = Modifier.padding(8.dp),
            contentDescription = stringResource(
                id = R.string.account_interested_in_title
            ),
        )
    }
}


@Preview
@Composable
fun InterestSelectionButtonSelected_Preview() {
    InterestSelectionButton(selected = true)
}

@Preview
@Composable
fun InterestSelectionButtonUnselected_Preview() {
    InterestSelectionButton(selected = false)
}