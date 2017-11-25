package com.capri4physio.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBaseFragment;
import com.capri4physio.activity.SplashActivity;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.capri4physio.util.Utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import viewreport.Services.WebServiceCallBack;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgotPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotPasswordFragment extends BaseFragment implements WebServiceCallBack{

    private static final String CALL_FORGOT_API = "call_forgot_api";
    private EditText mEdtxtEmail;
    private Button mBtnForgotPassword;

    private HttpUrlListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LoginFragment.
     */
    public static ForgotPasswordFragment newInstance() {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        return fragment;
    }

    public ForgotPasswordFragment() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        ((SplashActivity) getActivity()).getSupportActionBar().show();
        initView(rootView);
        setListener();
        return rootView;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mEdtxtEmail = (EditText)view.findViewById(R.id.edtxt_email);
        mBtnForgotPassword = (Button)view.findViewById(R.id.btn_submit);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBtnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginApiCall();
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

//            try {
//                JSONObject params = new JSONObject();
//                params.put(ApiConfig.EMAIL, mEdtxtEmail.getText().toString().trim());
//
//                new UrlConnectionTask(getActivity(), ApiConfig.FORGOT_PASSWORD_URL, ApiConfig.ID2, true, params, BaseModel.class, mListener).execute("");
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("email",mEdtxtEmail.getText().toString()));
            new WebServiceBaseFragment(nameValuePairs,getActivity(),this,CALL_FORGOT_API).execute(ApiConfig.FORGOT_PASSWORD_URL);


        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }

    /**
     * Validation to check user inputs
     * @return
     */
    private boolean isValid() {

        String email=mEdtxtEmail.getText().toString().trim();

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

        return true;
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case CALL_FORGOT_API:
                parseForgotPasswordAPI(response);
                break;
        }
    }

    public void parseForgotPasswordAPI(String response){
        Log.d(TagUtils.getTag(),"forgot response:-"+response);
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("success").equals("true")){
                ToastClass.showShortToast(getActivity().getApplicationContext(),jsonObject.optString("message"));
            }else{
                ToastClass.showShortToast(getActivity().getApplicationContext(),"Email Not Found");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}