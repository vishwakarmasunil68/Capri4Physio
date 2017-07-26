package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.FragmentTransaction;
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
import android.widget.ListView;

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
import com.capri4physio.model.assessment.MotorItem;
import com.capri4physio.model.assessment.MotorModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by emobi5 on 7/2/2016.
 */
public class CaseNotesFragment extends BaseFragment implements HttpUrlListener, ViewItemClickListener<MotorItem> {

    private RecyclerView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    public static ArrayList<InfoApps> contactDetails1;
    ListView listView;
    private UsersAdapterCase_Notes mAdapter;
    private List<MotorItem> mList;
    InfoApps Detailapp,Detailapp1;
    ArrayList<String> stringArrayList;
    private int itemPosition;

    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_moter_exam_data = "moter_exam_data";
    private static final String KEY_moter_exam_date1 ="moter_exam_date";

    private static final String KEY_TYPE = "type";
    private String patientId = "";
    private String assessmentType = "";
    private Button mAdd;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CheifComplaintFragment.
     */
    public static CaseNotesFragment newInstance(String patientId, String assessmentType) {
        CaseNotesFragment fragment = new CaseNotesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);
        fragment.setArguments(bundle);
        return fragment;
    }

    public CaseNotesFragment() {
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
            assessmentType = getArguments().getString(KEY_moter_exam_data);
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
//        new CatagoryUrlAsynTask().execute();
        viewAssessmentApiCall();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mSnackBarLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        stringArrayList=new ArrayList<String>();
        mList = new ArrayList<>();
        contactDetails1=new ArrayList<InfoApps>();
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        getpnotes();
        mAdd= (Button) view.findViewById(R.id.btn_add);
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
//                params.put(ApiConfig.ASSESSMENT_TYPE, assessmentType);

                /*params.put(KEY_moter_exam_data, "mansingh");
                params.put( KEY_moter_exam_date1,"1988-10-03");*/


                Log.e("id",patientId);
                Log.e("assessmentType",assessmentType);
                new UrlConnectionTask(getActivity(), ApiConfig.GET_PROGRESS_NOTES, ApiConfig.ID1, true, params, MotorModel.class, this).execute("");

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
                    mList.addAll(model.result);
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
    public void onPause() {
        super.onStart();
        Log.e("start","onStart");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("case note");
    }
    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Case note ");
    }

    public void onViewItemClick(MotorItem motorItem, int position, int actionId) {

    }

    private void addFragment() {
//        startActivity(new Intent(getActivity(),AddMotorExamFragment.class));
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        AddCaseNoteFragment addCaseNoteFragment = AddCaseNoteFragment.newInstance(patientId,assessmentType);
        ft.replace(R.id.fragment_container, addCaseNoteFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void getpnotes(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.GET_CASE_NOTES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
//                            pDialog.dismiss();
                            JSONObject jsonObject1=new JSONObject(response);
                            String success=jsonObject1.getString("success");
                            if (success.equals("true")){
                                Log.e("success",response);
                            JSONArray jsonArray = jsonObject1.getJSONArray("result");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String moter_exam_casedate = jsonObject.getString("moter_exam_casedate");
                                    String moter_exam_casedesc = jsonObject.getString("moter_exam_casedesc");
                                    String moter_exam_case_id = jsonObject.getString("moter_exam_case_id");
                                    String patient_id = jsonObject.getString("patient_id");


                                    Detailapp1 = new InfoApps();
                                    Detailapp1.setSend_date(moter_exam_case_id);
                                    Detailapp1.setName(moter_exam_casedesc);
                                    Detailapp1.setNumber(moter_exam_casedate);
                                    Detailapp1.setId(patient_id);
                                    contactDetails1.add(Detailapp1);
                                    Collections.reverse(contactDetails1);
                                    Log.e("contactDetails1",contactDetails1.toString());

                                }
                                mAdapter = new UsersAdapterCase_Notes(contactDetails1, getActivity(),mSnackBarLayout);
                                mRecyclerView.setAdapter(mAdapter);
                                }
                            else {

                            }
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
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
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
}
