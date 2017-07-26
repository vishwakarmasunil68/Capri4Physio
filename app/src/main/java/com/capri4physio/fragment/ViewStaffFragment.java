package com.capri4physio.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.adapter.UsersAdapter;
import com.capri4physio.fragment.assessment.InfoApps;
import com.capri4physio.model.UserItem;
import com.capri4physio.model.assessment.AddStaff;
import com.capri4physio.net.ApiConfig;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewStaffFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author  prabhunathy
 * @version 1.0
 * @since   2016-03-31
 */
public class ViewStaffFragment extends BaseFragment{
    String GetURL= ApiConfig.BASE_URL+"users/getuserlist";
    public static String userid;
    InfoApps Detailapp;
    public static ArrayList<String> patientaray;
    public static ArrayList<String> patientidarray =new ArrayList<String>();
    TextView add,manage,staffmanagenew;
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
    public static ViewStaffFragment newInstance() {
        ViewStaffFragment fragment = new ViewStaffFragment();
        return fragment;
    }

    public ViewStaffFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_view_staff, container, false);
        add=(TextView)rootView.findViewById(R.id.addnew);
        manage=(TextView)rootView.findViewById(R.id.managenew);
        staffmanagenew=(TextView)rootView.findViewById(R.id.staffmanagenew);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddStaff.class));
            }
        });
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ViewStaff.class));
            }
        });
        staffmanagenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ViewStaff.class));
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

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Staff");
    }

    @Override
    public void onPause() {
        super.onPause();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Dashboard");
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