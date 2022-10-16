package co.icanteach.apps.android.droidhub.features.account.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import co.icanteach.apps.android.droidhub.design.composables.VerticalSpacer

@Composable
internal fun SingleItem(
    title: String, description: String, icon: Painter, onItemClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClicked),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon, contentDescription = title
        )

        HorizontalSpacer(value = 16.dp)

        Column {
            Text(
                text = title,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h6,
                fontSize = 16.sp,
            )

            VerticalSpacer(value = 1.dp)

            Text(
                text = description,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SingleItem_Preview() {
    SingleItem(
        title = stringResource(id = R.string.account_share_this_application_title),
        description = stringResource(id = R.string.account_share_this_application_desc),
        icon = painterResource(id = R.drawable.ic_share)
    )
}