package com.capri4physio.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.HandlerConstant;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Emobi-Android-002 on 7/12/2016.
 */
public class AddNeuroFragment extends BaseFragment implements WebServicesCallBack {
    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String CALL_NEW_NEURO_FRAGMENT = "call_new_neuro_fragment";
    private String patientId = "";
    private String type = "";

    @BindView(R.id.btn_save)
    Button btn_save;

    @BindView(R.id.et_fn_time_taken)
    EditText et_fn_time_taken;
    @BindView(R.id.et_hs_speed)
    EditText et_hs_speed;
    @BindView(R.id.et_al_error)
    EditText et_al_error;
    @BindView(R.id.et_fn_error)
    EditText et_fn_error;

    @BindView(R.id.et_fn_speed)
    EditText et_fn_speed;
    @BindView(R.id.et_al_time)
    EditText et_al_time;
    @BindView(R.id.et_al_speed)
    EditText et_al_speed;
    @BindView(R.id.et_hs_time)
    EditText et_hs_time;
    @BindView(R.id.et_hs_error)
    EditText et_hs_error;
    @BindView(R.id.et_balance_left)
    EditText et_balance_left;
    @BindView(R.id.et_balance_right)
    EditText et_balance_right;
    @BindView(R.id.spinner1)
    Spinner spinner1;
    @BindView(R.id.spinner2)
    Spinner spinner2;
    @BindView(R.id.spinner3)
    Spinner spinner3;
    @BindView(R.id.spinner7_1)
    Spinner spinner7_1;
    @BindView(R.id.spinner7_2)
    Spinner spinner7_2;
    @BindView(R.id.spinner7_3)
    Spinner spinner7_3;
    @BindView(R.id.spinner7_4)
    Spinner spinner7_4;
    @BindView(R.id.spinner7_5)
    Spinner spinner7_5;
    @BindView(R.id.spinner7_6)
    Spinner spinner7_6;
    @BindView(R.id.spinner7_7)
    Spinner spinner7_7;
    @BindView(R.id.spinner7_8)
    Spinner spinner7_8;
    @BindView(R.id.spinner8_1)
    Spinner spinner8_1;
    @BindView(R.id.spinner8_2)
    Spinner spinner8_2;
    @BindView(R.id.spinner8_3)
    Spinner spinner8_3;
    @BindView(R.id.spinner8_4)
    Spinner spinner8_4;
    @BindView(R.id.spinner8_5)
    Spinner spinner8_5;
    @BindView(R.id.spinner8_6)
    Spinner spinner8_6;
    @BindView(R.id.spinner8_7)
    Spinner spinner8_7;
    @BindView(R.id.spinner8_8)
    Spinner spinner8_8;
    @BindView(R.id.spinner8_9)
    Spinner spinner8_9;
    @BindView(R.id.spinner8_10)
    Spinner spinner8_10;

    com.capri4physio.pojo.notes.NeuroExamResultPOJO neuroExamResultPOJO;

    public static AddNeuroFragment newInstance(String patientId, String type, com.capri4physio.pojo.notes.NeuroExamResultPOJO neuroExamResultPOJO) {
        AddNeuroFragment fragment = new AddNeuroFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString("type", type);
        bundle.putSerializable("exam", neuroExamResultPOJO);
        fragment.setArguments(bundle);
        return fragment;
    }

    public AddNeuroFragment() {
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
            type = getArguments().getString("type");
            neuroExamResultPOJO = (com.capri4physio.pojo.notes.NeuroExamResultPOJO) getArguments().getSerializable("exam");
//            assessmentType = getArguments().getString(KEY_TYPE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_neuro, container, false);
        ButterKnife.bind(this, rootView);
        initView(rootView);
        setListener();
        return rootView;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNeuroAddAPI();
            }
        });

        if (type.equals("update")) {
            btn_save.setText("Update");

            setValues();
        } else {
            if (type.equals("view")) {

                setValues();
                btn_save.setVisibility(View.GONE);
            }
        }
    }

    public void setValues() {
        setSpinnerValue(spinner1, neuroExamResultPOJO.getGcseye());
        setSpinnerValue(spinner2, neuroExamResultPOJO.getGcsverbal());
        setSpinnerValue(spinner3, neuroExamResultPOJO.getGcsmotor());
        setSpinnerValue(spinner7_1, neuroExamResultPOJO.getGaitsurface());
        setSpinnerValue(spinner7_2, neuroExamResultPOJO.getGaitspeed());
        setSpinnerValue(spinner7_3, neuroExamResultPOJO.getGaithorizontal());
        setSpinnerValue(spinner7_4, neuroExamResultPOJO.getGaitvertical());
        setSpinnerValue(spinner7_5, neuroExamResultPOJO.getGaitpivot());
        setSpinnerValue(spinner7_6, neuroExamResultPOJO.getGaitover());
        setSpinnerValue(spinner7_7, neuroExamResultPOJO.getGaitaround());
        setSpinnerValue(spinner7_8, neuroExamResultPOJO.getGaitsteps());
        setSpinnerValue(spinner8_1, neuroExamResultPOJO.getFtbowel());
        setSpinnerValue(spinner8_2, neuroExamResultPOJO.getFtbladder());
        setSpinnerValue(spinner8_3, neuroExamResultPOJO.getFttoilet());
        setSpinnerValue(spinner8_4, neuroExamResultPOJO.getFtgromming());
        setSpinnerValue(spinner8_5, neuroExamResultPOJO.getFtfeeding());
        setSpinnerValue(spinner8_6, neuroExamResultPOJO.getFttransfer());
        setSpinnerValue(spinner8_7, neuroExamResultPOJO.getFtmobility());
        setSpinnerValue(spinner8_8, neuroExamResultPOJO.getFtdressing());
        setSpinnerValue(spinner8_9, neuroExamResultPOJO.getFtstairs());
        setSpinnerValue(spinner8_10, neuroExamResultPOJO.getFtbathing());
        et_fn_time_taken.setText(neuroExamResultPOJO.getCtfntime());
        et_fn_speed.setText(neuroExamResultPOJO.getCtfnspeed());
        et_al_time.setText(neuroExamResultPOJO.getCtastime());
        et_al_speed.setText(neuroExamResultPOJO.getCtasspeed());
        et_al_speed.setText(neuroExamResultPOJO.getCtasspeed());
        et_hs_time.setText(neuroExamResultPOJO.getCthstime());
        et_hs_error.setText(neuroExamResultPOJO.getCthserror());
        et_balance_left.setText(neuroExamResultPOJO.getBalanceleft());
        et_balance_right.setText(neuroExamResultPOJO.getBalanceright());
        et_hs_speed.setText(neuroExamResultPOJO.getCthsspeed());
        et_al_error.setText(neuroExamResultPOJO.getCtaserror());
        et_fn_error.setText(neuroExamResultPOJO.getCtfnerror());
    }

    public void setSpinnerValue(Spinner spinner, String value) {
        String[] painarray = getActivity().getResources().getStringArray(R.array.neuro_of_pain);
        List<String> painList = new ArrayList<>(Arrays.asList(painarray));
        if (painList.contains(value)) {
            spinner.setSelection(painList.indexOf(value));
        }
    }

    public void callNeuroAddAPI() {


        ArrayList<NameValuePair> nameValuePairArrayList = new ArrayList<>();

        nameValuePairArrayList.add(new BasicNameValuePair("gcseye", spinner1.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gcsverbal", spinner2.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gcsmotor", spinner3.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ctfntime", et_fn_time_taken.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ctfnspeed", et_fn_speed.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ctfnerror", et_fn_error.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ctastime", et_al_speed.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ctasspeed", et_al_speed.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ctaserror", et_al_error.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("cthstime", et_hs_time.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("cthsspeed", et_hs_speed.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("cthserror", et_hs_error.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gaitsurface", spinner7_1.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gaitspeed", spinner7_2.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gaithorizontal", spinner7_3.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gaitvertical", spinner7_4.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gaitpivot", spinner7_5.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gaitover", spinner7_6.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gaitaround", spinner7_7.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gaitsteps", spinner7_8.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("balanceleft", et_balance_left.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("balanceright", et_balance_right.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ftbowel", spinner8_1.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ftbladder", spinner8_2.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("fttoilet", spinner8_3.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ftgromming", spinner8_4.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ftfeeding", spinner8_5.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("fttransfer", spinner8_6.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ftmobility", spinner8_7.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ftdressing", spinner8_8.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ftstairs", spinner8_9.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ftbathing", spinner8_10.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("patient_id", patientId));
        if (type.equals("update")) {
            nameValuePairArrayList.add(new BasicNameValuePair("gcid", neuroExamResultPOJO.getGcid()));
            nameValuePairArrayList.add(new BasicNameValuePair("neurodate", neuroExamResultPOJO.getNeurodate()));
            new WebServiceBaseFragment(nameValuePairArrayList, getActivity(), this, CALL_NEW_NEURO_FRAGMENT).execute(ApiConfig.UPDATE_NEURO_EXM);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new Date();
            nameValuePairArrayList.add(new BasicNameValuePair("neurodate", sdf.format(d)));
            new WebServiceBaseFragment(nameValuePairArrayList, getActivity(), this, CALL_NEW_NEURO_FRAGMENT).execute(ApiConfig.ADD_NEURO_EXM);
        }
    }

    @Override
    protected void setListener() {
        super.setListener();

    }

    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Neuroligical Examination");
    }

    @Override
    public void onGetMsg(String[] msg) {
        String response = msg[1];
        Log.d(TagUtils.getTag(), "add neuro exam:-" + response);
        try {
            if (new JSONObject(response).optString("success").equals("true")) {
                if(type.equalsIgnoreCase("update")) {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), "Report Updated");
                    getActivity().getFragmentManager().popBackStack();
                }else{
                    ToastClass.showShortToast(getActivity().getApplicationContext(), "Report Added");
                    getActivity().getFragmentManager().popBackStack();
                    HandlerConstant.POP_INNER_BACK_HANDLER.sendMessage(HandlerConstant.POP_INNER_BACK_HANDLER.obtainMessage(0, ""));
                }

            } else {
                ToastClass.showShortToast(getActivity(), "Failed to add report");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getActivity(), "Server Down");
        }
    }
}
