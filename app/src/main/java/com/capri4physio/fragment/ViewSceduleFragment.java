package com.capri4physio.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.fragment.assessment.AddAppointments;
import com.capri4physio.fragment.assessment.GetLUserList;
import com.capri4physio.fragment.assessment.GetLUserList1;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.fragment.assessment.InfoApps;
import com.capri4physio.fragment.assessment.ViewEnquiryList;
import com.capri4physio.util.AppPreferences;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Emobi-Android-002 on 7/13/2016.
 */
public class ViewSceduleFragment extends BaseFragment{
    String GetURL= "http://www.caprispine.in/users/getuserlist";
    public static String userid;
    String indexing;
    InfoApps Detailapp;
    public static ArrayList<String> staffarray,patientaray;
    public static ArrayList<String> patientidarray ;
    TextView add,manage,cancel;
    CardView enuiry;
    CardView changeclinic;
    public static ViewSceduleFragment newInstance() {
        ViewSceduleFragment fragment = new ViewSceduleFragment();
        return fragment;
    }
    public ViewSceduleFragment() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_schedule, container, false);
        add=(TextView)rootView.findViewById(R.id.addnew);
        patientidarray =new ArrayList<String>();
        patientaray =new ArrayList<String>();
        staffarray =new ArrayList<String>();
        new CatagoryUrlAsynTask().execute();
        manage=(TextView)rootView.findViewById(R.id.managenew);
        cancel=(TextView)rootView.findViewById(R.id.cancelnew);
        enuiry=(CardView)rootView.findViewById(R.id.main_3);
        changeclinic=(CardView)rootView.findViewById(R.id.main_4);

        if (AppPreferences.getInstance(getActivity()).getUserType().equals("0")) {
            add.setVisibility(View.INVISIBLE);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.cusotm_dialogoptappo);
                dialog.setTitle("Select -type");
                LinearLayout OPD = (LinearLayout) dialog.findViewById(R.id.OPD);
                dialog.setCancelable(true);
                LinearLayout Homevisit = (LinearLayout) dialog.findViewById(R.id.hvisit);

                Homevisit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        indexing="false";
                        dialog.dismiss();
                        viewPatient(indexing);
                    }
                });

                OPD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        indexing="true";
                        dialog.dismiss();
                        viewPatient(indexing);
                    }
                });
                dialog.show();
            }
        });

        enuiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ViewEnquiryList.class));
            }
        });
        changeclinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.cusotm_dialogopt);
                dialog.setTitle("Select -type");
                LinearLayout OPD = (LinearLayout) dialog.findViewById(R.id.OPD);
                dialog.setCancelable(true);
                LinearLayout Homevisit = (LinearLayout) dialog.findViewById(R.id.hvisit);


                Homevisit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        indexing="Opd";
                        dialog.dismiss();
                        viewPatient();
                    }
                });

                OPD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        indexing="Home Patient";
                        dialog.dismiss();
                        viewPatient();
                    }
                });
                dialog.show();

            }
        });

        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppPreferences.getInstance(getActivity()).getUserType().equals("4")) {
                    startActivity(new Intent(getActivity(), GetLUserList.class));
                }
                else {
                    startActivity(new Intent(getActivity(), GetLUserList.class));
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), GetLUserList1.class));
            }
        });


        /*initView(rootView);
        setListener();
        viewStaffApiCall();*/
        return rootView;
    }

    private void viewPatient() {
        Intent intent =new Intent(getActivity(),ChangePatientClinic.class);
        intent.putExtra("indexing",indexing);
        startActivity(intent);
    }

    private void viewPatient(String edit_datefrom_) {
        Intent intent =new Intent(getActivity(),AddAppointments.class);
        intent.putExtra("edit_datefrom_",edit_datefrom_);
        startActivity(intent);
    }

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
                        Detailapp = new InfoApps();
                        Detailapp.setName(username);
                        Detailapp.setNumber(userid);
                        patientaray.add(username);
                        patientidarray.add(userid);
                        Log.e("patientaray",patientaray.toString());
                    }
                    else if (usertype.equals("1")) {
                        staffarray.add(username);
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
        actionBar.setTitle("Schedule");
    }

    @Override
    public void onPause() {
        super.onPause();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Dashboard");
    }
}
