package com.capri4physio.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.Invoice.InfoApps1;
import com.capri4physio.R;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.fragment.assessment.InfoApps;
import com.capri4physio.model.UserItem;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 10/12/2016.
 */
public class ChangePatientClinic extends AppCompatActivity {

    LinearLayout linearLayout, linearLayout1;
    String GetURL = "http://www.caprispine.in/users/getuserlist";
    public static String userid;
    Button btn;
    public static final int TIME_DIALOG_ID = 1111;
    private ProgressDialog pDialog;
    public static TextView p_nametext, bill_amounttext, paid_amounttext, due_amounttext, payment_modetext, statustext, bill_numbertext, bill_datetext, pctxet;
    public static EditText bill_amount, paid_amount, due_amount, payment_mode, status, bill_number, bill_date, pc, gender, address, city, Designation, send_date;
    InfoApps Detailapp;
    Spinner p_name, staff_name;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    Spinner spinner1report, patientspinner;
    Button btn_submit;
    UserItem Detailapp1;
    LinearLayout linear1;
    public static ArrayList<String> patientaray,staffarray;
    public static ArrayList<String> patientidarray = new ArrayList<String>();
    public static ArrayList<InfoApps> contactDetails;
    public static ArrayList<UserItem> contactDetails1;
    ProgressDialog progressDialog;
    TextView add, manage, staffmanagenew;
    ArrayList<String> arrayList;
    InfoApps1 detailApps;
    public  String item;
    public  String item4;
    Spinner spinnerbranchloca;
    Spinner spinnerbranchloca1;
    ArrayList<String> stringArrayList;

    TextView repo_typ;
    Map<String,String> objresponse;
    Calendar myCalendar, myCalendar1;
    public String patient_id,staff_id,value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chnageclinic);
        spinnerbranchloca = (Spinner) findViewById(R.id.spinnerbranchloca);
        spinnerbranchloca1 = (Spinner) findViewById(R.id.spinnerbranchloca1);
        btn_submit= (Button) findViewById(R.id.btn_submit);
        p_name = (Spinner) findViewById(R.id.spinner2patient);
        arrayList = new ArrayList<String>();
        arrayList.add("Select Branch Name");
        patientaray = new ArrayList<String>();
        contactDetails = new ArrayList<InfoApps>();
        contactDetails1 = new ArrayList<UserItem>();
        patientaray.add("Select Patient name");
//        new CatagoryUrlAsynTask().execute();
        new CatagoryUrlAsynTask1().execute();

        value = getIntent().getStringExtra("indexing");
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
try {
    if (staff_id.equals("")) {
        Toast.makeText(ChangePatientClinic.this, "Please select patient name", Toast.LENGTH_LONG).show();

    } else {
        if (item4.equals("")){
            Toast.makeText(ChangePatientClinic.this, "Please select Clinic branch to change", Toast.LENGTH_LONG).show();
        }
        else {
            initProgressDialog("Please wait...");
            getpatientlist4();
        }
    }
}
catch (Exception e){
    e.toString();
}
         Log.d("condition","if");

        }

        });

        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")){
            spinnerbranchloca.setVisibility(View.VISIBLE);
        }
else {
            item = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
            initProgressDialog("Please wait...");
            getpatientlist();
            Log.e("item", item);
        }

        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (i==0){
                    try {
                        patientaray.clear();
                        patientaray.add("Select Patient name");
                        contactDetails1.clear();
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(), R.layout.simple_dropdown_item_1line, patientaray);
                        p_name.setAdapter(spinnerArrayAdapter);
                        spinnerbranchloca1.setVisibility(View.INVISIBLE);

                        }
                    catch (Exception e){

                    }
                }
                else {
                    String address = spinnerbranchloca.getSelectedItem().toString();

                    String ad[]=address.split("\\(");
                    String newaddress = ad[1];
                    item =newaddress.replace(")", "");

                    try{
//                        listView.setVisibility(View.INVISIBLE);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    initProgressDialog("Please wait...");
                    getpatientlist();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerbranchloca1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                    String address = spinnerbranchloca1.getSelectedItem().toString();

if (i==0){
    item4 ="";
}

                    try{
                        String ad[]=address.split("\\(");
                        String newaddress = ad[1];
                        item4 =newaddress.replace(")", "");
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        /*setListener();
        viewStaffApiCall();*/




        p_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    staff_id ="";
                    spinnerbranchloca1.setVisibility(View.INVISIBLE);
                    btn_submit.setVisibility(View.INVISIBLE);
                }
                else {
                    int pos = p_name.getSelectedItemPosition();
                    pos =pos-1;
                    try {
                        staff_id = contactDetails1.get(pos).getId().toString();
                        spinnerbranchloca1.setVisibility(View.VISIBLE);
                        btn_submit.setVisibility(View.VISIBLE);
                        Log.d("staff_id", staff_id.toString());
                    } catch (Exception e) {
                        Log.d("patient_idcatch", e.toString());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private void getpatientlist(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_PATIENT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (contactDetails1.size() > 0) {
                                contactDetails1.clear();
                            }
                            if (patientaray.size() > 0) {
                                patientaray.clear();
                            }
                            Log.e("result", response);
                            pDialog.dismiss();
                            patientaray.add("Select patient name");
//                            new UrlConnectionTask(getActivity(), ApiConfig.VIEW_PATIENT_URL, ApiConfig.ID1, true, objresponse, UserListModel.class, this).execute("");                            else {

                            JSONObject jsonObject1 = new JSONObject(response);
                            /*String success=jsonObject1.getString("success");
                            if (success.equals("true")){
                                Log.e("success",response);*/
                            JSONArray jsonArray = jsonObject1.getJSONArray("result");
                            for (int i = jsonArray.length() - 1; i >= 0; i--) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String status = jsonObject.getString("status");
                                if (status.equals("1")) {
                                    Log.e("json", jsonObject.toString());
                                    String id = jsonObject.getString("id");
                                    String first_name = jsonObject.getString("first_name");
                                    String last_name = jsonObject.getString("last_name");
                                    String email = jsonObject.getString("email");
                                    String profile_pic = jsonObject.getString("profile_pic");


                                    Detailapp1 = new UserItem();
                                    Detailapp1.setName(first_name + " " + last_name);
                                    Detailapp1.setEmail(email);
                                    Detailapp1.setId(id);
                                    Detailapp1.setProfilePic(profile_pic);
                                    contactDetails1.add(Detailapp1);
                                    patientaray.add(Detailapp1.getName());
//                                    contactDetails.add(Detailapp);
//                                    patientdarray.add(id);
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                            getApplicationContext(), R.layout.dropsimpledown, patientaray);
                                    p_name.setAdapter(spinnerArrayAdapter);
                                    spinnerbranchloca1.setVisibility(View.VISIBLE);
                                    btn_submit.setVisibility(View.VISIBLE);
//                                    btn_submit.reverse(contactDetails1);

//                                    Log.e("contactDetails1",mList.toString());

                                }

                                else {
                                    contactDetails1.clear();
                                    patientaray.clear();
                                    patientaray.add("Select patient name");

                                }
//                                mAdapter1 = new Patient_listAdapter(contactDetails1, getActivity());

                            }
                        }
                        catch (Exception e) {
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
                objresponse = new HashMap<String, String>();
                objresponse.put(ApiConfig.PATIENT_TYPE_TO_VIEW_PATIENT, value);
                objresponse.put("added_by", item);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
private void getpatientlist4(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://caprispine.in/users/updatebranchcodepatient",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (contactDetails1.size() > 0) {
                                contactDetails1.clear();
                            }
                            Log.e("result", response);
                            try{
                            pDialog.dismiss();}
                            catch (Exception e){
                                e.printStackTrace();
                            }
Toast.makeText(ChangePatientClinic.this,"Clinic updated successfully",Toast.LENGTH_LONG).show();
                            finish();


                        }
                        catch (Exception e) {
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
                objresponse = new HashMap<String, String>();
                objresponse.put("patient_id", staff_id);
                objresponse.put("added_by", item4);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }








    private void initProgressDialog(String loading) {
        try {
            pDialog = new ProgressDialog(ChangePatientClinic.this);
            pDialog.setMessage(loading);
            pDialog.setCancelable(true);
            try {
                pDialog.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private class CatagoryUrlAsynTask extends AsyncTask<String, String, String> {
        String id, catagoryName;

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            try{
                initProgressDialog("Please wait...");
            }
            catch (Exception e){
                e.printStackTrace();
            }
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
            try {
                pDialog.dismiss();
            }
            catch (Exception e){
                e.printStackTrace();
            }
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
                    String smobile = jsonObject2.getString("smobile");
                    String semail = jsonObject2.getString("semail");
                    String sfirst_name = jsonObject2.getString("sfirst_name");
                    String dob = jsonObject2.getString("sdob");
                    String age = jsonObject2.getString("sage");
                    String doj = jsonObject2.getString("sdatejoing");
                    String senddate = jsonObject2.getString("senddate");
                    String gen = jsonObject2.getString("sgender");
                    String marital_status = jsonObject2.getString("smarital_status");
                    String desig = jsonObject2.getString("sdesignation");
                    String addr = jsonObject2.getString("saddress");
                    String city = jsonObject2.getString("scity");
                    String pin_code = jsonObject2.getString("spincode");
                    String qual = jsonObject2.getString("squalifation");
                    String exp = jsonObject2.getString("sexprience");
                    Detailapp = new InfoApps();
                    Detailapp.setName(sfirst_name);
                    Detailapp.setStr4(semail);//date
                    Detailapp.setId(id);
                    Detailapp.setNumber(smobile);//startTime
                    Detailapp.setDob(dob);//reason
                    Detailapp.setAge(age);
                    Detailapp.setDoj(doj);
                    Detailapp.setSend_date(senddate);
                    Detailapp.setGender(gen);
                    Detailapp.setMarital_status(marital_status);
                    Detailapp.setDesig(desig);
                    Detailapp.setDataAdd(addr);
                    Detailapp.setCity(city);
                    Detailapp.setPc(pin_code);
                    Detailapp.setQualification(qual);
                    Detailapp.setExp(exp);
                    contactDetails.add(Detailapp);
                    patientaray.add(Detailapp.getName());


                }
//                List
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(), R.layout.simple_dropdown_item_1line, patientaray);
                patientspinner.setAdapter(spinnerArrayAdapter);
                Log.e("patientaray", patientaray.toString());
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /*private void viewStaffApiCall() {

        try {
            patientaray.clear();
            staffarray.clear();



        }
        catch (Exception e){
            Log.e("kabe",e.toString());
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_PATIENT_STAFF_FILTERATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            try {
                                patientaray.clear();
                                staffarray.clear();
                                staffarray.add("Select Staff name");
                                patientaray.add("Select Patient name");
                            }
                            catch (Exception e){
                                e.toString();
                            }
                            Log.e("result", response);
                            JSONArray jsonArray = new JSONArray(response);
                            Log.e("Post Method", jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                String status = jsonObject2.getString("status");
                                if (status.equals("1")){
                                    Log.e("2", jsonObject2.toString());
                                    String type = jsonObject2.getString("user_type");

                                    if (type.equals("1")) {

                                        String id = jsonObject2.getString("id");
                                        String sfirst_name = jsonObject2.getString("first_name");
                                        String last_name = jsonObject2.getString("last_name");
                                        Detailapp = new InfoApps();
                                        Detailapp.setName(sfirst_name+" "+ last_name);
                                        Detailapp.setId(id);
                                        contactDetails1.add(Detailapp);
                                        staffarray.add(Detailapp.getName());
                                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                                getApplicationContext(), R.layout.dropsimpledown, staffarray);
                                        staff_name.setAdapter(spinnerArrayAdapter);
                                    }
                                    if (type.equals("0")){
                                        String id = jsonObject2.getString("id");
                                        String sfirst_name = jsonObject2.getString("first_name");
                                        String last_name = jsonObject2.getString("last_name");
                                        Detailapp = new InfoApps();
                                        Detailapp.setName(sfirst_name+" "+ last_name);
                                        Detailapp.setId(id);
                                        contactDetails.add(Detailapp);
                                        patientaray.add(Detailapp.getName());
//                                    contactDetails.add(Detailapp);
//                                    patientdarray.add(id);
                                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                                getApplicationContext(), R.layout.dropsimpledown, patientaray);
                                        p_name.setAdapter(spinnerArrayAdapter);
                                    }
                                }
                                else{
                                    staffarray.clear();

                                    patientaray.clear();
                                    staffarray.add("Select Staff name");
                                    patientaray.add("Select Patient name");
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                            getApplicationContext(), R.layout.dropsimpledown, staffarray);
                                    staff_name.setAdapter(spinnerArrayAdapter);

                                    ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                                            getApplicationContext(), R.layout.dropsimpledown, patientaray);
                                    p_name.setAdapter(spinnerArrayAdapter1);
                                }
                            }

                        }

                        catch(JSONException e){
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
                }){


            protected Map<String,String> getParams(){
                objresponse = new HashMap<String, String>();
                objresponse.put("bracch_code", item);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }*/
    private class CatagoryUrlAsynTask1 extends AsyncTask<String, String, String> {
        String id, catagoryName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            initProgressDialog("Please wait...");
        }

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
pDialog.dismiss();
            try {
                Log.e("DoInBackGroundtr", String.valueOf(s));
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray jsonArray = new JSONArray(s);
                for (int i =0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String branch_name = jsonObject2.getString("branch_name");
                    String bracch_code = jsonObject2.getString("branch_code");
                    //branch_code
//                    arrayList.add(bracch_code);

                    detailApps = new InfoApps1();
                    detailApps.setName(branch_name);
                    detailApps.setId(bracch_code);
                    arrayList.add(detailApps.getName() + "  "+ "(" + detailApps.getId()+ ")");
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.dropsimpledown, arrayList);
                    spinnerbranchloca.setAdapter(spinnerArrayAdapter);
                    ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.dropsimpledown, arrayList);
                    spinnerbranchloca1.setAdapter(spinnerArrayAdapter1);

                }

            }catch(Exception e){
                Log.e("error",e.toString());

            }
        }
    }
}
