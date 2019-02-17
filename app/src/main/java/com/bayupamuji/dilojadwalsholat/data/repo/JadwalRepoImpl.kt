package com.bayupamuji.dilojadwalsholat.data.repo

import com.bayupamuji.dilojadwalsholat.data.NetworkService
import com.bayupamuji.dilojadwalsholat.data.entity.JadwalResponse
import retrofit2.Call

class JadwalRepoImpl : JadwalRepo {
    override fun getJadwalDaily(country: String, key: String): Call<JadwalResponse> {
        return NetworkService.create().getJadwalDaily(country, key)
    }

    override fun getJadwalWeekly(country:String, key:String): Call<JadwalResponse> {
        return NetworkService.create().getJadWalWeekly(country, key)
    }

}