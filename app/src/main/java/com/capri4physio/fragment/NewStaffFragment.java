package com.capri4physio.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
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
import android.widget.TextView;
import android.widget.Toast;

import com.capri4physio.Invoice.InfoApps1;
import com.capri4physio.R;
import com.capri4physio.Services.WebServiceUploadFragment;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.activity.CropActivity;
import com.capri4physio.activity.SplashActivity;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.model.UserDetailModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionAuthTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.Constants;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.capri4physio.util.Utils;
import com.capri4physio.view.CircleImageView;

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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.capri4physio.R.id.radio_marital;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewStaffFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author prabhunathy
 * @version 1.0
 * @since 2014-03-31
 */
public class NewStaffFragment extends BaseFragment implements HttpUrlListener, WebServicesCallBack {

    private static final String CALL_PATIENT_ADD_API = "call_patient_api";
    private static final String ADD_NEW_PATIENT = "ADD_NEW_PATIENT";
    private EditText mEdtxtFname;
    private RadioGroup mRadioGroupFoodHabit;
    private ImageView mImgDatePicker;
    private RadioGroup mRadioGroupGender;
    private RadioGroup mRadioGroupMarital;
    private EditText mEdtxtLname;
    private static EditText mEdtxtEmail, mEdtxtAge;
    private EditText mEdtxtPhone;
    private EditText mEdtxtHeight, aadharid, address, city, Pincode, ContactPerson, ContactPersonMobile, Referralsource;
    private EditText mEdtxtWeight;
    private EditText mEdtxtPassword;
    private CircleImageView mImgProfile;

    private Button mBtnRegister;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;
    private static final int PIC_CROP = 3;
    private Bitmap bitmapImage;
    String register_As;
    byte[] byteArray;
    private static String strDateOfBirth;
    Boolean indexing;
    private static String strAge;
    private String stMarital = "Opd";
    private String stGender = "";
    private String mImgBase64;
    private String stFoodhabit = "Veg";
    String branch_code;
    Spinner spinnerbranchloca;
    TextView tv_contact_info;
    InfoApps1 detailApps;
    ArrayList<String> arrayList;
    RadioButton radio_single;
    String[] location = {"Gurgaon", "Sant Parmanand Hospital", "Greater Kailash 1", "Karkarduma"};
    String bmi;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static NewStaffFragment newInstance() {
        NewStaffFragment fragment = new NewStaffFragment();
        return fragment;
    }

    public NewStaffFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_staff, container, false);
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
        mEdtxtFname = (EditText) view.findViewById(R.id.edtxt_fname);
        mRadioGroupMarital = (RadioGroup) view.findViewById(radio_marital);
        mRadioGroupGender = (RadioGroup) view.findViewById(R.id.rg_gender);
        mRadioGroupFoodHabit = (RadioGroup) view.findViewById(R.id.rg_fh);
        mImgDatePicker = (ImageView) view.findViewById(R.id.img_date_picker);
        mEdtxtLname = (EditText) view.findViewById(R.id.edtxt_lname);
        mEdtxtEmail = (EditText) view.findViewById(R.id.edtxt_email);
        mEdtxtAge = (EditText) view.findViewById(R.id.edtxt_age);
        mEdtxtPhone = (EditText) view.findViewById(R.id.edtxt_mobile);
        mEdtxtHeight = (EditText) view.findViewById(R.id.edtxt_height);
        mEdtxtWeight = (EditText) view.findViewById(R.id.edtxt_weight);
        aadharid = (EditText) view.findViewById(R.id.edtxt_aadhar_ID);
        city = (EditText) view.findViewById(R.id.edtxt_City);
        ContactPerson = (EditText) view.findViewById(R.id.edtxt_contact_person);
        ContactPersonMobile = (EditText) view.findViewById(R.id.edtxt_contact_person_mobile);
        Referralsource = (EditText) view.findViewById(R.id.edtxt_reffs);
        address = (EditText) view.findViewById(R.id.edtxt_address);
        Pincode = (EditText) view.findViewById(R.id.edtxt_pin_code);
        mEdtxtPassword = (EditText) view.findViewById(R.id.edtxt_password);
        mImgProfile = (CircleImageView) view.findViewById(R.id.img_profile);
        mBtnRegister = (Button) view.findViewById(R.id.btn_submit);
        spinnerbranchloca = (Spinner) view.findViewById(R.id.spinnerbranchloca);
        radio_single = (RadioButton) view.findViewById(R.id.radio_single);
    }

    @Override
    protected void setListener() {
        super.setListener();
        arrayList = new ArrayList<String>();
        new CatagoryUrlAsynTask1().execute();
        mImgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOption();
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final String Height = mEdtxtHeight.getText().toString();
                    final String weight = mEdtxtWeight.getText().toString().trim();

                    double height_cal = Double.parseDouble(Height);
                    double weight_cal = Double.parseDouble(weight);
                    height_cal = height_cal / 100;

                    bmi = getConvertedPrice(String.valueOf(weight_cal / (height_cal * height_cal)));
                    callAddNewPatient();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Please Enter Proper Value", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (AppPreferences.getInstance(getActivity()).getUserType().equals("4")) {
            spinnerbranchloca.setVisibility(View.VISIBLE);
        } else {
            branch_code = AppPreferences.getInstance(getActivity()).getUSER_BRANCH_CODE();
        }
        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String address = spinnerbranchloca.getSelectedItem().toString();

                String ad[] = address.split("\\(");
                String newaddress = ad[1];
                branch_code = newaddress.replace(")", "");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mImgDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

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

                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    stMarital = rb.getText().toString();
                }

                if (checkedId == R.id.radio_single) {
                    treatment_type = "Opd";
                } else {
                    treatment_type = "Home Patient";
                }
            }
        });
    }

    public String getConvertedPrice(String price) {
        try {
            double val = Double.parseDouble(price);
            DecimalFormat f = new DecimalFormat("##.##");
            return String.valueOf(f.format(val));
        } catch (Exception e) {
            e.printStackTrace();
            return price;
        }
    }

    String treatment_type = "Opd";

    private class CatagoryUrlAsynTask1 extends AsyncTask<String, String, String> {
        String id, catagoryName;


        @Override
        protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
            String content = HttpULRConnect.getData(ApiConfig.GetURL);
            Log.e("DoInBackGround ---->", String.valueOf(content));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                Log.e("DoInBackGroundtr", String.valueOf(s));
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String branch_name = jsonObject2.getString("branch_name");
                    String bracch_code = jsonObject2.getString("branch_code");
                    //branch_code
//                    arrayList.add(bracch_code);

                    detailApps = new InfoApps1();
                    detailApps.setName(branch_name);
                    detailApps.setId(bracch_code);
                    arrayList.add(detailApps.getName() + "  " + "(" + detailApps.getId() + ")");
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getActivity(), R.layout.dropsimpledown, arrayList);
                    spinnerbranchloca.setAdapter(spinnerArrayAdapter);

                }

            } catch (Exception e) {
                Log.e("error", e.toString());

            }
        }
    }

    private void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    String main_image_url = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != SplashActivity.RESULT_OK)
            return;

        Intent intent = null;

        switch (requestCode) {
            case PICK_FROM_CAMERA:
//                intent = new Intent(getActivity(), CropActivity.class);
//                intent.putExtra("url", SplashActivity.mImageCaptureUri.getPath());
//                startActivityForResult(intent, PIC_CROP);
                main_image_url = SplashActivity.mImageCaptureUri.getPath();
                mImgProfile.setImageBitmap(BitmapFactory.decodeFile(main_image_url));
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


//                intent = new Intent(getActivity(), CropActivity.class);
//                intent.putExtra("url", picturePath);
//                startActivityForResult(intent, PIC_CROP);
                main_image_url = picturePath;
                mImgProfile.setImageBitmap(BitmapFactory.decodeFile(main_image_url));
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


    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

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


    /**
     * @return none
     * @description Login web service API calling
     */

    public void callAddNewPatient() {
        if (!isValid())
            return;

        if (Utils.isNetworkAvailable(getActivity())) {
            String regis_As;
            try {
//            if (register_As.contains(null)){

                regis_As = register_As;
            } catch (Exception e) {
                e.printStackTrace();
                regis_As = "Opd";
            }


            try {
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("added_by", new StringBody(branch_code));
                reqEntity.addPart("first_name", new StringBody(mEdtxtFname.getText().toString().trim()));
                reqEntity.addPart("last_name", new StringBody(mEdtxtLname.getText().toString().trim()));
                reqEntity.addPart("mobile", new StringBody(mEdtxtPhone.getText().toString()));
                reqEntity.addPart("email", new StringBody(mEdtxtEmail.getText().toString()));
                reqEntity.addPart("password", new StringBody(mEdtxtPassword.getText().toString()));
                reqEntity.addPart("address", new StringBody(address.getText().toString()));
                reqEntity.addPart("user_type", new StringBody("0"));
                reqEntity.addPart("device_type", new StringBody("android"));
                reqEntity.addPart("device_token", new StringBody(""));
                reqEntity.addPart("status", new StringBody("1"));
                reqEntity.addPart("patient_treament_type", new StringBody(treatment_type));
                reqEntity.addPart("status", new StringBody("1"));
                reqEntity.addPart("show_password", new StringBody(mEdtxtPassword.getText().toString()));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                reqEntity.addPart("created", new StringBody(simpleDateFormat.format(new Date())));
                reqEntity.addPart("modified", new StringBody(simpleDateFormat.format(new Date())));
                reqEntity.addPart("treatment_type", new StringBody(treatment_type));
                reqEntity.addPart("dob", new StringBody(strDateOfBirth));
                reqEntity.addPart("gender", new StringBody(stGender.trim()));
                reqEntity.addPart("age", new StringBody(mEdtxtAge.getText().toString()));
                reqEntity.addPart("bmi", new StringBody(bmi.trim()));
                reqEntity.addPart("food_habit", new StringBody(stFoodhabit.trim()));
                reqEntity.addPart("ref_source", new StringBody(Referralsource.getText().toString()));
                reqEntity.addPart("contact_person", new StringBody(ContactPerson.getText().toString()));
                reqEntity.addPart("contact_person_mob", new StringBody(ContactPersonMobile.getText().toString()));
                reqEntity.addPart("height", new StringBody(mEdtxtHeight.getText().toString()));
                reqEntity.addPart("weight", new StringBody(mEdtxtWeight.getText().toString()));
                reqEntity.addPart("aadhar_id", new StringBody(aadharid.getText().toString()));
                reqEntity.addPart("city", new StringBody(city.getText().toString()));
                reqEntity.addPart("pincode", new StringBody(Pincode.getText().toString()));
                reqEntity.addPart("bracch_code", new StringBody(branch_code));
                if (main_image_url.length() > 0) {
                    FileBody bin1 = new FileBody(new File(main_image_url));
                    reqEntity.addPart("photo", bin1);
                } else {
                    reqEntity.addPart("photo", new StringBody(""));
                }
                reqEntity.addPart("otp_status", new StringBody("true"));

//                    Log.d(TagUtils.getTag(),"add patient params:-"+reqEntity.getContent().toString());
                new WebServiceUploadFragment(reqEntity, getActivity(), this, ADD_NEW_PATIENT).execute(ApiConfig.ADD_NEW_PATIENT);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getActivity(), "Please select patient register type", Toast.LENGTH_LONG).show();
        }
    }


    private void newStaffApiCall() {
        if (!isValid())
            return;


        if (Utils.isNetworkAvailable(getActivity())) {
            String regis_As;
            try {
//            if (register_As.contains(null)){

                regis_As = register_As;
            } catch (Exception e) {
                e.printStackTrace();
                regis_As = "Opd";
            }
            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.ADDED_BY, branch_code);
                params.put(ApiConfig.FIRST_NAME, mEdtxtFname.getText().toString().trim());
                params.put(ApiConfig.LAST_NAME, mEdtxtLname.getText().toString().trim());
                params.put(ApiConfig.MOBILE, mEdtxtPhone.getText().toString().trim());
                params.put(ApiConfig.USER_TYPE, "0");
                params.put(ApiConfig.LATITUDE, "");
                params.put(ApiConfig.LONGITUDE, "");
                params.put(ApiConfig.DEVICE_TYPE, Constants.GlobalConst.DEVICE_TYPE);
                params.put(ApiConfig.DEVICE_TOKEN, "");
                params.put(ApiConfig.REG_AS_A_PATIENT, stMarital);
                params.put("patient_treament_type", "");
                params.put(ApiConfig.STATUS, "1");
                params.put(ApiConfig.EMAIL, mEdtxtEmail.getText().toString().trim());
                params.put(ApiConfig.PASSWORD, mEdtxtPassword.getText().toString().trim());
                params.put("age", mEdtxtAge.getText().toString().trim());
                params.put("height", mEdtxtHeight.getText().toString().trim());
                params.put("weight", mEdtxtWeight.getText().toString().trim());
                params.put("aadhar_id", aadharid.getText().toString().trim());
                params.put("address2", address.getText().toString().trim());
                params.put("pincode", Pincode.getText().toString().trim());
                params.put("city", city.getText().toString().trim());
                params.put("gender", stGender.trim());
                params.put("food_habit", stFoodhabit.trim());
                params.put("bmi", bmi.trim());
                params.put("ref_source", Referralsource.getText().toString().trim());
                params.put("contact_person", ContactPerson.getText().toString().trim());
                params.put("contact_person_mob", ContactPersonMobile.getText().toString().trim());
//                params.put("conatct_person", ContactPerson.getText().toString().trim());
//                params.put("contact_person_mob", ContactPersonMobile.getText().toString().trim());
//
//                params.put("pincode", Pincode.getText().toString().trim());



                /*

                params.put("gender", stGender.trim());
                params.put("food_habit", stFoodhabit.trim());
                params.put("bmi", bmi.trim());
                params.put("ref_source", Referralsource.getText().toString().trim());
                params.put("conatct_person", ContactPerson.getText().toString().trim());
                params.put("contact_person_mob", ContactPersonMobile.getText().toString().trim());*/


                if (mImgBase64 != null && !mImgBase64.equals("")) {
                    params.put(ApiConfig.PROFILE_PIC, mImgBase64);
                } else {
                    params.put(ApiConfig.PROFILE_PIC, "");
                }

                new UrlConnectionAuthTask(getActivity(), ApiConfig.REGISTER_URL, ApiConfig.ID1, true, params, UserDetailModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getActivity(), "Please select patient register type", Toast.LENGTH_LONG).show();
        }
    }


    private void viewStaff() {
        getFragmentManager().popBackStack();
        StaffDashboardFragment fragment = StaffDashboardFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
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

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    takePictureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(getActivity().getApplicationContext(), "com.capri4physio.fileProvider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

                } else {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, SplashActivity.mImageCaptureUri);

                }

//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, SplashActivity.mImageCaptureUri);
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

        String fname = mEdtxtFname.getText().toString().trim();
        String lname = mEdtxtLname.getText().toString().trim();
        String email = mEdtxtEmail.getText().toString().trim();
        String phone = mEdtxtPhone.getText().toString().trim();
        String pass = mEdtxtPassword.getText().toString().trim();
        String stAge = mEdtxtAge.getText().toString().trim();
        String stheight = mEdtxtHeight.getText().toString().trim();
        String stweight = mEdtxtWeight.getText().toString().trim();


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
            case CALL_PATIENT_ADD_API:
                parseAddPatientResponse(response);
                break;
            case ADD_NEW_PATIENT:
                parseNewPatient(response);
                break;
        }
    }

    public void parseNewPatient(String response) {
        Log.d(TagUtils.getTag(), "new patient response:-" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "New Patient added");
                viewStaff();
            } else {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "Failed to add patient");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Something went wrong");
        }
    }

    public void parseAddPatientResponse(String response) {
        Log.d(TagUtils.getTag(), "register response:-" + response);
        ToastClass.showLongToast(getActivity().getApplicationContext(), "Patient Successfully Added");
        viewStaff();
    }

    @Override
    public void onPostSuccess(Object response, int id) {

        {
            switch (id) {
                case ApiConfig.ID1:
                    BaseModel baseModel = (BaseModel) response;
                    if (baseModel.getStatus() == 1) {
                        AppLog.i("Capri4Physio", "New Staff added Response : " + baseModel.getMessage());
                        Toast.makeText(getActivity(), "Record Successfully Added", Toast.LENGTH_LONG).show();
//                        getActivity().finish();
                        viewStaff();

                    } else {
                        Utils.showMessage(getActivity(), baseModel.getMessage());
                    }

                    break;


                case ApiConfig.ID3:
                    BaseModel baseResponse = (BaseModel) response;
                    Utils.showMessage(getActivity(), baseResponse.getMessage());
                    break;

                default:
                    AppLog.i("Capri4Physio", "UNKNOW RESPONSE - " + id);

            }

        }

    }

    @Override
    public void onPostError(String errMsg, int id) {

    }

}