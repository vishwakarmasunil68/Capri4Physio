package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.fragment.BaseFragment;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.model.assessment.MotorModel;
import com.capri4physio.model.assessment.PhysicalExamModel;
import com.capri4physio.model.assessment.PhysicalItem;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 7/12/2016.
 */
public class SensoryFragment extends BaseFragment implements HttpUrlListener, ViewItemClickListener<PhysicalItem> {

    private RecyclerView mRecyclerView;
    public static ArrayList<InfoApps> contactDetails1;
    private CoordinatorLayout mSnackBarLayout;
    private UsersAdapterSensory_ mAdapter;
    private List<PhysicalItem> mList;
    private int itemPosition;

    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
    private String patientId = "";
    private String assessmentType = "";
    private Button mAdd;
    InfoApps Detailapp,Detailapp1;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CheifComplaintFragment.
     */
    public static SensoryFragment newInstance(String patientId, String assessmentType) {
        SensoryFragment fragment = new SensoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);

        fragment.setArguments(bundle);
        return fragment;
    }

    public SensoryFragment() {
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
        viewAssessmentApiCall();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        contactDetails1=new ArrayList<InfoApps>();
//        contactDetails1.add("sbfjk");
        mSnackBarLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdd= (Button) view.findViewById(R.id.btn_add);
        mList = new ArrayList<>();
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
    }


    /**
     * @return none
     * @description view assessment web service API calling
     */
    private void viewAssessmentApiCall() {

        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.PATIENT_ID, patientId);
                params.put(ApiConfig.ASSESSMENT_TYPE, assessmentType);

                new UrlConnectionTask(getActivity(), ApiConfig.VIEW_ASSESSMENT_URL, ApiConfig.ID1, true, params, PhysicalExamModel.class, this).execute("");

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
                MotorModel model = (MotorModel) response;
                AppLog.i("Capri4Physio", "Assessment type - history: " + model.getStatus());
                if (model.result.size() > 0) {
                    mList.clear();
//                    mList.addAll(model.result);
                    mAdapter.notifyDataSetChanged();
                }
                break;

            case ApiConfig.ID2:
                BaseModel deleteResponse = (BaseModel) response;
                AppLog.i("Capri4Physio", "Patient delete Response : " + deleteResponse.getStatus());
                if (deleteResponse.getStatus() > 0) {
                    mList.remove(itemPosition);
                    mAdapter.notifyDataSetChanged();
                    showSnackMessage(deleteResponse.getMessage());
                }
                break;
        }

    }

    @Override
    public void onPostError(String errMsg, int id) {

    }


    private void showSnackMessage(String msg) {
        Snackbar snack = Snackbar.make(mSnackBarLayout, msg, Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snack.getView();
        group.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBtnNormal));
        snack.setActionTextColor(Color.WHITE);
        snack.show();
    }


    @Override
    public void onViewItemClick(PhysicalItem physicalItem, int position, int actionId) {

    }
    private void getpnotes(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_SENSORY4_REPORT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
//
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Detailapp1 = new InfoApps();
                                    Detailapp1.setPatientid(jsonObject.getString("patient_id"));
                                    Detailapp1.setAge(jsonObject.getString("sensory_exam_id"));
                                    Detailapp1.setSend_date(jsonObject.getString("sensory_exam_date"));
                                    Detailapp1.setDesig(jsonObject.getString("sensory_occipital_port"));
                                    Detailapp1.setExp(jsonObject.getString("sensory_neck_musc"));
                                    Detailapp1.setId(jsonObject.getString("sensory_supra_fossa"));
                                    Detailapp1.setAppname(jsonObject.getString("sensory_neck_lateral"));
                                    Detailapp1.setOne(jsonObject.getString("sensory_acrom_joint"));
                                    Detailapp1.setTwo(jsonObject.getString("sensory_shoulder_elevation"));
                                    Detailapp1.setThree(jsonObject.getString("sensory_ante_fossa"));
                                    Detailapp1.setFour(jsonObject.getString("sensory_shoulder_abduction"));
                                    Detailapp1.setFive(jsonObject.getString("sensory_biceps_brachi"));
                                    Detailapp1.setSix(jsonObject.getString("sensory_thumb"));
                                    Detailapp1.setSeven(jsonObject.getString("sensory_biceps_supin_wrist"));
                                    Detailapp1.setEight(jsonObject.getString("sensory_biceps_brachi1"));
                                    Detailapp1.setNine(jsonObject.getString("sensory_middle_finger"));
                                    Detailapp1.setTen(jsonObject.getString("sensory_wrist_flex"));
                                    Detailapp1.setEleven(jsonObject.getString("sensory_triceps"));
                                    Detailapp1.setFourteen(jsonObject.getString("sensory_little_finger"));
                                    Detailapp1.setFiftn(jsonObject.getString("sensory_thumb_extensors"));
                                    Detailapp1.setSixt(jsonObject.getString("sensory_triceps1"));
                                    Detailapp1.setSevent(jsonObject.getString("sensory_medial_side"));
                                    Detailapp1.setEightt(jsonObject.getString("sensory_apexaxilla"));
                                    Detailapp1.setNinet(jsonObject.getString("sensory_nipples"));
                                    Detailapp1.setTwenty(jsonObject.getString("sensory_xiphisternum"));
                                    Detailapp1.setTwentyone(jsonObject.getString("sensory_umbilicus"));
                                    Detailapp1.setTwentytwo(jsonObject.getString("sensory_midpoint_inguinal"));
                                    Detailapp1.setTwentythree(jsonObject.getString("sensory_mid_anterior"));
                                    Detailapp1.setTwentyfour(jsonObject.getString("sensory_hip_flexion"));
                                    Detailapp1.setTwentyfive(jsonObject.getString("sensory_patellar"));
                                    Detailapp1.setTwentysix(jsonObject.getString("sensory_medial_epicondyle"));
                                    Detailapp1.setTwentyseven(jsonObject.getString("sensory_knee_extension"));
                                    Detailapp1.setTwentyeight(jsonObject.getString("sensory_painslr"));
                                    Detailapp1.setTwentynine(jsonObject.getString("sensory_medial_malleolus"));
                                    Detailapp1.setThirtyone(jsonObject.getString("sensory_ankle_dorsi"));
                                    Detailapp1.setThirtytwo(jsonObject.getString("sensory_dorsum_foot"));
                                    Detailapp1.setThirtythree(jsonObject.getString("sensory_extensor"));
                                    Detailapp1.setThirtyfour(jsonObject.getString("sensory_Lateral_heel"));
                                    Detailapp1.setThirtyfive(jsonObject.getString("sensory_ankle_plantar"));
                                    Detailapp1.setThirtysix(jsonObject.getString("sensory_limitedslr"));
                                    Detailapp1.setThirtyseven(jsonObject.getString("sensory_popliteal_fossa"));
                                    Detailapp1.setThirtyeight(jsonObject.getString("sensory_knee_flexion"));
                                    Detailapp1.setThirty(jsonObject.getString("sensory_limitedslr_achilles"));
                                    Detailapp1.setThirtynine(jsonObject.getString("sensory_perianal"));
                                    Detailapp1.setFourty(jsonObject.getString("sensory_bladder_rectum"));
                                    Detailapp1.setSeventysix(jsonObject.getString("sensory_saphenres_left"));
                                    Detailapp1.setSeventyseven(jsonObject.getString("sensory_saphenres_right"));

                                   /* Detailapp1 = new InfoApps();
                                    Detailapp1.setSend_date(sensory_exam_date);
                                    Detailapp1.setName(sensory_occipital_port);
                                   */
                                    contactDetails1.add(Detailapp1);
                                    Collections.reverse(contactDetails1);
                                    Log.e("contactDetails1",contactDetails1.toString());

                                }
                                mAdapter = new UsersAdapterSensory_(contactDetails1, getActivity(),mSnackBarLayout);
                                mRecyclerView.setAdapter(mAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.e("Postdat", "" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                    }
                }){


            protected Map<String,String> getParams(){
                Map<String,String> objresponse = new HashMap<String, String>();
                objresponse.put("patient_id", patientId);
                Log.d(TagUtils.getTag(),"patient id:-"+patientId);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    private void addFragment() {
        /*FragmentTransaction ft = getFragmentManager().beginTransaction();
        *//*AddSensoryFragment sensoryFragment = AddSensoryFragment.newInstance(patientId, assessmentType);
        ft.replace(R.id.fragment_container, sensoryFragment);*//*
        ft.addToBackStack(null);
        ft.commit();*/
        Intent intent=new Intent(getActivity(),FragementActiSensory1.class);
        intent.putExtra("patient_id",patientId);
        startActivityForResult(intent,1);
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.e("start","onStart");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Sensory Exam");
    }
    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Sensory Exam");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1){
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.main,menu);
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
/*@Override
    public void onResume() {
        super.onResume();
        if (FragementActiSensory1.status = true){
            getFragmentManager().popBackStack();
        }
    }*/
}

