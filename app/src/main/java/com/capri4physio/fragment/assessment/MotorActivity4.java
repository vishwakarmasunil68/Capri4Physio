package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.ImageUtil;
import com.capri4physio.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by emobi5 on 7/2/2016.
 */
public class MotorActivity4 extends Activity{EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7;
    Button savebtn;
    ProgressDialog pDialog;
    Bitmap bitmap = null;
    ScrollView scroll_lumberspine;
    public static String lumbar_Flexion,patient_id,lumbar_Extension,lumbar_sideflexleft,lumbar_sideflexRight,lumbar_Rotation_Left,lumbar_RotationRight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lumberspine);
        ed1=(EditText)findViewById(R.id.edtxt_blood_presure);
        ed2=(EditText)findViewById(R.id.edtxt_temp);
        ed3=(EditText)findViewById(R.id.Left);
        ed4=(EditText)findViewById(R.id.Right);
        ed5=(EditText)findViewById(R.id.RotationLeft);
        ed6=(EditText)findViewById(R.id.RotationRight);
        scroll_lumberspine=(ScrollView) findViewById(R.id.scroll_lumberspine);
        savebtn=(Button)findViewById(R.id.savebtn);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"button",Toast.LENGTH_LONG);
                takeScreenShots(scroll_lumberspine);
                String main_base64= ImageUtil.encodeTobase64(bitmap);
                initProgressDialog("Please wait..");
                addMotorAPi(main_base64);

            }
        });
        patient_id =getIntent().getStringExtra("patient_id");
    }
    public String ValidateEdit(EditText edit){
        if(edit.getText().toString().equals(null)||edit.getText().toString().equals("")){
            return "";
        }
        else{
            return edit.getText().toString().trim();
        }
    }
    public Bitmap takeScreenShots(ScrollView scrollView) {
        int h = 0;

        //get the actual height of scrollview
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundResource(R.color.white);
        }
        // create bitmap with target size
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }
    private void addMotorAPi(final String main_base64){
        lumbar_Flexion =ValidateEdit(ed1);
        lumbar_Extension =ValidateEdit(ed2);
        lumbar_sideflexleft =ValidateEdit(ed3);
        lumbar_sideflexRight =ValidateEdit(ed4);
        lumbar_Rotation_Left=ValidateEdit(ed5);
        lumbar_RotationRight=ValidateEdit(ed6);
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.MOTOR__LUMBAR_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            pDialog.dismiss();
                            Toast.makeText(MotorActivity4.this,"successfully added", Toast.LENGTH_LONG).show();
                            finish();
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
                        Log.e("Postdat", "" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                    }
                }){


            protected Map<String,String> getParams(){
                Map<String,String> objresponse = new HashMap<String, String>();
                objresponse.put("moter_exam_date", Utils.getCurrentDate());
                objresponse.put("patient_id", patient_id);
                objresponse.put("lumbarsp_flex", lumbar_Flexion);
                objresponse.put("lumbarsp_exten", lumbar_Extension);
                objresponse.put("lumbarsp_side_flex_left", lumbar_sideflexleft);
                objresponse.put("lumbarsp_side_flex_right",lumbar_sideflexRight);
                objresponse.put("lumbarsp_rotation_left ",lumbar_Rotation_Left);
                objresponse.put("lumbarsp_rotation_right",lumbar_RotationRight);
                objresponse.put("moterexamlumbar_images",main_base64);
                /*params.put("sfirst_name",name);
                params.put("slast_name",lastName);
                params.put("sdob",dob);
                params.put("sage", "23");
                params.put("sdatejoing",doj);
                params.put("senddate", endingdateofcontract);
                params.put("sgender", rate);
                params.put("smarital_status", rate1);
                params.put("sdesignation",designation);
                params.put("saddress", address);
                params.put("scity", city);
                params.put("spincode", pin_code);
                params.put("smobile", phone);
                params.put("semail", email_id);
                params.put("squalifation", degree);
                params.put("sexprience", experienceduration);*/

//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(MotorActivity4.this);
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }
}
