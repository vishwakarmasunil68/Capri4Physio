package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.util.AppPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 7/16/2016.
 */
public class Viewpatientinlist extends AppCompatActivity {
    String JsonURL = "http://www.caprispine.in/users/getappointmentlist";
    public static final String KEY_PATIENTID = "patient_id";
   public static Viewpatientinlist viewpatientinlist;
    public static ArrayList<InfoApps> contactDetails1;
    public static TextView patient_name,date,_starttime,booking_reason;
    public static String patient,patientname,booking_date,booking_starttime,getPatientname,reason,getuniqueid,patientid,doctorid,visit_type;
    JSONArray jsonArray;
    Button btn;
    InfoApps Detailapp;
    ListView listView;
    LocationAdapter1 locationAdapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listofpatient);
        contactDetails1=new ArrayList<InfoApps>();
        listView=(ListView)findViewById(R.id.list);

        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("0")){
            patient=AppPreferences.getInstance(getApplicationContext()).getUserID();
            patientname = AppPreferences.getInstance(getApplicationContext()).getFirstName();
        }
        else {
            patient = getIntent().getStringExtra("phone_no");
            patientname = getIntent().getStringExtra("patient_name");
        }
        saveappointment();

    }
    private void saveappointment(){
        /*final String date = ed
        it_date.getText().toString().trim();
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
                            for (int i = jsonArray.length()-1;  i >=0 ; i--) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                booking_date=jsonObject.getString("booking_date");
                                getuniqueid=jsonObject.getString("id");
                                booking_starttime = jsonObject.getString("booking_starttime");
                                patientid = jsonObject.getString("patient_id");
                                doctorid = jsonObject.getString("doctor_id");
                                reason= jsonObject.getString("reason");
                                visit_type =jsonObject.getString("visit_type");
                                Detailapp = new InfoApps();
                                Detailapp.setStr4(patientname);
                                Detailapp.setName(booking_date);//date
                                Detailapp.setNumber(booking_starttime);//startTime
                                Detailapp.setAppname(reason);//reason
                                Detailapp.setDataAdd(doctorid);
                                Detailapp.setPatientid(patientid);
                                Detailapp.setDoctorid(getuniqueid);
                                Detailapp.setExp(visit_type);
                                contactDetails1.add(Detailapp);
                                locationAdapter1 = new LocationAdapter1(Viewpatientinlist.this, R.layout.listofpatientadappter);
                                listView.setAdapter(locationAdapter1);

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                Log.e("sun","result:-"+result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public void onBackPressed() {
        Log.e("onBack","onBackPressed");
        super.onBackPressed();
    }
}
