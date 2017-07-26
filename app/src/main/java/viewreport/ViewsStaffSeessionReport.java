package viewreport;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.capri4physio.fragment.ViewIncomeSessionwiseReport;
import com.capri4physio.fragment.assessment.Util;
import com.capri4physio.net.ApiConfig;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 9/2/2016.
 */
public class ViewsStaffSeessionReport extends AppCompatActivity {
    public static ArrayList<InfoApps1> contactDetails1;
    InfoApps1 Detailapp;
    String fromdate,staff_nmame,to_date;
    ArrayList<String> stringArrayList;
    private RecyclerView mRecyclerView;
    String GetURL = "http://www.caprispine.in/users/invoiceview";
    private ProgressDialog pDialog;
    //    LocationAdapter5 locationAdapter5;
    public static Button savechange;
    UsersAdapterSeession mAdapter;
    public  static String invo_id,invo_patient_name,invo_bill_amount,invo_due_amount,invo_paid_amount,
            invo_status,invo_bill_number,invo_pro_name,invo_pro_quantity,invo_staff,invo_date,invo_pay_mode;
    LinearLayout liear_total;
    Spinner spinnerbranchloca;
    TextView txt_total;
    TextView txt_title;
    TextView title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewinvoice);
        contactDetails1=new ArrayList<InfoApps1>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        liear_total = (LinearLayout) findViewById(R.id.liear_total);
        spinnerbranchloca = (Spinner) findViewById(R.id.spinnerbranchloca);
        txt_total = (TextView) findViewById(R.id.txt_total);
        txt_title = (TextView) findViewById(R.id.txt_title);
        title = (TextView) findViewById(R.id.productivity);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        fromdate= ViewIncomeSessionwiseReport.ed1.getText().toString();
        to_date=ViewIncomeSessionwiseReport.ed2.getText().toString();
        Log.e("fromdate",fromdate  + " " + " "+to_date);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            String staff_name_string=bundle.getString("staffid");
//            staff_nmame=getIntent().getStringExtra("staffid");
            staff_nmame=staff_name_string;
        }

        Log.e("staffid",staff_nmame);
        liear_total.setVisibility(View.VISIBLE);
        title.setVisibility(View.GONE);
        spinnerbranchloca.setVisibility(View.GONE);
        new CallServicesFp().execute(ApiConfig.VIEW_STAFF_SESSION_WISE);
//        report8();
    }
    public class CallServicesFp extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(ViewsStaffSeessionReport.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("staf_start_date", fromdate));
            namevaluepair.add(new BasicNameValuePair("staf_end_date", to_date));
            namevaluepair.add(new BasicNameValuePair("staff_name", staff_nmame));
            namevaluepair.add(new BasicNameValuePair("treament", ViewIncomeSessionwiseReport.seleted_treatment));

           /* params.put("staf_start_date", fromdate);
            params.put("staf_end_date", to_date);
            params.put("staff_name", ViewIncomeSessionwiseReport.staff);
            params.put("treament", ViewIncomeSessionwiseReport.seleted_treatment);*/
            //namevaluepair.add(new BasicNameValuePair("cat", "HAIR"));

            try {

                result = Util.executeHttpPost(params[0], namevaluepair);

            } catch (Exception e) {

                e.printStackTrace();

            }

            return result;


        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            if (pd != null) {
                pd.dismiss();
            }
            Log.e("response", "" + result);
            try{
            JSONObject jsonArray = new JSONObject(result);

                try {
                    JSONObject jsonObject = jsonArray.optJSONObject("status");
                    String status = jsonObject.getString("trea_status");
                    if (status.equals("0")) {
                        Toast.makeText(ViewsStaffSeessionReport.this, "No data found", Toast.LENGTH_LONG).show();
                        txt_total.setVisibility(View.INVISIBLE);
                        txt_title.setVisibility(View.INVISIBLE);
                    }
                }
                catch(Exception e){
                String tota = jsonArray.getString("total");
                txt_total.setText(tota);


                for (int i = 0; i < jsonArray.length() - 1; i++) {
                    JSONObject jsonObject2 = jsonArray.optJSONObject(i + "");

                    String sfirst_name = jsonObject2.getString("first_name");
                    String last_name = jsonObject2.getString("last_name");
                    String first_name = jsonObject2.getString("first_name");
                    String therapy = jsonObject2.getString("therapy");
                    String time_in = jsonObject2.getString("time_in");
                    String time_out = jsonObject2.getString("time_out");
                    String date = jsonObject2.getString("date");
                    String trea_amount = jsonObject2.getString("trea_amount");


                    Detailapp = new InfoApps1();

                    Detailapp.setName(first_name+" "+last_name);
                    Detailapp.setStaff_name(sfirst_name);
                    Detailapp.setPro_name(therapy);
                    Detailapp.setStatus(time_in);
                    Detailapp.setId(time_out);
                    Detailapp.setInvo_date(date);
                    Detailapp.setBill_amount(trea_amount);
                    Detailapp.setNumber(trea_amount);


                    contactDetails1.add(Detailapp);
                    Collections.reverse(contactDetails1);
                }
            }
            mAdapter = new UsersAdapterSeession(contactDetails1, ViewsStaffSeessionReport.this);
            mRecyclerView.setAdapter(mAdapter);

        }catch(Exception e){
            Log.e("error",e.toString());

        }
    }
        }
    private void report8(){
        /*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_STAFF_SESSION_WISE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            /*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*/

//                            JSONObject objresponse = new JSONObject(response);


                            Log.e("response", "" + response);
                            JSONObject jsonArray = new JSONObject(response);
                            JSONObject jsonObject = jsonArray.optJSONObject(0+"");
                            String status = jsonObject.getString("trea_status");
                            if (status.equals("0")) {
                                Toast.makeText(ViewsStaffSeessionReport.this,"No data found",Toast.LENGTH_LONG).show();
                            }
                            else {
                   String tota = jsonArray.getString("total");
                            txt_total.setText(tota);


                                for (int i = 0; i < jsonArray.length() - 1; i++) {
                                    JSONObject jsonObject2 = jsonArray.optJSONObject(i + "");

                                    String sfirst_name = jsonObject2.getString("first_name");
                                    String first_name = jsonObject2.getString("first_name");
                                    String therapy = jsonObject2.getString("therapy");
                                    String time_in = jsonObject2.getString("time_in");
                                    String time_out = jsonObject2.getString("time_out");
                                    String date = jsonObject2.getString("date");
                                    String trea_amount = jsonObject2.getString("trea_amount");


                                    Detailapp = new InfoApps1();

                                    Detailapp.setName(first_name);
                                    Detailapp.setStaff_name(sfirst_name);
                                    Detailapp.setPro_name(therapy);
                                    Detailapp.setStatus(time_in);
                                    Detailapp.setId(time_out);
                                    Detailapp.setInvo_date(date);
                                    Detailapp.setBill_amount(trea_amount);
                                    Detailapp.setNumber(trea_amount);


                                    contactDetails1.add(Detailapp);
                                    Collections.reverse(contactDetails1);
                                }
                            }
                            mAdapter = new UsersAdapterSeession(contactDetails1, ViewsStaffSeessionReport.this);
                            mRecyclerView.setAdapter(mAdapter);

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
                params.put("staf_start_date", fromdate);
                params.put("staf_end_date", to_date);
                params.put("staff_name", ViewIncomeSessionwiseReport.staff);
                params.put("treament", ViewIncomeSessionwiseReport.seleted_treatment);
                /*params.put("patient_id", to_date);
                params.put("therapist", staff_nmame);*/
//                Toast.makeText(UserStatement.this, username +"success", Toast.LENGTH_LONG).show();
//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



}
