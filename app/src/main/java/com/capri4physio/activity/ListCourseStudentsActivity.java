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
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.adapter.StudentAdapter;
import com.capri4physio.model.cources.CourcesResultPOJO;
import com.capri4physio.model.studentcourse.StudentCoursePOJO;
import com.capri4physio.model.studentcourse.StudentCourseResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListCourseStudentsActivity extends AppCompatActivity implements WebServicesCallBack{
    private static final String GET_ALL_STUDENTS = "get_all_students";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_students)
    RecyclerView rv_students;

    CourcesResultPOJO courcesResultPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_course_students);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        courcesResultPOJO= (CourcesResultPOJO) getIntent().getSerializableExtra("cource");

        if(courcesResultPOJO!=null){
            getAllStudents();
        }else{
            finish();
        }
    }


    public void ViewStudentDetails(StudentCourseResultPOJO studentCourseResultPOJO){
        Intent intent=new Intent(this,StudentCourseViewActivity.class);
        intent.putExtra("studentcourse",studentCourseResultPOJO);
        intent.putExtra("coursepojo",courcesResultPOJO);
        startActivity(intent);
    }

    public void getAllStudents(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("course_id", courcesResultPOJO.getC_id()));
        new WebServiceBase(nameValuePairs, this, GET_ALL_STUDENTS).execute(ApiConfig.get_student_by_course_id);
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
            case GET_ALL_STUDENTS:
                parseGetAllStudents(response);
                break;
        }
    }

    public void parseGetAllStudents(String response){
        Log.d(TagUtils.getTag(),"student response:-"+response);
        try{
            Gson gson=new Gson();
            StudentCoursePOJO studentCoursePOJO=gson.fromJson(response,StudentCoursePOJO.class);
            if(studentCoursePOJO.getSuccess().equals("true")){
                List<StudentCourseResultPOJO> studentCourseResultPOJOList=studentCoursePOJO.getStudentCourseResultPOJOList();
                StudentAdapter courceAdapter = new StudentAdapter(this, studentCourseResultPOJOList);
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_students.setLayoutManager(horizontalLayoutManagaer);
                rv_students.setHasFixedSize(true);
                rv_students.setItemAnimator(new DefaultItemAnimator());
                rv_students.setAdapter(courceAdapter);
            }else{
                ToastClass.showShortToast(getApplicationContext(),"No Student Found");
                finish();
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"No Student Found");
            finish();
        }
    }
}
