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

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.activity.IncomeReportPrintActivity;
import com.capri4physio.adapter.CaseNotesPrintAdapter;
import com.capri4physio.adapter.ProgressNotesAdapter;
import com.capri4physio.adapter.RemarkNotesAdapter;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.pojo.notes.CaseNotePOJO;
import com.capri4physio.pojo.notes.ProgressNotePOJO;
import com.capri4physio.pojo.notes.RemarkPOJO;
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

public class NotesReportFragment extends Fragment implements WebServicesCallBack {
    private static final String GET_ALL_INCOMES = "get_all_invoices";
    String branch_code;
    String patient_id;
    String from_date;

    @BindView(R.id.btn_print)
    Button btn_print;

    public NotesReportFragment(String branch_code, String patient_id, String from_date,String notetype) {
        this.branch_code = branch_code;
        this.patient_id = patient_id;
        this.from_date = from_date;
        this.notetype=notetype;
    }


    @BindView(R.id.rv_report)
    RecyclerView rv_report;
    String notetype="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_notes, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            String[] start_date = from_date.split("-");

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("date", start_date[2] + "-" + start_date[1] + "-" + start_date[0]));
            nameValuePairs.add(new BasicNameValuePair("patient_id", patient_id));
            Log.d(TagUtils.getTag(),"note type:-"+notetype);
            switch (notetype){
                case "CASE":
                    new WebServiceBase(nameValuePairs, getActivity(), this, GET_ALL_INCOMES).execute(ApiConfig.GET_CASE_NOTES_API);
                    break;
                case "PROGRESS":
                    new WebServiceBase(nameValuePairs, getActivity(), this, GET_ALL_INCOMES).execute(ApiConfig.GET_PROGRESS_NOTES_API);
                    break;
                case "REMARK":
                    new WebServiceBase(nameValuePairs, getActivity(), this, GET_ALL_INCOMES).execute(ApiConfig.GET_REMARK_NOTES_API);
                    break;
            }

            btn_print.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), IncomeReportPrintActivity.class);
                    intent.putExtra("type", notetype);
                    intent.putExtra("branch_code", branch_code);
                    intent.putExtra("patient_id", patient_id);
                    String[] start_date = from_date.split("-");
                    intent.putExtra("date", start_date[2] + "-" + start_date[1] + "-" + start_date[0]);
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
        Log.d(TagUtils.getTag(), "report response:-" + response);

        if(notetype.equals("CASE")){
            try {
                Gson gson = new Gson();
                CaseNotePOJO caseNotePOJO = gson.fromJson(response, CaseNotePOJO.class);
                if (caseNotePOJO.getSuccess().equals("true")) {
                    CaseNotesPrintAdapter invoiceAdapter = new CaseNotesPrintAdapter(getActivity(), caseNotePOJO.getCaseNoteResultPOJOList());
                    LinearLayoutManager horizontalLayoutManagaer
                            = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    rv_report.setLayoutManager(horizontalLayoutManagaer);
                    rv_report.setHasFixedSize(true);
                    rv_report.setItemAnimator(new DefaultItemAnimator());
                    rv_report.setAdapter(invoiceAdapter);
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), "No Report Found");
                }
            } catch (Exception e) {
                e.printStackTrace();
                ToastClass.showShortToast(getActivity().getApplicationContext(), "Something went wrong");
            }
        }else if(notetype.equals("PROGRESS")){

            try {
                Gson gson = new Gson();
                ProgressNotePOJO progressNotePOJO = gson.fromJson(response, ProgressNotePOJO.class);
                if (progressNotePOJO.getSuccess().equals("true")) {
                    ProgressNotesAdapter invoiceAdapter = new ProgressNotesAdapter(getActivity(), progressNotePOJO.getProgressNoteResultPOJOList());
                    LinearLayoutManager horizontalLayoutManagaer
                            = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    rv_report.setLayoutManager(horizontalLayoutManagaer);
                    rv_report.setHasFixedSize(true);
                    rv_report.setItemAnimator(new DefaultItemAnimator());
                    rv_report.setAdapter(invoiceAdapter);
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), "No Report Found");
                }
            } catch (Exception e) {
                e.printStackTrace();
                ToastClass.showShortToast(getActivity().getApplicationContext(), "Something went wrong");
            }

        }else if(notetype.equals("REMARK")){

            try {
                Gson gson = new Gson();
                RemarkPOJO remarkPOJO = gson.fromJson(response, RemarkPOJO.class);
                if (remarkPOJO.getSuccess().equals("true")) {
                    RemarkNotesAdapter invoiceAdapter = new RemarkNotesAdapter(getActivity(), remarkPOJO.getRemarkResultPOJOList());
                    LinearLayoutManager horizontalLayoutManagaer
                            = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    rv_report.setLayoutManager(horizontalLayoutManagaer);
                    rv_report.setHasFixedSize(true);
                    rv_report.setItemAnimator(new DefaultItemAnimator());
                    rv_report.setAdapter(invoiceAdapter);
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), "No Report Found");
                }
            } catch (Exception e) {
                e.printStackTrace();
                ToastClass.showShortToast(getActivity().getApplicationContext(), "Something went wrong");
            }

        }

    }
}
