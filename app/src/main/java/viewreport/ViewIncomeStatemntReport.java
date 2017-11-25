package viewreport;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
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
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.net.ApiConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 9/2/2016.
 */
public class ViewIncomeStatemntReport extends AppCompatActivity {
    public static ArrayList<InfoApps1> contactDetails1;
    InfoApps1 Detailapp;
    String fromdate,to_date;
    TextView txt_total;
    ArrayList<String> stringArrayList;
    private RecyclerView mRecyclerView;
    String GetURL = ApiConfig.BASE_URL+"users/invoiceview";
    private ProgressDialog pDialog;
    //    LocationAdapter5 locationAdapter5;
    public static Button savechange;
    UsersAdapterIncome mAdapter;
    public  static String invo_id,invo_patient_name,invo_bill_amount,invo_due_amount,invo_paid_amount,
            invo_status,invo_bill_number,invo_pro_name,invo_pro_quantity,invo_staff,invo_date,invo_pay_mode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewinvoice_1);
        contactDetails1=new ArrayList<InfoApps1>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
//        fromdate= ViewIncomeReport.ed1.getText().toString();
//        to_date=ViewIncomeReport.ed2.getText().toString();
        txt_total = (TextView) findViewById(R.id.txt_total);
        report8();
    }

    private void report8(){
        /*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_INCOME_STATEMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            /*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*/

//                            JSONObject objresponse = new JSONObject(response);


                            Log.e("response", "" + response);
                            JSONObject jsonArray = new JSONObject(response);
                        String TotalAmount = jsonArray.optString("total");
                            txt_total.setText(TotalAmount);
                            for (int i =0; i < jsonArray.length() -1; i++) {
                                JSONObject jsonObject2 = jsonArray.optJSONObject(i+ "");
                                Log.e("2", jsonObject2.toString());
                                String success = jsonObject2.getString("status");
                                if (success.equals("false")) {
                                    Toast.makeText(ViewIncomeStatemntReport.this, "No data found", Toast.LENGTH_LONG).show();
                                } else {
                                    invo_id = jsonObject2.getString("invo_id");
                                    invo_patient_name = jsonObject2.getString("invo_patient_name");
                                    invo_bill_amount = jsonObject2.getString("invo_bill_amount");
                                    invo_due_amount = jsonObject2.getString("invo_due_amount");
                                    invo_paid_amount = jsonObject2.getString("invo_paid_amount");
                                    invo_status = jsonObject2.getString("invo_status");
                                    invo_bill_number = jsonObject2.getString("invo_billno");
                                    invo_staff = jsonObject2.getString("invo_staff_name");
                                    invo_pro_name = jsonObject2.getString("invo_product_name");
                                    invo_pro_quantity = jsonObject2.getString("invo_pro_quanty");
                                    invo_pay_mode = jsonObject2.getString("invo_payment_mode");
                                    invo_date = jsonObject2.getString("invo_date");

                                    Detailapp = new InfoApps1();

                                    Detailapp.setId(invo_id);
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
                                    Collections.reverse(contactDetails1);
                                    contactDetails1.add(Detailapp);


                                }

                            }
                            mAdapter = new UsersAdapterIncome(contactDetails1, ViewIncomeStatemntReport.this);
                            mRecyclerView.setAdapter(mAdapter);
                            Log.e("stringArrayList", stringArrayList.toString());

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
                params.put("invo_start_date", fromdate);
                params.put("invo_end_date", to_date);
//                params.put("branch_code", ViewIncomeReport.item);
//                Toast.makeText(UserStatement.this, username +"success", Toast.LENGTH_LONG).show();
//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
                    invo_patient_name = jsonObject2.getString("invo_patient_name");
                    invo_bill_amount = jsonObject2.getString("invo_bill_amount");
                    invo_due_amount=jsonObject2.getString("invo_due_amount");
                    invo_paid_amount = jsonObject2.getString("invo_paid_amount");
                    invo_status = jsonObject2.getString("invo_status");
                    invo_bill_number = jsonObject2.getString("invo_billno");
                    invo_staff = jsonObject2.getString("invo_staff_name");
                    invo_pro_name=jsonObject2.getString("invo_product_name");
                    invo_pro_quantity=jsonObject2.getString("invo_pro_quanty");
                    invo_pay_mode=jsonObject2.getString("invo_payment_mode");
                    invo_date=jsonObject2.getString("invo_date");

                    Detailapp = new InfoApps1();

                    Detailapp.setId(invo_id);
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

                    Collections.reverse(contactDetails1);
                    contactDetails1.add(Detailapp);




                }
                mAdapter = new UsersAdapterIncome(contactDetails1, ViewIncomeStatemntReport.this);
                mRecyclerView.setAdapter(mAdapter);

            }catch(Exception e){
                Log.e("error",e.toString());

            }
        }
    }


}
