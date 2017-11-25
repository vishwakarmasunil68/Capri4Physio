package com.capri4physio.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBaseFragment;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.activity.IncomeReportPrintActivity;
import com.capri4physio.adapter.PatientTransAdapter;
import com.capri4physio.model.patient.PatientAmount;
import com.capri4physio.model.patient.PatientAmountPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
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

/**
 * Created by Emobi-Android-002 on 7/13/2016.
 */
public class ViewPatientInvoiceFragment extends BaseFragment implements WebServicesCallBack{

    private static final String GET_ALL_TRANSACTIONS = "get_all_transactions";
    @BindView(R.id.rv_transactions)
    RecyclerView rv_transactions;
    @BindView(R.id.btn_print)
    Button btn_print;

    public static ViewPatientInvoiceFragment newInstance() {
        ViewPatientInvoiceFragment fragment = new ViewPatientInvoiceFragment();
        return fragment;
    }

    public ViewPatientInvoiceFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_view_patient_invoice, container, false);

        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callPatientTransactionAPI();
        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent=new Intent(getActivity(), IncomeReportPrintActivity.class);
                    intent.putExtra("type","patientbill");
                    intent.putExtra("patient_id",AppPreferences.getInstance(getActivity().getApplicationContext()).getUserID());
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void callPatientTransactionAPI(){
        ArrayList<NameValuePair> nameValuePairArrayList=new ArrayList<>();
        nameValuePairArrayList.add(new BasicNameValuePair("p_id", AppPreferences.getInstance(getActivity().getApplicationContext()).getUserID()));
        new WebServiceBaseFragment(nameValuePairArrayList,getActivity(),this,GET_ALL_TRANSACTIONS).execute(ApiConfig.GET_PATIENT_TRANS);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case GET_ALL_TRANSACTIONS:
                parsePatientTransactions(response);
                break;
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
                PatientTransAdapter adapter = new PatientTransAdapter(getActivity(),this, patientTransAdapters);
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_transactions.setLayoutManager(horizontalLayoutManagaer);
                rv_transactions.setHasFixedSize(true);
                rv_transactions.setItemAnimator(new DefaultItemAnimator());
                rv_transactions.setAdapter(adapter);
            }else{
                ToastClass.showShortToast(getActivity().getApplicationContext(),"No Transaction Done.");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getActivity().getApplicationContext(),"Something went wrong");
        }
    }
}