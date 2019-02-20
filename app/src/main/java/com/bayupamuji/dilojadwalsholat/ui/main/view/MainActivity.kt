package com.bayupamuji.dilojadwalsholat.ui.main.view

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.bayupamuji.dilojadwalsholat.R
import com.bayupamuji.dilojadwalsholat.ui.main.presenter.LocationPresenter
import com.bayupamuji.dilojadwalsholat.utils.FetchAddressIntentService
import com.bayupamuji.dilojadwalsholat.utils.SharePreferencesUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), LocationContract {

    private lateinit var drawerToggle: Any
    private lateinit var presenter: LocationPresenter
    private lateinit var sp:SharePreferencesUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        initLocation()
        initNavigation()
    }

    private fun initLocation() {
        sp = SharePreferencesUtils(this)
        presenter = LocationPresenter(this)
        presenter.initLocation(this)
    }

    private fun loadFragment(savedInstanceState: Bundle?, type:String) {
        if (savedInstanceState == null) {
            when(type){
                "daily" -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_frame, DailyFragment(), DailyFragment::class.java.simpleName)
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
                }
                "hijri" ->{
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_frame, HijriFragment(), HijriFragment::class.java.simpleName)
                        .addToBackStack(null)
                        .commit()
                }
                "gregorian" -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_frame, GregorianFragment(), GregorianFragment::class.java.simpleName)
                        .addToBackStack(null)
                        .commit()
                }

                else -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_frame, DailyFragment(), DailyFragment::class.java.simpleName)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    private fun initNavigation() {
        drawerToggle = ActionBarDrawerToggle(this@MainActivity, drawer_layout, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(drawerToggle as ActionBarDrawerToggle)
        (drawerToggle as ActionBarDrawerToggle).syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.daily_menu -> {
                    loadFragment(null,"daily")
                    supportActionBar?.title = "Today"
                    true
                }
                R.id.hijri_menu -> {
                    loadFragment(null,"hijri")
                    supportActionBar?.title = "Hijri Calendar"
                    true
                }
                R.id.gregorian_menu ->{
                    loadFragment(null,"gregorian")
                    supportActionBar?.title = "Gregorian Calendar"
                    true
                }
                else -> true
            }
        }
        nav_view.menu.getItem(0).isChecked = true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if ((drawerToggle as ActionBarDrawerToggle).onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    override fun intentService(addressReceiver: LocationPresenter.LocationAddressReceiver?, location: Location?) {
        val intent = Intent(this, FetchAddressIntentService::class.java)
        intent.putExtra("add_receiver", addressReceiver)
        intent.putExtra("add_location", location)
        startService(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.startLocationAddress(this)
                } else {
                    showToast("Location permission not granted, restart the app if you want the feature")
                }
                return
            }
        }
    }

    override fun showResults(city: String?, location: String?) {
        sp.setString("sp_city",city)
        sp.setString("sp_country",location)
        loadFragment(null,"daily")
    }

    override fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 2
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this@MainActivity.finish()
    }
}
