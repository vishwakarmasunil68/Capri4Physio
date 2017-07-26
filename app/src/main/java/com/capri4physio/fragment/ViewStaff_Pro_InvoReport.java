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
import com.capri4physio.pojo.balancereport.Branch.BranchPOJO;
import com.capri4physio.pojo.balancereport.Branch.BranchPatientPOJO;
import com.capri4physio.util.AppPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import viewreport.ViewsStaffProFromInvoiceReport;

/**
 * Created by Emobi-Android-002 on 10/12/2016.
 */
public class ViewStaff_Pro_InvoReport extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

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
    public static ArrayList<String> patientaray, staffarray;
    public static ArrayList<String> patientidarray = new ArrayList<String>();
    public static ArrayList<InfoApps> contactDetails, contactDetails1;
    TextView add, manage, staffmanagenew;
    private RecyclerView mRecyclerView;
    String fromdate, to_date, staffname;
    public static EditText ed1, ed2;
    ArrayList<String> arrayList;
    InfoApps1 detailApps;
    public static String item;
    Spinner spinnerbranchloca;
    ArrayList<String> stringArrayList;

    TextView repo_typ;
    Map<String, String> objresponse;
    Calendar myCalendar, myCalendar1;
    public static String patient_id, staff_id;
    @BindView(R.id.from)
    ImageView iv_from;
    @BindView(R.id.to)
    ImageView iv_to;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incomerepobalncsheet);
        ButterKnife.bind(this);
        spinnerbranchloca = (Spinner) findViewById(R.id.spinnerbranchloca);
        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (EditText) findViewById(R.id.ed2);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        repo_typ = (TextView) findViewById(R.id.repo_typ);
        staff_name = (Spinner) findViewById(R.id.spinner2patient);
        p_name = (Spinner) findViewById(R.id.invo_patient_name);
        arrayList = new ArrayList<String>();
        arrayList.add("Select Branch Name");
        patientaray = new ArrayList<String>();
        staffarray = new ArrayList<String>();
        contactDetails = new ArrayList<InfoApps>();
        contactDetails1 = new ArrayList<InfoApps>();
        staffarray.add("Select Staff name");
        patientaray.add("Select Patient name");
        repo_typ.setText("Productivity Report");
//        new CatagoryUrlAsynTask().execute();
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
                try {
                    item = branchPOJOList.get(spinnerbranchloca.getSelectedItemPosition()).getBranch_code();
                    patient_id = staff_id = staffbranchPatientPOJOList.get(staff_name.getSelectedItemPosition()).getId();
                    startActivity(new Intent(ViewStaff_Pro_InvoReport.this, ViewsStaffProFromInvoiceReport.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")) {
            spinnerbranchloca.setVisibility(View.VISIBLE);
        } else {
            item = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
            viewStaffApiCall(item);
            Log.e("item", item);
        }

        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


//                if (i == 0) {
//                    try {
//                        patientaray.clear();
//                        staffarray.clear();
//                        staffarray.add("Select Staff name");
//                        patientaray.add("Select Patient name");
//                        contactDetails.clear();
//                        contactDetails1.clear();
//                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
//                                getApplicationContext(), R.layout.simple_dropdown_item_1line, patientaray);
//                        patientspinner.setAdapter(spinnerArrayAdapter);
//                    } catch (Exception e) {
//
//                    }
//                } else {
                String branch_code = branchPOJOList.get(spinnerbranchloca.getSelectedItemPosition()).getBranch_code();
                viewStaffApiCall(branch_code);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        /*setListener();
        viewStaffApiCall();*/

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ed1.setText(sdf.format(new Date()));
        ed2.setText(sdf.format(new Date()));
        iv_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ViewStaff_Pro_InvoReport.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        iv_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ViewStaff_Pro_InvoReport.this, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        p_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    staff_id = "";
                } else {
                    int pos = p_name.getSelectedItemPosition();
                    pos = pos - 1;
                    try {
                        staff_id = contactDetails.get(pos).getId().toString();
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

        staff_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    patient_id = "";
                } else {
                    int pos = staff_name.getSelectedItemPosition();
                    pos = pos - 1;
                    try {
                        patient_id = contactDetails1.get(pos).getId().toString();
                        Log.d("patient_id", patient_id.toString());
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

    private void report8() {
        /*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_STAFF_INVOICESHEET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            /*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*/

//                            JSONObject objresponse = new JSONObject(response);


                            Log.e("response", "" + response);
                            JSONObject jsonArray = new JSONObject(response);


                            for (int i = 0; i < jsonArray.length() - 2; i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i + "");
                                Log.e("2", jsonObject2.toString());
                                String invo_code = jsonObject2.getString("invo_code");
                                String invo_bill_amount = jsonObject2.getString("invo_bill_amount");

                                Detailapp = new InfoApps();

                                Detailapp.setId(invo_code);
                                Detailapp.setNumber(invo_bill_amount);

//                                contactDetails1.add(Detailapp);

                            }
                            /*mAdapter = new UsersAdapterStaffProfromInvoice(contactDetails1, ViewsStaffProFromInvoiceReport.this);
                            mRecyclerView.setAdapter(mAdapter);
                            Log.e("stringArrayList", stringArrayList.toString());*/

                        } catch (Exception e) {
                            Log.e("error", e.toString());

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
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("invo_start_date", fromdate);
                params.put("invo_end_date", to_date);
                params.put("staff_id", staffname);
//                Toast.makeText(UserStatement.this, username +"success", Toast.LENGTH_LONG).show();
//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void initProgressDialog(String loading) {
        try {
            pDialog = new ProgressDialog(getApplicationContext());
            pDialog.setMessage(loading);
            pDialog.setCancelable(false);
            pDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void clearstaffspinner() {
        List<String> staff_array = new ArrayList<>();
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(), R.layout.dropsimpledown, staff_array);
        staff_name.setAdapter(spinnerArrayAdapter);
    }

    public void clearPatientSpinner() {
        List<String> patient_array = new ArrayList<>();
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(), R.layout.dropsimpledown, patient_array);
        p_name.setAdapter(spinnerArrayAdapter);
    }

    List<BranchPatientPOJO> staffbranchPatientPOJOList = new ArrayList<>();
    List<BranchPatientPOJO> patientbranchPatientPOJOList = new ArrayList<>();

    private void viewStaffApiCall(final String branch_code) {

        try {
            staffbranchPatientPOJOList.clear();
            patientbranchPatientPOJOList.clear();
        } catch (Exception e) {
            Log.e("kabe", e.toString());
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
                            } catch (Exception e) {
                                e.toString();
                            }
                            Gson gson = new Gson();
                            List<BranchPatientPOJO> branchPatientPOJOs = gson.fromJson(response, new TypeToken<List<BranchPatientPOJO>>() {
                            }.getType());
                            for (BranchPatientPOJO branchPatientPOJO : branchPatientPOJOs) {
                                if (branchPatientPOJO.getStatus().equals("1")) {
                                    if (branchPatientPOJO.getUserType().equals("1")) {
                                        staffbranchPatientPOJOList.add(branchPatientPOJO);
                                    } else {
                                        if (branchPatientPOJO.getUserType().equals("0")) {
                                            patientbranchPatientPOJOList.add(branchPatientPOJO);
                                        }
                                    }
                                }
                            }

                            if (staffbranchPatientPOJOList.size() > 0) {

                                List<String> staff_array = new ArrayList<>();
                                for (BranchPatientPOJO branchPatientPOJO : staffbranchPatientPOJOList) {
                                    staff_array.add(branchPatientPOJO.getFirstName() + " " + branchPatientPOJO.getLastName());
                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                        getApplicationContext(), R.layout.dropsimpledown, staff_array);
                                staff_name.setAdapter(spinnerArrayAdapter);
                            } else {
                                clearstaffspinner();
                            }
                            if (patientbranchPatientPOJOList.size() > 0) {
                                List<String> patient_array = new ArrayList<>();
                                for (BranchPatientPOJO branchPatientPOJO : staffbranchPatientPOJOList) {
                                    patient_array.add(branchPatientPOJO.getFirstName() + " " + branchPatientPOJO.getLastName());
                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                        getApplicationContext(), R.layout.dropsimpledown, patient_array);
                                p_name.setAdapter(spinnerArrayAdapter);
                            } else {
                                clearPatientSpinner();
                            }

//                            Log.e("result", response);
//                            JSONArray jsonArray = new JSONArray(response);
//                            Log.e("Post Method", jsonArray.toString());
//                            for (int i = 0; i < jsonArray.length(); i++) {
//
//                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//                                String status = jsonObject2.getString("status");
//                                if (status.equals("1")) {
//                                    Log.e("2", jsonObject2.toString());
//                                    String type = jsonObject2.getString("user_type");
//
//                                    if (type.equals("1")) {
//
//                                        String id = jsonObject2.getString("id");
//                                        String sfirst_name = jsonObject2.getString("first_name");
//                                        String last_name = jsonObject2.getString("last_name");
//                                        Detailapp = new InfoApps();
//                                        Detailapp.setName(sfirst_name + " " + last_name);
//                                        Detailapp.setId(id);
//                                        contactDetails1.add(Detailapp);
//                                        staffarray.add(Detailapp.getName());
//                                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
//                                                getApplicationContext(), R.layout.dropsimpledown, staffarray);
//                                        staff_name.setAdapter(spinnerArrayAdapter);
//                                    }
//                                    if (type.equals("0")) {
//                                        String id = jsonObject2.getString("id");
//                                        String sfirst_name = jsonObject2.getString("first_name");
//                                        String last_name = jsonObject2.getString("last_name");
//                                        Detailapp = new InfoApps();
//                                        Detailapp.setName(sfirst_name + " " + last_name);
//                                        Detailapp.setId(id);
//                                        contactDetails.add(Detailapp);
//                                        patientaray.add(Detailapp.getName());
////                                    contactDetails.add(Detailapp);
////                                    patientdarray.add(id);
//                                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
//                                                getApplicationContext(), R.layout.dropsimpledown, patientaray);
//                                        p_name.setAdapter(spinnerArrayAdapter);
//                                    }
//                                } else {
//                                    staffarray.clear();
//
//                                    patientaray.clear();
//                                    staffarray.add("Select Staff name");
//                                    patientaray.add("Select Patient name");
//                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
//                                            getApplicationContext(), R.layout.dropsimpledown, staffarray);
//                                    staff_name.setAdapter(spinnerArrayAdapter);
//
//                                    ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
//                                            getApplicationContext(), R.layout.dropsimpledown, patientaray);
//                                    p_name.setAdapter(spinnerArrayAdapter1);
//                                }
//                            }

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
                objresponse.put("bracch_code", branch_code);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    List<BranchPOJO> branchPOJOList = new ArrayList<>();

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
                branchPOJOList.clear();
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    BranchPOJO branchPOJO = new BranchPOJO();
                    branchPOJO.setBranch_id(jsonObject2.getString("branch_id"));
                    branchPOJO.setBranch_name(jsonObject2.getString("branch_name"));
                    branchPOJO.setBranch_code(jsonObject2.getString("branch_code"));
                    branchPOJO.setBranch_status(jsonObject2.getString("branch_status"));

                    branchPOJOList.add(branchPOJO);
                }

                if (branchPOJOList.size() > 0) {
                    List<String> stringList = new ArrayList<>();
                    for (BranchPOJO branchPOJO : branchPOJOList) {
                        stringList.add(branchPOJO.getBranch_name() + " (" + branchPOJO.getBranch_code() + ")");
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.dropsimpledown, stringList);
                    spinnerbranchloca.setAdapter(spinnerArrayAdapter);
                    viewStaffApiCall(branchPOJOList.get(0).getBranch_code());
                } else {
                    List<String> stringList = new ArrayList<>();

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.dropsimpledown, stringList);
                    spinnerbranchloca.setAdapter(spinnerArrayAdapter);
                }


            } catch (Exception e) {
                Log.e("error", e.toString());

            }
        }
    }
}
