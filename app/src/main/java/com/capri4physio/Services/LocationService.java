package com.capri4physio.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.capri4physio.model.therapist.TherapistPOJO;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.TagUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Locale;

/**
 * Created by sunil on 09-08-2017.
 */

public class LocationService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 10000;
    private static final float LOCATION_DISTANCE = 0;

    private DatabaseReference root;

    private class LocationListener implements android.location.LocationListener
    {
        Location mLastLocation;

        public LocationListener(String provider)
        {
            Log.e(TagUtils.getTag(), "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location)
        {
            Log.e(TagUtils.getTag(), "onLocationChanged: " + location);
            mLastLocation.set(location);
            try {
                String address = getAddress(location.getLatitude(), location.getLongitude());
                Log.d(TagUtils.getTag(), "Address:-" + address);
//            if(AppPreferences.getInstance(getApplicationContext()).getUserType().equals(""))
                String id = AppPreferences.getInstance(getApplicationContext()).getUserID();
                String name = AppPreferences.getInstance(getApplicationContext()).getFirstName() + " " + AppPreferences.getInstance(getApplicationContext()).getLastName();
                String email = AppPreferences.getInstance(getApplicationContext()).getEmail();
                String deviceToken = AppPreferences.getInstance(getApplicationContext()).getDeviceToken();
                String branch_code = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
                TherapistPOJO therapistPOJO = new TherapistPOJO(id, name, email, address, deviceToken, branch_code);
                root.child("therapist").child(therapistPOJO.getId()).setValue(therapistPOJO);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public String getAddress(double latitude, double longitude) {
            String address = "";
//                    LocationAddress.getAddressFromLocation(latitude,longitude,LocationService.this,new GeocoderHandler());
            Geocoder geocoder = new Geocoder(LocationService.this, Locale.getDefault());

            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses != null && addresses.size() > 0) {
                    Address returnedAddress = addresses.get(0);

                    StringBuilder strReturnedAddress = new StringBuilder();
                    for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                    }
                    Log.d("sunil", strReturnedAddress.toString());
//                address = strReturnedAddress.toString();
                    return strReturnedAddress.toString();
                } else {
                    Log.d("sunil", "No Address returned!");
                    return "";
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.d("sunil", "Canont get Address!");
                return "";
            }
        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Log.e(TagUtils.getTag(), "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Log.e(TagUtils.getTag(), "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            Log.e(TagUtils.getTag(), "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.e(TagUtils.getTag(), "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate()
    {
        Log.e(TagUtils.getTag(), "onCreate");
        root = FirebaseDatabase.getInstance().getReference().getRoot();
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TagUtils.getTag(), "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TagUtils.getTag(), "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TagUtils.getTag(), "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TagUtils.getTag(), "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy()
    {
        Log.e(TagUtils.getTag(), "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TagUtils.getTag(), "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TagUtils.getTag(), "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

}
