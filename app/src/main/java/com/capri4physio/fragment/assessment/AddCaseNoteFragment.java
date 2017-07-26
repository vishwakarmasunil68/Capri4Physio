package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.fragment.BaseFragment;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jatinder on 10-06-2016.
 */
public class AddCaseNoteFragment extends BaseFragment implements HttpUrlListener {
    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
    private String patientId = "";
    private String assessmentType = "";
    private EditText mBloodPresure;
    private EditText mTemp;
    private EditText mHrate;
    private EditText mRespiratoryRate;
    private EditText mPosture;
    private EditText mGait;
    private EditText mScarType;
    private EditText mDescription,mSwelling;
    private Spinner mBuiltOfThePatient;
    private Button mSave;
    private String mbuiltpataient = "";

    @Override
    public void onPostSuccess(Object response, int id) {
        switch (id) {

            case ApiConfig.ID1:
                BaseModel baseModel = (BaseModel) response;
                AppLog.i("Capri4Physio", "Patient Response : " + baseModel.getStatus());
                getFragmentManager().popBackStack();
                break;
        }
    }

    @Override
    public void onPostError(String errMsg, int id) {

    }

    public static AddCaseNoteFragment newInstance(String patientId, String assessmentType) {
        AddCaseNoteFragment fragment = new AddCaseNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);
        fragment.setArguments(bundle);
        return fragment;
    }

    public AddCaseNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            patientId = getArguments().getString(KEY_PATIENT_ID);
            assessmentType = getArguments().getString(KEY_TYPE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_case_note, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mBloodPresure = (EditText) view.findViewById(R.id.edtxt_blood_presure);
        mSave = (Button) view.findViewById(R.id.btn_save);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMotorAPi();
            }
        });
    }
    private void addMotorAPi(){
        final String name = mBloodPresure.getText().toString().trim();
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.CASE_NOTES_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            Toast.makeText(getActivity(),"Record Added Successfully",Toast.LENGTH_SHORT).show();
                            getFragmentManager().popBackStack();
                            mBloodPresure.setText("");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        startActivity(new Intent(StmtActivity.this, PinDoMatch.class));
                        Log.e("Postdat", "" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                    }
                }){


            protected Map<String,String> getParams(){
                Map<String,String> objresponse = new HashMap<String, String>();
                objresponse.put("moter_exam_casedate", Utils.getCurrentDate());
                objresponse.put("patient_id", patientId);
                objresponse.put("moter_exam_casedesc", name);
                /*params.put("sfirst_name",name);
                params.put("slast_name",lastName);
                params.put("sdob",dob);
                params.put("sage", "23");
                params.put("sdatejoing",doj);
                params.put("senddate", endingdateofcontract);
                params.put("sgender", rate);
                params.put("smarital_status", rate1);
                params.put("sdesignation",designation);
                params.put("saddress", address);
                params.put("scity", city);
                params.put("spincode", pin_code);
                params.put("smobile", phone);
                params.put("semail", email_id);
                params.put("squalifation", degree);
                params.put("sexprience", experienceduration);*/

//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

        /*mBuiltOfThePatient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    mbuiltpataient = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }*/

    private void addApiCall() {

        if (Utils.isNetworkAvailable(getActivity())) {



        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }
}
/*
params.put("name", mHrate.getText().toString());
        params.put("phone_number", mRespiratoryRate.getText().toString());
        params.put("address", "hello.");
        params.put("dob", "10-03-1988");
        params.put("email_id", " mgh@gmail.com");
        params.put("bank_ac_no", mScarType.getText().toString());
        params.put("ifsc_code", mDescription.getText().toString());
        params.put("pan_card_number", mSwelling.getText().toString());
        Log.e("jsondata",params.toString());
        */
