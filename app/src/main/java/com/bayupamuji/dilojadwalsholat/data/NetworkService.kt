package com.bayupamuji.dilojadwalsholat.data

import com.bayupamuji.dilojadwalsholat.data.entity.MonthScheduleResponse
import com.bayupamuji.dilojadwalsholat.data.entity.ScheduleResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("v1/timingsByCity")
    fun getDailyTimePrayer(
        @Query("city") city: String,
        @Query("country") country: String,
        @Query("method") method: Int
    )
            : Call<ScheduleResponse>

    @GET("v1/hijriCalendarByCity")
    fun getHijriPrayerTime(
        @Query("city") city: String,
        @Query("country") country: String,
        @Query("method") method: Int
    )
            : Call<MonthScheduleResponse>

@GET("v1/calendarByCity")
    fun getGregorianPrayerTime(
        @Query("city") city: String,
        @Query("country") country: String,
        @Query("method") method: Int
    )
            : Call<MonthScheduleResponse>

    companion object {
        fun create(): NetworkService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.aladhan.com/")
                .build()
            return retrofit.create(NetworkService::class.java)
        }
    }
}