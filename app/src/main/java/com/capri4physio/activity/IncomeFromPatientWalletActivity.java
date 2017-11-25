package com.capri4physio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBaseFragment;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.adapter.PatientTransAdapter;
import com.capri4physio.model.patient.PatientAmount;
import com.capri4physio.model.patient.PatientAmountPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IncomeFromPatientWalletActivity extends AppCompatActivity implements WebServicesCallBack{
    private static final String GET_ALL_TRANSACTIONS = "get_all_transactions";
    String branch_code="";
    String start_date="";
    String end_date="";
    String payment_mode="";
    @BindView(R.id.rv_transactions)
    RecyclerView rv_transactions;
    @BindView(R.id.btn_print)
    Button btn_print;
    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_from_patient_wallet);
        ButterKnife.bind(this);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            branch_code=bundle.getString("branch_code");
            start_date=bundle.getString("start_date");
            end_date=bundle.getString("end_date");
            payment_mode=bundle.getString("payment_mode");

            callPatientTransactionAPI();
        }
        else{
            finish();
        }

        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent=new Intent(IncomeFromPatientWalletActivity.this, IncomeReportPrintActivity.class);
                    intent.putExtra("type","incomefrompatient");
                    intent.putExtra("branch_code",branch_code);
                    intent.putExtra("start_date",start_date);
                    intent.putExtra("end_date",end_date);
                    intent.putExtra("mode",payment_mode);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }



    public void callPatientTransactionAPI(){
        ArrayList<NameValuePair> nameValuePairArrayList=new ArrayList<>();
        nameValuePairArrayList.add(new BasicNameValuePair("mode", payment_mode));
        nameValuePairArrayList.add(new BasicNameValuePair("branch_code", branch_code));
        nameValuePairArrayList.add(new BasicNameValuePair("start_date", start_date));
        nameValuePairArrayList.add(new BasicNameValuePair("end_date", end_date));
        new WebServiceBaseFragment(nameValuePairArrayList,this,GET_ALL_TRANSACTIONS).execute(ApiConfig.ALL_PATIENT_WALLET_AMOUNT);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case GET_ALL_TRANSACTIONS:
                parseAllTransactions(response);
                break;
        }
    }

    public void parseAllTransactions(String response){
        Log.d(TagUtils.getTag(),"patients Transactions:-"+response);
        try{
            Gson gson=new Gson();
            PatientAmount patientAmount=gson.fromJson(response,PatientAmount.class);
            if(patientAmount.getSuccess().equals("true")){
                List<PatientAmountPOJO> patientTransAdapters=patientAmount.getPatientAmountPOJOs();
                Collections.reverse(patientTransAdapters);
                PatientTransAdapter adapter = new PatientTransAdapter(this, null,patientTransAdapters);
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_transactions.setLayoutManager(horizontalLayoutManagaer);
                rv_transactions.setHasFixedSize(true);
                rv_transactions.setItemAnimator(new DefaultItemAnimator());
                rv_transactions.setAdapter(adapter);

                tv_total_amount.setText("Rs "+patientAmount.getTotal());
            }else{
                ToastClass.showShortToast(getApplicationContext(),"No Transaction Done.");
                tv_total_amount.setText("Rs 0");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
            tv_total_amount.setText("Rs 0");
        }
    }
}

