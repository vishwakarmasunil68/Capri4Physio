package com.capri4physio.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.Invoice.InfoApps1;
import com.capri4physio.R;
import com.capri4physio.adapter.UsersAdapter;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.fragment.assessment.InfoApps;
import com.capri4physio.fragment.assessment.LocationAdapter5;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.model.UserItem;
import com.capri4physio.model.UserListModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.AppPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 7/21/2016.
 */
public class ViewStaff extends AppCompatActivity implements HttpUrlListener, ViewItemClickListener<UserItem> {
    private RecyclerView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    public static ArrayList<InfoApps> contactDetails1;
    ListView listView;
    private UsersAdapter mAdapter;
    private List<UserItem> mList;
    private int itemPosition;
    InfoApps Detailapp;
    InfoApps1 detailApps;
    ArrayList<String> arrayList;
    String item;
    Spinner spinnerbranchloca;
    ArrayList<String> stringArrayList;
    Map<String, String> objresponse;
    private ProgressDialog pDialog;
    LocationAdapter5 locationAdapter5;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        startActivity(new Intent(AddAppointments.this, GetLUserList.class));
        setContentView(R.layout.viewstaff);
        stringArrayList = new ArrayList<String>();
        arrayList = new ArrayList<String>();
        arrayList.add("Select Branch Name");
        listView = (ListView) findViewById(R.id.list);
        mList = new ArrayList<>();
        contactDetails1 = new ArrayList<InfoApps>();
        mAdapter = new UsersAdapter(getApplicationContext(), mList, this);
        initView();
//        new CatagoryUrlAsynTask().execute();
        new CatagoryUrlAsynTask1().execute();


    }

    protected void initView() {
        mSnackBarLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        spinnerbranchloca = (Spinner) findViewById(R.id.spinnerbranchloca);
        /*mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")) {
            spinnerbranchloca.setVisibility(View.VISIBLE);
        } else {
            item = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
            viewStaffApiCall();
            Log.e("item", item);
        }
        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (i == 0) {
                    try {
                        String address = spinnerbranchloca.getSelectedItem().toString();
                        if (address.equalsIgnoreCase("Select Branch Name")) {
                            listView.setVisibility(View.INVISIBLE);

                        }
                    } catch (Exception e) {

                    }
                } else {
                    String address = spinnerbranchloca.getSelectedItem().toString();

                    String ad[] = address.split("\\(");
                    String newaddress = ad[1];
                    item = newaddress.replace(")", "");

                    try {
                        listView.setVisibility(View.INVISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    viewStaffApiCall();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        mRecyclerView.setLayoutManager(layoutManager);

    }

    private void viewStaffApiCall() {
        try {
            listView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_STAFF_FILTERATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            contactDetails1.clear();
                            Log.e("result", response);
                            JSONArray jsonArray = new JSONArray(response);
                            Log.e("Post Method", jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                String status = jsonObject2.getString("status");
                                Log.e("2", jsonObject2.toString());
                                if (status.equals("1")) {
                                    String id = jsonObject2.getString("id");
                                    String staff_code = jsonObject2.getString("patient_code");
                                    String smobile = jsonObject2.getString("mobile");
                                    String semail = jsonObject2.getString("email");
                                    String sfirst_name = jsonObject2.getString("first_name");
                                    String last_name = jsonObject2.getString("last_name");
                                    String datejoing = jsonObject2.getString("datejoing");
                                    String dob = jsonObject2.getString("dob");
                                    String age = jsonObject2.getString("age");
                                    String gen = jsonObject2.getString("gender");
                                    String desig = jsonObject2.getString("designation");
                                    String desig1 = jsonObject2.getString("designation1");
                                    String addr = jsonObject2.getString("address");
                                    String city = jsonObject2.getString("city");
                                    String pin_code = jsonObject2.getString("pincode");
                                    String qual = jsonObject2.getString("qualifation");
                                    String exp = jsonObject2.getString("exprience");

                                    String end_date_contract = jsonObject2.getString("end_date_contract");
                                    String degree = jsonObject2.getString("degree");
                                    String institution = jsonObject2.getString("institution");
                                    String university = jsonObject2.getString("university");
                                    String duration = jsonObject2.getString("duration");
                                    String password = jsonObject2.getString("show_password");
                                    String average = jsonObject2.getString("average");
                                    String organisation = jsonObject2.getString("organisation");
                                    String nature_of_work = jsonObject2.getString("nature_of_work");

                                    stringArrayList.add(sfirst_name + " " + last_name);
                                    Detailapp = new InfoApps();
                                    Detailapp.setName(sfirst_name + " " + last_name);
                                    Detailapp.setStr4(semail);//date
                                    Detailapp.setId(id);
                                    Detailapp.setNumber(smobile);//startTime
                                    Detailapp.setDob(dob);//reason
                                    Detailapp.setAge(age);
                                    Detailapp.setDoj(datejoing);
                                    Detailapp.setSend_date("");
                                    Detailapp.setGender(gen);
                                    Detailapp.setMarital_status("");
                                    Detailapp.setDesig(desig);
                                    Detailapp.setDesignation1(desig1);
                                    Detailapp.setDataAdd(addr);
                                    Detailapp.setCity(city);
                                    Detailapp.setPc(pin_code);
                                    Detailapp.setQualification(qual);
                                    Detailapp.setExp(exp);
                                    Detailapp.setStaffcode(staff_code);

                                    Detailapp.setWork(nature_of_work);
                                    Detailapp.setDegree(degree);
                                    Detailapp.setInstitution(institution);
                                    Detailapp.setUniversity(university);
                                    Detailapp.setAverage(average);
                                    Detailapp.setPassword(password);
                                    Detailapp.setDuration(duration);
                                    Detailapp.setEnd(end_date_contract);
                                    Detailapp.setOrganisation(organisation);
                                    contactDetails1.add(Detailapp);
//                                    Collections.reverse(contactDetails1);
                                    locationAdapter5 = new LocationAdapter5(ViewStaff.this, R.layout.viewlistofstaff);
                                    listView.setAdapter(locationAdapter5);

                                } else {
                                    contactDetails1.clear();
//                                    Collections.reverse(contactDetails1);
                                    locationAdapter5 = new LocationAdapter5(ViewStaff.this, R.layout.viewlistofstaff);
                                    listView.setAdapter(locationAdapter5);
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Postdat", "" + e.toString());
                        }
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
                objresponse = new HashMap<String, String>();
                objresponse.put("bracch_code", item);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onPostSuccess(Object response, int id) {
        switch (id) {
            case ApiConfig.ID1:
                UserListModel userListModel = (UserListModel) response;
                AppLog.i("Capri4Physio", "Staff listing response : " + userListModel.getStatus());
                if (userListModel.result.size() > 0) {
                    mList.clear();
                    mList.addAll(userListModel.result);
                    mAdapter.notifyDataSetChanged();
                }
                break;

            case ApiConfig.ID2:
                BaseModel deleteResponse = (BaseModel) response;
                AppLog.i("Capri4Physio", "Staff delete response : " + deleteResponse.getStatus());
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
        group.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBtnNormal));
        snack.setActionTextColor(Color.WHITE);
        snack.show();
    }

    @Override
    public void onViewItemClick(UserItem userItem, int position, int actionId) {

    }

    private class CatagoryUrlAsynTask1 extends AsyncTask<String, String, String> {
        String id, catagoryName;


        @Override
        protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
            String content = HttpULRConnect.getData(ApiConfig.GetURL);
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
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String branch_name = jsonObject2.getString("branch_name");
                    String bracch_code = jsonObject2.getString("branch_code");
                    //branch_code
//                    arrayList.add(bracch_code);

                    detailApps = new InfoApps1();
                    detailApps.setName(branch_name);
                    detailApps.setId(bracch_code);
                    arrayList.add(detailApps.getName() + "  " + "(" + detailApps.getId() + ")");
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.dropsimpledown, arrayList);
                    spinnerbranchloca.setAdapter(spinnerArrayAdapter);

                }

            } catch (Exception e) {
                Log.e("error", e.toString());

            }
        }
    }

    private class CatagoryUrlAsynTask extends AsyncTask<String, String, String> {
        String id, catagoryName;

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            initProgressDialog("Please wait...");
        }

        @Override
        protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
            String content = HttpULRConnect.getData(ApiConfig.VIEW_STAFF);
            Log.e("DoInBackGround ---->", String.valueOf(content));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();
            try {
                Log.e("DoInBackGroundtr", String.valueOf(s));
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray jsonArray = new JSONArray(s);
                Log.e("Post Method", jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String id = jsonObject2.getString("id");
                    String staff_code = jsonObject2.getString("patient_code");
                    String smobile = jsonObject2.getString("mobile");
                    String semail = jsonObject2.getString("email");
                    String sfirst_name = jsonObject2.getString("first_name");
                    String dob = jsonObject2.getString("dob");
                    String age = jsonObject2.getString("age");
                    String gen = jsonObject2.getString("gender");
                    String desig = jsonObject2.getString("designation");
                    String addr = jsonObject2.getString("address");
                    String city = jsonObject2.getString("city");
                    String pin_code = jsonObject2.getString("pincode");
                    String qual = jsonObject2.getString("qualifation");
                    String exp = jsonObject2.getString("exprience");
                    stringArrayList.add(sfirst_name);
                    Detailapp = new InfoApps();
                    Detailapp.setName(sfirst_name);
                    Detailapp.setStr4(semail);//date
                    Detailapp.setId(id);
                    Detailapp.setNumber(smobile);//startTime
                    Detailapp.setDob(dob);//reason
                    Detailapp.setAge(age);
                    Detailapp.setDoj("");
                    Detailapp.setSend_date("");
                    Detailapp.setGender(gen);
                    Detailapp.setMarital_status("");
                    Detailapp.setDesig(desig);
                    Detailapp.setDataAdd(addr);
                    Detailapp.setCity(city);
                    Detailapp.setPc(pin_code);
                    Detailapp.setQualification(qual);
                    Detailapp.setExp(exp);
                    Detailapp.setStaffcode(staff_code);
                    contactDetails1.add(Detailapp);
                    Collections.reverse(contactDetails1);
                    locationAdapter5 = new LocationAdapter5(ViewStaff.this, R.layout.viewlistofstaff);
                    listView.setAdapter(locationAdapter5);
                    Log.e("usertype", smobile.toString());
                    Log.e("stringArrayList", stringArrayList.toString());
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

            } catch (Exception e) {
                e.printStackTrace();

            }
            //  CatagoryMetod();
        }
    }

    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void refresh() {
        new CatagoryUrlAsynTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        refresh();
    }

    /*private class CatagoryUrlAsynTask extends AsyncTask<String, String, String> {
        String id, catagoryName;


        @Override
        protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                *//*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*//*
            String content = HttpULRConnect.getData(JsonURL);
            Log.e("DoInBackGround ---->", String.valueOf(content));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                Log.e("DoInBackGroundtr", String.valueOf(s));
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONObject jsonObject = new JSONObject(s);
                JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                Log.e("Post Method", jsonObject1.toString());
                for (int i = 1; i <= jsonObject1.length(); i++) {

                    JSONObject jsonObject2 = jsonObject1.getJSONObject(i + "");
                    Log.e("2", jsonObject2.toString());
                    userid = jsonObject2.getString("userid");
                    String username = jsonObject2.getString("username");
                    usertype=jsonObject2.getString("usertype");

                    if (usertype.equals("0")){
                        Detailapp = new InfoApps();
                        Detailapp.setName(username);
                        Detailapp.setNumber(userid);
                        patientaray.add(Detailapp);
                        Log.e("patientaray",patientaray.toString());
                    }
                    else if (usertype.equals("1")){
                        staffaray.add(username);
                        Log.e("staffaray",staffaray.toString());
                    }
                    else if (usertype.equals("2")){
                        dcotoraray.add(username);
                        Log.e("dcotoraray",dcotoraray.toString());
                    }
                    else if (usertype.equals("3")){
                        brancharay.add(username);
                        Log.e("brancharay",brancharay.toString());
                    }
//                        int userid = Integer.parseInt(jsonObject2.getInt((i+1) + ""));
//                        int userid=Integer.parseInt(jsonObject2.getString((i+1) + ""));
                    Log.e("usertype", usertype.toString());
                        *//*Detailapp1 = new InfoApps();
                        Detailapp1.setName(username);*//*
                        *//*Detailapp.setAppname(trnsamount);
                        Detailapp.setDataAdd(trnsamounttype);*//*
                    contactDetails.add(username);
                    contactAdapter = new LocationAdapter(getApplicationContext(), R.layout.contactlistadap);
                    contactList.setAdapter(contactAdapter);
//                        Toast.makeText(getApplicationContext(),usertype,Toast.LENGTH_LONG).show();
//                        Toast.makeText(getApplicationContext(),userid,Toast.LENGTH_LONG).show();
//                        int value = jsonObject1.getInt(i + 1);
                }

            }catch(Exception e){
                e.printStackTrace();

            }
            //  CatagoryMetod();
        }*/
}