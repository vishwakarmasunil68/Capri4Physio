package com.capri4physio.fragment.assessment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.capri4physio.R;
import com.capri4physio.fragment.BaseFragment;
import com.capri4physio.net.ApiConfig;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by emobi on 17-03-2017.
 */

public class MapFragmentView extends BaseFragment implements OnMapReadyCallback {
    GoogleMap mMap;
    GPSTracker gps;
    ProgressDialog pDialog;
    InfoApps infoApps;
    LatLng latlng;
    public ArrayList<InfoApps> arrayList = new ArrayList<>();
    private Map<Marker, Integer> mMarkerMap = new HashMap<Marker, Integer>();

    public static MapFragmentView newInstance() {
        MapFragmentView fragment = new MapFragmentView();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        arrayList=new ArrayList<>();
        Toast.makeText(getActivity(), "mapfragment", Toast.LENGTH_SHORT).show();
//        new ViewAllClinic().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_mapfrstudent, container, false);
        setUpGoogleMap();
        gps = new GPSTracker(getActivity());

////
////        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            latlng = new LatLng(latitude, longitude);
        }
        return root;
    }

    public void setUpGoogleMap() {

//        SupportMapFragment supportMapFragment=(getSupportFragmentManager().
//                findFragmentById(R.id.googlmap)).getMap();
//        GoogleMap mGoogleMap=((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.googlmap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
//            googleMap.addMarker(new MarkerOptions()
//                    .position(position)
//                    .title("markerTitle").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
////            Toast.makeText(getActivity(), arrayList.size() + " " + "size", Toast.LENGTH_LONG).show();
//            Log.d("sixe", arrayList.size() + "");
//            final LatLng position = new LatLng(arrayList.get(i1).getLat(), arrayList.get(i1).getLng());
//            final MarkerOptions options = new MarkerOptions().position(position);
//
//            googleMap.addMarker(options);
//
//            builder.include(position);
//            googleMap.animateCamera(CameraUpdateFactory.newLatLng(position));
        }
    }

    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(getActivity());
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
//                String position= arrayList.get()
//                    marker.setTitle(arrayList.get());
            }
        });
    }
//        final LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        Toast.makeText(getActivity(),arrayList.size()+""+"size",Toast.LENGTH_LONG).show();
//        for (int i = 0; i < arrayList.size(); i++) {
//            Toast.makeText(getActivity(),"size",Toast.LENGTH_LONG).show();
//            Log.d("sixe",arrayList.size()+"");
//            final LatLng position = new LatLng(arrayList.get(i).getLat(), arrayList.get(i).getLng());
//            final MarkerOptions options = new MarkerOptions().position(position);
//
//            googleMap.addMarker(options);
//
//            builder.include(position);
//            googleMap.animateCamera(CameraUpdateFactory.newLatLng(position));
//        }
//        getLocation(mMap);
//    }
//    public void getLocation(GoogleMap map) {
//
////        gps = new GPSTracker(getActivity());
////
////        // check if GPS enabled
//////        if (gps.canGetLocation()) {
////
////            double latitude = gps.getLatitude();
////            double longitude = gps.getLongitude();
////            LatLng latlng = new LatLng(latitude, longitude);
//            final LatLngBounds.Builder builder = new LatLngBounds.Builder();
//
//            for (int i = 0; i < arrayList.size(); i++) {
//                Toast.makeText(getActivity(),"size",Toast.LENGTH_LONG).show();
//                Log.d("sixe",arrayList.size()+"");
//                final LatLng position = new LatLng(arrayList.get(i).getLat(), arrayList.get(i).getLng());
//                final MarkerOptions options = new MarkerOptions().position(position);
//
//                map.addMarker(options);
//
//                builder.include(position);
//                map.animateCamera(CameraUpdateFactory.newLatLng(position));
//            }
//        googlemap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
////                    marker.setTitle(arrayList.get());
//            }
//        });
//    }
//            String address=getAddress(latitude,longitude);
//            mMap.addMarker(new MarkerOptions()
//                    .position(new LatLng(latitude, longitude))
//                    .title(address).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)) );
//            mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
//            showFriendsOnMAP(list_friends);
//            Log.d("sun", latitude + "\n" + longitude);
//        } else {
//            gps.showSettingsAlert();
//        }
//    }
}

