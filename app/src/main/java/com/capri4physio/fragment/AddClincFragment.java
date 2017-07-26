package com.capri4physio.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.activity.CropActivity;
import com.capri4physio.activity.SplashActivity;
import com.capri4physio.fragment.assessment.GPSTracker;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.Utils;
import com.capri4physio.view.CircleImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddClincFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author prabhunathy
 * @version 1.0
 */
public class AddClincFragment extends BaseFragment {

    private EditText mEdtxtClinicName;
    private EditText mEdtxtLocation;
    private EditText mEdtxtLat;
    String countable="fa";
    GPSTracker gpsTracker;
    private EditText mEdtxtLng;
    private EditText mEdtxtShortDesc;
    private EditText mEdtxtDesc;
    private CircleImageView mImgClinic;

    private Button mBtnAddClinic;
    private ProgressDialog pDialog;
    private HttpUrlListener mListener;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;
    private static final int PIC_CROP = 3;
    private Bitmap bitmapImage;
    byte[] byteArray;
    private String mImgBase64;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddClincFragment.
     */
    public static AddClincFragment newInstance() {
        AddClincFragment fragment = new AddClincFragment();
        return fragment;
    }

    public AddClincFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof HttpUrlListener) {
            mListener = (HttpUrlListener) activity;
        } else {
            throw new RuntimeException(activity.toString() + " must implement HttpUrlListener");
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_clinic, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mEdtxtClinicName = (EditText) view.findViewById(R.id.edtxt_name);
        mEdtxtLocation = (EditText) view.findViewById(R.id.edtxt_location);
        mEdtxtLat = (EditText) view.findViewById(R.id.edtxt_lattitude);
        mEdtxtLng = (EditText) view.findViewById(R.id.edtxt_longitude);
        mEdtxtShortDesc = (EditText) view.findViewById(R.id.edtxt_short_desc);
        mEdtxtDesc = (EditText) view.findViewById(R.id.edtxt_desc);
        mImgClinic = (CircleImageView) view.findViewById(R.id.img_clinic);
        mBtnAddClinic = (Button) view.findViewById(R.id.btn_submit);
    }

    @Override
    protected void setListener() {
        super.setListener();

        mImgClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOption();
            }
        });
        mImgClinic.setVisibility(View.INVISIBLE);
        mEdtxtClinicName.setText(AppPreferences.getInstance(getActivity()).getFirstName());
        mEdtxtLocation.setText(AppPreferences.getInstance(getActivity()).getaddress());
//        mEdtxtLocation.setText("");

        mBtnAddClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initProgressDialog("Please wait..");
                registerApiCall();
            }
        });
        mEdtxtLat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                }
                else {
                    android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    dialog.setMessage("IS that your current Location for your clinic");
                    dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            gpsTracker = new GPSTracker(getActivity());
////
////        // check if GPS enabled
                            if (gpsTracker.canGetLocation()) {

                                double latitude = gpsTracker.getLatitude();
                                double longitude = gpsTracker.getLongitude();
                                mEdtxtLat.setText(latitude + "");
                                mEdtxtLng.setText(longitude + "");
//                                mEdtxtLat.setFocusable(false);
//                                mEdtxtLng.setFocusable(false);
//                    latlng = new LatLng(latitude, longitude);
                            } else {
                                showSettingsAlert();
//			show
                            }
                        }
                    });
                    dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        }
                    });
                    dialog.show();
                }
                }
        });
        Log.d("name",AppPreferences.getInstance(getActivity()).getFirstName());
        Log.d("branch_Code",AppPreferences.getInstance(getActivity()).getUSER_BRANCH_CODE());
        Log.d("ID",AppPreferences.getInstance(getActivity()).getUserID());
        Log.d("passwords",AppPreferences.getInstance(getActivity()).getPassword());
        Log.d("Email",AppPreferences.getInstance(getActivity()).getEmail());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (countable.equals("true")){
            Log.e("focus","true");
mEdtxtLat.requestFocus();
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                getActivity());
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        getActivity().startActivity(intent);
                        countable="true";
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        getActivity().finish();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != SplashActivity.RESULT_OK)
            return;

        Intent intent = null;

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                intent = new Intent(getActivity(), CropActivity.class);
                intent.putExtra("url", SplashActivity.mImageCaptureUri.getPath());
                startActivityForResult(intent, PIC_CROP);

                break;
            case PICK_FROM_FILE:
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                File mFile = new File(picturePath);
                InputStream inStream;
                try {
                    inStream = new FileInputStream(mFile);
                    @SuppressWarnings("resource")
                    OutputStream mOutputStream = new FileOutputStream(new File(SplashActivity.mImageCaptureUri.getPath()));
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inStream.read(buffer)) > 0) {
                        mOutputStream.write(buffer, 0, length);
                    }

                    inStream.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                intent = new Intent(getActivity(), CropActivity.class);
                intent.putExtra("url", picturePath);
                startActivityForResult(intent, PIC_CROP);

                break;

            case PIC_CROP:
                bitmapImage = CropActivity.croppedImage;
                mImgClinic.setImageBitmap(bitmapImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 0, stream);
                byteArray = stream.toByteArray();
                mImgBase64 = Base64.encodeToString(byteArray, 0);
                AppLog.e("Capri4Physio", "Base64 IMG - " + mImgBase64);

                break;
            default:
                break;

        }
    }

    private void dialogOption() {
        final String[] items = new String[]{"Take from camera", "Select from gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    dispatchTakePictureIntent();
                } else {
                    openGallery();
                }
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }


    /**
     * @return none
     * @description Login web service API calling
     */
    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }
    private void registerApiCall() {
        if (!isValid())
            return;


        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://caprispine.in/users/updateclinic",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    try{
                                        pDialog.hide();
                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    AppPreferences.getInstance(getActivity()).setClinicCount(1);
                                    getFragmentManager().popBackStack();
                                    StaffDashboardFragment fragment = StaffDashboardFragment.newInstance();
                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.add(R.id.fragment_container, fragment);
                                    ft.addToBackStack(null);
                                    ft.commit();
                                    Log.e("response", "" + response);
//                    pDialog.hide();

                                    Log.e("response", "" + response);
                                } catch (Exception e) {
                                    Log.e("error", e.toString());

                                }
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                                Log.w("Postdat", "" + error);
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("first_name", mEdtxtClinicName.getText().toString());
                        params.put("last_name", "");
                        params.put("email", AppPreferences.getInstance(getActivity()).getEmail());
                        params.put("password", AppPreferences.getInstance(getActivity()).getPassword());
                        params.put("address", mEdtxtLocation.getText().toString());
                        params.put("lat", mEdtxtLat.getText().toString());
                        params.put("lng", mEdtxtLng.getText().toString());
                        params.put("user_id", AppPreferences.getInstance(getActivity()).getUserID());
                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);

        } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }
    /*private void registerApiCall() {
        if (!isValid())
            return;


        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put("id", AppPreferences.getInstance(getActivity()).getUserID());
                params.put("last_name", mEdtxtClinicName.getText().toString().trim());
                params.put("first_name", mEdtxtClinicName.getText().toString().trim());
                params.put(ApiConfig.CLINIC_SHORT_DESC, mEdtxtShortDesc.getText().toString().trim());
                params.put(ApiConfig.CLINIC_DESC, mEdtxtDesc.getText().toString().trim());
                params.put(ApiConfig.LATITUDE, mEdtxtLat.getText().toString().trim());
                params.put(ApiConfig.LONGITUDE, mEdtxtLng.getText().toString().trim());
                params.put(ApiConfig.CLINIC_LOCATION, mEdtxtLocation.getText().toString().trim());

                if (mImgBase64 != null && !mImgBase64.equals("")) {
                    params.put("profile_pic", mImgBase64);
                } else {
                    params.put("profile_pic", "");
                }

                new UrlConnectionTask(getActivity(), ApiConfig.
                        CLINIC_MANAGE_URL, ApiConfig.ID1, true, params, BaseModel.class, mListener).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
*/
/*}*/


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                SplashActivity.mImageCaptureUri = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, SplashActivity.mImageCaptureUri);
                //takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                takePictureIntent.putExtra("return-data", true);
                startActivityForResult(takePictureIntent, PICK_FROM_CAMERA);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String imageFileName = "img" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory(), "CheckInMD");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        return image;
    }


    private void openGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (photoFile != null) {
            SplashActivity.mImageCaptureUri = Uri.fromFile(photoFile);
            try {
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * Validation to check user inputs
     *
     * @return none
     */
    private boolean isValid() {

        String cName = mEdtxtClinicName.getText().toString().trim();
        String location = mEdtxtLocation.getText().toString().trim();
        String lat = mEdtxtLat.getText().toString().trim();
        String lng = mEdtxtLng.getText().toString().trim();
        String shortDesc = mEdtxtShortDesc.getText().toString().trim();

        if (cName.isEmpty()) {
            mEdtxtClinicName.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_clinic_name));
            return false;
        }

        if (location.isEmpty()) {
            mEdtxtLocation.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_location));
            return false;
        }

        if (lat.isEmpty()) {
            mEdtxtLat.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error),
                    getResources().getString(R.string.err_lat));
            return false;
        }if (lng.isEmpty()) {
            mEdtxtLng.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error),
                    getResources().getString(R.string.err_lng));
            return false;
        }
//        if (shortDesc.isEmpty()) {
//            mEdtxtShortDesc.requestFocus();
//            Utils.showError(getActivity(), getResources().getString(R.string.error),
//                    getResources().getString(R.string.err_short_desc));
//            return false;
//        }


        return true;
    }


}