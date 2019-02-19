package com.bayupamuji.dilojadwalsholat.ui.main.view

import android.location.Location
import com.bayupamuji.dilojadwalsholat.data.entity.Data
import com.bayupamuji.dilojadwalsholat.ui.main.presenter.MainPresenter

interface MainView {
    fun showCountry(listCountry: List<String>)
    fun onError(message: String)
    fun showSchedule(result: Data)
    fun showLoading()
    fun hideLoading()
    fun showToast(s: String)
    fun showResults(city:String?, location: String?)
    fun intentService(addressReceiver: MainPresenter.LocationAddressReceiver?, location: Location?)
}
