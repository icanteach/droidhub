package co.icanteach.apps.android.droidhub.core

import android.content.Context
import co.icanteach.apps.android.droidhub.R
import com.erkutaras.statelayout.StateLayout

class StatusViewState(private val status: Status) {

    fun getStateInfo(context: Context): StateLayout.StateInfo {
        return when (status) {
            Status.Content -> StateLayout.provideContentStateInfo()
            is Status.Error -> provideErrorState(context, status.exception)
            Status.Loading -> StateLayout.provideLoadingStateInfo()
            Status.ContentWithLoading -> StateLayout.provideLoadingWithContentStateInfo()
        }
    }

    private fun provideErrorState(context: Context, exception: Throwable): StateLayout.StateInfo {
        val resourceError = ErrorResolver.resolve(exception)
        return StateLayout.StateInfo(
            infoImage = R.drawable.ic_state_info,
            infoTitle = context.getString(resourceError.errorTitle),
            infoMessage = context.getString(resourceError.errorDesc),
            infoButtonText = context.getString(resourceError.actionText),
            state = StateLayout.State.ERROR
        )
    }
}