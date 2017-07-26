/*
package viewreport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import com.capri4physio.fragment.assessment.InfoApps;
import com.capri4physio.fragment.assessment.LocationAdapter1;
import com.capri4physio.net.ApiConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * Created by Emobi-Android-002 on 7/16/2016.
 *//*

public class ViewReport extends AppCompatActivity implements View.OnClickListener{


    String patient,patientname;
    TextView txt_sensory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewallreport);

        patient=getIntent().getStringExtra("phone_no");
        patientname =getIntent().getStringExtra("patient_name");
        txt_sensory= (TextView) findViewById(R.id.txt_sensory);
        saveappointment();

    }
    private void saveappointment(){
        */
/*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*//*

        */
/*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*//*

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_SENSORY_REPORT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            */
/*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*//*


                            JSONObject objresponse = new JSONObject(response);
                            //					Toast.makeText(getApplicationContext(), "Could not retreive Data2!", Toast.LENGTH_LONG).show();

                            */
/*String success = objresponse.getString("isSuccess");
                            String success_msg = objresponse.getString("success_msg");*//*


//                            if (success.equalsIgnoreCase("true") || success_msg.equalsIgnoreCase("true")) {

                            Log.e("Postdat", "" + response);
                          JSONArray  jsonArray = objresponse.getJSONArray("result");


                            //Log.i("News Data", jsonArray.toString());

//                    JSONArray cast = jsonArray.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                               String booking_date=jsonObject.getString("booking_date");
                                String  getuniqueid=jsonObject.getString("id");
                                String booking_starttime = jsonObject.getString("booking_starttime");
                                String patientid = jsonObject.getString("patient_id");
                                String doctorid = jsonObject.getString("doctor_id");
                                String  reason= jsonObject.getString("reason");
                                Detailapp = new InfoApps();
                                Detailapp.setStr4(patientname);
                                Detailapp.setName(booking_date);//date
                                Detailapp.setNumber(booking_starttime);//startTime
                                Detailapp.setAppname(reason);//reason
                                Detailapp.setDataAdd(patientname);
                                Detailapp.setPatientid(patientid);
                                Detailapp.setDoctorid(getuniqueid);
                                contactDetails1.add(Detailapp);
                                locationAdapter1 = new LocationAdapter1(ViewReport.this, R.layout.listofpatientadappter);
                                listView.setAdapter(locationAdapter1);

                            }
                                    */
/*else {
                                        Toast.makeText(getApplicationContext(),"Pin number is incorrect",Toast.LENGTH_LONG).show();
                                    }*//*

//                            }
                            */
/*else {
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_sensory:
                startActivity(new Intent(ViewReport.this,SensoryReport.class));
                break;

        }
    }
}
*/
