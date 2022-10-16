package co.icanteach.apps.android.droidhub.features.submission

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.icanteach.apps.android.droidhub.R.array
import co.icanteach.apps.android.droidhub.R.string
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme
import co.icanteach.apps.android.droidhub.features.submission.SubmissionViewModel.*
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SubmissionScreen(
    viewModel: SubmissionViewModel = hiltViewModel(),
    sheetState: ModalBottomSheetState
) {

    val uiState = viewModel.state

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowError -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.message
                    )
                }
                is UiEvent.ClosePage -> {
                    sheetState.hide()
                }
            }
        }
    }

    SubmissionScreen(state = uiState, onContentUrlChanged = { url ->
        viewModel.onEvent(SubmissionScreenEvent.ContentUrlChanged(url))
    }, onPostTypeExpanded = { expanded ->
        viewModel.onEvent(SubmissionScreenEvent.PostTypeExpanded(expanded))
    }, onSelectedPostTypeChanged = { postType ->
        viewModel.onEvent(SubmissionScreenEvent.PostTypeChanged(postType))
    }, onSubmissionClicked = {
        viewModel.onEvent(SubmissionScreenEvent.SubmitForm)
    })

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SubmissionScreen(
    state: SubmissionScreenUiState,
    onContentUrlChanged: (String) -> (Unit),
    onSubmissionClicked: () -> (Unit),
    onPostTypeExpanded: (Boolean) -> (Unit),
    onSelectedPostTypeChanged: (String) -> (Unit),
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = stringResource(id = string.submission_page_title),
            style = MaterialTheme.typography.subtitle1
        )

        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = state.contentUrl,
            onValueChange = { input ->
                onContentUrlChanged.invoke(input)
            },
            label = {
                Text(stringResource(id = string.submission_page_url_placeholder))
            })

        Spacer(modifier = Modifier.height(32.dp))

        /**
         * see https://stackoverflow.com/a/67111599
         */

        Text(
            text = stringResource(id = string.submission_choose_type_title),
            style = MaterialTheme.typography.subtitle1
        )

        val options = stringArrayResource(array.submission_post_types)

        ExposedDropdownMenuBox(expanded = state.postTypeExpanded, onExpandedChange = {
            onPostTypeExpanded.invoke(state.postTypeExpanded.not())
        }) {
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                value = state.selectedPostType,
                onValueChange = { },
                label = { Text(stringResource(id = string.submission_choose_type_placeholder)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = state.postTypeExpanded
                    )
                })
            ExposedDropdownMenu(expanded = state.postTypeExpanded, onDismissRequest = {
                onPostTypeExpanded.invoke(false)
            }) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(onClick = {
                        onSelectedPostTypeChanged.invoke(selectionOption)
                        onPostTypeExpanded.invoke(false)
                    }) {
                        Text(text = selectionOption)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), onClick = {
                onSubmissionClicked.invoke()
            }, shape = RectangleShape
        ) {
            Text(text = stringResource(id = string.submission_share_button))
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SubmissionScreen_Preview() {
    SubmissionScreen_Preview(SubmissionScreenUiState.idle())
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SubmissionScreenDarkMode_Preview() {
    DroidhubTheme {
        Surface {
            SubmissionScreen_Preview(SubmissionScreenUiState.idle())
        }
    }
}

@Composable
fun SubmissionScreen_Preview(
    uiState: SubmissionScreenUiState
) {
    SubmissionScreen(
        uiState,
        onPostTypeExpanded = {},
        onContentUrlChanged = {},
        onSelectedPostTypeChanged = {},
        onSubmissionClicked = {},
    )
}

