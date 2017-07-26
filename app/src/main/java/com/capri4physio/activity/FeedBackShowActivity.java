package com.capri4physio.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.fragment.assessment.InfoApps;
import com.capri4physio.fragment.assessment.LocationAdapter3;
import com.capri4physio.util.AppPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FeedBackShowActivity extends AppCompatActivity {
    String FeedbackURL = "http://www.caprispine.in/feedbacks/addfeedback";
    public static final String KEY_therapist_time = "therapist_time";
    RadioGroup rg,rg1,rg2,rg3,rg4,rg5,rg6,rg7;
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
    LocationAdapter3 locationAdapter3;
    public static ArrayList<InfoApps> feebacklist;
    InfoApps Detailapp;
    EditText edittext;
    LinearLayout linear,linear1;
    RelativeLayout linear2;
    String rate,rate1,rate2,rate3,rate4,rate5,rate6,rate7;
    ImageView imageView1,imageView2,imageView3;
    TextView textfirst;
    Boolean expand=false;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_show);
        btn=(Button)findViewById(R.id.btn);
        rg=(RadioGroup)findViewById(R.id.rg);
        rg1=(RadioGroup)findViewById(R.id.rg1);
        rg2=(RadioGroup)findViewById(R.id.rg2);
        rg3=(RadioGroup)findViewById(R.id.rg3);
        rg4=(RadioGroup)findViewById(R.id.rg4);
        rg5=(RadioGroup)findViewById(R.id.rg5);
        rg6=(RadioGroup)findViewById(R.id.rg6);
        rg7=(RadioGroup)findViewById(R.id.rg7);
        edittext=(EditText)findViewById(R.id.edittext);
        imageView1= (ImageView)findViewById(R.id.image1);
        imageView2= (ImageView)findViewById(R.id.image2);
        imageView3= (ImageView)findViewById(R.id.image3);
        feebacklist=new ArrayList<InfoApps>();
        linear=(LinearLayout)findViewById(R.id.linear1);
        linear1=(LinearLayout)findViewById(R.id.linear2);
        linear2=(RelativeLayout)findViewById(R.id.linear3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgressDialog("Please wait...");
                Feedback();
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linear.getVisibility()==View.VISIBLE) {
                    linear.setVisibility(View.GONE);
                }
                else {
                    linear.setVisibility(View.VISIBLE);
                }
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linear1.getVisibility()==View.VISIBLE) {
                    linear1.setVisibility(View.GONE);
                }
                else {
                    linear1.setVisibility(View.VISIBLE);
                }
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linear2.getVisibility()==View.VISIBLE) {
                    linear2.setVisibility(View.GONE);
                }
                else {
                    linear2.setVisibility(View.VISIBLE);
                }
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos = rg.indexOfChild(findViewById(checkedId));
                /*Toast.makeText(getBaseContext(), "Method 1 ID = " + String.valueOf(pos),
                        Toast.LENGTH_SHORT).show();*/
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();
                /*Intent intent1=new Intent(TimeSetActivity.this,DataMovement.class);
                intent1.putExtra("timeindex0",pos);
                startActivity(intent1);*/
                switch (pos) {

                    case 0:
                        rate = "Poor";
                        break;
                    case 1:
                        rate = "Fair";
                        break;
                    case 2:
                        rate = "Good";
                        break;
                    case 3:
                        rate = "Very Good";
                        break;
                    default:
                        break;
                }
            }
        });
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos1 = rg1.indexOfChild(findViewById(checkedId));
                /*Toast.makeText(getBaseContext(), "Method 1 ID = " + String.valueOf(pos),
                        Toast.LENGTH_SHORT).show();*/
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();
                /*Intent intent1=new Intent(TimeSetActivity.this,DataMovement.class);
                intent1.putExtra("timeindex0",pos);
                startActivity(intent1);*/
                switch (pos1) {

                    case 0:
                        rate1 = "Poor";
                        break;
                    case 1:
                        rate1 = "Fair";
                        break;
                    case 2:
                        rate1 = "Good";
                        break;
                    case 3:
                        rate1 = "Very Good";
                        break;
                    default:
                        break;
                }
            }
        });
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos2 = rg2.indexOfChild(findViewById(checkedId));
                /*Toast.makeText(getBaseContext(), "Method 1 ID = " + String.valueOf(pos),
                        Toast.LENGTH_SHORT).show();*/
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();
                /*Intent intent1=new Intent(TimeSetActivity.this,DataMovement.class);
                intent1.putExtra("timeindex0",pos);
                startActivity(intent1);*/
                switch (pos2) {

                    case 0:
                        rate2 = "Poor";
                        break;
                    case 1:
                        rate2 = "Fair";
                        break;
                    case 2:
                        rate2 = "Good";
                        break;
                    case 3:
                        rate2 = "Very Good";
                        break;
                    default:
                        break;
                }
            }
        });
        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos3 = rg3.indexOfChild(findViewById(checkedId));
                /*Toast.makeText(getBaseContext(), "Method 1 ID = " + String.valueOf(pos),
                        Toast.LENGTH_SHORT).show();*/
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();
                /*Intent intent1=new Intent(TimeSetActivity.this,DataMovement.class);
                intent1.putExtra("timeindex0",pos);
                startActivity(intent1);*/
                switch (pos3) {

                    case 0:
                        rate3 = "Poor";
                        break;
                    case 1:
                        rate3 = "Fair";
                        break;
                    case 2:
                        rate3 = "Good";
                        break;
                    case 3:
                        rate3 = "Very Good";
                        break;
                    default:
                        break;
                }
            }
        });
        rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos4 = rg4.indexOfChild(findViewById(checkedId));
                /*Toast.makeText(getBaseContext(), "Method 1 ID = " + String.valueOf(pos),
                        Toast.LENGTH_SHORT).show();*/
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();
                /*Intent intent1=new Intent(TimeSetActivity.this,DataMovement.class);
                intent1.putExtra("timeindex0",pos);
                startActivity(intent1);*/
                switch (pos4) {

                    case 0:
                        rate4 = "Poor";
                        break;
                    case 1:
                        rate4 = "Fair";
                        break;
                    case 2:
                        rate4 = "Good";
                        break;
                    case 3:
                        rate4 = "Very Good";
                        break;
                    default:
                        break;
                }
            }
        });
        rg5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos5 = rg5.indexOfChild(findViewById(checkedId));
                /*Toast.makeText(getBaseContext(), "Method 1 ID = " + String.valueOf(pos),
                        Toast.LENGTH_SHORT).show();*/
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();
                /*Intent intent1=new Intent(TimeSetActivity.this,DataMovement.class);
                intent1.putExtra("timeindex0",pos);
                startActivity(intent1);*/
                switch (pos5) {

                    case 0:
                        rate5 = "Poor";
                        break;
                    case 1:
                        rate5 = "Fair";
                        break;
                    case 2:
                        rate5 = "Good";
                        break;
                    case 3:
                        rate5 = "Very Good";
                        break;
                    default:
                        break;
                }
            }
        });
        rg6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos6 = rg6.indexOfChild(findViewById(checkedId));
                /*Toast.makeText(getBaseContext(), "Method 1 ID = " + String.valueOf(pos),
                        Toast.LENGTH_SHORT).show();*/
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();
                /*Intent intent1=new Intent(TimeSetActivity.this,DataMovement.class);
                intent1.putExtra("timeindex0",pos);
                startActivity(intent1);*/
                switch (pos6) {

                    case 0:
                        rate6 = "Poor";
                        break;
                    case 1:
                        rate6 = "Fair";
                        break;
                    case 2:
                        rate6 = "Good";
                        break;
                    case 3:
                        rate6 = "Very Good";
                        break;
                    default:
                        break;
                }
            }
        });
        rg7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos7 = rg7.indexOfChild(findViewById(checkedId));
                /*Toast.makeText(getBaseContext(), "Method 1 ID = " + String.valueOf(pos),
                        Toast.LENGTH_SHORT).show();*/
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();
                /*Intent intent1=new Intent(TimeSetActivity.this,DataMovement.class);
                intent1.putExtra("timeindex0",pos);
                startActivity(intent1);*/
                switch (pos7) {

                    case 0:
                        rate7 = "Poor";
                        break;
                    case 1:
                        rate7 = "Fair";
                        break;
                    case 2:
                        rate7 = "Good";
                        break;
                    case 3:
                        rate7 = "Very Good";
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void Feedback(){
        final String exp = edittext.getText().toString().trim();
        /*final String time = edit_time.getText().toString().trim();
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
                            Toast.makeText(getApplicationContext(),"Feedback succesfully added",Toast.LENGTH_LONG).show();
                            pDialog.dismiss();

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

                params.put(KEY_therapist_knowledge,rate);
                params.put(KEY_therapist_handlingskill,rate1);
                params.put(KEY_therapist_behavior, rate2);
                params.put(KEY_therapist_overal, rate3);
                params.put(KEY_centre_time, rate4);
                params.put(KEY_centre_facilities, rate5);
                params.put(KEY_centre_cleanness, rate6);
                params.put(KEY_therapist_time,rate7);
                params.put(KEY_exp, exp);
                params.put("feedback_branch_code", AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE());
                params.put("feedback_p_id", AppPreferences.getInstance(getApplicationContext()).getUserID());

//                Toast.makeText(UserStatement.this, username +"success", Toast.LENGTH_LONG).show();
//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
