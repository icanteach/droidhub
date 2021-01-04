package co.icanteach.apps.android.droidfeeds.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.icanteach.apps.android.droidfeeds.core.BaseAdapter
import co.icanteach.apps.android.droidfeeds.core.DataClassDiffCallback
import co.icanteach.apps.android.droidfeeds.databinding.ItemBookmarkContentBinding
import co.icanteach.apps.android.droidfeeds.news.NewsItem
import javax.inject.Inject

class BookmarkAdapter @Inject constructor() :
    BaseAdapter<NewsItem, BookmarkAdapter.BookmarkItemViewHolder>(
        DataClassDiffCallback { it.originUrl }
    ) {

    var onExploreClicked: ((String) -> Unit)? = null
    var onRemoveClicked: ((NewsItem) -> Unit)? = null

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

        fun bind(newsItem: NewsItem) {
            binding.viewState = BookmarkItemViewState(newsItem = newsItem)
            binding.executePendingBindings()
        }
    }
}