package com.capri4physio.bookappopatient;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.capri4physio.fragment.BaseFragment;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.fragment.assessment.InfoApps;
import com.capri4physio.fragment.assessment.LocationAdapter;
import com.capri4physio.fragment.assessment.Util;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.Utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAppointments#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author prabhunathy
 * @version 1.0
 * @since 2016-03-31
 */
public class AddAppointments extends BaseFragment implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    private Calendar cal;
    private int day;
    private int month;
    private int year;

    private int hour;
    private int minute;
    int status;

    //    String[] sports = getResources().getStringArray(R.array.Time_Pickersheet);
    LinearLayout linear;
    EditText fname, email, login, pass;
    String Namewithposition = "First Visit";
    String Email, Login, Pass;
    RecyclerView listview;
    public static String blnc_id, trnsdtime, trnsamount, trnsamounttype;
    public static final String KEY_PATIENTID = "patient_id";
    public static final String KEY_DOCTORID = "doctor_id";
    public static final String KEY_DATEBOOKING = "booking_date";
    public static final String KEY_BOOKINGSTARTTIME = "booking_starttime";
    public static final String KEY_BOOKINGENDTIME = "booking_endtime";
    public static final String KEY_REASON = "reason";
    //public static final String KEY_REASON = "reason";
    JSONArray jsonArray;
    private static final String REGISTER_URL = "http://oldmaker.com/ecs/hooks/getBalanceInfoHook.php";

    public static final int TIME_DIALOG_ID = 1111;
    Spinner patientspinner;
    List<String> arr;
    ArrayList<String> patientarry;
    Spinner doctorspinner;
    String edit_time;
    EditText edit_date, edit_reason;
    Button mSave;
    RadioGroup radioGroup;
    String selectionfol1;
    RadioButton rb_firstvisit, rb_followup;
    String id1;
    LocationAdapter contactAdapter;
    public static ArrayList<InfoApps> contactDetails;
    int pos;
    String st = "0";
    public static ListView contactList;
    // URL of object to be parsed
    String JsonURL = "http://oldmaker.com/fijiyo/index.php/users/addappointment";
    InfoApps Detailapp;
    List<String> mrng_time, getbooked_time;
    List<Integer> list_removed_position = new ArrayList<>();
    public ArrayList<String> patientaray;
    public ArrayList<String> patientdarray;
    String selection = "First Visit";
    ArrayList<String> arrayList;
    String item;
    InfoApps1 detailApps;
    Map<String, String> objresponse;
    public static String userid;
    String newaddress;
    JSONObject jsonObject = new JSONObject();
    Spinner spinnerbranchloca;
    private ProgressDialog pDialog;

    List<String> arraytime;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static AddAppointments newInstance() {
        AddAppointments fragment = new AddAppointments();
        return fragment;
    }

    public AddAppointments() {
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
       /* mList = new ArrayList<>();
        mAdapter =new UsersAdapter(getActivity(), mList, this);*/


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.newappopatientlikeenquiry, container, false);
        arraytime = Arrays.asList(getResources().getStringArray(R.array.Time_Pickersheet));
        contactDetails = new ArrayList<InfoApps>();
        arrayList = new ArrayList<String>();
//        arrayList.add("Select Branch Name");
        getbooked_time = new ArrayList<String>();
        patientaray = new ArrayList<String>();
        patientarry = new ArrayList<>();
        patientdarray = new ArrayList<String>();
        List<String> categories = new ArrayList<String>();
//        new CatagoryUrlAsynTask().execute();
//        new CatagoryUrlAsynTask1().execute();
        listview = (RecyclerView) rootView.findViewById(R.id.listview);
        patientspinner = (Spinner) rootView.findViewById(R.id.spinner2patient);
        doctorspinner = (Spinner) rootView.findViewById(R.id.spinner1doctor);
        edit_reason = (EditText) rootView.findViewById(R.id.edit_reason);
        edit_date = (EditText) rootView.findViewById(R.id.edit_date);
        mSave = (Button) rootView.findViewById(R.id.savebtn);
        spinnerbranchloca = (Spinner) rootView.findViewById(R.id.spinnerbranchloca);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.rg_group);
        rb_firstvisit = (RadioButton) rootView.findViewById(R.id.radio_firstvisit);
        rb_followup = (RadioButton) rootView.findViewById(R.id.radio_followuptvisit);


        spinnerbranchloca.setVisibility(View.INVISIBLE);
        HorizontalAdapter adapter = new HorizontalAdapter(getActivity(), arraytime);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 5);
        listview.setHasFixedSize(true);
        listview.setLayoutManager(layoutManager);
        listview.setAdapter(adapter);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        hour = cal.get(Calendar.HOUR);
        minute = cal.get(Calendar.MINUTE);

        if ((month + 1) >= 10) {

            if (day >= 10) {
                edit_date.setText(day + "-" + (month + 1) + "-" + year);

            } else {
                edit_date.setText("0" + day + "-" + (month + 1) + "-" + year);
            }

        } else {
            if (day >= 10) {
                edit_date.setText(day + "-" + "0" + (month + 1) + "-" + year);

            } else {
                edit_date.setText("0" + day + "-" + "0" + (month + 1) + "-" + year);
            }
//            edit_date.setText(day + "-" + "0" + (month + 1) + "-" + year);
//            edit_datefrom_.setText(day + "-" + "0"+(month + 1) + "-"
//                    + year);
        }
//        edit_date.setText(day + "-" + "0"+ (month + 1) + "-"
//                + year);

//        listview.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, sports));
        //Row layout defined by Android: android.R.layout.simple_list_item_1
        // Spinner click listener
//        patientspinner.setOnItemSelectedListener(this);
        doctorspinner.setOnItemSelectedListener(this);
        String example = "Strings are immutable";
        char result = example.charAt(8);
//        ViewSceduleFragment.patientaray.setFilters(new InputFilter[] {new InputFilter.AllCaps()});


        arr = new ArrayList<>();
        /*initProgressDialog("Please wait..");
        addExpenseApiCall1(listview,edit_date.getText().toString());*/
        edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int monthdate = monthOfYear + 1;
                        String date = "Date" + String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                                + "-" + String.valueOf(dayOfMonth);
                        edit_date.setText(String.valueOf(dayOfMonth) + "-" + "0" + (String.valueOf(monthdate)) + "-"
                                + String.valueOf(year));
//                        tfDate.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();
                /*getActivity().showDialog(0);
                new DatePickerDialog(getActivity(), datePickerListener, year, month, day);*/
            }
        });


        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (!isValid()) {

                    return;
                } else {*/
                if (Utils.isNetworkAvailable(getActivity())) {
                    listview.setVisibility(View.INVISIBLE);
                    initProgressDialog("Please wait..");
                    addExpenseApiCall1(listview, edit_date.getText().toString());
//                    new CallServices1(listview,edit_date.getText().toString()).execute("http://oldmaker.com/fijiyo/index.php/users/enquirylistdatewise");

                } else {
                    Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
                }
            }
//            }
        });


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
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                int id = radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(id);
                int radioId = radioGroup.indexOfChild(radioButton);
                RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
                selection = (String) btn.getText();
                Toast.makeText(getActivity(), selection, Toast.LENGTH_LONG).show();
                pos = radioGroup.indexOfChild(rootView.findViewById(checkedId));
            }
        });
        /*if (AppPreferences.getInstance(getActivity()).getUserType().equals("4")){
            spinnerbranchloca.setVisibility(View.VISIBLE);
        }
        else {


            newaddress = AppPreferences.getInstance(getActivity()).getUSER_BRANCH_CODE();
        }*/
        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    String address = spinnerbranchloca.getSelectedItem().toString();

                    String ad[] = address.split("\\(");
                    newaddress = ad[1];
                    newaddress = newaddress.replace(")", "");
                } catch (Exception e) {
                    e.toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return rootView;
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
                        edit_time = horizontalList.get(position);
                        holder.tv_time.setTextColor(Color.BLACK);
                        if (edit_reason.getText().toString().length() > 1) {
                            if (AppPreferences.getInstance(getActivity()).getUSER_BRANCH_CODE().equals("")) {
                                Toast.makeText(getActivity(), "No clinic Branch added by you", Toast.LENGTH_LONG).show();
                            } else {
                                initProgressDialog("Please wait..");
                                addExpenseApiCall(edit_time);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Please add reason for appointments first", Toast.LENGTH_LONG).show();
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @Override
    protected void setListener() {
        super.setListener();


    }

    private void addExpenseApiCall1(final RecyclerView listview, final String booktime) {
        listview.setVisibility(View.VISIBLE);
        /*final String exp_billno = mPain_Nature.getText().toString().trim();
        final String exp_vendor = mPain_Onset.getText().toString().trim();
        final String exp_total = mPain_Duration.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://oldmaker.com/fijiyo/index.php/users/appoimentlistdatewise",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            try {
                                pDialog.hide();
                            } catch (Exception e) {
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
                                Log.e("jsonobj", "if");
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
                        HorizontalAdapter adapter = new HorizontalAdapter(getActivity(), arraytime);
                        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 5);
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
                params.put("appointment_branch_code", AppPreferences.getInstance(getActivity()).getUSER_BRANCH_CODE());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    private void addExpenseApiCall(final String edit_time) {
        /*final String exp_billno = mPain_Nature.getText().toString().trim();
        final String exp_vendor = mPain_Onset.getText().toString().trim();
        final String exp_total = mPain_Duration.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JsonURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("response", "" + response);
                            pDialog.hide();

                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("status");
                            if (result.equals("1")) {
                                Toast.makeText(getActivity(), "Appointment Successfully Added", Toast.LENGTH_LONG).show();
                                getFragmentManager().popBackStack();
                            } else {
                                Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_clinic));
                            }
                            Log.e("response", "" + response);
                        } catch (Exception e) {
                            Log.e("error", e.toString());

                        }
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
                params.put(KEY_PATIENTID, AppPreferences.getInstance(getActivity()).getUserID());
                params.put(KEY_DOCTORID, "aaa");
                params.put(KEY_DATEBOOKING, edit_date.getText().toString());
                params.put(KEY_BOOKINGSTARTTIME, edit_time);
                params.put("appointment_branch_code", AppPreferences.getInstance(getActivity()).getUSER_BRANCH_CODE());
                params.put(KEY_REASON, edit_reason.getText().toString());
                params.put("visit_type", selection);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class CallServices1 extends AsyncTask<String, String, String> {


        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String booktime;
        RecyclerView listview;

        public CallServices1(RecyclerView listview, String booktime) {
            this.booktime = booktime;
            this.listview = listview;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            listview.setVisibility(View.VISIBLE);
            try {
                pd = new ProgressDialog(getActivity());

                pd.setMessage("Working ...");
                pd.setIndeterminate(false);
                pd.setCancelable(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("enquiry_cl_code", "SPH"));
            namevaluepair.add(new BasicNameValuePair("enquiry_date", booktime));
            try {

                result = Util.executeHttpPost(params[0], namevaluepair);

                Log.e("result", result.toString());

            } catch (Exception e) {

                e.printStackTrace();

            }

            return result;


        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                if (pd != null) {
                    pd.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (result != null) {
                Log.e("resulthjjj", result.toString());
                try {
                    Log.e("response", "" + result);
//                    pDialog.hide();

                    JSONObject jsonObject = new JSONObject(result);
                    String resul = jsonObject.getString("success");
                    Log.e("jsonobj", "obj:-");
                    if (resul.equals("true")) {
                        Log.e("jsonobj", "if");
                        JSONArray jsonArray = jsonObject.optJSONArray("result");
                        for (int i = 0; i < jsonArray.length(); i++) {
//                            Log.e("jsonArray", "aray:-" + jsonArray.toString());
                            JSONObject jsonObject4 = jsonArray.optJSONObject(i);
                            Log.e("jsonobj", "obj:-" + jsonObject4.toString());

                            String time = jsonObject4.optString("enquiry_time");
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
                } catch (Exception e) {
                    Log.e("error", e.toString());

                }

                HorizontalAdapter adapter = new HorizontalAdapter(getActivity(), arraytime);
                GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 5);
                listview.setHasFixedSize(true);
                listview.setLayoutManager(layoutManager);
                listview.setAdapter(adapter);

            }
        }

    }
    /*private boolean isValid() {

        String cName = mPain_Nature.getText().toString().trim();
        String location = mPain_Onset.getText().toString().trim();
        String lat =mPain_Duration.getText().toString().trim();

        if (cName.isEmpty()) {
            mPain_Nature.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_bill_name));
            return false;
        }

        else if (location.isEmpty()) {
            mPain_Onset.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_vendor_name));
            return false;
        }

        else if (lat.isEmpty()) {
            mPain_Duration.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error),
                    getResources().getString(R.string.err_totl));
            return false;
        }


        return true;
    }*/

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
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String branch_name = jsonObject2.getString("branch_name");
                    String bracch_code = jsonObject2.getString("branch_code");
                    //branch_code
//                    arrayList.add(bracch_code);

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

    private void viewStaff() {
        getFragmentManager().popBackStack();
    }

    //    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:

                // set time picker as current time
                return new TimePickerDialog(getActivity(), timePickerListener, hour, minute, false);

            case 0:
                return new DatePickerDialog(getActivity(), datePickerListener, year, month, day);

        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            if (selectedMonth + 1 >= 10) {
                if (day >= 10) {
                    edit_date.setText(selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear);

                } else {
                    edit_date.setText("0" + selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear);
                }

            } else {
                if (day >= 10) {
                    edit_date.setText(selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear);

                } else {
                    edit_date.setText("0" + selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear);
                }

            }
        }
    };

    TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            int hour = hourOfDay;
            int minute = minutes;

            String time = hour + ":" + minute;
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
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String branch_name = jsonObject2.getString("branch_name");
                    String bracch_code = jsonObject2.getString("branch_code");
                    //branch_code
//                    arrayList.add(bracch_code);

                    detailApps = new InfoApps1();
                    detailApps.setName(branch_name);
                    detailApps.setId(bracch_code);
                    arrayList.add(detailApps.getName() + "  " + "(" + detailApps.getId() + ")");

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getActivity(), R.layout.dropsimpledown, arrayList);
                    spinnerbranchloca.setAdapter(spinnerArrayAdapter);

                }

            } catch (Exception e) {
                Log.e("error", e.toString());

            }
        }
    }

    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }


}