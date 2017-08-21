package com.capri4physio.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCourseActivity extends AppCompatActivity implements WebServicesCallBack {
    private static final String LIST_COURCES_API = "list_cources_api";
    private static final String APPLY_COURSE_API = "apply_course_api";
    private static final String APPLY_FOR_COURSE = "apply_for_course";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_course)
    RecyclerView rv_course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        callCourseListAPI();
    }

    public void callCourseListAPI() {
        new GetWebServices(SelectCourseActivity.this, LIST_COURCES_API).execute(ApiConfig.list_couces);
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

    CourcesResultPOJO courseResultPOJO;

    public void checkAppliedCourse(CourcesResultPOJO courcesResultPOJO) {
        this.courseResultPOJO = courcesResultPOJO;
        if (courseResultPOJO != null) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("student_id", AppPreferences.getInstance(getApplicationContext()).getUserID()));
            nameValuePairs.add(new BasicNameValuePair("course_id", courseResultPOJO.getC_id()));
            new WebServiceBase(nameValuePairs, this, APPLY_COURSE_API).execute(ApiConfig.student_course_list_by_id_api);
        } else {
            ToastClass.showShortToast(getApplicationContext(), "something went wrong");
        }
    }

    public void showSelectCourseDialog(final CourcesResultPOJO courcesResultPOJO) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Do you want to apply for course " + courcesResultPOJO.getC_name() + " ?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                callCourseApplyAPI(courcesResultPOJO);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void callCourseApplyAPI(CourcesResultPOJO courcesResultPOJO) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("sc_sid", AppPreferences.getInstance(getApplicationContext()).getUserID()));
        nameValuePairs.add(new BasicNameValuePair("sc_sname", AppPreferences.getInstance(getApplicationContext()).getFirstName() + " " + AppPreferences.getInstance(getApplicationContext()).getLastName()));
        nameValuePairs.add(new BasicNameValuePair("sc_semail", AppPreferences.getInstance(getApplicationContext()).getEmail()));
        nameValuePairs.add(new BasicNameValuePair("sc_sapplicationform_fill", ""));
        nameValuePairs.add(new BasicNameValuePair("sc_photo_upload", ""));
        nameValuePairs.add(new BasicNameValuePair("sc_cerifiato_upload", ""));
        nameValuePairs.add(new BasicNameValuePair("sc_idcard_upload", ""));
        nameValuePairs.add(new BasicNameValuePair("sc_reg_fees", ""));
        nameValuePairs.add(new BasicNameValuePair("sc_rem_fees", ""));
        nameValuePairs.add(new BasicNameValuePair("sc_fullfees", ""));
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy hh:mm a");

        String dates=sdf.format(date).split(" ")[0];
        String time=sdf.format(date).split(" ")[1]+" "+sdf.format(date).split(" ")[2];
        nameValuePairs.add(new BasicNameValuePair("sc_date", dates));
        nameValuePairs.add(new BasicNameValuePair("sc_time", time));
        nameValuePairs.add(new BasicNameValuePair("sc_cid", courcesResultPOJO.getC_id()));
        nameValuePairs.add(new BasicNameValuePair("admin_application_form", "false"));
        nameValuePairs.add(new BasicNameValuePair("admin_photo_upload", "false"));
        nameValuePairs.add(new BasicNameValuePair("admin_certificate_upload", "false"));
        nameValuePairs.add(new BasicNameValuePair("admin_icard", "false"));
        nameValuePairs.add(new BasicNameValuePair("admin_reg_fees", "false"));
        nameValuePairs.add(new BasicNameValuePair("sc_admin_rem_fees", "false"));
        nameValuePairs.add(new BasicNameValuePair("admin_full_fees", "false"));
        nameValuePairs.add(new BasicNameValuePair("admin_status", "Please Fill All Details Properly."));
        new WebServiceBase(nameValuePairs, this, APPLY_FOR_COURSE).execute(ApiConfig.add_student_course);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case LIST_COURCES_API:
                parseCourseListApi(response);
                break;
            case APPLY_COURSE_API:
                parseApplyCourseApi(response);
                break;
            case APPLY_FOR_COURSE:
                parseApplyForCourse(response);
                break;
        }
    }

    public void parseApplyForCourse(String response) {
        Log.d(TagUtils.getTag(), "course apply response:-" + response);
        try {
            Gson gson = new Gson();
            JSONObject jsonObject=new JSONObject(response);
            if (jsonObject.optString("Success").equals("true")) {
                StudentCourseResultPOJO studentCourseResultPOJO = new Gson().fromJson(jsonObject.optString("Result"),StudentCourseResultPOJO.class);
                Intent intent = new Intent(SelectCourseActivity.this, StudentCourseStatusActivity.class);
                intent.putExtra("studentcourseresultpojo", studentCourseResultPOJO);
                intent.putExtra("coursepojo", courseResultPOJO);
                startActivity(intent);

                ToastClass.showShortToast(getApplicationContext(), "You Successfully applied for this course");
            } else {
                ToastClass.showShortToast(getApplicationContext(), "Failed to apply for the course");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(), "Something went wrong");
        }
    }

    public void parseApplyCourseApi(String response) {
        Log.d(TagUtils.getTag(),"applied course response:-"+response);
        try {
            Gson gson = new Gson();
            StudentCoursePOJO studentCoursePOJO = gson.fromJson(response, StudentCoursePOJO.class);
            if (studentCoursePOJO.getSuccess().equals("true")) {
                StudentCourseResultPOJO studentCourseResultPOJO = studentCoursePOJO.getStudentCourseResultPOJOList().get(0);
                Intent intent = new Intent(SelectCourseActivity.this, StudentCourseStatusActivity.class);
                intent.putExtra("studentcourseresultpojo", studentCourseResultPOJO);
                intent.putExtra("coursepojo", courseResultPOJO);
                startActivity(intent);
            } else {
                showSelectCourseDialog(courseResultPOJO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseCourseListApi(String response) {
        try {
            Gson gson = new Gson();
            CourcesPOJO courcesPOJO = gson.fromJson(response, CourcesPOJO.class);
            if (courcesPOJO.getSuccess().equals("true")) {
                StudentCourseAdapter courceAdapter = new StudentCourseAdapter(this, courcesPOJO.getCourcesPOJOList(),false);
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
