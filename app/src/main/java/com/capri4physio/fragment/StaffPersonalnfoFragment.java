package com.capri4physio.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.capri4physio.R;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.UserDetailModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionAuthTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.Utils;

import org.json.JSONObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StaffPersonalnfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author prabhunathy
 * @version 1.0
 * @since 2016-May-08
 */
public class StaffPersonalnfoFragment extends BaseFragment {

    private EditText mEdtxtFname;
    private EditText mEdtxtLname;
    private EditText mEdtxtEmail;

    private Button mBtnSave;
    private HttpUrlListener mListener;
    private String mUserType = "0";
    private static final String USER_TYPE = "user_type";


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StaffPersonalnfoFragment.
     */
    public static StaffPersonalnfoFragment newInstance() {
        StaffPersonalnfoFragment fragment = new StaffPersonalnfoFragment();
        Bundle args = new Bundle();
//        args.putString(USER_TYPE, userType);
        fragment.setArguments(args);
        return fragment;
    }

    public StaffPersonalnfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof HttpUrlListener) {
            mListener = (HttpUrlListener) activity;
        } else {
            throw new RuntimeException(activity.toString() + " must implement HttpUrlListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mUserType = getArguments().getString(USER_TYPE);
            AppLog.i("App", "mUserType  :" + mUserType);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_info_staff, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mEdtxtFname = (EditText) view.findViewById(R.id.edtxt_fname);
        mEdtxtLname = (EditText) view.findViewById(R.id.edtxt_lname);
        mEdtxtEmail = (EditText) view.findViewById(R.id.edtxt_email);
        mBtnSave = (Button) view.findViewById(R.id.btn_save);
    }

    @Override
    protected void setListener() {
        super.setListener();

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateApiCall();
            }
        });
    }


    /**
     * @return none
     * @description Login web service API calling
     */
    private void updateApiCall() {
        if (!isValid())
            return;

        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.FIRST_NAME, mEdtxtFname.getText().toString().trim());
                params.put(ApiConfig.LAST_NAME, mEdtxtLname.getText().toString().trim());
                params.put(ApiConfig.EMAIL, mEdtxtEmail.getText().toString().trim());

                new UrlConnectionAuthTask(getActivity(), ApiConfig.REGISTER_URL, ApiConfig.ID2, true, params, UserDetailModel.class, mListener).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }


    /**
     * Validation to check user inputs
     *
     * @return none
     */
    private boolean isValid() {

        String fname = mEdtxtFname.getText().toString().trim();
        String lname = mEdtxtLname.getText().toString().trim();
        String email = mEdtxtEmail.getText().toString().trim();

        if (fname.isEmpty()) {
            mEdtxtFname.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_fname));
            return false;
        }

        if (lname.isEmpty()) {
            mEdtxtLname.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_lname));
            return false;
        }

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


}