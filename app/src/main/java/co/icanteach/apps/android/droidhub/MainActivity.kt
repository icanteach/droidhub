package co.icanteach.apps.android.droidhub

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterialNavigationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val sheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
            )
            val bottomSheetNavigator = remember { BottomSheetNavigator(sheetState) }
            val navController = rememberNavController(bottomSheetNavigator)

            val userThemePreference = mainViewModel.userThemePreference.observeAsState()

            DroidhubTheme(
                isSystemInDarkTheme = userThemePreference.value?.isDarkThemeSelected ?: false
            ) {
                MainScreen(navController, bottomSheetNavigator)
            }

            mainViewModel.openSubmissonPage.observe(this) { incomingUrl ->
                navController.navigate(
                    Screens.SubmissionScreen.route + "?incomingUrl=${incomingUrl}"
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if ((intent?.action == Intent.ACTION_SEND) && ("text/plain" == intent.type)) {
            intent.getStringExtra(Intent.EXTRA_TEXT)?.let { incomingUrl ->
                mainViewModel.onOpenSubmissonPage(incomingUrl)
            }
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
) {

    Scaffold(bottomBar = { BottomNavigationBar(navController = navController) }, topBar = {
        TopAppBar(actions = {
            IconButton(onClick = {
                navController.navigate(
                    Screens.SubmissionScreen.route
                )
            }) {
                Icon(Icons.Default.Add, "")
            }
        }, title = { Text(stringResource(id = R.string.app_name)) })
    }) { innerPadding ->
        // Apply the padding globally to the whole BottomNavScreensController
        Box(modifier = Modifier.padding(innerPadding)) {
            AppNavigator(navController, bottomSheetNavigator)
        }
    }
}

