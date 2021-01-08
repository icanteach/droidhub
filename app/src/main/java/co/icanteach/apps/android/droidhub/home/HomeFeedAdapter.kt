package co.icanteach.apps.android.droidhub.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.icanteach.apps.android.droidhub.core.BaseAdapter
import co.icanteach.apps.android.droidhub.core.DataClassDiffCallback
import co.icanteach.apps.android.droidhub.databinding.ItemDroidFeedsContentBinding
import co.icanteach.apps.android.droidhub.news.FeedItem
import javax.inject.Inject

class HomeFeedAdapter @Inject constructor() :
    BaseAdapter<FeedItem, HomeFeedAdapter.HomeFeedItemViewHolder>(
        DataClassDiffCallback { it.originUrl }
    ) {

    var onExploreClicked: ((String) -> Unit)? = null
    var onBookmarkClicked: ((FeedItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFeedItemViewHolder {
        val binding = ItemDroidFeedsContentBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return HomeFeedItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeFeedItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HomeFeedItemViewHolder(
        private val binding: ItemDroidFeedsContentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                buttonExplore.setOnClickListener {
                    onExploreClicked?.invoke(getItem(adapterPosition).originUrl)
                }

                buttonSave.setOnClickListener {
                    onBookmarkClicked?.invoke(getItem(adapterPosition))
                }
            }
        }

        fun bind(newsItem: FeedItem) {
            binding.viewState = HomeFeedItemViewState(newsItem = newsItem)
            binding.executePendingBindings()
        }
    }
}