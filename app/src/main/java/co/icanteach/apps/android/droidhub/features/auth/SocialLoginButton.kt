package co.icanteach.apps.android.droidhub.features.auth

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.icanteach.apps.android.droidhub.R
import co.icanteach.apps.android.droidhub.design.composables.HorizontalSpacer
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme

@Composable
fun SocialLoginButton(
    @DrawableRes buttonIcon: Int, @StringRes buttonText: Int, onButtonClicked: () -> Unit
) {
    OutlinedButton(border = ButtonDefaults.outlinedBorder.copy(width = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .height(50.dp),
        onClick = { onButtonClicked.invoke() },
        content = {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                content = {

                    Icon(
                        tint = Color.Unspecified,
                        painter = painterResource(id = buttonIcon),
                        contentDescription = null,
                    )

                    HorizontalSpacer(value = 8.dp)

                    Text(
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onSurface,
                        text = stringResource(buttonText)
                    )
                })
        })
}

@Preview
@Composable
fun FacebookLoginButton_Preview() {
    SocialLoginButton(
        buttonIcon = R.drawable.ic_launcher_background, buttonText = R.string.auth_facebook_login
    ) {}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FacebookLoginButtonDark_Preview() {
    DroidhubTheme {
        SocialLoginButton(
            buttonIcon = R.drawable.ic_launcher_background, buttonText = R.string.auth_facebook_login
        ) {}
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GoogleLoginButtonDark_Preview() {
    DroidhubTheme {
        SocialLoginButton(
            buttonIcon = R.drawable.ic_google_logo, buttonText = R.string.auth_google_login
        ) {}
    }
}

@Preview
@Composable
fun GoogleLoginButton_Preview() {
    SocialLoginButton(
        buttonIcon = R.drawable.ic_google_logo, buttonText = R.string.auth_google_login
    ) {}
}