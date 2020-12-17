package co.icanteach.apps.android.droidfeeds.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import javax.inject.Inject

class AnalyticsUseCase @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) {

    fun sendScreenView(@AnalyticsKeys.PAGE page: String) {
        firebaseAnalytics.logEvent(AnalyticsKeys.PAGE.EVENT_HOLDER) {
            param(AnalyticsKeys.FIREBASE.PAGE, page)
        }
    }

    fun sendClickEvent(
        @AnalyticsKeys.CLICK clickAction: String,
        @AnalyticsKeys.PAGE page: String
    ) {
        firebaseAnalytics.logEvent(AnalyticsKeys.CLICK.EVENT_HOLDER) {
            param(AnalyticsKeys.FIREBASE.ACTION, clickAction)
            param(AnalyticsKeys.FIREBASE.PAGE, page)
        }
    }
}