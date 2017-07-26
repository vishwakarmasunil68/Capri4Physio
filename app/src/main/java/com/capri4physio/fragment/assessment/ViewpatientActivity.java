package com.capri4physio.fragment.assessment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 7/15/2016.
 */
public class ViewpatientActivity extends AppCompatActivity {
    String JsonURL = "http://www.caprispine.in/users/getappointmentlist";
    public static final String KEY_PATIENTID = "patient_id";
    public static TextView patient_id,patient_name,date,visit,_starttime,Consultants,booking_reason;
    public static String vist,patient,patientname,booking_date,booking_starttime,getPatientname,reason;
    JSONArray jsonArray;
    Button btn;
    InfoApps Detailapp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpatient);


        patient_name=(TextView)findViewById(R.id.patient_name);
        patient_id= (TextView) findViewById(R.id.patient_id);
        date=(TextView)findViewById(R.id.booking_date);
        visit =(TextView)findViewById(R.id.vist);
        _starttime =(TextView)findViewById(R.id.booking_starttime);
        Consultants =(TextView)findViewById(R.id.Consultants);
        booking_reason=(TextView)findViewById(R.id.reason);
        btn=(Button)findViewById(R.id.savebtn);

         patient=getIntent().getStringExtra("phone_no");
        patientname=getIntent().getStringExtra("patient_name");
        booking_date=getIntent().getStringExtra("date");
        booking_starttime=getIntent().getStringExtra("startTime");
        reason=getIntent().getStringExtra("reason");
        vist=getIntent().getStringExtra("vist");

        patient_id.setText(LocationAdapter.patientCode);
        patient_name.setText(Viewpatientinlist.patientname);
        date.setText(booking_date);
        visit.setText(vist);
        _starttime.setText(booking_starttime);
        Consultants.setText(getIntent().getStringExtra("doctorname"));
        booking_reason.setText(reason);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        saveappointment();

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
                                    booking_starttime = jsonObject.getString("booking_starttime");
                                    reason = jsonObject.getString("reason");
                                    patient_name.setText(patientname);
                                    date.setText(booking_date);
                                    _starttime.setText(booking_starttime);
                                    booking_reason.setText(reason);
                                    Detailapp = new InfoApps();
                                    Detailapp.setName(booking_date);
                                    Detailapp.setNumber(booking_starttime);
                                    Detailapp.setAppname(reason);
                                    Detailapp.setDataAdd(patientname);


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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
