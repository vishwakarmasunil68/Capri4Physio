package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.capri4physio.util.HandlerConstant;
import com.capri4physio.util.Utils;

import org.json.JSONObject;

/**
 * Created by jatinder on 09-06-2016.
 */
public class AddPainFragment extends BaseFragment implements HttpUrlListener {
    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
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

    @Override
    public void onPostSuccess(Object response, int id) {
        switch (id) {

            case ApiConfig.ID1:
                BaseModel baseModel = (BaseModel) response;
                AppLog.i("Capri4Physio", "Patient Response : " + baseModel.getStatus());

                getFragmentManager().popBackStack();
                HandlerConstant.POP_INNER_BACK_HANDLER.sendMessage(HandlerConstant.POP_INNER_BACK_HANDLER.obtainMessage(0, ""));
                break;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Pain Examination");
    }

    @Override
    public void onPostError(String errMsg, int id) {

    }

    public static AddPainFragment newInstance(String patientId, String assessmentType) {
        AddPainFragment fragment = new AddPainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);

        fragment.setArguments(bundle);
        return fragment;
    }

    public AddPainFragment() {
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
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_pain, container, false);
        this.rootView=rootView;
        initView(rootView);
        setListener();
        return rootView;
    }

    public String getSpinnerValue(Spinner spinner){
        try{
            if(spinner.getSelectedItemPosition()==0){
                return "";
            }else {
                return spinner.getSelectedItem().toString();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return "";
        }
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
                addApiCall();
            }
        });
        mSpinner_Severity_of_pain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    severity_pain = adapterView.getItemAtPosition(i).toString();
                } else {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mPressure_pain_Threshold.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    threshold_pain = adapterView.getItemAtPosition(i).toString();
                } else {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mDiurnal_variations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    dinural_variation = adapterView.getItemAtPosition(i).toString();
                } else {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public String getEditValue(EditText editText){
        return editText.getText().toString();
    }

    private void addApiCall() {

        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.PATIENT_ID, patientId);
                params.put(ApiConfig.ASSESSMENT_TYPE, "Pain");
                params.put(ApiConfig.DATE, Utils.getCurrentDate());
                params.put(ApiConfig.PAIN_SIDE, getEditValue((EditText) rootView.findViewById(R.id.edtxt_painsite)));
                params.put(ApiConfig.SEVERITY_PAIN, getSpinnerValue((Spinner) rootView.findViewById(R.id.spinersvierity_pain)));
                params.put(ApiConfig.PRESURE_PAIN, "");
                params.put(ApiConfig.THRESHOLD_SITE, getSpinnerValue((Spinner) rootView.findViewById(R.id.presure_pain)));
                params.put(ApiConfig.PAIN_NATURE, getEditValue((EditText) rootView.findViewById(R.id.edtxt_painnature)));
                params.put(ApiConfig.PAIN_ONSET, getEditValue((EditText) rootView.findViewById(R.id.edtxt_painonset)));
                params.put(ApiConfig.PAIN_DURATION, getEditValue((EditText) rootView.findViewById(R.id.edtxt_painduration)));
                params.put(ApiConfig.PAIN_LOCATION, getEditValue((EditText) rootView.findViewById(R.id.edtxt_painside)));
                params.put(ApiConfig.DIURNAL_VARIATION, ((Spinner)rootView.findViewById(R.id.spinnervariation)).getSelectedItem().toString());
                params.put(ApiConfig.TRIGGER_POINT, getEditValue((EditText) rootView.findViewById(R.id.edtxt_trigger)));
                params.put(ApiConfig.AGGRAVATING_FACTORS, getEditValue((EditText) rootView.findViewById(R.id.edtxt_aggravating)));
                params.put(ApiConfig.RELIEVING_FACTORS,getEditValue((EditText) rootView.findViewById(R.id.edtxt_relieving)));


                new UrlConnectionTask(getActivity(), ApiConfig.ADD_ASSESSMENT_URL, ApiConfig.ID1, true, params, BaseModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }

}
