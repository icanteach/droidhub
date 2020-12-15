package co.icanteach.apps.android.droidfeeds.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.icanteach.apps.android.droidfeeds.R
import co.icanteach.apps.android.droidfeeds.core.BaseAdapter
import co.icanteach.apps.android.droidfeeds.core.DataClassDiffCallback
import co.icanteach.apps.android.droidfeeds.core.inflate
import co.icanteach.apps.android.droidfeeds.databinding.ItemDroidFeedsContentBinding
import co.icanteach.apps.android.droidfeeds.news.NewsItem
import javax.inject.Inject

class HomeFeedAdapter @Inject constructor() :
    BaseAdapter<NewsItem, HomeFeedAdapter.HomeFeedItemViewHolder>(
        DataClassDiffCallback { it.originUrl }
    ) {

    var onExploreClicked: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFeedItemViewHolder {
        val binding: ItemDroidFeedsContentBinding =
            parent.inflate(R.layout.item_droid_feeds_content, false)
        return HomeFeedItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeFeedItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HomeFeedItemViewHolder(
        private val binding: ItemDroidFeedsContentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {

            binding.buttonExplore.setOnClickListener {
                onExploreClicked?.invoke(getItem(adapterPosition).originUrl)
            }

            binding.buttonSave.setOnClickListener {

            }
        }

        fun bind(newsItem: NewsItem) {
            binding.viewState = HomeFeedItemViewState(newsItem = newsItem)
            binding.executePendingBindings()
        }
    }
}
