package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
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
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.capri4physio.R;
import com.capri4physio.Services.GetWebServicesFragment;
import com.capri4physio.Services.WebServiceUploadFragment;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.activity.CropActivity;
import com.capri4physio.activity.SplashActivity;
import com.capri4physio.fragment.BaseFragment;
import com.capri4physio.fragment.ViewPatientFragment;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.model.UserDetails;
import com.capri4physio.model.branch.BranchPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.FileUtil;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author prabhunathy
 * @version 1.0
 * @since 2014-03-31
 */
public class PatientInfoFragment extends BaseFragment implements HttpUrlListener,WebServicesCallBack {

    private static final String GET_ALL_BRANCHES = "get_all_branches";
    private static final String CALL_PROFILE_UPDATE_API = "call_profile_update";
    private Button mBtnSave;
    String file, selectedImagePath;
    private RadioGroup mRadioGroupGender;
    private RadioGroup mRadioGroupMarital;
    private ImageView mImgDatePicker;
    CircleImageView mImgProfile;
    RadioButton radio_male, radio_female, radio_single, radio_married;
    RadioButton radio_veg, radio_nonveg, radio_eggertarian;
    private EditText mEdtxtFname;
    private EditText mEdtxtLname;
    private static EditText mEdtxtAge;
    private EditText mEdtxtHeight;
    private EditText mEdtxtWeight;
    private EditText mEdtxtBmi;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;
    private static final int PIC_CROP = 3;
    private Bitmap bitmapImage;
    byte[] byteArray;
    private String mImgBase64;
    HttpEntity resEntity;
    String response_str;
    Activity activity;
    private EditText aadharid, edtxt_email, edtxt_password, edtxt_mobile, address, city, Pincode, ContactPerson, ContactPersonMobile, Referralsource;
    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_USER = "key_user";
    private String patientId = "";
    private String assessmentType = "";
    private static String strDateOfBirth;
    Boolean indexing;
    private static String strAge;
    private RadioGroup mRadioGroupFoodHabit;
    private String stFoodhabit = "Veg";
    private String stGender = "Male";
    private String stMarital = "Single";
    UserDetails userDetails;
    Spinner spinner_branch;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CheifComplaintFragment.
     */
    public static PatientInfoFragment newInstance(String patientId, UserDetails user) {
        PatientInfoFragment fragment = new PatientInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user_data", user);
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_PATIENT_ID, patientId);
        fragment.setArguments(bundle);
        return fragment;
    }

    public PatientInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            patientId = getArguments().getString(KEY_PATIENT_ID);
            userDetails = (UserDetails) getArguments().getSerializable("user_data");
            Log.d(TagUtils.getTag(), "user details:-" + userDetails.toString());
            // assessmentType = getArguments().getString(KEY_TYPE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_info_patient, container, false);
        initView(rootView);

        setListener();
        return rootView;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);

        edtxt_email = (EditText) view.findViewById(R.id.edtxt_email);
        edtxt_password = (EditText) view.findViewById(R.id.edtxt_password);
        aadharid = (EditText) view.findViewById(R.id.edtxt_aadhar_ID);
        city = (EditText) view.findViewById(R.id.edtxt_City);
        ContactPerson = (EditText) view.findViewById(R.id.edtxt_contact_person);
        edtxt_mobile = (EditText) view.findViewById(R.id.edtxt_mobile);
        ContactPersonMobile = (EditText) view.findViewById(R.id.edtxt_contact_person_mobile);
        Referralsource = (EditText) view.findViewById(R.id.edtxt_reffs);
        address = (EditText) view.findViewById(R.id.edtxt_address);
        Pincode = (EditText) view.findViewById(R.id.edtxt_pin_code);
        mRadioGroupGender = (RadioGroup) view.findViewById(R.id.radio_gender);
        mRadioGroupMarital = (RadioGroup) view.findViewById(R.id.radio_marital);
        mRadioGroupFoodHabit = (RadioGroup) view.findViewById(R.id.rg_fh);
        mEdtxtFname = (EditText) view.findViewById(R.id.edtxt_fname);
        mEdtxtLname = (EditText) view.findViewById(R.id.edtxt_lname);
        mEdtxtAge = (EditText) view.findViewById(R.id.edtxt_age);
        mEdtxtHeight = (EditText) view.findViewById(R.id.edtxt_height);
        mEdtxtWeight = (EditText) view.findViewById(R.id.edtxt_weight);
        radio_single = (RadioButton) view.findViewById(R.id.radio_single);
        radio_married = (RadioButton) view.findViewById(R.id.radio_married);
        radio_male = (RadioButton) view.findViewById(R.id.radio_male);
        radio_female = (RadioButton) view.findViewById(R.id.radio_female);
        radio_veg = (RadioButton) view.findViewById(R.id.radio_veg);
        radio_nonveg = (RadioButton) view.findViewById(R.id.radio_nonveg);
        radio_eggertarian = (RadioButton) view.findViewById(R.id.radio_eggeterian);
        spinner_branch = (Spinner) view.findViewById(R.id.spinner_branch);

        mBtnSave = (Button) view.findViewById(R.id.btn_save);
        mImgDatePicker = (ImageView) view.findViewById(R.id.img_date_picker);
        mImgProfile = (CircleImageView) view.findViewById(R.id.img_profile);

        mEdtxtFname.setText(String.valueOf(userDetails.getFirstName()));
        mEdtxtLname.setText(String.valueOf(userDetails.getLastName()));
        mEdtxtAge.setText(String.valueOf(userDetails.getAge()));
        mEdtxtHeight.setText(String.valueOf(userDetails.getHeight()));
        mEdtxtWeight.setText(String.valueOf(userDetails.getWeight()));
        edtxt_mobile.setText(String.valueOf(userDetails.getMobile()));
        edtxt_password.setText(String.valueOf(userDetails.getShowPassword()));
        Referralsource.setText(String.valueOf(userDetails.getRef_source()));
        ContactPerson.setText(String.valueOf(userDetails.getContact_person()));
        ContactPersonMobile.setText(String.valueOf(userDetails.getContact_person_mob()));
        aadharid.setText(String.valueOf(userDetails.getAadharId()));
        edtxt_email.setText(String.valueOf(userDetails.getEmail()));
        address.setText(String.valueOf(userDetails.getAddress()));
        city.setText(String.valueOf(userDetails.getCity()));
        Pincode.setText(String.valueOf(userDetails.getPincode()));

        Glide.with(getActivity().getApplicationContext())
                .load(ApiConfig.PROFILE_PIC_BASE_URL+userDetails.getProfilePic())
                .error(R.drawable.ic_action_person)
                .placeholder(R.drawable.ic_action_person)
                .dontAnimate()
                .into(mImgProfile);

        try {
            String gender = String.valueOf(userDetails.getGender());
            String marital_status = String.valueOf(userDetails.getMaritalStatus());
            String food_habbit = String.valueOf(userDetails.getFoodHabit());
            if (gender.equals("Male")) {
                radio_male.setChecked(true);
                stGender = "Male";
            } else if (gender.equals("Female")) {
                radio_female.setChecked(true);
                stGender = "Female";
            }


            if (marital_status.equals("Single")) {
                radio_single.setChecked(true);
                stMarital = "Single";
            } else if (marital_status.equals("Married")) {
                radio_married.setChecked(true);
                stMarital = "Married";
            }


            if (food_habbit.equals("Veg")) {
                radio_veg.setChecked(true);
                stFoodhabit = "Veg";
            } else if (food_habbit.equals("Non-veg")) {
                radio_nonveg.setChecked(true);
                stFoodhabit = "Non-veg";
            } else if (food_habbit.equals("Eggetarian")) {
                radio_eggertarian.setChecked(true);
                stFoodhabit = "Eggetarian";
            }

        } catch (Exception e) {
            e.printStackTrace();


        }

        if (String.valueOf(userDetails.getTreatment_type()).equalsIgnoreCase("Opd")) {
            indexing = true;
        } else {
            indexing = false;
        }
        try {
            String bitmap = String.valueOf(userDetails.getProfilePic());

            Log.e("stringToBitmap", bitmap.toString());

        } catch (Exception e) {

        }
        getBranchList();

    }

    public void getBranchList(){
        new GetWebServicesFragment(getActivity(),this, GET_ALL_BRANCHES,false).execute(ApiConfig.GetURL);
    }

    @Override
    protected void setListener() {
        super.setListener();

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addApiCall();
            }
        });
        mImgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOption();
            }
        });
        mImgDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

//        stMarital="Single";
//        stGender="Male";
        mRadioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {


                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    stGender = rb.getText().toString();
                }
            }
        });
        mRadioGroupFoodHabit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    stFoodhabit = rb.getText().toString();
                }
            }
        });

        mRadioGroupMarital.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case 0:
                        stMarital = "Single";
                        break;
                    case 1:
                        stMarital = "Married";
                }
                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    stMarital = rb.getText().toString();
                    Log.e("stMarital", stMarital);
                }
            }
        });
    }
    String main_profile_picture="";
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != SplashActivity.RESULT_OK)
            return;

        Intent intent = null;
        ByteArrayOutputStream stream;
        switch (requestCode) {
            case PICK_FROM_CAMERA:
//                intent = new Intent(getActivity(), CropActivity.class);
//                intent.putExtra("url", SplashActivity.mImageCaptureUri.getPath());
//                startActivityForResult(intent, PIC_CROP);

                File imgFile = new File(pictureImagePath);
                if (imgFile.exists()) {
                    main_profile_picture=pictureImagePath;
                    Bitmap bmp = BitmapFactory.decodeFile(pictureImagePath);
                    bmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth() / 4, bmp.getHeight() / 4, false);
                    bitmapImage=bmp;
                    mImgProfile.setImageBitmap(bitmapImage);
                    stream= new ByteArrayOutputStream();
                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 0, stream);
                    byteArray = stream.toByteArray();
                    mImgBase64 = Base64.encodeToString(byteArray, 0);
                    AppLog.e("Capri4Physio", "Base64 IMG - " + mImgBase64);
                }



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
                Log.e("filePath", selectedImage.toString());
//            selectedImagePath=filePath.getPath();
                getRealPathFromUri(getActivity(), selectedImage);
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
                main_profile_picture=picturePath;
                if(new File(main_profile_picture).exists()){
                    Bitmap bmp = BitmapFactory.decodeFile(main_profile_picture);
                    bmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth() / 4, bmp.getHeight() / 4, false);
                    bitmapImage=bmp;
                    mImgProfile.setImageBitmap(bitmapImage);
                    stream= new ByteArrayOutputStream();
                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 0, stream);
                    byteArray = stream.toByteArray();
                    mImgBase64 = Base64.encodeToString(byteArray, 0);
                    AppLog.e("Capri4Physio", "Base64 IMG - " + mImgBase64);
                }

//                intent = new Intent(getActivity(), CropActivity.class);
//                intent.putExtra("url", picturePath);
//                startActivityForResult(intent, PIC_CROP);

                break;

            case PIC_CROP:
                bitmapImage = CropActivity.croppedImage;
                mImgProfile.setImageBitmap(bitmapImage);
                stream = new ByteArrayOutputStream();
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 0, stream);
                byteArray = stream.toByteArray();
                mImgBase64 = Base64.encodeToString(byteArray, 0);
                AppLog.e("Capri4Physio", "Base64 IMG - " + mImgBase64);

                break;
            default:
                break;

        }
    }

    private void getRealPathFromUri(Context ctx, Uri uri) {

        String[] filePathColumn = {MediaStore.Files.FileColumns.DATA};

        Cursor cursor = ctx.getContentResolver().query(uri, filePathColumn,
                null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        selectedImagePath = cursor.getString(columnIndex);
        Log.e("picturePath", "picturePath : " + selectedImagePath);
        cursor.close();

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
    String pictureImagePath="";
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
        startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
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


    private void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }


    private void addApiCall() {
        if (!isValid())
            return;

        if (Utils.isNetworkAvailable(getActivity())) {

            if (main_profile_picture != null) {
//                progressBar.setVisibility(View.VISIBLE);
              callPatientUpdateFragment(main_profile_picture);
            } else {
                callPatientUpdateFragment("");
            }
        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }


    @Override
    public void onPostSuccess(Object response, int id) {
        switch (id) {

            case ApiConfig.ID1:
                BaseModel baseModel = (BaseModel) response;
                AppLog.i("Capri4Physio", "Patient Response : " + baseModel.getStatus());
                getFragmentManager().popBackStack();

                break;
        }

    }

    @Override
    public void onPostError(String errMsg, int id) {

    }


    /**
     * Validation to check user inputs
     *
     * @return
     */
    private boolean isValid() {

        String fname = mEdtxtFname.getText().toString().trim();
        String lname = mEdtxtLname.getText().toString().trim();
        String stAge = mEdtxtAge.getText().toString().trim();
        String stheight = mEdtxtHeight.getText().toString().trim();

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

        if (stAge.isEmpty()) {
            mEdtxtAge.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_age));
            return false;
        }

        if (stheight.isEmpty()) {
            mEdtxtHeight.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_height));
            return false;
        }

        if (stheight.isEmpty()) {
            mEdtxtHeight.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_height));
            return false;
        }

        return true;
    }
    List<BranchPOJO> branchPOJOList = new ArrayList<>();
    String branch_code="";
    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case GET_ALL_BRANCHES:
                parseAllBranches(response);
                break;
            case CALL_PROFILE_UPDATE_API:
                parseProfileUpdate(response);
                break;
        }
    }
    public void parseProfileUpdate(String response){
        Log.d(TagUtils.getTag(),"update response:-"+response);
//        viewStaff();
        getFragmentManager().popBackStack();
    }


    public void parseAllBranches(String response) {
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


            int position=-1;
            for(int i=0;i<branchPOJOList.size();i++){
                BranchPOJO branchPOJO=branchPOJOList.get(i);
                if(userDetails.getBracch_code().equals(branchPOJO.getBranch_code())){
                   position=i;
                }
            }



            spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    branch_code = branchPOJOList.get(position).getBranch_code();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            if(position!=-1){
                spinner_branch.setSelection(position);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day1 = 1;
            int day = c.get(Calendar.DAY_OF_MONTH);
            Log.d("dayyaa", day + "");

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            final Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, day);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            strDateOfBirth = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(c.getTime());
            strAge = getAge(year, month, day);
            mEdtxtAge.setText(strAge);
        }
    }


    private static String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    @Override
    public void onPause() {
        super.onPause();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Patient Information");
    }

    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Patient Information");
    }

    private String GetGender() {

        if (mRadioGroupGender.getCheckedRadioButtonId() == R.id.radio_male) {
            return "male";
        } else {
            return "female";
        }
    }

    private String GetMartialStatus() {

        if (mRadioGroupMarital.getCheckedRadioButtonId() == R.id.radio_single) {
            return "single";
        } else {
            return "married";
        }
    }


    private void getpatientlist() {

        final String first_name = mEdtxtFname.getText().toString().trim();
        final String emailid = edtxt_email.getText().toString();
        final String password = edtxt_password.getText().toString();
        final String last_name = mEdtxtLname.getText().toString().trim();
        final String Age = mEdtxtAge.getText().toString().trim();
        final String Height = mEdtxtHeight.getText().toString();
        float hght = 0;
        try {
            hght= Float.parseFloat(Height);
        }catch (Exception e){
            e.printStackTrace();
        }
        float hhh = (hght * 2);
//            final String image = selectedImagePath;
        final String weight = mEdtxtWeight.getText().toString().trim();
        final String aadhar = aadharid.getText().toString().trim();
        final String pc = Pincode.getText().toString().trim();
        final String add = address.getText().toString().trim();
        final String citi = city.getText().toString().trim();
        final String phon = edtxt_mobile.getText().toString().trim();
        final String conper = ContactPerson.getText().toString().trim();
        final String conpermob = ContactPersonMobile.getText().toString().trim();
        final String reff = Referralsource.getText().toString().trim();

        final float wght = Integer.parseInt(weight);
        float wwww = wght;
        float finalvaluee = (wght / hhh);
        final Double bmiresult = Double.valueOf(finalvaluee);
        final String bmi = String.format("%.2f", bmiresult);
        Log.e("bmoi", wght + "," + Height + "," + bmi);
//            final String image = selectedImagePath;

        final String u_gender = GetGender();
        final String u_marital_status = GetMartialStatus();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.UPDATE_PATIENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            viewStaff();
                            Toast.makeText(getActivity(), "Profile Successfully updated ", Toast.LENGTH_LONG).show();
                            Log.e("result", response);
//                            progressDialog.hide();
//                            new UrlConnectionTask(getActivity(), ApiConfig.VIEW_PATIENT_URL, ApiConfig.ID1, true, objresponse, UserListModel.class, this).execute("");                            else {


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.e("Postdat", "" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_gender));
                        error.printStackTrace();
                        Log.w("Postdat", "" + error);
                    }
                }) {


            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("p_id", String.valueOf(userDetails.getId()));
                params.put("first_name", first_name);
                params.put("last_name", last_name);
                params.put("email", emailid);
                params.put("age", Age);
                params.put("dob", "2014-09-09");
                params.put("bmi", bmi);
                params.put("height", Height);
                params.put("weight", weight);
                params.put("gender", u_gender);
                params.put("profile_pic", String.valueOf(userDetails.getProfilePic()));
                params.put("marital_status", u_marital_status);
                params.put("mobile", phon);
                params.put("aadhar_id", aadhar);
                params.put("branch_code", branch_code);
                params.put("address2", add);
                params.put("pincode", pc);
                params.put("city", citi);
                params.put("food_habit", stFoodhabit.trim());
                params.put("ref_source", reff);
                params.put("contact_person", conper);
                params.put("contact_person_mob", conpermob);
                Log.d(TagUtils.getTag(), "params:-" + params.toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    private void viewStaff() {
        ViewPatientFragment fragment = ViewPatientFragment.newInstance(indexing);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        getSupportActionBar().setTitle(title);
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void callPatientUpdateFragment(String image_path){
        try {

            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);


            final String first_name = mEdtxtFname.getText().toString().trim();
            final String last_name = mEdtxtLname.getText().toString().trim();
            final String Age = mEdtxtAge.getText().toString().trim();
            final String Height = mEdtxtHeight.getText().toString();
            final String emailid = edtxt_email.getText().toString();
            final String aadhar = aadharid.getText().toString().trim();
            final String pc = Pincode.getText().toString().trim();
            final String add = address.getText().toString().trim();
            final String citi = city.getText().toString().trim();
            final String conper = ContactPerson.getText().toString().trim();
            final String conpermob = ContactPersonMobile.getText().toString().trim();
            final String reff = Referralsource.getText().toString().trim();
            final float hght = Float.parseFloat(Height);
            float hhh = (hght * 2);
            final String mob_number = edtxt_mobile.getText().toString().trim();
            final String password = edtxt_password.getText().toString().trim();
            ;
            final String weight = mEdtxtWeight.getText().toString().trim();
            final float wght = Integer.parseInt(weight);
            float wwww = wght;
            float finalvaluee = (wght / hhh);
            final Double bmiresult = Double.valueOf(finalvaluee);
            final String bmi = String.format("%.2f", bmiresult);
            Log.e("bmoi", wght + "," + Height + "," + bmi);
            final String u_gender = GetGender();
            final String u_marital_status = GetMartialStatus();


            reqEntity.addPart("p_id", new StringBody(String.valueOf(userDetails.getId())));
            reqEntity.addPart("first_name", new StringBody(first_name));
            reqEntity.addPart("last_name", new StringBody(last_name));
            reqEntity.addPart("age", new StringBody(Age));
            reqEntity.addPart("email", new StringBody(emailid));
            reqEntity.addPart("dob", new StringBody("2014-09-09"));
            reqEntity.addPart("height", new StringBody(Height));
            reqEntity.addPart("bmi", new StringBody(bmi));
            reqEntity.addPart("weight", new StringBody(weight));
            reqEntity.addPart("gender", new StringBody(u_gender));
            reqEntity.addPart("marital_status", new StringBody(u_marital_status));
            reqEntity.addPart("mobile", new StringBody(mob_number));
            reqEntity.addPart("aadhar_id", new StringBody(aadhar));
            reqEntity.addPart("address2", new StringBody(add));
            reqEntity.addPart("pincode", new StringBody(pc));
            reqEntity.addPart("branch_code", new StringBody(branchPOJOList.get(spinner_branch.getSelectedItemPosition()).getBranch_code()));
            reqEntity.addPart("city", new StringBody(citi));
            reqEntity.addPart("food_habit", new StringBody(stFoodhabit.trim()));
            reqEntity.addPart("ref_source", new StringBody(reff));
            reqEntity.addPart("contact_person", new StringBody(conper));
            reqEntity.addPart("contact_person_mob", new StringBody(conpermob));
            reqEntity.addPart("city", new StringBody(citi));

            if(image_path.length()>0) {

                File file1 = new File(image_path);
                Log.e("file1", image_path);
                FileBody bin1 = new FileBody(file1);
                reqEntity.addPart("profile_pic", bin1);
            }else{
                reqEntity.addPart("profile_pic", new StringBody(""));
            }
            new WebServiceUploadFragment(reqEntity,getActivity(), this, CALL_PROFILE_UPDATE_API).execute(ApiConfig.UPDATE_PATIENT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


