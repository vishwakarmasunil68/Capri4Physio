package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.capri4physio.R;
import com.capri4physio.adapter.assessment.MotorExamAdapter;
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
import java.util.List;

/**
 * Created by emobi5 on 7/2/2016.
 */
public class MotorExamFragment extends BaseFragment implements HttpUrlListener, ViewItemClickListener<MotorItem> {

    private RecyclerView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    public static ArrayList<InfoApps> contactDetails1;
    ListView listView;
    private MotorExamAdapter mAdapter;
    private List<MotorItem> mList;
    InfoApps Detailapp;
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
    public static MotorExamFragment newInstance(String patientId, String assessmentType) {
        MotorExamFragment fragment = new MotorExamFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);
        fragment.setArguments(bundle);
        return fragment;
    }

    public MotorExamFragment() {
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
        mRecyclerView.setAdapter(mAdapter);
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
                params.put(ApiConfig.ASSESSMENT_TYPE, assessmentType);

                /*params.put(KEY_moter_exam_data, "mansingh");
                params.put( KEY_moter_exam_date1,"1988-10-03");*/


                Log.e("id",patientId);
                Log.e("assessmentType",assessmentType);
                new UrlConnectionTask(getActivity(), ApiConfig.SIGNIN_URL, ApiConfig.ID1, true, params, MotorModel.class, this).execute("");

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


    public void onViewItemClick(MotorItem motorItem, int position, int actionId) {

    }

    private void addFragment() {
        startActivity(new Intent(getActivity(),AddMotorExamFragment.class));
        /*FragmentTransaction ft = getFragmentManager().beginTransaction();
        AddMotorExamFragment addComplaintFragment = AddMotorExamFragment.newInstance(patientId,assessmentType);
        ft.replace(R.id.fragment_container, addComplaintFragment);
        ft.addToBackStack(null);
        ft.commit();*/
    }

    private class CatagoryUrlAsynTask extends AsyncTask<String, String, String> {
        String id, catagoryName;


        @Override
        protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
            String content = HttpULRConnect.getData(ApiConfig.MOTER_EXAM_VIEW);
            Log.e("DoInBackGround ---->", String.valueOf(content));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                Log.e("DoInBackGroundtr", String.valueOf(s));
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray jsonArray = new JSONArray(s);
                Log.e("Post Method", jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String  moter_exam_data = jsonObject2.getString("patient_id");
                    /*String  smobile = jsonObject2.getString("smobile");
                    String semail = jsonObject2.getString("semail");
                    String sfirst_name=jsonObject2.getString("sfirst_name");*/
                    stringArrayList.add(moter_exam_data);
                    Log.e("stringArrayList",stringArrayList.toString());
                    Detailapp = new InfoApps();
                    Detailapp.setData(moter_exam_data);
                    /*Detailapp.setStr4(semail);//date
                    Detailapp.setId(id);
                    Detailapp.setNumber(smobile);//startTime*/
                    /*Detailapp.setAppname(reason);//reason
                    Detailapp.setDataAdd(patientname);
                    Detailapp.setPatientid(patientid);
                    Detailapp.setDoctorid(getuniqueid);*/
                    contactDetails1.add(Detailapp);
                    Collections.reverse(contactDetails1);
                    /*motorExamAdapter = new MotorExamAdapter(getActivity(), R.layout.moterview);
                    listView.setAdapter(motorExamAdapter);*/
                        /*Detailapp1 = new InfoApps();
                        Detailapp1.setName(username);*/
                        /*Detailapp.setAppname(trnsamount);
                        Detailapp.setDataAdd(trnsamounttype);*/
                        /*Detailapp.setName(username);
                        contactDetails.add(Detailapp);
                        contactAdapter = new LocationAdapter4(getApplicationContext(), R.layout.contactlisadap1);
                        contactList.setAdapter(contactAdapter);
                        Toast.makeText(getApplicationContext(),contactDetails.toString(),Toast.LENGTH_LONG).show();*/
//                        Toast.makeText(getApplicationContext(),userid,Toast.LENGTH_LONG).show();
//                        int value = jsonObject1.getInt(i + 1);
                    /*contactAdapter = new LocationAdapter4(getApplicationContext(), R.layout.contactlisadap1);
                    contactList.setAdapter(contactAdapter);*/
                }

            }catch(Exception e){
                e.printStackTrace();

            }
            //  CatagoryMetod();
        }}
}
