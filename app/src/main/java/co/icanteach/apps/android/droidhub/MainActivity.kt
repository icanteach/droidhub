package co.icanteach.apps.android.droidhub

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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


        mainViewModel.userThemePreference
            .observe(this) { result ->
                setContent {

                    val sheetState = rememberModalBottomSheetState(
                        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
                    )
                    val bottomSheetNavigator = remember { BottomSheetNavigator(sheetState) }
                    val navController = rememberNavController(bottomSheetNavigator)

                    DroidhubTheme(
                        isSystemInDarkTheme = result.isDarkThemeSelected
                    ) {
                        MainScreen(navController, bottomSheetNavigator)
                    }
                }
            }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
) {

    Scaffold(topBar = {
        TopAppBar(actions = {
            IconButton(onClick = {
                navController.navigate(
                    Screens.SubmissionScreen.route
                )
            }) {
                Icon(Icons.Default.Add, "")
            }
        }, title = { Text(stringResource(id = R.string.app_name)) })
    },
        content = { AppNavigator(navController, bottomSheetNavigator) },
        bottomBar = { BottomNavigationBar(navController = navController) })
}

