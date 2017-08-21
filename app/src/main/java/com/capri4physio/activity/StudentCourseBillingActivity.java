package com.capri4physio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.capri4physio.R;
import com.capri4physio.Services.GetWebServices;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.adapter.StudentCourseAdapter;
import com.capri4physio.model.cources.CourcesPOJO;
import com.capri4physio.model.cources.CourcesResultPOJO;
import com.capri4physio.model.studentcourse.StudentCoursePOJO;
import com.capri4physio.model.studentcourse.StudentCourseResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StudentCourseBillingActivity extends AppCompatActivity implements WebServicesCallBack{
    private static final String LIST_COURCES_API = "list_course_api";
    private static final String APPLY_COURSE_API = "apply_course_api";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_course)
    RecyclerView rv_course;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_billing);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        callCourseListAPI();
    }
    public void callCourseListAPI() {
        new GetWebServices(StudentCourseBillingActivity.this, LIST_COURCES_API).execute(ApiConfig.list_couces);
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

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case LIST_COURCES_API:
                parseCourseListApi(response);
                break;
            case APPLY_COURSE_API:
                parseApplyCourseApi(response);
                break;
        }
    }


    public void parseApplyCourseApi(String response) {
        Log.d(TagUtils.getTag(),"applied course response:-"+response);
        try {
            Gson gson = new Gson();
            StudentCoursePOJO studentCoursePOJO = gson.fromJson(response, StudentCoursePOJO.class);
            if (studentCoursePOJO.getSuccess().equals("true")) {
                StudentCourseResultPOJO studentCourseResultPOJO = studentCoursePOJO.getStudentCourseResultPOJOList().get(0);
                Intent intent = new Intent(StudentCourseBillingActivity.this, StudentBillActivity.class);
                intent.putExtra("studentcourseresultpojo", studentCourseResultPOJO);
                intent.putExtra("coursepojo", courcesResultPOJO);
                startActivity(intent);
            } else {
                ToastClass.showShortToast(getApplicationContext(),"You have not applied for this course");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
        }
    }

    CourcesResultPOJO courcesResultPOJO;
    public void showStudentBill(CourcesResultPOJO courcesResultPOJO){
        this.courcesResultPOJO=courcesResultPOJO;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("student_id", AppPreferences.getInstance(getApplicationContext()).getUserID()));
        nameValuePairs.add(new BasicNameValuePair("course_id", courcesResultPOJO.getC_id()));
        new WebServiceBase(nameValuePairs, this, APPLY_COURSE_API).execute(ApiConfig.student_course_list_by_id_api);
    }

    public void parseCourseListApi(String response) {
        try {
            Gson gson = new Gson();
            CourcesPOJO courcesPOJO = gson.fromJson(response, CourcesPOJO.class);
            if (courcesPOJO.getSuccess().equals("true")) {
                StudentCourseAdapter courceAdapter = new StudentCourseAdapter(this, courcesPOJO.getCourcesPOJOList(),true);
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_course.setLayoutManager(horizontalLayoutManagaer);
                rv_course.setHasFixedSize(true);
                rv_course.setItemAnimator(new DefaultItemAnimator());
                rv_course.setAdapter(courceAdapter);
            } else {
                ToastClass.showShortToast(getApplicationContext(), "No Courses Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(), "No Courses Found");
        }
    }
}
