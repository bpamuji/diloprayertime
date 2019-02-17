package com.bayupamuji.dilojadwalsholat.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bayupamuji.dilojadwalsholat.R
import com.bayupamuji.dilojadwalsholat.data.entity.Item

class MainAdapter(private val list: MutableList<Item>) : RecyclerView.Adapter<MainAdapter.ViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_schedule,p0,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        //p0.bind(list[p1])
    }

    class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        /*fun bind(item: Item){
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(item.date_for)
            itemView.tv_calendar.text = dateFormat(date)
            itemView.tv_fajr_time.text = item.fajr
            itemView.tv_shurooq_time.text = item.shurooq
            itemView.tv_dhuhr_time.text = item.dhuhr
            itemView.tv_asr_time.text = item.asr
            itemView.tv_maghrib_time.text = item.maghrib
            itemView.tv_isha_time.text = item.isha
        }

        private fun dateFormat(date: Date?) : String? = with(date?: Date()){
            SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(this)
        }*/
    }

}
