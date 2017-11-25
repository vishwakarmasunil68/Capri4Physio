package com.capri4physio.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.model.applicationform.ApplicationFormResultPOJO;
import com.capri4physio.model.studentcourse.StudentCourseResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentCourseApplicationActivity extends AppCompatActivity implements WebServicesCallBack{
    private static final String GET_APPLICATION_FORM_DATA = "get_application_form_data";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_course)
    EditText et_course;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_application);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        studentCourseResultPOJO= (StudentCourseResultPOJO) getIntent().getSerializableExtra("studentcourseresultpojo");
        callApplicationAPI();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void callApplicationAPI(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("a_id", studentCourseResultPOJO.getScSapplicationformFill()));
        new WebServiceBase(nameValuePairs, this, GET_APPLICATION_FORM_DATA).execute(ApiConfig.get_application_form_api2);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setViews(ApplicationFormResultPOJO applicationFormResultPOJO){
        et_course.setText(applicationFormResultPOJO.getaCourse());
        et_first_name.setText(applicationFormResultPOJO.getaFirstName());
        et_middle_name.setText(applicationFormResultPOJO.getaMiddleName());
        et_last_name.setText(applicationFormResultPOJO.getaLastName());
        et_email.setText(applicationFormResultPOJO.getaEmail());
        et_mobile.setText(applicationFormResultPOJO.getaMobile());
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
        et_last_institute.setText(applicationFormResultPOJO.getaCollege());
        et_clinic_addr.setText(applicationFormResultPOJO.getaClinic());
        et_comments.setText(applicationFormResultPOJO.getaComment());

    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case GET_APPLICATION_FORM_DATA:
                parseApplicationFormData(response);
                break;
        }
    }

    public void parseApplicationFormData(String response){
        Log.d(TagUtils.getTag(),"applicationformresponse:-"+response);
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("success").equals("true")){
                Gson gson=new Gson();
                ApplicationFormResultPOJO applicationFormResultPOJO=gson.fromJson(jsonObject.optJSONArray("result").optJSONObject(0).toString(),ApplicationFormResultPOJO.class);
                setViews(applicationFormResultPOJO);
            }else{
                ToastClass.showShortToast(getApplicationContext(),"No Application Form data found");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
        }
    }
}
