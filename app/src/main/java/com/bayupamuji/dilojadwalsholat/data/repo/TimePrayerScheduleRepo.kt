package com.bayupamuji.dilojadwalsholat.data.repo

import com.bayupamuji.dilojadwalsholat.data.entity.JadwalResponse
import retrofit2.Call

interface TimePrayerScheduleRepo{
    //fun getJadwalWeekly(country:String, city:String): Call<JadwalResponse>
    fun getDailyTimePrayer(city: String, country: String, method: Int): Call<JadwalResponse>
}