package co.icanteach.apps.android.droidfeeds.home

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.icanteach.apps.android.droidfeeds.R
import co.icanteach.apps.android.droidfeeds.core.BaseFragment
import co.icanteach.apps.android.droidfeeds.databinding.FragmentHomeFeedBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFeedFragment : BaseFragment<FragmentHomeFeedBinding>() {

    @Inject
    lateinit var homeFeedAdapter: HomeFeedAdapter

    private val homeFeedViewModel: HomeFeedViewModel by viewModels()

    override fun getLayoutId(): Int = R.layout.fragment_home_feed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeFeedViewModel.fetchHomeFeed()

        homeFeedViewModel.homeFeedListing_.observe(viewLifecycleOwner, Observer {
            homeFeedAdapter.submitList(it.newsList)
        })
        binding.recyclerViewHomeFeeds.apply {
            adapter = homeFeedAdapter
        }

        homeFeedAdapter.onExploreClicked = {

            openOriginContent(it)
        }

    }

    private fun openOriginContent(url: String) {
        val uri: Uri = Uri.parse(url)
        val intentBuilder = CustomTabsIntent.Builder()
        val customTabsIntent = intentBuilder.build()
        customTabsIntent.launchUrl(requireContext(), uri)
    }
}