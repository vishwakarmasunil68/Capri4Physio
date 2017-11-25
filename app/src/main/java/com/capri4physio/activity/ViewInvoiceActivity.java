package com.capri4physio.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.capri4physio.R;
import com.capri4physio.Services.GetWebServices;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.adapter.InvoiceAdapter;
import com.capri4physio.model.branch.BranchPOJO;
import com.capri4physio.model.doctor.DoctorPOJO;
import com.capri4physio.model.doctor.DoctorResultPOJO;
import com.capri4physio.model.invoice.InvoicePOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewInvoiceActivity extends AppCompatActivity implements WebServicesCallBack {
    private static final String VIEW_INVOICE_API = "view_invoice_api";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    @BindView(R.id.rl_branch)
    RelativeLayout rl_branch;
    @BindView(R.id.rl_therapist)
    RelativeLayout rl_therapist;
    @BindView(R.id.ll_therapist)
    LinearLayout ll_therapist;
    @BindView(R.id.rv_invoices)
    RecyclerView rv_invoices;

    @BindView(R.id.spinner_therapist)
    Spinner spinner_therapist;
    List<BranchPOJO> branchPOJOList = new ArrayList<>();
    List<DoctorResultPOJO> doctorResultPOJOList = new ArrayList<>();
    private static final String GET_DOCTORS_API = "get_doctors_api";
    private static final String GET_ALL_BRANCHES = "get_all_branches";
    String branch_code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_invoice);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("View Invoice");

        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")) {
            new GetWebServices(this, GET_ALL_BRANCHES).execute(ApiConfig.GetURL);
        } else {
            rl_branch.setVisibility(View.GONE);
            rl_therapist.setVisibility(View.GONE);
            ll_therapist.setVisibility(View.GONE);
            branch_code = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
            getAllInvoices(AppPreferences.getInstance(getApplicationContext()).getUserID());
        }
    }

    public void getTherapists(String branch_code) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("bracch_code", branch_code));
        new WebServiceBase(nameValuePairs, this, GET_DOCTORS_API).execute(ApiConfig.get_branch_doctor);
    }

    public void parseViewInvoiceResponse(String response){
        Log.d(TagUtils.getTag(),"view invoice response:-"+response);
        try{
            Gson gson=new Gson();
            InvoicePOJO invoicePOJO=gson.fromJson(response,InvoicePOJO.class);
            if(invoicePOJO.getSuccess().equals("true")){
                Collections.reverse(invoicePOJO.getInvoiceResultPOJOList());
                InvoiceAdapter adapter = new InvoiceAdapter(this, invoicePOJO.getInvoiceResultPOJOList());
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_invoices.setLayoutManager(horizontalLayoutManagaer);
                rv_invoices.setHasFixedSize(true);
                rv_invoices.setItemAnimator(new DefaultItemAnimator());
                rv_invoices.setAdapter(adapter);
            }else{
                ToastClass.showShortToast(getApplicationContext(),"No Invoices Found");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void parseAllBranches(String response) {
        Log.d(TagUtils.getTag(), "response:-" + response);
        branchPOJOList.clear();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                BranchPOJO branchPOJO = new BranchPOJO(jsonObject.optString("branch_id"),
                        jsonObject.optString("branch_name"),
                        jsonObject.optString("branch_code"),
                        jsonObject.optString("branch_status"));
                branchPOJOList.add(branchPOJO);
            }
            List<String> braStringList = new ArrayList<>();
            for (BranchPOJO branchPOJO : branchPOJOList) {
                braStringList.add(branchPOJO.getBranch_name() + " (" + branchPOJO.getBranch_code() + ")");
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.dropsimpledown, braStringList);
            spinner_branch.setAdapter(spinnerArrayAdapter);

            if (branchPOJOList.size() > 0) {
                getTherapists(branchPOJOList.get(0).getBranch_code());
            }


            spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    getTherapists(branchPOJOList.get(position).getBranch_code());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseGetDoctorsResponse(String response) {
        Log.d(TagUtils.getTag(), "get doctor response:-" + response);
        try {
            Gson gson = new Gson();
            DoctorPOJO doctorPOJO = gson.fromJson(response, DoctorPOJO.class);
            doctorResultPOJOList.clear();
            if (doctorPOJO.getSuccess().equals("true")) {
                List<String> doctorStringList = new ArrayList<>();
                doctorResultPOJOList.addAll(doctorPOJO.getDoctorResultPOJOList());
                for (DoctorResultPOJO doctorResultPOJO : doctorResultPOJOList) {
                    doctorStringList.add(doctorResultPOJO.getFirstName() + " " + doctorResultPOJO.getLastName());
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(), R.layout.dropsimpledown, doctorStringList);
                spinner_therapist.setAdapter(spinnerArrayAdapter);

                getAllInvoices(doctorResultPOJOList.get(0).getId());

                spinner_therapist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        getAllInvoices(doctorResultPOJOList.get(0).getId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            } else {
                ToastClass.showShortToast(getApplicationContext(), "No Doctor Found. Please Select another branch");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllInvoices(String therapist_id) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("invo_therapist_id",therapist_id));
        new WebServiceBase(nameValuePairs,this,VIEW_INVOICE_API).execute(ApiConfig.VIEW_INVOICE_BY_THERAPIST_ID);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GET_ALL_BRANCHES:
                parseAllBranches(response);
                break;
            case GET_DOCTORS_API:
                parseGetDoctorsResponse(response);
                break;
            case VIEW_INVOICE_API:
                parseViewInvoiceResponse(response);
                break;
        }
    }

}
