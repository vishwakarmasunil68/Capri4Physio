package com.capri4physio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.capri4physio.R;
import com.capri4physio.Services.GetWebServices;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.model.branch.BranchPOJO;
import com.capri4physio.model.doctor.DoctorResultPOJO;
import com.capri4physio.model.therapist.TherapistPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddTherapistActivity extends AppCompatActivity implements WebServicesCallBack, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String ADD_THERAPIST_API = "add_therapist_api";
    private static final String GET_ALL_BRANCHES = "get_all_branches";
    private static final String UPDATE_THERAPIST = "update_therapist";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    @BindView(R.id.et_first_name)
    EditText et_first_name;
    @BindView(R.id.et_last_name)
    EditText et_last_name;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_age)
    EditText et_age;
    @BindView(R.id.et_dob)
    EditText et_dob;
    @BindView(R.id.et_date_of_joining)
    EditText et_date_of_joining;
    @BindView(R.id.et_date_of_contract)
    EditText et_date_of_contract;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_city)
    EditText et_city;
    @BindView(R.id.et_pin_code)
    EditText et_pin_code;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_degree)
    EditText et_degree;
    @BindView(R.id.et_institute)
    EditText et_institute;
    @BindView(R.id.et_university)
    EditText et_university;
    @BindView(R.id.et_duration)
    EditText et_duration;
    @BindView(R.id.et_average)
    EditText et_average;
    @BindView(R.id.et_organization)
    EditText et_organization;
    @BindView(R.id.et_designation)
    EditText et_designation;
    @BindView(R.id.et_experience_duration)
    EditText et_experience_duration;
    @BindView(R.id.et_work)
    EditText et_work;
    @BindView(R.id.et_height)
    EditText et_height;
    @BindView(R.id.et_weight)
    EditText et_weight;
    @BindView(R.id.et_to_time)
    EditText et_to_time;
    @BindView(R.id.et_from_time)
    EditText et_from_time;


    @BindView(R.id.rg_gender)
    RadioGroup rg_gender;
    @BindView(R.id.rg_male)
    RadioButton rg_male;
    @BindView(R.id.rg_female)
    RadioButton rg_female;


    @BindView(R.id.rg_martial)
    RadioGroup rg_martial;
    @BindView(R.id.rb_single)
    RadioButton rb_single;
    @BindView(R.id.rb_married)
    RadioButton rb_married;

    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.btn_from_select)
    Button btn_from_select;
    @BindView(R.id.btn_to_time)
    Button btn_to_time;


    List<BranchPOJO> branchPOJOList = new ArrayList<>();

    boolean from_select = true;

    DoctorResultPOJO doctorResultPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_therapist);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        doctorResultPOJO = (DoctorResultPOJO) getIntent().getSerializableExtra("doctor");


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEditValidation(et_first_name, et_last_name, et_email, et_password, et_age, et_dob, et_date_of_joining, et_date_of_contract, et_address, et_city, et_pin_code, et_degree, et_institute, et_university, et_average, et_organization, et_designation, et_experience_duration, et_height, et_weight, et_to_time, et_from_time)) {
                    callAddTherapistAPI();
                } else {
                    ToastClass.showShortToast(getApplicationContext(), "Please fill all fields properly");
                }
            }
        });

        btn_from_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from_select = true;
                TimePickerDialog tpd = TimePickerDialog
                        .newInstance(AddTherapistActivity.this, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);

                tpd.show(getFragmentManager(), "From Time Picker");
            }
        });

        btn_to_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from_select = false;
                TimePickerDialog tpd = TimePickerDialog
                        .newInstance(AddTherapistActivity.this, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);

                tpd.show(getFragmentManager(), "To Time Picker");
            }
        });

        new GetWebServices(AddTherapistActivity.this, GET_ALL_BRANCHES).execute(ApiConfig.GetURL);
    }


    public void callAddTherapistAPI() {
        String gender = "";
        if (rg_gender.getCheckedRadioButtonId() == R.id.rg_male) {
            gender = "male";
        } else {
            gender = "female";
        }

        String martial = "";
        if (rg_martial.getCheckedRadioButtonId() == R.id.rb_married) {
            martial = "married";
        } else {
            martial = "single";
        }
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("first_name", et_first_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("last_name", et_last_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("mobile", et_phone.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("email", et_email.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("password", et_password.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("address", et_address.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("user_type", "2"));
        nameValuePairs.add(new BasicNameValuePair("device_type", "android"));
        if (doctorResultPOJO != null) {
            nameValuePairs.add(new BasicNameValuePair("device_token", doctorResultPOJO.getDeviceToken().toString()));
        } else {
            nameValuePairs.add(new BasicNameValuePair("device_token", ""));
        }
        nameValuePairs.add(new BasicNameValuePair("show_password", et_password.getText().toString()));
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        nameValuePairs.add(new BasicNameValuePair("created", sdf.format(d)));
        nameValuePairs.add(new BasicNameValuePair("modified", sdf.format(d)));
        nameValuePairs.add(new BasicNameValuePair("gender", gender));
        nameValuePairs.add(new BasicNameValuePair("age", et_age.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("marital_status", martial));
        nameValuePairs.add(new BasicNameValuePair("height", et_height.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("weight", et_weight.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("aadhar_id", ""));
        nameValuePairs.add(new BasicNameValuePair("city", et_city.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("pincode", et_pin_code.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("treatment_type", "OPD"));
        nameValuePairs.add(new BasicNameValuePair("bracch_code", branchPOJOList.get(spinner_branch.getSelectedItemPosition()).getBranch_code()));
        nameValuePairs.add(new BasicNameValuePair("designation", et_designation.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("qualifation", et_degree.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("exprience", et_experience_duration.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("end_date_contract", et_date_of_contract.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("university", et_university.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("from_time", et_from_time.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("to_time", et_to_time.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("treatment_type", ""));
        nameValuePairs.add(new BasicNameValuePair("dob", et_dob.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("datejoing", et_date_of_joining.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("degree", et_degree.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("institution", et_institute.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("average", et_average.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("organisation", et_organization.getText().toString()));
        if (doctorResultPOJO != null) {
            nameValuePairs.add(new BasicNameValuePair("user_id", doctorResultPOJO.getId()));
            new WebServiceBase(nameValuePairs, this, UPDATE_THERAPIST).execute(ApiConfig.update_therapist_api);
        } else {
            new WebServiceBase(nameValuePairs, this, ADD_THERAPIST_API).execute(ApiConfig.add_therapist_api);
        }
    }


    public boolean checkEditValidation(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (editText.getText().toString().length() == 0) {
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case ADD_THERAPIST_API:
                parseAddTherapistApi(response);
                break;
            case GET_ALL_BRANCHES:
                parseGetBranch(response);
                break;
            case UPDATE_THERAPIST:
                parseUpdateTherapist(response);
                break;
        }
    }

    public void parseUpdateTherapist(String response){
        Log.d(TagUtils.getTag(),"update therapist response:-"+response);
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("Success").equals("true")){
                ToastClass.showShortToast(getApplicationContext(),"Therapist Updated Successfully");
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result","ok");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }else{
                ToastClass.showShortToast(getApplicationContext(),"Updation Failed");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
        }

    }

    List<String> braStringList = new ArrayList<>();
    List<String> branchCodeList = new ArrayList<>();

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
            branchCodeList.clear();
            braStringList.clear();
            for (BranchPOJO branchPOJO : branchPOJOList) {
                branchCodeList.add(branchPOJO.getBranch_code());
                braStringList.add(branchPOJO.getBranch_name() + " (" + branchPOJO.getBranch_code() + ")");
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.dropsimpledown, braStringList);
            spinner_branch.setAdapter(spinnerArrayAdapter);


            if (doctorResultPOJO != null) {
                checkValues(doctorResultPOJO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkValues(DoctorResultPOJO doctorResultPOJO) {

        if (branchCodeList.contains(doctorResultPOJO.getBracchCode())) {
            int index = branchCodeList.indexOf(doctorResultPOJO.getBracchCode());
            spinner_branch.setSelection(index);
        }

        et_first_name.setText(doctorResultPOJO.getFirstName());
        et_last_name.setText(doctorResultPOJO.getLastName());
        et_email.setText(doctorResultPOJO.getEmail());
        et_password.setText(doctorResultPOJO.getPassword());
        et_age.setText(doctorResultPOJO.getAge());
        et_dob.setText(doctorResultPOJO.getDob());
        et_date_of_joining.setText(doctorResultPOJO.getDatejoing());
        et_date_of_contract.setText(doctorResultPOJO.getEndDateContract());
        et_address.setText(doctorResultPOJO.getAddress());
        et_city.setText(doctorResultPOJO.getCity());
        et_pin_code.setText(doctorResultPOJO.getPincode());
        et_phone.setText(doctorResultPOJO.getPhone());
        et_degree.setText(doctorResultPOJO.getDegree());
        et_institute.setText(doctorResultPOJO.getInstitution());
        et_university.setText(doctorResultPOJO.getUniversity());
        et_duration.setText(doctorResultPOJO.getDuration());
        et_average.setText(doctorResultPOJO.getAverage());
        et_organization.setText(doctorResultPOJO.getOrganisation());
        et_designation.setText(doctorResultPOJO.getDesignation());
        et_experience_duration.setText(doctorResultPOJO.getExprience());
        et_work.setText(doctorResultPOJO.getNatureOfWork());
        et_height.setText(doctorResultPOJO.getHeight().toString());
        et_weight.setText(doctorResultPOJO.getWeight().toString());
        et_to_time.setText(doctorResultPOJO.getToTime());
        et_from_time.setText(doctorResultPOJO.getFromTime());


        if (doctorResultPOJO.getGender().equalsIgnoreCase("male")) {
            rg_male.setChecked(true);
        } else {
            rg_female.setChecked(true);
        }

        if (doctorResultPOJO.getMaritalStatus().toString().equalsIgnoreCase("single")) {
            rb_single.setChecked(true);
        } else {
            rb_married.setChecked(true);
        }


        btn_save.setText("Update");
    }
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    public void parseAddTherapistApi(String response) {
        Log.d(TagUtils.getTag(), "response:-" + response);
        try {
            JSONObject jsonObject=new JSONObject(response);
            if (jsonObject.optString("Success").equals("true")) {
                ToastClass.showShortToast(getApplicationContext(), "Therapist Added Successfully");
                JSONObject resultObj=jsonObject.optJSONObject("Result");
                String id=resultObj.optString("id");
                String name=resultObj.optString("first_name")+" "+resultObj.optString("last_name");
                String email=resultObj.optString("email");
                String address=resultObj.optString("address");
                String device_token=resultObj.optString("device_token");
                String branch=resultObj.optString("bracch_code");

                TherapistPOJO therapistPOJO=new TherapistPOJO(id,name,email,address,device_token,branch);
                root.child("therapist").child(therapistPOJO.getId()).setValue(therapistPOJO);

                finish();
            } else {
                ToastClass.showShortToast(getApplicationContext(), "Failed to Add Therapist");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(), "Something went wrong");
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "";
        if ((monthOfYear + 1) > 9) {
            date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        } else {
            date = dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year;
        }

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hour = "";
        if (hourOfDay < 10) {
            hour = "0" + hourOfDay;
        } else {
            hour = String.valueOf(hourOfDay);
        }

        String minutes = "";

        if (minute < 10) {
            minutes = "0" + minute;
        } else {
            minutes = String.valueOf(minute);
        }

        String time = hour + ":" + minutes;
        Log.d(TagUtils.getTag(), "time selected:-" + time);
        if (from_select) {
            et_from_time.setText(time);
        } else {
            et_to_time.setText(time);
        }
    }
}
