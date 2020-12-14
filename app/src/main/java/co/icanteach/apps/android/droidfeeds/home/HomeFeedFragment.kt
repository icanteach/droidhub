package co.icanteach.apps.android.droidfeeds.home

import android.os.Bundle
import android.view.View
import co.icanteach.apps.android.droidfeeds.R
import co.icanteach.apps.android.droidfeeds.core.BaseFragment
import co.icanteach.apps.android.droidfeeds.databinding.FragmentHomeFeedBinding
import co.icanteach.apps.android.droidfeeds.news.NewsItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFeedFragment : BaseFragment<FragmentHomeFeedBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_home_feed

    @Inject
    lateinit var homeFeedAdapter: HomeFeedAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeFeedAdapter.submitList(
            listOf(
                NewsItem(
                    originUrl = "",
                    description = "312312312312312",
                    title = "22222",
                    image = "https://cdn.dsmcdn.com/marketing/datascience/automation/BrandBoutique/2020/12/14/SlazengerSuperBrandDay_section1_202012140837_webBig.jpg"
                ),
                NewsItem(
                    originUrl = "",
                    description = "5554555",
                    title = "333333",
                    image = "https://cdn.dsmcdn.com/ty32/campaign/banners/original/545803/ce13c63bb1_2_new.jpg"
                ),
                NewsItem(
                    originUrl = "",
                    description = "5554555",
                    title = "333333",
                    image = "https://cdn.dsmcdn.com/ty32/campaign/banners/original/544495/f0f19fb9c2_0_new.jpg"
                )
            )
        )
        binding.recyclerViewInfo.apply {
            adapter = homeFeedAdapter
        }

    }
}