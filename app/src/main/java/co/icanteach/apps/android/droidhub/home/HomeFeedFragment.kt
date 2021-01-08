package co.icanteach.apps.android.droidhub.home

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.viewModels
import co.icanteach.apps.android.droidhub.R
import co.icanteach.apps.android.droidhub.core.BaseFragment
import co.icanteach.apps.android.droidhub.core.StatusViewState
import co.icanteach.apps.android.droidhub.core.extensions.showSnackbar
import co.icanteach.apps.android.droidhub.databinding.FragmentHomeFeedBinding
import co.icanteach.apps.android.droidhub.news.FeedItem
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

            filtersViewState_.observe(viewLifecycleOwner, { filtersViewState ->
                onRenderFiltersViewState(filtersViewState)
            })
        }

        binding.recyclerViewHomeFeeds.apply {
            adapter = homeFeedAdapter
        }

        binding.buttonFilter.setOnClickListener {
            show()
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

    private fun onRenderFiltersViewState(filtersViewState: DroidhubFiltersViewState) {
        binding.filterViewState = filtersViewState
        binding.executePendingBindings()
    }

    private fun showBookmarkSuccessResult() {
        view.showSnackbar(getString(R.string.add_bookmark_success_message), Snackbar.LENGTH_SHORT)
    }

    private fun onRenderPageStatusState(viewState: StatusViewState) {
        binding.viewState = viewState
        binding.executePendingBindings()
    }

    private fun addBookmark(newsItem: FeedItem) {
        homeFeedViewModel.addBookmark(newsItem)
    }

    private fun openOriginContent(url: String) {
        val uri: Uri = Uri.parse(url)
        val intentBuilder = CustomTabsIntent.Builder()
        val customTabsIntent = intentBuilder.build()
        customTabsIntent.launchUrl(requireContext(), uri)
    }

    private fun show() {

        val filters = homeFeedViewModel.getFilters().map {
            it as CharSequence
        }.toTypedArray()
        val checkedItem = 0

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.filters))
            .setSingleChoiceItems(filters, checkedItem) { dialog, which ->
                // TODO fetchSelectedFilterContent
                dialog.dismiss()
            }.show()
    }
}