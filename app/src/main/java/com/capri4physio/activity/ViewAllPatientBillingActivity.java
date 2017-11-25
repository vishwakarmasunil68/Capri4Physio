package com.capri4physio.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServiceBaseFragment;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.adapter.PatientTransAdapter;
import com.capri4physio.model.patient.PatientAmount;
import com.capri4physio.model.patient.PatientAmountPOJO;
import com.capri4physio.model.patient.PatientResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewAllPatientBillingActivity extends AppCompatActivity implements WebServicesCallBack{
    PatientResultPOJO patientResultPOJO;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_transactions)
    RecyclerView rv_transactions;
    @BindView(R.id.btn_print)
    Button btn_print;
    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;


    private static final String GET_PATIENT_LAST_AMOUNT = "get_patient_last_amount";
    private static final String GET_ALL_TRANSACTIONS = "get_all_transactions";
    private static final String ADD_AMOUNT_API = "add_amount_api";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_patient_billing);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Pateint Bills");

        patientResultPOJO= (PatientResultPOJO) getIntent().getSerializableExtra("patientpojo");

        if(patientResultPOJO!=null){
            callPatientTransactionAPI();
            btn_print.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        Intent intent=new Intent(ViewAllPatientBillingActivity.this, IncomeReportPrintActivity.class);
                        intent.putExtra("type","patientbill");
                        intent.putExtra("branch_code",patientResultPOJO.getBracchCode());
                        intent.putExtra("patient_id", patientResultPOJO.getId());
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }else{
            finish();
        }
    }

    public void callPatientTransactionAPI(){
        ArrayList<NameValuePair> nameValuePairArrayList=new ArrayList<>();
        nameValuePairArrayList.add(new BasicNameValuePair("p_id", patientResultPOJO.getId()));
        new WebServiceBaseFragment(nameValuePairArrayList,this,GET_ALL_TRANSACTIONS).execute(ApiConfig.GET_PATIENT_TRANS);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case GET_ALL_TRANSACTIONS:
                parsePatientTransactions(response);
                break;
            case GET_PATIENT_LAST_AMOUNT:
                parsePatientLastAmount(response);
                break;
            case ADD_AMOUNT_API:
                parseAddAmountResponse(response);
                break;
        }
    }

    public void parseAddAmountResponse(String response) {
        try {
            Log.d(TagUtils.getTag(), "add amount response:-" + response);
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("success").equals("true")){
                ToastClass.showShortToast(getApplicationContext(),"Amount added successfully");
                callPatientTransactionAPI();
            }else{
                ToastClass.showShortToast(getApplicationContext(),"Failed to add Amount");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Server Error");
        }
    }
    public void parsePatientLastAmount(String response) {
        Log.d(TagUtils.getTag(), "patient amount:-" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {
                PatientAmountPOJO patientAmountPOJO=new Gson().fromJson(jsonObject.optJSONArray("result").optJSONObject(0).toString(),PatientAmountPOJO.class);
                showDialog(patientResultPOJO,patientAmountPOJO.getPt_total_amount());
            } else {
                showDialog(patientResultPOJO, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(), "Something went wrong");
        }
    }

    public void parsePatientTransactions(String response){
        Log.d(TagUtils.getTag(),"patients Transactions:-"+response);
        try{
            Gson gson=new Gson();
            PatientAmount patientAmount=gson.fromJson(response,PatientAmount.class);
            if(patientAmount.getSuccess().equals("true")){
                List<PatientAmountPOJO> patientTransAdapters=patientAmount.getPatientAmountPOJOs();
                Collections.reverse(patientTransAdapters);
                PatientTransAdapter adapter = new PatientTransAdapter(this,null, patientTransAdapters);
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_transactions.setLayoutManager(horizontalLayoutManagaer);
                rv_transactions.setHasFixedSize(true);
                rv_transactions.setItemAnimator(new DefaultItemAnimator());
                rv_transactions.setAdapter(adapter);

                if(patientTransAdapters.size()>0){
                    tv_total_amount.setText("Rs "+patientTransAdapters.get(patientTransAdapters.size()-1).getPt_total_amount());
                }else{
                    tv_total_amount.setText("Rs 0");
                }
            }else{
                ToastClass.showShortToast(getApplicationContext(),"No Transaction Done.");
                tv_total_amount.setText("Rs 0");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Server Down");
            tv_total_amount.setText("Rs 0");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patient_billing_add, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_amount:
                showPatientWallerDialog(patientResultPOJO);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void showPatientWallerDialog(PatientResultPOJO patientResultPOJO) {
        this.patientResultPOJO = patientResultPOJO;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("p_id", patientResultPOJO.getId()));
        new WebServiceBase(nameValuePairs, this, GET_PATIENT_LAST_AMOUNT).execute(ApiConfig.GET_LAST_PATIENT_TRANSACTION_AMOUNT);
    }


    public void showDialog(final PatientResultPOJO patientResultPOJO, final String amount) {
        final Dialog dialog1 = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_patient_amount_add);
        dialog1.setTitle("Wallet Amount");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btn_add = (Button) dialog1.findViewById(R.id.btn_add);
        final EditText et_amount = (EditText) dialog1.findViewById(R.id.et_amount);
        final EditText et_tans_id = (EditText) dialog1.findViewById(R.id.et_tans_id);
        final TextView tv_patient_wallet = (TextView) dialog1.findViewById(R.id.tv_patient_wallet);
        final RadioGroup rg_mode = (RadioGroup) dialog1.findViewById(R.id.rg_mode);

        if (amount.length() > 0) {
            tv_patient_wallet.setText("Balance Amount :- " + amount);
        } else {
            tv_patient_wallet.setText("No Balance in patient Account");
        }

        final String[] mode = {"cash"};

        rg_mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId==R.id.rb_cash){
                    mode[0] ="cash";
                    et_tans_id.setVisibility(View.GONE);
                }else if(checkedId==R.id.rb_cheque){
                    mode[0]="cheque";
                    et_tans_id.setVisibility(View.VISIBLE);
                }else if(checkedId==R.id.rb_card){
                    mode[0]="card";
                    et_tans_id.setVisibility(View.VISIBLE);
                }
            }
        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_amount.getText().toString().length() > 0) {
                    dialog1.dismiss();
                    callAddAmountAPI(patientResultPOJO, et_amount.getText().toString(), amount,mode[0]);
                } else {
                    ToastClass.showShortToast(getApplicationContext(), "Enter Amount");
                }
            }
        });
    }

    public void callAddAmountAPI(PatientResultPOJO patientResultPOJO, String amount, String previous_amount,String mode) {
        int preamount = 0;
        int curr_amount = 0;
        try {
            preamount = Integer.parseInt(previous_amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            curr_amount = Integer.parseInt(amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<NameValuePair> nameValuePairArrayList = new ArrayList<>();
        nameValuePairArrayList.add(new BasicNameValuePair("pt_amount", amount));
        nameValuePairArrayList.add(new BasicNameValuePair("pt_submittedby", AppPreferences.getInstance(getApplicationContext()).getUserID()));
        nameValuePairArrayList.add(new BasicNameValuePair("pt_deduetedby", ""));
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String date = sdf.format(d).split(" ")[0];
        String time = sdf.format(d).split(" ")[1];
        nameValuePairArrayList.add(new BasicNameValuePair("pt_date", date));
        nameValuePairArrayList.add(new BasicNameValuePair("pt_time", time));
        nameValuePairArrayList.add(new BasicNameValuePair("pt_reason", ""));
        nameValuePairArrayList.add(new BasicNameValuePair("pt_total_amount", String.valueOf(preamount + curr_amount)));
        nameValuePairArrayList.add(new BasicNameValuePair("pt_p_id", patientResultPOJO.getId()));
        nameValuePairArrayList.add(new BasicNameValuePair("branch_code", patientResultPOJO.getAddedBy()));
        nameValuePairArrayList.add(new BasicNameValuePair("mode", mode));
        new WebServiceBase(nameValuePairArrayList, this, ADD_AMOUNT_API).execute(ApiConfig.ADD_PATIENT_AMOUNT_API);
    }
}
