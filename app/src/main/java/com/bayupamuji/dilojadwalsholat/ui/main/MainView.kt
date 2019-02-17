package com.bayupamuji.dilojadwalsholat.ui.main

import android.location.Location
import com.bayupamuji.dilojadwalsholat.data.entity.Item

interface MainView {
    fun showCountry(listCountry: List<String>)
    fun onError(message: String)
    fun showSchedule(result: List<Item>)
    fun showLoading()
    fun hideLoading()
    fun showToast(s: String)
    fun showResults(location: String)
    fun intentService(addressReceiver: MainPresenter.LocationAddressReceiver?, location: Location?)
}
