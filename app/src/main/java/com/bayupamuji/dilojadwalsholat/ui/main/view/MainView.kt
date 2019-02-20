package com.bayupamuji.dilojadwalsholat.ui.main.view

import android.location.Location
import com.bayupamuji.dilojadwalsholat.data.entity.Data
import com.bayupamuji.dilojadwalsholat.ui.main.presenter.LocationPresenter

interface MainView {
    fun showCountry(listCountry: List<String>)
    fun onError(message: String)
    fun showSchedule(result: Data)
    fun showLoading()
    fun hideLoading()
    fun showScheduleByMonth(data: List<Data>)
}

interface LocationContract{

    fun showResults(city:String?, location: String?)
    fun intentService(addressReceiver: LocationPresenter.LocationAddressReceiver?, location: Location?)
    fun showToast(s: String)
}
