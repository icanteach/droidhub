package co.icanteach.apps.android.droidfeeds.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import javax.inject.Inject

/**
 *  The AnalyticsUseCase class is responsible for dispatching all analytics events across the application.
 */
class AnalyticsUseCase @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) {

    /**
     * send screen view event
     *
     * @param  page   page name of the screen
     */
    fun sendScreenView(@AnalyticsKeys.PAGE page: String) {
        firebaseAnalytics.logEvent(AnalyticsKeys.PAGE.EVENT_HOLDER) {
            param(AnalyticsKeys.FIREBASE.PAGE, page)
        }
    }

    /**
     * send click action event
     *
     * @param  clickAction   action name
     * @param  page   page name of the screen
     */
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