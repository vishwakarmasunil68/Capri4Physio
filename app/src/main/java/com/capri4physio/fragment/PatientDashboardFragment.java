package com.capri4physio.fragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capri4physio.R;
import com.capri4physio.activity.ChatUserActivity;
import com.capri4physio.fragment.assessment.MapActivity;
import com.capri4physio.listener.FragmentListener;
import com.capri4physio.util.AppPreferences;


public class PatientDashboardFragment extends BaseFragment {

    private View img_appointments,mViewBokAppointment,img_billing,img_health_summary,rv_chat;
    private FragmentListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
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

    }

    @Override
    protected void setListener() {
        super.setListener();
        mViewBokAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MapActivity.class));
//                mListener.onFragmentResult(new Bundle(), PatientDashboardActivity.BOOK_APPOINTMENT);
            }
        });

        img_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PatientAppointmetFragment reportfragment = PatientAppointmetFragment.newInstance();
//                ViewAppoinmentsPatientFragment reportfragment = ViewAppoinmentsPatientFragment.newInstance();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, reportfragment);
//                getSupportActionBar().setTitle(title);
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

        img_billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewInvoicesPatientFragment reportfragment = ViewInvoicesPatientFragment.newInstance();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, reportfragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        rv_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ChatUserActivity.class);
                intent.putExtra("user_type", AppPreferences.getInstance(getActivity().getApplicationContext()).getUserType());
                startActivity(intent);
            }
        });

    }
}
