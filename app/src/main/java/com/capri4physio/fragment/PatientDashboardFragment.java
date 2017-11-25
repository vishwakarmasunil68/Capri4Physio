package com.capri4physio.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBaseFragment;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.activity.AddPatientAppointment;
import com.capri4physio.activity.ChatUserActivity;
import com.capri4physio.activity.IncomeReportPrintActivity;
import com.capri4physio.activity.ManagePatientAppointmentsActivity;
import com.capri4physio.listener.FragmentListener;
import com.capri4physio.model.patient.PatientAmountPOJO;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.capri4physio.net.ApiConfig.GET_LAST_PATIENT_TRANSACTION_AMOUNT;


public class PatientDashboardFragment extends BaseFragment implements WebServicesCallBack {

    private static final String GET_PATIENT_TRANSACTION_AMOUNT = "get_patient_transaction_amount";
    private View img_appointments, mViewBokAppointment, img_billing, img_health_summary, rv_chat, rl_wallet;
    private FragmentListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static PatientDashboardFragment newInstance() {
        PatientDashboardFragment fragment = new PatientDashboardFragment();
        return fragment;
    }

    public PatientDashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FragmentListener) {
            mListener = (FragmentListener) activity;
        } else {
            throw new RuntimeException(activity.toString() + " must implement FragmentListener");
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_patient_dashboard, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        mViewBokAppointment = (View) view.findViewById(R.id.layout_book_appointment);
        img_appointments = (View) view.findViewById(R.id.img_appointments);
        img_health_summary = (View) view.findViewById(R.id.img_health_summary);
        img_billing = (View) view.findViewById(R.id.img_billing);
        rv_chat = (View) view.findViewById(R.id.rv_chat);
        rl_wallet = (View) view.findViewById(R.id.rl_wallet);

    }

    @Override
    protected void setListener() {
        super.setListener();
        mViewBokAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), MapActivity.class));
                startActivity(new Intent(getActivity(), AddPatientAppointment.class));
//                mListener.onFragmentResult(new Bundle(), PatientDashboardActivity.BOOK_APPOINTMENT);
            }
        });

        img_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                PatientAppointmetFragment reportfragment = PatientAppointmetFragment.newInstance();
////                ViewAppoinmentsPatientFragment reportfragment = ViewAppoinmentsPatientFragment.newInstance();
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment_container, reportfragment);
////                getSupportActionBar().setTitle(title);
//                ft.addToBackStack(null);
//                ft.commit();
                startActivity(new Intent(getActivity(), ManagePatientAppointmentsActivity.class));
            }
        });

        img_health_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ViewReportsPatientFragment reportfragment = ViewReportsPatientFragment.newInstance();
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment_container, reportfragment);
//                ft.addToBackStack(null);
//                ft.commit();

                Intent intent=new Intent(getActivity(), IncomeReportPrintActivity.class);
                intent.putExtra("type","patientcasereport");
                intent.putExtra("patient_id",AppPreferences.getInstance(getActivity().getApplicationContext()).getUserID());
                intent.putExtra("branch_code",AppPreferences.getInstance(getActivity().getApplicationContext()).getUSER_BRANCH_CODE());
                startActivity(intent);
            }
        });

        img_billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPatientInvoiceFragment reportfragment = ViewPatientInvoiceFragment.newInstance();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, reportfragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        rv_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatUserActivity.class);
                intent.putExtra("user_type", AppPreferences.getInstance(getActivity().getApplicationContext()).getUserType());
                startActivity(intent);
            }
        });

        rl_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPatientAmountAPI();
            }
        });
    }

    public void callPatientAmountAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("p_id", AppPreferences.getInstance(getActivity().getApplicationContext()).getUserID()));
        new WebServiceBaseFragment(nameValuePairs, getActivity(), PatientDashboardFragment.this, GET_PATIENT_TRANSACTION_AMOUNT).execute(GET_LAST_PATIENT_TRANSACTION_AMOUNT);
    }

    public void showPatientWalletAmount(String amount) {
        final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_amount);
        dialog1.setTitle("Wallet Amount");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btn_amount = (Button) dialog1.findViewById(R.id.btn_amount);
        TextView tv_amount = (TextView) dialog1.findViewById(R.id.tv_amount);

        tv_amount.setText("Amount in your wallet is INR " + amount);


        btn_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GET_PATIENT_TRANSACTION_AMOUNT:
                parsePatientAmount(response);
                break;
        }
    }

    private void parsePatientAmount(String response) {
        Log.d(TagUtils.getTag(), "patient amount response:-" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {
                PatientAmountPOJO patientAmountPOJO = new Gson().fromJson(jsonObject.optJSONArray("result").optJSONObject(0).toString(), PatientAmountPOJO.class);
                showPatientWalletAmount(patientAmountPOJO.getPt_total_amount());
            } else {
                showPatientWalletAmount("0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Something went wrong");
        }

    }
}
