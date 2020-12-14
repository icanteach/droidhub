package co.icanteach.apps.android.droidfeeds.core

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class DataClassDiffCallback<T>(private val uniqueProperty: (T) -> Any?) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T) = uniqueProperty(oldItem) == uniqueProperty(newItem)

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
}