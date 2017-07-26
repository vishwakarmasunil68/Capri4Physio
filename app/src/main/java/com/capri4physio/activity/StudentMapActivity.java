package com.capri4physio.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.capri4physio.R;
import com.capri4physio.fragment.assessment.GPSTracker;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.fragment.assessment.InfoApps;
import com.capri4physio.net.ApiConfig;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;

    GPSTracker gps;
    ProgressDialog pDialog;
    InfoApps infoApps;
    LatLng latlng;
    public ArrayList<InfoApps> arrayList = new ArrayList<>();
    private Map<Marker, Integer> mMarkerMap = new HashMap<Marker, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_map);
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        gps = new GPSTracker(this);

        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            latlng = new LatLng(latitude, longitude);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        new ViewAllClinic(googleMap).execute();


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {

                int index = mMarkerMap.get(marker);
                Log.e("index", index + "");
                return true;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                int index = mMarkerMap.get(marker);
                Log.e("index", index + "");
            }
        });
    }

    private class ViewAllClinic extends AsyncTask<String, String, String> {
        String id, catagoryName;
        GoogleMap googleMap;

        public ViewAllClinic(GoogleMap googleMap) {
            this.googleMap = googleMap;
        }

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            initProgressDialog("Please wait...");
        }

        @Override
        protected String doInBackground(String... params) {
            String content = HttpULRConnect.getData(ApiConfig.VIEW_CLINIC);
            Log.e("DoInBackGround ---->", String.valueOf(content));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();
            try {
                Log.e("DoInBackGroundtr", String.valueOf(s));
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray jsonArray = new JSONArray(s);
                Log.e("Post Method", jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String id = jsonObject2.getString("id");
                    String staff_code = jsonObject2.getString("bracch_code");
                    String smobile = jsonObject2.getString("mobile");
                    String semail = jsonObject2.getString("email");
                    String password = jsonObject2.getString("show_password");
                    String sfirst_name = jsonObject2.getString("first_name");
                    String address = jsonObject2.getString("address2");
                    String age = jsonObject2.getString("age");
                    String lat = jsonObject2.getString("lat");
                    String lng = jsonObject2.getString("lng");
                    Double latitude = Double.valueOf(lat);
                    Double longtitude = Double.valueOf(lng);
                    infoApps = new InfoApps();
                    infoApps.setLat(Double.valueOf(lat));
                    infoApps.setId(id);
                    infoApps.setDataAdd(address);
                    infoApps.setLng(Double.valueOf(lng));//date
                    arrayList.add(infoApps);
                    Log.e("arrayList", arrayList.toString());
                }
                setMarker(googleMap, arrayList);

            } catch (Exception e) {
                Log.e("error", e.toString());

            }
        }

    }

    private final String TAG = getClass().getSimpleName();

    private void setMarker(GoogleMap googleMap, ArrayList<InfoApps> arrayList) {
        Log.d(TAG, "size:-" + arrayList.size());
        Log.d(TAG, "size:-" + arrayList.toString());
//        final LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i1 = 0; i1 < arrayList.size(); i1++) {

            LatLng position = new LatLng(arrayList.get(i1).getLat(), arrayList.get(i1).getLng());
            drawMarker(position, arrayList.get(i1).getDataAdd(), arrayList.get(i1).getId(), i1);
        }


    }
    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void drawMarker(LatLng point, String address, String ID, Integer positionInLocationList) {
        Log.d("mMap", "mMap");
// Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

// Setting latitude and longitude for the marker
        markerOptions.position(point).title(address).snippet(point + "");


// Adding marker on the Google Map
        Marker marker = mMap.addMarker(markerOptions);
        mMarkerMap.put(marker, positionInLocationList);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latlng).zoom(15).build();
//				 Log.e("Thus Marker",point.toString());
//				Log.e("Thus name",name.toString());
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));


    }

}
