package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.capri4physio.util.HandlerConstant;
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
 * Created by emobi5 on 7/2/2016.
 */
public class ProgressNotesFragment extends BaseFragment implements HttpUrlListener, ViewItemClickListener<MotorItem> {

    private RecyclerView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    public static ArrayList<InfoApps> contactDetails1;
    ListView listView;
    private UsersAdapterProgres_Notes mAdapter;
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
    private Button mAdd,btn_skip;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CheifComplaintFragment.
     */
    public static ProgressNotesFragment newInstance(String patientId, String assessmentType) {
        ProgressNotesFragment fragment = new ProgressNotesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);
        fragment.setArguments(bundle);
        return fragment;
    }

    public ProgressNotesFragment() {
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
        getpnotes();
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
        btn_skip= (Button) view.findViewById(R.id.btn_skip);
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
                HandlerConstant.POP_BACK_HANDLER.sendMessage(HandlerConstant.POP_BACK_HANDLER.obtainMessage(0,"14"));
            }
        });

        HandlerConstant.POP_INNER_BACK_HANDLER= new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                String message = (String) msg.obj;
                Log.d(TagUtils.getTag(),"pop back handler:-"+message);
                btn_skip.callOnClick();
                return false;
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
//        startActivity(new Intent(getActivity(),AddMotorExamFragment.class));
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        AddProgressNoteFragment addProgressNoteFragment = AddProgressNoteFragment.newInstance(patientId,assessmentType);
        ft.replace(R.id.fragment_container, addProgressNoteFragment);
        ft.addToBackStack(null);
        ft.commit();
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

    private void getpnotes(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.GET_PROGRESS_NOTES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
//                            pDialog.dismiss();
                            JSONObject jsonObject1=new JSONObject(response);
                            String success=jsonObject1.getString("success");
                            if (success.equals("true")) {
                                JSONArray jsonArray = jsonObject1.getJSONArray("result");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String moter_exam_casedate = jsonObject.getString("moter_exam_progressdate");
                                    String moter_exam_casedesc = jsonObject.getString("moter_exam_progressdesc");
                                    String moter_exam_case_id = jsonObject.getString("moter_exam_progress_id");
                                    String patient_id = jsonObject.getString("patient_id");
                                    Detailapp1 = new InfoApps();
                                    Detailapp1.setSend_date(moter_exam_case_id);
                                    Detailapp1.setName(moter_exam_casedesc);
                                    Detailapp1.setNumber(moter_exam_casedate);
                                    Detailapp1.setId(patient_id);
                                    contactDetails1.add(Detailapp1);
                                    Collections.reverse(contactDetails1);

                                }
                                mAdapter = new UsersAdapterProgres_Notes(contactDetails1, getActivity(),mSnackBarLayout);
                                mRecyclerView.setAdapter(mAdapter);
                            }
                            else {

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        startActivity(new Intent(StmtActivity.this, PinDoMatch.class));
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
    public void onPause() {
        super.onStart();
        Log.e("start","onStart");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Progress note");
    }
    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Progress note");
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
