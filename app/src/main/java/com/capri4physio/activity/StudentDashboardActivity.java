package com.capri4physio.activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.capri4physio.Services.ChatService;
import com.capri4physio.Services.LogoutService;
import com.capri4physio.fragment.ClinicDetailsFragment;
import com.capri4physio.fragment.PatientProfileFragment;
import com.capri4physio.fragment.StaffProfileFragment;
import com.capri4physio.fragment.StudentDashboardFragment;
import com.capri4physio.fragment.assessment.DoFeedbackActivity;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.fragment.assessment.PaymentStatusFragmentView;
import com.capri4physio.fragment.assessment.StudentUploadFragmentView;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentDashboardActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, FragmentListener<Bundle>, HttpUrlListener {
    public static final int BOOK_APPOINTMENT = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private UserDetailModel mUserModel;

    private List<ClinicAttribute> mListClinics;
    ImageView imageView;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainfrstude);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadStudentDashboard();
        if(!AppPreferences.getInstance(getApplicationContext()).isChatSync()){
            new ChatService(StudentDashboardActivity.this,AppPreferences.getInstance(getApplicationContext()).getUserID()).execute(ApiConfig.getallchat);
        }

    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        TextView txtName = (TextView) view.findViewById(R.id.txt_name);
        TextView txtEmail = (TextView) view.findViewById(R.id.txt_email);
        imageView = (CircleImageView) view.findViewById(R.id.imageView);
        txtName.setText(AppPreferences.getInstance(this).getUserName());
        txtEmail.setText(AppPreferences.getInstance(this).getEmail());

        try {

            String userDetails = AppPreferences.getInstance(this).getUserDetails();
            String bitmap = AppPreferences.getInstance(this).getPropic();

            Log.e("stringToBitmap", bitmap.toString());
//            if (bitmap.length() < 2){
//                ImageLoader.getInstance().displayImage(bitmap, imageView);
//            }
//            else {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_person));
//                ImageLoader.getInstance().displayImage(bitmap, imageView);
//            }
//            Picasso.with(getActivity()).load(bitmap).into(mImgProfile);
        } catch (Exception e) {

        }
    }

    /**
     * Profile ImageView click method to view profile details
     *
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

    private void loadStudentDashboard() {
        StudentDashboardFragment fragment = StudentDashboardFragment.newInstance();
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

    private class CatagoryUrlAsynTask extends AsyncTask<String, String, String> {
        String id, catagoryName;

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
                Log.i("DoInBackGroundtr", String.valueOf(s));
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
                    String dob = jsonObject2.getString("dob");
                    String lat = jsonObject2.getString("lat");
                    String lng = jsonObject2.getString("lng");
                    String age = jsonObject2.getString("age");
                    Log.e("usertype", smobile.toString());
                        /*Detailapp1 = new InfoApps();
                        Detailapp1.setName(username);*/
                        /*Detailapp.setAppname(trnsamount);
                        Detailapp.setDataAdd(trnsamounttype);*/
                        /*Detailapp.setName(username);
                        contactDetails.add(Detailapp);
                        contactAdapter = new LocationAdapter4(getApplicationContext(), R.layout.contactlisadap1);
                        contactList.setAdapter(contactAdapter);
                        Toast.makeText(getApplicationContext(),contactDetails.toString(),Toast.LENGTH_LONG).show();*/
//                        Toast.makeText(getApplicationContext(),userid,Toast.LENGTH_LONG).show();
//                        int value = jsonObject1.getInt(i + 1);
                    /*contactAdapter = new LocationAdapter4(getApplicationContext(), R.layout.contactlisadap1);
                    contactList.setAdapter(contactAdapter);*/
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
            //  CatagoryMetod();
        }
    }

    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setMyLocationEnabled(true);
Log.d("onMapReady","OnReady");
        String latValue = valueNotNull("28.630516");
        String lngValue = valueNotNull("77.276175");
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(latValue), Double.valueOf(lngValue)), 11));
        MarkerOptions markerOptions;

//        for (ClinicAttribute clinic : mListClinics) {
//
//            latValue = valueNotNull(clinic.getLat());
//            lngValue = valueNotNull(clinic.getLng());
            markerOptions = new MarkerOptions()
                    .title("getClinicName")
                    .snippet("getShortDescription")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .position(new LatLng(Double.valueOf(latValue), Double.valueOf(lngValue)))
                    .anchor(0.0f, 1.0f);

            Marker marker = googleMap.addMarker(markerOptions);
//        final HashMap <String, ClinicAttribute> mPOI = new HashMap<String, ClinicAttribute>();
//        if (mListClinics != null && mListClinics.size() > 0) {
//
//            ClinicAttribute clinicMove = mListClinics.get(0);
//            String latValue = valueNotNull(clinicMove.getLat());
//            String lngValue = valueNotNull(clinicMove.getLng());
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(latValue), Double.valueOf(lngValue)), 11));
//            MarkerOptions markerOptions;
//
//            for (ClinicAttribute clinic : mListClinics) {
//
//                latValue = valueNotNull(clinic.getLat());
//                lngValue = valueNotNull(clinic.getLng());
//                markerOptions = new MarkerOptions()
//                        .title(clinic.getClinicName())
//                        .snippet(clinic.getShortDescription())
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
//                        .position(new LatLng(Double.valueOf(latValue), Double.valueOf(lngValue)))
//                        .anchor(0.0f, 1.0f);
//
//                Marker marker= googleMap.addMarker(markerOptions);
//
//                mPOI.put(marker.getId(), clinic);
//            }
//
//        }

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
//                ClinicAttribute clinic = mPOI.get(marker.getId());
//                loadClinicDetails(clinic);
                //Toast.makeText(PatientDashboardActivity.this,"Marker Id - "+clinic.getId()+" Title - "+clinic.getClinicName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onPostSuccess(Object response, int id) {

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
        new CatagoryUrlAsynTask().execute();
        ClinicsApiCall();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            StaffProfileFragment fragment = StaffProfileFragment.newInstance();
            ft.replace(R.id.fragment_container, fragment);
            ft.addToBackStack(null);
            ft.commit();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            PaymentStatusFragmentView reportfragment = PaymentStatusFragmentView.newInstance();
//                ViewAppoinmentsPatientFragment reportfragment = ViewAppoinmentsPatientFragment.newInstance();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, reportfragment);
//                getSupportActionBar().setTitle(title);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_slideshow) {
            getfeedbackActivity();
        } else if (id == R.id.nav_logout) {
            logout();
        }else if (id == R.id.Upload) {
            StudentUploadFragmentView reportfragment = StudentUploadFragmentView.newInstance();
//                ViewAppoinmentsPatientFragment reportfragment = ViewAppoinmentsPatientFragment.newInstance();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, reportfragment);
//                getSupportActionBar().setTitle(title);
            ft.addToBackStack(null);
            ft.commit();
//            openGallery();
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
        Intent notifctionintent=new Intent(StudentDashboardActivity.this,DoFeedbackActivity.class);
        startActivity(notifctionintent);
        StudentDashboardActivity.this.finish();
    }
    private void logout() {

        AlertDialog.Builder builder = new AlertDialog.Builder(StudentDashboardActivity.this);
        builder.setMessage("Are you sure, you want to logout");
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                AppPreferences.getInstance(StudentDashboardActivity.this).setUserLogin(false);
                dialog.dismiss();
//                Intent intent = new Intent(StudentDashboardActivity.this, SplashActivity.class);
//                intent.putExtra(BundleConst.IS_LOGOUT, true);
//                startActivity(intent);
//                StudentDashboardActivity.this.finish();
                new LogoutService(StudentDashboardActivity.this,AppPreferences.getInstance(getApplicationContext()).getUserID()).execute(ApiConfig.logout_users);

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