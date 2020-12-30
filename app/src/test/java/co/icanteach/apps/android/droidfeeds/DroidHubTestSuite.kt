package co.icanteach.apps.android.droidfeeds

import co.icanteach.apps.android.droidfeeds.auth.AuthenticationUseCaseTest
import co.icanteach.apps.android.droidfeeds.bookmark.domain.BookmarkActionsUseCaseTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    AuthenticationUseCaseTest::class,
    BookmarkActionsUseCaseTest::class,
)
class DroidHubTestSuite