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
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.Utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import viewreport.Services.WebServiceBase;
import viewreport.Services.WebServiceCallBack;

/**
 * Created by emobi5 on 7/2/2016.
 */
public class MotorActivity1 extends Activity implements WebServiceCallBack{
    EditText ed1,ed2,ed3;
    Button save;
    ProgressDialog pDialog;
    Bitmap bitmap = null;
    String patient_id;
    ScrollView scroll_combined_spine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combinedspine);
        ed1=(EditText)findViewById(R.id.edtxt_blood_presure);
        ed2=(EditText)findViewById(R.id.edtxt_temp);
        ed3=(EditText)findViewById(R.id.edtxt_hrate);
        save=(Button)findViewById(R.id.savebtn);
        scroll_combined_spine=(ScrollView) findViewById(R.id.scroll_combined_spine);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenShots(scroll_combined_spine);
                String main_base64= ImageUtil.encodeTobase64(bitmap);
                initProgressDialog("Please wait..");
                addMotorApi1(main_base64);
            }
        });
        patient_id =getIntent().getStringExtra("patient_id");
        setTitle("");
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
    public String ValidateEdit(EditText edit){
        if(edit.getText().toString().equals(null)||edit.getText().toString().equals("")){
            return "";
        }
        else{
            return edit.getText().toString().trim();
        }
    }

    private void addMotorApi1(final String main_base64){
        final String name =ValidateEdit(ed1);
        final String lastName =ValidateEdit(ed2);
        final String dob =ValidateEdit(ed3);
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("moter_exam_date", Utils.getCurrentDate()));
        nameValuePairs.add(new BasicNameValuePair("patient_id",patient_id));
        nameValuePairs.add(new BasicNameValuePair("moter_exam_data",name));
        nameValuePairs.add(new BasicNameValuePair("moter_exam_data1",lastName));
        nameValuePairs.add(new BasicNameValuePair("moter_exam_data2",dob));
        nameValuePairs.add(new BasicNameValuePair("moterexams_images",main_base64));

        new WebServiceBase(nameValuePairs,this,"cervical_api").execute(ApiConfig.SIGNIN_URL);
    }
    private void addMotorAPi(final String main_base64){
        final String name =ValidateEdit(ed1);
        final String lastName =ValidateEdit(ed2);
        final String dob =ValidateEdit(ed3);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.SIGNIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            try{
                                pDialog.dismiss();
                            }
                            catch (Exception e){
                                e.toString();
                            }
                            Log.e("result",response);
                            Toast.makeText(MotorActivity1.this,"successfully added", Toast.LENGTH_LONG).show();
                            finish();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.e("Postdat", "" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w("Postdat", "" + error);
                    }
                }){


            protected Map<String,String> getParams(){
                Map<String,String> objresponse = new HashMap<String, String>();
                objresponse.put("moter_exam_date", Utils.getCurrentDate());
                objresponse.put("patient_id", patient_id);
                objresponse.put("moter_exam_data", name);
                objresponse.put("moter_exam_data1", lastName);
                objresponse.put("moter_exam_data2", dob);
                objresponse.put("moterexams_images", main_base64);
                Log.d(TagUtils.getTag(),"params:-"+objresponse.toString());
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(MotorActivity1.this);
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    public void onGetMsg(String[] msg) {
        String response=msg[1];
        try {
            try{
                pDialog.dismiss();
            }
            catch (Exception e){
                e.toString();
            }
            Log.e("result",response);
            Toast.makeText(MotorActivity1.this,"successfully added", Toast.LENGTH_LONG).show();
            finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("Postdat", "" + response.toString());
    }
}
