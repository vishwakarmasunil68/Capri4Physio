package com.capri4physio.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.adapter.DoctorAppointmentAdapter;
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

public class ManageAppointmentActivity extends AppCompatActivity implements WebServicesCallBack{
    private static final String GET_BOOKED_APPOINTMENTS = "get_booked_appointments";
    private static final String DELETE_APPOINTMENT_API = "delete_appointment_api";
    private static final String UPDATE_APPOINTMENT = "update_appointments";
    @BindView(R.id.rv_manage_appointment)
    RecyclerView rv_manage_appointment;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_appointment);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        callBookedAppointmentsAPI(AppPreferences.getInstance(getApplicationContext()).getUserID());
    }
    int position;
    public void cancelAppointment(String id,String patient_id, int position) {
        this.position = position;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id", id));
        nameValuePairs.add(new BasicNameValuePair("patient_id", patient_id));
        new WebServiceBase(nameValuePairs, this, DELETE_APPOINTMENT_API).execute(ApiConfig.delete_appointment_api);
    }

    public void callBookedAppointmentsAPI(String doctor_id){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("doctor_id", doctor_id));
        new WebServiceBase(nameValuePairs, this, GET_BOOKED_APPOINTMENTS).execute(ApiConfig.get_appointments_by_doctor_id);
    }
    TextView tv_status,tv_confirm;
    public void updateAppointmentStatus(NewAppointmentResultPOJO newAppointmentResultPOJO, TextView tv_status,TextView tv_confirm){
        this.tv_confirm=tv_confirm;
        this.tv_status=tv_status;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("appointment_id", newAppointmentResultPOJO.getId()));
        nameValuePairs.add(new BasicNameValuePair("patient_id", newAppointmentResultPOJO.getPatientId()));
        nameValuePairs.add(new BasicNameValuePair("status", "1"));
        new WebServiceBase(nameValuePairs, this, UPDATE_APPOINTMENT).execute(ApiConfig.update_appointment);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case GET_BOOKED_APPOINTMENTS:
                parseBookedAppointments(response);
                break;
            case DELETE_APPOINTMENT_API:
                parseDeleteAppointment(response);
                break;
            case UPDATE_APPOINTMENT:
                parseUpdateAPpointment(response);
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter(StringUtils.BOOKED_APPOINTMENT));
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
                callBookedAppointmentsAPI(AppPreferences.getInstance(getApplicationContext()).getUserID());
            } catch (Exception e) {
                Log.d(TagUtils.getTag(), e.toString());
            }
        }
    };

    public void parseUpdateAPpointment(String response){
        Log.d(TagUtils.getTag(),"update response:-"+response);
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("success").equals("true")){
                tv_status.setText("Appointment Confirmed");
                tv_confirm.setVisibility(View.GONE);
                ToastClass.showShortToast(getApplicationContext(),"Appointment Confirmed");
            }else{
                ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
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
                ToastClass.showShortToast(getApplicationContext(), "Failed to delete Appointment");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(), "Something went wrong");
        }
    }

    List<NewAppointmentResultPOJO> newAppointmentResultPOJOList;
    DoctorAppointmentAdapter adapter;
    public void parseBookedAppointments(String response){
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
                adapter = new DoctorAppointmentAdapter(this, newAppointmentResultPOJOList);
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_manage_appointment.setLayoutManager(horizontalLayoutManagaer);
                rv_manage_appointment.setHasFixedSize(true);
                rv_manage_appointment.setItemAnimator(new DefaultItemAnimator());
                rv_manage_appointment.setAdapter(adapter);

            } else {
                ToastClass.showShortToast(getApplicationContext(), "No Appointments Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}