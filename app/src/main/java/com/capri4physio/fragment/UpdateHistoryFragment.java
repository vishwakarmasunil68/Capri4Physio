package com.capri4physio.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBaseFragment;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.model.assessment.HistoryItem;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Use the {@link UpdateHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author prabhunathy
 * @version 1.0
 * @since 2014-03-31
 */
public class UpdateHistoryFragment extends BaseFragment implements WebServicesCallBack {

    private static final String CALL_UPDATE_PATIENT_HISTORY = "call_update_histories";
    private Button mBtnSave;


    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
    private String patientId = "";
    private String assessmentType = "";
    private HistoryItem historyItem;

    @BindView(R.id.et_present_illness_history)
    EditText et_present_illness_history;
    @BindView(R.id.et_past_illness_history)
    EditText et_past_illness_history;
    @BindView(R.id.et_medical_history)
    EditText et_medical_history;
    @BindView(R.id.et_surgical_history)
    EditText et_surgical_history;
    @BindView(R.id.et_other_history)
    EditText et_other_history;
    @BindView(R.id.et_medicine_used)
    EditText et_medicine_used;
    @BindView(R.id.et_remark)
    EditText et_remark;

    @BindView(R.id.rg_diabetes)
    RadioGroup rg_diabetes;
    @BindView(R.id.rb_db_yes)
    RadioButton rb_db_yes;
    @BindView(R.id.rb_db_no)
    RadioButton rb_db_no;
    @BindView(R.id.rg_bp)
    RadioGroup rg_bp;
    @BindView(R.id.rb_bp_normal)
    RadioButton rb_bp_normal;
    @BindView(R.id.rb_bp_low)
    RadioButton rb_bp_low;
    @BindView(R.id.rb_bp_high)
    RadioButton rb_bp_high;
    @BindView(R.id.rg_smoking)
    RadioGroup rg_smoking;
    @BindView(R.id.rb_smoking_yes)
    RadioButton rb_smoking_yes;
    @BindView(R.id.rb_smoking_no)
    RadioButton rb_smoking_no;
    @BindView(R.id.rg_drinking)
    RadioGroup rg_drinking;
    @BindView(R.id.rb_drinking_yes)
    RadioButton rb_drinking_yes;
    @BindView(R.id.rb_drinking_no)
    RadioButton rb_drinking_no;
    @BindView(R.id.rg_fever)
    RadioGroup rg_fever;
    @BindView(R.id.rb_fever_yes)
    RadioButton rb_fever_yes;
    @BindView(R.id.rb_fever_no)
    RadioButton rb_fever_no;
    @BindView(R.id.rg_hd)
    RadioGroup rg_hd;
    @BindView(R.id.rb_hd_yes)
    RadioButton rb_hd_yes;
    @BindView(R.id.rb_hd_no)
    RadioButton rb_hd_no;
    @BindView(R.id.rg_bleeding)
    RadioGroup rg_bleeding;
    @BindView(R.id.rb_bleeing_yes)
    RadioButton rb_bleeing_yes;
    @BindView(R.id.rb_bleeing_no)
    RadioButton rb_bleeing_no;
    @BindView(R.id.rg_ri)
    RadioGroup rg_ri;
    @BindView(R.id.rb_ri_yes)
    RadioButton rb_ri_yes;
    @BindView(R.id.rb_ri_no)
    RadioButton rb_ri_no;
    @BindView(R.id.rg_pregenency)
    RadioGroup rg_pregenency;
    @BindView(R.id.rb_preg_yes)
    RadioButton rb_preg_yes;
    @BindView(R.id.rb_preg_no)
    RadioButton rb_preg_no;
    @BindView(R.id.rg_htn)
    RadioGroup rg_htn;
    @BindView(R.id.rb_htn_yes)
    RadioButton rb_htn_yes;
    @BindView(R.id.rb_htn_no)
    RadioButton rb_htn_no;
    @BindView(R.id.rg_tb)
    RadioGroup rg_tb;
    @BindView(R.id.rb_tb_yes)
    RadioButton rb_tb_yes;
    @BindView(R.id.rb_tb_no)
    RadioButton rb_tb_no;
    @BindView(R.id.rg_cancer)
    RadioGroup rg_cancer;
    @BindView(R.id.rb_cancer_yes)
    RadioButton rb_cancer_yes;
    @BindView(R.id.rb_cancer_no)
    RadioButton rb_cancer_no;
    @BindView(R.id.rg_aids)
    RadioGroup rg_aids;
    @BindView(R.id.rb_aids_yes)
    RadioButton rb_aids_yes;
    @BindView(R.id.rb_aids_no)
    RadioButton rb_aids_no;
    @BindView(R.id.rg_past_surgery)
    RadioGroup rg_past_surgery;
    @BindView(R.id.rb_past_surgery_yes)
    RadioButton rb_past_surgery_yes;
    @BindView(R.id.rb_past_surgery_no)
    RadioButton rb_past_surgery_no;
    @BindView(R.id.rg_allergy)
    RadioGroup rg_allergy;
    @BindView(R.id.rb_allergy_yes)
    RadioButton rb_allergy_yes;
    @BindView(R.id.rb_allergy_no)
    RadioButton rb_allergy_no;
    @BindView(R.id.rg_oster)
    RadioGroup rg_oster;
    @BindView(R.id.rb_oster_yes)
    RadioButton rb_oster_yes;
    @BindView(R.id.rb_oster_no)
    RadioButton rb_oster_no;
    @BindView(R.id.rg_depression)
    RadioGroup rg_depression;
    @BindView(R.id.rb_depression_yes)
    RadioButton rb_depression_yes;
    @BindView(R.id.rb_depression_no)
    RadioButton rb_depression_no;
    @BindView(R.id.rg_hepatitis)
    RadioGroup rg_hepatitis;
    @BindView(R.id.rb_hepatitis_yes)
    RadioButton rb_hepatitis_yes;
    @BindView(R.id.rb_hepatatis_no)
    RadioButton rb_hepatatis_no;
    @BindView(R.id.rg_implants)
    RadioGroup rg_implants;
    @BindView(R.id.rb_implants_yes)
    RadioButton rb_implants_yes;
    @BindView(R.id.rb_implants_no)
    RadioButton rb_implants_no;
    @BindView(R.id.rg_hereditary)
    RadioGroup rg_hereditary;
    @BindView(R.id.rb_herediatary_yes)
    RadioButton rb_herediatary_yes;
    @BindView(R.id.rb_herediatary_no)
    RadioButton rb_herediatary_no;

    public static UpdateHistoryFragment newInstance(String patientId, String assessmentType, HistoryItem historyItem) {
        UpdateHistoryFragment fragment = new UpdateHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);
        bundle.putSerializable("historyitem", historyItem);

        fragment.setArguments(bundle);
        return fragment;
    }

    public UpdateHistoryFragment() {
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
            historyItem = (HistoryItem) getArguments().getSerializable("historyitem");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_update_history, container, false);
        ButterKnife.bind(this, rootView);
        initView(rootView);
        setListener();
        return rootView;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        mBtnSave = (Button) view.findViewById(R.id.btn_save);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViews();


        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<NameValuePair> nameValuePairArrayList = new ArrayList<NameValuePair>();
                nameValuePairArrayList.add(new BasicNameValuePair("present_illness", et_present_illness_history.getText().toString()));
                nameValuePairArrayList.add(new BasicNameValuePair("past_illness", et_past_illness_history.getText().toString()));
                nameValuePairArrayList.add(new BasicNameValuePair("medical_history", et_medical_history.getText().toString()));
                nameValuePairArrayList.add(new BasicNameValuePair("surgical_history", et_surgical_history.getText().toString()));
                nameValuePairArrayList.add(new BasicNameValuePair("other_history", et_other_history.getText().toString()));
                nameValuePairArrayList.add(new BasicNameValuePair("medicine_used", et_medicine_used.getText().toString()));
                nameValuePairArrayList.add(new BasicNameValuePair("diabetes", submittedValue(rb_db_yes, rb_db_no)));
                nameValuePairArrayList.add(new BasicNameValuePair("blood_pressure", submittedBPValue(rb_bp_low, rb_bp_high, rb_bp_normal)));
                nameValuePairArrayList.add(new BasicNameValuePair("smoking", submittedValue(rb_smoking_yes, rb_smoking_no)));
                nameValuePairArrayList.add(new BasicNameValuePair("fever_and_chill", submittedValue(rb_fever_yes, rb_fever_no)));
                nameValuePairArrayList.add(new BasicNameValuePair("heart_diseases", submittedValue(rb_hd_yes, rb_hd_no)));
                nameValuePairArrayList.add(new BasicNameValuePair("bleeding_disorder", submittedValue(rb_bleeing_yes, rb_bleeing_no)));
                nameValuePairArrayList.add(new BasicNameValuePair("recent_infection", submittedValue(rb_ri_yes, rb_ri_no)));
                nameValuePairArrayList.add(new BasicNameValuePair("tb", submittedValue(rb_tb_yes, rb_tb_no)));
                nameValuePairArrayList.add(new BasicNameValuePair("cancer", submittedValue(rb_cancer_yes, rb_cancer_no)));
                nameValuePairArrayList.add(new BasicNameValuePair("hiv_aids", submittedValue(rb_aids_yes, rb_aids_no)));
                nameValuePairArrayList.add(new BasicNameValuePair("allergies", submittedValue(rb_allergy_yes, rb_allergy_no)));
                nameValuePairArrayList.add(new BasicNameValuePair("osteoporotic", submittedValue(rb_oster_yes, rb_oster_no)));
                nameValuePairArrayList.add(new BasicNameValuePair("any_implants", submittedValue(rb_implants_yes, rb_implants_no)));
                nameValuePairArrayList.add(new BasicNameValuePair("hereditary_disease", submittedValue(rb_herediatary_yes, rb_herediatary_no)));
                nameValuePairArrayList.add(new BasicNameValuePair("depression", et_remark.getText().toString()));
                nameValuePairArrayList.add(new BasicNameValuePair("history_id", historyItem.getId()));

                Log.d(TagUtils.getTag(), "namevalue pairs:-" + nameValuePairArrayList.toString());
                new WebServiceBaseFragment(nameValuePairArrayList, getActivity(), UpdateHistoryFragment.this, CALL_UPDATE_PATIENT_HISTORY).execute(ApiConfig.EDIT_HISTORY_);
            }
        });
    }

    @Override
    protected void setListener() {
        super.setListener();


    }

    public String submittedValue(RadioButton radio_yes, RadioButton radio_no) {
        if (radio_yes.isChecked()) {
            return "yes";
        } else {
            if (radio_no.isChecked()) {
                return "no";
            } else {
                return "";
            }
        }
    }

    public String submittedBPValue(RadioButton radio_low, RadioButton high, RadioButton normal) {
        if (radio_low.isChecked()) {
            return "low";
        } else {
            if (high.isChecked()) {
                return "high";
            } else if (normal.isChecked()) {
                return "normal";
            } else {
                return "";
            }
        }
    }

    public void setViews() {
        Log.d(TagUtils.getTag(), "history item:-" + historyItem.toString());
        et_present_illness_history.setText(historyItem.getPresent_illness());
        et_past_illness_history.setText(historyItem.getPast_illness());
        et_medical_history.setText(historyItem.getMedicalHistory());
        et_surgical_history.setText(historyItem.getSurgicalHistory());
        et_other_history.setText(historyItem.getOtherHistory());
        et_medicine_used.setText(historyItem.getMedicineUsed());
        et_remark.setText(historyItem.getDepression());

        checkValues(rb_db_yes, rb_db_no, historyItem.getDiabetes());
        checBPValues(rb_bp_low, rb_bp_high, rb_bp_normal, historyItem.getBp());
        checkValues(rb_smoking_yes, rb_smoking_no, historyItem.getSmoking());
        checkValues(rb_drinking_yes, rb_drinking_no, historyItem.getPregnancy());
        checkValues(rb_fever_yes, rb_fever_no, historyItem.getFever_and_chill());
        checkValues(rb_hd_yes, rb_hd_no, historyItem.getHeart_diseases());
        checkValues(rb_bleeing_yes, rb_bleeing_no, historyItem.getBleeding_disorder());
        checkValues(rb_ri_yes, rb_ri_no, historyItem.getRecent_infection());
//        checkValues(rb_preg_yes, rb_preg_no, historyItem.getPregnancy());
        checkValues(rb_htn_yes, rb_htn_no, historyItem.getHtn());
        checkValues(rb_tb_yes, rb_tb_no, historyItem.getTb());
        checkValues(rb_cancer_yes, rb_cancer_no, historyItem.getCancer());
        checkValues(rb_aids_yes, rb_aids_no, historyItem.getHiv_aids());
        checkValues(rb_past_surgery_yes, rb_past_surgery_no, historyItem.getPast_surgery());
        checkValues(rb_allergy_yes, rb_allergy_no, historyItem.getAllergies());
        checkValues(rb_oster_yes, rb_oster_no, historyItem.getOsteoporotic());
//        checkValues(rb_depression_yes, rb_depression_no, historyItem.getDepression());
        checkValues(rb_herediatary_yes, rb_herediatary_no, historyItem.getHereditary_disease());
        checkValues(rb_implants_yes, rb_implants_no, historyItem.getAny_implants());

    }

    public void checkValues(RadioButton rb_yes, RadioButton rb_no, String value) {
        if (value != null && !value.equals("")) {
            if (value.equalsIgnoreCase("yes")) {
                rb_yes.setChecked(true);
            } else {
                if (value.equalsIgnoreCase("no")) {
                    rb_no.setChecked(true);
                }
            }
        }
    }


    public void checBPValues(RadioButton rb_low, RadioButton rb_high, RadioButton rb_normal, String value) {
        if (value != null && !value.equals("")) {
            if (value.equalsIgnoreCase("high")) {
                rb_high.setChecked(true);
            } else {
                if (value.equalsIgnoreCase("normal")) {
                    rb_normal.setChecked(true);
                } else {
                    rb_low.setChecked(true);
                }
            }
        }
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case CALL_UPDATE_PATIENT_HISTORY:
                parseUpdatePatientHistory(response);
                break;
        }
    }

    public void parseUpdatePatientHistory(String response) {
        Log.d(TagUtils.getTag(), "patient history response:-" + response);
        try {
            if (new JSONObject(response).optString("success").equals("true")) {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "History updated");
                getFragmentManager().popBackStack();
            } else {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "Updation Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Server Down");
        }
    }
}