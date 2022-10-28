package co.icanteach.apps.android.droidhub.features.interests

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme

@Composable
fun InterestsScreen(
    feedViewModel: InterestsViewModel = hiltViewModel(),
) {
    val screenState = feedViewModel.interestScreenState
    InterestsScreen(screenState) { item ->
        feedViewModel.onInterestSelection(item)
    }
}

@Composable
fun InterestsScreen(
    screenState: InterestsScreenUiState, onToggle: (InterestItem) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(screenState.items) { interest ->
            InterestListingItem(interest = interest, selected = interest.isSelected, onToggle = {
                onToggle.invoke(interest)
            })
        }
    }
}

@Composable
private fun InterestListingItem(
    interest: InterestItem,
    selected: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            modifier = modifier.toggleable(value = selected, onValueChange = { result ->
                onToggle(result)
            }), verticalAlignment = Alignment.CenterVertically
        ) {
            val image =
                painterResource(id = co.icanteach.apps.android.droidhub.R.drawable.placeholder_interest_item)
            Image(
                painter = image,
                contentDescription = interest.displayName,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Text(
                text = interest.displayName,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(Modifier.width(16.dp))
            InterestSelectionButton(selected = selected)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun InterestsScreen_Preview() {
    val uiState = InterestsScreenUiState(FakeInterestProvider().interests)
    InterestsScreen(uiState) {}
}

@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun InterestsScreen_DarkPreview() {
    val uiState = InterestsScreenUiState(FakeInterestProvider().interests)
    DroidhubTheme {
        Surface {
            InterestsScreen(uiState) {}
        }
    }
}
