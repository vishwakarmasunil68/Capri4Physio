package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
 * Use the {@link AddHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author prabhunathy
 * @version 1.0
 * @since 2014-03-31
 */
public class AddHistoryFragment extends BaseFragment implements HttpUrlListener {

    private Button mBtnSave;
    RadioGroup rg1,rg2,rg3,rg4,rg5,rg6,rg7,rg8,rg9,rg10,rg11,rg12,rg13,rg14,rg15,rg16,rg2_bloodpressure,rg17,rg18,rg19;
    private RadioGroup mRadioGroup;
    private EditText mEdtxtMedicalHistory;
    private EditText mEdtxtSurgicalHistory;
    private EditText mEdtxtOtherHistory;
    private EditText mEdtxtMedicineUsed;

    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
    private String patientId = "";
    private String assessmentType = "";
    private String rate1 = "NO";
    private String rate2 = "NO";
    private String rate3 = "NO";
    private String rate4 = "NO";
    private String rate5 = "NO";
    private String rate6 = "NO";
    private String rate7 = "NO";
    private String rate8 = "NO";
    private String rate9 = "NO";
    private String rate10 = "NO";
    private String rate11 = "NO";
    private String rate12 = "NO";
    private String rate13 = "NO";
    private String rate14 = "NO";
    private String rate15 = "NO";
    private String rate16 = "NO";
    private String rate17 = "NO";
    private String rate18 = "NO";
    private String rate19 = "NO";
    private String rate20 = "Normal";



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CheifComplaintFragment.
     */
    public static AddHistoryFragment newInstance(String patientId, String assessmentType) {
        AddHistoryFragment fragment = new AddHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);

        fragment.setArguments(bundle);
        return fragment;
    }

    public AddHistoryFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_add_history, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);

        mBtnSave = (Button) view.findViewById(R.id.btn_save);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.radio_group1);
        mEdtxtMedicalHistory = (EditText) view.findViewById(R.id.edtxt_medical_history);
        mEdtxtSurgicalHistory = (EditText) view.findViewById(R.id.edtxt_surgical_history);
        mEdtxtOtherHistory = (EditText) view.findViewById(R.id.edtxt_other_history);
        mEdtxtMedicineUsed = (EditText) view.findViewById(R.id.edtxt_medicine_used);
        rg1=(RadioGroup)view.findViewById(R.id.radio_group1);
        rg2_bloodpressure=(RadioGroup)view.findViewById(R.id.radio_groupblood_pressure);
        rg2=(RadioGroup)view.findViewById(R.id.radio_group2);
        rg3=(RadioGroup)view.findViewById(R.id.radio_group3);
        rg4=(RadioGroup)view.findViewById(R.id.radio_group4);
        rg5=(RadioGroup)view.findViewById(R.id.radio_group5);
        rg6=(RadioGroup)view.findViewById(R.id.radio_group6);
        rg7=(RadioGroup)view.findViewById(R.id.radio_group7);
        rg8=(RadioGroup)view.findViewById(R.id.radio_group8);
        rg9=(RadioGroup)view.findViewById(R.id.radio_group9);
        rg10=(RadioGroup)view.findViewById(R.id.radio_group10);
        rg11=(RadioGroup)view.findViewById(R.id.radio_group11);
        rg12=(RadioGroup)view.findViewById(R.id.radio_group12);
        rg13=(RadioGroup)view.findViewById(R.id.radio_group13);
        rg14=(RadioGroup)view.findViewById(R.id.radio_group14);
        rg15=(RadioGroup)view.findViewById(R.id.radio_group15);
        rg16=(RadioGroup)view.findViewById(R.id.radio_group16);
        rg17=(RadioGroup)view.findViewById(R.id.radio_group17);
        rg18=(RadioGroup)view.findViewById(R.id.radio_group18);
        rg19=(RadioGroup)view.findViewById(R.id.radio_group19);
    }

    @Override
    protected void setListener() {
        super.setListener();

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addApiCall();
            }
        });

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos1 = rg1.indexOfChild(group.findViewById(checkedId));
                RadioButton rb = (RadioButton) rg1.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate1 = rb.getText().toString();
                }
            }
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();

        });
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos1 = rg2.indexOfChild(group.findViewById(checkedId));
                RadioButton rb = (RadioButton) rg2.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate2 = rb.getText().toString();
                }
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();

            }
        });
        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos1 = rg3.indexOfChild(group.findViewById(checkedId));
                RadioButton rb = (RadioButton) rg3.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate3 = rb.getText().toString();
                }
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();

            }
        });
        rg2_bloodpressure.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos4 = rg2_bloodpressure.indexOfChild(group.findViewById(checkedId));
                RadioButton rb = (RadioButton) rg2_bloodpressure.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate20 = rb.getText().toString();
                }
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();

            }
        });
        rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos4 = rg4.indexOfChild(group.findViewById(checkedId));
                RadioButton rb = (RadioButton) rg4.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate4 = rb.getText().toString();
                }
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();

            }
        });
        rg5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos5 = rg5.indexOfChild(group.findViewById(checkedId));
                RadioButton rb = (RadioButton) rg5.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate5 = rb.getText().toString();
                }
            }
        });
        rg6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos6 = rg6.indexOfChild(group.findViewById(checkedId));
                RadioButton rb = (RadioButton) rg6.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate6 = rb.getText().toString();
                }
            }
        });
        rg7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos7 = rg7.indexOfChild(group.findViewById(checkedId));
                RadioButton rb = (RadioButton) rg7.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate7 = rb.getText().toString();
                }
            }
        });
        rg8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                RadioButton rb = (RadioButton) rg8.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate8 = rb.getText().toString();
                }
            }
        });
        rg9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                RadioButton rb = (RadioButton) rg9.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate9 = rb.getText().toString();
                }
            }
        });
        rg10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                RadioButton rb = (RadioButton) rg10.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate10 = rb.getText().toString();
                }
            }
        });

        rg11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                RadioButton rb = (RadioButton) rg11.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate11 = rb.getText().toString();
                }
            }
        });

        rg12.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                RadioButton rb = (RadioButton) rg12.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate12 = rb.getText().toString();
                }
            }
        });

        rg13.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                RadioButton rb = (RadioButton) rg13.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate13 = rb.getText().toString();
                }
            }
        });

        rg14.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                RadioButton rb = (RadioButton) rg14.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate14 = rb.getText().toString();
                }
            }
        });

        rg15.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                RadioButton rb = (RadioButton) rg15.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate15 = rb.getText().toString();
                }
            }
        });

        rg16.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                RadioButton rb = (RadioButton) rg16.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate16 = rb.getText().toString();
                }
            }
        });

        rg17.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                RadioButton rb = (RadioButton) rg17.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate17 = rb.getText().toString();
                }
            }
        });

        rg18.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                RadioButton rb = (RadioButton) rg18.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate18 = rb.getText().toString();
                }
            }
        });
        rg19.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                RadioButton rb = (RadioButton) rg19.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    rate19 = rb.getText().toString();
                }
            }
        });

    }


    private void addApiCall() {
        if (!isValid())
            return;

        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.PATIENT_ID, patientId);
                params.put(ApiConfig.ASSESSMENT_TYPE, assessmentType);
                params.put(ApiConfig.MEDICAL_HISTORY, mEdtxtMedicalHistory.getText().toString().trim());
                params.put(ApiConfig.SURGICAL_HISTORY, mEdtxtSurgicalHistory.getText().toString().trim());
                params.put(ApiConfig.OTHER_HISTORY, mEdtxtOtherHistory.getText().toString().trim());
                params.put(ApiConfig.MEDICINE_USED, mEdtxtMedicineUsed.getText().toString().trim());
                params.put(ApiConfig.DATE, Utils.getCurrentDate());
                params.put("diabetes", rate1);
                params.put("blood_pressure", "normal");
                params.put("bp", rate20);
                params.put("smoking", rate2);
//                params.put("blood_pressure", rate3);
                params.put("fever_and_chill", rate4);
                params.put("heart_diseases", rate5);
                params.put("bleeding_disorder", rate6);
                params.put("recent_infection", rate7);
                params.put("pregnancy", rate8);
                params.put("htn", rate9);
                params.put("tb", rate10);
                params.put("cancer", rate11);
                params.put("hiv_aids", rate12);
                params.put("past_surgery", rate13);
                params.put("allergies", rate14);
                params.put("osteoporotic", rate15);
//                params.put("osteoporotic", hadProblemBefore14);
                params.put("depression", rate16);
                params.put("hepatitis", rate17);
                params.put("any_implants", rate18);
                params.put("hereditary_disease", rate19);



                new UrlConnectionTask(getActivity(), ApiConfig.ADD_ASSESSMENT_URL, ApiConfig.ID1, true, params, BaseModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }


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


    /**
     * Validation to check user inputs
     *
     * @return
     */
    private boolean isValid() {

        String medicalHistory = mEdtxtMedicalHistory.getText().toString().trim();
        String surgicalHistory = mEdtxtSurgicalHistory.getText().toString().trim();
        String otherHistory = mEdtxtOtherHistory.getText().toString().trim();
        String medicineUsed = mEdtxtMedicineUsed.getText().toString().trim();

        if (medicalHistory.isEmpty()) {
            mEdtxtMedicalHistory.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_medical_history));
            return false;
        }

        if (surgicalHistory.isEmpty()) {
            mEdtxtSurgicalHistory.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_surgical_history));
            return false;
        }

        if (otherHistory.isEmpty()) {
            mEdtxtOtherHistory.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_other_history));
            return false;
        }
        if (medicineUsed.isEmpty()) {
            mEdtxtMedicineUsed.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_medicine_used));
            return false;
        }

        return true;
    }
}