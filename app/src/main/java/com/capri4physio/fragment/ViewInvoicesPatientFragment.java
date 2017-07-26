package com.capri4physio.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.Invoice.InfoApps1;
import com.capri4physio.Invoice.UsersAdapter_1;
import com.capri4physio.R;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.TagUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 7/13/2016.
 */
public class ViewInvoicesPatientFragment extends BaseFragment {
    TextView patient_name, staff_name, bill_number, bill_amount, paid_amount, due_amount, pro_name, pro_quantity, invodate,
            invo_staaus;
    Button button;
    ProgressDialog progressDialog;
    public static ArrayList<InfoApps1> contactDetails1;
    public static TextView date,_starttime,booking_reason;
    public static String patient,patientname,booking_date,booking_starttime,getPatientname,reason,getuniqueid,patientid,doctorid;
    JSONArray jsonArray;
    Button btn;
    InfoApps1 Detailapp;
    private RecyclerView mRecyclerView;
    UsersAdapter_1 mAdapter;


    public static ViewInvoicesPatientFragment newInstance() {
        ViewInvoicesPatientFragment fragment = new ViewInvoicesPatientFragment();
        return fragment;
    }

    public ViewInvoicesPatientFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.viewappo, container, false);

        contactDetails1=new ArrayList<InfoApps1>();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        /*View rootView = inflater.inflate(R.layout.cusotm_dialog_edit, container, false);

        patient_name = (TextView) rootView.findViewById(R.id.Patient_name);
        button= (Button) rootView.findViewById(R.id.button);
        staff_name = (TextView) rootView.findViewById(R.id.Staff_Name);
        bill_number = (TextView) rootView.findViewById(R.id.Bill_number);
        bill_amount = (TextView) rootView.findViewById(R.id.Bill_amount);
        paid_amount = (TextView) rootView.findViewById(R.id.Paid_amount);
        due_amount = (TextView) rootView.findViewById(R.id.Due_amount);
        pro_name = (TextView) rootView.findViewById(R.id.Pro_name);
        pro_quantity = (TextView) rootView.findViewById(R.id.Pro_quantity);
        invodate = (TextView) rootView.findViewById(R.id.Invoice_date);
        invo_staaus = (TextView) rootView.findViewById(R.id.Invoice_status);
        button.setVisibility(View.INVISIBLE );*/

        initProgressDialog("Please wait...");
        report8();

        return rootView;
}

    private void report8(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.INVOICE_P_VIEW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            progressDialog.hide();
                            /*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*/

//                            JSONObject objresponse = new JSONObject(response);

                            Log.e("response", "" + response);
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i =0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                                Log.e("2", jsonObject2.toString());
                                String invo_id = jsonObject2.getString("invo_id");
                                String invo_patient_name = jsonObject2.getString("invo_patient_name");
                                String invo_bill_amount = jsonObject2.getString("invo_bill_amount");
                                String invo_due_amount = jsonObject2.getString("invo_due_amount");
                                String invo_paid_amount = jsonObject2.getString("invo_paid_amount");
                                String invo_status = jsonObject2.getString("invo_status");
                                String invo_bill_number = jsonObject2.getString("invo_billno");
                                String invo_staff = jsonObject2.getString("invo_staff_name");
                                String invo_pro_name = jsonObject2.getString("invo_product_name");
                                String invo_pro_quantity = jsonObject2.getString("invo_pro_quanty");
                                String invo_pay_mode = jsonObject2.getString("invo_payment_mode");
                                String invo_date = jsonObject2.getString("invo_date");

                                Detailapp = new InfoApps1();
                                Detailapp.setId(invo_id);
                                Detailapp.setStatus("");
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

                        mAdapter = new UsersAdapter_1(contactDetails1,getActivity());
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
                params.put("patient_id", AppPreferences.getInstance(getActivity()).getUserID());
                params.put("admin_permisstion_status", AppPreferences.getInstance(getActivity()).getAdminStatsu());
                Log.d(TagUtils.getTag(),"params:-"+params.toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }



    private void initProgressDialog(String loading) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(loading);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
