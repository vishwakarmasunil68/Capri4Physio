package com.capri4physio.fragment;

import android.app.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.activity.SplashActivity;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.UserDetailModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionAuthTask;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.Utils;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author  prabhunathy
 * @version 1.0
 */
public class LoginFragment extends BaseFragment {

    private EditText mEdtxtEmail;
    private EditText mEdtxtPassword;
    private Button mBtnLogin;
    private TextView mTxtRegister;
    private TextView mTxtForgotPassword;
    private String[] arUserType = null;
    private HttpUrlListener mListener;
    private String priorityValue = "";


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof HttpUrlListener) {
            mListener = (HttpUrlListener) activity;
        } else {
            throw new RuntimeException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arUserType = getResources().getStringArray(R.array.array_priority_value);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        ((SplashActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mEdtxtEmail = (EditText)view.findViewById(R.id.edtxt_email);
        mEdtxtPassword = (EditText)view.findViewById(R.id.edtxt_password);
        mBtnLogin = (Button)view.findViewById(R.id.btn_login);
        mTxtForgotPassword = (TextView) view.findViewById(R.id.btn_forgot_password);
        mTxtRegister = (TextView) view.findViewById(R.id.btn_register);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginApiCall();

            }
        });
        mTxtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadForgotPassword();
            }
        });

        mTxtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModeFragment fragment = UserModeFragment.newInstance();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

    }


    /**
     * @description Login web service API calling
     * @return none
     */
    private void loginApiCall() {
        if (!isValid())
            return;

            if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.EMAIL, mEdtxtEmail.getText().toString().trim());
                params.put(ApiConfig.PASSWORD, mEdtxtPassword.getText().toString().trim());
                params.put("device_token", AppPreferences.GetDeviceToken(getActivity().getApplicationContext()));
                new UrlConnectionAuthTask(getActivity(), ApiConfig.LOGIN_URL, ApiConfig.ID1, true, params, UserDetailModel.class, mListener).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }

    /**
     * Launch to forgot password
     */
    private void loadForgotPassword(){
        ForgotPasswordFragment fragment=ForgotPasswordFragment.newInstance();
        FragmentTransaction ft=getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * Validation to check user inputs
     * @return none
     */
    private boolean isValid() {

        String email=mEdtxtEmail.getText().toString().trim();
        String pass=mEdtxtPassword.getText().toString().trim();

        if (email.isEmpty()) {
            mEdtxtEmail.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_email));
            return false;
        }

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        // Make the comparison case-insensitive.
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        boolean matchFound = matcher.matches();
        if (!matchFound) {
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_email));
            mEdtxtEmail.requestFocus();
            return false;
        }

        if (pass.isEmpty()) {
            mEdtxtPassword.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_password));
            return false;
        }
        return true;
    }
}