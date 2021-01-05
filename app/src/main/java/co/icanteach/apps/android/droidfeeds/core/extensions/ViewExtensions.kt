package co.icanteach.apps.android.droidfeeds.core.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View?.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this!!, snackbarText, timeLength).show()
}