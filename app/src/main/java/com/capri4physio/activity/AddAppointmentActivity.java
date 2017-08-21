package com.capri4physio.activity;

import android.app.Activity;
import android.graphics.Color;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.capri4physio.R;
import com.capri4physio.Services.GetWebServices;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.model.branch.BranchPOJO;
import com.capri4physio.model.chat.ChatUsersListPOJO;
import com.capri4physio.model.user.UserPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.capri4physio.util.Utils;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAppointmentActivity extends AppCompatActivity implements WebServicesCallBack, DatePickerDialog.OnDateSetListener {
    private static final String GET_TIMINGS = "get_timings";
    private static final String ADD_APPOINTMENTS = "add_appointments";
    private static final String GET_ALL_BRANCHES = "get_all_branches";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_patients)
    Spinner spinner_patients;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    @BindView(R.id.tv_appointment_date)
    TextView tv_appointment_date;
    @BindView(R.id.et_reason)
    EditText et_reason;
    @BindView(R.id.rg_visit_type)
    RadioGroup rg_visit_type;
    @BindView(R.id.rb_first_visit)
    RadioButton rb_first_visit;
    @BindView(R.id.rb_follow_up)
    RadioButton rb_follow_up;
    @BindView(R.id.rv_appointment_time)
    RecyclerView rv_appointment_time;
    @BindView(R.id.rl_branch)
    RelativeLayout rl_branch;
    @BindView(R.id.btn_choose_timings)
    Button btn_choose_timings;

    List<String> list_booked_time = new ArrayList<>();
    List<String> arraytime;
    List<Integer> list_removed_position = new ArrayList<>();
    private final String GET_ALL_PATIENTS = "get_all_patients";
    String branch_code="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        arraytime = Arrays.asList(getResources().getStringArray(R.array.Time_Pickersheet));
        spinner_patients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        branch_code=AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
        tv_appointment_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddAppointmentActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date d = new Date();
        tv_appointment_date.setText(simpleDateFormat.format(d));
        getTimings();
        btn_choose_timings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTimings();
            }
        });

    }

    public void getTimings() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("booking_date", tv_appointment_date.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("appointment_branch_code", AppPreferences.getInstance(this).getUSER_BRANCH_CODE()));
        new WebServiceBase(nameValuePairs, this, GET_TIMINGS).execute(ApiConfig.get_timings_api);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TagUtils.getTag(),"user id:-"+AppPreferences.getInstance(getApplicationContext()).getUserType());
        if(AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")){
            getAllBranches();
            spinner_branch.setVisibility(View.VISIBLE);
            rl_branch.setVisibility(View.VISIBLE);
        }else{
            spinner_branch.setVisibility(View.GONE);
            rl_branch.setVisibility(View.GONE);
        }

        getAllPatients(AppPreferences.getInstance(this).getUSER_BRANCH_CODE());

    }

    public void getAllBranches(){
        new GetWebServices(this, GET_ALL_BRANCHES).execute(ApiConfig.GetURL);
    }


    public void getAllPatients(String branch_code) {
//        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//        nameValuePairs.add(new BasicNameValuePair("branch_code", branch_code));
//        new WebServiceBase(nameValuePairs, this, GET_ALL_PATIENTS).execute(ApiConfig.get_all_patients);
        new GetWebServices(this,GET_ALL_PATIENTS).execute(ApiConfig.get_all_patients);
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
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GET_ALL_PATIENTS:
                parseAllPatients(response);
                break;
            case GET_TIMINGS:
                parseGetTimings(response);
                break;
            case ADD_APPOINTMENTS:
                parseAddAppointments(response);
                break;
            case GET_ALL_BRANCHES:
                getAllBranches(response);
                break;
        }
    }
    List<BranchPOJO> list_branches;
    public void getAllBranches(String response){
        try{
            JSONArray jsonArray=new JSONArray(response);
            list_branches=new ArrayList<>();
            final List<String> branchLists=new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.optJSONObject(i);
                BranchPOJO branch_pojo=new BranchPOJO(jsonObject.optString("branch_id"),
                        jsonObject.optString("branch_name"),
                        jsonObject.optString("branch_code"),
                        jsonObject.optString("branch_status"));
                branchLists.add(branch_pojo.getBranch_name()+" ("+branch_pojo.getBranch_code()+")");
                list_branches.add(branch_pojo);
            }
            if(branchLists.size()>0) {
                ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, branchLists);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                spinner_branch.setAdapter(aa);
                branch_code=list_branches.get(0).getBranch_code();
                spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        branch_code=list_branches.get(position).getBranch_code();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
            if(branchLists.size()>0) {
                getAllPatients(list_branches.get(0).getBranch_code());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void parseAddAppointments(String response){
        Log.d(TagUtils.getTag(),"add response:-"+response);
        try{
            JSONObject jsonObject = new JSONObject(response);

            if(jsonObject.optString("success").equals("true")){
                ll_time.setClickable(false);
                tv_time.setTextColor(Color.RED);
                Toast.makeText(getApplicationContext(), "Appointment Successfully Added", Toast.LENGTH_LONG).show();
            }else{
                Utils.showError(getApplicationContext(), getResources().getString(R.string.error), getResources().getString(R.string.err_clinic));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void parseGetTimings(String response) {
        list_booked_time.clear();
        list_removed_position.clear();
        try {
            JSONObject jsonObject = new JSONObject(response);
            String resul = jsonObject.getString("success");
            list_removed_position.clear();
            list_booked_time.clear();
            Log.e("jsonobj", "obj:-");
            if (resul.equals("true")) {
                Log.e("jsonobj", "if");
                JSONArray jsonArray = jsonObject.optJSONArray("result");
                for (int i = 0; i < jsonArray.length(); i++) {
//                            Log.e("jsonArray", "aray:-" + jsonArray.toString());
                    JSONObject jsonObject4 = jsonArray.optJSONObject(i);
                    Log.e("jsonobj", "obj:-" + jsonObject4.toString());

                    String time = jsonObject4.optString("booking_starttime");
                    list_booked_time.add(time);

                    for (String bookd_appointmentt : list_booked_time) {
                        for (int j = 0; j < arraytime.size(); j++) {

                            String server_string = arraytime.get(j);
                            if (bookd_appointmentt.equals(server_string)) {
//                                            mrng_time.remove(bookd_appointmentt);
                                list_removed_position.add(j);
                            }
                        }
                        Log.e("getbooked_time", list_booked_time.toString());
                    }
                }
            }
            Log.e("response", "" + response);
        } catch (Exception e) {
            Log.e("error", e.toString());

        }
        HorizontalAdapter adapter = new HorizontalAdapter(this, arraytime);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
        rv_appointment_time.setHasFixedSize(true);
        rv_appointment_time.setLayoutManager(layoutManager);
        rv_appointment_time.setAdapter(adapter);
    }

    List<UserPOJO> userPOJOList;

    public void parseAllPatients(String response) {
        try {
            Gson gson = new Gson();
            ChatUsersListPOJO chatUsersListPOJO = gson.fromJson(response, ChatUsersListPOJO.class);
            if (chatUsersListPOJO.getSuccess().equals("true")) {
                List<String> user_list = new ArrayList<>();
                userPOJOList = chatUsersListPOJO.getUserPOJOList();
                for (UserPOJO userPOJO : chatUsersListPOJO.getUserPOJOList()) {
                    user_list.add(userPOJO.getFirstName() + " " + userPOJO.getLastName());
                }
                ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, user_list);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                spinner_patients.setAdapter(aa);
            } else {
                ToastClass.showShortToast(getApplicationContext(), "No Patients Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date="";
        if((monthOfYear+1)<10){
            date = dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year;
        }else{
            date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        }

        tv_appointment_date.setText(date);
        getTimings();
    }

    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<String> horizontalList;
        Boolean allvalue;
        private Activity activity;

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


        public HorizontalAdapter(Activity context, List<String> horizontalList) {
            this.horizontalList = horizontalList;
            this.activity = context;
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


            if (list_removed_position.contains(position)) {
                holder.tv_time.setTextColor(Color.RED);
                Log.e("mrng_timee", "morngtime");

            } else {
                holder.ll_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        methods();
//                        edit_time = horizontalList.get(position);
                        holder.tv_time.setTextColor(Color.BLACK);
                        if (et_reason.getText().toString().length() > 1) {
                            if (branch_code.equals("")) {
                                Toast.makeText(activity, "No clinic Branch added by you", Toast.LENGTH_LONG).show();
                            } else {
                                addAppointment(horizontalList.get(position),branch_code,holder.ll_time,holder.tv_time);
                            }
                        } else {
                            Toast.makeText(activity.getApplicationContext(), "Please add reason for appointments first", Toast.LENGTH_LONG).show();
                            et_reason.requestFocus();
                        }
                    }

                    private void methods() {
                        holder.tv_time.setTextColor(Color.GREEN);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }
    LinearLayout ll_time;
    TextView tv_time;
    public void addAppointment(String time,String branch_code,LinearLayout ll_time,TextView tv_time){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String visit_type="";
        if(rg_visit_type.getCheckedRadioButtonId()==R.id.rb_first_visit){
            visit_type="First Visit";
        }else{
            visit_type="Follow Up";
        }
        this.ll_time=ll_time;
        this.tv_time=tv_time;

        nameValuePairs.add(new BasicNameValuePair("patient_id", userPOJOList.get(spinner_patients.getSelectedItemPosition()).getId()));
        String user_id=AppPreferences.getInstance(getApplicationContext()).getUserID();
        if(user_id.equals("1")||user_id.equals("2")||user_id.equals("3")){
            nameValuePairs.add(new BasicNameValuePair("doctor_id", user_id));
        }else {
            nameValuePairs.add(new BasicNameValuePair("doctor_id", "138"));
        }
        nameValuePairs.add(new BasicNameValuePair("booking_date", tv_appointment_date.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("booking_starttime", time));
        nameValuePairs.add(new BasicNameValuePair("appointment_branch_code", branch_code));
        nameValuePairs.add(new BasicNameValuePair("reason", et_reason.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("visit_type", visit_type));
        nameValuePairs.add(new BasicNameValuePair("status", "1"));
        new WebServiceBase(nameValuePairs, this, ADD_APPOINTMENTS).execute(ApiConfig.add_appointment_api);
    }
}
