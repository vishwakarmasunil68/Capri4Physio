package viewreport;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.Invoice.InfoApps1;
import com.capri4physio.R;
import com.capri4physio.fragment.ViewBalanceReport;
import com.capri4physio.net.ApiConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 9/2/2016.
 */
public class ViewBalanceSheetReport extends AppCompatActivity {
    public static ArrayList<InfoApps1> contactDetails1;
    InfoApps1 Detailapp;
    String fromdate,to_date;
    private ProgressDialog pDialog;
    ArrayList<String> stringArrayList;
    private RecyclerView mRecyclerView;
    String GetURL = ApiConfig.BASE_URL+"users/invoiceview";
    //    LocationAdapter5 locationAdapter5;
    public static Button savechange;
    UsersAdapterBalanceSheet mAdapter;
    String branch_code,first_date,last_date;
    public  static String invo_id,invo_patient_name,invo_bill_amount,invo_due_amount,invo_paid_amount,
            invo_status,invo_bill_number,invo_pro_name,invo_pro_quantity,invo_staff,invo_date,invo_pay_mode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewexpense);
        contactDetails1=new ArrayList<InfoApps1>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
              fromdate= ViewBalanceReport.ed1.getText().toString();
        to_date=ViewBalanceReport.ed2.getText().toString();

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            branch_code=bundle.getString("branch_code");
            first_date=bundle.getString("first_date");
            last_date=bundle.getString("last_date");
        }

        report8();
    }

    private void report8(){
        initProgressDialog("Please wait..");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_BALANCE_SHEET,
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
                            JSONObject jsonObject2 = new JSONObject(response);
                            String success = jsonObject2.getString("status");
                            if (success.equals("false")) {
                                Toast.makeText(ViewBalanceSheetReport.this, "No data found", Toast.LENGTH_LONG).show();
                            } else {
                                invo_id = jsonObject2.getString("total-income-Amount");
                                invo_patient_name = jsonObject2.getString("total-income-AmountPay");
                                invo_paid_amount = jsonObject2.getString("Expence-Amount");
                                invo_bill_amount = jsonObject2.getString("Balance-Amount");
                                invo_due_amount = jsonObject2.getString("Balance-due-Amount");

                                Detailapp = new InfoApps1();

                                Detailapp.setId(invo_id);
                                Detailapp.setName(invo_patient_name);
                                Detailapp.setBill_amount(invo_bill_amount);
                                Detailapp.setPaid_amount(invo_paid_amount);
                                Detailapp.setDue_amount(invo_due_amount);

                                Collections.reverse(contactDetails1);
                                contactDetails1.add(Detailapp);


                                mAdapter = new UsersAdapterBalanceSheet(contactDetails1, ViewBalanceSheetReport.this);
                                mRecyclerView.setAdapter(mAdapter);
//                            Log.e("stringArrayList", stringArrayList.toString());

                            }
                        }
                    catch(JSONException e){
                            Log.e("error",e.toString());

                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w("Postdat", "" + error);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("invo_start_date", first_date);
                params.put("invo_end_date", last_date);
                params.put("invo_branch_code", branch_code);
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

}
