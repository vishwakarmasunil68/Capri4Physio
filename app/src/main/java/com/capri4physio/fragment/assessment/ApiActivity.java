package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by emobi5 on 7/6/2016.
 */
public class ApiActivity extends Activity{
    Button save;
    String patient,patientname,booking_date,booking_starttime,getPatientname,reason;
    String GET_CANCEL_lISTURL = "http://www.caprispine.in/users/getcancelledlist";
    String JsonURL = "http://www.caprispine.in/users/getappointmentlist";
    InfoApps Detailapp;
    public static ArrayList<InfoApps> deatilsarraylist;
    public static final String KEY_ID = "id";
    public static final String KEY_PATIENTID = "patient_id";
    ListView listView;
    LocationAdapter2 locationAdapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motorapi);
        save=(Button)findViewById(R.id.btn_for_api);
        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("0")){
            patient = AppPreferences.getInstance(getApplicationContext()).getUserID();
        }
       else {
            patient = getIntent().getStringExtra("phone_no");
        }
        deatilsarraylist=new ArrayList<InfoApps>();
        listView=(ListView)findViewById(R.id.list);
        loginUser();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
finish();
            }
        });
    }
    private void loginUser() {
        /*final String passwordlogin = edit_loginpass.getText().toString().trim();
        final String emaillogin = edit_loginemail.getText().toString().trim();*/

        try {


            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    GET_CANCEL_lISTURL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    Log.e("response data", response);
                    try {
                        JSONObject objresponse = new JSONObject(response);
                        //					Toast.makeText(getApplicationContext(), "Could not retreive Data2!", Toast.LENGTH_LONG).show();

                            /*String success = objresponse.getString("isSuccess");
                            String success_msg = objresponse.getString("success_msg");*/

//                            if (success.equalsIgnoreCase("true") || success_msg.equalsIgnoreCase("true")) {

                        Log.e("Postdat", "" + response);
                         JSONArray jsonArray = objresponse.getJSONArray("result");


                        //Log.i("News Data", jsonArray.toString());

//                    JSONArray cast = jsonArray.getJSONArray("result");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            booking_date = jsonObject.getString("booking_date");
                            booking_starttime = jsonObject.getString("booking_starttime");
                            reason = jsonObject.getString("reason");
                            String ID =jsonObject.getString("id");
                            Detailapp = new InfoApps();
                            Detailapp.setName(booking_date);
                            Detailapp.setNumber(booking_starttime);
                            Detailapp.setAppname(reason);
                            Detailapp.setId(ID);
                            deatilsarraylist.add(Detailapp);
                            Log.e("Postdat", "" + deatilsarraylist.toString());
                            locationAdapter2 = new LocationAdapter2(getApplicationContext(), R.layout.getcancelladapter);
                            listView.setAdapter(locationAdapter2);

                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
	/*
							Toast.makeText(HomeActivity.this,
									response + "SucessFully Registered..",
									Toast.LENGTH_LONG).show();*/
                    //    					new CallLoginServices().execute();


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
							Toast.makeText(ApiActivity.this, error.toString(),
                                    Toast.LENGTH_LONG).show();
                }
            }
            ) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    if (response.headers == null)
                    {
                        // cant just set a new empty map because the member is final.
                        response = new NetworkResponse(
                                response.statusCode,
                                response.data,
                                Collections.<String, String>emptyMap(), // this is the important line, set an empty but non-null map.
                                response.notModified,
                                response.networkTimeMs);


                    }

                    return super.parseNetworkResponse(response);

                };
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_PATIENTID,patient);

                    /*params.put(KEY_METHOD, "doDoctorSingIn");//method
                    params.put(KEY_LOGIN_USERNAME, emaillogin);//name
                    params.put(KEY_LOGIN_PASSWORD, passwordlogin);//pswd*/

                    //				params.put(KEY_USERDOB, email);
                    // params.put(KEY_USEREXTR ,editTextCapcha50);
                    // params.put(KEY_USERMBBSCOLLEGE ,editTextCapcha70);
                    // params.put(KEY_USERJOINASDOCTOR ,editTextCapcha60);
                    // params.put(KEY_USERADDRESS ,editTextCapcha80);
                    // params.put(KEY_USERJOINASHEALTHEXEC ,editTextCapcha90);
                    // params.put(KEY_USERSKILL ,editTextCapcha100);
					/*
					 * params.put(KEY_USERCAPTCHA ,editTextPgColle );
					 * params.put(KEY_USERMBBSYEAR ,editTextCapcha110);
					 * params.put(KEY_USERPGCOLLEGE ,editTextNam);
					 * params.put(KEY_USERPGYEAR ,editTextskil );
					 * params.put(KEY_USERDSUPCOLLEGE ,editTextQualificati);
					 * params.put(KEY_USERDSUPYEAR ,editTextCapcha120);
					 * params.put(KEY_USERSPECIALITY ,editTextCapcha);
					 * params.put(KEY_USERNAMEQUALIFICATIONS ,editTextCapch);
					 * params.put(KEY_USERNAM ,editTextCapc);
					 * params.put(KEY_USERMOBILE ,editTextCapcha40);
					 * params.put(KEY_USERACTIVITY ,editTextSuperColleg);
					 * params.put(KEY_USERSEX,editTexthosad );
					 */
                    // params.put(KEY_USERID,username);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
