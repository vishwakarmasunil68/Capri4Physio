package com.capri4physio.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.activity.CropActivity;
import com.capri4physio.activity.SplashActivity;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.UserDetailModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionAuthTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.Utils;
import com.capri4physio.view.CircleImageView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import multipleimageselect.activities.AlbumSelectActivity;
import multipleimageselect.helpers.Constants;
import multipleimageselect.models.Image;


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
    private String mImgBase64;
    ArrayList<String> arrayListbitmap = new ArrayList<>();
    private String mUserType = "5";
    private static final String USER_TYPE = "user_type";


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
            Log.d(TagUtils.getTag(),"user type:-"+mUserType);
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

    }

    @Override
    protected void setListener() {
        super.setListener();

        mImgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
//                dialogOption();
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initProgressDialog("Please wait...");
                addStudentApiCall();
//                registerApiCall();
            }
        });
    }

    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<String> horizontalList;
        Boolean allvalue;
        private Context context;
        Activity activity;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public ImageView tv_time;
            public ImageView tv_time_cross;
            public LinearLayout ll_time;

            public MyViewHolder(View view) {
                super(view);
                tv_time = (ImageView) view.findViewById(R.id.tv_time);
                tv_time_cross = (ImageView) view.findViewById(R.id.tv_time_cross);
                ll_time = (LinearLayout) view.findViewById(R.id.ll_time);

                allvalue = false;


            }
        }


        public HorizontalAdapter(Context context, List<String> horizontalList) {
            this.horizontalList = horizontalList;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.inflate_monday_time, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            Bitmap bitmap = BitmapFactory.decodeFile(horizontalList.get(position));
            holder.tv_time.setImageBitmap(bitmap);
            holder.tv_time_cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    horizontalList.remove(position);
//                    listview.noti
//                    listview.notify();
                }
            });


        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
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

        if (resultCode != SplashActivity.RESULT_OK)
            return;

        Intent intent = null;

        switch (requestCode) {
            case Constants.REQUEST_CODE:
                ArrayList<String> arrayList = new ArrayList<>();
                ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0, l = images.size(); i < l; i++) {
                    stringBuffer.append(images.get(i).path + "\n");
                }
                for (Image image : images) {

                    arrayList.add(image.path.toString());
                    Bitmap bitmap = BitmapFactory.decodeFile(image.path);
                    mImgProfile.setVisibility(View.GONE);
                    Log.e("images1", image.path);
                    Log.e("images1", arrayList.toString());
                    Log.e("images1", bitmap.toString());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
                    byteArray = stream.toByteArray();
                    mImgBase64 = Base64.encodeToString(byteArray, 0);

                    AppLog.e("Capri4Physio", "Base64 IMG - " + arrayListbitmap);
//                    StringToBitMap(image.path);
//                    Uri filepath =image.path;
                }
                arrayListbitmap.add(mImgBase64.replace("[", "").replace("]", ""));
//                Log.e("images",arrayList+"");
                listview.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), stringBuffer.toString(), Toast.LENGTH_LONG).show();
                HorizontalAdapter adapter = new HorizontalAdapter(getActivity(), arrayList);
                GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
                listview.setHasFixedSize(true);
                listview.setLayoutManager(layoutManager);
//                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                listview.setAdapter(adapter);
//                textView.setText(stringBuffer.toString());
                break;
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
                mImgProfile.setImageBitmap(bitmapImage);
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
    private void registerApiCall() {
        if (!isValid())
            return;


        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.FIRST_NAME, mEdtxtFname.getText().toString().trim());
                params.put(ApiConfig.ADDED_BY, "");
                params.put(ApiConfig.LAST_NAME, mEdtxtLname.getText().toString().trim());
                params.put(ApiConfig.MOBILE, mEdtxtPhone.getText().toString().trim());
                params.put(ApiConfig.USER_TYPE, mUserType);
                params.put(ApiConfig.LATITUDE, "");
                params.put(ApiConfig.LONGITUDE, "");
//                params.put(ApiConfig.DEVICE_TYPE, Constants.GlobalConst.DEVICE_TYPE);
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

                if (mImgBase64 != null && !mImgBase64.equals("")) {
                    params.put(ApiConfig.PROFILE_PIC, mImgBase64);
                } else {
                    params.put(ApiConfig.PROFILE_PIC, "");
                }

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
                params.put(ApiConfig.att_photo, arrayListbitmap + "");
                Log.d(TagUtils.getTag(),"students params:-"+params.toString());
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
        Intent intent = new Intent(getActivity(), AlbumSelectActivity.class);
//set limit on number of images that can be selected, default is 10
        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 3);
        startActivityForResult(intent, Constants.REQUEST_CODE);
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        File photoFile = null;
//        try {
//            photoFile = createImageFile();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//        if (photoFile != null) {
//            SplashActivity.mImageCaptureUri = Uri.fromFile(photoFile);
//            try {
//                startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
//            } catch (ActivityNotFoundException e) {
//                e.printStackTrace();
//            }
//        }

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


}