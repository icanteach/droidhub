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
import com.google.android.material.snackbar.Snackbar
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

        with(bookmarkViewModel) {
            homeFeedListing_.observe(viewLifecycleOwner, Observer {
                bookmarkAdapter.submitList(it.newsList)
            })
            status_.observe(viewLifecycleOwner, Observer { viewState ->
                onRenderPageStatusState(viewState)
            })
            bookmarkSuccessResult.observe(viewLifecycleOwner, Observer {
                showBookmarkSuccessResult()
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
        findNavController(this@BookmarkFragment).navigate(R.id.action_to_homefeed)
    }

    private fun showBookmarkSuccessResult() {
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            requireContext().getString(R.string.remove_bookmark_success_message),
            Snackbar.LENGTH_SHORT
        ).show()
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