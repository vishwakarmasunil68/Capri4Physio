package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    private AutoCompleteTextView mEdtxtProblem;
    private EditText mEdtxtProblemDuration;

    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
    private String patientId = "";
    private String assessmentType = "";
    private String hadProblemBefore = "NO";
    private DatabaseReference root;

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

    ArrayAdapter<String> arrayAdapter;
    List<String> list_doses = new ArrayList<>();
    Set<String> set_doses= new HashSet<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_complaint);
        root = FirebaseDatabase.getInstance().getReference().getRoot();
        mBtnSave = (Button) findViewById(R.id.btn_save);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mEdtxtProblem = (AutoCompleteTextView) findViewById(R.id.edtxt_problem);
        mEdtxtProblemDuration = (EditText) findViewById(R.id.edtxt_how_long);
        patientId = getIntent().getStringExtra(KEY_PATIENT_ID);
        assessmentType = getIntent().getStringExtra(KEY_TYPE);
        Log.e("getreult", patientId + " " + assessmentType);

        getSupportActionBar().setTitle("Chief Complaint");
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

        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, list_doses);
        //Used to specify minimum number of
        //characters the user has to type in order to display the drop down hint.
        mEdtxtProblem.setThreshold(1);
        //Setting adapter
        mEdtxtProblem.setAdapter(arrayAdapter);

        root.child("chiefcomplaint").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_doses.clear();
                set_doses.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TagUtils.getTag(), "doses datashapshot:-" + postSnapshot.getValue());
                    set_doses.add(postSnapshot.getValue().toString());
                }
                list_doses.addAll(set_doses);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TagUtils.getTag(), "Failed to read app title value.", databaseError.toException());
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

                String mGroupId = root.push().getKey();
                root.child("chiefcomplaint").child(mGroupId).setValue(mEdtxtProblem.getText().toString());

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
                returnIntent.putExtra("result", "2");
                setResult(Activity.RESULT_OK, returnIntent);
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