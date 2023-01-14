package com.abhishek.androidlearn

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.androidlearn.databinding.ListItemBinding

class RecycleViewProgressAdapter(val infoList: List<Info>) :
    RecyclerView.Adapter<RecycleViewProgressAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        infoList[position].let { info ->
            holder.binding.let {
                it.progressBar.progress = info.progress
                it.tvProgress.text = "${info.progress}%"
            }
        }

    }

    override fun getItemCount(): Int {
        return infoList.size
    }

    fun updateInfo(info: Info) {
        infoList.forEachIndexed { index, info2 ->
            if (info.id == info2.id) {
                notifyItemChanged(index)
                return
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