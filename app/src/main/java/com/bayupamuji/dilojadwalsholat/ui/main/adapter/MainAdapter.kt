package com.bayupamuji.dilojadwalsholat.ui.main.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bayupamuji.dilojadwalsholat.R
import com.bayupamuji.dilojadwalsholat.data.entity.Data
import kotlinx.android.synthetic.main.item_schedule.view.*

class MainAdapter(private val list: MutableList<Data>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewGroup: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_schedule,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Data) {
            val day = item.date.gregorian.weekday.en
            val date = item.date.gregorian.day
            val month = item.date.gregorian.month.en
            val year = item.date.gregorian.year

            val dateH = item.date.hijri.day
            val monthH = item.date.hijri.month.en
            val yearH = item.date.hijri.year

            itemView.tv_calendar_day.text = day
            itemView.tv_calendar.text = "$date $month $year / $dateH $monthH $yearH"
            itemView.tv_imsak_time.text = item.timings.Imsak
            itemView.tv_fajr_time.text = item.timings.Fajr
            itemView.tv_shurooq_time.text = item.timings.Sunrise
            itemView.tv_dhuhr_time.text = item.timings.Dhuhr
            itemView.tv_asr_time.text = item.timings.Asr
            itemView.tv_maghrib_time.text = item.timings.Maghrib
            itemView.tv_isha_time.text = item.timings.Isha
        }
    }

}
