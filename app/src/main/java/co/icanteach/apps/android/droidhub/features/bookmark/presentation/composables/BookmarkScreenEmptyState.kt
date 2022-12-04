package co.icanteach.apps.android.droidhub.features.bookmark.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.icanteach.apps.android.droidhub.R
import co.icanteach.apps.android.droidhub.design.composables.VerticalSpacer

@Composable
fun BookmarkScreenEmptyState(
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_empty_bookmark),
            contentDescription = stringResource(id = R.string.bookmark_empty_page_title),
            modifier = Modifier.size(120.dp, 120.dp)
        )
        VerticalSpacer(value = 16.dp)
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.bookmark_empty_page_title),
            style = MaterialTheme.typography.h5,
            maxLines = 3,
            color = MaterialTheme.colors.onBackground,
        )

        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.bookmark_empty_page_desc),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground,
        )
        VerticalSpacer(value = 16.dp)
    }
}