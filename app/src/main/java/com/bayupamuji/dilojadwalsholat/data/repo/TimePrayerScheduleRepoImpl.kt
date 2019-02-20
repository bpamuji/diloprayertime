package com.bayupamuji.dilojadwalsholat.data.repo

import com.bayupamuji.dilojadwalsholat.data.NetworkService
import com.bayupamuji.dilojadwalsholat.data.entity.MonthScheduleResponse
import com.bayupamuji.dilojadwalsholat.data.entity.ScheduleResponse
import retrofit2.Call

class TimePrayerScheduleRepoImpl : TimePrayerScheduleRepo {
    override fun getDailyTimePrayer(city: String, country: String, method:Int): Call<ScheduleResponse> {
        return NetworkService.create().getDailyTimePrayer(city, country, method)
    }

    override fun getScheduleByHijri(city: String, country: String, method: Int): Call<MonthScheduleResponse> {
        return NetworkService.create().getHijriPrayerTime(city, country, method)
    }

    override fun getScheduleByGregorian(city: String, country: String, method: Int): Call<MonthScheduleResponse> {
        return NetworkService.create().getGregorianPrayerTime(city, country, method)
    }

}