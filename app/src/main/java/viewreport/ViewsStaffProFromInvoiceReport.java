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
import com.capri4physio.fragment.ViewStaff_Pro_InvoReport;
import com.capri4physio.net.ApiConfig;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 9/2/2016.
 */
public class ViewsStaffProFromInvoiceReport extends AppCompatActivity {
    public static ArrayList<InfoApps1> contactDetails1;
    InfoApps1 Detailapp;
    String fromdate, to_date;
    Spinner spinnerbranchloca;
    ArrayList<String> stringArrayList;
    private RecyclerView mRecyclerView;
    String GetURL = ApiConfig.BASE_URL + "users/invoiceview";
    private ProgressDialog pDialog;
    //    LocationAdapter5 locationAdapter5;
    TextView txt_total, productivity;
    public static Button savechange;
    UsersAdapterStaffProfromInvoice mAdapter;
    public static String invo_id, invo_patient_name, invo_bill_amount, invo_due_amount, invo_paid_amount,
            invo_status, invo_bill_number, invo_pro_name, invo_pro_quantity, invo_staff, invo_date, invo_pay_mode;
    LinearLayout liear_total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewinvoice);
        spinnerbranchloca = (Spinner) findViewById(R.id.spinnerbranchloca);
        contactDetails1 = new ArrayList<InfoApps1>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        liear_total = (LinearLayout) findViewById(R.id.liear_total);
        txt_total = (TextView) findViewById(R.id.txt_total);
        productivity = (TextView) findViewById(R.id.productivity);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        fromdate = ViewStaff_Pro_InvoReport.ed1.getText().toString();
        to_date = ViewStaff_Pro_InvoReport.ed2.getText().toString();
        liear_total.setVisibility(View.VISIBLE);
        spinnerbranchloca.setVisibility(View.INVISIBLE);
        report8();
    }

    private void report8() {
        final String date = ViewStaff_Pro_InvoReport.patient_id.toString().trim();
        final String time = ViewStaff_Pro_InvoReport.staff_id.toString().trim();
        final String reason = ViewStaff_Pro_InvoReport.item.toString().trim();

        if (time.equals("") && date.equals("")) {
            productivity.setText("Clinic Productivity");
        } else if (date.equals("")) {
            productivity.setText("Patient Productivity");
        } else if (time.equals("")) {
            productivity.setText("Staff Productivity");
        } else {
            productivity.setText("Clinic Productivity");
        }
        Log.e("date", date);
        Log.e("time", time);
        Log.e("reason", reason);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_STAFF_INVOICESHEET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            /*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*/

//                            JSONObject objresponse = new JSONObject(response);


                            Log.e("response", "" + response);
                            JSONObject jsonArray = new JSONObject(response);

//                            String total=jsonArray.getString("total");
                            String status = jsonArray.getString("status");
                            if (status.equals("false")) {
                                Toast.makeText(ViewsStaffProFromInvoiceReport.this, "No data found", Toast.LENGTH_LONG).show();
                            } else {
                                String tota = jsonArray.getString("total");
                                txt_total.setText(tota);
                                for (int i = 0; i < jsonArray.length() - 2; i++) {

                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i + "");
                                    Log.e("2", jsonObject2.toString());

                                    String invo_code = jsonObject2.getString("invo_code");
                                    String invo_bill_amount = jsonObject2.getString("invo_bill_amount");
                                    invo_date = jsonObject2.getString("invo_date");
                                    String invo_staff_name = jsonObject2.getString("invo_staff_name");
                                    invo_patient_name = jsonObject2.getString("invo_patient_name");
                                    String invo_payment_mode = jsonObject2.getString("invo_payment_mode");
                                    Detailapp = new InfoApps1();

                                    Detailapp.setId(invo_code);
                                    Detailapp.setInvo_date(invo_date);
                                    Detailapp.setStatus(invo_payment_mode);
                                    Detailapp.setStaff_name(invo_staff_name);
                                    Detailapp.setName(invo_patient_name);
                                    Detailapp.setBill_amount(invo_bill_amount);


                                    contactDetails1.add(Detailapp);
                                    mAdapter = new UsersAdapterStaffProfromInvoice(contactDetails1, ViewsStaffProFromInvoiceReport.this);
                                    mRecyclerView.setAdapter(mAdapter);
//                                Log.e("stringArrayList", stringArrayList.toString());
//                                Collections.reverse(contactDetails1);
                                }
                            }


                        } catch (Exception e) {
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
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("invo_start_date", fromdate);
                params.put("invo_end_date", to_date);
                params.put("staff_id", ViewStaff_Pro_InvoReport.patient_id);
                params.put("invo_Patient_id", ViewStaff_Pro_InvoReport.staff_id);
                params.put("invo_branch_code", ViewStaff_Pro_InvoReport.item);
//                Toast.makeText(UserStatement.this, username +"success", Toast.LENGTH_LONG).show();
//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
