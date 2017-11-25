package com.capri4physio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.capri4physio.R;
import com.capri4physio.Services.GetWebServices;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.adapter.CourceAdapter;
import com.capri4physio.model.cources.CourcesPOJO;
import com.capri4physio.model.cources.CourcesResultPOJO;
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

public class CourseActivity extends AppCompatActivity implements WebServicesCallBack{

    private static final String CALL_DELETE_COURSE = "call_delete_course_api";
    private final String LIST_COURCES_API="list_cources_api";


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_course)
    RecyclerView rv_course;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        callCourseListAPI();
    }

    public void callCourseListAPI(){
        new GetWebServices(CourseActivity.this, LIST_COURCES_API).execute(ApiConfig.list_couces);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_add_course:
                startActivity(new Intent(this,AddNewCourseActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void callCourseDeleteAPI(CourcesResultPOJO courcesResultPOJO){
        ArrayList<NameValuePair> nameValuePairArrayList=new ArrayList<>();
        nameValuePairArrayList.add(new BasicNameValuePair("c_id",courcesResultPOJO.getC_id()));
        new WebServiceBase(nameValuePairArrayList,this,CALL_DELETE_COURSE).execute(ApiConfig.DELETE_COURSE_API);
    }
    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case LIST_COURCES_API:
                parseCourcseList(response);
                break;
            case CALL_DELETE_COURSE:
                parseDeleteCourse(response);
        }
    }

    public void parseDeleteCourse(String response){
        Log.d(TagUtils.getTag(),"course delete:-"+response);
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("success").equals("true")){
                ToastClass.showLongToast(getApplicationContext(),"Course Deleted Successfully");
                callCourseListAPI();
            }else{
                ToastClass.showLongToast(getApplicationContext(),"Course Deletion Failed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void parseCourcseList(String response){
        try{
            Gson gson=new Gson();
            CourcesPOJO courcesPOJO=gson.fromJson(response,CourcesPOJO.class);
            if(courcesPOJO.getSuccess().equals("true")){
                CourceAdapter courceAdapter = new CourceAdapter(this, courcesPOJO.getCourcesPOJOList());
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_course.setLayoutManager(horizontalLayoutManagaer);
                rv_course.setHasFixedSize(true);
                rv_course.setItemAnimator(new DefaultItemAnimator());
                rv_course.setAdapter(courceAdapter);
            }else{
                ToastClass.showShortToast(getApplicationContext(),"No Courses Found");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"No Courses Found");
        }
    }
}
