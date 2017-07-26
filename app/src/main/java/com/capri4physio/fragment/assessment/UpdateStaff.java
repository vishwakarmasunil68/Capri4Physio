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
import com.capri4physio.net.ApiConfig;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 7/15/2016.
 */
public class UpdateStaff extends AppCompatActivity {
    public static EditText staffemail,staffname,_starttime,booking_reason,dob,exp_Designation,exp,Quali,doj,mar,pc,gender,address,city,Designation,send_date;
    String booking_Endtime,patientname,booking_date,booking_starttime,getPatientname,reason,mobile_number
            ,saddress,bookingdateedit,doctoridedit,bookingstarttimeedit,dob1,exp1,Quali1,doj1,ma1r,pc1,gender1,address1,city1,Age1;
    public  EditText Institution,University,Nature,Degree,organisation,Average,Duration;
    JSONArray jsonArray;
    Button btn;
    String UPDATE_STAFF_URL = ApiConfig.BASE_URL+"users/updatestaff";
    String JsonURL = ApiConfig.BASE_URL+"users/getappointmentlist";
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
        setContentView(R.layout.updatestaff);
//        patient=getIntent().getStringExtra("phone_no1");
        staffemail=(EditText)findViewById(R.id.patient_name);
        staffname=(EditText)findViewById(R.id.booking_date);
        _starttime=(EditText)findViewById(R.id.booking_starttime);
        booking_reason=(EditText)findViewById(R.id.reason);
        dob=(EditText)findViewById(R.id.booking_dob);
        exp=(EditText)findViewById(R.id.booking_exp);
        Quali=(EditText)findViewById(R.id.booking_qualification);
        address=(EditText)findViewById(R.id.booking_address);
        city=(EditText)findViewById(R.id.booking_city);
        pc=(EditText)findViewById(R.id.booking_pc);
        mar=(EditText)findViewById(R.id.booking_marital_status);
        gender=(EditText)findViewById(R.id.booking_gen);
        doj=(EditText)findViewById(R.id.booking_joiningdate);
        Designation=(EditText)findViewById(R.id.Designation);
        send_date=(EditText)findViewById(R.id.booking_senddate);
        btn=(Button)findViewById(R.id.savebtn);
        exp_Designation=(EditText)findViewById(R.id.exp_Designation);

        Institution=(EditText)findViewById(R.id.Institution);
        University=(EditText)findViewById(R.id.University);
        Duration=(EditText)findViewById(R.id.Duration);
        Average=(EditText)findViewById(R.id.Average);
        organisation=(EditText)findViewById(R.id.organisation);
        Nature =(EditText)findViewById(R.id.Nature_work);
        Degree =(EditText)findViewById(R.id.Degree);


        Nature.setText(getIntent().getStringExtra("work"));
        Degree.setText(getIntent().getStringExtra("degree"));
        organisation.setText(getIntent().getStringExtra("organisation"));
        Institution.setText(getIntent().getStringExtra("institution"));
        University.setText(getIntent().getStringExtra("university"));
        Duration.setText(getIntent().getStringExtra("duration"));
        Average.setText(getIntent().getStringExtra("average"));

        getPatientname=getIntent().getStringExtra("staffname");
        patientname=getIntent().getStringExtra("staffemail");
        reason=getIntent().getStringExtra("staffid");
        mobile_number=getIntent().getStringExtra("staffphone_no");
        mobile_number=getIntent().getStringExtra("staffphone_no");
        dob1=getIntent().getStringExtra("dob");
        exp1=getIntent().getStringExtra("exp");
        Quali1=getIntent().getStringExtra("password");
        address1=getIntent().getStringExtra("city");
        pc1=getIntent().getStringExtra("pc");
        ma1r=getIntent().getStringExtra("maried_status");
        gender1=getIntent().getStringExtra("doj");
        Age1=getIntent().getStringExtra("Age");
        doctoridedit=getIntent().getStringExtra("desi");
        exp_Designation.setText(getIntent().getStringExtra("designation1"));
        bookingdateedit=getIntent().getStringExtra("End");
        saddress = getIntent().getStringExtra("saddress");
        Toast.makeText(getApplicationContext(),patientname + getPatientname+reason+bookingstarttimeedit + doctoridedit+ bookingdateedit,Toast.LENGTH_LONG).show();
//        saveappointment();
        staffemail.setText(patientname);
        staffname.setText(getPatientname);
        _starttime.setText(mobile_number);
        dob.setText(dob1);
        doj.setText(gender1);
        exp.setText(bookingdateedit);
        Quali.setText(Quali1);
        city.setText(address1);
        pc.setText(pc1);
        mar.setText(ma1r);
        gender.setText(Age1);
        Designation.setText(doctoridedit);
        address.setText(saddress);
        send_date.setText(bookingdateedit);
//        booking_reason.setText(reason);
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
        final String phone = _starttime.getText().toString().trim();
        final String p_email = staffemail.getText().toString().trim();
//        final String reasonparam = booking_reason.getText().toString().trim();
        final String p_name = staffname.getText().toString().trim();
        final String date = dob.getText().toString().trim();
        final String time = doj.getText().toString().trim();
        final String reason1 = send_date.getText().toString().trim();
        final String Gender = gender.getText().toString().trim();
        final String maritl = mar.getText().toString().trim();
        final String designation = Designation.getText().toString().trim();
        final String Address = address.getText().toString().trim();
        final String City = city.getText().toString().trim();
        final String Pin_Code = pc.getText().toString().trim();
        final String qualifi = Quali.getText().toString().trim();
        final String experience = exp.getText().toString().trim();

        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_STAFF_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(getApplicationContext(), "Updated successfully.", Toast.LENGTH_LONG).show();
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
                params.put("first_name",p_email);
                params.put("last_name","");
                params.put("user_id",reason);
                params.put("email",p_name);
                params.put("mobile", phone);
                params.put("pincode", Pin_Code);
                params.put("dob", date);
                params.put("age","sage");
                params.put("datejoing",time);
                params.put("gender",Gender);
                params.put("designation", designation);
                params.put("designation1", exp_Designation.getText().toString());
                params.put("address", Address);
                params.put("city", City);
                params.put("qualifation", qualifi);
                params.put("exprience",experience);
                params.put("end_date_contract",experience);
                params.put("degree",Degree.getText().toString());
                params.put("institution",Institution.getText().toString());
                params.put("university",University.getText().toString());
                params.put("duration",Duration.getText().toString());
                params.put("password",Quali.getText().toString());
                params.put("average",Average.getText().toString());
                params.put("organisation",organisation.getText().toString());
                params.put("nature_of_work",Nature.getText().toString());

//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
