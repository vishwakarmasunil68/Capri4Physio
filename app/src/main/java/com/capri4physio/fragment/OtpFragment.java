package com.capri4physio.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.activity.PatientDashboardActivity;
import com.capri4physio.activity.BranchAdminActivity;
import com.capri4physio.activity.SplashActivity;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.Constants;
import com.capri4physio.util.Utils;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OtpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OtpFragment extends BaseFragment {


    private static final String OTP = "otp";
    private static final String USER_TRYPE = "user_type";
    private String mOtpPasscode = "";
    private String mUserType;

    private Button mBtnVerify;
    private EditText mEdtxtOtp;
    private TextView mTxtOtpTemp;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OtpFragment.
     */
    public static OtpFragment newInstance(String otp,String userType) {
        OtpFragment fragment = new OtpFragment();
        Bundle args = new Bundle();
        args.putString(OTP, otp);
        args.putString(USER_TRYPE, userType);
        fragment.setArguments(args);
        return fragment;
    }

    public OtpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOtpPasscode = getArguments().getString(OTP);
            mUserType = getArguments().getString(USER_TRYPE);
            AppLog.i("App", "OTP  :"+mOtpPasscode);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_otp, container, false);
        ((SplashActivity) getActivity()).getSupportActionBar().show();
        initView(rootView);
        setListener();
        return rootView;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mTxtOtpTemp = (TextView) view.findViewById(R.id.txt_otp_temp);
        mEdtxtOtp = (EditText) view.findViewById(R.id.edtxt_otp);
        mBtnVerify = (Button) view.findViewById(R.id.btn_verify);
        mTxtOtpTemp.setText("This text will be removed after SMS; OTP PASSCODE - "+mOtpPasscode);
    }

    @Override
    protected void setListener() {
        super.setListener();

        mBtnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isValid())
                    return;

                if (mOtpPasscode != null && mOtpPasscode.equals(mEdtxtOtp.getText().toString().trim())) {
                    dashboard();
                }else {
                    Utils.showMessage(getActivity(), getResources().getString(R.string.err_otp_msg));
                }
            }
        });


    }


    private void dashboard(){
        if (mUserType != null && mUserType.equals(Constants.GlobalConst.USER_PATIENT)) {
            AppPreferences.getInstance(getActivity()).setUserLogin(true);
            Intent intent = new Intent(getActivity(), PatientDashboardActivity.class);
            startActivity(intent);
            getActivity().finish();

        }else if (mUserType != null && mUserType.equals(Constants.GlobalConst.USER_BRANCH_MANAGER)) {

            AppPreferences.getInstance(getActivity()).setUserLogin(true);
            Intent intent = new Intent(getActivity(), BranchAdminActivity.class);
            startActivity(intent);
            getActivity().finish();

        }
    }

    /**
     * Validation to check user inputs
     *
     * @return none
     */
    private boolean isValid() {

        String strOtp = mEdtxtOtp.getText().toString().trim();

        if (strOtp.isEmpty()) {
            mEdtxtOtp.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_otp));
            return false;
        }

        return true;
    }
}