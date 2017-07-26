package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.capri4physio.R;
import com.capri4physio.fragment.BaseFragment;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.Utils;

import org.json.JSONObject;

/**
 * Created by jatinder on 10-06-2016.
 */
public class AddPhysicalExamFragment extends BaseFragment implements HttpUrlListener {
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

    public static AddPhysicalExamFragment newInstance(String patientId, String assessmentType) {
        AddPhysicalExamFragment fragment = new AddPhysicalExamFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);
        fragment.setArguments(bundle);
        return fragment;
    }

    public AddPhysicalExamFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_add_physical_exam, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mBloodPresure = (EditText) view.findViewById(R.id.edtxt_blood_presure);
        mTemp = (EditText) view.findViewById(R.id.edtxt_temp);
        mHrate = (EditText) view.findViewById(R.id.edtxt_hrate);
        mRespiratoryRate = (EditText) view.findViewById(R.id.edtxt_rrate);
        mBuiltOfThePatient = (Spinner) view.findViewById(R.id.spinnerbpataitent);
        mPosture = (EditText) view.findViewById(R.id.edtxt_posture);
        mGait = (EditText) view.findViewById(R.id.edtxt_gate);
        mScarType = (EditText) view.findViewById(R.id.edtxt_scartype);
        mDescription = (EditText) view.findViewById(R.id.edtxt_desc);
        mSave = (Button) view.findViewById(R.id.btn_save);
        mSwelling = (EditText) view.findViewById(R.id.edtxt_swelling);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addApiCall();
            }
        });

        mBuiltOfThePatient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    mbuiltpataient = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addApiCall() {

        if (Utils.isNetworkAvailable(getActivity())) {

            try {

                JSONObject params = new JSONObject();
                params.put(ApiConfig.PATIENT_ID, patientId);
                params.put(ApiConfig.ASSESSMENT_TYPE, "PhysicalExamination");
                params.put(ApiConfig.DATE, Utils.getCurrentDate());
                params.put("blood_pressure", mBloodPresure.getText().toString());
                params.put("temperature", mTemp.getText().toString());
                params.put("heart_rate", mHrate.getText().toString());
                params.put("respiratory_rate", mRespiratoryRate.getText().toString());
                params.put("built_patient", mbuiltpataient);
                params.put("posture", mPosture.getText().toString());
                params.put("galt", mGait.getText().toString());
                params.put("scare_type", mScarType.getText().toString());
                params.put("description", mDescription.getText().toString());
                params.put("swelling", mSwelling.getText().toString());
                Log.e("jsondata",params.toString());
                new UrlConnectionTask(getActivity(), ApiConfig.ADD_ASSESSMENT_URL, ApiConfig.ID1, true, params, BaseModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }

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
        new UrlConnectionTask(getActivity(), ApiConfig.REGISTER_API, ApiConfig.ID1, true, params, BaseModel.class, this).execute("");*/
