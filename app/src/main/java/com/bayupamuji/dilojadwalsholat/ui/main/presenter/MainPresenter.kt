package com.bayupamuji.dilojadwalsholat.ui.main.presenter

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.bayupamuji.dilojadwalsholat.data.entity.JadwalResponse
import com.bayupamuji.dilojadwalsholat.data.repo.TimePrayerScheduleRepo
import com.bayupamuji.dilojadwalsholat.ui.main.view.MainView
import com.bayupamuji.dilojadwalsholat.ui.main.view.MainActivity
import com.google.android.gms.location.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(private val repo: TimePrayerScheduleRepo, private val view: MainView) {
    private var addressReceiver: LocationAddressReceiver? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null
    private var location: Location? = null

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
        repo.getDailyTimePrayer(city, country,11).enqueue(object : Callback<JadwalResponse> {
            override fun onFailure(call: Call<JadwalResponse>, t: Throwable) {
                view.hideLoading()
                Log.e("error schedule", t.localizedMessage)
            }

            override fun onResponse(call: Call<JadwalResponse>, response: Response<JadwalResponse>) {
                view.hideLoading()
                if (response.isSuccessful && response.body()?.code == 200) {
                    val result = response.body()
                    /*if (result?.data?.equals(null)) {
                        view.onError("Schedule is empty")
                    } else {
                        result?.items?.let { view.showSchedule(it) }
                    }*/
                    result?.data?.let { view.showSchedule(it) }
                } else {
                    Log.e("error onresponse", response.message())
                }
            }

        })
    }

//    fun getWeeklySchedule(country: String){
//        view.showLoading()
//        repo.getJadwalWeekly(country, "cc99165154eaaefd4d4898baed75d5c9").enqueue(object : Callback<JadwalResponse>{
//            override fun onResponse(call: Call<JadwalResponse>, response: Response<JadwalResponse>) {
//                view.hideLoading()
//                if (response.isSuccessful) {
//                    val result = response.body()
//                    if (result?.items.isNullOrEmpty()) {
//                        view.onError("Schedule is empty")
//                    } else {
//                        result?.items?.let { view.showSchedule(it) }
//                    }
//                } else {
//                    Log.e("error onresponse weekly", response.message())
//                }
//            }
//
//            override fun onFailure(call: Call<JadwalResponse>, t: Throwable) {
//                view.hideLoading()
//                Log.e("error weekly schedule", t.localizedMessage)
//            }
//
//        })
//    }

    fun initLocation(context: Activity) {
        addressReceiver = LocationAddressReceiver(Handler())
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                location = p0.locations[0]
                getAddress()
            }
        }
        startLocationAddress(context)
    }

    fun startLocationAddress(context: Activity) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MainActivity.LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            val locationRequest = LocationRequest()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            fusedLocationProviderClient?.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    private fun getAddress() {
        if (!Geocoder.isPresent()) {
            view.showToast("Can't find current address")
        }

        view.intentService(addressReceiver, location)
    }

    fun removeLocationUpdate() {
        fusedLocationProviderClient?.removeLocationUpdates(locationCallback)
    }

    inner class LocationAddressReceiver(handler: Handler) : ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            if (resultCode == 0) {
                Log.d("Address", "Location null retrying")
                getAddress()
            }
            if (resultCode == 1) {
                view.showToast("Address not found")
            }
            val address = resultData?.getString("address_result")
            val address2 = resultData?.getString("address_result2")

            view.showResults(address2, address)

        }
    }
}