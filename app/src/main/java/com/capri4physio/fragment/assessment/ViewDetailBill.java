package com.capri4physio.fragment.assessment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 7/15/2016.
 */
public class ViewDetailBill extends AppCompatActivity {
    public static EditText p_name,staffname,status,pro_name,pro_quanty,bill_number,paid_amount,
            bill_amount,due_amount,invodate,gender,address,city,Designation,send_date;
    String booking_Endtime,patientname,booking_date,booking_starttime,getPatientname,reason,mobile_number
            ,uniqueid,bookingdateedit,doctoridedit,bookingstarttimeedit,dob1,exp1,Quali1,doj1,ma1r,pc1,gender1,address1,city1,Age1;
    JSONArray jsonArray;
    Button btn;
    String UPDATE_STAFF_URL = "http://www.caprispine.in/users/updatestaff";
    String JsonURL = "http://www.caprispine.in/users/getappointmentlist";
    InfoApps Detailapp;
    public static final String KEY_ID = "id";
    public static final String KEY_PATIENTID = "patient_id";
    public static final String KEY_DOCTORID = "doctor_id";
    public static final String KEY_DATEBOOKING = "booking_date";
    public static final String KEY_BOOKINGSTARTTIME = "booking_starttime";
    public static final String KEY_BOOKINGENDTIME = "booking_endtime";
    public static final String KEY_REASON = "reason";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdetailofbill);
//        patient=getIntent().getStringExtra("phone_no1");
        p_name=(EditText)findViewById(R.id.Patient_name);
        staffname=(EditText)findViewById(R.id.Staff_Name);
        status=(EditText)findViewById(R.id.Invoice_status);
        pro_name=(EditText)findViewById(R.id.Pro_name);
        pro_quanty=(EditText)findViewById(R.id.Pro_quantity);
        bill_number=(EditText)findViewById(R.id.Bill_number);
        paid_amount=(EditText)findViewById(R.id.Paid_amount);
        bill_amount=(EditText)findViewById(R.id.Bill_amount);
//        city=(EditText)findViewById(R.id.booking_city);
        due_amount=(EditText)findViewById(R.id.Due_amount);
        invodate=(EditText)findViewById(R.id.Invoice_date);

        btn=(Button)findViewById(R.id.savebtn);

        getPatientname=getIntent().getStringExtra("staffname");
        patientname=getIntent().getStringExtra("staffemail");
        reason=getIntent().getStringExtra("staffid");
        mobile_number=getIntent().getStringExtra("staffphone_no");
        dob1=getIntent().getStringExtra("dob");
        exp1=getIntent().getStringExtra("exp");
        Quali1=getIntent().getStringExtra("Quali");
        address1=getIntent().getStringExtra("city");
        pc1=getIntent().getStringExtra("pc");
        ma1r=getIntent().getStringExtra("maried_status");
        gender1=getIntent().getStringExtra("doj");
        Age1=getIntent().getStringExtra("Age");
        doctoridedit=getIntent().getStringExtra("desi");
        bookingdateedit=getIntent().getStringExtra("send_date");
        Toast.makeText(getApplicationContext(),patientname + getPatientname+reason+bookingstarttimeedit + doctoridedit+ bookingdateedit,Toast.LENGTH_LONG).show();
//        saveappointment();
        /*staffemail.setText(getPatientname);
        staffname.setText(patientname);
        _starttime.setText(mobile_number);
        dob.setText(dob1);
        doj.setText(gender1);
        exp.setText(exp1);
        Quali.setText(Quali1);
        city.setText(address1);
        pc.setText(pc1);
        mar.setText(ma1r);
        gender.setText(Age1);
        _starttime.setText(mobile_number);*/
        Designation.setText(doctoridedit);
        send_date.setText(bookingdateedit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });


    }
    /*private void saveappointment(){
        final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();
        *//*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*//*
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JsonURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            *//*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*//*

                            JSONObject objresponse = new JSONObject(response);
                            //					Toast.makeText(getApplicationContext(), "Could not retreive Data2!", Toast.LENGTH_LONG).show();

                            *//*String success = objresponse.getString("isSuccess");
                            String success_msg = objresponse.getString("success_msg");*//*

//                            if (success.equalsIgnoreCase("true") || success_msg.equalsIgnoreCase("true")) {

                            Log.e("Postdat", "" + response);
                            jsonArray = objresponse.getJSONArray("result");


                            //Log.i("News Data", jsonArray.toString());

//                    JSONArray cast = jsonArray.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                *//*GetDcotorid=jsonObject.getString("doctor_id");
                                booking_date=jsonObject.getString("booking_date");
                                uniqueid=jsonObject.getString("id");
                                booking_starttime = jsonObject.getString("booking_starttime");
                                booking_Endtime=jsonObject.getString("booking_endtime");
                                reason = jsonObject.getString("reason");*//*
                                Detailapp = new InfoApps();
                                Detailapp.setName(booking_date);
                                Detailapp.setNumber(booking_starttime);
                                Detailapp.setAppname(reason);
                                Detailapp.setDataAdd(patientname);


                            }
                                    *//*else {
                                        Toast.makeText(getApplicationContext(),"Pin number is incorrect",Toast.LENGTH_LONG).show();
                                    }*//*
//                            }
                            *//*else {
                                Toast.makeText(getApplicationContext(),"Phone_no. or password is incorrect",Toast.LENGTH_LONG).show();
                            }*//*
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/
    private void editAppointment(){
        /*final String phone = _starttime.getText().toString().trim();
        final String p_email = staffemail.getText().toString().trim();*/
//        final String reasonparam = booking_reason.getText().toString().trim();
        final String p_name = staffname.getText().toString().trim();
        /*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_STAFF_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(getApplicationContext(), "Appointment request has been sent successfully.", Toast.LENGTH_LONG).show();
                            finish();
                            Log.e("result",response);
                            /*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*/

                            /*JSONObject objresponse = new JSONObject(response);
                            //					Toast.makeText(getApplicationContext(), "Could not retreive Data2!", Toast.LENGTH_LONG).show();

                            String success = objresponse.getString("isSuccess");
                            String success_msg = objresponse.getString("success_msg");

                            if (success.equalsIgnoreCase("true") || success_msg.equalsIgnoreCase("true")) {

                                Log.e("Postdat", "" + response);
                                jsonArray = objresponse.getJSONArray("result");


                                //Log.i("News Data", jsonArray.toString());

//                    JSONArray cast = jsonArray.getJSONArray("result");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    blnc_id = jsonObject.getString("receiver_name");
                                    trnsdtime = jsonObject.getString("transaction_datetime");
                                    trnsamount= jsonObject.getString("balance_amount");
                                    trnsamounttype= jsonObject.getString("transaction_transfer_type");
//                                     balance_id=new ArrayList<String>();
//                                    balance_id.add(blnc_id);
                                    Detailapp = new InfoApps();
                                    Detailapp.setName(blnc_id);
                                    Detailapp.setNumber(trnsdtime);
                                    Detailapp.setAppname(trnsamount);
                                    Detailapp.setDataAdd(trnsamounttype);
                                    Log.e("account_blnc_id", blnc_id);
                                    Log.e("account_balance_id", contactDetails.toString());
//                                    if (BalanceDetail.password.equals(pinpassword)) {
                                    pass.setVisibility(View.GONE);
                                    linear.setVisibility(View.VISIBLE);
                                    contactAdapter = new LocationAdapter(getApplicationContext(), R.layout.contactlistadap);
                                    contactList.setAdapter(contactAdapter);
//                                    Double user_long = jsonObject.getDouble("user_long");
//                                    Double user_lat = jsonObject.getDouble("user_lat");
//                                    UserType = "UserType: " + jsonObject.getString("usertype");
                                    *//*Intent intent1 = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent1);*//*
                                    *//*}
                                    else {
                                        Toast.makeText(getApplicationContext(),"Pin number is incorrect",Toast.LENGTH_LONG).show();
                                    }*//*
                                }*/
//                            }
                           /* else {
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
                params.put("sfirst_name",p_name);
                params.put("slast_name",p_name);
                params.put(KEY_ID,reason);
                /*params.put("semail",p_email);
                params.put("smobile", phone);*/
                params.put("spincode", "110088");
                params.put("smarital_status", "Married");
                params.put("sdob", "18-02-2016");
                params.put("sage","sage");
                params.put("sdatejoing","sdatejoing");
                params.put("senddate","18-02-2016");
                params.put("sgender","Male");
                params.put("sdesignation", "sdesignation");
                params.put("saddress", "saddress");
                params.put("scity", "new dehi");
                params.put("squalifation", "Phys");
                params.put("sexprience","13");
//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
