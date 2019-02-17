package com.bayupamuji.dilojadwalsholat.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.design.chip.Chip
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.bayupamuji.dilojadwalsholat.R
import com.bayupamuji.dilojadwalsholat.data.entity.Item
import com.bayupamuji.dilojadwalsholat.data.repo.JadwalRepoImpl
import com.bayupamuji.dilojadwalsholat.utils.FetchAddressIntentService
import com.bayupamuji.dilojadwalsholat.utils.dateFormat
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), MainView {
    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 2
    }

    private lateinit var presenter: MainPresenter
    private lateinit var repo: JadwalRepoImpl

    private val countries = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()
    }

    private fun getData() {
        repo = JadwalRepoImpl()
        presenter = MainPresenter(repo, this)
        presenter.getCountry()
        presenter.initLocation(this@MainActivity)
    }

    override fun showLoading() {
        progressbar.visibility = VISIBLE
        card.visibility = GONE
        img_calendar.visibility = GONE
        tv_asr.visibility = GONE
        tv_dhuhr.visibility = GONE
        tv_maghrib.visibility = GONE
        tv_isha.visibility = GONE
        tv_shurooq.visibility = GONE
        tv_fajr.visibility = GONE

        tv_calendar.visibility = GONE
        tv_dhuhr_time.visibility = GONE
        tv_asr_time.visibility = GONE
        tv_maghrib_time.visibility = GONE
        tv_isha_time.visibility = GONE
        tv_shurooq_time.visibility = GONE
        tv_fajr_time.visibility = GONE

        view1.visibility = GONE
        view2.visibility = GONE
        view3.visibility = GONE
        view4.visibility = GONE
        view5.visibility = GONE
    }

    override fun hideLoading() {
        progressbar.visibility = GONE
        card.visibility = VISIBLE
        img_calendar.visibility = VISIBLE
        tv_dhuhr.visibility = VISIBLE
        tv_asr.visibility = VISIBLE
        tv_maghrib.visibility = VISIBLE
        tv_isha.visibility = VISIBLE
        tv_shurooq.visibility = VISIBLE
        tv_fajr.visibility = VISIBLE

        tv_calendar.visibility = VISIBLE
        tv_dhuhr_time.visibility = VISIBLE
        tv_asr_time.visibility = VISIBLE
        tv_maghrib_time.visibility = VISIBLE
        tv_isha_time.visibility = VISIBLE
        tv_shurooq_time.visibility = VISIBLE
        tv_fajr_time.visibility = VISIBLE

        view1.visibility = VISIBLE
        view2.visibility = VISIBLE
        view3.visibility = VISIBLE
        view4.visibility = VISIBLE
        view5.visibility = VISIBLE
    }

    override fun onError(message: String) {
        showToast("Error $message")
    }

    override fun showSchedule(result: List<Item>) {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(result[0].date_for)
        tv_calendar.text = dateFormat(date)
        tv_fajr_time.text = result[0].fajr
        tv_shurooq_time.text = result[0].shurooq
        tv_dhuhr_time.text = result[0].dhuhr
        tv_asr_time.text = result[0].asr
        tv_maghrib_time.text = result[0].maghrib
        tv_isha_time.text = result[0].isha
    }

    override fun showCountry(listCountry: List<String>) {
        countries.clear()
        countries.addAll(listCountry)
        for (index in countries.indices) {
            val chip = Chip(chip_countries.context)
            chip.text = countries[index]
            chip.isClickable = true
            chip.isCheckable = true
            chip_countries.addView(chip)

            chip.setOnClickListener {
                presenter.getDailySchedule(countries[index])
                supportActionBar?.title = countries[index]
                tv_recent_country.text = countries[index]
                if (!chip.isChecked) presenter.initLocation(this@MainActivity)
            }
        }
    }

    override fun showResults(location: String) {
        presenter.getDailySchedule(location)
        supportActionBar?.title = location
        tv_recent_country.text = location
    }

    override fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun intentService(addressReceiver: MainPresenter.LocationAddressReceiver?, location: Location?) {
        val intent = Intent(this, FetchAddressIntentService::class.java)
        intent.putExtra("add_receiver", addressReceiver)
        intent.putExtra("add_location", location)
        startService(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.startLocationAddress(this@MainActivity)
                } else {
                    showToast("Location permission not granted, restart the app if you want the feature")
                }
                return
            }
        }
    }
}
