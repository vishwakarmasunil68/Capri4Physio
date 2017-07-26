package com.capri4physio.fragment.assessment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
public class EditpatientActivity extends AppCompatActivity {
    public static EditText visit,patient_name,date,_starttime,booking_reason;
    String booking_Endtime,patientname,booking_date,booking_starttime,getPatientname,reason,visittype,uniqueid,bookingdateedit,doctoridedit,bookingstarttimeedit;
    JSONArray jsonArray;
    Button btn;
    String UPDATE_URL = "http://www.caprispine.in/users/updateappointment";
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatepatient);
//        patient=getIntent().getStringExtra("phone_no1");
        patient_name =(EditText)findViewById(R.id.patient_name);
        visit =(EditText)findViewById(R.id.visit);
        date=(EditText)findViewById(R.id.booking_date);
        _starttime=(EditText)findViewById(R.id.booking_starttime);
        booking_reason=(EditText)findViewById(R.id.reason);
        btn=(Button)findViewById(R.id.savebtn);

        getPatientname=getIntent().getStringExtra("doctoridedit");
        patientname=getIntent().getStringExtra("phone_no1");
        reason=getIntent().getStringExtra("reason");
        bookingstarttimeedit=getIntent().getStringExtra("bookingstarttimeedit");
        doctoridedit=getIntent().getStringExtra("nameedit");
        visittype=getIntent().getStringExtra("vist");
        bookingdateedit=getIntent().getStringExtra("bookingdateedit");
        Toast.makeText(getApplicationContext(),patientname + getPatientname+reason+bookingstarttimeedit + doctoridedit+ bookingdateedit,Toast.LENGTH_LONG).show();
//        saveappointment();
        patient_name.setText(patientname);
        date.setText(bookingdateedit);
        _starttime.setText(bookingstarttimeedit);
        booking_reason.setText(reason);
        visit.setText(visittype);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAppointment();
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
        final String time = _starttime.getText().toString().trim();
        final String p_name = patient_name.getText().toString().trim();
        final String reasonparam = booking_reason.getText().toString().trim();
        final String datepara = date.getText().toString().trim();
        final String v_type = visit.getText().toString().trim();
        /*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_URL,
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
                params.put(KEY_PATIENTID,doctoridedit);
                params.put(KEY_ID,getPatientname);
                params.put(KEY_DOCTORID,"2");
                params.put(KEY_DATEBOOKING, datepara);
                params.put(KEY_BOOKINGSTARTTIME, time);
                params.put("visit_type", v_type);
                params.put(KEY_BOOKINGENDTIME, time);
                params.put(KEY_REASON, reasonparam);


//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
