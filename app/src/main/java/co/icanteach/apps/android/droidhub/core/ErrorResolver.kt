package co.icanteach.apps.android.droidhub.core

import co.icanteach.apps.android.droidhub.R

object ErrorResolver {

    fun resolve(throwable: Throwable): ResourceError {
        return when (throwable) {
            is NoContentListingException -> ResourceError(
                actionText = R.string.no_content_error_action,
                errorDesc = R.string.no_content_error_message,
                errorTitle = R.string.default_error_title
            )
            else ->
                ResourceError(
                    actionText = R.string.common_action_retry,
                    errorDesc = R.string.default_error_message,
                    errorTitle = R.string.default_error_title
                )
        }
    }
}