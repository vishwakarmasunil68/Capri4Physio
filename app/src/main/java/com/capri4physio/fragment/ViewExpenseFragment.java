package com.capri4physio.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
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
import com.capri4physio.Invoice.InfoApps1;
import com.capri4physio.R;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author  prabhunathy
 * @version 1.0
 * @since   2016-03-31
 */
public class ViewExpenseFragment extends BaseFragment {
    String GetURL= ApiConfig.BASE_URL+"users/getuserlist";
    private EditText mPain_Nature;
    private EditText mPain_Onset;
    private EditText mPain_Duration;
    private Button mSave;
    InfoApps1 detailapps;
    String newaddress;
    ArrayList<String> arrayList;
    Spinner spinnerbranchloca;
    private ProgressDialog pDialog;
FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LoginFragment.
     */
    public static ViewExpenseFragment newInstance() {
        ViewExpenseFragment fragment = new ViewExpenseFragment();
        return fragment;
    }

    public ViewExpenseFragment() {
        // Required empty public constructor
    }

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
        View rootView = inflater.inflate(R.layout.frgament_add_expe, container, false);

        fragmentManager =getFragmentManager();
        fragmentTransaction =fragmentManager.beginTransaction();
        mPain_Duration= (EditText) rootView.findViewById(R.id.edtxt_painduration);
        mPain_Nature= (EditText) rootView.findViewById(R.id.edtxt_painnature);
        mPain_Onset= (EditText) rootView.findViewById(R.id.edtxt_painonset);
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

        if (AppPreferences.getInstance(getActivity()).getUserType().equals("4")){
            spinnerbranchloca.setVisibility(View.VISIBLE);
        }
        else {


            newaddress = AppPreferences.getInstance(getActivity()).getUSER_BRANCH_CODE();
        }
        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String address = spinnerbranchloca.getSelectedItem().toString();

                String ad[]=address.split("\\(");
                newaddress = ad[1];
                newaddress =newaddress.replace(")", "");
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
        final String exp_billno = mPain_Nature.getText().toString().trim();
        final String exp_vendor = mPain_Onset.getText().toString().trim();
        final String exp_total = mPain_Duration.getText().toString().trim();
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.ADD_EXPENSE_REPORT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            pDialog.hide();


                            Toast.makeText(getActivity(),"Record Successfully Added",Toast.LENGTH_LONG).show();
//                        getActivity().finish();
                            viewStaff();
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
                params.put("exp_date", Utils.getCurrentDate());
                params.put("exp_billno", exp_billno);
                params.put("exp_branch_code", newaddress);
                params.put("exp_vendor", exp_vendor);
                params.put("exp_total", exp_total);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Add Expense");
    }

    @Override
    public void onPause() {
        super.onPause();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Dashboard");
    }

    private boolean isValid() {

        String cName = mPain_Nature.getText().toString().trim();
        String location = mPain_Onset.getText().toString().trim();
        String lat =mPain_Duration.getText().toString().trim();

        if (cName.isEmpty()) {
            mPain_Nature.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_bill_name));
            return false;
        }

        else if (location.isEmpty()) {
            mPain_Onset.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_vendor_name));
            return false;
        }

        else if (lat.isEmpty()) {
            mPain_Duration.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error),
                    getResources().getString(R.string.err_totl));
            return false;
        }


        return true;
    }

    private class CatagoryViewAsynTask extends AsyncTask<String, String, String> {
        String id, catagoryName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                initProgressDialog("Please wait..");
            }catch (Exception e){
                e.toString();
            }
        }

        @Override
        protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
            String content = HttpULRConnect.getData(ApiConfig.GetURL);
            Log.e("DoInBackGround ---->", String.valueOf(content));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                Log.e("DoInBackGroundtr", String.valueOf(s));
                     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray jsonArray = new JSONArray(s);
                for (int i =0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String branch_name = jsonObject2.getString("branch_name");
                    String bracch_code = jsonObject2.getString("branch_code");
                    //branch_code
//                    arrayList.add(bracch_code);

                    detailapps = new InfoApps1();
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