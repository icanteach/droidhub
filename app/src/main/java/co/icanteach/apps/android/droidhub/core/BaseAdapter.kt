package co.icanteach.apps.android.droidhub.core

import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.AsyncDifferConfig

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder>
constructor(diffCallback: DiffUtil.ItemCallback<T>) : RecyclerView.Adapter<VH>() {

    private val diffHelper = AsyncListDiffer(
        AdapterListUpdateCallback(this),
        AsyncDifferConfig.Builder(diffCallback)
            .build()
    )

    init {
        submitList(mutableListOf())
    }

    private fun getMutableList(): MutableList<T> {
        return mutableListOf<T>().apply {
            addAll(diffHelper.currentList)
        }
    }

    override fun getItemCount(): Int {
        return diffHelper.currentList.size
    }

    fun submitList(list: List<T>) {
        diffHelper.submitList(list)
    }

    fun clearItems() {
        submitList(mutableListOf())
    }

    protected fun getItem(position: Int): T = diffHelper.currentList[position]
}