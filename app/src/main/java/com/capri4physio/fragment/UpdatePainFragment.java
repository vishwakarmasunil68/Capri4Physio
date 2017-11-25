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
import com.capri4physio.model.assessment.PainItem;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jatinder on 09-06-2016.
 */
public class UpdatePainFragment extends BaseFragment implements WebServicesCallBack {
    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
    private static final String UPDATE_PAIN_REPORT = "update_pain_report";
    private String patientId = "";
    private String assessmentType = "";
    private EditText mPain_Site;
    private EditText mPain_Nature;
    private EditText mPain_Onset;
    private EditText mPain_Duration;
    private EditText mSide_Location;
    private EditText mTrigger_Point;
    private EditText mAggravative_Factors;
    private EditText mReliving_Factors;
    private Spinner mSpinner_Severity_of_pain;
    private Spinner mPressure_pain_Threshold;
    private Spinner mDiurnal_variations;
    private Button mSave;
    private String severity_pain = "";
    private String threshold_pain = "";
    private String dinural_variation = "";
    PainItem painItem;
    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Pain Examination");
    }

    public static UpdatePainFragment newInstance(String patientId, String assessmentType,PainItem painItem) {
        UpdatePainFragment fragment = new UpdatePainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);
        bundle.putSerializable("pain", painItem);

        fragment.setArguments(bundle);
        return fragment;
    }

    public UpdatePainFragment() {
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
            painItem = (PainItem) getArguments().getSerializable("pain");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_update_pain, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mAggravative_Factors = (EditText) view.findViewById(R.id.edtxt_aggravating);
        mDiurnal_variations = (Spinner) view.findViewById(R.id.spinnervariation);
        mPain_Duration = (EditText) view.findViewById(R.id.edtxt_painduration);
        mPain_Nature = (EditText) view.findViewById(R.id.edtxt_painnature);
        mPain_Onset = (EditText) view.findViewById(R.id.edtxt_painonset);
        mPain_Site = (EditText) view.findViewById(R.id.edtxt_painsite);
        mPressure_pain_Threshold = (Spinner) view.findViewById(R.id.presure_pain);
        mSpinner_Severity_of_pain = (Spinner) view.findViewById(R.id.spinersvierity_pain);
        mSide_Location = (EditText) view.findViewById(R.id.edtxt_painside);
        mTrigger_Point = (EditText) view.findViewById(R.id.edtxt_trigger);
        mReliving_Factors = (EditText) view.findViewById(R.id.edtxt_relieving);
        mSave = (Button) view.findViewById(R.id.btn_save);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePain();
            }
        });

        setValues();

    }

    public void setValues(){
        mPain_Site.setText(painItem.getPainSide());
        mPain_Nature.setText(painItem.getPainNature());
        mPain_Onset.setText(painItem.getPainOnset());
        mPain_Duration.setText(painItem.getPainDuration());
        mSide_Location.setText(painItem.getLocation());
        mTrigger_Point.setText(painItem.getTriggerPoint());
        mAggravative_Factors.setText(painItem.getAggravatingFactors());
        mReliving_Factors.setText(painItem.getRelievingFactors());

        Log.d(TagUtils.getTag(),"pain string:-"+painItem.toString());

        String[] severityarray=getActivity().getResources().getStringArray(R.array.severity_of_pain);
        List<String> severityList= new ArrayList<String>(Arrays.asList(severityarray));
        if(severityList.contains(painItem.getSeverityPain())){
            mSpinner_Severity_of_pain.setSelection(severityList.indexOf(painItem.getSeverityPain()));
        }

        String[] pressurearray=getActivity().getResources().getStringArray(R.array.pressure_pain_threshold);
        List<String> pressureList= new ArrayList<String>(Arrays.asList(pressurearray));
        if(pressureList.contains(painItem.getThresholdSite())){
            mPressure_pain_Threshold.setSelection(pressureList.indexOf(painItem.getThresholdSite()));
        }

        String[] variationarray=getActivity().getResources().getStringArray(R.array.diurnal_variations);
        List<String> variationList= new ArrayList<String>(Arrays.asList(variationarray));
        if(variationList.contains(painItem.getDiurnalVariations())){
            mDiurnal_variations.setSelection(variationList.indexOf(painItem.getDiurnalVariations()));
        }


    }

    public void updatePain(){
        ArrayList<NameValuePair> nameValuePairArrayList=new ArrayList<>();
        nameValuePairArrayList.add(new BasicNameValuePair("pain_side",mPain_Site.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("severity_pain",mSpinner_Severity_of_pain.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("pressure_pain",mPressure_pain_Threshold.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("threshold_site",mPressure_pain_Threshold.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("pain_nature",mPain_Nature.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("pain_onset",mPain_Onset.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("pain_duration",mPain_Duration.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("location",mSide_Location.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("diurnal_variations",mDiurnal_variations.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("trigger_point",mTrigger_Point.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("aggravating_factors",mAggravative_Factors.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("relieving_factors",mReliving_Factors.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("pain_id",painItem.getId()));
        new WebServiceBaseFragment(nameValuePairArrayList,getActivity(),this,UPDATE_PAIN_REPORT).execute(ApiConfig.EDIT_PAIN_);
    }


    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case UPDATE_PAIN_REPORT:
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
