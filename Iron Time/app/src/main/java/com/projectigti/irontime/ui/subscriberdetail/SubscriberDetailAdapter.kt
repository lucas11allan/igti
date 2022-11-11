package com.projectigti.irontime.ui.subscriberdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projectigti.irontime.data.db.model.SubscriberEntity
import com.projectigti.irontime.databinding.CheckinItemBinding
import com.projectigti.irontime.databinding.SubscriberItemBinding
import com.projectigti.irontime.ui.subscriberlist.SubscriberListAdapter
import java.util.Date

class SubscriberDetailAdapter(
    private val checkins: List<Date>
) : RecyclerView.Adapter<SubscriberDetailAdapter.SubscriberDetailViewHolder>() {
    private lateinit var binding: CheckinItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberDetailViewHolder {
        binding = CheckinItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubscriberDetailViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SubscriberDetailViewHolder, position: Int) {
        holder.bind(checkins[position])
    }

    override fun getItemCount() = checkins.size

    inner class SubscriberDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Date) {
            binding.apply {
                textCheckinDate.text = item.toString()
            }
        }
    }
}