package com.capri4physio.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.Services.GetWebServices;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.model.branch.BranchPOJO;
import com.capri4physio.model.doctor.DoctorPOJO;
import com.capri4physio.model.doctor.DoctorResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPatientAppointment extends AppCompatActivity implements WebServicesCallBack, DatePickerDialog.OnDateSetListener {

    private static final String GET_ALL_BRANCHES = "get_all_branches";
    private static final String GET_DOCTORS_API = "get_doctor_api";
    private static final String ADD_APPOINTMENT_API = "add_appointment_api";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    @BindView(R.id.spinner_doctor)
    Spinner spinner_doctor;
    @BindView(R.id.et_reason)
    EditText et_reason;
    @BindView(R.id.et_date)
    EditText et_date;
    @BindView(R.id.rv_appointment_time)
    RecyclerView rv_appointment_time;

    @BindView(R.id.btn_select_date)
    Button btn_select_date;

    List<BranchPOJO> branchPOJOList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient_appointment);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        btn_select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddPatientAppointment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        new GetWebServices(AddPatientAppointment.this, GET_ALL_BRANCHES).execute(ApiConfig.GetURL);
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
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GET_ALL_BRANCHES:
                parseAllBranches(response);
                break;
            case GET_DOCTORS_API:
                parseGetAllDoctors(response);
                break;
            case ADD_APPOINTMENT_API:
                parseAddAppointmentAPI(response);
                break;
        }
    }

    public void parseAddAppointmentAPI(String response){
        Log.d(TagUtils.getTag(),"response:-"+response);
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("status").equals("1")){
                ToastClass.showShortToast(getApplicationContext(),"Appointment Booked Successfully.");
            }else{
                ToastClass.showShortToast(getApplicationContext(),"Failed to book appointment");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
        }
    }

    List<DoctorResultPOJO> doctorResultPOJOList = new ArrayList<>();

    public void parseGetAllDoctors(String response) {
        Log.d(TagUtils.getTag(), "get doctor response:-" + response);
        try {
            Gson gson = new Gson();
            DoctorPOJO doctorPOJO = gson.fromJson(response, DoctorPOJO.class);
            if (doctorPOJO.getSuccess().equals("true")) {
                List<String> doctorStringList = new ArrayList<>();
                doctorResultPOJOList.addAll(doctorPOJO.getDoctorResultPOJOList());
                for (DoctorResultPOJO doctorResultPOJO : doctorResultPOJOList) {
                    doctorStringList.add(doctorResultPOJO.getFirstName() + " " + doctorResultPOJO.getLastName());
                }


                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(), R.layout.dropsimpledown, doctorStringList);
                spinner_doctor.setAdapter(spinnerArrayAdapter);
            } else {
                ToastClass.showShortToast(getApplicationContext(), "No Doctor Found. Please Select another branch");
            }

            showTimings(doctorResultPOJOList.get(0));
            spinner_doctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    showTimings(doctorResultPOJOList.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showTimings(DoctorResultPOJO doctorResultPOJO) {
        if (doctorResultPOJO.getFromTime().length() > 0 && doctorResultPOJO.getToTime().length() > 0) {
            List<String> time_difference_list = getDifference(doctorResultPOJO.getFromTime(), doctorResultPOJO.getToTime());
            HorizontalAdapter adapter = new HorizontalAdapter(this, time_difference_list);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
            rv_appointment_time.setHasFixedSize(true);
            rv_appointment_time.setLayoutManager(layoutManager);
            rv_appointment_time.setAdapter(adapter);
        } else {
            ToastClass.showShortToast(getApplicationContext(), "Please select another doctor");
            List<String> time_difference_list = getDifference("08:00", "22:00");
            Log.d(TagUtils.getTag(),"time differences:-"+time_difference_list.toString());
            HorizontalAdapter adapter = new HorizontalAdapter(this, time_difference_list);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
            rv_appointment_time.setHasFixedSize(true);
            rv_appointment_time.setLayoutManager(layoutManager);
            rv_appointment_time.setAdapter(adapter);
        }
    }


    public static List<String> getDifference(String time1, String time2) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Date date1 = simpleDateFormat.parse(time1);
            Date date2 = simpleDateFormat.parse(time2);
            List<java.sql.Time> intervals = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            intervals.add(new java.sql.Time(cal.getTimeInMillis()));
            while (cal.getTime().before(date2)) {
                cal.add(Calendar.MINUTE, 15);
                intervals.add(new java.sql.Time(cal.getTimeInMillis()));
            }

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            List<String> lisStrings = new ArrayList<>();
            for (java.sql.Time s : intervals) {
//		    System.out.println(sdf.format(s));
                lisStrings.add(sdf.format(s));
            }
            return lisStrings;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void parseAllBranches(String response) {
        branchPOJOList.clear();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                BranchPOJO branchPOJO = new BranchPOJO(jsonObject.optString("branch_id"),
                        jsonObject.optString("branch_name"),
                        jsonObject.optString("branch_code"),
                        jsonObject.optString("branch_status"));
                branchPOJOList.add(branchPOJO);
            }
            List<String> braStringList = new ArrayList<>();
            for (BranchPOJO branchPOJO : branchPOJOList) {
                braStringList.add(branchPOJO.getBranch_name() + " (" + branchPOJO.getBranch_code() + ")");
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.dropsimpledown, braStringList);
            spinner_branch.setAdapter(spinnerArrayAdapter);
            if (branchPOJOList.size() > 0) {
                getbranchdoctors(branchPOJOList.get(0));
            }
            spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    getbranchdoctors(branchPOJOList.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getbranchdoctors(BranchPOJO branchPOJO) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("bracch_code  ", branchPOJO.getBranch_code()));
        new WebServiceBase(nameValuePairs, this, GET_DOCTORS_API).execute(ApiConfig.get_branch_doctor);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "";
        if ((monthOfYear + 1) < 10) {
            date = dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year;
        } else {
            date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        }
        et_date.setText(date);
    }
    List<Integer> list_removed_position = new ArrayList<>();
    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<String> horizontalList;
        Boolean allvalue;
        private Context context;
        Activity activity;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView tv_time;
            public LinearLayout ll_time;

            public MyViewHolder(View view) {
                super(view);
                tv_time = (TextView) view.findViewById(R.id.tv_time);
                ll_time = (LinearLayout) view.findViewById(R.id.ll_time);
                allvalue = false;
            }
        }
        public HorizontalAdapter(Context context, List<String> horizontalList) {
            this.horizontalList = horizontalList;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.enq_time, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.tv_time.setText(horizontalList.get(position));
            holder.ll_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callAddAppointmentAPI(horizontalList.get(position));
                }
            });

//            if (list_removed_position.contains(position)) {
//                holder.tv_time.setTextColor(Color.RED);
//                Log.e(TagUtils.getTag(), "morngtime");
//
//            } else {
//                holder.ll_time.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
////                        methods();
//                        edit_time = horizontalList.get(position);
//                        holder.tv_time.setTextColor(Color.BLACK);
//                        if (edit_reason.getText().toString().length() > 1) {
//                            if (AppPreferences.getInstance(getActivity()).getUSER_BRANCH_CODE().equals("")) {
//                                Toast.makeText(getActivity(), "No clinic Branch added by you", Toast.LENGTH_LONG).show();
//                            } else {
//                                initProgressDialog("Please wait..");
//                                addExpenseApiCall(edit_time);
//                            }
//                        } else {
//                            Toast.makeText(getActivity(), "Please add reason for appointments first", Toast.LENGTH_LONG).show();
//                            edit_reason.requestFocus();
//                        }
//                    }
//
//                    private void methods() {
//                        holder.tv_time.setTextColor(Color.GREEN);
//                    }
//                });
//            }

        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }


    public void callAddAppointmentAPI(String time){
        try {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("patient_id", AppPreferences.getInstance(this).getUserID()));
            nameValuePairs.add(new BasicNameValuePair("doctor_id", doctorResultPOJOList.get(spinner_doctor.getSelectedItemPosition()).getId()));
            nameValuePairs.add(new BasicNameValuePair("booking_date", et_date.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("booking_starttime", time));
            nameValuePairs.add(new BasicNameValuePair("appointment_branch_code", branchPOJOList.get(spinner_branch.getSelectedItemPosition()).getBranch_code()));
            nameValuePairs.add(new BasicNameValuePair("reason", et_reason.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("visit_type", "First Visit"));
            new WebServiceBase(nameValuePairs, this, ADD_APPOINTMENT_API).execute(ApiConfig.add_appointment_api);
        }
        catch (Exception e){
            ToastClass.showShortToast(getApplicationContext(),"sorry appointment cannot booked at this time.");
            e.printStackTrace();
        }
    }
}
