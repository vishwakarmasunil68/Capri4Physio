package com.capri4physio.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.model.cources.CourcesResultPOJO;
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

public class StudentCourseViewActivity extends AppCompatActivity implements View.OnClickListener, WebServicesCallBack {
    private static final String UPDATE_STUDENT = "update_student";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.et_s_name)
    EditText et_s_name;
    @BindView(R.id.et_s_email)
    EditText et_s_email;
    @BindView(R.id.iv_application_status)
    ImageView iv_application_status;
    @BindView(R.id.iv_photo_upload)
    ImageView iv_photo_upload;
    @BindView(R.id.iv_id_card_upload)
    ImageView iv_id_card_upload;
    @BindView(R.id.iv_registration_fees)
    ImageView iv_registration_fees;
    @BindView(R.id.iv_full_fees)
    ImageView iv_full_fees;
    @BindView(R.id.iv_certificatie_upload)
    ImageView iv_certificatie_upload;

    @BindView(R.id.iv_confirm_app)
    ImageView iv_confirm_app;
    @BindView(R.id.iv_confirm_photo)
    ImageView iv_confirm_photo;
    @BindView(R.id.iv_confirm_cert)
    ImageView iv_confirm_cert;
    @BindView(R.id.iv_confirm_id)
    ImageView iv_confirm_id;
    @BindView(R.id.iv_confirm_reg)
    ImageView iv_confirm_reg;
    @BindView(R.id.iv_confirm_full)
    ImageView iv_confirm_full;
    @BindView(R.id.et_remark)
    EditText et_remark;

    StudentCourseResultPOJO studentCourseResultPOJO;
    CourcesResultPOJO courcesResultPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_view);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        studentCourseResultPOJO = (StudentCourseResultPOJO) getIntent().getSerializableExtra("studentcourse");
        courcesResultPOJO = (CourcesResultPOJO) getIntent().getSerializableExtra("coursepojo");

        if (studentCourseResultPOJO != null && courcesResultPOJO != null) {
            checkStatus();
        } else {
            finish();
        }

        iv_confirm_app.setOnClickListener(this);
        iv_confirm_photo.setOnClickListener(this);
        iv_confirm_cert.setOnClickListener(this);
        iv_confirm_id.setOnClickListener(this);
        iv_confirm_reg.setOnClickListener(this);
        iv_confirm_full.setOnClickListener(this);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_remark.getText().toString().length()>0){
                    studentCourseResultPOJO.setAdminStatus(et_remark.getText().toString());
                    updateStudent();
                }
                else{
                    ToastClass.showShortToast(getApplicationContext(),"Please write some remark");
                }
            }
        });
    }

    public void checkStatus() {
        if (studentCourseResultPOJO != null) {
            et_s_name.setText(studentCourseResultPOJO.getScSname());
            et_s_email.setText(studentCourseResultPOJO.getScSemail());
            if (studentCourseResultPOJO.getScSapplicationformFill().length() == 0) {
                iv_application_status.setImageResource(R.drawable.ic_not_filled);
                iv_confirm_app.setVisibility(View.GONE);
            } else {
                if (studentCourseResultPOJO.getAdminApplicationForm().equals("false")) {
                    iv_application_status.setImageResource(R.drawable.ic_filled);
                    iv_confirm_app.setVisibility(View.VISIBLE);
                } else {
                    iv_application_status.setImageResource(R.drawable.ic_approved);
                    iv_confirm_app.setVisibility(View.GONE);
                }
            }

            if (studentCourseResultPOJO.getScPhotoUpload().length() == 0) {
                iv_photo_upload.setImageResource(R.drawable.ic_not_filled);
                iv_confirm_photo.setVisibility(View.GONE);
            } else {
                if (studentCourseResultPOJO.getAdminPhotoUpload().equals("false")) {
                    iv_photo_upload.setImageResource(R.drawable.ic_filled);
                    iv_confirm_photo.setVisibility(View.VISIBLE);
                } else {
                    iv_photo_upload.setImageResource(R.drawable.ic_approved);
                    iv_confirm_photo.setVisibility(View.GONE);
                }
            }

            if (studentCourseResultPOJO.getScCerifiatoUpload().length() == 0) {
                iv_certificatie_upload.setImageResource(R.drawable.ic_not_filled);
                iv_confirm_cert.setVisibility(View.GONE);
            } else {
                if (studentCourseResultPOJO.getAdminCertificateUpload().equals("false")) {
                    iv_certificatie_upload.setImageResource(R.drawable.ic_filled);
                    iv_confirm_cert.setVisibility(View.VISIBLE);
                } else {
                    iv_certificatie_upload.setImageResource(R.drawable.ic_approved);
                    iv_confirm_cert.setVisibility(View.GONE);
                }
            }

            if (studentCourseResultPOJO.getScIdcardUpload().length() == 0) {
                iv_id_card_upload.setImageResource(R.drawable.ic_not_filled);
                iv_confirm_id.setVisibility(View.GONE);
            } else {
                if (studentCourseResultPOJO.getAdminIcard().equals("false")) {
                    iv_id_card_upload.setImageResource(R.drawable.ic_filled);
                    iv_confirm_id.setVisibility(View.VISIBLE);
                } else {
                    iv_id_card_upload.setImageResource(R.drawable.ic_approved);
                    iv_confirm_id.setVisibility(View.GONE);
                }
            }

            if (studentCourseResultPOJO.getScRegFees().length() == 0) {
                iv_registration_fees.setImageResource(R.drawable.ic_not_filled);
                iv_confirm_reg.setVisibility(View.GONE);
            } else {
                if (studentCourseResultPOJO.getAdminRegFees().equals("false")) {
                    iv_registration_fees.setImageResource(R.drawable.ic_filled);
                    iv_confirm_reg.setVisibility(View.VISIBLE);
                } else {
                    iv_registration_fees.setImageResource(R.drawable.ic_approved);
                    iv_confirm_reg.setVisibility(View.GONE);
                }
            }

            if (studentCourseResultPOJO.getScFullfees().length() == 0) {
                iv_full_fees.setImageResource(R.drawable.ic_not_filled);
                iv_confirm_full.setVisibility(View.GONE);
            } else {
                if (studentCourseResultPOJO.getAdminFullFees().equals("false")) {
                    iv_full_fees.setImageResource(R.drawable.ic_filled);
                    iv_confirm_full.setVisibility(View.VISIBLE);
                } else {
                    iv_full_fees.setImageResource(R.drawable.ic_approved);
                    iv_confirm_full.setVisibility(View.GONE);
                }
            }
        } else {
            finish();
        }
    }

    public void setListenerNull() {

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

    public void updateStudent() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("sc_sid", studentCourseResultPOJO.getScSid()));
        nameValuePairs.add(new BasicNameValuePair("sc_sname", studentCourseResultPOJO.getScSname()));
        nameValuePairs.add(new BasicNameValuePair("sc_semail", studentCourseResultPOJO.getScSemail()));
        nameValuePairs.add(new BasicNameValuePair("sc_sapplicationform_fill", studentCourseResultPOJO.getScSapplicationformFill()));
        nameValuePairs.add(new BasicNameValuePair("sc_photo_upload", studentCourseResultPOJO.getScPhotoUpload()));
        nameValuePairs.add(new BasicNameValuePair("sc_cerifiato_upload", studentCourseResultPOJO.getScCerifiatoUpload()));
        nameValuePairs.add(new BasicNameValuePair("sc_idcard_upload", studentCourseResultPOJO.getScIdcardUpload()));
        nameValuePairs.add(new BasicNameValuePair("sc_reg_fees", studentCourseResultPOJO.getScRegFees()));
        nameValuePairs.add(new BasicNameValuePair("sc_fullfees", studentCourseResultPOJO.getScFullfees()));
        nameValuePairs.add(new BasicNameValuePair("sc_cid", studentCourseResultPOJO.getScCid()));
        nameValuePairs.add(new BasicNameValuePair("sc_id", studentCourseResultPOJO.getScId()));
        nameValuePairs.add(new BasicNameValuePair("sc_date", studentCourseResultPOJO.getSc_date()));
        nameValuePairs.add(new BasicNameValuePair("sc_time", studentCourseResultPOJO.getSc_time()));
        nameValuePairs.add(new BasicNameValuePair("admin_application_form", studentCourseResultPOJO.getAdminApplicationForm()));
        nameValuePairs.add(new BasicNameValuePair("admin_photo_upload", studentCourseResultPOJO.getAdminPhotoUpload()));
        nameValuePairs.add(new BasicNameValuePair("admin_certificate_upload", studentCourseResultPOJO.getAdminCertificateUpload()));
        nameValuePairs.add(new BasicNameValuePair("admin_icard", studentCourseResultPOJO.getAdminIcard()));
        nameValuePairs.add(new BasicNameValuePair("admin_status", studentCourseResultPOJO.getAdminStatus()));
        nameValuePairs.add(new BasicNameValuePair("admin_reg_fees", studentCourseResultPOJO.getAdminRegFees()));
        nameValuePairs.add(new BasicNameValuePair("admin_full_fees", studentCourseResultPOJO.getAdminFullFees()));
        new WebServiceBase(nameValuePairs, this, UPDATE_STUDENT).execute(ApiConfig.update_student_course);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_confirm_app:
                studentCourseResultPOJO.setAdminApplicationForm("true");
                updateStudent();
                break;
            case R.id.iv_confirm_photo:
                studentCourseResultPOJO.setAdminPhotoUpload("true");
                updateStudent();
                break;
            case R.id.iv_confirm_cert:
                studentCourseResultPOJO.setAdminCertificateUpload("true");
                updateStudent();
                break;
            case R.id.iv_confirm_id:
                studentCourseResultPOJO.setAdminIcard("true");
                updateStudent();
                break;
            case R.id.iv_confirm_reg:
                studentCourseResultPOJO.setAdminRegFees("true");
                updateStudent();
                break;
            case R.id.iv_confirm_full:
                studentCourseResultPOJO.setAdminFullFees("true");
                updateStudent();
                break;
        }
    }


    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case UPDATE_STUDENT:
                parseUpdateStudent(response);
                break;
        }
    }

    public void parseUpdateStudent(String response) {
        Log.d(TagUtils.getTag(), "update response:-" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("Success").equals("true")) {
                Gson gson = new Gson();
                studentCourseResultPOJO = gson.fromJson(jsonObject.optJSONObject("Result").toString(), StudentCourseResultPOJO.class);
                checkStatus();
                ToastClass.showShortToast(getApplicationContext(), "Updated Successfully");
            } else {
                ToastClass.showShortToast(getApplicationContext(), "Updation Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(), "Something went wrong");
        }
    }
}
