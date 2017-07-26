package com.capri4physio.fragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.adapter.ClinicStaffAdapter;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.model.ClinicsDetailsModel;
import com.capri4physio.model.UserItem;
import com.capri4physio.model.UserListModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ClinicDetailsFragment extends BaseFragment implements HttpUrlListener, ViewItemClickListener<UserItem>{


    private String mClinicId = "";
    private static final String CLINIC_ID = "clinic_id";
    private RecyclerView mRecyclerView;
    private ClinicStaffAdapter mAdapter;
    private List<UserItem> mList;

    private ImageView mImgLogo;
    private TextView mTxtTitle;
    private TextView mTxtDesc;
    private RatingBar mRatingBar;
    private Button mBtnAvailabiltiy;
    private ClinicsDetailsModel clinicsDetailsModel = null;


    public ClinicDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ClinicDetailsFragment.
     */

    public static ClinicDetailsFragment newInstance(String clinicId) {
        ClinicDetailsFragment fragment = new ClinicDetailsFragment();
        Bundle args = new Bundle();
        args.putString(CLINIC_ID, clinicId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();
        mAdapter =new ClinicStaffAdapter(getActivity(), mList, this);

        if (getArguments() != null) {
            mClinicId = getArguments().getString(CLINIC_ID);
            AppLog.i("App", "mUserType  :" + mClinicId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_clinic_details, container, false);
        initView(rootView);
        setListener();
        clinicApiCall();
        return rootView;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        mImgLogo = (ImageView) view.findViewById(R.id.img_logo);
        mTxtTitle = (TextView) view.findViewById(R.id.txt_title);
        mTxtDesc = (TextView) view.findViewById(R.id.txt_desc_value);
        mBtnAvailabiltiy = (Button) view.findViewById(R.id.btn_availability);
        mBtnAvailabiltiy.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();

        mBtnAvailabiltiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clinicsDetailsModel != null) {
                    AvailabilityFragment fragment = AvailabilityFragment.newInstance(mClinicId, clinicsDetailsModel.getResult().getUserId());
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.add(R.id.fragment_container, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });
    }

    /**
     * @return none
     * @description Login web service API calling
     */
    private void clinicApiCall() {

        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.CLINIC_ID, mClinicId);
                new UrlConnectionTask(getActivity(), ApiConfig.CLINIC_URL, ApiConfig.ID1, true, params, ClinicsDetailsModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }

    /**
     * @description View staff web service API calling
     * @return none
     */
    private void viewStaffApiCall() {

        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.USER_ID, clinicsDetailsModel.getResult().getUserId());// Branch admin id

                new UrlConnectionTask(getActivity(), ApiConfig.VIEW_STAFF_URL, ApiConfig.ID2, true, params, UserListModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }

    @Override
    public void onPostSuccess(Object response, int id) {


        switch (id) {
            case ApiConfig.ID1:
                clinicsDetailsModel = (ClinicsDetailsModel) response;
                if (clinicsDetailsModel.getStatus() == 1) {

                    AppLog.i("Capri4Physio", "ClinicsDetailsModel Response : " + clinicsDetailsModel.getMessage());
                    mTxtTitle.setText(clinicsDetailsModel.getResult().clinicName);
                    mTxtDesc.setText(clinicsDetailsModel.getResult().getDescription());
                    viewStaffApiCall();

                } else {
                    Utils.showMessage(getActivity(), clinicsDetailsModel.getMessage());
                }

                break;

            case ApiConfig.ID2:
                UserListModel userListModel = (UserListModel) response;
                AppLog.i("Capri4Physio", "Staff listing response : " + userListModel.getStatus());
                if(userListModel.result.size() > 0) {
                    mList.clear();
                    mList.addAll(userListModel.result);
                    mAdapter.notifyDataSetChanged();
                    mBtnAvailabiltiy.setVisibility(View.GONE);
                }else{
                    mBtnAvailabiltiy.setVisibility(View.VISIBLE);
                }

                break;

            case ApiConfig.ID3:
                BaseModel baseResponse = (BaseModel) response;
                Utils.showMessage(getActivity(), baseResponse.getMessage());
                break;

            default:
                AppLog.e("Capri4Physio", "UNKNOW RESPONSE - " + id);

        }

    }

    @Override
    public void onPostError(String errMsg, int id) {

    }

    @Override
    public void onViewItemClick(UserItem userItem, int position, int actionId) {

        AvailabilityFragment fragment = AvailabilityFragment.newInstance(mClinicId, userItem.getId());
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
