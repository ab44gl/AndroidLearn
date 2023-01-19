package com.abhishek.androidlearn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.androidlearn.databinding.ListItemBinding


class RvAdapter(private val timerItemList: List<Timer>) :
    RecyclerView.Adapter<RvAdapter.ViewHolder>() {
    private var itemClickListener: ((int: Int, msg: String) -> Unit)? = null

    inner class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                imgStartStop.setOnClickListener {
                    click("startStop")
                }
                imgResetStop.setOnClickListener {
                    click("resetStop")
                }
            }
        }

        private fun click(msg: String) {
            val position: Int = this.layoutPosition
            if (position != RecyclerView.NO_POSITION) {
                itemClickListener?.invoke(position, msg)
            }
        }
    }

    fun setOnItemClick(f: (int: Int, msg: String) -> Unit) {
        itemClickListener = f
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return timerItemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = timerItemList[position]
        holder.binding.apply {
            tvTime.text = item.getCount().toString()
            if (item.isStarted()) {
                imgResetStop.visibility = View.VISIBLE
                imgStartStop.setImageResource(R.drawable.baseline_pause_24)
            } else {
                imgResetStop.visibility = View.GONE
                imgStartStop.setImageResource(R.drawable.baseline_play_arrow_24)
            }
        }
    }

    fun update() {
        notifyDataSetChanged()
    }
}