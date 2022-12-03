package co.icanteach.apps.android.droidhub.features.auth

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.icanteach.apps.android.droidhub.R.drawable
import co.icanteach.apps.android.droidhub.R.string
import co.icanteach.apps.android.droidhub.design.composables.VerticalSpacer
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onAuthActionResultSuccess: () -> (Unit),
) {

    val context = LocalContext.current
    val token = stringResource(string.default_web_client_id)
    val scaffoldState = rememberScaffoldState()

    /**
     * Equivalent of onActivityResult
     * see @ https://github.com/eric-ampire/firebase-auth-compose/blob/master/app/src/main/java/com/ericampire/mobile/firebaseauthcompose/ui/login/LoginScreen.kt
     */
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                viewModel.authUser(credential)
            } catch (_: ApiException) {
                // TODO error handling ...
            }
        }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AuthViewModel.UiEvent.ShowError -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "stringResource(id = event.message)"
                    )
                }
                is AuthViewModel.UiEvent.ClosePage -> {
                    onAuthActionResultSuccess.invoke()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = drawable.ic_verified_user),
            contentDescription = stringResource(id = string.app_name),
            modifier = Modifier.size(120.dp, 120.dp)
        )

        VerticalSpacer(value = 32.dp)

        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = string.app_name),
            style = MaterialTheme.typography.h5,
            maxLines = 3,
            color = MaterialTheme.colors.onBackground,
            overflow = TextOverflow.Ellipsis,
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = string.auth_desc),
            style = MaterialTheme.typography.body1,
            maxLines = 3,
            color = MaterialTheme.colors.onBackground,
            overflow = TextOverflow.Ellipsis,
        )
        VerticalSpacer(value = 32.dp)
        SocialLoginButton(
            buttonIcon = drawable.ic_google_logo, buttonText = string.auth_google_login
        ) {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(token).requestEmail().requestProfile().build()

            val googleSignInClient = GoogleSignIn.getClient(context, gso)
            launcher.launch(googleSignInClient.signInIntent)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AuthScreen_Preview() {
    AuthScreen {}
}

@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AuthScreenDark_Preview() {
    DroidhubTheme {
        Surface {
            AuthScreen {}
        }
    }
}