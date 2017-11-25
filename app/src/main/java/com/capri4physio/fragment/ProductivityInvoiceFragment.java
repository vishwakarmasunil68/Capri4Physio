package com.capri4physio.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.activity.IncomeReportPrintActivity;
import com.capri4physio.adapter.InvoiceAdapter;
import com.capri4physio.model.invoice.InvoicePOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 30-08-2017.
 */

public class ProductivityInvoiceFragment extends Fragment implements WebServicesCallBack{
    private static final String GET_ALL_INVOICES = "get_all_invoices";
    String branch_code;String therapist_id;String from_date;String to_date;
    ProductivityInvoiceFragment(String branch_code,String therapist_id,String from_date,String to_date){
        this.branch_code=branch_code;
        this.therapist_id=therapist_id;
        this.from_date=from_date;
        this.to_date=to_date;
    }

    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;
    @BindView(R.id.rv_report)
    RecyclerView rv_report;
    @BindView(R.id.btn_print)
    Button btn_print;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_productivity_invoice,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        try {
            nameValuePairs.add(new BasicNameValuePair("bracch_code", branch_code));
            nameValuePairs.add(new BasicNameValuePair("invo_therapist_id", therapist_id));
            String[] start_date = from_date.split("-");
            String[] end_date = to_date.split("-");
            nameValuePairs.add(new BasicNameValuePair("start_date", start_date[2] + "-" + start_date[1] + "-" + start_date[0]));
            nameValuePairs.add(new BasicNameValuePair("end_date", end_date[2] + "-" + end_date[1] + "-" + end_date[0]));
            new WebServiceBase(nameValuePairs, getActivity(), this, GET_ALL_INVOICES).execute(ApiConfig.GET_INVOICE_BY_DATE);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getActivity(), IncomeReportPrintActivity.class);
                    intent.putExtra("type", "productivity");
                    intent.putExtra("branch_code", branch_code);
                    intent.putExtra("therapist_id", therapist_id);
                    String[] start_date = from_date.split("-");
                    String[] end_date = to_date.split("-");
                    intent.putExtra("start_date", start_date[2] + "-" + start_date[1] + "-" + start_date[0]);
                    intent.putExtra("end_date", end_date[2] + "-" + end_date[1] + "-" + end_date[0]);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case GET_ALL_INVOICES:
                parseAllInvoices(response);
                break;
        }
    }

    public void parseAllInvoices(String response){
        Log.d(TagUtils.getTag(),"invoices response:-"+response);
        try{
            Gson gson=new Gson();
            InvoicePOJO invoicePOJO=gson.fromJson(response,InvoicePOJO.class);
            if(invoicePOJO.getSuccess().equals("true")){
                InvoiceAdapter invoiceAdapter=new InvoiceAdapter(getActivity(),invoicePOJO.getInvoiceResultPOJOList());
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_report.setLayoutManager(horizontalLayoutManagaer);
                rv_report.setHasFixedSize(true);
                rv_report.setItemAnimator(new DefaultItemAnimator());
                rv_report.setAdapter(invoiceAdapter);
                tv_total_amount.setText("INR "+invoicePOJO.getTotal());
            }else{
                ToastClass.showShortToast(getActivity().getApplicationContext(),"No Invoice Found");
                tv_total_amount.setText("INR 0");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getActivity().getApplicationContext(),"Something went wrong");
        }
    }
}
