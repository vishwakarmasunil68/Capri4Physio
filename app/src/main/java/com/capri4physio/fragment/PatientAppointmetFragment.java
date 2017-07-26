package com.capri4physio.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.adapter.UsersAdapter;
import com.capri4physio.bookappopatient.AddAppointments;
import com.capri4physio.fragment.assessment.InfoApps;
import com.capri4physio.model.UserItem;
import com.capri4physio.util.AppPreferences;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientAppointmetFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author  prabhunathy
 * @version 1.0
 * @since   2016-03-31
 */
public class PatientAppointmetFragment extends BaseFragment {
    String GetURL= "http://www.caprispine.in/users/getuserlist";
    public static String userid;
    InfoApps Detailapp;
    public static ArrayList<String> patientaray;
    public static ArrayList<String> patientidarray =new ArrayList<String>();
    TextView add,manage,cancelnew,staffmanagenew;
    CardView main_2,main,main_1;
    private RecyclerView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    private UsersAdapter mAdapter;
    private List<UserItem> mList;
    private int itemPosition;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LoginFragment.
     */
    public static PatientAppointmetFragment newInstance() {
        PatientAppointmetFragment fragment = new PatientAppointmetFragment();
        return fragment;
    }

    public PatientAppointmetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* mList = new ArrayList<>();
        mAdapter =new UsersAdapter(getActivity(), mList, this);*/


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_patients, container, false);
        add=(TextView)rootView.findViewById(R.id.addnew);
        cancelnew=(TextView)rootView.findViewById(R.id.cancelnew);
        manage=(TextView)rootView.findViewById(R.id.managenew);
        staffmanagenew=(TextView)rootView.findViewById(R.id.staffmanagenew);
        main=(CardView)rootView.findViewById(R.id.main_weather);
        main_1=(CardView)rootView.findViewById(R.id.main_1);
        main_2=(CardView)rootView.findViewById(R.id.main_2);


        try {
            if (AppPreferences.getInstance(getActivity()).getUSER_BRANCH_CODE().equals("")) {
                main_1.setVisibility(View.GONE);
                main_2.setVisibility(View.GONE);
            } else {
                main_1.setVisibility(View.VISIBLE);
                main_2.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception e){
            main_1.setVisibility(View.GONE);
            main_2.setVisibility(View.GONE);
            e.printStackTrace();
        }
        String number =cancelnew.getText().toString();
       /* if (number.contains("9897386243")){

        }*/
        cancelnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "9897386243"));
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAppointmentFragmentbyPatient reportfragment = AddAppointmentFragmentbyPatient.newInstance();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, reportfragment);
//                getSupportActionBar().setTitle(title);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAppointments reportfragment = AddAppointments.newInstance();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, reportfragment);
//                getSupportActionBar().setTitle(title);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        staffmanagenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAppoinmentsPatientFragment reportfragment = ViewAppoinmentsPatientFragment.newInstance();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, reportfragment);
//                getSupportActionBar().setTitle(title);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        /*setListener();
        viewStaffApiCall();*/
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        /*mSnackBarLayout = (CoordinatorLayout)view.findViewById(R.id.coordinator_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);*/
    }

    @Override
    protected void setListener() {
        super.setListener();
    }


    /**
     * @description View staff web service API calling
     * @return none
     */
    /*private void viewStaffApiCall() {

        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.USER_ID, AppPreferences.getInstance(getActivity()).getUserID());

                new UrlConnectionTask(getActivity(), ApiConfig.VIEW_STAFF_URL, ApiConfig.ID1, true, params, UserListModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }

    @Override
    public void onPostSuccess(Object response, int id) {
        switch (id){
            case ApiConfig.ID1:
                UserListModel userListModel = (UserListModel) response;
                AppLog.i("Capri4Physio", "Staff listing response : " + userListModel.getStatus());
                if(userListModel.result.size() > 0) {
                    mList.clear();
                    mList.addAll(userListModel.result);
                    mAdapter.notifyDataSetChanged();
                }
                break;

            case ApiConfig.ID2:
                BaseModel deleteResponse = (BaseModel) response;
                AppLog.i("Capri4Physio", "Staff delete response : " + deleteResponse.getStatus());
                if(deleteResponse.getStatus() > 0) {
                    mList.remove(itemPosition);
                    mAdapter.notifyDataSetChanged();
                    showSnackMessage(deleteResponse.getMessage());
                }
                break;
        }

    }

    @Override
    public void onPostError(String errMsg, int id) {

    }


    private void showSnackMessage(String msg){
        Snackbar snack=Snackbar.make(mSnackBarLayout, msg, Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snack.getView();
        group.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBtnNormal));
        snack.setActionTextColor(Color.WHITE);
        snack.show();
    }

    @Override
    public void onViewItemClick(UserItem userItem, int position, int actionId) {

    }*/
}