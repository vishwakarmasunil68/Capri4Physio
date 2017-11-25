package com.capri4physio.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.capri4physio.R;
import com.capri4physio.Services.GetWebServicesFragment;
import com.capri4physio.Services.WebServiceBaseFragment;
import com.capri4physio.Services.WebServiceUploadFragment;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.activity.SplashActivity;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.UserDetailModel;
import com.capri4physio.model.branch.BranchPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionAuthTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.Constants;
import com.capri4physio.util.FileUtil;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.capri4physio.util.Utils;
import com.capri4physio.view.CircleImageView;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author prabhunathy
 * @version 1.0
 * @since 2016-03-31
 */
public class SignUpDetailFragment extends BaseFragment implements WebServicesCallBack {

    private static final String CALL_REGISTER_API = "call_register_api";
    private EditText mEdtxtFname;
    private EditText mEdtxtLname;
    private EditText mEdtxtEmail;
    private EditText mEdtxtPhone;
    private EditText mEdtxtPassword;
    private CircleImageView mImgProfile;

    private Button mBtnRegister;
    private HttpUrlListener mListener;
    private EditText et_tell_about;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;
    private static final int PIC_CROP = 3;
    private Bitmap bitmapImage;
    byte[] byteArray;
    private String mImgBase64;
    private String mUserType = "0";
    private static final String USER_TYPE = "user_type";
    Spinner spinner_branch,spinner_opd;
    private static final String GET_ALL_BRANCHES = "get_all_branches";

    public static SignUpDetailFragment newInstance(String userType) {
        SignUpDetailFragment fragment = new SignUpDetailFragment();
        Bundle args = new Bundle();
        args.putString(USER_TYPE, userType);
        fragment.setArguments(args);
        return fragment;
    }

    public SignUpDetailFragment() {
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

        if (getArguments() != null) {
            mUserType = getArguments().getString(USER_TYPE);
            AppLog.i("App", "mUserType  :" + mUserType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup_details, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((SplashActivity) getActivity()).getSupportActionBar().show();

    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mEdtxtFname = (EditText) view.findViewById(R.id.edtxt_fname);
        mEdtxtLname = (EditText) view.findViewById(R.id.edtxt_lname);
        mEdtxtEmail = (EditText) view.findViewById(R.id.edtxt_email);
        mEdtxtPhone = (EditText) view.findViewById(R.id.edtxt_mobile);
        mEdtxtPassword = (EditText) view.findViewById(R.id.edtxt_password);
        mImgProfile = (CircleImageView) view.findViewById(R.id.img_profile);
        mBtnRegister = (Button) view.findViewById(R.id.btn_submit);
        spinner_branch = (Spinner) view.findViewById(R.id.spinner_branch);
        spinner_opd = (Spinner) view.findViewById(R.id.spinner_opd);
        et_tell_about = (EditText) view.findViewById(R.id.et_tell_about);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new GetWebServicesFragment(getActivity(), this, GET_ALL_BRANCHES, false).execute(ApiConfig.GetURL);
    }

    @Override
    protected void setListener() {
        super.setListener();

        mImgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOption();
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callRegisterAPI();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            Log.d(TAG, photo.toString());

            File imgFile = new File(pictureImagePath);
            if (imgFile.exists()) {
                Bitmap bmp = BitmapFactory.decodeFile(pictureImagePath);
                bmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth() / 4, bmp.getHeight() / 4, false);
                String strMyImagePath = FileUtil.getChatDirPath();
                File file_name = new File(strMyImagePath + File.separator + System.currentTimeMillis() + ".png");
                FileOutputStream fos = null;

                try {
                    fos = new FileOutputStream(file_name);
                    Log.d(TagUtils.getTag(), "taking photos");
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                    setImage(file_name.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (null == data)
                    return;
                Uri selectedImageUri = data.getData();
                System.out.println(selectedImageUri.toString());
                // MEDIA GALLERY
                String selectedImagePath = getPath(
                        getActivity(), selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if (selectedImagePath != null && selectedImagePath != "") {
//                    image_path_string = selectedImagePath;
                    Log.d(TagUtils.getTag(), "selected path:-" + selectedImagePath);
                    setImage(selectedImagePath);
                } else {
                    Toast.makeText(getActivity(), "File Selected is corrupted", Toast.LENGTH_LONG).show();
                }
                System.out.println("Image Path =" + selectedImagePath);
            }
        }
    }

    public void setImage(String path) {
        Glide.with(getActivity().getApplicationContext()).load(path.toString()).into(mImgProfile);
        bitmapImage = BitmapFactory.decodeFile(path.toString());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byteArray = stream.toByteArray();
        mImgBase64 = Base64.encodeToString(byteArray, 0);
        AppLog.e("Capri4Physio", "Base64 IMG - " + mImgBase64);
        pictureImagePath = path;
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

    public void callRegisterAPI() {
        if (!isValid()) {
            return;
        }
        if (Utils.isNetworkAvailable(getActivity())) {
            if (pictureImagePath != null && !pictureImagePath.equals("")) {
                callUploadImageAPI();
            } else {
                callAPI();
            }
        } else {
            ToastClass.showShortToast(getActivity().getApplicationContext(), "No Internet Connection");
        }
    }

    public void callUploadImageAPI() {
        if (new File(pictureImagePath).exists()) {
            try {
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                reqEntity.addPart("added_by", new StringBody(branchPOJOList.get(spinner_branch.getSelectedItemPosition()).getBranch_code()));
                reqEntity.addPart("first_name", new StringBody(mEdtxtFname.getText().toString()));
                reqEntity.addPart("last_name", new StringBody(mEdtxtLname.getText().toString()));
                reqEntity.addPart("mobile", new StringBody(mEdtxtPhone.getText().toString()));
                reqEntity.addPart("email", new StringBody(mEdtxtEmail.getText().toString()));
                reqEntity.addPart("password", new StringBody(mEdtxtPassword.getText().toString()));
                reqEntity.addPart("user_type", new StringBody("0"));
                reqEntity.addPart("device_token", new StringBody(AppPreferences.GetDeviceToken(getActivity().getApplicationContext())));
                reqEntity.addPart("show_password", new StringBody(mEdtxtPassword.getText().toString()));
                reqEntity.addPart("device_type", new StringBody("android"));
                reqEntity.addPart("lat", new StringBody("0"));
                reqEntity.addPart("lng", new StringBody("0"));
                reqEntity.addPart("height", new StringBody("0"));
                reqEntity.addPart("bracch_code", new StringBody(branchPOJOList.get(spinner_branch.getSelectedItemPosition()).getBranch_code()));
                reqEntity.addPart("weight", new StringBody("0"));
                reqEntity.addPart("aadhar_id", new StringBody(""));
                reqEntity.addPart("address2", new StringBody(""));
                reqEntity.addPart("city", new StringBody(""));
                reqEntity.addPart("pincode", new StringBody(""));
                reqEntity.addPart("treatment_type", new StringBody(spinner_opd.getSelectedItem().toString()));
                reqEntity.addPart("ref_source", new StringBody(""));
                reqEntity.addPart("contact_person", new StringBody(""));
                reqEntity.addPart("contact_person_mob", new StringBody(""));
                reqEntity.addPart("user_description", new StringBody(et_tell_about.getText().toString()));
                FileBody bin = new FileBody(new File(pictureImagePath));
                reqEntity.addPart("profile_picture", bin);
                new WebServiceUploadFragment(reqEntity, getActivity(), this, CALL_REGISTER_API).execute(ApiConfig.PATIENT_REGISTRATION);
            } catch (Exception e) {
                e.printStackTrace();
                ToastClass.showShortToast(getActivity().getApplicationContext(), "Something went wrong");
            }
        } else {
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Error in getting image");
        }

    }

    public void callAPI() {
        ArrayList<NameValuePair> nameValuePairArrayList = new ArrayList<>();
        nameValuePairArrayList.add(new BasicNameValuePair("added_by", branchPOJOList.get(spinner_branch.getSelectedItemPosition()).getBranch_code()));
        nameValuePairArrayList.add(new BasicNameValuePair("first_name", mEdtxtFname.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("last_name", mEdtxtLname.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("mobile", mEdtxtPhone.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("email", mEdtxtEmail.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("password", mEdtxtPassword.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("user_type", "0"));
        nameValuePairArrayList.add(new BasicNameValuePair("device_token", AppPreferences.GetDeviceToken(getActivity().getApplicationContext())));
        nameValuePairArrayList.add(new BasicNameValuePair("show_password", mEdtxtPassword.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("device_type", "android"));
        nameValuePairArrayList.add(new BasicNameValuePair("lat", "0"));
        nameValuePairArrayList.add(new BasicNameValuePair("lng", "0"));
        nameValuePairArrayList.add(new BasicNameValuePair("height", "0"));
        nameValuePairArrayList.add(new BasicNameValuePair("weight", "0"));
        nameValuePairArrayList.add(new BasicNameValuePair("aadhar_id", ""));
        nameValuePairArrayList.add(new BasicNameValuePair("bracch_code", branchPOJOList.get(spinner_branch.getSelectedItemPosition()).getBranch_code()));
        nameValuePairArrayList.add(new BasicNameValuePair("address2", ""));
        nameValuePairArrayList.add(new BasicNameValuePair("treatment_type", spinner_opd.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("city", ""));
        nameValuePairArrayList.add(new BasicNameValuePair("pincode", ""));
        nameValuePairArrayList.add(new BasicNameValuePair("ref_source", ""));
        nameValuePairArrayList.add(new BasicNameValuePair("contact_person", ""));
        nameValuePairArrayList.add(new BasicNameValuePair("contact_person_mob", ""));
        nameValuePairArrayList.add(new BasicNameValuePair("user_description", et_tell_about.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("profile_picture", ""));
        new WebServiceBaseFragment(nameValuePairArrayList, getActivity(), this, CALL_REGISTER_API).execute(ApiConfig.PATIENT_REGISTRATION);
    }

    /**
     * @return none
     * @description Login web service API calling
     */
    private void registerApiCall() {
        if (!isValid())
            return;


        if (Utils.isNetworkAvailable(getActivity())) {


            if (mImgBase64 != null && !mImgBase64.equals("")) {

            } else {

            }

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.FIRST_NAME, mEdtxtFname.getText().toString().trim());
                params.put(ApiConfig.ADDED_BY, branchPOJOList.get(spinner_branch.getSelectedItemPosition()).getBranch_code());
                params.put(ApiConfig.LAST_NAME, mEdtxtLname.getText().toString().trim());
                params.put(ApiConfig.MOBILE, mEdtxtPhone.getText().toString().trim());
                params.put(ApiConfig.USER_TYPE, mUserType);
                params.put(ApiConfig.LATITUDE, "");
                params.put(ApiConfig.LONGITUDE, "");
                params.put(ApiConfig.DEVICE_TYPE, Constants.GlobalConst.DEVICE_TYPE);
                params.put(ApiConfig.DEVICE_TOKEN, "");
                params.put(ApiConfig.STATUS, "1");
                params.put(ApiConfig.EMAIL, mEdtxtEmail.getText().toString().trim());
                params.put(ApiConfig.PASSWORD, mEdtxtPassword.getText().toString().trim());
                params.put(ApiConfig.REG_AS_A_PATIENT, "");
                params.put("age", "");
                params.put("height", "");
                params.put("weight", "");
                params.put("aadhar_id", "");
                params.put("address2", "");
                params.put("pincode", "");
                params.put("city", "");
                params.put("gender", "");
                params.put("food_habit", "");
                params.put("bmi", "");
                params.put("ref_source", "");
                params.put("contact_person", "");
                params.put("contact_person_mob", "");
                params.put("treatment_type", "Opd");
                params.put("user_description", et_tell_about.getText().toString());

                if (mImgBase64 != null && !mImgBase64.equals("")) {
                    params.put(ApiConfig.PROFILE_PIC, mImgBase64);
                } else {
                    params.put(ApiConfig.PROFILE_PIC, "");
                }
                Log.d(TagUtils.getTag(), "registration params:-" + params.toString());
                new UrlConnectionAuthTask(getActivity(), ApiConfig.REGISTER_URL, ApiConfig.ID2, true, params, UserDetailModel.class, mListener).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }
//            OtpFragment fragment = OtpFragment.newInstance();
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            ft.add(R.id.fragment_container, fragment);
//            ft.addToBackStack(null);
//            ft.commit();

        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }

    private static final int CAMERA_REQUEST = 1888;
    String pictureImagePath = "";

    private void dispatchTakePictureIntent() {
        String strMyImagePath = FileUtil.getBaseFilePath() + File.separator + "temp.png";

        pictureImagePath = strMyImagePath;
        File file = new File(pictureImagePath);
        Uri outputFileUri = Uri.fromFile(file);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getActivity().getApplicationContext(), "com.capri4physio.fileProvider", file);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

        } else {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        }
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
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

    private int PICK_IMAGE_REQUEST = 1;

    private void openGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }


    /**
     * Validation to check user inputs
     *
     * @return none
     */
    private boolean isValid() {

        String fname = mEdtxtFname.getText().toString().trim();
        String lname = mEdtxtLname.getText().toString().trim();
        String email = mEdtxtEmail.getText().toString().trim();
        String phone = mEdtxtPhone.getText().toString().trim();
        String pass = mEdtxtPassword.getText().toString().trim();

        if (fname.isEmpty()) {
            mEdtxtFname.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_fname));
            return false;
        }

        if (lname.isEmpty()) {
            mEdtxtLname.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_lname));
            return false;
        }


        if (email.isEmpty()) {
            mEdtxtEmail.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_email));
            return false;
        }

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        // Make the comparison case-insensitive.
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        boolean matchFound = matcher.matches();
        if (!matchFound) {
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_email));
            mEdtxtEmail.requestFocus();
            return false;
        }
        if (phone.isEmpty()) {
            mEdtxtPhone.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_mobile));
            return false;
        }

        if (pass.isEmpty()) {
            mEdtxtPassword.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_password));
            return false;
        }

        return true;
    }


    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GET_ALL_BRANCHES:
                parseGetBranch(response);
                break;
            case CALL_REGISTER_API:
                parseRegisterAPI(response);
                break;
        }
    }

    public void parseRegisterAPI(String response) {
        Log.d(TagUtils.getTag(), "register response:-" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {
                JSONObject result = jsonObject.optJSONObject("result");

                AppPreferences.getInstance(getActivity().getApplicationContext()).setUserID(result.optString("id"));
                AppPreferences.getInstance(getActivity().getApplicationContext()).setUserName(result.optString("id"));
                AppPreferences.getInstance(getActivity().getApplicationContext()).setLastName(result.optString("id"));
                AppPreferences.getInstance(getActivity().getApplicationContext()).setUSER_BRANCH_CODE(result.optString("bracch_code"));
                AppPreferences.getInstance(getActivity().getApplicationContext()).setUserName(result.optString("first_name")+" "+result.optString("last_name"));
                AppPreferences.getInstance(getActivity().getApplicationContext()).setEmail(result.optString("email"));
                AppPreferences.getInstance(getActivity().getApplicationContext()).setPropic(result.optString("profile_pic"));
                AppPreferences.getInstance(getActivity().getApplicationContext()).setMobile(result.optString("mobile"));
                AppPreferences.getInstance(getActivity().getApplicationContext()).setClinicCount(0);
                AppPreferences.getInstance(getActivity().getApplicationContext()).setClinicId("");
                AppPreferences.getInstance(getActivity().getApplicationContext()).setUserType("0");
                AppPreferences.getInstance(getActivity().getApplicationContext()).setFirstName(result.optString("first_name"));
                AppPreferences.getInstance(getActivity().getApplicationContext()).setaddress("");
                AppPreferences.getInstance(getActivity().getApplicationContext()).setOTPVerified(false);

                SplashActivity splashActivity= (SplashActivity) getActivity();
                splashActivity.callOTPFragment("0");
            } else {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "Email Address Already Exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<BranchPOJO> branchPOJOList = new ArrayList<>();

    public void parseGetBranch(String response) {
        Log.d(TagUtils.getTag(), "response:-" + response);
        branchPOJOList.clear();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                BranchPOJO branchPOJO = new BranchPOJO(jsonObject.optString("branch_id"),
                        jsonObject.optString("branch_name"),
                        jsonObject.optString("branch_code"),
                        jsonObject.optString("branch_status"));
                branchPOJOList.add(branchPOJO);
            }
            List<String> braStringList = new ArrayList<>();
            for (BranchPOJO branchPOJO : branchPOJOList) {
                braStringList.add(branchPOJO.getBranch_name() + " (" + branchPOJO.getBranch_code() + ")");
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    getActivity().getApplicationContext(), R.layout.dropsimpledown, braStringList);
            spinner_branch.setAdapter(spinnerArrayAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }
}