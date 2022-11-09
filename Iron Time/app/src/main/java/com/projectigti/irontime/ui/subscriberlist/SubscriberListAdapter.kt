package com.projectigti.irontime.ui.subscriberlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projectigti.irontime.data.db.model.SubscriberEntity
import com.projectigti.irontime.databinding.SubscriberItemBinding


class SubscriberListAdapter(
    private val subscribers: List<SubscriberEntity>
) : RecyclerView.Adapter<SubscriberListAdapter.SubscriberListViewHolder>() {
    private lateinit var binding: SubscriberItemBinding

    var onItemClick: ((entity: SubscriberEntity) -> Unit)? = null
    var onCheckinButtonClick: ((entity: SubscriberEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberListViewHolder {
        binding = SubscriberItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubscriberListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SubscriberListViewHolder, position: Int) {
        holder.bind(subscribers[position])
    }

    override fun getItemCount() = subscribers.size

    inner class SubscriberListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: SubscriberEntity) {
            binding.apply {
                textSubscriberName.text = item.name
                textSubscriberEmail.text = item.email

                buttonCheckin.setOnClickListener {
                    onCheckinButtonClick?.invoke(item)
                }

                itemView.setOnClickListener {
                    onItemClick?.invoke(item)
                }
            }
        }
    }
}