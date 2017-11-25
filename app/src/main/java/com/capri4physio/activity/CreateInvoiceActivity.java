package com.capri4physio.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.capri4physio.R;
import com.capri4physio.Services.GetWebServices;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.model.branch.BranchPOJO;
import com.capri4physio.model.doctor.DoctorPOJO;
import com.capri4physio.model.doctor.DoctorResultPOJO;
import com.capri4physio.model.patient.PatientPOJO;
import com.capri4physio.model.patient.PatientResultPOJO;
import com.capri4physio.model.treatment.TreatmentPOJO;
import com.capri4physio.model.treatment.TreatmentResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateInvoiceActivity extends AppCompatActivity implements WebServicesCallBack{

    private static final String GET_ALL_PATIENTS_API = "get_all_patients";
    private static final String ADD_INVOICE_API = "add_invoice_api";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_patients)
    Spinner spinner_patients;
    @BindView(R.id.spinner_therapist)
    Spinner spinner_therapist;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    @BindView(R.id.spinner_treatment)
    Spinner spinner_treatment;
    @BindView(R.id.spinner_payment_mode)
    Spinner spinner_payment_mode;
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.et_payment_trans)
    EditText et_payment_trans;
    @BindView(R.id.ll_therapist)
    LinearLayout ll_therapist;
    @BindView(R.id.rl_branch)
    RelativeLayout rl_branch;

    List<String> paymentList=new ArrayList<>();
    List<BranchPOJO> branchPOJOList=new ArrayList<>();
    private static final String GET_ALL_BRANCHES = "get_all_branches";
    private static final String GET_DOCTORS_API = "get_doctors_api";
    private static final String GET_ALL_TREATMENT = "get_all_treatment_api";
    List<DoctorResultPOJO> doctorResultPOJOList = new ArrayList<>();
    List<PatientResultPOJO> patientResultPOJOArrayList= new ArrayList<>();
    List<String> all_treatment;
    List<TreatmentResultPOJO> listadminTreatments;
    String branch_code="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Create Invoice");

        paymentList.add("Cash");
        paymentList.add("Online");
        paymentList.add("Cheque");



        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(), R.layout.dropsimpledown, paymentList);
        spinner_payment_mode.setAdapter(spinnerArrayAdapter);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCreateInvoiceAPI();
            }
        });
        spinner_payment_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    et_payment_trans.setVisibility(View.GONE);
                }else{
                    et_payment_trans.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")){
            new GetWebServices(this, GET_ALL_BRANCHES).execute(ApiConfig.GetURL);
        }else{
            rl_branch.setVisibility(View.GONE);
            branch_code=AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
            if(AppPreferences.getInstance(getApplicationContext()).getUserType().equals("2")){
                getAllPatients(branch_code);
                ll_therapist.setVisibility(View.GONE);
            }else {
                getAllPatients(branch_code);
                getTherapists(branch_code);
            }
        }
        getAllTheray();
    }

    public void callCreateInvoiceAPI(){
        String payment_mode=paymentList.get(spinner_payment_mode.getSelectedItemPosition());
        String trans_id="";
        if(payment_mode.equals("Online")||payment_mode.equals("Cheque")){
            if(et_payment_trans.getText().toString().length()>0){
                trans_id=et_payment_trans.getText().toString();
            }else{
                ToastClass.showShortToast(getApplicationContext(),"Please Enter Trans ID");
                return;
            }
        }
        String branch="";
        String doctor_id="";
        if(AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")){
            branch=branchPOJOList.get(spinner_branch.getSelectedItemPosition()).getBranch_code();
            doctor_id=doctorResultPOJOList.get(spinner_therapist.getSelectedItemPosition()).getId();
        }else{
            branch=branch_code;
            doctor_id=AppPreferences.getInstance(getApplicationContext()).getUserID();
        }
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String date=sdf.format(d).split(" ")[0];
        String time=sdf.format(d).split(" ")[1];

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//        nameValuePairs.add(new BasicNameValuePair("invo_code", ""));
        nameValuePairs.add(new BasicNameValuePair("invo_patient_id", patientResultPOJOArrayList.get(spinner_patients.getSelectedItemPosition()).getId()));
        nameValuePairs.add(new BasicNameValuePair("invo_patient_name", patientResultPOJOArrayList.get(spinner_patients.getSelectedItemPosition()).getFirstName()+" "+patientResultPOJOArrayList.get(spinner_patients.getSelectedItemPosition()).getLastName()));
        nameValuePairs.add(new BasicNameValuePair("invo_thurapy", listadminTreatments.get(spinner_treatment.getSelectedItemPosition()).getTreatment_name()));
        nameValuePairs.add(new BasicNameValuePair("invo_amount", listadminTreatments.get(spinner_treatment.getSelectedItemPosition()).getTreatment_price()));
        nameValuePairs.add(new BasicNameValuePair("invo_payment_mode", paymentList.get(spinner_payment_mode.getSelectedItemPosition())));
        nameValuePairs.add(new BasicNameValuePair("invo_status", "paid"));
        nameValuePairs.add(new BasicNameValuePair("invo_therapist_id", doctor_id));
        nameValuePairs.add(new BasicNameValuePair("invo_trans_id", trans_id));
        nameValuePairs.add(new BasicNameValuePair("invo_branch_code", branch));
        nameValuePairs.add(new BasicNameValuePair("invo_date", date));
        nameValuePairs.add(new BasicNameValuePair("invo_time", time));

        Log.d(TagUtils.getTag(),"name value pairs:-"+nameValuePairs.toString());
        new WebServiceBase(nameValuePairs, this, ADD_INVOICE_API).execute(ApiConfig.ADD_INVOICE_CREATED);
    }


    public void getAllTheray(){
        new GetWebServices(this,GET_ALL_TREATMENT,true).execute(ApiConfig.get_all_admin_treatment);
    }

    public void getTherapists(String branch_code){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("bracch_code", branch_code));
        new WebServiceBase(nameValuePairs, this, GET_DOCTORS_API).execute(ApiConfig.get_branch_doctor);
    }

    public void getAllPatients(String branch_code){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("bracch_code", branch_code));
        new WebServiceBase(nameValuePairs, this, GET_ALL_PATIENTS_API).execute(ApiConfig.GET_PATIENTS_BY_BRANCH_CODE);
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
            case GET_ALL_PATIENTS_API:
                parseAllPatientsResponse(response);
                break;
            case GET_ALL_TREATMENT:
                parseGetAllTreatment(response);
                break;
            case ADD_INVOICE_API:
                parseAddInvoiceCreatedResponse(response);
                break;
        }
    }

    public void parseAddInvoiceCreatedResponse(String response){
        Log.d(TagUtils.getTag(),"invoice response:-"+response);
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("success").equals("true")){
                ToastClass.showShortToast(getApplicationContext(),"Invoice Created");
                finish();
            }else{
                ToastClass.showShortToast(getApplicationContext(),"Failed to Create Invoice");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
        }
    }

    public void parseGetAllTreatment(String response){
        Log.d(TagUtils.getTag(),"admin treatment response:-"+response);
        try{
            Gson gson=new Gson();
            TreatmentPOJO treatmentPOJO=gson.fromJson(response,TreatmentPOJO.class);
            if(treatmentPOJO.getSuccess().equals("true")){
                all_treatment=new ArrayList<>();
                listadminTreatments=treatmentPOJO.getTreatmentResultPOJOList();
                for(TreatmentResultPOJO treatmentResultPOJO:treatmentPOJO.getTreatmentResultPOJOList()){
                    all_treatment.add(treatmentResultPOJO.getTreatment_name());
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, all_treatment);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_treatment.setAdapter(dataAdapter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void parseAllPatientsResponse(String response){
        Log.d(TagUtils.getTag(), "patient response:-" + response);
        try {
            Gson gson = new Gson();
            PatientPOJO patientPOJO = gson.fromJson(response, PatientPOJO.class);
            if (patientPOJO.getSuccess().equals("true")) {
                 patientResultPOJOArrayList.addAll(patientPOJO.getPatientResultPOJOs());
                List<String> patientStringPOJOList=new ArrayList<>();
                for(PatientResultPOJO patientResultPOJO:patientResultPOJOArrayList){
                    patientStringPOJOList.add(patientResultPOJO.getFirstName()+" "+patientResultPOJO.getLastName());
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(), R.layout.dropsimpledown, patientStringPOJOList);
                spinner_patients.setAdapter(spinnerArrayAdapter);

            } else {
                ToastClass.showShortToast(getApplicationContext(), "No Patients Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseGetDoctorsResponse(String response){
        Log.d(TagUtils.getTag(), "get doctor response:-" + response);
        try {
            Gson gson = new Gson();
            DoctorPOJO doctorPOJO = gson.fromJson(response, DoctorPOJO.class);
            doctorResultPOJOList.clear();
            if (doctorPOJO.getSuccess().equals("true")) {
                List<String> doctorStringList = new ArrayList<>();
                doctorResultPOJOList.addAll(doctorPOJO.getDoctorResultPOJOList());
                for (DoctorResultPOJO doctorResultPOJO : doctorResultPOJOList) {
                    doctorStringList.add(doctorResultPOJO.getFirstName() + " " + doctorResultPOJO.getLastName());
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(), R.layout.dropsimpledown, doctorStringList);
                spinner_therapist.setAdapter(spinnerArrayAdapter);



            } else {
                ToastClass.showShortToast(getApplicationContext(), "No Doctor Found. Please Select another branch");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
                getTherapists(branchPOJOList.get(0).getBranch_code());
            }
            if(branchPOJOList.size()>0){
                getAllPatients(branchPOJOList.get(0).getBranch_code());
            }

            spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    getTherapists(branchPOJOList.get(position).getBranch_code());
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
