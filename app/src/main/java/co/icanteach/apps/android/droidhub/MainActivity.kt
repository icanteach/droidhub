package co.icanteach.apps.android.droidhub

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.userThemePreference.observe(this) { result ->
            setContent {
                DroidhubTheme(isSystemInDarkTheme = result.isDarkThemeSelected) {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        MainScreen()
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {

    val navController = rememberNavController()

    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(id = R.string.app_name)) }) },
        content = { AppNavigator(navController) },
        bottomBar = { BottomNavigationBar(navController = navController) })
}