package com.bayupamuji.dilojadwalsholat.data.repo

import com.bayupamuji.dilojadwalsholat.data.NetworkService
import com.bayupamuji.dilojadwalsholat.data.entity.JadwalResponse
import retrofit2.Call

class TimePrayerScheduleRepoImpl : TimePrayerScheduleRepo {
    override fun getDailyTimePrayer(city: String, country: String, method:Int): Call<JadwalResponse> {
        return NetworkService.create().getDailyTimePrayer(city, country, method)
    }

//    override fun getJadwalWeekly(country:String, key:String): Call<JadwalResponse> {
//        return NetworkService.create().getJadWalWeekly(country, key)
//    }

}