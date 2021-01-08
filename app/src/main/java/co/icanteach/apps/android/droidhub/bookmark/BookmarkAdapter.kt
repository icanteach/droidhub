package co.icanteach.apps.android.droidhub.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.icanteach.apps.android.droidhub.core.BaseAdapter
import co.icanteach.apps.android.droidhub.core.DataClassDiffCallback
import co.icanteach.apps.android.droidhub.databinding.ItemBookmarkContentBinding
import co.icanteach.apps.android.droidhub.news.FeedItem
import javax.inject.Inject

class BookmarkAdapter @Inject constructor() :
    BaseAdapter<FeedItem, BookmarkAdapter.BookmarkItemViewHolder>(
        DataClassDiffCallback { it.originUrl }
    ) {

    var onExploreClicked: ((String) -> Unit)? = null
    var onRemoveClicked: ((FeedItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkItemViewHolder {
        val binding =
            ItemBookmarkContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BookmarkItemViewHolder(
        private val binding: ItemBookmarkContentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                buttonExplore.setOnClickListener {
                    onExploreClicked?.invoke(getItem(adapterPosition).originUrl)
                }

                buttonSave.setOnClickListener {
                    onRemoveClicked?.invoke(getItem(adapterPosition))
                }
            }
        }

        fun bind(newsItem: FeedItem) {
            binding.viewState = BookmarkItemViewState(newsItem = newsItem)
            binding.executePendingBindings()
        }
    }
}