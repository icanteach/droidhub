package co.icanteach.apps.android.droidfeeds.bookmark

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import co.icanteach.apps.android.droidfeeds.R
import co.icanteach.apps.android.droidfeeds.core.BaseFragment
import co.icanteach.apps.android.droidfeeds.core.StatusViewState
import co.icanteach.apps.android.droidfeeds.databinding.FragmentBookmarkBinding
import co.icanteach.apps.android.droidfeeds.home.HomeFeedAdapter
import co.icanteach.apps.android.droidfeeds.news.NewsItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>() {

    @Inject
    lateinit var bookmarkAdapter: BookmarkAdapter

    private val bookmarkViewModel: BookmarkViewModel by viewModels()

    override fun getLayoutId(): Int = R.layout.fragment_bookmark

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookmarkViewModel.homeFeedListing_.observe(viewLifecycleOwner, Observer {
            bookmarkAdapter.submitList(it.newsList)
        })

        bookmarkViewModel.status_.observe(viewLifecycleOwner, Observer { viewState ->
            onRenderPageStatusState(viewState)
        })

        binding.recyclerViewBookmarks.apply {
            adapter = bookmarkAdapter
        }

        bookmarkAdapter.onExploreClicked = {
            openOriginContent(it)
        }

        bookmarkAdapter.onRemoveClicked = {
            removeBookmark(it)
        }

        binding.stateLayout.infoButtonListener {
            findNavController(this).navigate(R.id.action_to_homefeed)
        }

    }

    private fun removeBookmark(newsItem: NewsItem) {
        bookmarkViewModel.removeBookmark(newsItem)
    }

    private fun onRenderPageStatusState(viewState: StatusViewState) {
        binding.viewState = viewState
        binding.executePendingBindings()
    }

    private fun openOriginContent(url: String) {
        val uri: Uri = Uri.parse(url)
        val intentBuilder = CustomTabsIntent.Builder()
        val customTabsIntent = intentBuilder.build()
        customTabsIntent.launchUrl(requireContext(), uri)
    }
}