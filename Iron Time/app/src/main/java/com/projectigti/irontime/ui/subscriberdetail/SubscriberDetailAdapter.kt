package com.projectigti.irontime.ui.subscriberdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projectigti.irontime.databinding.CheckinItemBinding
import java.util.*

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
                val daysOfWeek = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
                val date:Date = item
                val cal = Calendar.getInstance()
                cal.time = date
                val hours = cal.get(Calendar.HOUR_OF_DAY)
                val minutes = cal.get(Calendar.MINUTE)
                val week = cal.get(Calendar.DAY_OF_WEEK) - 1
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH) + 1
                val day = cal.get(Calendar.DAY_OF_MONTH)

                textCheckinDate.text = "${day}/${month}/${year}"
                textCheckinHour.text = "${hours}:${minutes}"
                textCheckinWeek.text = daysOfWeek[week]
            }
        }
    }
}