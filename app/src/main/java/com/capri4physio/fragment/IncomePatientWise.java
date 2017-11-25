package com.capri4physio.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.capri4physio.R;
import com.capri4physio.Services.GetWebServicesFragment;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.model.branch.BranchPOJO;
import com.capri4physio.model.patient.PatientPOJO;
import com.capri4physio.model.patient.PatientResultPOJO;
import com.capri4physio.model.treatment.TreatmentResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.commons.lang.time.DateUtils;
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

/**
 * Created by Emobi-Android-002 on 9/2/2016.
 */
public class IncomePatientWise extends AppCompatActivity implements WebServicesCallBack, DatePickerDialog.OnDateSetListener {
    private static final String GET_ALL_PATIENTS_API = "get_all_patients";
    private static final String GET_ALL_BRANCHES = "get_all_branches";
    private static final String CALL_SEARCH_PATIENTS= "call_search_patients";
    List<String> all_treatment;
    List<TreatmentResultPOJO> listadminTreatments;
    @BindView(R.id.spinner_patients)
    Spinner spinner_patients;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    List<BranchPOJO> branchPOJOList = new ArrayList<>();
    @BindView(R.id.btn_submit)
    Button btn_submit;
    String branch_code = "";
    String treatment_id = "";

    String therapist_id = "";
    String start_date = "", end_date = "";
    @BindView(R.id.et_from)
    EditText et_from;
    @BindView(R.id.iv_from)
    ImageView iv_from;
    @BindView(R.id.et_to)
    EditText et_to;
    @BindView(R.id.iv_to)
    ImageView iv_to;
    @BindView(R.id.ll_branch)
    LinearLayout ll_branch;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;

    boolean is_from = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_income_patient_wise);
        ButterKnife.bind(this);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TagUtils.getTag(),"branc_code:-"+branch_code);
//                Log.d(TagUtils.getTag(),"therapist_id:-"+therapist_id);
//                Log.d(TagUtils.getTag(),"start_date:-"+start_date);
//                Log.d(TagUtils.getTag(),"end_date:-"+end_date);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date from_d = sdf.parse(start_date);
                    Date to_d = sdf.parse(end_date);

                    if (from_d.before(new Date())) {
                        if (DateUtils.isSameDay(to_d, new Date()) || to_d.after(new Date())) {
                            if (branch_code.length() > 0 && patient_id.length() > 0) {
                                viewReportFragment(branch_code, patient_id, start_date, end_date);
                            } else {
                                ToastClass.showShortToast(getApplicationContext(), "Invalid Branch code or patientID");
                                return;
                            }
                        } else {
                            ToastClass.showShortToast(getApplicationContext(), "you have selected passed to date");
                        }
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), "You have selected coming from date");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastClass.showShortToast(getApplicationContext(), "Invalid Date Formats");
                }
            }
        });


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        et_from.setText(sdf.format(new Date()));
        start_date = sdf.format(new Date());
        end_date = sdf.format(new Date());
        et_to.setText(sdf.format(new Date()));
        iv_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_from = true;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        IncomePatientWise.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Select from date");
            }
        });

        iv_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_from = false;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        IncomePatientWise.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "select to date");
            }
        });

        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")) {
            new GetWebServicesFragment(this, GET_ALL_BRANCHES).execute(ApiConfig.GetURL);
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
                        setPatientSpinner(mainpatientarray);
                    }else{
                        setPatientSpinner(new ArrayList<PatientResultPOJO>());
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

    public void viewReportFragment(String branch_code, String patient_id, String start_date, String end_date) {
        IncomePatientWiseFragment incomeBranchWiseFragment = new IncomePatientWiseFragment(branch_code, patient_id, start_date, end_date);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fram_main, incomeBranchWiseFragment, "incomeBranchWiseFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GET_ALL_PATIENTS_API:
                parseAllPatientsResponse(response);
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
                setPatientSpinner(patientPOJO.getPatientResultPOJOs());
            }else{
                setPatientSpinner(new ArrayList<PatientResultPOJO>());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    List<PatientResultPOJO> patientResultPOJOArrayList = new ArrayList<>();
    List<PatientResultPOJO> mainpatientarray = new ArrayList<>();
    String patient_id = "";

    public void parseAllPatientsResponse(String response) {
        Log.d(TagUtils.getTag(), "patient response:-" + response);
        try {
            Gson gson = new Gson();
            PatientPOJO patientPOJO = gson.fromJson(response, PatientPOJO.class);
            if (patientPOJO.getSuccess().equals("true")) {
                patientResultPOJOArrayList.clear();
                patientResultPOJOArrayList.addAll(patientPOJO.getPatientResultPOJOs());
                mainpatientarray = patientResultPOJOArrayList;
                setPatientSpinner(patientResultPOJOArrayList);

            } else {
                setPatientSpinner(new ArrayList<PatientResultPOJO>());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPatientSpinner(final List<PatientResultPOJO> patientResultPOJOArrayList){

        if(patientResultPOJOArrayList.size()>0) {
            final List<String> patientStringPOJOList = new ArrayList<>();
            for (PatientResultPOJO patientResultPOJO : patientResultPOJOArrayList) {
                patientStringPOJOList.add(patientResultPOJO.getFirstName() + " " + patientResultPOJO.getLastName());
            }

            if (patientStringPOJOList.size() > 0) {
                patient_id = patientResultPOJOArrayList.get(0).getId();
            }
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.dropsimpledown, patientStringPOJOList);
            spinner_patients.setAdapter(spinnerArrayAdapter);

            spinner_patients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    patient_id = patientResultPOJOArrayList.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else{
            ToastClass.showShortToast(getApplicationContext(),"No Patients Found");
            patient_id="";
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.dropsimpledown, new ArrayList<String>());
            spinner_patients.setAdapter(spinnerArrayAdapter);
            spinner_patients.setOnItemSelectedListener(null);
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
                getAllPatients(branchPOJOList.get(0).getBranch_code());
            }


            spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    branch_code = branchPOJOList.get(position).getBranch_code();
                    getAllPatients(branchPOJOList.get(position).getBranch_code());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Log.d(TagUtils.getTag(), "date:-" + dayOfMonth + "-" + monthOfYear + "-" + year);
        String day = "";
        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = String.valueOf(dayOfMonth);
        }
        String month = "";
        if ((monthOfYear + 1) < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf(monthOfYear + 1);
        }
        if (is_from) {
            et_from.setText(day + "-" + month + "-" + year);
            start_date = day + "-" + month + "-" + year;
        } else {
            et_to.setText(day + "-" + month + "-" + year);
            end_date = day + "-" + month + "-" + year;
        }
    }
}
