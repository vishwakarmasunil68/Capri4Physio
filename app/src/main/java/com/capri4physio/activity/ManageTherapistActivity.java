package com.capri4physio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.capri4physio.R;
import com.capri4physio.Services.GetWebServices;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.adapter.DoctorAdapter;
import com.capri4physio.model.branch.BranchPOJO;
import com.capri4physio.model.doctor.DoctorPOJO;
import com.capri4physio.model.doctor.DoctorResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManageTherapistActivity extends AppCompatActivity implements WebServicesCallBack{
    private static final String GET_ALL_BRANCHES = "get_all_branches";
    private static final String GET_DOCTORS_API = "get_doctors_api";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    @BindView(R.id.rv_therapist)
    RecyclerView rv_therapist;

    List<BranchPOJO> branchPOJOList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_therapist);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        new GetWebServices(ManageTherapistActivity.this, GET_ALL_BRANCHES).execute(ApiConfig.GetURL);

//        DatabaseReference root= FirebaseDatabase.getInstance().getReference().getRoot();
//        root.child("therapist").child("143").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
////                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
////                    Log.e(TagUtils.getTag(), "======="+postSnapshot.child("email").getValue());
////                    Log.e(TagUtils.getTag(), "======="+postSnapshot.child("name").getValue());
////                }
//                Log.d(TagUtils.getTag(),"email:-"+dataSnapshot.child("email").getValue());
//                Log.d(TagUtils.getTag(),"name:-"+dataSnapshot.child("name").getValue());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.e(TagUtils.getTag(), "Failed to read app title value.", databaseError.toException());
//            }
//        });
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
            case GET_ALL_BRANCHES:
                parseGetBranch(response);
                break;
            case GET_DOCTORS_API:
                parseGetDoctorsResponse(response);
                break;
        }
    }
    List<DoctorResultPOJO> doctorResultPOJOList = new ArrayList<>();
    DoctorAdapter adapter;
    public void parseGetDoctorsResponse(String response){
        Log.d(TagUtils.getTag(), "get doctor response:-" + response);
        try {
            Gson gson = new Gson();
            DoctorPOJO doctorPOJO = gson.fromJson(response, DoctorPOJO.class);
            if (doctorPOJO.getSuccess().equals("true")) {
                doctorResultPOJOList.clear();
                List<String> doctorStringList = new ArrayList<>();
                doctorResultPOJOList.addAll(doctorPOJO.getDoctorResultPOJOList());
                for (DoctorResultPOJO doctorResultPOJO : doctorResultPOJOList) {
                    doctorStringList.add(doctorResultPOJO.getFirstName() + " " + doctorResultPOJO.getLastName());
                }

                adapter = new DoctorAdapter(this, doctorResultPOJOList);
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_therapist.setLayoutManager(horizontalLayoutManagaer);
                rv_therapist.setHasFixedSize(true);
                rv_therapist.setItemAnimator(new DefaultItemAnimator());
                rv_therapist.setAdapter(adapter);


            } else {
                doctorResultPOJOList.clear();
                
                adapter = new DoctorAdapter(this, doctorResultPOJOList);
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_therapist.setLayoutManager(horizontalLayoutManagaer);
                rv_therapist.setHasFixedSize(true);
                rv_therapist.setItemAnimator(new DefaultItemAnimator());
                rv_therapist.setAdapter(adapter);
                ToastClass.showShortToast(getApplicationContext(), "No Doctor Found. Please Select another branch");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTherapist(DoctorResultPOJO doctorResultPOJO){
        Intent intent=new Intent(this,AddTherapistActivity.class);
        intent.putExtra("doctor",doctorResultPOJO);
        startActivityForResult(intent, 101);
    }

    public void deleteTherapist(DoctorResultPOJO doctorResultPOJO){

    }

    public void parseGetBranch(String response){
        Log.d(TagUtils.getTag(),"response:-"+response);
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
                    getApplicationContext(), R.layout.dropsimpledown, braStringList);
            spinner_branch.setAdapter(spinnerArrayAdapter);

            if(branchPOJOList.size()>0){
                getTherapists(branchPOJOList.get(0));
            }

            spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    getTherapists(branchPOJOList.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTherapists(BranchPOJO branchPOJO){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("bracch_code", branchPOJO.getBranch_code()));
        new WebServiceBase(nameValuePairs, this, GET_DOCTORS_API).execute(ApiConfig.get_branch_doctor);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                try {
                    getTherapists(branchPOJOList.get(spinner_branch.getSelectedItemPosition()));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
