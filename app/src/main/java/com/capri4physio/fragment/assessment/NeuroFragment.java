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
import com.capri4physio.model.assessment.PhysicalExamModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 7/12/2016.
 */
public class NeuroFragment extends BaseFragment implements HttpUrlListener, ViewItemClickListener<MotorItem> {

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
    private Button mAdd;


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
        viewAssessmentApiCall();
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


    private void getpnotes() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_NEURO4_REPORT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        try {
//                            Log.e("result", response);
//
                        Log.d(TagUtils.getTag(), "neuro response:-" + response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Detailapp1 = new InfoApps();
                                Detailapp1.setSend_date(jsonObject.getString("moter_examneuro_date"));
                                Detailapp1.setStr4(jsonObject.getString("moter_examneuro_id"));
                                Detailapp1.setPatientid(jsonObject.getString("patient_id"));
                                Detailapp1.setOne(jsonObject.getString("moter_examneuro_glas_eye"));
                                Detailapp1.setTwo(jsonObject.getString("moter_examneuro_glas_verbal"));
                                Detailapp1.setThree(jsonObject.getString("moter_examneuro_glas_motor"));
                                Detailapp1.setFour(jsonObject.getString("moter_examneuro_left_Ulnar"));
                                Detailapp1.setFive(jsonObject.getString("moter_examneuro_right_Ulnar"));
                                Detailapp1.setSix(jsonObject.getString("moter_examneuro_left_Radial"));
                                Detailapp1.setSeven(jsonObject.getString("moter_examneuro_right_Radial"));
                                Detailapp1.setEight(jsonObject.getString("moter_examneuro_left_Median"));
                                Detailapp1.setNine(jsonObject.getString("moter_examneuro_right_Median"));

                                Detailapp1.setTen(jsonObject.getString("moter_examneuro_left_musculocutaneous"));
                                Detailapp1.setEleven(jsonObject.getString("moter_examneuro_right_Musculocutaneous"));
                                Detailapp1.setTwelve(jsonObject.getString("moter_examneuro_left_sciatic"));
                                Detailapp1.setThirteen(jsonObject.getString("moter_examneuro_right_Sciatic"));
                                Detailapp1.setFourteen(jsonObject.getString("moter_examneuro_left_Tibial"));
                                Detailapp1.setFiftn(jsonObject.getString("moter_examneuro_right_Tibial"));
                                Detailapp1.setSixt(jsonObject.getString("moter_examneuro_left_Commanperonial"));
                                Detailapp1.setSevent(jsonObject.getString("moter_examneuro_right_Comman"));
                                Detailapp1.setEightt(jsonObject.getString("moter_examneuro_left_Femoral"));
                                Detailapp1.setNinet(jsonObject.getString("moter_examneuro_right_Femoral"));

                                Detailapp1.setTwenty(jsonObject.getString("moter_examneuro_left_Lateralcutaneous"));
                                Detailapp1.setTwentyone(jsonObject.getString("moter_examneuro_right_Lateralcutaneous"));
                                Detailapp1.setTwentytwo(jsonObject.getString("moter_examneuro_left_Obturator"));
                                Detailapp1.setTwentythree(jsonObject.getString("moter_examneuro_right_Obturator"));
                                Detailapp1.setTwentyfour(jsonObject.getString("moter_examneuro_left_Sural"));
                                Detailapp1.setTwentyfive(jsonObject.getString("moter_examneuro_right_Sural"));
                                Detailapp1.setTwentysix(jsonObject.getString("moterexamsneuro_saphenres_left"));
                                Detailapp1.setTwentyseven(jsonObject.getString("moterexamsneuro_saphenres_right"));
//                                    Detailapp1.setTwentysix(jsonObject.getString("moter_examneuro_ntp_left_Ulnar"));
                                Detailapp1.setTwentyeight(jsonObject.getString("moter_examneuro_special_test"));
//                                    Detailapp1.setTwentyseven(jsonObject.getString("moter_examneuro_ntp_left_Ulnar"));
                                Detailapp1.setTwentynine(jsonObject.getString("moter_examneuro_special_desc"));
                                Detailapp1.setThirty(jsonObject.getString("moter_examneuro_adl_name"));
                                Detailapp1.setThirtyone(jsonObject.getString("moter_examneuro_adl_desc"));
                                Detailapp1.setThirtytwo(jsonObject.getString("moter_examneuro_ntp_left_Ulnar"));
                                Detailapp1.setThirtythree(jsonObject.getString("moter_examneuro_ntp_right_Ulnar"));
                                Detailapp1.setThirtyfour(jsonObject.getString("moter_examneuro_ntp_left_Radial"));
                                Detailapp1.setThirtyfive(jsonObject.getString("moter_examneuro_ntp_right_Radial"));
                                Detailapp1.setThirtysix(jsonObject.getString("moter_examneuro_ntp_left_Median"));
                                Detailapp1.setThirtyseven(jsonObject.getString("moter_examneuro_ntp_right_Median"));
                                Detailapp1.setThirtyeight(jsonObject.getString("moter_examneuro_ntp_left_Sciatic"));
                                Detailapp1.setThirtynine(jsonObject.getString("moter_examneuro_ntp_right_Sciatic"));
                                Detailapp1.setFourty(jsonObject.getString("moter_examneuro_ntp_left_tibial"));
                                Detailapp1.setFourtyone(jsonObject.getString("moter_examneuro_ntp_right_Tibial"));
                                Detailapp1.setFourtytwo(jsonObject.getString("moter_examneuro_ntp_left_peronial"));
                                Detailapp1.setFourtythree(jsonObject.getString("moter_examneuro_ntp_right_peronial"));
                                Detailapp1.setFourtyfour(jsonObject.getString("moter_examneuro_ntp_left_Femoral"));
                                Detailapp1.setFourtyfive(jsonObject.getString("moter_examneuro_ntp_right_Femoral"));
//                                    Detailapp1.setFourtyfive(jsonObject.getString("moter_examneuro_ntp_right_Femoral"));
                                Detailapp1.setFourtysix(jsonObject.getString("moter_examneuro_ntp_left_sural"));
                                Detailapp1.setFourtyseven(jsonObject.getString("moter_examneuro_ntp_right_Sural"));
//                                    Detailapp1.setFourtyeight(jsonObject.getString("moter_examneuro_timetaken"));
                                Detailapp1.setFourtynine(jsonObject.getString("moter_examneuro_ntp_left_Saphenous"));
                                Detailapp1.setFifty(jsonObject.getString("moter_examneuro_ntp_right_Saphenous"));
                                Detailapp1.setFiftyone(jsonObject.getString("moter_examneuro_timetaken"));
                                Detailapp1.setFiftytwo(jsonObject.getString("moter_examneuro_step"));
                                Detailapp1.setFiftythree(jsonObject.getString("moter_examneuro_no_of_error"));
                                Detailapp1.setFiftyfour(jsonObject.getString("moter_examneuro_timetaken1"));
                                Detailapp1.setFiftyfive(jsonObject.getString("moter_examneuro_step1"));
                                Detailapp1.setFiftysix(jsonObject.getString("moter_examneuro_no_of_error1"));
                                Detailapp1.setFiftyseven(jsonObject.getString("moter_examneuro_timetaken2"));
                                Detailapp1.setFiftyeight(jsonObject.getString("moter_examneuro_step2"));
                                Detailapp1.setFiftynine(jsonObject.getString("moter_examneuro_no_of_error2"));
                                Detailapp1.setSixty(jsonObject.getString("moter_examneuro_gait_surface"));
                                Detailapp1.setSixtyone(jsonObject.getString("moter_examneuro_gait_speed"));
                                Detailapp1.setSixtytwo(jsonObject.getString("moter_examneuro_gait_hori_head"));
                                Detailapp1.setSixtythree(jsonObject.getString("moter_examneuro_gait_veri_head"));
                                Detailapp1.setSixtyfour(jsonObject.getString("moter_examneuro_gait_piovt"));
                                Detailapp1.setSixtyfive(jsonObject.getString("moter_examneuro_gait_overobstacle"));
                                Detailapp1.setInstitution(jsonObject.getString("moterexamsneuro_images"));

                                Detailapp1.setSixtysix(jsonObject.getString("moter_examneuro_gait_aroundobstacles"));
                                Detailapp1.setSixtyseven(jsonObject.getString("moter_examneuro_gait_steps"));
                                Detailapp1.setSixtyeight(jsonObject.getString("moter_examneuro_gait_balance_desc"));
                                Detailapp1.setSixtynine(jsonObject.getString("moter_examneuro_noerror"));
                                Detailapp1.setSeventy(jsonObject.getString("moter_examneuro_modifie_bowels"));
                                Detailapp1.setSeventyone(jsonObject.getString("moter_examneuro_modifie_bladder"));
                                Detailapp1.setSeventytwo(jsonObject.getString("moter_examneuro_modifie_grooming"));
                                Detailapp1.setSeventythree(jsonObject.getString("moter_examneuro_modifie_toilet"));
                                Detailapp1.setSeventyfour(jsonObject.getString("moter_examneuro_modifie_feeding"));
                                Detailapp1.setSeventyfive(jsonObject.getString("moter_examneuro_modifie_transfer"));
                                Detailapp1.setSeventysix(jsonObject.getString("moter_examneuro_modifie_mobility"));
                                Detailapp1.setSeventyseven(jsonObject.getString("moter_examneuro_modifie_dressing"));
                                Detailapp1.setSeventyeight(jsonObject.getString("moter_examneuro_modifie_stairs"));
                                Detailapp1.setSeventynine(jsonObject.getString("moter_examneuro_modifie_bathing"));

                                   /* Detailapp1 = new InfoApps();
                                    Detailapp1.setSend_date(sensory_exam_date);
                                    Detailapp1.setName(sensory_occipital_port);
                                   */
                                contactDetails1.add(Detailapp1);
                                Collections.reverse(contactDetails1);
                                Log.e("contactDetails1", contactDetails1.toString());

                            }
                            mAdapter = new UsersAdapterNeuro_(contactDetails1, getActivity(), mSnackBarLayout);
                            mRecyclerView.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            Log.e("catch", e.toString());
                        }
//                        }
//                        catch (Exception e) {
//                            Log.e("catchExce",e.toString());
//                        }
                        Log.e("Postdat", "" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                    }
                }) {


            protected Map<String, String> getParams() {
                Map<String, String> objresponse = new HashMap<String, String>();
                objresponse.put("patient_id", patientId);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void addFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        AddNeuroFragment addNeuroFragment = AddNeuroFragment.newInstance(patientId, assessmentType);

//       ft.remove(addNeuroFragment);

//        ft.remove(null);
        ft.replace(R.id.fragment_container, addNeuroFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onViewItemClick(MotorItem motorItem, int position, int actionId) {

    }

    @Override
    public void onPause() {
        super.onStart();
        Log.e("start", "onStart");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Neuro Exam");
    }

    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Neuro Exam");
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
}

