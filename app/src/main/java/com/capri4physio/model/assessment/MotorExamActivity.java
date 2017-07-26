package com.capri4physio.model.assessment;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.capri4physio.R;
import com.capri4physio.adapter.UsersAdapter;
import com.capri4physio.adapter.assessment.MotorExamAdapter;
import com.capri4physio.fragment.assessment.AddMotorExamFragment;
import com.capri4physio.fragment.assessment.AddPhysicalExamFragment;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.fragment.assessment.InfoApps;
import com.capri4physio.fragment.assessment.LocationAdapter5;
import com.capri4physio.fragment.assessment.MotorAdapter;
import com.capri4physio.model.UserItem;
import com.capri4physio.net.ApiConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emobi-Android-002 on 7/30/2016.
 */
public class MotorExamActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    public static ArrayList<InfoApps> contactDetails1;
    ListView listView;
    private UsersAdapter mAdapter;
    private List<UserItem> mList;
    private int itemPosition;
    private Button mAdd;
    InfoApps Detailapp;
    ArrayList<String> stringArrayList;
    private ProgressDialog pDialog;
    MotorAdapter motorAdapter;
    private String patientId = "";
    private String assessmentType = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moterexam);
        stringArrayList = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.list);
        mAdd= (Button) findViewById(R.id.btn_add);
        mList = new ArrayList<>();
        contactDetails1 = new ArrayList<InfoApps>();
        new CatagoryUrlAsynTask().execute();
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment();
            }
        });
    }

    private void addFragment() {
        startActivity(new Intent(getApplicationContext(), AddMotorExamFragment.class));
        /*FragmentTransaction ft = getFragmentManager().beginTransaction();
        AddMotorExamFragment addComplaintFragment = AddMotorExamFragment.newInstance(patientId, assessmentType);
        ft.replace(R.id.fragment_container, addComplaintFragment);
        ft.addToBackStack(null);
        ft.commit();*/
    }

    private class CatagoryUrlAsynTask extends AsyncTask<String, String, String> {
        String id, catagoryName;

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            initProgressDialog("Please wait...");
        }
        @Override
        protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
            String content = HttpULRConnect.getData(ApiConfig.MOTER_EXAM_VIEW);
            Log.e("DoInBackGround ---->", String.valueOf(content));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();
            try {
                Log.e("DoInBackGroundtr", String.valueOf(s));
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray jsonArray = new JSONArray(s);
                Log.e("Post Method", jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String patient_id = jsonObject2.getString("patient_id");
                    String  moter_exam_date = jsonObject2.getString("moter_exam_date");
                    String cervsp_flex = jsonObject2.getString("cervsp_flex");
                    String cervsp_exten = jsonObject2.getString("cervsp_exten");
                    String cervsp_side_flex_left = jsonObject2.getString("cervsp_side_flex_left");
                    String cervsp_side_flex_right = jsonObject2.getString("cervsp_side_flex_right");
                    String cervsp_rotation_left = jsonObject2.getString("cervsp_rotation_left");
                    String cervsp_rotation_right=jsonObject2.getString("cervsp_rotation_right");
                    stringArrayList.add(patient_id);
                    Log.e("stringArrayList", stringArrayList.toString());
                    Detailapp = new InfoApps();
                    Detailapp.setPatientid(patient_id);
//                    Detailapp.setData(cervsp_exten);
                    Detailapp.setNumber(moter_exam_date);
                    Detailapp.setStr4(cervsp_side_flex_left);
                    Detailapp.setAppname(cervsp_side_flex_right);
                    Detailapp.setName(cervsp_rotation_left);
                    Detailapp.setExp(cervsp_rotation_right);
                    /*Detailapp.setStr4(semail);//date
                    Detailapp.setId(id);
                    Detailapp.setNumber(smobile);//startTime*/
                    /*Detailapp.setAppname(reason);//reason
                    Detailapp.setDataAdd(patientname);
                    Detailapp.setPatientid(patientid);
                    Detailapp.setDoctorid(getuniqueid);*/
                    contactDetails1.add(Detailapp);
                    motorAdapter = new MotorAdapter(MotorExamActivity.this, R.layout.moterview);
                    listView.setAdapter(motorAdapter);
                        /*Detailapp1 = new InfoApps();
                        Detailapp1.setName(username);*/
                        /*Detailapp.setAppname(trnsamount);
                        Detailapp.setDataAdd(trnsamounttype);*/
                        /*Detailapp.setName(username);
                        contactDetails.add(Detailapp);
                        contactAdapter = new LocationAdapter4(getApplicationContext(), R.layout.contactlisadap1);
                        contactList.setAdapter(contactAdapter);
                        Toast.makeText(getApplicationContext(),contactDetails.toString(),Toast.LENGTH_LONG).show();*/
//                        Toast.makeText(getApplicationContext(),userid,Toast.LENGTH_LONG).show();
//                        int value = jsonObject1.getInt(i + 1);
                    /*contactAdapter = new LocationAdapter4(getApplicationContext(), R.layout.contactlisadap1);
                    contactList.setAdapter(contactAdapter);*/
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    private void initProgressDialog(String loadingText) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(loadingText);
        pDialog.setCancelable(false);
        pDialog.show();
    }
}
