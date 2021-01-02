package co.icanteach.apps.android.droidfeeds.home

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.viewModels
import co.icanteach.apps.android.droidfeeds.R
import co.icanteach.apps.android.droidfeeds.core.BaseFragment
import co.icanteach.apps.android.droidfeeds.core.StatusViewState
import co.icanteach.apps.android.droidfeeds.core.extensions.showSnackbar
import co.icanteach.apps.android.droidfeeds.databinding.FragmentHomeFeedBinding
import co.icanteach.apps.android.droidfeeds.news.NewsItem
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFeedFragment : BaseFragment<FragmentHomeFeedBinding>() {

    @Inject
    lateinit var homeFeedAdapter: HomeFeedAdapter

    private val homeFeedViewModel: HomeFeedViewModel by viewModels()

    override fun getViewBinding() = FragmentHomeFeedBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(homeFeedViewModel) {
            homeFeedListing_.observe(viewLifecycleOwner, {
                homeFeedAdapter.submitList(it.newsList)
            })

            status_.observe(viewLifecycleOwner, { viewState ->
                onRenderPageStatusState(viewState)
            })

            bookmarkSuccessResult.observe(viewLifecycleOwner, {
                showBookmarkSuccessResult()
            })
        }

        binding.recyclerViewHomeFeeds.apply {
            adapter = homeFeedAdapter
        }

        with(homeFeedAdapter) {
            onExploreClicked = {
                openOriginContent(it)
            }

            onBookmarkClicked = { newsItem ->
                addBookmark(newsItem)
            }
        }
    }

    private fun showBookmarkSuccessResult() {
        view.showSnackbar(getString(R.string.add_bookmark_success_message), Snackbar.LENGTH_SHORT)
    }

    private fun onRenderPageStatusState(viewState: StatusViewState) {
        binding.viewState = viewState
        binding.executePendingBindings()
    }

    private fun addBookmark(newsItem: NewsItem) {
        homeFeedViewModel.addBookmark(newsItem)
    }

    private fun openOriginContent(url: String) {
        val uri: Uri = Uri.parse(url)
        val intentBuilder = CustomTabsIntent.Builder()
        val customTabsIntent = intentBuilder.build()
        customTabsIntent.launchUrl(requireContext(), uri)
    }
}