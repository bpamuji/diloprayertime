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
import com.bayupamuji.dilojadwalsholat.ui.main.view.LocationContract
import com.bayupamuji.dilojadwalsholat.ui.main.view.MainActivity
import com.google.android.gms.location.*

class LocationPresenter(private val view:LocationContract) {
    private var addressReceiver: LocationAddressReceiver? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null
    private var location: Location? = null

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