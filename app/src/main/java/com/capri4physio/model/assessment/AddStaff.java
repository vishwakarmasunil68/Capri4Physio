package com.capri4physio.model.assessment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.capri4physio.R;
import com.capri4physio.Services.WebServices;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.fragment.assessment.InfoApps;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 7/21/2016.
 */
public class AddStaff extends AppCompatActivity {
    LinearLayout linear, linear1, linear3;
    LinearLayout linear2;
    private Calendar cal;
    private int day;
    private int month;
    private int year;

    private int hour;
    private int minute;
    EditText edtxt_name, edtxt_lastname, edtxt_dob, edtxt_dojoining, edtxt_edocontract, edtxt_designation, edtxt_address, edtxt_city, edtxt_pin_code, edtxt_phone, edtxt_degree, edtxt_Institution, edtxt_University, edtxt_Duration, edtxt_Average, edtxt_Organisation, edtxt_experienceDesignation, edtxt_experienceDuration, edtxt_work, edtxt_email, edtxt_password;
    ImageView imageView1, imageView2, imageView3, imageView4;
    String ADD_STAFF_URL = ApiConfig.BASE_URL+"users/addstaff";
    public static final String SIGNIN_URL = ApiConfig.BASE_URL+"users/moterexam";
    Button savebtn;
    RadioGroup rg, rg1;
    private ProgressDialog pDialog;
    String rate = "Mail";
    String rate1 = "Single";
    public static final int TIME_DIALOG_ID = 1111;
    ArrayList<String> arrayList = new ArrayList<>();
    String item;
    InfoApps Detailapp;
    String branch_code;
    Spinner spinnerbranchloca;
    TextView tv_contact_info;


    String[] location ={"Gurgaon","Sant Parmanand Hospital","Greater Kailash 1","Karkarduma"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewstaff);
        imageView1 = (ImageView) findViewById(R.id.image1);
        imageView2 = (ImageView) findViewById(R.id.image2);
        imageView3 = (ImageView) findViewById(R.id.image3);
        imageView4 = (ImageView) findViewById(R.id.image4);
        linear = (LinearLayout) findViewById(R.id.linear1);
        linear1 = (LinearLayout) findViewById(R.id.linear2);
        linear2 = (LinearLayout) findViewById(R.id.linear3);
        linear3 = (LinearLayout) findViewById(R.id.linear4);
        edtxt_name = (EditText) findViewById(R.id.edtxt_name);
        edtxt_lastname = (EditText) findViewById(R.id.edtxt_lastname);
        edtxt_dob = (EditText) findViewById(R.id.edtxt_dob);
        edtxt_dojoining = (EditText) findViewById(R.id.edtxt_dojoining);
        edtxt_edocontract = (EditText) findViewById(R.id.edtxt_edocontract);
        edtxt_designation = (EditText) findViewById(R.id.edtxt_designation);
        edtxt_address = (EditText) findViewById(R.id.edtxt_address);
        edtxt_city = (EditText) findViewById(R.id.edtxt_city);
        edtxt_pin_code = (EditText) findViewById(R.id.edtxt_pin_code);
        edtxt_phone = (EditText) findViewById(R.id.edtxt_phone);
        edtxt_degree = (EditText) findViewById(R.id.edtxt_degree);
        edtxt_Institution = (EditText) findViewById(R.id.edtxt_Institution);
        edtxt_University = (EditText) findViewById(R.id.edtxt_University);
        edtxt_Duration = (EditText) findViewById(R.id.edtxt_Duration);
        edtxt_Average = (EditText) findViewById(R.id.edtxt_Average);
        edtxt_Organisation = (EditText) findViewById(R.id.edtxt_Organisation);
        edtxt_experienceDesignation = (EditText) findViewById(R.id.edtxt_experienceDesignation);
        edtxt_experienceDuration = (EditText) findViewById(R.id.edtxt_experienceDuration);
        edtxt_work = (EditText) findViewById(R.id.edtxt_work);
        edtxt_email = (EditText) findViewById(R.id.edtxt_email);
        savebtn = (Button) findViewById(R.id.savebtn);
        rg = (RadioGroup) findViewById(R.id.rgforgen);
        spinnerbranchloca= (Spinner) findViewById(R.id.spinnerbranchloca);
        rg1 = (RadioGroup) findViewById(R.id.rgformaried);
        edtxt_password = (EditText) findViewById(R.id.edtxt_password);
        tv_contact_info = (TextView) findViewById(R.id.tv_contact_info);

        arrayList.add("Select Branch");
        new CatagoryUrlAsynTask1().execute();

        edtxt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });

        edtxt_dojoining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });

        edtxt_edocontract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(2);
            }
        });

        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        hour = cal.get(Calendar.HOUR);
        minute = cal.get(Calendar.MINUTE);

        edtxt_dob.setText(year + "-" + (month + 1) + "-"
                + day);

        edtxt_dojoining.setText(year + "-" + (month + 1) + "-"
                + day);
        edtxt_edocontract.setText(year + "-" + (month + 1) + "-"
                + day);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linear.getVisibility() == View.VISIBLE) {
                    linear.setVisibility(View.GONE);
                } else {
                    linear.setVisibility(View.VISIBLE);
                }
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linear1.getVisibility() == View.VISIBLE) {
                    linear1.setVisibility(View.GONE);
                } else {
                    linear1.setVisibility(View.VISIBLE);
                }
            }
        });

        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")){
            spinnerbranchloca.setVisibility(View.VISIBLE);
        }


        item = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
        Log.e("item",item);
        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if (i==0){
                 item  = " ";
                }
                else{

                    String address = spinnerbranchloca.getSelectedItem().toString();

                    String ad[]=address.split("\\(");
                    String newaddress = ad[1];
                    item =newaddress.replace(")", "");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linear2.getVisibility() == View.VISIBLE) {
                    linear2.setVisibility(View.GONE);
                } else {
                    linear2.setVisibility(View.VISIBLE);
                }
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linear3.getVisibility() == View.VISIBLE) {
                    linear3.setVisibility(View.GONE);
                } else {
                    linear.setVisibility(View.GONE);
                    linear1.setVisibility(View.GONE);
                    linear2.setVisibility(View.GONE);
                    linear3.setVisibility(View.VISIBLE);
                }
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.equals(" ")){
                    Toast.makeText(AddStaff.this,"Plesae Select Branch Name",Toast.LENGTH_LONG).show();
                }
                else {
                    initProgressDialog("Please wait...");
                    addStaff();
                }
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos = rg.indexOfChild(findViewById(checkedId));
                /*Toast.makeText(getBaseContext(), "Method 1 ID = " + String.valueOf(pos),
                        Toast.LENGTH_SHORT).show();*/
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();
                /*Intent intent1=new Intent(TimeSetActivity.this,DataMovement.class);
                intent1.putExtra("timeindex0",pos);
                startActivity(intent1);*/
                switch (pos) {

                    case 0:
                        rate = "Male";
                        break;
                    case 1:
                        rate = "Female";
                        break;
                    default:
                        break;
                }
            }
        });
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos = rg1.indexOfChild(findViewById(checkedId));
                /*Toast.makeText(getBaseContext(), "Method 1 ID = " + String.valueOf(pos),
                        Toast.LENGTH_SHORT).show();*/
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();
                /*Intent intent1=new Intent(TimeSetActivity.this,DataMovement.class);
                intent1.putExtra("timeindex0",pos);
                startActivity(intent1);*/
                switch (pos) {

                    case 0:
                        rate1 = "Single";
                        break;
                    case 1:
                        rate1 = "Married";
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:

                // set time picker as current time
                return new TimePickerDialog(this, timePickerListener, hour, minute, false);

            case 0:
                return new DatePickerDialog(this, datePickerListener, year, month, day);

            case 1:
                return new DatePickerDialog(this, datePickerListener1, year, month, day);

            case 2:
                return new DatePickerDialog(this, datePickerListener2, year, month, day);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            if (selectedMonth + 1 >= 10) {
                edtxt_dob.setText(selectedYear + "-" + (selectedMonth + 1) + "-"
                        + selectedDay);

            } else {

                edtxt_dob.setText(selectedYear + "-" + "0" + (selectedMonth + 1) + "-"
                        + selectedDay);
            }
        }
    };


    private DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            if (selectedMonth + 1 >= 10) {
                edtxt_dojoining.setText(selectedYear + "-" + (selectedMonth + 1) + "-"
                        + selectedDay);

            } else {

                edtxt_dojoining.setText(selectedYear + "-" + "0" + (selectedMonth + 1) + "-"
                        + selectedDay);
            }
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListener2 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            if (selectedMonth + 1 >= 10) {
                edtxt_edocontract.setText(selectedYear + "-" + (selectedMonth + 1) + "-"
                        + selectedDay);

            } else {

                edtxt_edocontract.setText(selectedYear + "-" + "0" + (selectedMonth + 1) + "-"
                        + selectedDay);
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

    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private final static String TAG = "AddStaff";

    private void addStaff() {
        final String name = edtxt_name.getText().toString().trim();
        final String lastName = edtxt_lastname.getText().toString().trim();
        final String password = edtxt_password.getText().toString().trim();
        final String dob = edtxt_dob.getText().toString().trim();
        final String doj = edtxt_dojoining.getText().toString().trim();
        final String endingdateofcontract = edtxt_edocontract.getText().toString().trim();
        final String designation = edtxt_designation.getText().toString().trim();
        final String address = edtxt_address.getText().toString().trim();
        final String city = edtxt_city.getText().toString().trim();
        final String pin_code = edtxt_pin_code.getText().toString().trim();
        final String phone = edtxt_phone.getText().toString().trim();
        final String degree = edtxt_degree.getText().toString().trim();
        final String experienceDesignation = edtxt_experienceDesignation.getText().toString().trim();
        final String email_id = edtxt_email.getText().toString().trim();
        final String institution = edtxt_Institution.getText().toString().trim();
        final String University = edtxt_University.getText().toString().trim();
        final String duration = edtxt_Duration.getText().toString().trim();
        final String average = edtxt_Average.getText().toString().trim();
        final String organisation = edtxt_Organisation.getText().toString().trim();
        final String experienceduration = edtxt_experienceDuration.getText().toString().trim();
        final String work = edtxt_work.getText().toString().trim();

        Map<String, String> params = new HashMap<String, String>();
//                params.put("first_name",name);
//                params.put("last_name",lastName);
//                params.put("mobile",phone);
//                params.put("email",email_id);
//                params.put("password",password);
//                params.put("address",name);
//                params.put("user_type","2");
//                params.put("dob",dob);
//                params.put("gender",rate);
//                params.put("age","23");
//                params.put("pincode",pin_code);
//                params.put("phone",phone);
//                params.put("treatment_type","OPD");
//                params.put("designation",designation);
//                params.put("qualifation",degree);
//                params.put("exprience",experienceduration);

        new AddStaffService(name, lastName, phone, email_id, password, address, "1", dob,
                rate, "23", pin_code, phone, "OPD", designation, degree, doj,experienceDesignation,city
        ,institution,degree,work,endingdateofcontract,University,average,experienceduration,organisation).execute();



        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_STAFF_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            Log.d(TAG,response);
//                            if(pDialog!=null)
//                            pDialog.dismiss();
//                            Toast.makeText(AddStaff.this,"Record Submitted Successfully", Toast.LENGTH_LONG).show();
//                            finish();
//                        } catch (Exception e) {
//                            if(pDialog!=null)
//                                pDialog.dismiss();
//                            e.printStackTrace();
//                        }
////                        startActivity(new Intent(StmtActivity.this, PinDoMatch.class));
//                        Log.e(TAG, "" + response.toString());
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
//                        if(pDialog!=null)
//                            pDialog.dismiss();
//                        Log.w(TAG, "" + error);
//                    }
//                }){
//
//
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("first_name",name);
//                params.put("last_name",lastName);
//                params.put("mobile",phone);
//                params.put("email",email_id);
//                params.put("password",password);
//                params.put("address",name);
//                params.put("user_type","2");
//                params.put("dob",dob);
//                params.put("gender",rate);
//                params.put("age","23");
//                params.put("pincode",pin_code);
//                params.put("phone",phone);
//                params.put("treatment_type","OPD");
//                params.put("designation",designation);
//                params.put("qualifation",degree);
//                params.put("exprience",experienceduration);
//
////
////
////
////
////                params.put("sfirst_name",name);
////                params.put("slast_name",lastName);
////                params.put("staff_pass",password);
////                params.put("staff_user_type","2");
////                params.put("sdob",dob);
////                params.put("sage", "23");
////                params.put("sdatejoing",doj);
////                params.put("senddate", endingdateofcontract);
////                params.put("sgender", rate);
////                params.put("smarital_status", rate1);
////                params.put("sdesignation",designation);
////                params.put("saddress", address);
////                params.put("scity", city);
////                params.put("spincode", pin_code);
////                params.put("smobile", phone);
////                params.put("semail", email_id);
////                params.put("squalifation", degree);
////                params.put("sexprience", experienceduration);
//
////                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
//                return params;
//            }
//
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
    }

    public class AddStaffService extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        String first_name;
        String last_name;
        String mobile;
        String email;
        String password;
        String address;
        String user_type;
        String dob;
        String gender;
        String age;
        String pincode;
        String phone;
        String treatment_type;
        String designation;
        String qualifation;
        String doj;
        String city;
        String exprience;
        String institution;
        String degree;
        String work;
        String endingdateofcontract;
        String University;
        String average;
        String experienceduration;
        String organisation;


        public AddStaffService(String first_name,
                               String last_name,
                               String mobile,
                               String email,
                               String password,
                               String address,
                               String user_type,
                               String dob,
                               String gender,
                               String age,
                               String pincode,
                               String phone,
                               String treatment_type,
                               String exprience,
                               String qualifation,
                               String doj ,String designation,String city
                             ,String  institution,String degree,String work,String endingdateofcontract,
                               String University ,String average,String experienceduration,
                               String organisation ) {
            this.first_name = first_name;
            this.last_name = last_name;
            this.mobile = mobile;
            this.email = email;
            this.password = password;
            this.address = address;
            this.user_type = user_type;
            this.dob = dob;
            this.gender = gender;
            this.age = age;
            this.pincode = pincode;
            this.phone = phone;
            this.treatment_type = treatment_type;
            this.designation = designation;
            this.qualifation = qualifation;
            this.doj = doj;
            this.city = city;
            this.exprience = exprience;
            this.age = age;
            this.average = average;
            this.experienceduration = experienceduration;
            this.organisation = organisation;
            this.institution = institution;
            this.degree = degree;
            this.work = work;
            this.endingdateofcontract = endingdateofcontract;
            this.University = University;

            Log.d("endingdateofcontract","parameters:-"+endingdateofcontract.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(mContext, "Please wait...", "Adding Habit");
//            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("first_name", first_name));
            nameValuePairs.add(new BasicNameValuePair("added_by", item));
            nameValuePairs.add(new BasicNameValuePair("last_name", last_name));
            nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            nameValuePairs.add(new BasicNameValuePair("address", address));
            nameValuePairs.add(new BasicNameValuePair("user_type", user_type));
            nameValuePairs.add(new BasicNameValuePair("status", "1"));
            nameValuePairs.add(new BasicNameValuePair("dob", dob));
            nameValuePairs.add(new BasicNameValuePair("gender", gender));
            nameValuePairs.add(new BasicNameValuePair("age", age));
            nameValuePairs.add(new BasicNameValuePair("pincode", pincode));
            nameValuePairs.add(new BasicNameValuePair("phone", phone));
            nameValuePairs.add(new BasicNameValuePair("treatment_type", treatment_type));
            nameValuePairs.add(new BasicNameValuePair("designation", designation));
            nameValuePairs.add(new BasicNameValuePair("qualifation", qualifation));
            nameValuePairs.add(new BasicNameValuePair("datejoing", doj));
            nameValuePairs.add(new BasicNameValuePair("city", city));
            nameValuePairs.add(new BasicNameValuePair("designation1", exprience));
            nameValuePairs.add(new BasicNameValuePair("end_date_contract", endingdateofcontract));
            nameValuePairs.add(new BasicNameValuePair("degree", degree));
            nameValuePairs.add(new BasicNameValuePair("institution",institution ));
            nameValuePairs.add(new BasicNameValuePair("university", University));
            nameValuePairs.add(new BasicNameValuePair("duration", experienceduration));
            nameValuePairs.add(new BasicNameValuePair("average", average));
            nameValuePairs.add(new BasicNameValuePair("organisation", organisation));
            nameValuePairs.add(new BasicNameValuePair("nature_of_work", work));
            try {
                jResult = WebServices.httpCall(WebServices.ADD_STAFF_URL, nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jResult;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            if (pDialog != null)
                pDialog.dismiss();
            try {
                Log.d(TAG, aVoid);
                JSONObject jsonObject = new JSONObject(aVoid);
                String success = jsonObject.optString("status");
                if (success.equals("1")) {
                        String message=jsonObject.optString("message");
                    if(message.equals("Staff request has been sent successfully.")){
                        Toast.makeText(getApplicationContext(),"Staff added Successfully",Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public String toString() {
            return "AddStaffService{" +
                    "nameValuePairs=" + nameValuePairs +
                    ", jResult='" + jResult + '\'' +
                    ", first_name='" + first_name + '\'' +
                    ", last_name='" + last_name + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", address='" + address + '\'' +
                    ", user_type='" + user_type + '\'' +
                    ", dob='" + dob + '\'' +
                    ", gender='" + gender + '\'' +
                    ", age='" + age + '\'' +
                    ", pincode='" + pincode + '\'' +
                    ", phone='" + phone + '\'' +
                    ", treatment_type='" + treatment_type + '\'' +
                    ", designation='" + designation + '\'' +
                    ", qualifation='" + qualifation + '\'' +
                    ", exprience='" + exprience + '\'' +
                    '}';
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

                    Detailapp = new InfoApps();
                    Detailapp.setName(branch_name);
                    Detailapp.setId(bracch_code);
                    arrayList.add(Detailapp.getName() + "  "+ "(" + Detailapp.getId()+ ")");

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.dropsimpledown, arrayList);
                    spinnerbranchloca.setAdapter(spinnerArrayAdapter);

                }

            }catch(Exception e){
                Log.e("error",e.toString());

            }
        }
    }

}
