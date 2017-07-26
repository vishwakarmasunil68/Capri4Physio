package com.capri4physio.activity;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.capri4physio.R;
import com.capri4physio.fragment.LoginFragment;
import com.capri4physio.fragment.OtpFragment;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.model.UserDetailModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.BundleConst;
import com.capri4physio.util.Constants;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link android.support.v7.app.AppCompatActivity} subclass.
 * Used to handle app loading and user authentication
 *
 * @author prabhunathy
 * @version 1.0
 * @see android.support.v7.app.AppCompatActivity
 * @since 18-05-2016
 */
public class SplashActivity extends BaseActivity implements HttpUrlListener {

    public static Uri mImageCaptureUri;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.hide();
        if (checkAndRequestPermissions()) {
            makeLogin();
        } else {
//            startActivity(new Intent(SplashActivity.this, SplashActivity.class));
//            finish();
        }

    }

    private boolean checkAndRequestPermissions() {
        int read_storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int write_storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int ACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int CAMERA = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();


        if (read_storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (write_storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ACCESS_FINE_LOCATION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (CAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("msg", "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);

                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            ) {
                        Log.d("msg", "All Permissions granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted

                        makeLogin();


                    } else {
                        Log.d("msg", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)
                                ) {
                            showDialogOK("Permisions required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                }
            }
        }

    }

    public void makeLogin() {
        AppPreferences.getInstance(getApplicationContext()).setDeviceToken(AppPreferences.GetDeviceToken(getApplicationContext()));
        if (getIntent().getBooleanExtra(BundleConst.IS_LOGOUT, false)) {
            loadScreen();
        } else {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    loadScreen();
                }

            }, 2000);
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadScreen() {

        if (AppPreferences.getInstance(this).isUserLogin()) {

            String userType = AppPreferences.getInstance(SplashActivity.this).getUserType();
            Log.d(TagUtils.getTag(),"device token:-"+AppPreferences.GetDeviceToken(getApplicationContext()));
            Log.d(TagUtils.getTag(),"user login type:-"+userType);
            if (userType != null && userType.equals(Constants.GlobalConst.USER_PATIENT)) {
                Log.d("sunil", "userpatient");
                AppPreferences.getInstance(SplashActivity.this).setUserLogin(true);
                Intent intent = new Intent(SplashActivity.this, PatientDashboardActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();

            } else if (userType != null && userType.equals(Constants.GlobalConst.USER_BRANCH_MANAGER)) {
                Log.d("sunil", "userbranchmanager");
                AppPreferences.getInstance(SplashActivity.this).setUserLogin(true);
                Intent intent = new Intent(SplashActivity.this, BranchAdminActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();

            } else if (userType != null && userType.equals(Constants.GlobalConst.USER_AMIN)) {
                Log.d("sunil", "useradmin");
                AppPreferences.getInstance(SplashActivity.this).setUserLogin(true);
                Intent intent = new Intent(SplashActivity.this, BranchAdminActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();

            } else {
                if (userType != null && (userType.equals(Constants.GlobalConst.USER_STAFF)||userType.equals(Constants.GlobalConst.USER_THERAPIST))) {
                    Log.d("sunil", "userstaff");
                    AppPreferences.getInstance(SplashActivity.this).setUserLogin(true);
                    Intent intent = new Intent(SplashActivity.this, BranchAdminActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();

                } else {
                    AppPreferences.getInstance(SplashActivity.this).setUserLogin(true);
                    Intent intent = new Intent(SplashActivity.this, StudentDashboardActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }

            }

        } else {

            LoginFragment fragment = LoginFragment.newInstance();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mImageCaptureUri != null) {
            outState.putString("cameraImageUri", mImageCaptureUri.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("cameraImageUri")) {
            mImageCaptureUri = Uri.parse(savedInstanceState.getString("cameraImageUri"));
        }
    }

    @Override
    protected void setListener(View view) {
        super.setListener(view);


    }

    @Override
    public void onPostSuccess(Object response, int id) {
        Log.d("sunil", "resposse splash:-" + response.toString());
        Log.d("sunil", "id splash:-" + id);


        switch (id) {
            case ApiConfig.ID1:
                UserDetailModel loginModel = (UserDetailModel) response;
                Log.d("sunil", "loginmodel:-" + loginModel.toString());
                if (loginModel.getStatus() == 1) {
//                    Log.e("responsestat",response.toString());
                    AppLog.i("Capri4Physio", "Login Response : " + loginModel.getMessage());
                    // Save login status
                    AppPreferences.getInstance(SplashActivity.this).setUserLogin(true);
                    AppPreferences.getInstance(SplashActivity.this).setUserID(loginModel.getResult().getUser().getId());
                    String fName = loginModel.getResult().getUser().getFirstName();
                    AppPreferences.getInstance(SplashActivity.this).setUSER_BRANCH_CODE(loginModel.getResult().getUser().getBracch_code());
                    if (!(fName == null || fName.equalsIgnoreCase("null") || fName.equals(null)))
                        AppPreferences.getInstance(SplashActivity.this).setUserName(loginModel.getResult().getUser().getFirstName() + " " + loginModel.getResult().getUser().getLastName());
                    AppPreferences.getInstance(SplashActivity.this).setEmail(loginModel.getResult().getUser().getEmail());
                    AppPreferences.getInstance(SplashActivity.this).setPropic(loginModel.getResult().getUser().getProfilePic());
                    AppPreferences.getInstance(SplashActivity.this).setMarital(loginModel.getResult().getUser().getCity());
                    AppPreferences.getInstance(SplashActivity.this).setWeight(loginModel.getResult().getUser().getAddress2());
                    AppPreferences.getInstance(SplashActivity.this).setMobile(loginModel.getResult().getUser().getMobile());
                    AppPreferences.getInstance(SplashActivity.this).setPatientCode(loginModel.getResult().getUser().getPatient_code());
                    AppPreferences.getInstance(SplashActivity.this).setDate(loginModel.getResult().getUser().getCreated());
                    AppPreferences.getInstance(SplashActivity.this).setClinicCount(loginModel.getResult().getUser().getCliniccount());
                    AppPreferences.getInstance(SplashActivity.this).setClinicId(loginModel.getResult().getUser().getClinicId());
                    AppPreferences.getInstance(SplashActivity.this).setADminSTAtus(loginModel.getResult().getUser().getAdmin_permisstion_status());
                    AppPreferences.getInstance(SplashActivity.this).setPassword(loginModel.getResult().getUser().getShowPassword());
                    AppPreferences.getInstance(SplashActivity.this).setFirstName(loginModel.getResult().getUser().getFirstName());
                    AppPreferences.getInstance(SplashActivity.this).setaddress(loginModel.getResult().getUser().getAddress2());
// AppPreferences.getInstance(SplashActivity.this).setPassword(loginModel.getResult().getUser().getAdd());

                    try {
//                        //Save profile data
//                        Gson gson = new Gson();
//                        String userDetails = gson.toJson(loginModel);
//                        AppPreferences.getInstance(SplashActivity.this).setUserDetails(userDetails);
                        //Navigate to dashboard
                        String userType = loginModel.getResult().getUser().getUserType();
                        AppPreferences.getInstance(SplashActivity.this).setUserType(userType);
                        if (userType != null && userType.equals(Constants.GlobalConst.USER_PATIENT)) {
                            Log.d("sunil", "userpatient");
                            AppPreferences.getInstance(SplashActivity.this).setUserLogin(true);
                            Intent intent = new Intent(SplashActivity.this, PatientDashboardActivity.class);
                            startActivity(intent);
                            SplashActivity.this.finish();

                        } else if (userType != null && userType.equals(Constants.GlobalConst.USER_BRANCH_MANAGER)) {
                            Log.d("sunil", "userbranchmanager");
                            AppPreferences.getInstance(SplashActivity.this).setUserLogin(true);
                            Intent intent = new Intent(SplashActivity.this, BranchAdminActivity.class);
                            startActivity(intent);
                            SplashActivity.this.finish();

                        } else if (userType != null && userType.equals(Constants.GlobalConst.USER_AMIN)) {
                            Log.d("sunil", "useradmin");
                            AppPreferences.getInstance(SplashActivity.this).setUserLogin(true);
                            Intent intent = new Intent(SplashActivity.this, BranchAdminActivity.class);
                            startActivity(intent);
                            SplashActivity.this.finish();

                        } else if (userType != null && userType.equals(Constants.GlobalConst.USER_STUDENT)) {
                            AppPreferences.getInstance(SplashActivity.this).setUserLogin(true);
                            Intent intent = new Intent(SplashActivity.this, StudentDashboardActivity.class);
                            startActivity(intent);
                            SplashActivity.this.finish();
                        } else {
                            if (userType != null && (userType.equals(Constants.GlobalConst.USER_STAFF)||userType.equals(Constants.GlobalConst.USER_THERAPIST))) {
                                Log.d("sunil", "userstaff");
                                AppPreferences.getInstance(SplashActivity.this).setUserLogin(true);
                                Intent intent = new Intent(SplashActivity.this, BranchAdminActivity.class);
                                startActivity(intent);
                                SplashActivity.this.finish();

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Utils.showMessage(this, loginModel.getMessage());
                }

                break;

            case ApiConfig.ID2:
                UserDetailModel registerModel = (UserDetailModel) response;
                if (registerModel.getStatus() == 1) {

                    AppLog.i("Capri4Physio", "Register Response : " + registerModel.getMessage());
                    // Save login status
                    AppPreferences.getInstance(SplashActivity.this).setUserID(registerModel.getResult().getUser().getId());
                    AppPreferences.getInstance(SplashActivity.this).setUserName(getString(R.string.user_name));
                    AppPreferences.getInstance(SplashActivity.this).setLastName(getString(R.string.user_name));
                    String fName = registerModel.getResult().getUser().getFirstName();
                    AppPreferences.getInstance(SplashActivity.this).setUSER_BRANCH_CODE(registerModel.getResult().getUser().getBracch_code());
                    if (!(fName == null || fName.equalsIgnoreCase("null") || fName.equals(null)))
                        AppPreferences.getInstance(SplashActivity.this).setUserName(registerModel.getResult().getUser().getFirstName() + " " + registerModel.getResult().getUser().getLastName());
                    AppPreferences.getInstance(SplashActivity.this).setEmail(registerModel.getResult().getUser().getEmail());
                    AppPreferences.getInstance(SplashActivity.this).setPropic(registerModel.getResult().getUser().getProfilePic());
                    AppPreferences.getInstance(SplashActivity.this).setMobile(registerModel.getResult().getUser().getMobile());
                    AppPreferences.getInstance(SplashActivity.this).setClinicCount(registerModel.getResult().getUser().getCliniccount());
                    AppPreferences.getInstance(SplashActivity.this).setClinicId(registerModel.getResult().getUser().getClinicId());
//                    AppPreferences.getInstance(SplashActivity.this).set(registerModel.getResult().getUser().getClinicId());
                    String userType = registerModel.getResult().getUser().getUserType();
                    AppPreferences.getInstance(SplashActivity.this).setUserType(userType);
                    AppPreferences.getInstance(SplashActivity.this).setFirstName(registerModel.getResult().getUser().getFirstName());
                    AppPreferences.getInstance(SplashActivity.this).setaddress(registerModel.getResult().getUser().getAddress2());
                    try {
//                        //Save profile data
//                        Gson gson = new Gson();
//                        String userDetails = gson.toJson(registerModel);
//                        AppPreferences.getInstance(SplashActivity.this).setUserDetails(userDetails);

                        OtpFragment fragment = OtpFragment.newInstance(registerModel.getOtp(), registerModel.getResult().getUser().getUserType());
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.add(R.id.fragment_container, fragment);
                        ft.addToBackStack(null);
                        ft.commit();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Utils.showMessage(this, registerModel.getMessage());
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

    @Override
    public void onPostError(String errMsg, int id) {

    }


    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }

}