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
    import android.widget.TextView;
    import android.widget.Toast;

    import com.capri4physio.R;
    import com.capri4physio.Services.ChatService;
    import com.capri4physio.Services.LogoutService;
    import com.capri4physio.fragment.AddClincFragment;
    import com.capri4physio.fragment.MyClinicFragment;
    import com.capri4physio.fragment.NewStaffFragment;
    import com.capri4physio.fragment.StaffDashboardFragment;
    import com.capri4physio.fragment.StaffProfileFragment;
    import com.capri4physio.fragment.assessment.DoFeedbackActivity;
    import com.capri4physio.listener.FragmentListener;
    import com.capri4physio.listener.HttpUrlListener;
    import com.capri4physio.model.BaseModel;
    import com.capri4physio.model.ClinicsModel;
    import com.capri4physio.net.ApiConfig;
    import com.capri4physio.task.UrlConnectionTask;
    import com.capri4physio.util.AppLog;
    import com.capri4physio.util.AppPreferences;
    import com.capri4physio.util.TagUtils;
    import com.capri4physio.util.Utils;

    import org.json.JSONObject;


public class BranchAdminActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentListener<Bundle>, HttpUrlListener {
    public static final int BOOK_APPOINTMENT = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private int clinicCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_staff);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initView(navigationView.getHeaderView(0));


        clinicCount = AppPreferences.getInstance(BranchAdminActivity.this).getClinicCount();
//        addNewClinic();
        if (clinicCount == 0) {
//            addNewClinic();
            loadStaffDashboard();
        } else {
            loadStaffDashboard();
        }
        Log.d("sunil",AppPreferences.getInstance(getApplicationContext()).getUserType());
        if(AppPreferences.getInstance(getApplicationContext()).getUserType().equals("0")
                ||AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")){

        }
        else{
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_slideshow).setVisible(false);
            String id =AppPreferences.getInstance(BranchAdminActivity.this).getUSER_BRANCH_CODE();
            Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
        }
        Log.d(TagUtils.getTag(),"chat sync:-"+AppPreferences.getInstance(getApplicationContext()).isChatSync());
        if(!AppPreferences.getInstance(getApplicationContext()).isChatSync()){
            new ChatService(BranchAdminActivity.this,AppPreferences.getInstance(getApplicationContext()).getUserID()).execute(ApiConfig.getallchat);
        }
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        TextView txtName = (TextView) view.findViewById(R.id.txt_name);
        TextView txtEmail = (TextView) view.findViewById(R.id.txt_email);

        txtName.setText(AppPreferences.getInstance(this).getUserName());
        txtEmail.setText(AppPreferences.getInstance(this).getEmail());
    }

    @Override
    protected void setListener(View view) {
        super.setListener(view);
    }

    /**
     * Profile ImageView click method to view profile details
     * @param view
     */
    public void viewProfile(View view) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        StaffProfileFragment fragment = StaffProfileFragment.newInstance();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }


    private void loadStaffDashboard() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
//            MenuItem menuItem1 = menu.findItem(R.id.action_new);
            getSupportActionBar().setTitle("Dashboard");
            StaffDashboardFragment fragment = StaffDashboardFragment.newInstance();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.addToBackStack(null);
            ft.commit();

        } else {
            getSupportActionBar().setTitle("Dashboard");
            StaffDashboardFragment fragment = StaffDashboardFragment.newInstance();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

    }

    private void addNewClinic() {
        AddClincFragment fragment = AddClincFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, fragment);
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
                params.put(ApiConfig.USER_ID, AppPreferences.getInstance(this).getUserID());
                new UrlConnectionTask(this, ApiConfig.CLINICS_URL, ApiConfig.ID1, true, params, ClinicsModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(this, getResources().getString(R.string.err_network));
        }
    }


    @Override
    public void onPostSuccess(Object response, int id) {

        switch (id) {
            case ApiConfig.ID1:
                BaseModel baseModel = (BaseModel) response;
                if (baseModel.getStatus() == 1) {
                    AppLog.i("Capri4Physio", "Clinic added response : " + baseModel.getMessage());
                    AppPreferences.getInstance(BranchAdminActivity.this).setClinicCount(1);
//                    AppPreferences.getInstance(BranchAdminActivity.this).setClinicId();

                    loadStaffDashboard();

                } else {
                    Utils.showMessage(this, baseModel.getMessage());
                }
                break;

            case ApiConfig.ID3:
                BaseModel baseResponse = (BaseModel) response;
                Utils.showMessage(this, baseResponse.getMessage());
                break;

            default:
                AppLog.i("Capri4Physio", "UNKNOW RESPONSE - " + id);

        }

    }

    @Override
    public void onPostError(String errMsg, int id) {

    }

    @Override
    public void onBackPressed() {
        /*if (getFragmentManager().getBackStackEntryCount() > 2) {
            Log.d("2backpres","2");
            getFragmentManager().popBackStack();
        } else {
            Log.d("2backpres","0");
            super.onBackPressed();
        }*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if (getFragmentManager().getBackStackEntryCount() == 1) {
                Log.d("finidh","fi");
                exit();
//                finish();
            } else {
                super.onBackPressed();
            }
        }

    }
    private void exit() {

        AlertDialog.Builder builder = new AlertDialog.Builder(BranchAdminActivity.this);
        builder.setMessage("Are you sure, you want to exit");
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                finish();

            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.create();
        builder.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_staff, menu);

        MenuItem menuItem1 = menu.findItem(R.id.action_new);
        menuItem1.setVisible(false);
        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("0")){
            menuItem1.setVisible(false);
        }
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
        if (id == R.id.action_new) {
            addNewStaff();
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

        if (id == R.id.nav_clinic) {
            MyClinics();
        } else if (id == R.id.nav_camera) {
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
    private void getfeedbackActivity(){
        Log.e("check","check");
        Intent notifctionintent=new Intent(BranchAdminActivity.this,DoFeedbackActivity.class);
        startActivity(notifctionintent);
    }
    private void addNewStaff() {
        NewStaffFragment fragment = NewStaffFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
//    getSupportFragmentManager().addOnBackStackChangedListener(getListener());
    private void MyClinics() {
        String clinicId = AppPreferences.getInstance(BranchAdminActivity.this).getClinicId();

        if (null != clinicId && !clinicId.equals("")) {
            MyClinicFragment fragment = MyClinicFragment.newInstance(String.valueOf(clinicId));
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    /**
     * Logout dialog to exit from the app
     *
     * @return none
     */
    private void logout() {

        AlertDialog.Builder builder = new AlertDialog.Builder(BranchAdminActivity.this);
        builder.setMessage("Are you sure, you want to logout");
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                AppPreferences.getInstance(BranchAdminActivity.this).clearAllData();
                dialog.dismiss();
//                Intent intent = new Intent(BranchAdminActivity.this, SplashActivity.class);
//                intent.putExtra(BundleConst.IS_LOGOUT, true);
//                startActivity(intent);
//                BranchAdminActivity.this.finish();
                new LogoutService(BranchAdminActivity.this,AppPreferences.getInstance(getApplicationContext()).getUserID()).execute(ApiConfig.logout_users);

            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.create();
        builder.show();
    }

}