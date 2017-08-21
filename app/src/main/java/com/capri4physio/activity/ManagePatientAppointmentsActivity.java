package com.capri4physio.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.adapter.PatientAppointmentAdapter;
import com.capri4physio.model.newappointment.NewAppointmentResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.StringUtils;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagePatientAppointmentsActivity extends AppCompatActivity implements WebServicesCallBack {
    private static final String DELETE_APPOINTMENT_API = "delete_appointment_api";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_appointments)
    RecyclerView rv_appointments;

    public static final String GET_ALL_APPOINTMENTS = "get_all_appointments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_patient_appointments);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        callApplicationFormApi();
    }

    public void callApplicationFormApi() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("patient_id", AppPreferences.getInstance(getApplicationContext()).getUserID()));
        new WebServiceBase(nameValuePairs, this, GET_ALL_APPOINTMENTS).execute(ApiConfig.get_appointments_by_patients);
    }

    int position;

    public void cancelAppointment(String id, int position) {
        this.position = position;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id", id));
        new WebServiceBase(nameValuePairs, this, DELETE_APPOINTMENT_API).execute(ApiConfig.delete_appointment_api);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter(StringUtils.APPOINTMENT_CONFIRMED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        getApplicationContext().unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String result = intent.getStringExtra("message");
            Log.d(TagUtils.getTag(), "message:-" + result);
            try {
                callApplicationFormApi();
            } catch (Exception e) {
                Log.d(TagUtils.getTag(), e.toString());
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GET_ALL_APPOINTMENTS:
                parseAllAppointments(response);
                break;
            case DELETE_APPOINTMENT_API:
                parseDeleteAppointment(response);
                break;
        }
    }

    public void parseDeleteAppointment(String response) {
        Log.d(TagUtils.getTag(), "response:-" + response);
        try {
            if (new JSONObject(response).optString("success").equals("true")) {
                newAppointmentResultPOJOList.remove(position);
                adapter.notifyDataSetChanged();
                ToastClass.showShortToast(getApplicationContext(), "Appointment deleted");
            } else {
                ToastClass.showShortToast(getApplicationContext(), "Failed to delet Appointment");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(), "Something went wrong");
        }
    }

    List<NewAppointmentResultPOJO> newAppointmentResultPOJOList;
    PatientAppointmentAdapter adapter;
    public void parseAllAppointments(String response) {
        Log.d(TagUtils.getTag(), "appointments response:-" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("Success").equals("true")) {
                JSONArray jsonArray = jsonObject.optJSONArray("Result");
                newAppointmentResultPOJOList = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    Gson gson1 = new Gson();
                    NewAppointmentResultPOJO newAppointmentResultPOJO = gson1.fromJson(jsonArray.optJSONObject(i).toString(), NewAppointmentResultPOJO.class);
                    newAppointmentResultPOJOList.add(newAppointmentResultPOJO);
                }
                Collections.reverse(newAppointmentResultPOJOList);
                adapter = new PatientAppointmentAdapter(this, newAppointmentResultPOJOList);
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_appointments.setLayoutManager(horizontalLayoutManagaer);
                rv_appointments.setHasFixedSize(true);
                rv_appointments.setItemAnimator(new DefaultItemAnimator());
                rv_appointments.setAdapter(adapter);

            } else {
                newAppointmentResultPOJOList = new ArrayList<>();
                adapter = new PatientAppointmentAdapter(this, newAppointmentResultPOJOList);
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_appointments.setLayoutManager(horizontalLayoutManagaer);
                rv_appointments.setHasFixedSize(true);
                rv_appointments.setItemAnimator(new DefaultItemAnimator());
                rv_appointments.setAdapter(adapter);
                ToastClass.showShortToast(getApplicationContext(), "No Appointments Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
