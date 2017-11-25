package com.capri4physio.activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.capri4physio.R;
import com.capri4physio.Services.ChatService;
import com.capri4physio.Services.LogoutService;
import com.capri4physio.fragment.ClinicDetailsFragment;
import com.capri4physio.fragment.PatientDashboardFragment;
import com.capri4physio.fragment.PatientProfileFragment;
import com.capri4physio.fragment.assessment.DoFeedbackActivity;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.fragment.assessment.InfoApps;
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
import com.capri4physio.util.NotificationPublisher;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.capri4physio.util.Utils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientDashboardActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentListener<Bundle>, HttpUrlListener {
    public static final int BOOK_APPOINTMENT = 1;
    private GoogleApiClient client;
    private UserDetailModel mUserModel;

    private List<ClinicAttribute> mListClinics;
    ImageView imageView;
    ProgressDialog pDialog;
    InfoApps infoApps;
    public static ArrayList<InfoApps> arrayList;
    NavigationView navigationView;

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

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initView(navigationView.getHeaderView(0));

        try {
            String userDetails = AppPreferences.getInstance(getApplicationContext()).getUserDetails();
            Gson gson = new Gson();
            mUserModel = gson.fromJson(userDetails, UserDetailModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        arrayList = new ArrayList<InfoApps>();

        loadPatientDashboard();
//        new CatagoryUrlAsynTask().execute();
        if (!AppPreferences.getInstance(getApplicationContext()).isChatSync()) {
            new ChatService(PatientDashboardActivity.this, AppPreferences.getInstance(getApplicationContext()).getUserID()).execute(ApiConfig.getallchat);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
//        scheduleNotification(getNotification("10 sec delay"), 10000);
    }
    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher);
        return builder.build();
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        TextView txtName = (TextView) view.findViewById(R.id.txt_name);
        TextView txtEmail = (TextView) view.findViewById(R.id.txt_email);

        txtName.setText(AppPreferences.getInstance(this).getUserName());
        txtEmail.setText(AppPreferences.getInstance(this).getEmail());

        CircleImageView imageView = (CircleImageView) view.findViewById(R.id.imageView);
//            Log.d(TagUtils.getTag(),"profilepic:-"+ApiConfig.PROFILE_PIC_BASE_URL+AppPreferences.getInstance(this).getPropic());
        Log.d(TagUtils.getTag(), "profilepic:-" + AppPreferences.getInstance(this).getPropic());
        Glide.with(getApplicationContext()).load(AppPreferences.getInstance(this).getPropic())
                .placeholder(R.drawable.ic_action_person)
                .error(R.drawable.ic_action_person)
                .dontAnimate()
                .into(imageView);
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

    private void loadPatientDashboard() {
        PatientDashboardFragment fragment = PatientDashboardFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private class ViewAllClinic extends AsyncTask<String, String, String> {
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
                Log.e("DoInBackGroundtr", String.valueOf(s));
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray jsonArray = new JSONArray(s);
                Log.e("Post Method", jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String lat = jsonObject2.getString("lat");
                    String lng = jsonObject2.getString("lng");
                    infoApps = new InfoApps();
                    infoApps.setLat(Double.valueOf(lat));
                    infoApps.setLng(Double.valueOf(lng));//date
                    arrayList.add(infoApps);
                    Log.e("arrayList", arrayList.toString());
                    loadMapFragment();

                }

            } catch (Exception e) {
                Log.e("error", e.toString());

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

    private void loadMapFragment() {
        Log.e("arrayList", "arrayList");
//        MapFragmentView mMapFragment = MapFragmentView.newInstance();
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_container, mMapFragment);
//        ft.addToBackStack(null);
//        ft.commit();

//        mMapFragment.getMapAsync(this);
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


//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        googleMap.setMyLocationEnabled(true);
//        Log.e("arrayListMapReady","arrayList");
//        final LatLngBounds.Builder builder = new LatLngBounds.Builder();
//
//        for (int i = 0; i < arrayList.size(); i++) {
//            final LatLng position = new LatLng(arrayList.get(i).getLat(), PatientDashboardActivity.arrayList.get(i).getLng());
//            final MarkerOptions options = new MarkerOptions().position(position);
//
//            googleMap.addMarker(options);
//
//            builder.include(position);
//            googleMap.animateCamera(CameraUpdateFactory.newLatLng(position));
//        }
////        final HashMap<String, ClinicAttribute> mPOI = new HashMap<String, ClinicAttribute>();
////        if (mListClinics != null && mListClinics.size() > 0) {
////
////            ClinicAttribute clinicMove = mListClinics.get(0);
////            String latValue = valueNotNull(clinicMove.getLat());
////            String lngValue = valueNotNull(clinicMove.getLng());
//////        String latValue = valueNotNull("28.630516");
//////        String lngValue = valueNotNull("77.276175");
////            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(latValue), Double.valueOf(lngValue)), 11));
////            MarkerOptions markerOptions;
////
//////            for (ClinicAttribute clinic : mListClinics) {
////
//////                latValue = valueNotNull(clinic.getLat());
//////                lngValue = valueNotNull(clinic.getLng());
////            markerOptions = new MarkerOptions()
////                    .title("clinic.getClinicName()")
////                    .snippet("clinic.getShortDescription()")
////                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
////                    .position(new LatLng(Double.valueOf(latValue), Double.valueOf(lngValue)))
////                    .anchor(0.0f, 1.0f);
////
////            Marker marker = googleMap.addMarker(markerOptions);
////        }
////                mPOI.put(marker.getId(), clinic);
////    }
////}
//
//
//        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//
//            @Override
//            public View getInfoWindow(Marker arg0) {
//
//                return null;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                View myContentsView = getLayoutInflater().inflate(R.layout.map_marker_info, null);
//                TextView txtTitle = ((TextView)myContentsView.findViewById(R.id.txt_title));
//                txtTitle.setText(marker.getTitle());
//                TextView txtSnippet = ((TextView)myContentsView.findViewById(R.id.txt_snippet));
//                txtSnippet.setText(marker.getSnippet());
//                return myContentsView;
//            }
//        });
//
//        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
////                ClinicAttribute clinic = mPOI.get(marker.getId());
////                loadClinicDetails(clinic);
//                //Toast.makeText(PatientDashboardActivity.this,"Marker Id - "+clinic.getId()+" Title - "+clinic.getClinicName(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }

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
        MenuItem call_menu = menu.findItem(R.id.action_alert);
        call_menu.setIcon(R.drawable.ic_call_white_24px);
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
            showCallDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showCallDialog() {
        final Dialog dialog1 = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_call);
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btn_cancel = (Button) dialog1.findViewById(R.id.btn_cancel);
        Button btn_call = (Button) dialog1.findViewById(R.id.btn_call);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "9873738969"));
                    if (ActivityCompat.checkSelfPermission(PatientDashboardActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastClass.showShortToast(getApplicationContext(), "Failed to place call");
                }
            }
        });
    }


    @Override
    public void onFragmentResult(Bundle bundle, int Id) {
        new ViewAllClinic().execute();
//        ClinicsApiCall();
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
    private void getfeedbackActivity() {
        Log.e("check", "check");
        Intent notifctionintent = new Intent(PatientDashboardActivity.this, DoFeedbackActivity.class);
        startActivity(notifctionintent);
        PatientDashboardActivity.this.finish();
    }

    private void logout() {

        AlertDialog.Builder builder = new AlertDialog.Builder(PatientDashboardActivity.this);
        builder.setMessage("Are you sure, you want to logout");
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
//                AppPreferences.getInstance(PatientDashboardActivity.this).setUserLogin(false);
//                dialog.dismiss();
//                Intent intent = new Intent(PatientDashboardActivity.this, SplashActivity.class);
//                intent.putExtra(BundleConst.IS_LOGOUT, true);
//                startActivity(intent);
//                PatientDashboardActivity.this.finish();
                new LogoutService(PatientDashboardActivity.this, AppPreferences.getInstance(getApplicationContext()).getUserID()).execute(ApiConfig.logout_users);

            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.create();
        builder.show();
    }

    private String valueNotNull(String value) {
        if (value == null || value.equals("")) {
            return "0.00";
        }
        return value;
    }

}