package com.capri4physio.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.activity.BranchAdminActivity;
import com.capri4physio.activity.PatientDashboardActivity;
import com.capri4physio.activity.SplashActivity;
import com.capri4physio.activity.StudentDashboardActivity;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.Constants;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.capri4physio.util.Utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OtpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OtpFragment extends BaseFragment implements WebServicesCallBack{


    private static final String OTP = "otp";
    private static final String USER_TRYPE = "user_type";
    private static final String VERIFY_OTP = "verify_otp";
    private String mUserType;

    private Button mBtnVerify;
    private EditText mEdtxtOtp;
    private TextView mTxtOtpTemp,tv_resend_otp;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OtpFragment.
     */
    public static OtpFragment newInstance(String userType) {
        OtpFragment fragment = new OtpFragment();
        Bundle args = new Bundle();
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
            mUserType = getArguments().getString(USER_TRYPE);
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
        tv_resend_otp = (TextView) view.findViewById(R.id.tv_resend_otp);
        mEdtxtOtp = (EditText) view.findViewById(R.id.edtxt_otp);
        mBtnVerify = (Button) view.findViewById(R.id.btn_verify);
        mTxtOtpTemp.setVisibility(View.GONE);
    }

    @Override
    protected void setListener() {
        super.setListener();

        mBtnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isValid())
                    return;

                checkOTP();
//                if (mOtpPasscode != null && mOtpPasscode.equals(mEdtxtOtp.getText().toString().trim())) {
//                    dashboard();
//                }else {
//                    Utils.showMessage(getActivity(), getResources().getString(R.string.err_otp_msg));
//                }
            }
        });

        tv_resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOTP();
            }
        });
    }

    public void resendOTP(){
//        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//        nameValuePairs.add(new BasicNameValuePair("branch_code", branch_code));
//        new WebServiceBase(nameValuePairs, this, GET_APPOINTMENTS).execute(ApiConfig.view_all_appointments);
        new ResendOTP(getActivity(),AppPreferences.getInstance(getActivity().getApplicationContext()).getEmail()).execute(ApiConfig.resend_otp);
    }

    public void checkSmsOTP(String otp){
        mEdtxtOtp.setText(otp);
        new OtpVerify(getActivity(),AppPreferences.getInstance(getActivity().getApplicationContext()).getEmail(),otp).execute(ApiConfig.otp_verify_api);
    }

    public void checkOTP(){
        new OtpVerify(getActivity(),AppPreferences.getInstance(getActivity().getApplicationContext()).getEmail(),mEdtxtOtp.getText().toString()).execute(ApiConfig.otp_verify_api);
    }


    private void dashboard() {
        if (mUserType != null && mUserType.equals(Constants.GlobalConst.USER_PATIENT)) {
            AppPreferences.getInstance(getActivity()).setUserLogin(true);
            Intent intent = new Intent(getActivity(), PatientDashboardActivity.class);
            startActivity(intent);
            getActivity().finish();

        } else if (mUserType != null && mUserType.equals(Constants.GlobalConst.USER_BRANCH_MANAGER)) {

            AppPreferences.getInstance(getActivity()).setUserLogin(true);
            Intent intent = new Intent(getActivity(), BranchAdminActivity.class);
            startActivity(intent);
            getActivity().finish();

        }else if (mUserType != null && mUserType.equals(Constants.GlobalConst.USER_STUDENT)) {
            AppPreferences.getInstance(getActivity()).setUserLogin(true);
            Intent intent = new Intent(getActivity(), StudentDashboardActivity.class);
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

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){

        }
    }

    class OtpVerify extends AsyncTask<String,Void,String> {
        Activity activity;
        String otp,email;
        String jResult="";
        ProgressDialog progressDialog;
        public OtpVerify(Activity activity,String email,String otp){
            this.activity=activity;
            this.email=email;
            this.otp=otp;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("otp", otp));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                jResult = WebServiceBase.httpCall(params[0], nameValuePairs);
            } catch (Exception e) {
                if(progressDialog!=null){
                    progressDialog.dismiss();
                }
                e.printStackTrace();
            }
            return jResult;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
            try{
                Log.d(TagUtils.getTag(),"response:-"+jResult);
                JSONObject jsonObject=new JSONObject(jResult);
                if(jsonObject.optString("success").equals("true")){
                    AppPreferences.getInstance(getActivity().getApplicationContext()).setOTPVerified(true);
                    dashboard();
                }else{
                    ToastClass.showShortToast(getActivity().getApplicationContext(),"You Entered Wrong OTP.");
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
    class ResendOTP extends AsyncTask<String,Void,String> {
        Activity activity;
        String email;
        String jResult="";
        ProgressDialog progressDialog;
        public ResendOTP(Activity activity,String email){
            this.activity=activity;
            this.email=email;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("email", email));
                jResult = WebServiceBase.httpCall(params[0], nameValuePairs);
            } catch (Exception e) {
                if(progressDialog!=null){
                    progressDialog.dismiss();
                }
                e.printStackTrace();
            }
            return jResult;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(progressDialog!=null){
                progressDialog.dismiss();
            }

            try{
                Log.d(TagUtils.getTag(),"response:-"+jResult);
                JSONObject jsonObject=new JSONObject(jResult);
                if(jsonObject.optString("success").equals("true")){
                    ToastClass.showShortToast(getActivity().getApplicationContext(),"Otp Verification has been send successfully");
                }else{
                    ToastClass.showShortToast(getActivity().getApplicationContext(),"You Entered Wrong OTP.");
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}