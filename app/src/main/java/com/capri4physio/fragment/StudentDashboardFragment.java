package com.capri4physio.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.activity.ChatActivity;
import com.capri4physio.activity.ChatUserActivity;
import com.capri4physio.activity.SelectCourseActivity;
import com.capri4physio.activity.StudentCourseBillingActivity;
import com.capri4physio.listener.FragmentListener;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.Constants;
import com.capri4physio.util.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class StudentDashboardFragment extends BaseFragment {

    private View img_appointments, mViewBokAppointment, img_billing, img_health_summary;
    LinearLayout std_dashboard;
    ProgressDialog pDialog;
    private FragmentListener mListener;
    ImageView img_chat;
    RelativeLayout rl_billing;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static StudentDashboardFragment newInstance() {
        StudentDashboardFragment fragment = new StudentDashboardFragment();
        return fragment;
    }

    public StudentDashboardFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_student_dashboard, container, false);
        initView(rootView);
        setListener();
        Log.e("detail", AppPreferences.getInstance(getActivity()).getUserID());
//        initProgressDialog("Please wait...");
//        viewStudentApiCall();

        return rootView;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        mViewBokAppointment = (View) view.findViewById(R.id.layout_book_appointment);
        img_appointments = (View) view.findViewById(R.id.img_appointments);
        img_health_summary = (View) view.findViewById(R.id.img_health_summary);
        img_billing = (View) view.findViewById(R.id.img_billing);
        std_dashboard = (LinearLayout) view.findViewById(R.id.std_dashboard);
        img_chat = (ImageView) view.findViewById(R.id.img_chat);
        rl_billing = (RelativeLayout) view.findViewById(R.id.rl_billing);

        img_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppPreferences.getInstance(getActivity().getApplicationContext()).getUserType().equals(Constants.GlobalConst.USER_STUDENT)) {
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.putExtra("user_id",AppPreferences.getInstance(getActivity().getApplicationContext()).getUserID());
                    intent.putExtra("friend_user_id", "1");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), ChatUserActivity.class);
                    intent.putExtra("user_type", AppPreferences.getInstance(getActivity().getApplicationContext()).getUserType());
                    startActivity(intent);
                }
            }
        });

        rl_billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), StudentCourseBillingActivity.class));
            }
        });
    }

    private void exit() {
        Log.e("exit", "exit");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure, you want to done payment");
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                initProgressDialog("Please wait...");
                addStudentApiCall();

            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.create();
        builder.show();
    }

    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        try {
            pDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addStudentApiCall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.addstudentamount,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("response", "" + response);
                            pDialog.hide();

                            JSONObject jsonObject = new JSONObject(response);
                            String branch_status = jsonObject.getString("Success");
                            if (branch_status.equals("true")) {
                                Toast.makeText(getActivity(), "Payment Successfully Done", Toast.LENGTH_LONG).show();
                                AppPreferences.getInstance(getActivity()).setUSER_PAYMENT_STATUS("1");
                                std_dashboard.setVisibility(View.VISIBLE);
//                                getActivity().finish();
                                Log.e("response", "" + response);
                            } else {
                                Toast.makeText(getActivity(), "Payment failed", Toast.LENGTH_LONG).show();
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
                params.put(ApiConfig.std_user_id, AppPreferences.getInstance(getActivity()).getUserID());
                params.put(ApiConfig.std_amount, "1000");
                params.put(ApiConfig.date, Utils.getCurrentDate());

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mViewBokAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SelectCourseActivity.class));
            }
        });

        img_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PatientAppointmetFragment reportfragment = PatientAppointmetFragment.newInstance();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, reportfragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        img_health_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewReportsPatientFragment reportfragment = ViewReportsPatientFragment.newInstance();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, reportfragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


    }
}
