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

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBaseFragment;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.pojo.notes.ADLResultPOJO;
import com.capri4physio.util.HandlerConstant;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Emobi-Android-002 on 7/12/2016.
 */
public class AddADLExamFragment extends BaseFragment implements WebServicesCallBack {
    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String CALL_NEW_NEURO_FRAGMENT = "call_new_neuro_fragment";
    private String patientId = "";
    private String type="";

    @BindView(R.id.Special_Tests)
    EditText et_special_test;
    @BindView(R.id.Description1)
    EditText et_special_description;
    @BindView(R.id.Name)
    EditText et_name;
    @BindView(R.id.Description4)
    EditText et_description;

    @BindView(R.id.btn_save)
    Button btn_save;

    ADLResultPOJO adlResultPOJO;
    public static AddADLExamFragment newInstance(String patientId, String type,ADLResultPOJO adlResultPOJO) {
        AddADLExamFragment fragment = new AddADLExamFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString("type", type);
        bundle.putSerializable("exam", adlResultPOJO);
        fragment.setArguments(bundle);
        return fragment;
    }

    public AddADLExamFragment() {
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
            adlResultPOJO = (ADLResultPOJO) getArguments().getSerializable("exam");
//            assessmentType = getArguments().getString(KEY_TYPE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_add_adl, container, false);
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


        if(type.equals("update")){
            btn_save.setText("Update");

            et_name.setText(adlResultPOJO.getAdlname());
            et_description.setText(adlResultPOJO.getAdldescription());
            et_special_test.setText(adlResultPOJO.getSpecialtest());
            et_special_description.setText(adlResultPOJO.getSpecialdescription());



        }else{
            if(type.equals("view")){

                et_name.setText(adlResultPOJO.getAdlname());
                et_description.setText(adlResultPOJO.getAdldescription());
                et_special_test.setText(adlResultPOJO.getSpecialtest());
                et_special_description.setText(adlResultPOJO.getSpecialdescription());
                btn_save.setVisibility(View.GONE);
            }
        }
    }

    public void callNeuroAddAPI() {


        ArrayList<NameValuePair> nameValuePairArrayList = new ArrayList<>();

        nameValuePairArrayList.add(new BasicNameValuePair("adlname", et_name.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("adldescription", et_description.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("specialtest", et_special_test.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("specialdescription", et_special_description.getText().toString()));
//        nameValuePairArrayList.add(new BasicNameValuePair("ndtmedianleft", Median1.getText().toString()));
//
        nameValuePairArrayList.add(new BasicNameValuePair("patient_id", patientId));
        if(type.equals("update")) {
            nameValuePairArrayList.add(new BasicNameValuePair("adlid", adlResultPOJO.getAdlid()));
            nameValuePairArrayList.add(new BasicNameValuePair("adldate", adlResultPOJO.getAdldate()));
            new WebServiceBaseFragment(nameValuePairArrayList, getActivity(), this, CALL_NEW_NEURO_FRAGMENT).execute(ApiConfig.UPDATE_ADL_EXAM);
        }else{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date d=new Date();
            nameValuePairArrayList.add(new BasicNameValuePair("adldate", sdf.format(d)));
            new WebServiceBaseFragment(nameValuePairArrayList, getActivity(), this, CALL_NEW_NEURO_FRAGMENT).execute(ApiConfig.ADD_ADL_EXAM);
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
        actionBar.setTitle("Special and ADL Exam");
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
