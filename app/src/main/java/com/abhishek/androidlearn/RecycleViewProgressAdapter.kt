package com.abhishek.androidlearn

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.androidlearn.databinding.ListItemBinding

class RecycleViewProgressAdapter(private val infoList: List<Info>) :
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
                if (info.progress == -1) {
                    it.progressBar.isIndeterminate = true
                } else {
                    it.progressBar.isIndeterminate = false
                    it.progressBar.progress = info.progress
                    it.tvProgress.text = "${info.progress}%"
                }
                it.tvSpeed.text = "${info.rate}"

            }
        }

    }

    override fun getItemCount(): Int {
        return infoList.size
    }

    fun updateInfo(info: Info) {
        notifyDataSetChanged()
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