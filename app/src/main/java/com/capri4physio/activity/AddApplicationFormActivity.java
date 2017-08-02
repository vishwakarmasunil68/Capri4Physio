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
import android.widget.Spinner;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.model.applicationform.ApplicationFormResultPOJO;
import com.capri4physio.model.studentcourse.StudentCourseResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddApplicationFormActivity extends AppCompatActivity implements WebServicesCallBack{
    private static final String ADD_APPLICATION_FORM = "add_application_form";
    private static final String UPDATE_APPLICATION_FORM = "update_application_form";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_course)
    Spinner spinner_course;
    @BindView(R.id.et_first_name)
    EditText et_first_name;
    @BindView(R.id.et_middle_name)
    EditText et_middle_name;
    @BindView(R.id.et_last_name)
    EditText et_last_name;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_mobile)
    EditText et_mobile;
    @BindView(R.id.et_city_date)
    EditText et_city_date;
    @BindView(R.id.et_trans_id)
    EditText et_trans_id;
    @BindView(R.id.et_amount)
    EditText et_amount;
    @BindView(R.id.et_name_of_bank)
    EditText et_name_of_bank;
    @BindView(R.id.et_present_addr)
    EditText et_present_addr;
    @BindView(R.id.et_city)
    EditText et_city;
    @BindView(R.id.et_state)
    EditText et_state;
    @BindView(R.id.et_zip_code)
    EditText et_zip_code;
    @BindView(R.id.et_country)
    EditText et_country;
    @BindView(R.id.et_qualification)
    EditText et_qualification;
    @BindView(R.id.et_last_institute)
    EditText et_last_institute;
    @BindView(R.id.et_clinic_addr)
    EditText et_clinic_addr;
    @BindView(R.id.et_comments)
    EditText et_comments;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    StudentCourseResultPOJO studentCourseResultPOJO;

    String[] spinner_items = { "COMT", "Jenny McConell Concept", "CRASH COURSE"};
    ApplicationFormResultPOJO applicationFormResultPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_application_form);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,spinner_items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_course.setAdapter(aa);

        studentCourseResultPOJO= (StudentCourseResultPOJO) getIntent().getSerializableExtra("studentcourseresultpojo");
        applicationFormResultPOJO= (ApplicationFormResultPOJO) getIntent().getSerializableExtra("applicationform");
        if(studentCourseResultPOJO!=null){
            btn_submit.setText("Update");
        }else{
            btn_submit.setText("Submit");
        }

        if(applicationFormResultPOJO==null){
            btn_submit.setText("Submit");
        }else{
            btn_submit.setText("Update");
            et_first_name.setText(applicationFormResultPOJO.getaFirstName());
            et_middle_name.setText(applicationFormResultPOJO.getaMiddleName());
            et_last_name.setText(applicationFormResultPOJO.getaLastName());
            et_email.setText(applicationFormResultPOJO.getaEmail());
            et_city_date.setText(applicationFormResultPOJO.getaCityDate());
            et_trans_id.setText(applicationFormResultPOJO.getaTranstionid());
            et_amount.setText(applicationFormResultPOJO.getaAmount());
            et_name_of_bank.setText(applicationFormResultPOJO.getaBankName());
            et_present_addr.setText(applicationFormResultPOJO.getaPresentAddress());
            et_city.setText(applicationFormResultPOJO.getaCity());
            et_state.setText(applicationFormResultPOJO.getaState());
            et_zip_code.setText(applicationFormResultPOJO.getaZipCode());
            et_country.setText(applicationFormResultPOJO.getaCountry());
            et_qualification.setText(applicationFormResultPOJO.getaQualification());
            et_clinic_addr.setText(applicationFormResultPOJO.getaClinic());
            et_comments.setText(applicationFormResultPOJO.getaComment());

        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateEdits(et_first_name,et_last_name,et_email,et_mobile,
                        et_city_date,et_trans_id,et_amount,et_name_of_bank,et_present_addr,et_city,
                        et_state,et_zip_code,et_country,et_qualification,et_last_institute)) {

                    if(applicationFormResultPOJO==null){
                        addApplicationForm(true);
                    }else{
                        addApplicationForm(false);
                    }
                }else{
                    ToastClass.showShortToast(getApplicationContext(),"Please fill all required data");
                }
            }
        });
    }

    public void addApplicationForm(boolean add){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("a_c_id", studentCourseResultPOJO.getScCid()));
        nameValuePairs.add(new BasicNameValuePair("a_s_id", studentCourseResultPOJO.getScSid()));
        nameValuePairs.add(new BasicNameValuePair("a_course", spinner_items[spinner_course.getSelectedItemPosition()]));
        nameValuePairs.add(new BasicNameValuePair("a_first_name", et_first_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("a_middle_name", et_middle_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("a_last_name", et_last_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("a_email", et_email.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("a_city_date", et_city_date.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("a_transtionid", et_trans_id.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("a_amount", et_amount.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("a_bank_name", et_name_of_bank.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("a_date", new SimpleDateFormat("dd-MM-yyyy").format(new Date())));
        nameValuePairs.add(new BasicNameValuePair("a_present_address", et_present_addr.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("a_city", et_city.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("a_state", et_state.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("a_zip_code", et_zip_code.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("a_country", et_country.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("a_qualification", et_qualification.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("a_college", et_clinic_addr.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("a_clinic", et_last_institute.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("a_comment", et_comments.getText().toString()));
//        nameValuePairs.add(new BasicNameValuePair("a_photograph", et_first_name.getText().toString()));
//        nameValuePairs.add(new BasicNameValuePair("a_icard", et_first_name.getText().toString()));
        if(add){
            new WebServiceBase(nameValuePairs, this, ADD_APPLICATION_FORM).execute(ApiConfig.add_application_form_api);
        }else{
            nameValuePairs.add(new BasicNameValuePair("a_id", applicationFormResultPOJO.getaId()));
            new WebServiceBase(nameValuePairs, this, UPDATE_APPLICATION_FORM).execute(ApiConfig.update_course_api);
        }

    }


    public boolean ValidateEdits(EditText... editTexts) {
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
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case ADD_APPLICATION_FORM:
                parseApplicationFormdata(response);
                break;
            case UPDATE_APPLICATION_FORM:
                parseUdateApplicationFormData(response);
                break;
        }
    }

    public void parseUdateApplicationFormData(String response){
        Log.d(TagUtils.getTag(),"update form data:-"+response);
    }

    public void parseApplicationFormdata(String response){
        Log.d(TagUtils.getTag(),"application form response:-"+response);
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("Success").equals("true")){
                ToastClass.showShortToast(getApplicationContext(),"Application Form saved successfully");
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result","ok");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }else{
                ToastClass.showShortToast(getApplicationContext(),"Failed to saved Application Form");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Somthing went wrong");
        }
    }
}
