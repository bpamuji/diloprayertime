package com.bayupamuji.dilojadwalsholat.data.repo

import com.bayupamuji.dilojadwalsholat.data.entity.JadwalResponse
import retrofit2.Call

interface JadwalRepo{
    fun getJadwalWeekly(country:String, key:String): Call<JadwalResponse>
    fun getJadwalDaily(country: String, key: String): Call<JadwalResponse>
}