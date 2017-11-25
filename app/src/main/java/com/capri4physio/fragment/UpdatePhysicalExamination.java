package com.capri4physio.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBaseFragment;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.model.assessment.PhysicalItem;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jatinder on 10-06-2016.
 */
public class UpdatePhysicalExamination extends BaseFragment implements WebServicesCallBack{
    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
    private static final String UPDATE_PHYSICAL_REPORT = "update_physical_report";
    private String patientId = "";
    private String assessmentType = "";
    private EditText mBloodPresure;
    private EditText mTemp;
    private EditText mHrate;
    private EditText mRespiratoryRate;
    private EditText mPosture;
    private EditText mGait;
    private EditText mScarType;
    private EditText mDescription, mSwelling;
    private Spinner mBuiltOfThePatient;
    private Button mSave;
    private String mbuiltpataient = "";


    PhysicalItem physicalItem;

    public static UpdatePhysicalExamination newInstance(String patientId, String assessmentType, PhysicalItem physicalItem) {
        UpdatePhysicalExamination fragment = new UpdatePhysicalExamination();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);
        bundle.putSerializable("physical", physicalItem);
        fragment.setArguments(bundle);
        return fragment;
    }

    public UpdatePhysicalExamination() {
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
            physicalItem = (PhysicalItem) getArguments().getSerializable("physical");
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Physical Exam");
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

        mSave.setText("update");
    }

    @Override
    protected void setListener() {
        super.setListener();
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePhysicalExamination();
            }
        });
        setValues();
    }

    public void setValues(){
        mBloodPresure.setText(physicalItem.getBloodPressure());
        mTemp.setText(physicalItem.getTemperature());
        mHrate.setText(physicalItem.getHeartRate());
        mRespiratoryRate.setText(physicalItem.getRespiratoryRate());
        mPosture.setText(physicalItem.getPosture());
        mGait.setText(physicalItem.getGalt());
        mScarType.setText(physicalItem.getScareType());
        mSwelling.setText(physicalItem.getSwelling());
        mDescription.setText(physicalItem.getDescription());


        String[] pressure_pain_thresholdarray=getActivity().getResources().getStringArray(R.array.pressure_pain_threshold);
        List<String> pressure_pain_thresholdList= new ArrayList<String>(Arrays.asList(pressure_pain_thresholdarray));
        if(pressure_pain_thresholdList.contains(physicalItem.getBuiltPatient())){
            mBuiltOfThePatient.setSelection(pressure_pain_thresholdList.indexOf(physicalItem.getBuiltPatient()));
        }
    }

    public void updatePhysicalExamination(){
        ArrayList<NameValuePair> nameValuePairArrayList=new ArrayList<>();
        nameValuePairArrayList.add(new BasicNameValuePair("blood_pressure",mBloodPresure.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("temperature",mTemp.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("heart_rate",mHrate.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("respiratory_rate",mRespiratoryRate.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("built_patient",mBuiltOfThePatient.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("posture",mPosture.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("galt",mGait.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("scare_type",mScarType.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("swelling",mSwelling.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("description",mDescription.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("physical_id",physicalItem.getId()));
        new WebServiceBaseFragment(nameValuePairArrayList,getActivity(),this,UPDATE_PHYSICAL_REPORT).execute(ApiConfig.EDIT_PHYSICAL_);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case UPDATE_PHYSICAL_REPORT:
                parsePainresponse(response);
                break;
        }
    }

    public void parsePainresponse(String response){
        Log.d(TagUtils.getTag(),"response:-"+response);
        try{
            getFragmentManager().popBackStack();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}