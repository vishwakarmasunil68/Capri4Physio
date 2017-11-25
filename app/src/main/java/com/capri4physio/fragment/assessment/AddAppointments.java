package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.Invoice.InfoApps1;
import com.capri4physio.R;
import com.capri4physio.fragment.ViewSceduleFragment;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 7/14/2016.
 */
public class AddAppointments extends AppCompatActivity implements AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener{
    private Calendar cal;
    private int day;
    private int month;
    private int year;

    private int hour;
    private int minute;
int status;

    LinearLayout linear;
    EditText fname, email, login, pass;
    String Namewithposition  = "First Visit";
    String Email, Login, Pass;
    RecyclerView listview;
    public static String blnc_id,trnsdtime,trnsamount,trnsamounttype;
    public static final String KEY_PATIENTID = "patient_id";
    public static final String KEY_DOCTORID = "doctor_id";
    public static final String KEY_DATEBOOKING = "booking_date";
    public static final String KEY_BOOKINGSTARTTIME = "booking_starttime";
    public static final String KEY_BOOKINGENDTIME = "booking_endtime";
    public static final String KEY_REASON = "reason";
    //public static final String KEY_REASON = "reason";
    JSONArray jsonArray;
    String all_date;
    private static final String REGISTER_URL = "http://oldmaker.com/ecs/hooks/getBalanceInfoHook.php";

    public static final int TIME_DIALOG_ID = 1111;
    Spinner patientspinner;
    List<String> arr;
    ArrayList<String> patientarry;
    Spinner doctorspinner;
    String edit_time;
    EditText edit_date,edit_reason;
    EditText edit_datefrom_;
    Button savebtn;
    Date toDate ;
    Date fromDate;
    RadioGroup radioGroup;
    String selectionfol1;
    RadioButton rb_firstvisit,rb_followup;
    String id1;
    LocationAdapter contactAdapter;
    List<String> dates = new ArrayList<String>();
    List<String> mrng_time, getbooked_time,arraytime;
    List<Integer> list_removed_position = new ArrayList<>();
    public static ArrayList<InfoApps> contactDetails;
    int pos;
    String st="0";
    public static ListView contactList;
    // URL of object to be parsed
    String JsonURL = "http://www.caprispine.in/users/addappointment";
    InfoApps Detailapp;
    public ArrayList<String> patientaray;
    public ArrayList<String> patientdarray;
    String selection="First Visit";
    ProgressDialog pDialog;
    ArrayList<String> arrayList;
    String item;
    InfoApps1 detailApps;
    Spinner spinnerbranchloca;
    Map<String,String> objresponse;
    public static String userid;
    JSONObject jsonObject = new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        startActivity(new Intent(AddAppointments.this, GetLUserList.class));
        setContentView(R.layout.newappointments);
        getbooked_time  =new  ArrayList<String>();
        mrng_time  =new  ArrayList<String>();
        contactDetails  =new  ArrayList<InfoApps>();
        arrayList = new ArrayList<String>();
        arraytime = Arrays.asList(getResources().getStringArray(R.array.Time_Pickersheet));
        arrayList.add("Select Branch Name");
        patientaray =new ArrayList<String>();
        patientarry =new ArrayList<>();
        patientdarray = new ArrayList<String>();
        List<String> categories = new ArrayList<String>();


//        new CatagoryUrlAsynTask().execute();
        new CatagoryUrlAsynTask1().execute();
        listview=(RecyclerView)findViewById(R.id.listview);
        patientspinner = (Spinner) findViewById(R.id.spinner2patient);
        doctorspinner = (Spinner) findViewById(R.id.spinner1doctor);
        edit_date = (EditText) findViewById(R.id.edit_date);
        edit_datefrom_ = (EditText) findViewById(R.id.edit_datefrom_);
        edit_reason = (EditText) findViewById(R.id.edit_reason);
        savebtn = (Button) findViewById(R.id.savebtn);
        spinnerbranchloca = (Spinner) findViewById(R.id.spinnerbranchloca);
        radioGroup = (RadioGroup) findViewById(R.id.rg_group);
        rb_firstvisit = (RadioButton) findViewById(R.id.radio_firstvisit);
        rb_followup = (RadioButton) findViewById(R.id.radio_followuptvisit);
//        HorizontalAdapter
        HorizontalAdapter adapter = new HorizontalAdapter(getApplicationContext(), arraytime);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 5);
        listview.setHasFixedSize(true);
        listview.setLayoutManager(layoutManager);
        listview.setAdapter(adapter);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        hour = cal.get(Calendar.HOUR);
        minute = cal.get(Calendar.MINUTE);


        if ((month + 1)>=10){

            if(day >=10)
            {
                edit_date.setText(day + "-" +  (month + 1) + "-"+ year);
                edit_datefrom_.setText(day + "-" + (month + 1) + "-"
                        + year);

            }
            else{
                edit_date.setText("0"+day + "-" +  (month + 1) + "-"+ year);
                edit_datefrom_.setText("0"+day + "-" + (month + 1) + "-"
                        + year);
            }

        }
        else {
            if(day >=10)
            {
                edit_date.setText(day + "-" + "0" +  (month + 1) + "-"+ year);
                edit_datefrom_.setText(day + "-" + (month + 1) + "-"
                        + year);

            }
            else{
                edit_date.setText("0"+day + "-"  + "0"+  (month + 1) + "-"+ year);
                edit_datefrom_.setText("0"+day + "-"  + "0"+ (month + 1) + "-"
                        + year);
            }
//            edit_date.setText(day + "-" + "0" + (month + 1) + "-" + year);
//            edit_datefrom_.setText(day + "-" + "0"+(month + 1) + "-"
//                    + year);
        }


        if (getIntent().getStringExtra("edit_datefrom_").equals("true")){
            edit_datefrom_.setVisibility(View.GONE);
        }

        String[] sports = getResources().getStringArray(R.array.Time_Pickersheet);

        //Row layout defined by Android: android.R.layout.simple_list_item_1
        /*listview.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, sports));
        listview.setOnItemClickListener(this);*/
        // Spinner click listener
//        patientspinner.setOnItemSelectedListener(this);
        doctorspinner.setOnItemSelectedListener(this);
        String example = "Strings are immutable";
        char result = example.charAt(8);
//        ViewSceduleFragment.patientaray.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
         arr=new ArrayList<>();

//        patientarry = ViewSceduleFragment.patientaray;

//        patientdarray = ViewSceduleFragment.patientidarray;


        /*for(String s:patientarry){
            arr.add(s.toUpperCase());
        }*/

        patientspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*String user_name= patientspinner.getOnItemSelectedListener().toString();
                List<String> id_lst=ViewSceduleFragment.patientidarray;
                List<String> user_lst=ViewSceduleFragment.patientaray;
                Log.e("sun",id_lst.toString());
                Log.e("sun",user_lst.toString());
                boolean bol=user_lst.contains(user_name);
                Log.e("sun",bol+"");
//        if(bol){
                int index=user_lst.indexOf(user_name);
                   id1=id_lst.get(index);
                Log.e("idjkkjkjkj",id1);*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")){
            spinnerbranchloca.setVisibility(View.VISIBLE);
        }
        /*if(AppPreferences.getInstance(getApplicationContext()).getUserType().equals("1")){
            patientaray.add(AppPreferences.getInstance(getApplicationContext()).getUserName());
        }*/
        else {
            item = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
            Log.e("item", item);
            viewStaffApiCall();
        }
        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    try {
                        patientaray.clear();
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(), R.layout.dropsimpledown, patientaray);
                        doctorspinner.setAdapter(spinnerArrayAdapter);
                        patientarry.clear();
                        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                                getApplicationContext(), R.layout.dropsimpledown, patientarry);
                        patientspinner.setAdapter(spinnerArrayAdapter1);

//                        patientaray.clear();
                    }
                    catch (Exception e){
                Log.e("eror",e.toString());
                    }
                }
                else {
                    String address = spinnerbranchloca.getSelectedItem().toString();

                    String ad[]=address.split("\\(");
                    item = ad[1];
                    item =item.replace(")", "");
//                    edtxt_branchcode.setText(newaddress);
                    viewStaffApiCall();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        mRecyclerView.setLayoutManager(layoutManager);


        List<String> user_lst=ViewSceduleFragment.patientaray;


//        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
//                this, android.R.layout.simple_dropdown_item_1line, categories);
//        doctorspinner.setAdapter(spinnerArrayAdapter1);

        edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });
        edit_datefrom_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });

        /*edit_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });*/
        edit_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listview.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        /*edit_datefrom_.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listview.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (patientaray.size() > 0&&patientarry.size()>0) {
                    try {
                        pDialog = new ProgressDialog(AddAppointments.this);
                        pDialog.setMessage("Working...");
                        pDialog.setCancelable(false);
                        try {
                            pDialog.show();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        addExpenseApiCall1(listview, edit_date.getText().toString());
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Please fil all information correctly", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please Select all entries", Toast.LENGTH_LONG).show();
                }

                }
        });



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                int id = radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(id);
                int radioId = radioGroup.indexOfChild(radioButton);
                RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
                selection = (String) btn.getText();
                Toast.makeText(getApplicationContext(), selection, Toast.LENGTH_LONG).show();
                pos = radioGroup.indexOfChild(findViewById(checkedId));
            }
            });


//                SavePreferences(KEY_SAVED_RADIO_BUTTON_INDEX, therapist_id);

               /* switch (therapist_id) {

                    case 0:
                         selection = (String) rb_firstvisit.getText();
String radiobutton1=String.valueOf(therapist_id);
                        Toast.makeText(getApplicationContext(),selection,Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        selection = (String) rb_followup.getText();
                        String radiobutton2=String.valueOf(therapist_id);
                        Toast.makeText(getApplicationContext(),selectionfol1,Toast.LENGTH_LONG).show();

                        break;
                }
            }
        });*/
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:

                // set time picker as current time
                return new TimePickerDialog(this, timePickerListener, hour, minute,  false);

            case 0:
                return new DatePickerDialog(this, datePickerListener, year, month, day);
            case 1:
                return new DatePickerDialog(this, datePickerListener1, year, month, day);

        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            if(selectedMonth + 1>=10)
            {
                if(selectedDay >=10)
                {
                    edit_date.setText(selectedDay  + "-" + (selectedMonth + 1) + "-"
                            + selectedYear);

                }
                else{

                    edit_date.setText("0"+ selectedDay  + "-" + (selectedMonth + 1) + "-"
                            +selectedYear );
                }
            }
            else{

                if(selectedDay >=10)
                {
                    edit_date.setText(selectedDay + "-" +"0"+ (selectedMonth + 1) + "-"
                            +selectedYear );

                }
                else{

                    edit_date.setText("0"+ selectedDay + "-" +"0"+ (selectedMonth + 1) + "-"
                            +selectedYear );
                }
            }
        }
    };
    private DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            if(selectedMonth + 1>=10)
            {
                if(selectedDay >=10)
                {
                    edit_datefrom_.setText(selectedDay  + "-" + (selectedMonth + 1) + "-"
                            + selectedYear);

                }
                else{

                    edit_datefrom_.setText("0"+ selectedDay  + "-" + (selectedMonth + 1) + "-"
                            +selectedYear );
                }
            }
            else{

                if(selectedDay >=10)
                {
                    edit_datefrom_.setText(selectedDay + "-" +"0"+ (selectedMonth + 1) + "-"
                            +selectedYear );

                }
                else{

                    edit_datefrom_.setText("0"+ selectedDay + "-" +"0"+ (selectedMonth + 1) + "-"
                            +selectedYear );
                }
            }
        }
    };

    TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            int hour   = hourOfDay;
            int minute = minutes;

            String time = hour+":"+minute;
            try {
                final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                final Date dateObj = sdf.parse(time);
                time = new SimpleDateFormat("K:mm a").format(dateObj);
            } catch (final ParseException e) {
                e.printStackTrace();
            }

            // updateTime(hour,minute);
//            edit_time.setText(time);
        }

    };
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getId()){
            case R.id.spinner1doctor:
        int pos1= doctorspinner.getSelectedItemPosition();
                InfoApps obj = contactDetails.get(pos1);
               String postiond=contactDetails.get(pos1).getId();
                Log.d("postiond",postiond);
                /*String user_name= patientspinner.getOnItemSelectedListener().toString();
                List<String> id_lst=ViewSceduleFragment.patientidarray;
                List<String> user_lst=ViewSceduleFragment.patientaray;
                Log.e("sun",id_lst.toString());
                Log.e("sun",user_lst.toString());
                boolean bol=user_lst.contains(user_name);
                Log.e("sun",bol+"");
//        if(bol){
                int index=user_lst.indexOf(user_name);
               String  id1=id_lst.get(index);
                Log.e("idjkkjkjkj",id1);*/
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
    private void addExpenseApiCall1(final RecyclerView listview, final String booktime) {
        listview.setVisibility(View.VISIBLE);
//        final String booktime1 =booktime;
        Log.e("booktime",booktime+ " "+ item);
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://caprispine.in/users/appoimentlistdatewise",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            try{
                                pDialog.hide();
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            Log.e("response", "" + response);
//                    pDialog.hide();

                            JSONObject jsonObject = new JSONObject(response);
                            String resul = jsonObject.getString("success");
                            list_removed_position.clear();
                            getbooked_time.clear();
                            Log.e("jsonobj", "obj:-");
                            if (resul.equals("true")) {
                                Log.e("jsonobj","if");
                                JSONArray jsonArray = jsonObject.optJSONArray("result");
                                for (int i = 0; i < jsonArray.length(); i++) {
//                            Log.e("jsonArray", "aray:-" + jsonArray.toString());
                                    JSONObject jsonObject4 = jsonArray.optJSONObject(i);
                                    Log.e("jsonobj", "obj:-" + jsonObject4.toString());

                                    String time = jsonObject4.optString("booking_starttime");
                                    getbooked_time.add(time);

                                    for (String bookd_appointmentt : getbooked_time) {
                                        for (int j = 0; j < arraytime.size(); j++) {

                                            String server_string = arraytime.get(j);
                                            if (bookd_appointmentt.equals(server_string)) {
//                                            mrng_time.remove(bookd_appointmentt);
                                                list_removed_position.add(j);
                                            }
                                        }
                                        Log.e("getbooked_time", getbooked_time.toString());
                                    }
                                }
                            }
                            Log.e("response", "" + response);
                        } catch (Exception e) {
                            Log.e("error", e.toString());

                        }
                        HorizontalAdapter adapter = new HorizontalAdapter(getApplicationContext(), arraytime);
                        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 5);
                        listview.setHasFixedSize(true);
                        listview.setLayoutManager(layoutManager);
                        listview.setAdapter(adapter);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("booking_date", booktime);
                params.put("appointment_branch_code", item);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(AddAppointments.this);
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        try {
            pDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addappointment(final String timeappo){

        all_date=  dates.toString().replace("[", "").replace("]", "");
//        all_date=   dates.toString().replace("]","");


        /*String user_name= patientspinner.getSelectedItem().toString();
        List<String> id_lst=patientdarray;
        List<String> user_lst=arr;
        Log.e("sun",id_lst.toString());
        Log.e("sun",user_lst.toString());
        boolean bol=user_lst.contains(user_name);
        Log.e("sun",bol+"");
//        if(bol){
        int index=user_lst.indexOf(user_name);
        Log.e("sun",String.valueOf(index));
        id1=id_lst.get(index);*/
        int id = patientspinner.getSelectedItemPosition();
        id1 = contactDetails.get(id).getId();
        Log.e("idjkkjkjkj",all_date);
        /*String user_name= patientspinner.getOnItemSelectedListener().toString();
        List<String> id_lst=ViewSceduleFragment.patientidarray;
        List<String> user_lst=ViewSceduleFragment.patientaray;
        Log.e("sun",id_lst.toString());
        Log.e("sun",user_lst.toString());
        boolean bol=user_lst.contains(user_name);
        Log.e("sun",bol+"");
//        if(bol){
            int index=user_lst.indexOf(user_name);
            id=id_lst.get(index);
            Log.e("idjkkjkjkj",id);*/
//        }
        final String date = edit_date.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();
        final String Docname = doctorspinner.getSelectedItem().toString().trim();
Log.e("date",date);
        Log.e("reasonkkkjjkjkj",reason);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JsonURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            try {
                                pDialog.dismiss();
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            Toast.makeText(AddAppointments.this,"Appointment created successfully", Toast.LENGTH_LONG).show();
                            finish();
                            /*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*/

                            /*JSONObject objresponse = new JSONObject(response);
                            //					Toast.makeText(getApplicationContext(), "Could not retreive Data2!", Toast.LENGTH_LONG).show();

                            String success = objresponse.getString("isSuccess");
                            String success_msg = objresponse.getString("success_msg");

                            if (success.equalsIgnoreCase("true") || success_msg.equalsIgnoreCase("true")) {

                                Log.e("Postdat", "" + response);
                                jsonArray = objresponse.getJSONArray("result");


                                //Log.i("News Data", jsonArray.toString());

//                    JSONArray cast = jsonArray.getJSONArray("result");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    blnc_id = jsonObject.getString("receiver_name");
                                    trnsdtime = jsonObject.getString("transaction_datetime");
                                    trnsamount= jsonObject.getString("balance_amount");
                                    trnsamounttype= jsonObject.getString("transaction_transfer_type");
//                                     balance_id=new ArrayList<String>();
//                                    balance_id.add(blnc_id);
                                    Detailapp = new InfoApps();
                                    Detailapp.setName(blnc_id);
                                    Detailapp.setNumber(trnsdtime);
                                    Detailapp.setAppname(trnsamount);
                                    Detailapp.setDataAdd(trnsamounttype);
                                    Log.e("account_blnc_id", blnc_id);
                                    Log.e("account_balance_id", contactDetails.toString());
//                                    if (BalanceDetail.password.equals(pinpassword)) {
                                    pass.setVisibility(View.GONE);
                                    linear.setVisibility(View.VISIBLE);
                                    contactAdapter = new LocationAdapter(getApplicationContext(), R.layout.contactlistadap);
                                    contactList.setAdapter(contactAdapter);
//                                    Double user_long = jsonObject.getDouble("user_long");
//                                    Double user_lat = jsonObject.getDouble("user_lat");
//                                    UserType = "UserType: " + jsonObject.getString("usertype");
                                    *//*Intent intent1 = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent1);*//*
                                    *//*}
                                    else {
                                        Toast.makeText(getApplicationContext(),"Pin number is incorrect",Toast.LENGTH_LONG).show();
                                    }*//*
                                }*/
//                            }
                           /* else {
                                Toast.makeText(getApplicationContext(),"Phone_no. or password is incorrect",Toast.LENGTH_LONG).show();
                            }*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        startActivity(new Intent(StmtActivity.this, PinDoMatch.class));
                        Log.e("Postdat", "" + response);
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
                params.put(KEY_PATIENTID,id1);
                params.put(KEY_DOCTORID,Docname);
                params.put(KEY_DATEBOOKING, all_date);
                params.put(KEY_BOOKINGSTARTTIME, timeappo);
                params.put("appointment_branch_code", item);
                params.put(KEY_REASON, reason);
                params.put("visit_type", selection);
//
//
// Toast.makeText(UserStatement.this, username +"success", Toast.LENGTH_LONG).show();
//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }private void addappointment1(final String timeappo){

//        all_date=   dates.toString().replace("]","");


        /*String user_name= patientspinner.getSelectedItem().toString();
        List<String> id_lst=patientdarray;
        List<String> user_lst=arr;
        Log.e("sun",id_lst.toString());
        Log.e("sun",user_lst.toString());
        boolean bol=user_lst.contains(user_name);
        Log.e("sun",bol+"");
//        if(bol){
        int index=user_lst.indexOf(user_name);
        Log.e("sun",String.valueOf(index));
        id1=id_lst.get(index);*/
        int id = patientspinner.getSelectedItemPosition();
        id1 = contactDetails.get(id).getId();
        Log.e("idjkkjkjkj",all_date);
        /*String user_name= patientspinner.getOnItemSelectedListener().toString();
        List<String> id_lst=ViewSceduleFragment.patientidarray;
        List<String> user_lst=ViewSceduleFragment.patientaray;
        Log.e("sun",id_lst.toString());
        Log.e("sun",user_lst.toString());
        boolean bol=user_lst.contains(user_name);
        Log.e("sun",bol+"");
//        if(bol){
            int index=user_lst.indexOf(user_name);
            id=id_lst.get(index);
            Log.e("idjkkjkjkj",id);*/
//        }
        final String date = edit_date.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();
        final String Docname = doctorspinner.getSelectedItem().toString().trim();
Log.e("date",date);
        Log.e("reasonkkkjjkjkj",reason);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JsonURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            pDialog.dismiss();
                            Toast.makeText(AddAppointments.this,"Appointment created successfully", Toast.LENGTH_LONG).show();
                            finish();
                            /*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*/

                            /*JSONObject objresponse = new JSONObject(response);
                            //					Toast.makeText(getApplicationContext(), "Could not retreive Data2!", Toast.LENGTH_LONG).show();

                            String success = objresponse.getString("isSuccess");
                            String success_msg = objresponse.getString("success_msg");

                            if (success.equalsIgnoreCase("true") || success_msg.equalsIgnoreCase("true")) {

                                Log.e("Postdat", "" + response);
                                jsonArray = objresponse.getJSONArray("result");


                                //Log.i("News Data", jsonArray.toString());

//                    JSONArray cast = jsonArray.getJSONArray("result");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    blnc_id = jsonObject.getString("receiver_name");
                                    trnsdtime = jsonObject.getString("transaction_datetime");
                                    trnsamount= jsonObject.getString("balance_amount");
                                    trnsamounttype= jsonObject.getString("transaction_transfer_type");
//                                     balance_id=new ArrayList<String>();
//                                    balance_id.add(blnc_id);
                                    Detailapp = new InfoApps();
                                    Detailapp.setName(blnc_id);
                                    Detailapp.setNumber(trnsdtime);
                                    Detailapp.setAppname(trnsamount);
                                    Detailapp.setDataAdd(trnsamounttype);
                                    Log.e("account_blnc_id", blnc_id);
                                    Log.e("account_balance_id", contactDetails.toString());
//                                    if (BalanceDetail.password.equals(pinpassword)) {
                                    pass.setVisibility(View.GONE);
                                    linear.setVisibility(View.VISIBLE);
                                    contactAdapter = new LocationAdapter(getApplicationContext(), R.layout.contactlistadap);
                                    contactList.setAdapter(contactAdapter);
//                                    Double user_long = jsonObject.getDouble("user_long");
//                                    Double user_lat = jsonObject.getDouble("user_lat");
//                                    UserType = "UserType: " + jsonObject.getString("usertype");
                                    *//*Intent intent1 = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent1);*//*
                                    *//*}
                                    else {
                                        Toast.makeText(getApplicationContext(),"Pin number is incorrect",Toast.LENGTH_LONG).show();
                                    }*//*
                                }*/
//                            }
                           /* else {
                                Toast.makeText(getApplicationContext(),"Phone_no. or password is incorrect",Toast.LENGTH_LONG).show();
                            }*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        startActivity(new Intent(StmtActivity.this, PinDoMatch.class));
                        Log.e("Postdat", "" + response);
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
                params.put(KEY_PATIENTID,id1);
                params.put(KEY_DOCTORID,Docname);
                params.put(KEY_DATEBOOKING, all_date);
                params.put(KEY_BOOKINGSTARTTIME, timeappo);
                params.put("appointment_branch_code", item);
                params.put(KEY_REASON, reason);
                params.put("visit_type", selection);
//
//
// Toast.makeText(UserStatement.this, username +"success", Toast.LENGTH_LONG).show();
//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public List<String> getDaysBetweenDates(Date startdate, Date enddate)
    {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);
        DateFormat parseFormat = new SimpleDateFormat( "EEE MMM dd HH:mm:ss z yyyy");
        while (calendar.getTime().before(enddate)||calendar.getTime().equals(enddate))
        {
            Date result = calendar.getTime();
            try{
                Date parsed=parseFormat.parse(result.toString());
                SimpleDateFormat sdf1_time=new SimpleDateFormat("dd-MM-yyyy");
                dates.add(sdf1_time.format(parsed));
            }
            catch(Exception e){
                e.printStackTrace();
            }


            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }
    private void viewStaffApiCall() {

        try {
            patientarry.clear();
            patientaray.clear();
        }
        catch (Exception e){
            e.toString();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_PATIENT_STAFF_FILTERATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            try {
                                patientarry.clear();
                                patientaray.clear();
                            }
                            catch (Exception e){
                                e.toString();
                            }
                            Log.e("result", response);
                            JSONArray jsonArray = new JSONArray(response);
                            Log.e("Post Method", jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                String status = jsonObject2.getString("status");

                                if (status.equals("1")){
                                Log.e("2", jsonObject2.toString());
                                String type = jsonObject2.getString("user_type");

                                if (type.equals("1")) {

                                    String id = jsonObject2.getString("id");
                                    String sfirst_name = jsonObject2.getString("first_name");
                                    String last_name= jsonObject2.getString("last_name");
                                    patientaray.add(sfirst_name+ " "+last_name);
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                            getApplicationContext(), R.layout.dropsimpledown, patientaray);
                                    doctorspinner.setAdapter(spinnerArrayAdapter);
                                }
                                if (type.equals("0")){
                                    String id = jsonObject2.getString("id");
                                    String sfirst_name = jsonObject2.getString("first_name");
                                    String last_name= jsonObject2.getString("last_name");
                                    Detailapp = new InfoApps();
                                    Detailapp.setName(sfirst_name+ " "+ last_name);
                                    Detailapp.setId(id);

                                    patientarry.add(Detailapp.getName());
                                    contactDetails.add(Detailapp);
//                                    patientdarray.add(id);
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                            getApplicationContext(), R.layout.dropsimpledown, patientarry);
                                    patientspinner.setAdapter(spinnerArrayAdapter);
                                }
                            }
                            else {
                                    patientaray.clear();
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                            getApplicationContext(), R.layout.dropsimpledown, patientaray);
                                    doctorspinner.setAdapter(spinnerArrayAdapter);
                                    patientarry.clear();
                                    ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                                            getApplicationContext(), R.layout.dropsimpledown, patientarry);
                                    patientspinner.setAdapter(spinnerArrayAdapter1);
                                }
                            }

                        }catch(JSONException e){
                            Log.e("Postdat", "" + e.toString());
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


            protected Map<String,String> getParams(){
                objresponse = new HashMap<String, String>();
                objresponse.put("bracch_code", item);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
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
                    .inflate(R.layout.inflate_monday_time, parent, false);

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
                        edit_time = horizontalList.get(position);
                        holder.tv_time.setTextColor(Color.BLACK);
                        if (edit_reason.getText().toString().length()>1) {

                            if(edit_datefrom_.getVisibility()==View.GONE)
                            {

//                                Calendar cal = Calendar.getInstance();
                                String date1= edit_date.getText().toString();
                                all_date = date1;
                                initProgressDialog("Please wait..");
                                addappointment1(edit_time);
                            }else {
                                DateFormat readFormat = new SimpleDateFormat( "dd-MM-yyyy");

                                try{
                                    Date d1=readFormat.parse(edit_datefrom_.getText().toString());
                                    Date d2=readFormat.parse(edit_date.getText().toString());
                                    getDaysBetweenDates(d1, d2);
                                    try {
                                        initProgressDialog("Please wait..");
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    addappointment(edit_time);
                                }
                                catch(Exception e){
                                    e.printStackTrace();
                                }



                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Please add reason for appointments first", Toast.LENGTH_LONG).show();
                            edit_reason.requestFocus();
                        }
                    }

                    private void methods() {
                        holder.tv_time.setTextColor(Color.GREEN);
                    }
                });
            }
//            if(list_removed_position.contains(position)){
//                Log.e("mrng_timee","morngtime");
//            }
            /*if(list_removed_position.contains(position)){
                holder.tv_time.setTextColor(Color.RED);
                Log.e("mrng_timee","morngtime");

            }
            else {
                holder.ll_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String appo_time = horizontalList.get(position);
                        Intent intent = new Intent(getActivity(), AppointmentDetails.class);
                        intent.putExtra("appointment_time", appo_time);
                        intent.putExtra("clinic_id", clinic_id);
                        intent.putExtra("appointment_date", appointment_date);
                        startActivityForResult(intent,2);
//                        activity.finish();
                    }
                });
            }*/
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }
    private class CatagoryUrlAsynTask1 extends AsyncTask<String, String, String> {
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
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray jsonArray = new JSONArray(s);
                for (int i =0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String branch_name = jsonObject2.getString("branch_name");
                    String bracch_code = jsonObject2.getString("branch_code");
                    //branch_code
//                    arrayList.add(bracch_code);

                    detailApps = new InfoApps1();
                    detailApps.setName(branch_name);
                    detailApps.setId(bracch_code);
                    arrayList.add(detailApps.getName() + "  "+ "(" + detailApps.getId()+ ")");

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.dropsimpledown, arrayList);
                    spinnerbranchloca.setAdapter(spinnerArrayAdapter);

                }

            }catch(Exception e){
                Log.e("error",e.toString());

            }
        }
    }


    /*private void saveappointment(){
        *//*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*//*
        *//*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*//*
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JsonURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            *//*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*//*

                            JSONObject objresponse = new JSONObject(response);
                            //					Toast.makeText(getApplicationContext(), "Could not retreive Data2!", Toast.LENGTH_LONG).show();

                            *//*String success = objresponse.getString("isSuccess");
                            String success_msg = objresponse.getString("success_msg");*//*

//                            if (success.equalsIgnoreCase("true") || success_msg.equalsIgnoreCase("true")) {

                            Log.e("Postdat", "" + response);
                            jsonArray = objresponse.getJSONArray("result");


                            //Log.i("News Data", jsonArray.toString());

//                    JSONArray cast = jsonArray.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                GetDcotorid=jsonObject.getString("doctor_id");
                                booking_date=jsonObject.getString("booking_date");
                                uniqueid=jsonObject.getString("id");
                                booking_starttime = jsonObject.getString("booking_starttime");
                                booking_Endtime=jsonObject.getString("booking_endtime");
                                reason = jsonObject.getString("reason");
                                patient_name.setText(getPatientname);
                                date.setText(booking_date);
                                _starttime.setText(booking_starttime);
                                booking_reason.setText(reason);
                                Log.e("datas",uniqueid+ GetDcotorid);
                                Detailapp = new InfoApps();
                                Detailapp.setName(booking_date);
                                Detailapp.setNumber(booking_starttime);
                                Detailapp.setAppname(reason);
                                Detailapp.setDataAdd(patientname);


                            }
                                    *//*else {
                                        Toast.makeText(getApplicationContext(),"Pin number is incorrect",Toast.LENGTH_LONG).show();
                                    }*//*
//                            }
                            *//*else {
                                Toast.makeText(getApplicationContext(),"Phone_no. or password is incorrect",Toast.LENGTH_LONG).show();
                            }*//*
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        startActivity(new Intent(StmtActivity.this, PinDoMatch.class));
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
                params.put(KEY_PATIENTID,patient);

//                Toast.makeText(UserStatement.this, username +"success", Toast.LENGTH_LONG).show();
//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Namewithposition=(String)parent.getItemAtPosition(position);
Toast.makeText(getApplicationContext(),Namewithposition,Toast.LENGTH_LONG).show();
    }

    private class CatagoryUrlAsynTask extends AsyncTask<String, String, String> {
        String id, catagoryName;


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

                    patientaray.add(sfirst_name);
//                    stringArrayList.add(sfirst_name);
                    /*Detailapp = new InfoApps();
                    Detailapp.setName(sfirst_name);
                    Detailapp.setStr4(semail);//date
                    Detailapp.setId(id);
                    Detailapp.setNumber(smobile);//startTime
                    Detailapp.setDob(dob);//reason
                    Detailapp.setAge(age);
                    Detailapp.setDoj(doj);
                    Detailapp.setSend_date(senddate);
                    Detailapp.setGender(gen);
                    Detailapp.setMarital_status(marital_status);
                    Detailapp.setDesig(desig);
                    Detailapp.setDataAdd(addr);
                    Detailapp.setCity(city);
                    Detailapp.setPc(pin_code);
                    Detailapp.setQualification(qual);
                    Detailapp.setExp(exp);
                    contactDetails.add(Detailapp);*/
//                    patientaray.add(Detailapp.getName());
                    /*Detailapp = new InfoApps();
                    Detailapp.setName(sfirst_name);
                    Detailapp.setStr4(semail);//date
                    Detailapp.setId(id);

                    contactDetails.add(Detailapp);*/

                    Log.e("staffarray", (patientaray.toString()));
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.dropsimpledown, patientaray);
                    doctorspinner.setAdapter(spinnerArrayAdapter);

                /*contactAdapter = new ReportAdapter(getActivity(), R.layout.contactlistadap);
                contactList.setAdapter(contactAdapter);*/
                }
            }catch(Exception e){
                e.printStackTrace();

            }
            //  CatagoryMetod();
        }
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
