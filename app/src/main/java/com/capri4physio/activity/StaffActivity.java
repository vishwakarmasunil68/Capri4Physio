package com.capri4physio.activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.fragment.ClinicDetailsFragment;
import com.capri4physio.fragment.PatientDashboardFragment;
import com.capri4physio.fragment.PatientProfileFragment;
import com.capri4physio.fragment.assessment.DoFeedbackActivity;
import com.capri4physio.listener.FragmentListener;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.model.ClinicAttribute;
import com.capri4physio.model.ClinicsModel;
import com.capri4physio.model.UserDetailModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.BundleConst;
import com.capri4physio.util.Utils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class StaffActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, FragmentListener<Bundle>, HttpUrlListener {
    public static final int BOOK_APPOINTMENT = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private UserDetailModel mUserModel;

    private List<ClinicAttribute> mListClinics;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initView(navigationView.getHeaderView(0));

        try {
            String userDetails = AppPreferences.getInstance(getApplicationContext()).getUserDetails();
            Gson gson = new Gson();
            mUserModel = gson.fromJson(userDetails, UserDetailModel.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        loadPatientDashboard();


        Log.d("sunil","user_type:-"+AppPreferences.getInstance(this).getUserType());
        Log.d("sunil","staff activity");

    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        TextView txtName = (TextView) view.findViewById(R.id.txt_name);
        TextView txtEmail = (TextView) view.findViewById(R.id.txt_email);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        txtName.setText(AppPreferences.getInstance(this).getUserName());
        txtEmail.setText(AppPreferences.getInstance(this).getEmail());

        try {
            String bitmap = "http://caprispine.in/app/webroot/upload/" + mUserModel.getResult().getUser().getProfilePic();;

            Log.e("stringToBitmap", bitmap.toString());
            Picasso.with(getApplicationContext()).load(bitmap).into(imageView);
        }
        catch (Exception e){

        }
    }

    /**
     * Profile ImageView click method to view profile details
     * @param view
     */
    public void viewProfile(View view) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        PatientProfileFragment fragment = PatientProfileFragment.newInstance();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void loadPatientDashboard() {
        PatientDashboardFragment fragment = PatientDashboardFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void loadMapFragment() {
        MapFragment mMapFragment = MapFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, mMapFragment);
        ft.addToBackStack(null);
        ft.commit();

        mMapFragment.getMapAsync(this);
    }

    private void loadClinicDetails(ClinicAttribute clinic) {
        ClinicDetailsFragment fragment = ClinicDetailsFragment.newInstance(clinic.getId());
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * @return none
     * @description Login web service API calling
     */
    private void ClinicsApiCall() {

        if (Utils.isNetworkAvailable(this)) {

            try {
                JSONObject params = new JSONObject();
//                params.put(ApiConfig.USER_ID, AppPreferences.getInstance(this).getUserID());
                new UrlConnectionTask(this, ApiConfig.CLINICS_URL, ApiConfig.ID1, true, params, ClinicsModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            Utils.showMessage(this, getResources().getString(R.string.err_network));
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setMyLocationEnabled(true);

        final HashMap<String, ClinicAttribute> mPOI = new HashMap<String, ClinicAttribute>();
        if (mListClinics != null && mListClinics.size() > 0) {

            ClinicAttribute clinicMove = mListClinics.get(0);
            String latValue = valueNotNull(clinicMove.getLat());
            String lngValue = valueNotNull(clinicMove.getLng());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(latValue), Double.valueOf(lngValue)), 11));
            MarkerOptions markerOptions;

            for (ClinicAttribute clinic : mListClinics) {

                latValue = valueNotNull(clinic.getLat());
                lngValue = valueNotNull(clinic.getLng());
                markerOptions = new MarkerOptions()
                        .title(clinic.getClinicName())
                        .snippet(clinic.getShortDescription())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .position(new LatLng(Double.valueOf(latValue), Double.valueOf(lngValue)))
                        .anchor(0.0f, 1.0f);

                Marker marker= googleMap.addMarker(markerOptions);

                mPOI.put(marker.getId(), clinic);
            }

        }

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {

                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View myContentsView = getLayoutInflater().inflate(R.layout.map_marker_info, null);
                TextView txtTitle = ((TextView)myContentsView.findViewById(R.id.txt_title));
                txtTitle.setText(marker.getTitle());
                TextView txtSnippet = ((TextView)myContentsView.findViewById(R.id.txt_snippet));
                txtSnippet.setText(marker.getSnippet());
                return myContentsView;
            }
        });

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                ClinicAttribute clinic = mPOI.get(marker.getId());
                loadClinicDetails(clinic);
                //Toast.makeText(StaffActivity.this,"Marker Id - "+clinic.getId()+" Title - "+clinic.getClinicName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onPostSuccess(Object response, int id) {
        Log.d("sunil","response:-"+response.toString());
        Log.d("sunil","id:-"+id);

        {

            switch (id) {
                case ApiConfig.ID1:
                    ClinicsModel clinicsModel = (ClinicsModel) response;
                    if (clinicsModel.getStatus() == 1) {
                        AppLog.i("Capri4Physio", "Clinics Response : " + clinicsModel.getMessage());
                        mListClinics = clinicsModel.getResult();

                        loadMapFragment();

                    } else {
                        Utils.showMessage(this, clinicsModel.getMessage());
                    }

                    break;


                case ApiConfig.ID3:
                    BaseModel baseResponse = (BaseModel) response;
                    Utils.showMessage(this, baseResponse.getMessage());
                    break;

                default:
                    AppLog.e("Capri4Physio", "UNKNOW RESPONSE - " + id);

            }

        }

    }

    @Override
    public void onPostError(String errMsg, int id) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_alert) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onFragmentResult(Bundle bundle, int Id) {
        ClinicsApiCall();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            getfeedbackActivity();
        } else if (id == R.id.nav_logout) {
            logout();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Logout dialog to exit from the app
     *
     * @return none
     */
    private void getfeedbackActivity(){
        Log.e("check","check");
        Intent notifctionintent=new Intent(StaffActivity.this,DoFeedbackActivity.class);
        startActivity(notifctionintent);
        StaffActivity.this.finish();
    }
    private void logout() {

        AlertDialog.Builder builder = new AlertDialog.Builder(StaffActivity.this);
        builder.setMessage("Are you sure, you want to logout");
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AppPreferences.getInstance(StaffActivity.this).setUserLogin(false);
                dialog.dismiss();
                Intent intent = new Intent(StaffActivity.this, SplashActivity.class);
                intent.putExtra(BundleConst.IS_LOGOUT, true);
                startActivity(intent);
                StaffActivity.this.finish();

            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.create();
        builder.show();
    }

    private String valueNotNull(String value){
        if(value == null || value.equals("")){
            return "0.00";
        }

        return value;
    }

}