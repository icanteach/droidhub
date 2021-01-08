package co.icanteach.apps.android.droidhub.analytics

object AnalyticsKeys {

    annotation class FIREBASE {
        companion object {
            var ACTION = "ACTION"
            var PAGE = "PAGE"
        }
    }

    annotation class CLICK {
        companion object {
            var EXPLORE = "CLICK_EXPLORE"
            var SAVE = "CLICK_SAVE"
            var REMOVE = "CLICK_REMOVE"
            var EVENT_HOLDER = "CLICK_ACTION"
        }
    }

    annotation class PAGE {
        companion object {
            var EVENT_HOLDER = "SCREEN_VIEW"
            var HOME = "PAGE_HOME"
            var READING_LIST = "PAGE_READING_LIST"
        }
    }
}