package com.capri4physio.fragment.assessment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.fragment.BaseFragment;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by emobi on 17-03-2017.
 */

public class PaymentStatusFragmentView extends BaseFragment {
    InfoApps infoApps;
    ProgressDialog pDialog;
    RecyclerView recycler_view;
    public ArrayList<InfoApps> arrayList=new ArrayList<>();
    StudentsPaymentsAdapter mAdapter;
    public static PaymentStatusFragmentView newInstance() {
        PaymentStatusFragmentView fragment = new PaymentStatusFragmentView();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initProgressDialog("Please wait...");
        getpnotes();
//        arrayList=new ArrayList<>();
//        Toast.makeText(getActivity(), "mapfragment", Toast.LENGTH_SHORT).show();
//        new ViewAllClinic().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.payment,container,false);
        recycler_view = (RecyclerView) root.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(layoutManager);
        return root;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    private void getpnotes(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_PAYMENT_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
//                        try {
//                            Log.e("result", response);
//
                        try {

                                JSONObject jsonObject = new JSONObject(response);
                            String Success =jsonObject.getString("Success");
                            if (Success.equalsIgnoreCase("true")){
                                JSONArray jsonArray=jsonObject.getJSONArray("Result");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                    String date =jsonObject1.getString("date");
                                    String std_amount =jsonObject1.getString("std_amount");
                                    infoApps = new InfoApps();
                                    infoApps.setData(std_amount);
                                    infoApps.setSend_date(date);//date
                                    arrayList.add(infoApps);
                                    mAdapter = new StudentsPaymentsAdapter(arrayList,getActivity());
                                    recycler_view.setAdapter(mAdapter);
                                }

                            }
                        }
                        catch (JSONException e){
                            Log.e("catch",e.toString());
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
                }){


            protected Map<String,String> getParams(){
                Map<String,String> objresponse = new HashMap<String, String>();
                objresponse.put("user_id", AppPreferences.getInstance(getActivity()).getUserID());
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }
}
