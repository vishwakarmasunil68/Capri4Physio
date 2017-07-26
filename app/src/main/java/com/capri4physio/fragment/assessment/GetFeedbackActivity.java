package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 7/18/2016.
 */
public class GetFeedbackActivity extends AppCompatActivity {
    String FeedbackURL = "http://www.caprispine.in/feedbacks/addfeedback";
    String JsonURL = "http://www.caprispine.in/feedbacks/getfeedbacklist";
    public static final String KEY_therapist_time = "therapist_time";
    public static final String KEY_therapist_knowledge = "therapist_knowledge";
    public static final String KEY_therapist_handlingskill = "therapist_handlingskill";
    public static final String KEY_therapist_behavior = "therapist_behavior";
    public static final String KEY_therapist_overal = "therapist_overal";
    public static final String KEY_centre_time = "centre_time";
    public static final String KEY_centre_facilities = "centre_facilities";
    public static final String KEY_centre_cleanness = "centre_cleanness";
    public static final String KEY_exp = "exp";
    Button btn_feedback,btn;
    ListView listView;
    private ProgressDialog pDialog;
    LocationAdapter3 locationAdapter3;
    public static ArrayList<InfoApps> feebacklist;;
    InfoApps Detailapp;
    LinearLayout linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getfeedbacklayout);
        btn_feedback=(Button)findViewById(R.id.btn_feedback);
        listView=(ListView)findViewById(R.id.list);
        feebacklist=new ArrayList<InfoApps>();
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            new CatagoryUrlAsynTask().execute();
        }
        else {
            Utils.showMessage(getApplicationContext(), getResources().getString(R.string.err_network));
        }
        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Feedback();
            }
        });*/
    }
    private class CatagoryUrlAsynTask extends AsyncTask<String, String, String> {
        String id, catagoryName;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
                initProgressDialog("Please wait...");
        }

        @Override
        protected String doInBackground(String... params) {

//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
            String content = HttpULRConnect.getData(JsonURL);
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
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                Log.e("Post Method", jsonArray.toString());
                for (int i = 1; i <= jsonArray.length(); i++) {

                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String therapist_time = jsonObject2.getString("therapist_time");
                    String therapist_knowledge = jsonObject2.getString("therapist_knowledge");
                    String therapist_handlingskill = jsonObject2.getString("therapist_handlingskill");
                    String therapist_behavior = jsonObject2.getString("therapist_behavior");
                    String therapist_overal = jsonObject2.getString("therapist_overal");
                    String centre_time = jsonObject2.getString("centre_time");
                    String centre_facilities = jsonObject2.getString("centre_facilities");
                    String centre_cleanness = jsonObject2.getString("centre_cleanness");
                    String exp=jsonObject2.getString("exp");
//                        int userid = Integer.parseInt(jsonObject2.getInt((i+1) + ""));
//                        int userid=Integer.parseInt(jsonObject2.getString((i+1) + ""));
//                    Log.e("usertype", usertype.toString());

                        Detailapp = new InfoApps();
                        Detailapp.setName(therapist_time);
                        Detailapp.setAppname(therapist_knowledge);
                        Detailapp.setDataAdd(therapist_handlingskill);
                    Detailapp.setPatientid(therapist_behavior);
                    Detailapp.setDoctorid(therapist_overal);
                    Detailapp.setExp(exp);
                    Detailapp.setNumber(centre_time);
                    Detailapp.setData(centre_facilities);
                    Detailapp.setStr4(centre_cleanness);
                    feebacklist.add(Detailapp);
                    Log.e("feebacklist","list:-"+feebacklist.toString());
                    locationAdapter3 = new LocationAdapter3(getApplicationContext(), R.layout.getfeedbackadapter,feebacklist);
                    listView.setAdapter(locationAdapter3);
//                        Toast.makeText(getApplicationContext(),usertype,Toast.LENGTH_LONG).show();
//                        Toast.makeText(getApplicationContext(),userid,Toast.LENGTH_LONG).show();
//                        int value = jsonObject1.getInt(i + 1);
                }

            }catch(Exception e){
                e.printStackTrace();

            }
            //  CatagoryMetod();
        }
    }
    private void initProgressDialog(String loadingText) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(loadingText);
        pDialog.setCancelable(false);
        pDialog.show();
    }
    private void Feedback(){
        /*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, FeedbackURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            finish();

                            Log.e("Postdat", "" + response);
                            /*jsonArray = objresponse.getJSONArray("result");


                            //Log.i("News Data", jsonArray.toString());

//                    JSONArray cast = jsonArray.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                booking_date=jsonObject.getString("booking_date");
                                getuniqueid=jsonObject.getString("id");
                                booking_starttime = jsonObject.getString("booking_starttime");
                                patientid = jsonObject.getString("patient_id");
                                doctorid = jsonObject.getString("doctor_id");
                                reason= jsonObject.getString("reason");
                                Detailapp = new InfoApps();
                                Detailapp.setStr4(getuniqueid);
                                Detailapp.setName(booking_date);
                                Detailapp.setNumber(booking_starttime);
                                Detailapp.setAppname(reason);
                                Detailapp.setDataAdd(patientname);
                                Detailapp.setPatientid(patientid);
                                Detailapp.setDoctorid(doctorid);
                                contactDetails1.add(Detailapp);
                                locationAdapter1 = new LocationAdapter1(getApplicationContext(), R.layout.listofpatientadappter);
                                listView.setAdapter(locationAdapter1);*/

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
                params.put(KEY_therapist_time,"TEST");
                params.put(KEY_therapist_knowledge,"TEST");
                params.put(KEY_therapist_handlingskill,"TEST");
                params.put(KEY_therapist_behavior, "TEST");
                params.put(KEY_therapist_overal, "TEST");
                params.put(KEY_centre_time, "TEST");
                params.put(KEY_centre_facilities, "Test");
                params.put(KEY_centre_cleanness, "TEST");
                params.put(KEY_exp, "TEST");

//                Toast.makeText(UserStatement.this, username +"success", Toast.LENGTH_LONG).show();
//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
