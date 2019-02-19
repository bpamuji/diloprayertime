package com.bayupamuji.dilojadwalsholat.utils;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class FetchAddressIntentService extends IntentService {

    private static final String IDENTIFIER = "GetAddressIntentService";
    private ResultReceiver addressResultReceiver;

    public FetchAddressIntentService() {
        super(IDENTIFIER);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String msg;

        if (intent != null) {
            addressResultReceiver = intent.getParcelableExtra("add_receiver");
        }

        if (addressResultReceiver == null) {
            Log.e("GetAddressIntentService",
                    "No receiver, not processing the request further");
            return;
        }

        Location location = intent.getParcelableExtra("add_location");

        if (location == null) {
            msg = "No location, can't go further without location";
            sendResultsToReceiver(0, msg,"");
            return;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
        } catch (Exception ioException) {
            Log.e("", "Error in getting address for the location");
        }

        if (addresses == null || addresses.size() == 0) {
            msg = "No address found for the location";
            sendResultsToReceiver(1, msg,"");
        } else {
            Address address = addresses.get(0);
            String addressDetails = address.getSubAdminArea();
            String addressDetails2 = address.getCountryName();
            sendResultsToReceiver(2, addressDetails, addressDetails2);
        }
    }

    private void sendResultsToReceiver(int resultCode, String message, String message2) {
        Bundle bundle = new Bundle();
        bundle.putString("address_result", message);
        bundle.putString("address_result2", message2);
        addressResultReceiver.send(resultCode, bundle);
    }
}
