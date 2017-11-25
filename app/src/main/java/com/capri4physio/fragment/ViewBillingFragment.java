package com.capri4physio.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.activity.CreateInvoiceActivity;
import com.capri4physio.activity.ViewInvoiceActivity;
import com.capri4physio.adapter.UsersAdapter;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.fragment.assessment.InfoApps;
import com.capri4physio.model.UserItem;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewBillingFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author  prabhunathy
 * @version 1.0
 * @since   2016-03-31
 */
public class ViewBillingFragment extends BaseFragment {
    LinearLayout linearLayout, linearLayout1;
    String GetURL = ApiConfig.BASE_URL+"users/getuserlist";
    public static String userid;
    String admingst_tax;
    Button btn;
    Dialog dialog;
    public static final int TIME_DIALOG_ID = 1111;
    private ProgressDialog pDialog;
    public static TextView p_nametext, bill_amounttext, paid_amounttext, due_amounttext, payment_modetext, statustext, bill_numbertext, bill_datetext, pctxet;
    public static EditText bill_amount, paid_amount, due_amount, payment_mode, status, bill_number, bill_date, pc, gender, address, city, Designation, send_date;
    InfoApps Detailapp;
    Spinner p_name, staff_name;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    public static ArrayList<String> patientaray;
    public static ArrayList<String> patientidarray = new ArrayList<String>();
    TextView add, manage, staffmanagenew;
    ImageView view,edit_gst;
    private RecyclerView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    private UsersAdapter mAdapter;
    private List<UserItem> mList;
    private int itemPosition;
    public static ArrayList<String> staffid ;
    public static ArrayList<InfoApps> staffarray,patientspinneraray;
    RelativeLayout rl_create_invoice,rl_view_invoice;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static ViewBillingFragment newInstance() {
        ViewBillingFragment fragment = new ViewBillingFragment();
        return fragment;
    }

    public ViewBillingFragment() {
        // Required empty public constructor
    }

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
        View rootView = inflater.inflate(R.layout.billing, container, false);
        add = (TextView) rootView.findViewById(R.id.addnew);
        manage = (TextView) rootView.findViewById(R.id.viewnew);
        view = (ImageView) rootView.findViewById(R.id.viewgst);
        rl_view_invoice = (RelativeLayout) rootView.findViewById(R.id.rl_view_invoice);
        edit_gst = (ImageView) rootView.findViewById(R.id.edit_gst);
        rl_create_invoice = (RelativeLayout) rootView.findViewById(R.id.rl_create_invoice);
        patientspinneraray = new ArrayList<InfoApps>();
        staffarray = new ArrayList<InfoApps>();
        patientidarray = new ArrayList<>();
        staffid = new ArrayList<>();

        new CatagoryUrlAsynTask2().execute();
        new CatagoryUrlAsynTask().execute();
        new CatagoryUrlAsynTask1().execute();
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                viewbilling();
//                startActivity(new Intent(getActivity(), BillingActivity.class));
//            }
//        });
//        manage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), ViewInvoice.class);
//                startActivity(i);
//            }
//        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.dialog_edit_casenotes);
                dialog.setTitle("View -  GST");

                final  EditText editTextcontents;
                //adding text dynamically
                editTextcontents= (EditText) dialog.findViewById(R.id.edtxt_old_note);
                editTextcontents.setText(admingst_tax+"%");
//                editTextcontents.setHint("GST");
                editTextcontents.setFocusable(false);

                //adding button click event
                Button dismissButton = (Button) dialog.findViewById(R.id.btn_save);
                dismissButton.setText("OKAY");
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });

        edit_gst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.dialog_edit_casenotes);
                dialog.setTitle("Edit -  GST");

                final  EditText editTextcontents;
                //adding text dynamically
                editTextcontents= (EditText) dialog.findViewById(R.id.edtxt_old_note);
                editTextcontents.setText(admingst_tax+"%");


                //adding button click event
                Button dismissButton = (Button) dialog.findViewById(R.id.btn_save);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getpnotes(editTextcontents.getText().toString());

                    }
                });
                dialog.show();
            }
        });

        rl_create_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateInvoiceActivity.class));
            }
        });
        rl_view_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ViewInvoiceActivity.class));
            }
        });

        /*setListener();
        viewStaffApiCall();*/
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        /*mSnackBarLayout = (CoordinatorLayout)view.findViewById(R.id.coordinator_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);*/
    }

    @Override
    protected void setListener() {
        super.setListener();
    }



    private class CatagoryUrlAsynTask1 extends AsyncTask<String, String, String> {
        String id, catagoryName;

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            try {
//                initProgressDialog("Please wait...");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
            String content = HttpULRConnect.getData(ApiConfig.VIEW_STAFF);
            Log.e("DoInBackGround ---->", String.valueOf(content));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
//                pDialog.dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                Log.e("DoInBackGroundtr", String.valueOf(s));
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray jsonArray = new JSONArray(s);
                Log.e("Post Method", jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String id = jsonObject2.getString("id");
                    String smobile = jsonObject2.getString("mobile");
                    String semail = jsonObject2.getString("email");
                    String sfirst_name = jsonObject2.getString("first_name");
                    String dob = jsonObject2.getString("dob");
                    String age = jsonObject2.getString("age");
                   /* String doj = jsonObject2.getString("sdatejoing");
                    String senddate = jsonObject2.getString("senddate");
                    String gen = jsonObject2.getString("sgender");
                    String marital_status = jsonObject2.getString("smarital_status");
                    String desig = jsonObject2.getString("sdesignation");
                    String addr = jsonObject2.getString("saddress");
                    String city = jsonObject2.getString("scity");
                    String pin_code = jsonObject2.getString("spincode");
                    String qual = jsonObject2.getString("squalifation");
                    String exp = jsonObject2.getString("sexprience");*/


                    staffid.add(id);

                    Detailapp = new InfoApps();
                    Detailapp.setName(sfirst_name);
                    Detailapp.setStr4(semail);//date
                    Detailapp.setId(id);
                    Detailapp.setNumber(smobile);//startTime
                    Detailapp.setDob(dob);//reason
                    Detailapp.setAge(age);
                    Detailapp.setDoj("");
                    Detailapp.setSend_date("");
                    Detailapp.setGender("");
                    Detailapp.setMarital_status("");
                    Detailapp.setDesig("");
                    Detailapp.setDataAdd("");
                    Detailapp.setCity("");
                    Detailapp.setPc("");
                    Detailapp.setQualification("");
                    Detailapp.setExp("");
                    staffarray.add(Detailapp);
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
            //  CatagoryMetod();
        }
    }


    private void getpnotes(final String textView){



//        final String casedesc =editTextcontents.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.EDITGST  ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            dialog.dismiss();
                            getFragmentManager().popBackStack();

                        }

                        catch (Exception e) {
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
                }){


            protected Map<String,String> getParams(){
                Map<String,String> objresponse = new HashMap<String, String>();
                objresponse.put("admingst_tax", textView);
                objresponse.put("admingst_id", AppPreferences.getInstance(getActivity()).getUserID());
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    private class CatagoryUrlAsynTask2 extends AsyncTask<String, String, String> {
        String id, catagoryName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            initProgressDialog("Please Wait..");
        }

        @Override
        protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
            String content = HttpULRConnect.getData(ApiConfig.BASE_URL+"users/admingstlist");
            Log.e("DoInBackGround ---->", String.valueOf(content));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            try {

                Log.e("DoInBackGroundtr", String.valueOf(s));
//                pDialog.dismiss();
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray jsonArray = new JSONArray(s);
                Log.e("Post Method", jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    admingst_tax = jsonObject2.getString("admingst_tax");
                }

            }catch(Exception e){
                e.printStackTrace();

            }
            //  CatagoryMetod();
        }}


    private class CatagoryUrlAsynTask extends AsyncTask<String, String, String> {
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
                JSONObject jsonObject = new JSONObject(s);
                JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                Log.e("Post Method", jsonObject1.toString());
                for (int i = 1; i <= jsonObject1.length(); i++) {

                    JSONObject jsonObject2 = jsonObject1.getJSONObject(i + "");
                    Log.e("2", jsonObject2.toString());
                    userid = jsonObject2.getString("userid");
                    String username = jsonObject2.getString("username");
                    String usertype=jsonObject2.getString("usertype");

                    if (usertype.equals("0")){
                        String username1 = jsonObject2.getString("username");
                        String userid = jsonObject2.getString("userid");
                        Detailapp = new InfoApps();
                        Detailapp.setName(username1);
                        Detailapp.setNumber(userid);
                        patientspinneraray.add(Detailapp);
//                        patientidarray.add(userid);
                        Log.e("patientaray",patientspinneraray.toString());
                    }
                    else if (usertype.equals("1")) {
                        String user = jsonObject2.getString("username");
//                        staffarray.add(user);
                    }
                    /*else if (usertype.equals("1")){
                        staffaray.add(username);
                        Log.e("staffaray",staffaray.toString());
                    }
                    else if (usertype.equals("2")){
                        dcotoraray.add(username);
                        Log.e("dcotoraray",dcotoraray.toString());
                    }
                    else if (usertype.equals("3")){
                        brancharay.add(username);
                        Log.e("brancharay",brancharay.toString());
                    }*/
//                        int userid = Integer.parseInt(jsonObject2.getInt((i+1) + ""));
//                        int userid=Integer.parseInt(jsonObject2.getString((i+1) + ""));
                        /*Detailapp1 = new InfoApps();
                        Detailapp1.setName(username);*/
                        /*Detailapp.setAppname(trnsamount);
                        Detailapp.setDataAdd(trnsamounttype);*/

//                        Toast.makeText(getApplicationContext(),usertype,Toast.LENGTH_LONG).show();
//                        Toast.makeText(getApplicationContext(),userid,Toast.LENGTH_LONG).show();
//                        int value = jsonObject1.getInt(i + 1);
                }

            }catch(Exception e){
                e.printStackTrace();

            }
            //  CatagoryMetod();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Invoice");
    }

    @Override
    public void onPause() {
        super.onPause();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Dashboard");
    }
    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(loading);
        pDialog.setCancelable(true);
        pDialog.show();
    }
}