package co.icanteach.apps.android.droidhub.bookmark

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import co.icanteach.apps.android.droidhub.core.BaseFragment
import co.icanteach.apps.android.droidhub.core.StatusViewState
import co.icanteach.apps.android.droidhub.databinding.FragmentBookmarkBinding
import co.icanteach.apps.android.droidhub.news.FeedItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>() {

    @Inject
    lateinit var bookmarkAdapter: BookmarkAdapter

    private val bookmarkViewModel: BookmarkViewModel by viewModels()

    override fun getViewBinding() = FragmentBookmarkBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(bookmarkViewModel) {
            homeFeedListing_.observe(viewLifecycleOwner, {
                bookmarkAdapter.submitList(it.newsList)
            })
            status_.observe(viewLifecycleOwner, { viewState ->
                onRenderPageStatusState(viewState)
            })
        }

        with(binding) {
            recyclerViewBookmarks.apply {
                adapter = bookmarkAdapter
            }
            stateLayout.infoButtonListener {
                navigateToHomeFeed()
            }
        }

        with(bookmarkAdapter) {
            onExploreClicked = {
                openOriginContent(it)
            }
            onRemoveClicked = {
                removeBookmark(it)
            }
        }
    }

    private fun navigateToHomeFeed() {
        val direction = BookmarkFragmentDirections.actionToHomefeed()
        findNavController(this@BookmarkFragment).navigate(direction)
    }

    private fun removeBookmark(newsItem: FeedItem) {
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