package com.capri4physio.fragment.assessment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.fragment.AddAppointmentActivitytbyPatient;
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

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    TextView txtLat;
    MarkerOptions markerOptions;
    LatLng latLng;
    GoogleMap mMap;
    GPSTracker gps;
    ProgressDialog pDialog;
    InfoApps infoApps;
    LatLng latlng;
    private Map<Marker, Integer> mMarkerMap = new HashMap<Marker, Integer>();
    public ArrayList<InfoApps> arrayList = new ArrayList<>();
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    GoogleMap googleMap;
    private ArrayList<LatLng> arrayPoints = null;
    MarkerOptions markeroptions;
    String username, Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

//		googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
//		        Button btn_find = (Button) findViewById(R.id.btn_find);
        // Getting a reference to the map
        supportMapFragment.getMapAsync(this);
//		setUpGoogleMap();
        gps = new GPSTracker(MapActivity.this);
////
////        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            latlng = new LatLng(latitude, longitude);
        } else {
//			show
        }
    }

//	public void setUpGoogleMap() {
//		MapFragment mapFragment = (MapFragment) getFragmentManager()
//				.findFragmentById(R.id.googlmap);
//		mapFragment.getMapAsync(this);
//	}


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
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
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
                    String bracch_code = jsonObject2.getString("bracch_code");

                    String address = jsonObject2.getString("address2");
//                    String smobile = jsonObject2.getString("mobile");
//                    String semail = jsonObject2.getString("email");
//                    String password = jsonObject2.getString("show_password");
//                    String sfirst_name=jsonObject2.getString("first_name");
//                    String dob = jsonObject2.getString("dob");
//                    String age = jsonObject2.getString("age");
                    String lat = jsonObject2.getString("lat");
                    String lng = jsonObject2.getString("lng");
//					Double latitude = Double.valueOf(lat);
//					Double longtitude = Double.valueOf(lng);
                    infoApps = new InfoApps();
                    infoApps.setId(id);
                    infoApps.setLat(Double.valueOf(lat));
                    infoApps.setStr4(bracch_code);
                    infoApps.setDataAdd(address);
                    infoApps.setLng(Double.valueOf(lng));//date
                    arrayList.add(infoApps);

//                    Toast.makeText(getActivity(),arrayList.size()+""+"size",Toast.LENGTH_LONG).show();

                    Log.e("arrayList", arrayList.toString());
//                    loadMapFragment();

                }
                setMarker(googleMap, arrayList);

            } catch (Exception e) {
                Log.e("error", e.toString());

            }

            //  CatagoryMetod();
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
        pDialog = new ProgressDialog(MapActivity.this);
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

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                int index = mMarkerMap.get(marker);
                String ID = arrayList.get(index).getId();
                LatLng position = new LatLng(arrayList.get(index).getLat(), arrayList.get(index).getLng());
                final String Title = arrayList.get(index).getDataAdd();
                final String bracch_code = arrayList.get(index).getStr4();
                showDialog(Title);

            }
        });
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latlng).zoom(15).build();
//				 Log.e("Thus Marker",point.toString());
//				Log.e("Thus name",name.toString());
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
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
    }


    public void showDialog(final String Title) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(MapActivity.this, R.style.MyDialogTheme);
        builder1.setMessage(Title);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "BOOK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent i = new Intent(MapActivity.this, AddAppointmentActivitytbyPatient.class);
                        i.putExtra("branch_code", Title);
                        startActivity(i);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}