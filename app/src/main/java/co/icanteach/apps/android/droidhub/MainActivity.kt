package co.icanteach.apps.android.droidhub

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme
import co.icanteach.apps.android.droidhub.features.submission.SubmissionScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.userThemePreference.observe(this) { result ->
            setContent {
                DroidhubTheme(
                    isSystemInDarkTheme = result.isDarkThemeSelected
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded })
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { SubmissionScreen(sheetState = sheetState) },
        modifier = Modifier.fillMaxSize()
    ) {
        HomeScreenContent(navController, sheetState, coroutineScope)
    }
}


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenContent(
    navController: NavHostController,
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope
) {

    Scaffold(topBar = {
        TopAppBar(actions = {
            IconButton(onClick = {
                coroutineScope.launch {
                    if (sheetState.isVisible) sheetState.hide()
                    else sheetState.show()
                }
            }) {
                Icon(Icons.Default.Add, "")
            }
        }, title = { Text(stringResource(id = R.string.app_name)) })
    },
        content = { AppNavigator(navController) },
        bottomBar = { BottomNavigationBar(navController = navController) })
}
