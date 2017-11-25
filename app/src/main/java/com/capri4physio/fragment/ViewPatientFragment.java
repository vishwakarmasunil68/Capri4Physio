package com.capri4physio.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.capri4physio.adapter.UsersAdapter;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.fragment.assessment.Patient_listAdapter;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.model.UserItem;
import com.capri4physio.model.UserListModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.Constants;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ViewPatientFragment extends BaseFragment implements HttpUrlListener, ViewItemClickListener<UserItem>, AdapterView.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    private UsersAdapter mAdapter;
    private Patient_listAdapter mAdapter1;
    private List<UserItem> mList;
    public static ArrayList<UserItem> contactDetails1;
    private int itemPosition;
    ProgressDialog progressDialog;
    ArrayList<String> arrayList;
    String Branch_Code;
    Spinner spinnerbranchloca;
    UserItem Detailapp1;
    InfoApps1 detailApps;
    Map<String, String> objresponse;
    static String value;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ViewPatientFragment.
     */
    public static ViewPatientFragment newInstance(Boolean indexing) {
        ViewPatientFragment fragment = new ViewPatientFragment();
//        value=indexing;
        if (indexing == true) {
            value = "Opd";
        } else {
            value = "Home Patient";
        }
        Log.e("value", value);
        return fragment;
    }

    public ViewPatientFragment() {
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
        arrayList.add("Select Branch");
        mList = new ArrayList<>();
        new CatagoryViewAsynTask().execute();
        contactDetails1 = new ArrayList<UserItem>();
        mAdapter = new UsersAdapter(getActivity(), mList, this);
        mAdapter1 = new Patient_listAdapter(contactDetails1, getActivity(), this, mSnackBarLayout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_patient, container, false);
        mSnackBarLayout = (CoordinatorLayout) rootView.findViewById(R.id.coordinator_layout);
        setHasOptionsMenu(true);
        initView(rootView);
        setListener();
        getpatientlist();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        Log.e("initview", "initview");
        if (AppPreferences.getInstance(getActivity()).getUserType().equals("4")) {
            Branch_Code = "";
        } else {
            Branch_Code = AppPreferences.getInstance(getActivity()).getUSER_BRANCH_CODE();
        }
        spinnerbranchloca = (Spinner) view.findViewById(R.id.spinnerbranchloca);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();

        if (AppPreferences.getInstance(getActivity()).getUserType().equals("4")) {
            spinnerbranchloca.setVisibility(View.VISIBLE);
        }
        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
                    Branch_Code = spinnerbranchloca.getSelectedItem().toString();
                    Log.d("cd", Branch_Code);

                    String ad[] = Branch_Code.split("\\(");
                    Branch_Code = ad[1];
                    Branch_Code = Branch_Code.replace(")", "");

//                    edtxt_branchcode.setText(newaddress);
                    getpatientlist();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        mRecyclerView.setOnClickListener(getActivity());
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main1, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        int mNotifCount = 0;
        if (searchItem != null) {
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    try {
                        if (newText.length() > 0 && Branch_Code.length() > 0) {
                            try {
                                isInteger(newText);
                                mRecyclerView.setVisibility(View.INVISIBLE);
                            } catch (Exception e) {
                                Log.d("sunil", e.toString());
                            }
                        } else if (newText.equals("")) {
                            mRecyclerView.setVisibility(View.INVISIBLE);
                            getpatientlist();
                        } else {
                            Toast.makeText(getActivity(), "Please Select branch name", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.d("sunil", e.toString());
                    }
                    return false;
                }
            });

        } else {
            try {
                contactDetails1.clear();
            } catch (Exception e) {
                Log.d("sunil", e.toString());
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_alert) {
            return true;
        }
        if (id == R.id.action_new) {
            addNewStaff();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void addNewStaff() {
        NewStaffFragment fragment = NewStaffFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public boolean isAlphaNumeric(String s) {
        String pattern = "^[a-zA-Z0-9]*$";
        return s.matches(pattern);
    }

    public boolean isInteger(String s) {
        try {

            Integer.parseInt(s);
            mRecyclerView.setVisibility(View.VISIBLE);
            Log.e("try", "try");

            getsearchlistMobile(s);

        } catch (NumberFormatException e) {
            if (s.contains("@")) {
                Log.e("catch", "catch@");
                mRecyclerView.setVisibility(View.VISIBLE);
                getsearchlistEmail(s);
            } else {
                Log.e("try", "catchstri");
                mRecyclerView.setVisibility(View.VISIBLE);
                getsearchlist(s);
            }
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /**
     * @return none
     * @description View staff web service API calling
     */
    private void viewPatientApiCall() {

        if (Utils.isNetworkAvailable(getActivity())) {
            try {
                JSONObject params = new JSONObject();
                params.put("treatment_type", "opd");
                new UrlConnectionTask(getActivity(), ApiConfig.VIEW_PATIENT_URL, ApiConfig.ID1, true, params, UserListModel.class, this).execute("");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }

    @Override
    public void onPostSuccess(Object response, int id) {
        switch (id) {
            case ApiConfig.ID1:
                UserListModel userListModel = (UserListModel) response;
//                AppLog.i("Capri4Physio", "Staff listing Response : " + userListModel.getStatus());
                if (userListModel.result.size() > 0) {
                    mList.clear();
                    mList.addAll(userListModel.result);
                    mAdapter.notifyDataSetChanged();
                }
                break;

            case ApiConfig.ID2:
//                Toast.makeText(getActivity(),"",Toast.LENGTH_LONG).show();
                BaseModel deleteResponse = (BaseModel) response;
                AppLog.i("Capri4Physio", "Patient delete Response : " + deleteResponse.getStatus());
                if (deleteResponse.getStatus() > 0) {
                    try {
                        mList.remove(itemPosition);
                        mAdapter.notifyDataSetChanged();
                        showSnackMessage(deleteResponse.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

    }

    @Override
    public void onPostError(String errMsg, int id) {

    }


    private void showSnackMessage(String msg) {
        Snackbar snack = Snackbar.make(mSnackBarLayout, msg, Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snack.getView();
        group.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBtnNormal));
        snack.setActionTextColor(Color.WHITE);
        snack.show();
    }

    private void deleteAlert(final String id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure, you want to delete");
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                deleteApiCall(id);

            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.create();
        builder.show();
    }


    @Override
    public void onViewItemClick(UserItem userItem, int position, int actionId) {


        if (actionId == Constants.ClickIDConst.ID_ATTACHMENT_CLICK) {
            showSnackMessage("Record Successfully Removed");
        } else {
            String clinicId = AppPreferences.getInstance(getActivity()).getClinicId();
            MyClinicPatientFragment fragment = MyClinicPatientFragment.newInstance(clinicId, userItem.getId(), userItem.getBracch_code());
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    private void getsearchlist(final String searchstringvalue) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEWSEARCHPATIENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        try {
                            if (contactDetails1.size() > 0) {
                                contactDetails1.clear();
                            }
                            Log.e("result", response);
                            progressDialog.hide();
//                            new UrlConnectionTask(getActivity(), ApiConfig.VIEW_PATIENT_URL, ApiConfig.ID1, true, objresponse, UserListModel.class, this).execute("");                            else {

                            JSONObject jsonObject1 = new JSONObject(response);
                            /*String success=jsonObject1.getString("success");
                            if (success.equals("true")){
                                Log.e("success",response);*/
                            JSONArray jsonArray = jsonObject1.getJSONArray("result");
                            for (int i = jsonArray.length() - 1; i >= 0; i--) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String status = jsonObject.getString("status");
                                if (status.equals("1")) {
                                    Log.e("json", jsonObject.toString());
                                    String id = jsonObject.getString("id");
                                    String first_name = jsonObject.getString("first_name");
                                    String last_name = jsonObject.getString("last_name");
                                    String email = jsonObject.getString("email");
                                    String profile_pic = jsonObject.getString("profile_pic");


                                    Detailapp1 = new UserItem();
                                    Detailapp1.setName(first_name + " " + last_name);
                                    Detailapp1.setEmail(email);
                                    Detailapp1.setId(id);
                                    Detailapp1.setProfilePic(profile_pic);
                                    contactDetails1.add(Detailapp1);

                                    mRecyclerView.setAdapter(mAdapter1);
//                                    Collections.reverse(contactDetails1);

//                                    Log.e("contactDetails1",mList.toString());

                                } else {
                                    contactDetails1.clear();

                                    mRecyclerView.setAdapter(mAdapter1);
                                }
//                                mAdapter1 = new Patient_listAdapter(contactDetails1, getActivity());

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.e("Postdat", "" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                    }
                }) {


            protected Map<String, String> getParams() {
                objresponse = new HashMap<String, String>();
                objresponse.put("first_name", searchstringvalue);
                objresponse.put("bracch_code", Branch_Code);
                objresponse.put("mobile", "");
                objresponse.put("email", "");
                objresponse.put("patient_code", "");
                objresponse.put("age", "");
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getsearchlistcode(final String searchstringvalue) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEWSEARCHPATIENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        try {
                            if (contactDetails1.size() > 0) {
                                contactDetails1.clear();
                            }
                            Log.e("result", response);
                            if (progressDialog != null) {
                                progressDialog.hide();
                            }
//                            new UrlConnectionTask(getActivity(), ApiConfig.VIEW_PATIENT_URL, ApiConfig.ID1, true, objresponse, UserListModel.class, this).execute("");                            else {

                            JSONObject jsonObject1 = new JSONObject(response);
                            /*String success=jsonObject1.getString("success");
                            if (success.equals("true")){
                                Log.e("success",response);*/
                            JSONArray jsonArray = jsonObject1.getJSONArray("result");
                            for (int i = jsonArray.length() - 1; i >= 0; i--) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String status = jsonObject.getString("status");
                                if (status.equals("1")) {
                                    Log.e("json", jsonObject.toString());
                                    String id = jsonObject.getString("id");
                                    String first_name = jsonObject.getString("first_name");
                                    String last_name = jsonObject.getString("last_name");
                                    String email = jsonObject.getString("email");
                                    String profile_pic = jsonObject.getString("profile_pic");


                                    Detailapp1 = new UserItem();
                                    Detailapp1.setName(first_name + " " + last_name);
                                    Detailapp1.setEmail(email);
                                    Detailapp1.setId(id);
                                    Detailapp1.setProfilePic(profile_pic);
                                    contactDetails1.add(Detailapp1);

                                    mRecyclerView.setAdapter(mAdapter1);
//                                    Collections.reverse(contactDetails1);

//                                    Log.e("contactDetails1",mList.toString());

                                } else {
                                    contactDetails1.clear();

                                    mRecyclerView.setAdapter(mAdapter1);
                                }
//                                mAdapter1 = new Patient_listAdapter(contactDetails1, getActivity());

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.e("Postdat", "" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                    }
                }) {


            protected Map<String, String> getParams() {
                objresponse = new HashMap<String, String>();
                objresponse.put("first_name", "");
                objresponse.put("mobile", "");
                objresponse.put("email", "");
                objresponse.put("patient_code", searchstringvalue);
                objresponse.put("age", "");
                objresponse.put("bracch_code", Branch_Code);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getsearchlistMobile(final String searchstringvalue) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEWSEARCHPATIENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        try {
                            if (contactDetails1.size() > 0) {
                                contactDetails1.clear();
                            }
                            Log.e("result", response);
                            if (progressDialog != null) {
                                progressDialog.hide();
                            }
                            JSONObject jsonObject1 = new JSONObject(response);
                            /*String success=jsonObject1.getString("success");
                            if (success.equals("true")){
                                Log.e("success",response);*/
                            JSONArray jsonArray = jsonObject1.getJSONArray("result");
                            for (int i = jsonArray.length() - 1; i >= 0; i--) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String status = jsonObject.getString("status");
                                if (status.equals("1")) {
                                    Log.e("json", jsonObject.toString());
                                    String id = jsonObject.getString("id");
                                    String first_name = jsonObject.getString("first_name");
                                    String last_name = jsonObject.getString("last_name");
                                    String email = jsonObject.getString("email");
                                    String profile_pic = jsonObject.getString("profile_pic");


                                    Detailapp1 = new UserItem();
                                    Detailapp1.setName(first_name + " " + last_name);
                                    Detailapp1.setEmail(email);
                                    Detailapp1.setId(id);
                                    Detailapp1.setProfilePic(profile_pic);
                                    contactDetails1.add(Detailapp1);

                                    mRecyclerView.setAdapter(mAdapter1);
//                                    Collections.reverse(contactDetails1);

//                                    Log.e("contactDetails1",mList.toString());

                                } else {
                                    contactDetails1.clear();

                                    mRecyclerView.setAdapter(mAdapter1);
                                }
//                                mAdapter1 = new Patient_listAdapter(contactDetails1, getActivity());

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.e("Postdat", "" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                    }
                }) {


            protected Map<String, String> getParams() {
                objresponse = new HashMap<String, String>();
                objresponse.put("first_name", "");
                objresponse.put("mobile", searchstringvalue);
                objresponse.put("email", "");
                objresponse.put("patient_code", "");
                objresponse.put("age", "");
                objresponse.put("bracch_code", Branch_Code);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getsearchlistEmail(final String searchstringvalue) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEWSEARCHPATIENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        try {
                            if (contactDetails1.size() > 0) {
                                contactDetails1.clear();
                            }
                            Log.e("result", response);
                            if (progressDialog != null) {
                                progressDialog.hide();
                            }
                            JSONObject jsonObject1 = new JSONObject(response);

                            JSONArray jsonArray = jsonObject1.getJSONArray("result");
                            for (int i = jsonArray.length() - 1; i >= 0; i--) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String status = jsonObject.getString("status");
                                if (status.equals("1")) {
                                    Log.e("json", jsonObject.toString());
                                    String id = jsonObject.getString("id");
                                    String first_name = jsonObject.getString("first_name");
                                    String last_name = jsonObject.getString("last_name");
                                    String email = jsonObject.getString("email");
                                    String profile_pic = jsonObject.getString("profile_pic");


                                    Detailapp1 = new UserItem();
                                    Detailapp1.setName(first_name + " " + last_name);
                                    Detailapp1.setEmail(email);
                                    Detailapp1.setId(id);
                                    Detailapp1.setProfilePic(profile_pic);
                                    contactDetails1.add(Detailapp1);

                                    mRecyclerView.setAdapter(mAdapter1);
//                                    Collections.reverse(contactDetails1);

//                                    Log.e("contactDetails1",mList.toString());

                                } else {
                                    contactDetails1.clear();

                                    mRecyclerView.setAdapter(mAdapter1);
                                }
//                                mAdapter1 = new Patient_listAdapter(contactDetails1, getActivity());

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.e("Postdat", "" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                    }
                }) {


            protected Map<String, String> getParams() {
                objresponse = new HashMap<String, String>();
                objresponse.put("first_name", "");
                objresponse.put("mobile", "");
                objresponse.put("email", searchstringvalue);
                objresponse.put("patient_code", "");
                objresponse.put("age", "");
                objresponse.put("bracch_code", Branch_Code);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    private void getpatientlist() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_PATIENT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (contactDetails1.size() > 0) {
                                contactDetails1.clear();
                            }
                            Log.d(TagUtils.getTag(), "patient list:-" + response);
                            if (progressDialog != null) {
                                progressDialog.hide();
                            }
//                            new UrlConnectionTask(getActivity(), ApiConfig.VIEW_PATIENT_URL, ApiConfig.ID1, true, objresponse, UserListModel.class, this).execute("");                            else {

                            JSONObject jsonObject1 = new JSONObject(response);
                            /*String success=jsonObject1.getString("success");
                            if (success.equals("true")){
                                Log.e("success",response);*/
                            JSONArray jsonArray = jsonObject1.getJSONArray("result");
                            for (int i = jsonArray.length() - 1; i >= 0; i--) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String status = jsonObject.getString("status");
                                if (status.equals("1")) {
                                    Log.e("json", jsonObject.toString());
                                    String id = jsonObject.getString("id");
                                    String first_name = jsonObject.getString("first_name");
                                    String last_name = jsonObject.getString("last_name");
                                    String email = jsonObject.getString("email");
                                    String profile_pic = jsonObject.getString("profile_pic");
                                    String bracch_code = jsonObject.getString("bracch_code");


                                    Detailapp1 = new UserItem();
                                    Detailapp1.setName(first_name + " " + last_name);
                                    Detailapp1.setEmail(email);
                                    Detailapp1.setId(id);
                                    Detailapp1.setProfilePic(profile_pic);
                                    Detailapp1.setBracch_code(bracch_code);
                                    contactDetails1.add(Detailapp1);

                                    mRecyclerView.setAdapter(mAdapter1);
//                                    Collections.reverse(contactDetails1);

//                                    Log.e("contactDetails1",mList.toString());

                                } else {
                                    contactDetails1.clear();

                                    mRecyclerView.setAdapter(mAdapter1);
                                }
//                                mAdapter1 = new Patient_listAdapter(contactDetails1, getActivity());

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.e("Postdat", "" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                    }
                }) {


            protected Map<String, String> getParams() {
                objresponse = new HashMap<String, String>();
                objresponse.put(ApiConfig.PATIENT_TYPE_TO_VIEW_PATIENT, value);
                objresponse.put("added_by", Branch_Code);
                return objresponse;
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
            String content = HttpULRConnect.getData(ApiConfig.GetURL);
            Log.e("DoInBackGround ---->", String.valueOf(content));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                Log.e("DoInBackGroundtr", String.valueOf(s));
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String branch_name = jsonObject2.getString("branch_name");
                    String bracch_code = jsonObject2.getString("branch_code");

                    detailApps = new InfoApps1();
                    detailApps.setName(branch_name);
                    detailApps.setId(bracch_code);
                    arrayList.add(detailApps.getName() + "  " + "(" + detailApps.getId() + ")");
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getActivity(), android.R.layout.simple_dropdown_item_1line, arrayList);
                    spinnerbranchloca.setAdapter(spinnerArrayAdapter);

                }

            } catch (Exception e) {
                Log.e("error", e.toString());

            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        deleteAlert(mList.get(position).getId());
    }

    private void initProgressDialog(String loading) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(loading);
        progressDialog.setCancelable(false);
        try {
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("start", "onStart");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Dashboard.");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("start", "onStart");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Patient List");
    }

    @Override
    public void onBackPressed() {
        Log.e("onBack", "onBackPressed");
        super.onBackPressed();
        getFragmentManager().popBackStack();
    }
}