package com.capri4physio.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.Invoice.InfoApps1;
import com.capri4physio.R;
import com.capri4physio.fragment.assessment.Viewpatientinlist;
import com.capri4physio.patientappo.PatientAppointmentsAdapter;
import com.capri4physio.util.AppPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 7/13/2016.
 */
public class ViewAppoinmentsPatientFragment extends BaseFragment{
    String JsonURL = "http://oldmaker.com/fijiyo/index.php/users/getappointmentlist";
    public static final String KEY_PATIENTID = "patient_id";
    public static Viewpatientinlist viewpatientinlist;
    public static ArrayList<InfoApps1> contactDetails1;
    public static TextView patient_name,date,_starttime,booking_reason;
    public static String patient,patientname,booking_date,booking_starttime,getPatientname,reason,getuniqueid,patientid,doctorid;
    JSONArray jsonArray;
    Button btn;
    InfoApps1 Detailapp;
    private RecyclerView mRecyclerView;
    ProgressDialog progressDialog;
    PatientAppointmentsAdapter mAdapter;

    public static ViewAppoinmentsPatientFragment newInstance() {
        ViewAppoinmentsPatientFragment fragment = new ViewAppoinmentsPatientFragment();
        return fragment;
    }
    public ViewAppoinmentsPatientFragment() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.viewappo, container, false);

        contactDetails1=new ArrayList<InfoApps1>();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        patient= AppPreferences.getInstance(getActivity()).getUserID();
        Log.i("patient",patient);
        initProgressDialog("Please wait...");
        saveappointment();

        return rootView;
    }
    private void saveappointment(){
        /*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JsonURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            progressDialog.hide();
                            /*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*/

                            JSONObject objresponse = new JSONObject(response);
                            //					Toast.makeText(getApplicationContext(), "Could not retreive Data2!", Toast.LENGTH_LONG).show();

                            /*String success = objresponse.getString("isSuccess");
                            String success_msg = objresponse.getString("success_msg");*/

//                            if (success.equalsIgnoreCase("true") || success_msg.equalsIgnoreCase("true")) {

                            Log.e("Postdat", "" + response);
                            jsonArray = objresponse.getJSONArray("result");


                            //Log.i("News Data", jsonArray.toString());

//                    JSONArray cast = jsonArray.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                booking_date=jsonObject.getString("booking_date");
                                getuniqueid=jsonObject.getString("id");
                                booking_starttime = jsonObject.getString("booking_starttime");
                                patientid = jsonObject.getString("patient_id");
                                doctorid = jsonObject.getString("doctor_id");
                                reason= jsonObject.getString("reason");
                                Detailapp = new InfoApps1();
                                Detailapp.setName(patientname);
                                Detailapp.setInvo_date(booking_date);//date
                                Detailapp.setNumber(booking_starttime);//startTime
                                Detailapp.setAppname(reason);//reason
                                Detailapp.setDataAdd(patientname);
                                Detailapp.setId(patientid);
                                Detailapp.setStatus(getuniqueid);
                                contactDetails1.add(Detailapp);
                                mAdapter = new PatientAppointmentsAdapter(contactDetails1,getActivity());
                                mRecyclerView.setAdapter(mAdapter);

                            }
                                    /*else {
                                        Toast.makeText(getApplicationContext(),"Pin number is incorrect",Toast.LENGTH_LONG).show();
                                    }*/
//                            }
                            /*else {
                                Toast.makeText(getApplicationContext(),"Phone_no. or password is incorrect",Toast.LENGTH_LONG).show();
                            }*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        startActivity(new Intent(StmtActivity.this, PinDoMatch.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_PATIENTID,patient);

//                Toast.makeText(UserStatement.this, username +"success", Toast.LENGTH_LONG).show();
//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void initProgressDialog(String loading) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(loading);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
