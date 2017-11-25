package com.capri4physio.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
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
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.capri4physio.R;
import com.capri4physio.activity.SplashActivity;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.UserDetailModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionAuthTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.FileUtil;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.Utils;
import com.capri4physio.view.CircleImageView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpStudentDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author prabhunathy
 * @version 1.0
 * @since 2016-03-31
 */
public class SignUpStudentDetailFragment extends BaseFragment {

    private EditText mEdtxtFname;
    private EditText mEdtxtLname;
    private EditText mEdtxtEmail;
    private EditText mEdtxtPhone;
    private EditText mEdtxtPassword;
    private CircleImageView mImgProfile;

    private Button mBtnRegister;
    private HttpUrlListener mListener;
    RecyclerView listview;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;
    private static final int PIC_CROP = 3;
    private Bitmap bitmapImage;
    byte[] byteArray;

    ProgressDialog pDialog;
    private String mImgBase64="";
    ArrayList<String> arrayListbitmap = new ArrayList<>();
    private String mUserType = "5";
    private static final String USER_TYPE = "user_type";
    private EditText et_tell_about;
    private RelativeLayout rl_branch;
    private RelativeLayout rl_opd;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static SignUpStudentDetailFragment newInstance(String userType) {
        SignUpStudentDetailFragment fragment = new SignUpStudentDetailFragment();
        Bundle args = new Bundle();
        args.putString(USER_TYPE, userType);
        fragment.setArguments(args);
        return fragment;
    }

    public SignUpStudentDetailFragment() {
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
            Log.d(TagUtils.getTag(), "user type:-" + mUserType);
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
        listview = (RecyclerView) view.findViewById(R.id.listview);
        rl_branch = (RelativeLayout) view.findViewById(R.id.rl_branch);
        rl_opd = (RelativeLayout) view.findViewById(R.id.rl_opd);
        et_tell_about = (EditText) view.findViewById(R.id.et_tell_about);

        rl_branch.setVisibility(View.GONE);
        et_tell_about.setVisibility(View.GONE);


        rl_opd.setVisibility(View.GONE);

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
                initProgressDialog("Please wait...");
                addStudentApiCall();
            }
        });
    }


    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        try {
            pDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            mImgProfile.setImageBitmap(bitmap);
            Log.e("imagesStr2Bmp", bitmap + "");
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
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

    private int PICK_IMAGE_REQUEST = 1;

    public void setImage(String path) {
        Glide.with(getActivity().getApplicationContext()).load(path.toString()).into(mImgProfile);
        bitmapImage = BitmapFactory.decodeFile(path.toString());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byteArray = stream.toByteArray();
        mImgBase64 = Base64.encodeToString(byteArray, 0);
        AppLog.e("Capri4Physio", "Base64 IMG - " + mImgBase64);
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

    private void addStudentApiCall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.REGISTER_STUDENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("response", "" + response);
                            pDialog.hide();

                            JSONObject jsonObject = new JSONObject(response);
                            String branch_status = jsonObject.getString("status");
                            if (branch_status.equals("1")) {
                                Toast.makeText(getActivity(), "Record Successfully Added", Toast.LENGTH_LONG).show();
                                loginApiCall();
//                                getActivity().finish();
                                Log.e("response", "" + response);
                            } else {
                                Toast.makeText(getActivity(), "User already exist", Toast.LENGTH_LONG).show();
                            }
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
                params.put(ApiConfig.FIRST_NAME, mEdtxtFname.getText().toString().trim());
                params.put(ApiConfig.LAST_NAME, mEdtxtLname.getText().toString().trim());
                params.put(ApiConfig.MOBILE, mEdtxtPhone.getText().toString().trim());
                params.put(ApiConfig.USER_TYPE, mUserType);
                params.put(ApiConfig.STATUS, "1");
                params.put(ApiConfig.EMAIL, mEdtxtEmail.getText().toString().trim());
                params.put(ApiConfig.PASSWORD, mEdtxtPassword.getText().toString().trim());
                params.put(ApiConfig.SHOW_PASSWORD, mEdtxtPassword.getText().toString().trim());
                params.put(ApiConfig.att_photo, mImgBase64);
                Log.d(TagUtils.getTag(), "students params:-" + params.toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void loginApiCall() {
        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.EMAIL, mEdtxtEmail.getText().toString().trim());
                params.put(ApiConfig.PASSWORD, mEdtxtPassword.getText().toString().trim());
                new UrlConnectionAuthTask(getActivity(), ApiConfig.LOGIN_URL, ApiConfig.ID1, true, params, UserDetailModel.class, mListener).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }


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