package com.abhishek.androidlearn

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.androidlearn.databinding.ListItemBinding

class RecycleViewProgressAdapter :
    ListAdapter<Info, RecycleViewProgressAdapter.ViewHolder>(DIFF_CALLBACK) {
    inner class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentList[position]?.let { info ->
            holder.binding.let {
                it.progressBar.progress = info.progress
                it.tvProgress.text = "${info.progress}%"
            }
        }

    }
}

object DIFF_CALLBACK : DiffUtil.ItemCallback<Info>() {
    override fun areItemsTheSame(oldItem: Info, newItem: Info): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Info, newItem: Info): Boolean {
        return oldItem.progress == newItem.progress
    }

}