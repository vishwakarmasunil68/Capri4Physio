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
import com.capri4physio.pojo.notes.NdtResultPOJO;
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
public class AddNTPExamFragment extends BaseFragment implements WebServicesCallBack {
    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String CALL_NEW_NEURO_FRAGMENT = "call_new_neuro_fragment";
    private String patientId = "";
    private String type="";
    @BindView(R.id.Ulnar_left)
    EditText Ulnar_left;
    @BindView(R.id.Ulnar_right)
    EditText Ulnar_right;
    @BindView(R.id.Radial_left)
    EditText Radial_left;
    @BindView(R.id.Radial_right)
    EditText Radial_right;
    @BindView(R.id.Median1)
    EditText Median1;
    @BindView(R.id.Median2)
    EditText Median2;
    @BindView(R.id.Musculocutaneous1)
    EditText Musculocutaneous1;
    @BindView(R.id.Musculocutaneous2)
    EditText Musculocutaneous2;
    @BindView(R.id.Sciatic2_1)
    EditText Sciatic2_1;
    @BindView(R.id.Sciatic2_2)
    EditText Sciatic2_2;
    @BindView(R.id.Tibial1)
    EditText Tibial1;
    @BindView(R.id.Tibia2)
    EditText Tibia2;
    @BindView(R.id.Comman_peronial1)
    EditText Comman_peronial1;
    @BindView(R.id.Comman_peronial2)
    EditText Comman_peronial2;
    @BindView(R.id.Femoral1)
    EditText Femoral1;
    @BindView(R.id.Femoral2)
    EditText Femoral2;
    @BindView(R.id.Lateral_cutaneous1)
    EditText Lateral_cutaneous1;
    @BindView(R.id.Lateral_cutaneous2)
    EditText Lateral_cutaneous2;
    @BindView(R.id.Obturator1)
    EditText Obturator1;
    @BindView(R.id.Obturator2)
    EditText Obturator2;
    @BindView(R.id.Sural1)
    EditText Sural1;
    @BindView(R.id.Sural2)
    EditText Sural2;
    @BindView(R.id.sapp_left)
    EditText sapp_left;
    @BindView(R.id.sapp_right)
    EditText sapp_right;
    @BindView(R.id.Ulnar5_1)
    EditText Ulnar5_1;
    @BindView(R.id.Ulnar5_2)
    EditText Ulnar5_2;
    @BindView(R.id.Radial5_1)
    EditText Radial5_1;
    @BindView(R.id.Radial5_2)
    EditText Radial5_2;
    @BindView(R.id.Median5_1)
    EditText Median5_1;
    @BindView(R.id.Median5_2)
    EditText Median5_2;
    @BindView(R.id.Sciatic5_1)
    EditText Sciatic5_1;
    @BindView(R.id.Sciatic5_2)
    EditText Sciatic5_2;
    @BindView(R.id.Tibial5_1)
    EditText Tibial5_1;
    @BindView(R.id.Tibial5_2)
    EditText Tibial5_2;
    @BindView(R.id.Comman_peronial5_1)
    EditText Comman_peronial5_1;
    @BindView(R.id.Comman_peronial5_2)
    EditText Comman_peronial5_2;
    @BindView(R.id.Femoral5_1)
    EditText Femoral5_1;
    @BindView(R.id.Femoral5_2)
    EditText Femoral5_2;
    @BindView(R.id.Sural5_1)
    EditText Sural5_1;
    @BindView(R.id.Sural5_2)
    EditText Sural5_2;
    @BindView(R.id.Obturator5_1)
    EditText Obturator5_1;
    @BindView(R.id.Obturator5_2)
    EditText Obturator5_2;
    @BindView(R.id.btn_save)
    Button btn_save;

    NdtResultPOJO ndtResultPOJO;
    public static AddNTPExamFragment newInstance(String patientId, String type, NdtResultPOJO ndtResultPOJO) {
        AddNTPExamFragment fragment = new AddNTPExamFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString("type", type);
        bundle.putSerializable("exam", ndtResultPOJO);
        fragment.setArguments(bundle);
        return fragment;
    }

    public AddNTPExamFragment() {
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
            ndtResultPOJO = (NdtResultPOJO) getArguments().getSerializable("exam");
//            assessmentType = getArguments().getString(KEY_TYPE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_new_neuro, container, false);
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
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("NDT,NTP Examination");

        if(type.equals("update")){
            btn_save.setText("Update");


            Ulnar_left.setText(ndtResultPOJO.getNdtulnarleft());
            Ulnar_right.setText(ndtResultPOJO.getNdtulnarright());
            Radial_left.setText(ndtResultPOJO.getNdtradianleft());
            Radial_right.setText(ndtResultPOJO.getNdtradianright());
            Median1.setText(ndtResultPOJO.getNdtmedianleft());
            Median2.setText(ndtResultPOJO.getNdtmedianright());
            Musculocutaneous1.setText(ndtResultPOJO.getNdtmusculocleft());
            Musculocutaneous2.setText(ndtResultPOJO.getNdtmusculocright());
            Sciatic2_1.setText(ndtResultPOJO.getNdtsciaticleft());
            Sciatic2_2.setText(ndtResultPOJO.getNdtsciaticright());
            Tibial1.setText(ndtResultPOJO.getNdttibialleft());
            Tibia2.setText(ndtResultPOJO.getNdttibialright());
            Comman_peronial1.setText(ndtResultPOJO.getNdtcpleft());
            Comman_peronial2.setText(ndtResultPOJO.getNdtcpright());
            Femoral1.setText(ndtResultPOJO.getNdtfemoralleft());
            Femoral2.setText(ndtResultPOJO.getNdtfemoralright());
            Lateral_cutaneous1.setText(ndtResultPOJO.getNdtcutaneousleft());
            Lateral_cutaneous2.setText(ndtResultPOJO.getNdtcutaneousright());
            Obturator1.setText(ndtResultPOJO.getNdtobturatorleft());
            Obturator2.setText(ndtResultPOJO.getNdtobturatorright());
            Sural1.setText(ndtResultPOJO.getNdtsuralleft());
            Sural2.setText(ndtResultPOJO.getNdtsuralright());
            sapp_left.setText(ndtResultPOJO.getNdtsaphenousleft());
            sapp_right.setText(ndtResultPOJO.getNdtsaphenousright());
            Ulnar5_1.setText(ndtResultPOJO.getNtpulnarleft());
            Ulnar5_2.setText(ndtResultPOJO.getNtpulnarright());
            Radial5_1.setText(ndtResultPOJO.getNtpradianleft());
            Radial5_2.setText(ndtResultPOJO.getNtpradianright());
            Median5_1.setText(ndtResultPOJO.getNtpmedianleft());
            Median5_2.setText(ndtResultPOJO.getNtpmedianright());
            Sciatic5_1.setText(ndtResultPOJO.getNtpsciaticleft());
            Sciatic5_2.setText(ndtResultPOJO.getNtpsciaticright());
            Tibial5_1.setText(ndtResultPOJO.getNtptibialleft());
            Tibial5_2.setText(ndtResultPOJO.getNtptibialright());
            Comman_peronial5_1.setText(ndtResultPOJO.getNtpperonialleft());
            Comman_peronial5_2.setText(ndtResultPOJO.getNtpparonialright());
            Femoral5_1.setText(ndtResultPOJO.getNtpfemoralleft());
            Femoral5_2.setText(ndtResultPOJO.getNtpfemoralright());
            Sural5_1.setText(ndtResultPOJO.getNtpsuralleft());
            Sural5_2.setText(ndtResultPOJO.getNtpsuralright());
            Obturator5_1.setText(ndtResultPOJO.getNtpsaphenousleft());
            Obturator5_2.setText(ndtResultPOJO.getNtpsaphenousright());


        }else{
            if(type.equals("view")){
                Ulnar_left.setText(ndtResultPOJO.getNdtulnarleft());
                Ulnar_right.setText(ndtResultPOJO.getNdtulnarright());
                Radial_left.setText(ndtResultPOJO.getNdtradianleft());
                Radial_right.setText(ndtResultPOJO.getNdtradianright());
                Median1.setText(ndtResultPOJO.getNdtmedianleft());
                Median2.setText(ndtResultPOJO.getNdtmedianright());
                Musculocutaneous1.setText(ndtResultPOJO.getNdtmusculocleft());
                Musculocutaneous2.setText(ndtResultPOJO.getNdtmusculocright());
                Sciatic2_1.setText(ndtResultPOJO.getNdtsciaticleft());
                Sciatic2_2.setText(ndtResultPOJO.getNdtsciaticright());
                Tibial1.setText(ndtResultPOJO.getNdttibialleft());
                Tibia2.setText(ndtResultPOJO.getNdttibialright());
                Comman_peronial1.setText(ndtResultPOJO.getNdtcpleft());
                Comman_peronial2.setText(ndtResultPOJO.getNdtcpright());
                Femoral1.setText(ndtResultPOJO.getNdtfemoralleft());
                Femoral2.setText(ndtResultPOJO.getNdtfemoralright());
                Lateral_cutaneous1.setText(ndtResultPOJO.getNdtcutaneousleft());
                Lateral_cutaneous2.setText(ndtResultPOJO.getNdtcutaneousright());
                Obturator1.setText(ndtResultPOJO.getNdtobturatorleft());
                Obturator2.setText(ndtResultPOJO.getNdtobturatorright());
                Sural1.setText(ndtResultPOJO.getNdtsuralleft());
                Sural2.setText(ndtResultPOJO.getNdtsuralright());
                sapp_left.setText(ndtResultPOJO.getNdtsaphenousleft());
                sapp_right.setText(ndtResultPOJO.getNdtsaphenousright());
                Ulnar5_1.setText(ndtResultPOJO.getNtpulnarleft());
                Ulnar5_2.setText(ndtResultPOJO.getNtpulnarright());
                Radial5_1.setText(ndtResultPOJO.getNtpradianleft());
                Radial5_2.setText(ndtResultPOJO.getNtpradianright());
                Median5_1.setText(ndtResultPOJO.getNtpmedianleft());
                Median5_2.setText(ndtResultPOJO.getNtpmedianright());
                Sciatic5_1.setText(ndtResultPOJO.getNtpsciaticleft());
                Sciatic5_2.setText(ndtResultPOJO.getNtpsciaticright());
                Tibial5_1.setText(ndtResultPOJO.getNtptibialleft());
                Tibial5_2.setText(ndtResultPOJO.getNtptibialright());
                Comman_peronial5_1.setText(ndtResultPOJO.getNtpperonialleft());
                Comman_peronial5_2.setText(ndtResultPOJO.getNtpparonialright());
                Femoral5_1.setText(ndtResultPOJO.getNtpfemoralleft());
                Femoral5_2.setText(ndtResultPOJO.getNtpfemoralright());
                Sural5_1.setText(ndtResultPOJO.getNtpsuralleft());
                Sural5_2.setText(ndtResultPOJO.getNtpsuralright());
                Obturator5_1.setText(ndtResultPOJO.getNtpsaphenousleft());
                Obturator5_2.setText(ndtResultPOJO.getNtpsaphenousright());

                btn_save.setVisibility(View.GONE);
            }
        }
    }

    public void callNeuroAddAPI() {


        ArrayList<NameValuePair> nameValuePairArrayList = new ArrayList<>();

        nameValuePairArrayList.add(new BasicNameValuePair("ndtulnarleft", Ulnar_left.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtulnarright", Ulnar_right.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtradianleft", Radial_left.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtradianright", Radial_right.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtmedianleft", Median1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtmedianright", Median2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtmusculocleft", Musculocutaneous1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtmusculocright", Musculocutaneous2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtsciaticleft", Sciatic2_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtsciaticright", Sciatic2_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndttibialleft", Tibial1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndttibialright", Tibia2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtcpleft", Comman_peronial1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtcpright", Comman_peronial2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtfemoralleft", Femoral1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtfemoralright", Femoral2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtcutaneousleft", Lateral_cutaneous1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtcutaneousright", Lateral_cutaneous2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtobturatorleft", Obturator1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtobturatorright", Obturator2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtsuralleft", Sural1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtsuralright", Sural2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtsaphenousleft", sapp_left.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtsaphenousright", sapp_right.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpulnarleft", Ulnar5_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpulnarright", Ulnar5_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpradianleft", Radial5_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpradianright", Radial5_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpmedianleft", Median5_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpmedianright", Median5_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpsciaticleft", Sciatic5_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpsciaticright", Sciatic5_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntptibialleft", Tibial5_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntptibialright", Tibial5_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpperonialleft", Comman_peronial5_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpparonialright", Comman_peronial5_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpfemoralleft", Femoral5_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpfemoralright", Femoral5_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpsuralleft", Sural5_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpsuralright", Sural5_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpsaphenousleft", Obturator5_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpsaphenousright", Obturator5_2.getText().toString()));

        nameValuePairArrayList.add(new BasicNameValuePair("patient_id", patientId));
        if(type.equals("update")) {
            nameValuePairArrayList.add(new BasicNameValuePair("ndtid", ndtResultPOJO.getNdtid()));
            nameValuePairArrayList.add(new BasicNameValuePair("ntpdate", ndtResultPOJO.getNtpdate()));
            new WebServiceBaseFragment(nameValuePairArrayList, getActivity(), this, CALL_NEW_NEURO_FRAGMENT).execute(ApiConfig.UPDATE_NTP_EXAM);
        }else{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date d=new Date();
            nameValuePairArrayList.add(new BasicNameValuePair("ntpdate", sdf.format(d)));
            new WebServiceBaseFragment(nameValuePairArrayList, getActivity(), this, CALL_NEW_NEURO_FRAGMENT).execute(ApiConfig.ADD_NTP_EXAM);
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
        actionBar.setTitle("NDT,NTP Examination");
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
