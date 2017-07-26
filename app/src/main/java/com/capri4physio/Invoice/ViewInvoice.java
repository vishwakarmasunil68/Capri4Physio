package com.capri4physio.Invoice;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 9/2/2016.
 */
public class ViewInvoice extends AppCompatActivity {
    public static ArrayList<InfoApps1> contactDetails1;
    InfoApps1 Detailapp;
    public static ArrayList<String> stringArrayList;
    private RecyclerView mRecyclerView;
    String GetURL = ApiConfig.BASE_URL+"users/invoiceview";
    private ProgressDialog pDialog;
    //    LocationAdapter5 locationAdapter5;
    public static Button savechange;
    TextView productivity;
    ArrayList<String> arrayList;
    String item;
    InfoApps1 detailApps;
    Spinner spinnerbranchloca;
    UsersAdapter mAdapter;
    public  static String invo_id,invo_patient_name,invo_bill_amount,invo_due_amount,invo_paid_amount,
            invo_status,invo_bill_number,invo_pro_name,invo_pro_quantity,invo_staff,invo_date,invo_pay_mode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewinvoice);
        contactDetails1=new ArrayList<InfoApps1>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<String>();
        arrayList.add("Select Branch Name");
        spinnerbranchloca = (Spinner) findViewById(R.id.spinnerbranchloca);
        productivity = (TextView) findViewById(R.id.productivity);

        productivity.setVisibility(View.GONE);
//        new CatagoryViewAsynTask().execute();

        new CatagoryUrlAsynTask1().execute();

        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")){
            spinnerbranchloca.setVisibility(View.VISIBLE);
        }
        else {
            item = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
            report8();
            Log.e("item", item);
        }

        //LinearLayout layout = (LinearLayout) findViewById(R.id.info);

        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    try {
                    }
                    catch (Exception e){
                        Log.e("eror",e.toString());
                    }
                }
                else {
                    String address = spinnerbranchloca.getSelectedItem().toString();

                    String ad[]=address.split("\\(");
                    item = ad[1];
                    item =item.replace(")", "");
                    report8();
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private class CatagoryViewAsynTask extends AsyncTask<String, String, String> {
        String id, catagoryName;


        @Override
        protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
            String content = HttpULRConnect.getData(GetURL);
            Log.e("DoInBackGround ---->", String.valueOf(content));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                Log.e("DoInBackGroundtr", String.valueOf(s));
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray jsonArray = new JSONArray(s);
                for (int i =0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    invo_id = jsonObject2.getString("invo_id");
                    String Patient_ID = jsonObject2.getString("invo_Patient_id");
                    invo_patient_name = jsonObject2.getString("invo_patient_name");
                    invo_bill_amount = jsonObject2.getString("invo_bill_amount");
                    invo_due_amount=jsonObject2.getString("invo_due_amount");
                    invo_paid_amount = jsonObject2.getString("invo_paid_amount");
                    invo_status = jsonObject2.getString("invo_status");
                    invo_bill_number = jsonObject2.getString("invo_code");
                    invo_staff = jsonObject2.getString("invo_staff_name");
                    invo_pro_name=jsonObject2.getString("invo_product_name");
                    invo_pro_quantity=jsonObject2.getString("invo_pro_quanty");
                    invo_pay_mode=jsonObject2.getString("invo_payment_mode");
                    invo_date=jsonObject2.getString("invo_date");

                    Detailapp = new InfoApps1();

                    Detailapp.setId(invo_id);
                    Detailapp.setStatus(Patient_ID);
                    Detailapp.setName(invo_patient_name);
                    Detailapp.setStatus(invo_status);
                    Detailapp.setNumber(invo_bill_number);
                    Detailapp.setStaff_name(invo_staff);
                    Detailapp.setBill_amount(invo_bill_amount);
                    Detailapp.setPaid_amount(invo_paid_amount);
                    Detailapp.setDue_amount(invo_due_amount);
                    Detailapp.setInvo_date(invo_date);
                    Detailapp.setPro_name(invo_pro_name);
                    Detailapp.setPass(invo_pay_mode);
                    Detailapp.setPro_quantity(invo_pro_quantity);


                    contactDetails1.add(Detailapp);
                    Collections.reverse(contactDetails1);



                }
                mAdapter = new UsersAdapter(contactDetails1,ViewInvoice.this);
                mRecyclerView.setAdapter(mAdapter);
                Log.e("stringArrayList", stringArrayList.toString());

            }catch(Exception e){
                Log.e("error",e.toString());

            }
        }
    }

    private void report8(){
        initProgressDialog("Please wait..");
        /*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GetURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            try {
                                pDialog.hide();
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            Log.e("2", response.toString());
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i =0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                                Log.e("2", jsonObject2.toString());
                                invo_id = jsonObject2.getString("invo_id");
                                String Patient_ID = jsonObject2.getString("invo_Patient_id");
                                invo_patient_name = jsonObject2.getString("invo_patient_name");
                                invo_bill_amount = jsonObject2.getString("invo_bill_amount");
                                invo_due_amount=jsonObject2.getString("invo_due_amount");
                                invo_paid_amount = jsonObject2.getString("invo_paid_amount");
                                invo_status = jsonObject2.getString("invo_status");
                                invo_bill_number = jsonObject2.getString("invo_code");
                                invo_staff = jsonObject2.getString("invo_staff_name");
                                invo_pro_name=jsonObject2.getString("invo_product_name");
                                invo_pro_quantity=jsonObject2.getString("invo_pro_quanty");
                                invo_pay_mode=jsonObject2.getString("invo_payment_mode");
                                String email=jsonObject2.getString("email");
                                invo_date=jsonObject2.getString("invo_date");


                                Detailapp = new InfoApps1();

                                Detailapp.setId(invo_id);
                                Detailapp.setDesignation(Patient_ID);
                                Detailapp.setName(invo_patient_name);
                                Detailapp.setStatus(invo_status);
                                Detailapp.setNumber(invo_bill_number);
                                Detailapp.setStaff_name(invo_staff);
                                Detailapp.setBill_amount(invo_bill_amount);
                                Detailapp.setPaid_amount(invo_paid_amount);
                                Detailapp.setDue_amount(invo_due_amount);
                                Detailapp.setInvo_date(invo_date);
                                Detailapp.setEmail_id(email);
                                Detailapp.setPro_name(invo_pro_name);
                                Detailapp.setPass(invo_pay_mode);
                                Detailapp.setPro_quantity(invo_pro_quantity);


                                contactDetails1.add(Detailapp);
                                Collections.reverse(contactDetails1);



                            }
                            mAdapter = new UsersAdapter(contactDetails1,ViewInvoice.this);
                            mRecyclerView.setAdapter(mAdapter);

                        }
                        catch(JSONException e){
                            Log.e("error",e.toString());

                        }
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
                params.put("invo_branch_code", item);
//                params.put("admin_permisstion_status", AppPreferences.getInstance(getApplicationContext()).getAdminStatsu());
//                Toast.makeText(UserStatement.this, username +"success", Toast.LENGTH_LONG).show();
//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void initProgressDialog(String loading) {
        try {
            pDialog = new ProgressDialog(getApplicationContext());
            pDialog.setMessage(loading);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private class CatagoryUrlAsynTask1 extends AsyncTask<String, String, String> {
        String id, catagoryName;


        @Override
        protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
            String content = HttpULRConnect.getData(ApiConfig.GetURL);
            Log.e("DoInBackGround ---->", String.valueOf(content));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                Log.e("DoInBackGroundtr", String.valueOf(s));
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray jsonArray = new JSONArray(s);
                for (int i =0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String branch_name = jsonObject2.getString("branch_name");
                    String bracch_code = jsonObject2.getString("branch_code");
                    //branch_code
//                    arrayList.add(bracch_code);

                    detailApps = new InfoApps1();
                    detailApps.setName(branch_name);
                    detailApps.setId(bracch_code);
                    arrayList.add(detailApps.getName() + "  "+ "(" + detailApps.getId()+ ")");

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.dropsimpledown, arrayList);
                    spinnerbranchloca.setAdapter(spinnerArrayAdapter);

                }

            }catch(Exception e){
                Log.e("error",e.toString());

            }
        }
    }
}
