package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.capri4physio.R;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.Utils;

import org.json.JSONObject;


/**
 * create an instance of this fragment.
 *
 * @author prabhunathy
 * @version 1.0
 * @since 2014-03-31
 */
public class AddComplaintFragment extends AppCompatActivity implements HttpUrlListener {

    private Button mBtnSave;
    private RadioGroup mRadioGroup;
    private EditText mEdtxtProblem;
    private EditText mEdtxtProblemDuration;

    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
    private String patientId = "";
    private String assessmentType = "";
    private String hadProblemBefore = "NO";


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CheifComplaintFragment.
     */
    /*public static AddComplaintFragment newInstance(String patientId, String assessmentType) {
        AddComplaintFragment fragment = new AddComplaintFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);

        fragment.setArguments(bundle);
        return fragment;
    }*/

    public AddComplaintFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
setContentView(R.layout.fragment_add_complaint);
        mBtnSave = (Button) findViewById(R.id.btn_save);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mEdtxtProblem = (EditText) findViewById(R.id.edtxt_problem);
        mEdtxtProblemDuration = (EditText) findViewById(R.id.edtxt_how_long);
            patientId = getIntent().getStringExtra(KEY_PATIENT_ID);
            assessmentType = getIntent().getStringExtra(KEY_TYPE);
        Log.e("getreult",patientId +" "+assessmentType);





        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addApiCall();
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    hadProblemBefore = rb.getText().toString();
                }
            }
        });
    }




    private void addApiCall() {
        if (!isValid())
            return;

        if (Utils.isNetworkAvailable(AddComplaintFragment.this)) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.PATIENT_ID, patientId);
                params.put(ApiConfig.ASSESSMENT_TYPE, "ChiefComplaint");
                params.put(ApiConfig.COMPLAINTS, mEdtxtProblem.getText().toString());
                params.put(ApiConfig.PROBLEM_DURATION, mEdtxtProblemDuration.getText().toString());
                params.put(ApiConfig.PROBLEM_BEFORE, hadProblemBefore);
                params.put(ApiConfig.DATE, Utils.getCurrentDate());

                new UrlConnectionTask(AddComplaintFragment.this, ApiConfig.ADD_ASSESSMENT_URL, ApiConfig.ID1, true, params, BaseModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(AddComplaintFragment.this, getResources().getString(R.string.err_network));
        }
    }


    @Override
    public void onPostSuccess(Object response, int id) {
        switch (id) {

            case ApiConfig.ID1:
                BaseModel baseModel = (BaseModel) response;
                AppLog.i("Capri4Physio", "Patient Response : " + baseModel.getStatus());
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result","2");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
//                getFragmentManager().popBackStack();

                break;
        }

    }

    @Override
    public void onPostError(String errMsg, int id) {

    }




    /**
     * Validation to check user inputs
     *
     * @return
     */

    private boolean isValid() {

        String problem = mEdtxtProblem.getText().toString().trim();
        String problemDuration = mEdtxtProblemDuration.getText().toString().trim();

        if (problem.isEmpty()) {
            mEdtxtProblem.requestFocus();
            Utils.showError(AddComplaintFragment.this, getResources().getString(R.string.error), getResources().getString(R.string.err_cheif_complaint));
            return false;
        }

        if (problemDuration.isEmpty()) {
            mEdtxtProblemDuration.requestFocus();
            Utils.showError(AddComplaintFragment.this, getResources().getString(R.string.error), getResources().getString(R.string.err_cheif_complaint_how_long));
            return false;
        }

        return true;
    }
}