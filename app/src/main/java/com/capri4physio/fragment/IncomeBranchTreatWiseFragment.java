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
import com.capri4physio.adapter.adapter.IncomeAdapter;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.pojo.incomepojo.IncomePOJO;
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

public class IncomeBranchTreatWiseFragment extends Fragment implements WebServicesCallBack {
    private static final String GET_ALL_INCOMES = "get_all_invoices";
    String branch_code;
    String treatment_id;

    @BindView(R.id.btn_print)
    Button btn_print;

    public IncomeBranchTreatWiseFragment(String branch_code, String treatmen_id) {
        this.branch_code = branch_code;
        this.treatment_id= treatmen_id;
    }

    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;
    @BindView(R.id.rv_report)
    RecyclerView rv_report;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_income_branch_wise, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("bracch_code", branch_code));
            nameValuePairs.add(new BasicNameValuePair("trea_treatment_id", treatment_id));
            new WebServiceBase(nameValuePairs, getActivity(), this, GET_ALL_INCOMES).execute(ApiConfig.ALL_TREATMENT_INCOME);

            btn_print.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), IncomeReportPrintActivity.class);
                    intent.putExtra("type", "incometreatmentwise");
                    intent.putExtra("branch_code", branch_code);
                    intent.putExtra("trea_treatment_id", treatment_id);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GET_ALL_INCOMES:
                parseAllInvoices(response);
                break;
        }
    }

    public void parseAllInvoices(String response) {
        Log.d(TagUtils.getTag(), "invoices response:-" + response);
        try {
            Gson gson = new Gson();
            IncomePOJO invoicePOJO = gson.fromJson(response, IncomePOJO.class);
            if (invoicePOJO.getSuccess().equals("true")) {
                IncomeAdapter invoiceAdapter = new IncomeAdapter(getActivity(), invoicePOJO.getIncomeResultPOJOList());
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_report.setLayoutManager(horizontalLayoutManagaer);
                rv_report.setHasFixedSize(true);
                rv_report.setItemAnimator(new DefaultItemAnimator());
                rv_report.setAdapter(invoiceAdapter);
                tv_total_amount.setText("INR " + invoicePOJO.getTotal());
            } else {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "No Invoice Found");
                tv_total_amount.setText("INR 0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Something went wrong");
        }
    }
}
