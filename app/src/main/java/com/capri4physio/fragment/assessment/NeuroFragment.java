package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBaseFragment;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.fragment.BaseFragment;
import com.capri4physio.model.assessment.MotorItem;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.pojo.notes.NeuroExamPOJO;
import com.capri4physio.util.HandlerConstant;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Emobi-Android-002 on 7/12/2016.
 */
public class NeuroFragment extends BaseFragment implements WebServicesCallBack {

    private static final String CALL_GET_NTP_EXAMS = "call_get_exams";
    private RecyclerView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    //    private PhysicalExamAdapter mAdapter;
    private List<MotorItem> mList;
    private int itemPosition;

    public static ArrayList<InfoApps> contactDetails1;
    private UsersAdapterNeuro_ mAdapter;

    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
    private String patientId = "";
    private String assessmentType = "";
    InfoApps Detailapp, Detailapp1;
    private Button mAdd, btn_skip;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CheifComplaintFragment.
     */
    public static NeuroFragment newInstance(String patientId, String assessmentType) {
        NeuroFragment fragment = new NeuroFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);

        fragment.setArguments(bundle);
        return fragment;
    }

    public NeuroFragment() {
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

        mList = new ArrayList<>();
//        mAdapter = new PhysicalExamAdapter(getActivity(), mList, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_assessment, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        contactDetails1 = new ArrayList<InfoApps>();
        mSnackBarLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setAdapter(mAdapter);
        mAdd = (Button) view.findViewById(R.id.btn_add);
        btn_skip = (Button) view.findViewById(R.id.btn_skip);
        getpnotes();
    }

    @Override
    protected void setListener() {
        super.setListener();
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment();
            }
        });
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                HandlerConstant.POP_BACK_HANDLER.sendMessage(HandlerConstant.POP_BACK_HANDLER.obtainMessage(0, "7"));
            }
        });
        HandlerConstant.POP_INNER_BACK_HANDLER = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                String message = (String) msg.obj;
                Log.d(TagUtils.getTag(), "pop back handler:-" + message);
                btn_skip.callOnClick();
                return false;
            }
        });
    }


    private void getpnotes() {
        ArrayList<NameValuePair> nameValuePairArrayList = new ArrayList<>();
        nameValuePairArrayList.add(new BasicNameValuePair("patient_id", patientId));
        new WebServiceBaseFragment(nameValuePairArrayList, getActivity(), this, CALL_GET_NTP_EXAMS).execute(ApiConfig.GET_NEURO_EXAM);

    }

    void addFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        com.capri4physio.fragment.AddNeuroFragment addNeuroFragment = com.capri4physio.fragment.AddNeuroFragment.newInstance(patientId, "add",null);
        ft.replace(R.id.fragment_container, addNeuroFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
    void ViewExam(com.capri4physio.pojo.notes.NeuroExamResultPOJO neuroExamResultPOJO) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        com.capri4physio.fragment.AddNeuroFragment addNeuroFragment = com.capri4physio.fragment.AddNeuroFragment.newInstance(patientId, "view",neuroExamResultPOJO);
        ft.replace(R.id.fragment_container, addNeuroFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
    void updateNeuroReport(com.capri4physio.pojo.notes.NeuroExamResultPOJO neuroExamResultPOJO) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        com.capri4physio.fragment.AddNeuroFragment addNeuroFragment = com.capri4physio.fragment.AddNeuroFragment.newInstance(patientId, "update",neuroExamResultPOJO);
        ft.replace(R.id.fragment_container, addNeuroFragment);
        ft.addToBackStack(null);
        ft.commit();
    }



    @Override
    public void onPause() {
        super.onPause();
        Log.e("start", "onpause");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Assesment");
    }

    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Neuroligical Examination");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
    }

    /**
     * @return none
     * @description Login web service API calling
     */


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_alert) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case CALL_GET_NTP_EXAMS:
                parseExamResponse(response);
                break;
        }
    }

    public void parseExamResponse(String response) {
        Log.d(TagUtils.getTag(), "exam response:-" + response);
        try {
            Gson gson = new Gson();
            NeuroExamPOJO ndtpojo = gson.fromJson(response, NeuroExamPOJO.class);
            if (ndtpojo.getSuccess().equals("true")) {
                Collections.reverse(ndtpojo.getNeuroExamResultPOJOList());
                UserAdapterNeuroExam mAdapter = new UserAdapterNeuroExam(ndtpojo.getNeuroExamResultPOJOList(), getActivity(), NeuroFragment.this);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "No Exams Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

