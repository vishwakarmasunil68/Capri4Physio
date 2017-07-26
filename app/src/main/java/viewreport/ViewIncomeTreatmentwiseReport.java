package viewreport;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.Invoice.InfoApps1;
import com.capri4physio.R;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 9/2/2016.
 */
public class ViewIncomeTreatmentwiseReport extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static ArrayList<InfoApps1> contactDetails1;
    InfoApps1 Detailapp;
    Spinner spinner1report, patientspinner;
    String treatmentpro, to_date;
    ArrayList<String> stringArrayList;
    private RecyclerView mRecyclerView;
    String GetURL = ApiConfig.BASE_URL + "users/invoiceview";
    private ProgressDialog pDialog;
    //    LocationAdapter5 locationAdapter5;
    public Button btn_submit;
    public static EditText ed1, ed2;
    Calendar myCalendar, myCalendar1;
    UsersAdapterIncomeTreatment mAdapter;
    ArrayList<String> arrayList;
    InfoApps1 detailApps;
    public static String item;
    TextView total, txdq, txt_total;
    Spinner spinnerbranchloca;
    public static String first_name, last_name, therapy, sfirst_name, slast_name, time_in, time_out, date;
    LinearLayout lin1, lin2, footer4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewincometreatment);
        patientspinner = (Spinner) findViewById(R.id.spinner2patient);
        txt_total = (TextView) findViewById(R.id.txt_total);
        total = (TextView) findViewById(R.id.total);
        spinnerbranchloca = (Spinner) findViewById(R.id.spinnerbranchloca);
        lin1 = (LinearLayout) findViewById(R.id.lin1);
        lin2 = (LinearLayout) findViewById(R.id.lin2);
        footer4 = (LinearLayout) findViewById(R.id.footer4);
        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (EditText) findViewById(R.id.ed2);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        arrayList = new ArrayList<String>();
        contactDetails1 = new ArrayList<InfoApps1>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed1.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please select all entries", Toast.LENGTH_LONG).show();

                }
                if (ed2.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please select all entries", Toast.LENGTH_LONG).show();
                }
                if (patientspinner.getSelectedItemPosition() < 1) {
                    Toast.makeText(getApplicationContext(), "Please select all entries", Toast.LENGTH_LONG).show();
                } else {
                    report8();
                }

            }
        });

        new CatagoryUrlAsynTask1().execute();
        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")) {
            spinnerbranchloca.setVisibility(View.VISIBLE);
        } else {
            item = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
            Log.e("item", item);
        }
        ed2.setVisibility(View.GONE);
        ed1.setVisibility(View.GONE);
        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                String address = spinnerbranchloca.getSelectedItem().toString();

                String ad[] = address.split("\\(");
                String newaddress = ad[1];
                item = newaddress.replace(")", "");

                try {
//                        listView.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ed1.setText(sdf.format(new Date()));
        ed2.setText(sdf.format(new Date()));


        ed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onDateSet();
                new DatePickerDialog(ViewIncomeTreatmentwiseReport.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onDateSet();
                new DatePickerDialog(ViewIncomeTreatmentwiseReport.this, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


//        treatmentpro= patientspinner.getSelectedItem().toString();

        patientspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void report8() {
        final String start_time = ed1.getText().toString().trim();
        final String end_time = ed2.getText().toString().trim();
        String treatment1 = patientspinner.getSelectedItem().toString().trim();
        final String treatment1___1 = patientspinner.getSelectedItem().toString().trim();
        treatment1 = treatment1.split(" ")[0];

        final String treatment = "[" + treatment1;
        final String treatment4 = treatment1;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_INCOME_TREATMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            /*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*/

//                            JSONObject objresponse = new JSONObject(response);


                            Log.e("response", "" + response);
                            JSONObject jsonArray = new JSONObject(response);


                            for (int i = 0; i < jsonArray.length() - 1; i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i + "");
                                Log.e("2", jsonObject2.toString());
                                String status = jsonObject2.getString("status");
                                if (status.equals("0")) {
                                    Toast.makeText(ViewIncomeTreatmentwiseReport.this, "no data found", Toast.LENGTH_LONG).show();

                                } else {
                                    String txt_tota = jsonArray.getString("total");
                                    txt_total.setText(txt_tota);
                                    String id = jsonObject2.getString("id");
                                    first_name = jsonObject2.getString("first_name");
                                    last_name = jsonObject2.getString("last_name");
                                    time_in = jsonObject2.getString("invo_bill_amount");
                                    date = jsonObject2.getString("invo_date");
                                    therapy = jsonObject2.getString("invo_product_name");
                                    String invo_pro_quanty = jsonObject2.getString("invo_pro_quanty");
                                    String invo_staff = jsonObject2.getString("invo_staff_name");
                                    Detailapp = new InfoApps1();

                                    Detailapp.setStaff_name(invo_staff);
                                    Detailapp.setName(first_name + " " + last_name);
                                    Detailapp.setPro_name(therapy);
                                    Detailapp.setStatus(time_in);
                                    Detailapp.setId(id);
                                    Detailapp.setInvo_date(date);
                                    Detailapp.setPro_quantity(treatment4);

                                    Collections.reverse(contactDetails1);
                                    contactDetails1.add(Detailapp);

                                    spinnerbranchloca.setVisibility(View.GONE);
                                    patientspinner.setVisibility(View.GONE);
                                    btn_submit.setVisibility(View.GONE);
                                    lin1.setVisibility(View.GONE);
                                    lin2.setVisibility(View.GONE);
                                    total.setVisibility(View.VISIBLE);
                                    txt_total.setVisibility(View.VISIBLE);
                                    footer4.setVisibility(View.VISIBLE);
                                }
                            }
                            mAdapter = new UsersAdapterIncomeTreatment(contactDetails1, ViewIncomeTreatmentwiseReport.this);
                            mRecyclerView.setAdapter(mAdapter);

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
                params.put("treatment_type", treatment1___1);
                params.put("invo_branch_code", item);
                /*params.put("trea_start_date", start_time);
                params.put("trea_end_date", end_time);
                params.put("branch_code", item);*/
//                Toast.makeText(UserStatement.this, username +"success", Toast.LENGTH_LONG).show();
//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String branch_name = jsonObject2.getString("branch_name");
                    String bracch_code = jsonObject2.getString("branch_code");

                    detailApps = new InfoApps1();
                    detailApps.setName(branch_name);
                    detailApps.setId(bracch_code);
                    arrayList.add(detailApps.getName() + "  " + "(" + detailApps.getId() + ")");
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.dropsimpledown, arrayList);
                    spinnerbranchloca.setAdapter(spinnerArrayAdapter);

                }

            } catch (Exception e) {
                Log.e("error", e.toString());

            }
        }
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


}
