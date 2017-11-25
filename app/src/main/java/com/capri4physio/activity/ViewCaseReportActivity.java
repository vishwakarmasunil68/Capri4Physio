package com.capri4physio.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.capri4physio.R;
import com.capri4physio.Services.GetWebServices;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.adapter.PatientAdapter;
import com.capri4physio.model.branch.BranchPOJO;
import com.capri4physio.model.patient.PatientPOJO;
import com.capri4physio.model.patient.PatientResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
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

public class ViewCaseReportActivity extends AppCompatActivity implements WebServicesCallBack{
    private static final String GET_ALL_PATIENTS_API = "get_all_patients_api";
    private static final String CALL_SEARCH_PATIENTS = "call_search_patients";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_patients)
    RecyclerView rv_patients;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    @BindView(R.id.ll_branch)
    LinearLayout ll_branch;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;

    List<BranchPOJO> branchPOJOList = new ArrayList<>();
    private static final String GET_ALL_BRANCHES = "get_all_branches";
    String branch_code="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_case_report);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Add Patient Amount");


        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")) {
            new GetWebServices(this, GET_ALL_BRANCHES).execute(ApiConfig.GetURL);
        } else {
            ll_branch.setVisibility(View.GONE);
            branch_code = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
            getAllPatients(branch_code);
        }

        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_name.setText("");
            }
        });

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(et_name.getText().toString().length()>0){
                    callSearchPatientAPI(et_name.getText().toString());
                }else{
                    if(mainpatientarray.size()>0) {
                        setPatientAdapter(mainpatientarray);
                    }else{
                        setPatientAdapter(new ArrayList<PatientResultPOJO>());
                    }
                }
            }
        });
    }

    public void callSearchPatientAPI(String key){
        ArrayList<NameValuePair> nameValuePairArrayList=new ArrayList<>();
        nameValuePairArrayList.add(new BasicNameValuePair("key",key));
        nameValuePairArrayList.add(new BasicNameValuePair("branch_code",branch_code));
        new WebServiceBase(nameValuePairArrayList,this,CALL_SEARCH_PATIENTS,false).execute(ApiConfig.SEARCH_PATIENTS);

    }

    public void getAllPatients(String branch_code) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("bracch_code", branch_code));
        new WebServiceBase(nameValuePairs, this, GET_ALL_PATIENTS_API).execute(ApiConfig.GET_PATIENTS_BY_BRANCH_CODE);
    }

    PatientResultPOJO patientResultPOJO;

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GET_ALL_PATIENTS_API:
                parseGetAllPatientsAPI(response);
                break;
            case GET_ALL_BRANCHES:
                parseAllBranches(response);
                break;
            case CALL_SEARCH_PATIENTS:
                parseSearchPatients(response);
                break;
        }
    }
    public void parseSearchPatients(String response){
        Log.d(TagUtils.getTag(),"response:-"+response);
        try{
            Gson gson = new Gson();
            PatientPOJO patientPOJO = gson.fromJson(response, PatientPOJO.class);
            if (patientPOJO.getSuccess().equals("true")) {
                setPatientAdapter(patientPOJO.getPatientResultPOJOs());
            }else{
                setPatientAdapter(new ArrayList<PatientResultPOJO>());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void parseAllBranches(String response) {
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
            List<String> braStringList = new ArrayList<>();
            for (BranchPOJO branchPOJO : branchPOJOList) {
                braStringList.add(branchPOJO.getBranch_name() + " (" + branchPOJO.getBranch_code() + ")");
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.dropsimpledown, braStringList);
            spinner_branch.setAdapter(spinnerArrayAdapter);

            if (branchPOJOList.size() > 0) {
                branch_code = branchPOJOList.get(0).getBranch_code();
                getAllPatients(branch_code);
            }


            spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    branch_code = branchPOJOList.get(position).getBranch_code();
                    getAllPatients(branch_code);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseGetAllPatientsAPI(String response) {
        Log.d(TagUtils.getTag(), "patient response:-" + response);
        try {
            Gson gson = new Gson();
            PatientPOJO patientPOJO = gson.fromJson(response, PatientPOJO.class);
            if (patientPOJO.getSuccess().equals("true")) {
                List<PatientResultPOJO> patientResultPOJOs = patientPOJO.getPatientResultPOJOs();
                mainpatientarray=patientResultPOJOs;
                setPatientAdapter(patientResultPOJOs);
            } else {
                ToastClass.showShortToast(getApplicationContext(), "No Patients Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    List<PatientResultPOJO> mainpatientarray=new ArrayList<>();

    public void setPatientAdapter(List<PatientResultPOJO> patientResultPOJOs){
        if(patientResultPOJOs.size()>0) {

        }else{
            ToastClass.showShortToast(getApplicationContext(),"No Patients Found");
        }
        PatientAdapter adapter = new PatientAdapter(this, patientResultPOJOs);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv_patients.setLayoutManager(horizontalLayoutManagaer);
        rv_patients.setHasFixedSize(true);
        rv_patients.setItemAnimator(new DefaultItemAnimator());
        rv_patients.setAdapter(adapter);
    }
}
