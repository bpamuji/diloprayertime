package com.bayupamuji.dilojadwalsholat.data

import com.bayupamuji.dilojadwalsholat.data.entity.JadwalResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {
    @GET("{country}/weekly.json")
    fun getJadWalWeekly(@Path("country") country: String, @Query("key") key: String): Call<JadwalResponse>

    @GET("{country}/daily.json")
    fun getJadwalDaily(@Path("country") country: String, @Query("key") key: String) : Call<JadwalResponse>

    companion object {
        fun create(): NetworkService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://muslimsalat.com/")
                .build()
            return retrofit.create(NetworkService::class.java)
        }
    }
}