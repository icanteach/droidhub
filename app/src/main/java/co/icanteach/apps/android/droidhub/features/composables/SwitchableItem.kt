package co.icanteach.apps.android.droidhub.features.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.icanteach.apps.android.droidhub.R
import co.icanteach.apps.android.droidhub.design.composables.HorizontalSpacer

@Composable
internal fun SwitchableItem(
    title: String,
    description: String,
    icon: Painter,
    isChecked: Boolean,
    onItemChanged: (Boolean) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon, contentDescription = title, tint = MaterialTheme.colors.onBackground
        )

        HorizontalSpacer(value = 16.dp)

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h6,
                fontSize = 16.sp
            )

            HorizontalSpacer(value = 1.dp)

            Text(
                text = description,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body2
            )
        }

        Switch(checked = isChecked, onCheckedChange = onItemChanged)
    }
}

@Preview(name = "On")
@Composable
fun SwitchableItem_On_Preview() {
    SwitchableItem(
        title = stringResource(id = R.string.account_dark_mode_title),
        description = stringResource(id = R.string.account_dark_mode_desc),
        icon = painterResource(id = R.drawable.ic_dark_mode),
        isChecked = true
    )
}

@Preview(name = "Off")
@Composable
fun SwitchableItem_Off_Preview() {
    SwitchableItem(
        title = stringResource(id = R.string.account_dark_mode_title),
        description = stringResource(id = R.string.account_dark_mode_desc),
        icon = painterResource(id = R.drawable.ic_dark_mode),
        isChecked = false
    )
}