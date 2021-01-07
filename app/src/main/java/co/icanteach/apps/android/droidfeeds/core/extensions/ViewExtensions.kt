package co.icanteach.apps.android.droidfeeds.core.extensions

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.snackbar.Snackbar

fun View?.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this!!, snackbarText, timeLength).show()
}

@BindingAdapter("isVisible")
internal fun View.isVisible(isVisible: Boolean?) {
    visibility = if (isVisible == true) View.VISIBLE else View.GONE
}