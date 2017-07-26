package com.capri4physio.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.capri4physio.R;
import com.capri4physio.activity.SplashActivity;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.BundleConst;
import com.capri4physio.util.Utils;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends BaseFragment implements HttpUrlListener {

    private EditText mEdtxtOldPassword;
    private EditText mEdtxtNewPassword;
    private Button mBtnChangePassword;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LoginFragment.
     */
    public static ChangePasswordFragment newInstance() {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        return fragment;
    }

    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_change_password, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        mEdtxtOldPassword = (EditText)view.findViewById(R.id.edtxt_old_password);
        mEdtxtNewPassword = (EditText)view.findViewById(R.id.edtxt_new_password);
        mBtnChangePassword = (Button)view.findViewById(R.id.btn_change_password);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBtnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePasswordApiCall();
            }
        });
    }


    /**
     * @description Change password web service API calling
     * @return none
     */
    private void changePasswordApiCall() {
        if (!isValid())
            return;

        if (Utils.isNetworkAvailable(getActivity())) {

            try {
//                JSONObject params = new JSONObject();
//                params.put(ApiConfig.USER_ID, AppPreferences.getInstance(getActivity()).getUserID());
//                params.put(ApiConfig.CURRENT_PASSWORD, mEdtxtOldPassword.getText().toString().trim());
//                params.put(ApiConfig.NEW_PASSWORD, mEdtxtNewPassword.getText().toString().trim());

               // new UrlConnectionTask(getActivity(), ApiConfig.CHANGE_PASSWORD_URL, ApiConfig.ID3, true, params, BaseModel.class, this).execute("");

            } catch (Exception e) {e.printStackTrace();}


        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
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

    /**
     * Validation to check user inputs
     * @return
     */
    private boolean isValid() {

        String odlPassword=mEdtxtOldPassword.getText().toString().trim();
        String newPassword=mEdtxtNewPassword.getText().toString().trim();

        if (odlPassword.isEmpty()) {
            mEdtxtOldPassword.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_old_password));
            return false;
        }

        if (newPassword.isEmpty()) {
            mEdtxtNewPassword.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_new_password));
            return false;
        }

        return true;
    }
}