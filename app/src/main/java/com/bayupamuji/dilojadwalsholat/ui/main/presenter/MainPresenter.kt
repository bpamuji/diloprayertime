package com.bayupamuji.dilojadwalsholat.ui.main.presenter

import android.util.Log
import com.bayupamuji.dilojadwalsholat.data.entity.MonthScheduleResponse
import com.bayupamuji.dilojadwalsholat.data.entity.ScheduleResponse
import com.bayupamuji.dilojadwalsholat.data.repo.TimePrayerScheduleRepo
import com.bayupamuji.dilojadwalsholat.ui.main.view.MainView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(private val repo: TimePrayerScheduleRepo, private val view: MainView) {

    fun getCountry() {
        val listCountry = listOf(
            "Jakarta", "Surabaya",
            "Medan", "Bandung",
            "Makassar", "Semarang",
            "Balikpapan", "Palembang",
            "Pekanbaru", "Banjarmasin",
            "Batam", "Pontianak",
            "Surakarta", "Samarinda",
            "Padang", "Yogyakarta",
            "Malang", "Manado",
            "Denpasar", "Lampung"
        )
        view.showCountry(listCountry)
    }

    fun getDailySchedule(city:String,country: String) {
        view.showLoading()
        repo.getDailyTimePrayer(city, country,11).enqueue(object : Callback<ScheduleResponse> {
            override fun onFailure(call: Call<ScheduleResponse>, t: Throwable) {
                view.hideLoading()
                Log.e("error schedule", t.localizedMessage)
            }

            override fun onResponse(call: Call<ScheduleResponse>, response: Response<ScheduleResponse>) {
                view.hideLoading()
                if (response.isSuccessful && response.body()?.code == 200) {
                    val result = response.body()
                    result?.data?.let { view.showSchedule(it) }
                } else {
                    view.onError(response.message())
                }
            }

        })
    }

    fun getScheduleByHijri(city: String, country: String){
        view.showLoading()
        repo.getScheduleByHijri(city,country,11).enqueue(object : Callback<MonthScheduleResponse>{
            override fun onFailure(call: Call<MonthScheduleResponse>, t: Throwable) {
                view.hideLoading()
                Log.e("error schedule", t.localizedMessage)
            }

            override fun onResponse(call: Call<MonthScheduleResponse>, responseMonth: Response<MonthScheduleResponse>) {
                view.hideLoading()
                if (responseMonth.isSuccessful && responseMonth.body()?.code == 200){
                    val result = responseMonth.body()
                    if (result?.data.isNullOrEmpty()){
                        view.onError("Sorry, data is empty")
                    }else{
                        result?.data?.let { view.showScheduleByMonth(it) }
                    }
                }else{
                    view.onError(responseMonth.message())
                }
            }

        })
    }
    fun getScheduleGregorian(city: String, country: String){
        view.showLoading()
        repo.getScheduleByGregorian(city,country,11).enqueue(object : Callback<MonthScheduleResponse>{
            override fun onFailure(call: Call<MonthScheduleResponse>, t: Throwable) {
                view.hideLoading()
                Log.e("error schedule", t.localizedMessage)
            }

            override fun onResponse(call: Call<MonthScheduleResponse>, responseMonth: Response<MonthScheduleResponse>) {
                view.hideLoading()
                if (responseMonth.isSuccessful && responseMonth.body()?.code == 200){
                    val result = responseMonth.body()
                    if (result?.data.isNullOrEmpty()){
                        view.onError("Sorry, data is empty")
                    }else{
                        result?.data?.let { view.showScheduleByMonth(it) }
                    }
                }else{
                    view.onError(responseMonth.message())
                }
            }

        })
    }

}