package com.capri4physio.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.capri4physio.model.UserItem;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.TagUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import viewreport.SensoryReport;
import viewreport.SensoryReportFirst;

/**
 * Created by Emobi-Android-002 on 10/12/2016.
 */
public class ViewCaseReport extends AppCompatActivity {

    LinearLayout linearLayout, linearLayout1;
    String GetURL = ApiConfig.BASE_URL + "users/getuserlist";
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
    LinearLayout linear1;
    public static ArrayList<String> patientaray;
    public static ArrayList<String> patientidarray = new ArrayList<String>();
    TextView add, manage, staffmanagenew;
    private RecyclerView mRecyclerView;
    public static TextView name, contact_number, email, date, patientid, address1;
    public static String scity, sgender;
    public static ListView contactList;
    private CoordinatorLayout mSnackBarLayout;
    private UsersAdapter mAdapter;
    private List<UserItem> mList;
    private int itemPosition;
    public static ArrayList<String> staffarray, patientspinneraray;
    //    ReportAdapter contactAdapter;
    public static ArrayList<InfoApps> contactDetails;
    ArrayList<String> arrayList;
    String Branch_Code;
    Spinner spinnerbranchloca;
    InfoApps1 detailApps;
    UserItem Detailapp1;
    Map<String, String> objresponse;
    int pos;
    public static String patient_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.casereport);

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        contact_number = (TextView) findViewById(R.id.contact_number);
        patientid = (TextView) findViewById(R.id.id);
        date = (TextView) findViewById(R.id.date);
        address1 = (TextView) findViewById(R.id.address);
        patientspinner = (Spinner) findViewById(R.id.spinner2patient);
        spinner1report = (Spinner) findViewById(R.id.spinner1report);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        spinnerbranchloca = (Spinner) findViewById(R.id.spinnerbranchloca);

        contactDetails = new ArrayList<InfoApps>();
        patientaray = new ArrayList<String>();
        patientspinneraray = new ArrayList<String>();
        patientaray.add("Select patient name");
        staffarray = new ArrayList<String>();

        arrayList = new ArrayList<String>();
        arrayList.add("Select Branch");
        mList = new ArrayList<>();
        new CatagoryViewAsynTask1().execute();
        Branch_Code = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();

        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")) {
            spinnerbranchloca.setVisibility(View.VISIBLE);
        } else {
            Branch_Code = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
            getpatientlist();
            Log.e("item", Branch_Code);
        }
//        new CatagoryUrlAsynTask().execute();

        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    patientaray.clear();
                    patientaray.add("Select patient name");
                } else {
                    Branch_Code = spinnerbranchloca.getSelectedItem().toString();
                    Log.d("cd", Branch_Code);

                    String ad[] = Branch_Code.split("\\(");
                    Branch_Code = ad[1];
                    Branch_Code = Branch_Code.replace(")", "");


                    try {
                        initProgressDialog("Please wait...");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    edtxt_branchcode.setText(newaddress);
                    getpatientlist();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        patientspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = patientspinner.getSelectedItemPosition();
                if (position > 0) {
                    btn_submit.setVisibility(View.VISIBLE);
                    linear1.setVisibility(View.VISIBLE);
                } else {
                    btn_submit.setVisibility(View.INVISIBLE);
                    linear1.setVisibility(View.INVISIBLE);
                }
                try {
                    InfoApps obj = contactDetails.get(pos - 1);
                    name.setText(obj.getName());
                    Log.d("name", obj.getName());
                    email.setText(obj.getStr4());
                    contact_number.setText(obj.getNumber());
                    address1.setText(obj.getDataAdd());
                    scity = (obj.getCity());
                    sgender = obj.getGender();
                    patient_id = obj.getId();
                    patientid.setText(obj.getPatientid());
                    String stringdate = obj.getSend_date();
                    String string[] = stringdate.split(" ");
                    String getdate = string[0];
                    date.setText(getdate);
                    Log.d("sunil", obj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ViewCaseReport.this, android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.cusotm_dialogopt1);
                dialog.setTitle("Select -type");
                LinearLayout OPD = (LinearLayout) dialog.findViewById(R.id.OPD);
                dialog.setCancelable(true);
                LinearLayout Homevisit = (LinearLayout) dialog.findViewById(R.id.hvisit);


                Homevisit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(new Intent(ViewCaseReport.this, SensoryReport.class));
                    }
                });

                OPD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(new Intent(ViewCaseReport.this, SensoryReportFirst.class));
                    }
                });
                dialog.show();
//        Log.d("sunil",patient_id);

            }
        });

        /*setListener();
        viewStaffApiCall();*/
    }


    private class CatagoryUrlAsynTask extends AsyncTask<String, String, String> {
        String id, catagoryName;


        @Override
        protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
            String content = HttpULRConnect.getData(ApiConfig.VIEW_PATIENT);
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
                    String id = jsonObject2.getString("id");
                    String smobile = jsonObject2.getString("mobile");
                    String patient_code = jsonObject2.getString("patient_code");
                    String semail = jsonObject2.getString("email");
                    String gender = jsonObject2.getString("gender");
                    String sfirst_name = jsonObject2.getString("first_name");
                    String slast_name = jsonObject2.getString("last_name");
                    String created_date = jsonObject2.getString("created");
                    String addr = jsonObject2.getString("address");
                    String city = jsonObject2.getString("address2");
                    String pin_code = jsonObject2.getString("pincode");
                    /*String qual = jsonObject2.getString("qualifation");
                    String exp = jsonObject2.getString("exprience");*/


                    /*patientaray.add(sfirst_name);
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getActivity(), android.R.layout.simple_dropdown_item_1line, patientaray);
                    patientspinner.setAdapter(spinnerArrayAdapter);
                    Log.e("patientaray",patientaray.toString());*/

                    Detailapp = new InfoApps();
                    Detailapp.setName(sfirst_name + "  " + slast_name);
                    Detailapp.setStr4(semail);//date
                    Detailapp.setId(id);
                    Detailapp.setNumber(smobile);
                    Detailapp.setSend_date(created_date);//startTime
                    Detailapp.setPatientid(patient_code);
                    Detailapp.setDataAdd(addr + "," + city);
                    Detailapp.setGender(gender);
                    Detailapp.setCity(addr + "," + city);
                    /*Detailapp.setCity(city);
                    Detailapp.setPc(pin_code);
                    Detailapp.setQualification(qual);
                    Detailapp.setExp(exp);*/
                    contactDetails.add(Detailapp);
                    patientaray.add(Detailapp.getName());
//                Collections.reverse(contactDetails);


                }
//                List
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(), R.layout.simple_dropdown_item_1line, patientaray);
                patientspinner.setAdapter(spinnerArrayAdapter);
                Log.e("patientaray", patientaray.toString());
                /*contactAdapter = new ReportAdapter(getActivity(), R.layout.contactlistadap);
                contactList.setAdapter(contactAdapter);*/
            } catch (Exception e) {
                e.printStackTrace();

            }
            //  CatagoryMetod();
        }
    }

    private class CatagoryViewAsynTask1 extends AsyncTask<String, String, String> {
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

    private void getpatientlist() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_PATIENT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (patientaray.size() > 0) {
                                patientaray.clear();
                            }

                            patientaray.add("Select patient name");
                            Log.e("result", response);
                            try {
                                pDialog.hide();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                            new UrlConnectionTask(getActivity(), ApiConfig.VIEW_PATIENT_URL, ApiConfig.ID1, true, objresponse, UserListModel.class, this).execute("");                            else {

                            JSONObject jsonObject1 = new JSONObject(response);
                            /*String success=jsonObject1.getString("success");
                            if (success.equals("true")){
                                Log.e("success",response);*/
                            JSONArray jsonArray = jsonObject1.getJSONArray("result");
//                            String status  = jsonArray.getString("status");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                Log.e("2", jsonObject2.toString());

                                String status = jsonObject2.getString("status");
                                if (status.equals("1")) {
                                    String id = jsonObject2.getString("id");
                                    String smobile = jsonObject2.getString("mobile");
                                    String patient_code = jsonObject2.getString("patient_code");
                                    String semail = jsonObject2.getString("email");
                                    String sfirst_name = jsonObject2.getString("first_name");
                                    String slast_name = jsonObject2.getString("last_name");
                                    String created_date = jsonObject2.getString("created");
                                    String addr = jsonObject2.getString("address2");
                                    String city = jsonObject2.getString("city");
                                    String pin_code = jsonObject2.getString("pincode");
                    /*String qual = jsonObject2.getString("qualifation");
                    String exp = jsonObject2.getString("exprience");*/


                    /*patientaray.add(sfirst_name);
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getActivity(), android.R.layout.simple_dropdown_item_1line, patientaray);
                    patientspinner.setAdapter(spinnerArrayAdapter);
                    Log.e("patientaray",patientaray.toString());*/

                                    Detailapp = new InfoApps();
                                    Detailapp.setName(sfirst_name + " " + slast_name);
                                    Detailapp.setStr4(semail);//date
                                    Detailapp.setId(id);
                                    Detailapp.setNumber(smobile);
                                    Detailapp.setSend_date(created_date);//startTime
                                    Detailapp.setPatientid(patient_code);
                                    Detailapp.setDataAdd(addr + "," + city);
                    /*Detailapp.setCity(city);
                    Detailapp.setPc(pin_code);
                    Detailapp.setQualification(qual);
                    Detailapp.setExp(exp);*/
                                    contactDetails.add(Detailapp);
                                    patientaray.add(Detailapp.getName());
//                Collections.reverse(contactDetails);
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                            getApplicationContext(), R.layout.simple_dropdown_item_1line, patientaray);
                                    patientspinner.setAdapter(spinnerArrayAdapter);

                                } else {
                                    patientaray.clear();
                                    patientaray.add("Select patient name");
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                            getApplicationContext(), R.layout.simple_dropdown_item_1line, patientaray);
                                    patientspinner.setAdapter(spinnerArrayAdapter);
                                }
//                List


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
                }) {


            protected Map<String, String> getParams() {
                objresponse = new HashMap<String, String>();
                objresponse.put(ApiConfig.PATIENT_TYPE_TO_VIEW_PATIENT, "");
                objresponse.put("added_by", Branch_Code);
                Log.d(TagUtils.getTag(), "params:-" + objresponse.toString());
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(getApplicationContext());
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }
}
