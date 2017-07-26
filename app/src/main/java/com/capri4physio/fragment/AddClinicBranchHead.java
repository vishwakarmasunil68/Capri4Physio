package com.capri4physio.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
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
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.fragment.assessment.InfoApps;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddClinicBranchHead#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author  prabhunathy
 * @version 1.0
 * @since   2016-03-31
 */
public class AddClinicBranchHead extends BaseFragment {
    String GetURL= ApiConfig.BASE_URL+"users/viewbranch";
    private EditText edtxt_name;
    private EditText edtxt_branchcode;
    private EditText edtxt_mail;
    private EditText edtxt_password;
    private Button mSave;
    InfoApps detailapps;
    ArrayList<String> arrayList;
    Spinner spinnerbranchloca;
    private ProgressDialog pDialog;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LoginFragment.
     */
    public static AddClinicBranchHead newInstance() {
        AddClinicBranchHead fragment = new AddClinicBranchHead();
        return fragment;
    }

    public AddClinicBranchHead() {
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

        arrayList = new ArrayList<String>();
       /* mList = new ArrayList<>();
        mAdapter =new UsersAdapter(getActivity(), mList, this);*/


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_clinic, container, false);
        edtxt_name= (EditText) rootView.findViewById(R.id.edtxt_name);
        edtxt_mail= (EditText) rootView.findViewById(R.id.edtxt_mail);
        edtxt_branchcode= (EditText) rootView.findViewById(R.id.edtxt_branchcode);
        edtxt_password= (EditText) rootView.findViewById(R.id.edtxt_password);
        mSave= (Button) rootView.findViewById(R.id.btn_save);
        spinnerbranchloca= (Spinner) rootView.findViewById(R.id.spinnerbranchloca);

        new CatagoryViewAsynTask().execute();
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


        /*if (AppPreferences.getInstance(getActivity()).getUserID().equals("4")){
            spinnerbranchloca.setVisibility(View.VISIBLE);
        }

        item = AppPreferences.getInstance(getActivity()).getUserType();
        Log.e("item",item);*/


        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String address = spinnerbranchloca.getSelectedItem().toString();

                String ad[]=address.split("\\(");
                String newaddress = ad[1];
                newaddress =newaddress.replace(")", "");
                edtxt_branchcode.setText(newaddress);
                edtxt_name.setText(ad[0]);
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

        /*String ad[]=address.split(" ");
        final String newaddress = ad[1];
        edtxt_branchcode.setText(newaddress);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.ADD_CLINIC_HEAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            pDialog.hide();

                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equals("Record Not save successfully")){
                                edtxt_mail.requestFocus();
                                Utils.showError(getActivity(), getResources().getString(R.string.error), "Clinic ID already exist");
                            }
                            else {
                                Toast.makeText(getActivity(), "Record Successfully Added", Toast.LENGTH_LONG).show();
                                viewStaff();


                            }
//                        getActivity().finish();

                            Log.e("response", "" + response);
                        }catch(Exception e){
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
                params.put("first_name", edtxt_name.getText().toString());
                params.put("last_name", edtxt_name.getText().toString());
                params.put("email", edtxt_mail.getText().toString());
                params.put("show_password", edtxt_password.getText().toString());
                params.put("password", edtxt_password.getText().toString());
                params.put("user_type", "3");
                params.put("status", "1");
                params.put("address2", spinnerbranchloca.getSelectedItem().toString());
                params.put("bracch_code", edtxt_branchcode.getText().toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    private class CatagoryViewAsynTask extends AsyncTask<String, String, String> {
        String id, catagoryName;


        @Override
        protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
            String content = HttpULRConnect.getData(GetURL);
            Log.e("DoInBackGround ---->", String.valueOf(content));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                Log.e("DoInBackGroundtr", String.valueOf(s));
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray jsonArray = new JSONArray(s);
                for (int i =0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String branch_name = jsonObject2.getString("branch_name");
                    String bracch_code = jsonObject2.getString("branch_code");
                    //branch_code
//                    arrayList.add(bracch_code);

                    detailapps = new InfoApps();
                    detailapps.setName(branch_name);
                    detailapps.setId(bracch_code);
                    arrayList.add(detailapps.getName() + "  "+ "(" + detailapps.getId()+ ")");

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getActivity(), android.R.layout.simple_dropdown_item_1line, arrayList);
                    spinnerbranchloca.setAdapter(spinnerArrayAdapter);

                }

            }catch(Exception e){
                Log.e("error",e.toString());

            }
        }
    }
    private boolean isValid() {

        String cName = edtxt_name.getText().toString().trim();
        String location = spinnerbranchloca.getSelectedItem().toString().trim();
        String email =edtxt_mail.getText().toString().trim();
        String password =edtxt_password.getText().toString().trim();

        if (cName.isEmpty()) {
            edtxt_name.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.h_name));
            return false;
        }

         if (location.isEmpty()) {
            spinnerbranchloca.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.location));
            return false;
        }

        else if (email.isEmpty()) {
            edtxt_mail.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error),
                    getResources().getString(R.string.h_email));
            return false;
        }

        else if (password.isEmpty()) {
            edtxt_password.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error),
                    getResources().getString(R.string.err_password));
            return false;
        }

        return true;
    }

    @Override
    public void onPause() {
        super.onStart();
        Log.e("start","onStart");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Add Clinic");
    }
    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Add Clinic");
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

}