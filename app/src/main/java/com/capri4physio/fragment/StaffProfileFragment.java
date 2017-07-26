package com.capri4physio.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.capri4physio.R;
import com.capri4physio.activity.SplashActivity;
import com.capri4physio.adapter.MenuAdapter;
import com.capri4physio.dialog.ChangePasswordDialog;
import com.capri4physio.listener.DialogListener;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.model.UserDetailModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionTask;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.BundleConst;
import com.capri4physio.util.Utils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StaffProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StaffProfileFragment extends BaseFragment implements HttpUrlListener, ViewItemClickListener<String>, DialogListener<Bundle> {

    private Button mBtnChangePassword;
    private ImageView mgProfile;
    private TextView mTxtName, mTxtEmail, mTxtPhone;

    private UserDetailModel mUserModel;
    private ImageView mImgProfile;
    private MenuAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String[] mList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static StaffProfileFragment newInstance() {
        StaffProfileFragment fragment = new StaffProfileFragment();
        return fragment;
    }

    public StaffProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            String userDetails = AppPreferences.getInstance(getActivity()).getUserDetails();
            Gson gson = new Gson();
            mUserModel = gson.fromJson(userDetails, UserDetailModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mList = getResources().getStringArray(R.array.menu_staff_profile);
        mAdapter = new MenuAdapter(getActivity(), Arrays.asList(mList), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_staff, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mImgProfile = (ImageView) view.findViewById(R.id.img_profile);
        mTxtName = (TextView) view.findViewById(R.id.txt_name);
        mTxtEmail = (TextView) view.findViewById(R.id.txt_email);
        mTxtPhone = (TextView) view.findViewById(R.id.txt_phone);
        mBtnChangePassword = (Button) view.findViewById(R.id.btn_change_password);

        String fName = mUserModel.getResult().getUser().getFirstName();
        if (!(fName == null || fName.equalsIgnoreCase("null") || fName.equals(null)))
            mTxtName.setText(mUserModel.getResult().getUser().getFirstName() + " " + mUserModel.getResult().getUser().getLastName());

        mTxtEmail.setText(mUserModel.getResult().getUser().getEmail());
//        mTxtPhone.setText(mUserModel.getResult().getUser().get);

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

    }


    @Override
    public void onPostSuccess(Object response, int id) {
        BaseModel baseResponse = (BaseModel) response;
        int status = 0;
        String msg = "";
        try {
            status = baseResponse.getStatus();
            msg = baseResponse.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            status = 0;
            msg = "Oops! problem to change password!";
        }


        if (status == 1) {

            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            AppPreferences.getInstance(getActivity()).setUserLogin(false);
            AppPreferences.getInstance(getActivity()).clearAllData();
            Intent intent = new Intent(getActivity(), SplashActivity.class);
            intent.putExtra(BundleConst.IS_LOGOUT, true);
            startActivity(intent);
            getActivity().finish();

        } else {
            Utils.showMessage(getActivity(), msg);
        }
    }

    @Override
    public void onPostError(String errMsg, int id) {

    }


    private void changePassword() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ChangePasswordFragment fragment = ChangePasswordFragment.newInstance();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onViewItemClick(String s, int position, int actionId) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();


        switch (position) {
            case 0:
                StaffPersonalnfoFragment personalnfoFragment = StaffPersonalnfoFragment.newInstance();
                ft.add(R.id.fragment_container, personalnfoFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case 1:
                StaffPracticeHoursFragment practiceHoursFragment = StaffPracticeHoursFragment.newInstance();
                ft.add(R.id.fragment_container, practiceHoursFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;


            case 2:
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                ChangePasswordDialog changePasswordDialog = ChangePasswordDialog.newInstance();
                changePasswordDialog.setDialogListener(this);
                changePasswordDialog.show(ft, "dialog");
                break;

            default:


        }

    }

    @Override
    public void onDialogResult(Bundle bundle, int Id) {
        changePasswordApiCall(bundle);
    }

    private void changePasswordApiCall(Bundle b) {

        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.USER_ID, AppPreferences.getInstance(getActivity()).getUserID());
                params.put(ApiConfig.PASSWORD, b.getString(ApiConfig.NEW_PASSWORD));
                params.put(ApiConfig.OLD_PASSWORD, b.getString(ApiConfig.OLD_PASSWORD));

                new UrlConnectionTask(getActivity(), ApiConfig.CHANGE_PASSWORD_URL, ApiConfig.ID1, true, params, BaseModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }
}