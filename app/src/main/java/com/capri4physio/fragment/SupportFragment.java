package com.capri4physio.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.capri4physio.R;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.Utils;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SupportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SupportFragment extends BaseFragment implements HttpUrlListener {
    private Spinner spinnPriority;
    private EditText mEdtxtTitle,mEdTxtDesc;
    private CoordinatorLayout mSnackBarLayout;

    private String[] arPriority = null;
    private String priorityValue = "";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static SupportFragment newInstance() {
        SupportFragment fragment = new SupportFragment();
        return fragment;
    }

    public SupportFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arPriority = getResources().getStringArray(R.array.array_priority_value);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_support, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        mSnackBarLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        spinnPriority = (Spinner) view.findViewById(R.id.spinner);
        mEdtxtTitle = (EditText) view.findViewById(R.id.edtxt_title);
        mEdTxtDesc = (EditText) view.findViewById(R.id.edtxt_desc);
    }

    @Override
    protected void setListener() {
        super.setListener();


        spinnPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priorityValue = arPriority[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void onPostSuccess(Object response, int id) {
        BaseModel bugResponse = (BaseModel) response;
        AppLog.i("Jobswolf", "Bug casereport response : " + bugResponse.getStatus());
        if (bugResponse.getStatus() > 0) {
            showSnackMessage(bugResponse.getMessage());
            mEdtxtTitle.setText("");
            mEdTxtDesc.setText("");
        }
    }

    @Override
    public void onPostError(String errMsg, int id) {

    }







    private void showSnackMessage(String msg) {
        Snackbar snack = Snackbar.make(mSnackBarLayout, msg, Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snack.getView();
        group.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        snack.setActionTextColor(Color.WHITE);
        snack.show();
    }


    /**
     * Validation to check user inputs
     *
     * @return none
     */
    private boolean isValid() {

        String title = mEdtxtTitle.getText().toString().trim();
        String desc = mEdTxtDesc.getText().toString().trim();

        if (title.isEmpty()) {
            mEdtxtTitle.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_bug_title));
            return false;
        }

        if (desc.isEmpty()) {
            mEdTxtDesc.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_bug_desc));
            return false;
        }
        return true;
    }
}