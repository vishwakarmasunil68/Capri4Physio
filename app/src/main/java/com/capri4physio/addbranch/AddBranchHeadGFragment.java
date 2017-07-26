package com.capri4physio.addbranch;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.fragment.BaseFragment;
import com.capri4physio.fragment.StaffDashboardFragment;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddBranchHeadGFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author  prabhunathy
 * @version 1.0
 * @since   2016-03-31
 */
public class AddBranchHeadGFragment extends BaseFragment {
    String GetURL= ApiConfig.BASE_URL+"users/getuserlist";
    private EditText edtxt_name;
    private EditText edtxt_branchcode;
    private EditText edtxt_mail;
    private EditText edtxt_password;
    private Button mSave;
    Spinner spinnerbranchloca;
    private ProgressDialog pDialog;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LoginFragment.
     */
    public static AddBranchHeadGFragment newInstance() {
        AddBranchHeadGFragment fragment = new AddBranchHeadGFragment();
        return fragment;
    }

    public AddBranchHeadGFragment() {
        // Required empty public constructor
    }

    String[] location ={"Gurgaon","Sant Parmanand Hospital","Greater Kailash 1","Karkarduma"};
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
        View rootView = inflater.inflate(R.layout.add_cbranhc, container, false);
        edtxt_name= (EditText) rootView.findViewById(R.id.edtxt_name);
        edtxt_mail= (EditText) rootView.findViewById(R.id.edtxt_mail);
        edtxt_branchcode= (EditText) rootView.findViewById(R.id.edtxt_branchcode);
        edtxt_password= (EditText) rootView.findViewById(R.id.edtxt_password);
        mSave= (Button) rootView.findViewById(R.id.btn_save);
        spinnerbranchloca= (Spinner) rootView.findViewById(R.id.spinnerbranchloca);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isValid()) {

                    return;
                } else {
                    if (Utils.isNetworkAvailable(getActivity())) {
                        initProgressDialog("Please wait..");
                        addExpenseApiCall();
                    } else {
                        Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
                    }
                }
            }
        });

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, location);
        spinnerbranchloca.setAdapter(spinnerArrayAdapter);
        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              switch (position){
                  case 0:
                      edtxt_branchcode.setText("GGN");
                break;

                  case 1:
                      edtxt_branchcode.setText("SPH");
                      break;

                  case 2:
                      edtxt_branchcode.setText("GK1");
                      break;

                  case 3:
                      edtxt_branchcode.setText("KKD");
                      break;
              }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @Override
    protected void setListener() {
        super.setListener();


    }

    private void addExpenseApiCall(){

        String  input = edtxt_branchcode.getText().toString();

        final String  input1 = input.replace(" ", "");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.ADD_BRANCH_HEAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            pDialog.hide();

                            JSONObject jsonObject = new JSONObject(response);
                            String branch_status = jsonObject.getString("branch_status");
                            if (branch_status.equals("success")) {
                                Toast.makeText(getActivity(), "Record Successfully Added", Toast.LENGTH_LONG).show();
//                        getActivity().finish();
                                viewStaff();
                                Log.e("response", "" + response);
                            }
                        else {
                                Toast.makeText(getActivity(),"Branch code already exist",Toast.LENGTH_LONG).show();
                            }
                        }
                        catch(Exception e){
                            Log.e("error",e.toString());

                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
//                params.put("first_name", edtxt_name.getText().toString());
//                params.put("last_name", edtxt_name.getText().toString());
                params.put("branch_name", edtxt_name.getText().toString());
                params.put("branch_code", input1.toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    private boolean isValid() {

        String cName = edtxt_name.getText().toString().trim();
        String location = spinnerbranchloca.getSelectedItem().toString().trim();
        String email =edtxt_mail.getText().toString().trim();
        String password =edtxt_branchcode.getText().toString().trim();

        if (cName.isEmpty()) {
            edtxt_name.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.b_name));
            return false;
        }



        else if (password.isEmpty()) {
            edtxt_password.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error),
                    getResources().getString(R.string.err_bc));
            return false;
        }

        return true;
    }



    private void viewStaff() {
        getFragmentManager().popBackStack();
        StaffDashboardFragment fragment = StaffDashboardFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }
    @Override
    public void onPause() {
        super.onPause();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Dashboard");
    }
    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Add Branch");
    }
}