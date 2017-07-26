package viewreport;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.Invoice.InfoApps1;
import com.capri4physio.R;
import com.capri4physio.fragment.ViewExpenseReport;
import com.capri4physio.net.ApiConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 9/2/2016.
 */
public class ViewIncomeExpensetReport extends AppCompatActivity {
    public static ArrayList<InfoApps1> contactDetails1;
    InfoApps1 Detailapp;
    LinearLayout liear_total;
    TextView txt_total;
    String fromdate,to_date;
    ArrayList<String> stringArrayList;
    private RecyclerView mRecyclerView;
    String GetURL = "http://www.caprispine.in/users/invoiceview";
    private ProgressDialog pDialog;
    //    LocationAdapter5 locationAdapter5;
    public static Button savechange;
    UsersAdapterExpense mAdapter;
    public  static String invo_id,invo_patient_name,invo_bill_amount,invo_due_amount,invo_paid_amount,
            invo_status,invo_bill_number,invo_pro_name,invo_pro_quantity,invo_staff,invo_date,invo_pay_mode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewexps);
        contactDetails1=new ArrayList<InfoApps1>();
        liear_total=(LinearLayout)findViewById(R.id.liear_total);

        liear_total.setVisibility(View.VISIBLE);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        txt_total = (TextView) findViewById(R.id.txt_total);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        fromdate= ViewExpenseReport.ed1.getText().toString();
        to_date=ViewExpenseReport.ed2.getText().toString();
        Log.e("fromdate",fromdate);
        Log.e("to_date",to_date);
        initProgressDialog("Please wait..");
        report8();
    }

    private void report8(){
        /*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_EXPENSE_REPORT,
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
                            /*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*/

//                            JSONObject objresponse = new JSONObject(response);


                            Log.e("response", "" + response);
                            JSONObject jsonArray = new JSONObject(response);
                            String message = jsonArray.getString("exp_status");
                            if (message.equals("false")) {
                                Toast.makeText(getApplicationContext(), "No expense value", Toast.LENGTH_LONG).show();
                            } else {
                                Log.e("error", jsonArray.length() + "");
                                String total = jsonArray.getString("total");
                                for (int i = 0; i < jsonArray.length() - 2; i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i + "");

                                    String invo_patient_name = jsonObject2.getString("exp_vendor");
                                    String ID =jsonObject2.getString("exp_id");
                                    String invo_bill_amount = jsonObject2.getString("exp_total");
                                    String invo_bill_number = jsonObject2.getString("exp_billno");
                                    String invo_date = jsonObject2.getString("exp_date");

                                    Log.e("total", total.toString());
                                    Detailapp = new InfoApps1();

                                    Detailapp.setName(invo_patient_name);
                                    Detailapp.setId(ID);
                                    Detailapp.setNumber(invo_bill_number);
                                    Detailapp.setBill_amount(invo_bill_amount);
                                    Detailapp.setInvo_date(invo_date);
//                                    Detailapp.setDataAdd(total);

                                    txt_total.setText(total);

//                                Collections.reverse(contactDetails1);
                                    contactDetails1.add(Detailapp);


                                }
                                mAdapter = new UsersAdapterExpense(contactDetails1, ViewIncomeExpensetReport.this);
                                mRecyclerView.setAdapter(mAdapter);
//                            Log.e("stringArrayList", stringArrayList.toString());
                            }
                            }catch(JSONException e){
                                Log.e("error", e.toString());
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
                params.put("exp_start_date", fromdate);
                params.put("exp_end_date", to_date);
                params.put("exp_branch_code", ViewExpenseReport.newaddress);
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


}
