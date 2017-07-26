package com.capri4physio.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.capri4physio.R;
import com.capri4physio.Services.GetWebServices;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.adapter.AppointmentAdapter;
import com.capri4physio.model.appointment.AppointmentPOJO;
import com.capri4physio.model.appointment.AppointmentResultPOJO;
import com.capri4physio.model.branch.BranchPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
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

public class ViewAllAppointmentsActivity extends AppCompatActivity implements WebServicesCallBack {
    private static final String DELETE_APPOINTMENT_API = "delete_appointment_api";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_appointments)
    RecyclerView rv_appointments;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    String branch_code = "";
    private static final String GET_ALL_BRANCHES = "get_all_branches";
    private final String GET_APPOINTMENTS = "get_appointments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_appointments);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if(AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")) {
            getAllBranches();
        }else{
            spinner_branch.setVisibility(View.GONE);
            branch_code=AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
            getAllAppointments();
        }

    }

    public void getAllBranches() {
        new GetWebServices(this, GET_ALL_BRANCHES).execute(ApiConfig.GetURL);
    }

    public void getAllBranches(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            final List<BranchPOJO> list_branches = new ArrayList<>();
            final List<String> branchLists = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                BranchPOJO branch_pojo = new BranchPOJO(jsonObject.optString("branch_id"),
                        jsonObject.optString("branch_name"),
                        jsonObject.optString("branch_code"),
                        jsonObject.optString("branch_status"));
                branchLists.add(branch_pojo.getBranch_name() + " (" + branch_pojo.getBranch_code() + ")");
                list_branches.add(branch_pojo);
            }
            if (branchLists.size() > 0) {
                ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, branchLists);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                branch_code = list_branches.get(0).getBranch_code();
                spinner_branch.setAdapter(aa);
                spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        branch_code = list_branches.get(position).getBranch_code();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                getAllAppointments();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
    protected void onResume() {
        super.onResume();

    }

    int position;

    public void cancelAppointment(String id, int position) {
        this.position = position;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id", id));
        new WebServiceBase(nameValuePairs, this, DELETE_APPOINTMENT_API).execute(ApiConfig.delete_appointment_api);
    }

    public void getAllAppointments() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("branch_code", branch_code));
        new WebServiceBase(nameValuePairs, this, GET_APPOINTMENTS).execute(ApiConfig.view_all_appointments);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GET_APPOINTMENTS:
                parseAppointments(response);
                break;
            case DELETE_APPOINTMENT_API:
                parseDeleteAppointment(response);
                break;
            case GET_ALL_BRANCHES:
                getAllBranches(response);
                break;
        }
    }

    public void parseDeleteAppointment(String response) {
        Log.d(TagUtils.getTag(), "response:-" + response);
        try {
            if (new JSONObject(response).optString("success").equals("true")) {
                appointmentList.remove(position);
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

    List<AppointmentResultPOJO> appointmentList;
    AppointmentAdapter adapter;

    public void parseAppointments(String response) {
        try {
            Gson gson = new Gson();
            AppointmentPOJO appointmentPOJO = gson.fromJson(response, AppointmentPOJO.class);
            if (appointmentPOJO.getSuccess().equals("true")) {
                appointmentList = appointmentPOJO.getAppointmentResultPOJOList();
                adapter = new AppointmentAdapter(this, appointmentList);
                Collections.reverse(appointmentPOJO.getAppointmentResultPOJOList());
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_appointments.setLayoutManager(horizontalLayoutManagaer);
                rv_appointments.setHasFixedSize(true);
                rv_appointments.setItemAnimator(new DefaultItemAnimator());
                rv_appointments.setAdapter(adapter);
            } else {
                ToastClass.showShortToast(getApplicationContext(), "No Appointments Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
