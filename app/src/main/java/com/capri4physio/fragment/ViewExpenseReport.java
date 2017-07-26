package com.capri4physio.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.capri4physio.Invoice.InfoApps1;
import com.capri4physio.R;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.fragment.assessment.InfoApps;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import viewreport.ViewIncomeExpensetReport;

/**
 * Created by Emobi-Android-002 on 10/12/2016.
 */
public class ViewExpenseReport extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    LinearLayout linearLayout, linearLayout1;
    String GetURL = ApiConfig.BASE_URL+"users/getuserlist";
    public static String userid;
    Button btn;
    public static final int TIME_DIALOG_ID = 1111;
    private ProgressDialog pDialog;
    public static TextView p_nametext, bill_amounttext, paid_amounttext, due_amounttext, payment_modetext, statustext, bill_numbertext, bill_datetext, pctxet;
    public static EditText bill_amount, paid_amount, due_amount, payment_mode, status, bill_number, bill_date, pc, gender, address, city, Designation, send_date;
    InfoApps Detailapp;
    Spinner p_name, staff_name;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    Spinner spinner1report, patientspinner;
    Button btn_submit;
    LinearLayout linear1;
    public static ArrayList<String> patientaray;
    public static ArrayList<String> patientidarray = new ArrayList<String>();
    TextView add, manage, staffmanagenew;
    InfoApps1 detailapps;
    public static String newaddress;
    ArrayList<String> arrayList;
    Spinner spinnerbranchloca;
    private RecyclerView mRecyclerView;
    public static EditText ed1, ed2;
    Calendar myCalendar, myCalendar1;
    @BindView(R.id.from)
    ImageView iv_from;
    @BindView(R.id.to)
    ImageView iv_to;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenserepo);
        ButterKnife.bind(this);
        arrayList = new ArrayList<String>();
        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (EditText) findViewById(R.id.ed2);
        btn_submit= (Button) findViewById(R.id.btn_submit);
        spinnerbranchloca= (Spinner) findViewById(R.id.spinnerbranchloca);

        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")){
            spinnerbranchloca.setVisibility(View.VISIBLE);
        }

        newaddress = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
        Log.e("item",newaddress);

        new CatagoryViewAsynTask().execute();

        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String address = spinnerbranchloca.getSelectedItem().toString();

                String ad[]=address.split("\\(");
                newaddress = ad[1];
                newaddress =newaddress.replace(")", "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        myCalendar1 = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed1.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please select date",Toast.LENGTH_LONG).show();

                }
                if (ed2.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please select date",Toast.LENGTH_LONG).show();
                }
                else {
                    startActivity(new Intent(ViewExpenseReport.this, ViewIncomeExpensetReport.class));
                }
            }
        });

        /*setListener();
        viewStaffApiCall();*/

        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        ed1.setText( sdf.format( new Date() ));
        ed2.setText( sdf.format( new Date() ));

        iv_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ViewExpenseReport.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        iv_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ViewExpenseReport.this, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ed1.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel1() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ed2.setText(sdf.format(myCalendar1.getTime()));
    }



    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }



    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(getApplicationContext());
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }

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

                    detailapps = new InfoApps1();
                    detailapps.setName(branch_name);
                    detailapps.setId(bracch_code);
                    arrayList.add(detailapps.getName() + "  " + "(" + detailapps.getId() + ")");

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.dropsimpledown, arrayList);
                    spinnerbranchloca.setAdapter(spinnerArrayAdapter);
                }
                }catch(Exception e){
                    Log.e("error", e.toString());

                }
            }
        }
}
