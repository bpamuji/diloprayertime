package com.bayupamuji.dilojadwalsholat.data.repo

import com.bayupamuji.dilojadwalsholat.data.entity.MonthScheduleResponse
import com.bayupamuji.dilojadwalsholat.data.entity.ScheduleResponse
import retrofit2.Call

interface TimePrayerScheduleRepo{
    fun getDailyTimePrayer(city: String, country: String, method: Int): Call<ScheduleResponse>
    fun getScheduleByHijri(city:String, country:String,method: Int): Call<MonthScheduleResponse>
    fun getScheduleByGregorian(city: String,country: String,method: Int): Call<MonthScheduleResponse>
}