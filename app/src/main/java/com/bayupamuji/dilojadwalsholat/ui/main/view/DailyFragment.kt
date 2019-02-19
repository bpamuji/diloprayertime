package com.bayupamuji.dilojadwalsholat.ui.main.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.design.chip.Chip
import android.support.design.chip.ChipGroup
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.bayupamuji.dilojadwalsholat.R
import com.bayupamuji.dilojadwalsholat.data.entity.Data
import com.bayupamuji.dilojadwalsholat.data.repo.TimePrayerScheduleRepoImpl
import com.bayupamuji.dilojadwalsholat.ui.main.adapter.MainAdapter
import com.bayupamuji.dilojadwalsholat.ui.main.presenter.MainPresenter
import com.bayupamuji.dilojadwalsholat.ui.main.view.MainActivity.Companion.LOCATION_PERMISSION_REQUEST_CODE
import com.bayupamuji.dilojadwalsholat.utils.FetchAddressIntentService

class DailyFragment : Fragment(), MainView {

    private val countries = mutableListOf<String>()
    private val schedules = mutableListOf<Data>()

    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter
    private lateinit var repo: TimePrayerScheduleRepoImpl
    private lateinit var chips: ChipGroup
    private lateinit var card: CardView
    private lateinit var tvRecentCountry: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var rvSchedule: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chips = view.findViewById(R.id.chip_countries)
        card = view.findViewById(R.id.card)
        tvRecentCountry = view.findViewById(R.id.tv_recent_country)
        progressBar = view.findViewById(R.id.progressbar)
        rvSchedule = view.findViewById(R.id.rv_schedule)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getData()
    }

    private fun getData() {
        repo = TimePrayerScheduleRepoImpl()
        presenter = MainPresenter(repo, this)
        presenter.getCountry()
        activity?.let { presenter.initLocation(it) }

        adapter = MainAdapter(schedules)
        rvSchedule.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvSchedule.adapter = adapter
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        card.visibility = View.GONE
        rvSchedule.visibility = View.GONE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        card.visibility = View.VISIBLE
        rvSchedule.visibility = View.VISIBLE
    }

    override fun onError(message: String) {
        showToast("Error $message")
    }

    override fun showSchedule(result: Data) {
        schedules.clear()
        schedules.add(result)
        adapter.notifyDataSetChanged()
    }

    override fun showCountry(listCountry: List<String>) {
        countries.clear()
        countries.addAll(listCountry)
        for (index in countries.indices) {
            val chip = Chip(chips.context)
            chip.text = countries[index]
            chip.isClickable = true
            chip.isCheckable = true
            chips.addView(chip)

            chip.setOnClickListener {
                presenter.getDailySchedule("indonesia", countries[index])
                tvRecentCountry.text = "${countries[index]}, Indonesia"
                //tvHeader.text = countries[index]
                if (!chip.isChecked) activity?.let { activity -> presenter.initLocation(activity) }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun showResults(city: String?, location: String?) {
        city?.let {
            location?.let { location -> presenter.getDailySchedule(it, location) }
        }
        //tvHeader.text = location
        tvRecentCountry.text = "$location, $city"
    }

    override fun showToast(s: String) {
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show()
    }

    override fun intentService(addressReceiver: MainPresenter.LocationAddressReceiver?, location: Location?) {
        val intent = Intent(activity, FetchAddressIntentService::class.java)
        intent.putExtra("add_receiver", addressReceiver)
        intent.putExtra("add_location", location)
        activity?.startService(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    activity?.let { presenter.startLocationAddress(it) }
                } else {
                    showToast("Location permission not granted, restart the app if you want the feature")
                }
                return
            }
        }
    }

}

