package com.capri4physio.patientappo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 10/14/2016.
 */
public class ViewInvoice_Patient extends AppCompatActivity {

    TextView patient_name,staff_name,bill_number,bill_amount,paid_amount,due_amount,pro_name,pro_quantity,invodate,
            invo_staaus;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cusotm_dialog_edit);

        patient_name = (TextView) findViewById(R.id.Patient_name);
        staff_name = (TextView) findViewById(R.id.Staff_Name);
        bill_number = (TextView) findViewById(R.id.Bill_number);
        bill_amount = (TextView) findViewById(R.id.Bill_amount);
        paid_amount = (TextView) findViewById(R.id.Paid_amount);
        due_amount = (TextView) findViewById(R.id.Due_amount);
        pro_name = (TextView) findViewById(R.id.Pro_name);
        pro_quantity = (TextView) findViewById(R.id.Pro_quantity);
        invodate = (TextView) findViewById(R.id.Invoice_date);
        invo_staaus = (TextView) findViewById(R.id.Invoice_status);

        initProgressDialog("Please wait...");
        report8();


    }

    private void report8(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.INVOICE_P_VIEW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            /*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*/

//                            JSONObject objresponse = new JSONObject(response);


                            Log.e("response", "" + response);
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject2=jsonArray.getJSONObject(0);
                            Log.e("2", jsonObject2.toString());
                            String  invo_id = jsonObject2.getString("invo_id");
                            String invo_patient_name = jsonObject2.getString("invo_patient_name");
                            String invo_bill_amount = jsonObject2.getString("invo_bill_amount");
                            String  invo_due_amount=jsonObject2.getString("invo_due_amount");
                            String  invo_paid_amount = jsonObject2.getString("invo_paid_amount");
                            String invo_status = jsonObject2.getString("invo_status");
                            String invo_bill_number = jsonObject2.getString("invo_billno");
                            String invo_staff = jsonObject2.getString("invo_staff_name");
                            String  invo_pro_name=jsonObject2.getString("invo_product_name");
                            String   invo_pro_quantity=jsonObject2.getString("invo_pro_quanty");
                            String invo_pay_mode=jsonObject2.getString("invo_payment_mode");
                            String invo_date=jsonObject2.getString("invo_date");

                            patient_name.setText(invo_patient_name);
                            staff_name.setText(invo_staff);
                            bill_number.setText(invo_bill_number);
                            paid_amount.setText(invo_paid_amount);
                            bill_amount.setText(invo_bill_amount);
                            due_amount.setText(invo_due_amount);

                        }catch(Exception e){
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
                params.put("patient_id", AppPreferences.getInstance(getApplicationContext()).getUserID());

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.print:
                return true;

            case android.R.id.home: {
                Log.e("clik", "action bar clicked");

//            saveImage();
                finish();
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initProgressDialog(String loading) {
        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage(loading);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
