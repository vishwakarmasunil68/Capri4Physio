package com.capri4physio.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import viewreport.ViewsStaffSeessionReport;

/**
 * Created by Emobi-Android-002 on 10/12/2016.
 */
public class ViewIncomeSessionwiseReport extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

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
    public static Spinner spinner1report, patientspinner;
    Button btn_submit;
    LinearLayout linear1;
    public static ArrayList<String> patientaray;
    public static ArrayList<String> patientidarray = new ArrayList<String>();
    public static ArrayList<InfoApps> contactDetails;
    TextView add, manage, staffmanagenew;
    private RecyclerView mRecyclerView;
    public static EditText ed1, ed2;
    public static String staff, seleted_treatment;
    Calendar myCalendar, myCalendar1;
    InfoApps1 detailApps;
    ArrayList<String> arrayList;
    String item;
    Spinner spinnerbranchloca, edtxt_therapy_proquant;
    ArrayList<String> stringArrayList;
    Map<String, String> objresponse;

    @BindView(R.id.iv_from)
    ImageView iv_from;
    @BindView(R.id.iv_to)
    ImageView iv_to;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incomersessepo);
        ButterKnife.bind(this);

        spinnerbranchloca = (Spinner) findViewById(R.id.spinnerbranchloca);
        edtxt_therapy_proquant = (Spinner) findViewById(R.id.edtxt_therapy_prodoctitem);
        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (EditText) findViewById(R.id.ed2);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        patientspinner = (Spinner) findViewById(R.id.spinner2patient);
        arrayList = new ArrayList<String>();
        arrayList.add("Select Branch Name");

        patientaray = new ArrayList<String>();
        contactDetails = new ArrayList<InfoApps>();
        patientaray.add("Select Staff name");

        new CatagoryUrlAsynTask1().execute();

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        myCalendar1 = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed1.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please select all entries", Toast.LENGTH_LONG).show();

                }
                if (ed2.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please select all entries", Toast.LENGTH_LONG).show();
                }

                if (patientspinner.getSelectedItemPosition() < 1) {
                    Toast.makeText(getApplicationContext(), "Please select all entries", Toast.LENGTH_LONG).show();
                } else {
                    seleted_treatment = edtxt_therapy_proquant.getSelectedItem().toString();
                    int numb = patientspinner.getSelectedItemPosition();
                    staff = contactDetails.get(numb - 1).getId().toString();
                    Log.e("staff", staff + " " + numb + " " + seleted_treatment);
                    Intent intent = new Intent(ViewIncomeSessionwiseReport.this, ViewsStaffSeessionReport.class);
                    intent.putExtra("staffid", contactDetails.get(numb - 1).getId().toString());
                    startActivity(intent);


                }

            }
        });


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
                        patientaray.clear();
                        patientaray.add("Select Staff Name");
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(), R.layout.simple_dropdown_item_1line, patientaray);
                        patientspinner.setAdapter(spinnerArrayAdapter);

                    } catch (Exception e) {

                    }
                } else {
                    String address = spinnerbranchloca.getSelectedItem().toString();

                    String ad[] = address.split("\\(");
                    String newaddress = ad[1];
                    item = newaddress.replace(")", "");

                    try {
//                        listView.setVisibility(View.INVISIBLE);
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



        /*setListener();
        viewStaffApiCall();*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ed1.setText(sdf.format(new Date()));
        ed2.setText(sdf.format(new Date()));

        iv_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ViewIncomeSessionwiseReport.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        iv_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ViewIncomeSessionwiseReport.this, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ed1.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel1() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ed2.setText(sdf.format(myCalendar1.getTime()));
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }

    private class CatagoryUrlAsynTask extends AsyncTask<String, String, String> {
        String id, catagoryName;

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            try {
                initProgressDialog("Please wait...");
            } catch (Exception e) {
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
            } catch (Exception e) {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    private void viewStaffApiCall() {
        try {
//            listView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_STAFF_FILTERATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            contactDetails1.clear();
                            Log.e("result", response);
                            JSONArray jsonArray = new JSONArray(response);
                            Log.e("Post Method", jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                String status = jsonObject2.getString("status");
                                Log.e("2", jsonObject2.toString());
                                if (status.equals("1")) {
                                    String id = jsonObject2.getString("id");
                                    String semail = jsonObject2.getString("email");
                                    String sfirst_name = jsonObject2.getString("first_name");
                                    Detailapp = new InfoApps();
                                    Detailapp.setName(sfirst_name);
                                    Detailapp.setStr4(semail);//date
                                    Detailapp.setId(id);
                                    patientaray.add(Detailapp.getName());
                                    contactDetails.add(Detailapp);
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                            getApplicationContext(), R.layout.simple_dropdown_item_1line, patientaray);
                                    patientspinner.setAdapter(spinnerArrayAdapter);

                                } else {
                                    patientaray.clear();
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                            getApplicationContext(), R.layout.simple_dropdown_item_1line, patientaray);
                                    patientspinner.setAdapter(spinnerArrayAdapter);
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


    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(getApplicationContext());
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }


}
