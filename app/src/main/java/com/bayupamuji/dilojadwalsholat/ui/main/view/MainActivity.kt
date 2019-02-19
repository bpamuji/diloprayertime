package com.bayupamuji.dilojadwalsholat.ui.main.view

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.bayupamuji.dilojadwalsholat.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var drawerToggle: Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        initNavigation()
        loadFragment(savedInstanceState,"daily")
    }

    private fun loadFragment(savedInstanceState: Bundle?, type:String) {
        if (savedInstanceState == null) {
            when(type){
                "daily" -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_frame, DailyFragment(), DailyFragment::class.java.simpleName)
                        .addToBackStack(null)
                        .commit()
                }
//                "weekly" ->{
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.content_frame, WeeklyFragment(), WeeklyFragment::class.java.simpleName)
//                        .addToBackStack(null)
//                        .commit()
//                }

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
                    true
                }
                R.id.weekly_menu -> {
                    Toast.makeText(this, "Weekly", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.monthly_menu ->{
                    Toast.makeText(this, "Monthly", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.yearly_menu -> {
                    Toast.makeText(this, "Yearly", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if ((drawerToggle as ActionBarDrawerToggle).onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 2
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this@MainActivity.finish()
    }
}
