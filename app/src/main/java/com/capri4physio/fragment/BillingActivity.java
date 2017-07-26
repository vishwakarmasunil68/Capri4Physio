package com.capri4physio.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
import com.capri4physio.adapter.UsersAdapter;
import com.capri4physio.fragment.assessment.BillAmountDialog;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.fragment.assessment.InfoApps;
import com.capri4physio.model.UserItem;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.Utils;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 8/30/2016.
 */
public class BillingActivity extends AppCompatActivity {
    LinearLayout linearLayout, linearLayout1;
    String GetURL = ApiConfig.BASE_URL+"users/getuserlist";
    FrameLayout frameLayout;
    public static String userid,payamount_quan;
    public static int payamount_item,payamount_itemquanitiy,finalpayableamoun,sum;
    Button btn,buton;
    public static final int TIME_DIALOG_ID = 1111;
    private ProgressDialog pDialog;
    TextView text_bill__GST;
    public static TextView p_nametext, txt_ok,bill_amounttext, paid_amounttext, due_amounttext, payment_modetext, statustext, bill_numbertext, bill_datetext, staffnametext,pro_name,pro_quant;
    public static EditText bill_amount, paid_amount, due_amount ,bill_number, bill_date, pc, gender, address, city, Designation, send_date;
    InfoApps Detailapp;
    Spinner p_name,status,payment_mode,staff_name,invo_item_quant,Item_name;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    public static ArrayList<String> patientaray;
    public static ArrayList<String> patientidarray = new ArrayList<String>();
    String id,patient_id;
    ArrayList<String> arrayList;
    public static String item;
    public static String admingst_tax;
    InfoApps1 detailApps;
    Spinner spinnerbranchloca;
    TextView add, manage, staffmanagenew;
    private RecyclerView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    Boolean flag =false;
    public static ArrayList<String>selectedItemcost= new ArrayList<String>();
    public static ArrayList<String>selectedItem= new ArrayList<String>();
    public  ArrayList<String>selequantity= new ArrayList<String>();
    Map<String,String> objresponse;
    int total_quant = 0;
    public static ArrayList<String> staffarray,patientspinneraray;
    private UsersAdapter mAdapter;
    private List<UserItem> mList;
    private int itemPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewreport);

        patientspinneraray = new ArrayList<String>();
        staffarray = new ArrayList<String>();
        btn = (Button) findViewById(R.id.savebtn);
        buton = (Button) findViewById(R.id.savebuton);
        /*categories.add("");
        categories.add("");
        categories.add("");
        categories.add("");
        categories.add("");
        categories.add("");
        categories.add("");
        categories.add("");
        categories.add("");
        categories.add("");
        categories.add("");
        categories.add("");
        categories.add("");
        categories.add("Pneumatic Compression : Rs. 600/- per visit x 2 (1000 Rs)");
        categories.add("Cryo compression : Rs.400/- per visit x 2 (600 Rs)");*/

        linearLayout = (LinearLayout) findViewById(R.id.add_billing_layout);
        linearLayout1 = (LinearLayout) findViewById(R.id.view_billing_layout);
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        p_name = (Spinner) findViewById(R.id.invo_patient_name);
        staff_name = (Spinner) findViewById(R.id.invo_staff_name);
        Item_name = (Spinner) findViewById(R.id.invo_product_name);
        invo_item_quant = (Spinner) findViewById(R.id.invo_item_quant);
        /*bill_amount = (EditText) findViewById(R.id.invo_bill_amount);
        paid_amount = (EditText) findViewById(R.id.invo_paid_amount);
        due_amount = (EditText) findViewById(R.id.invo_due_amount);*/
        payment_mode = (Spinner) findViewById(R.id.invo_payment_mode);
        arrayList = new ArrayList<String>();
        arrayList.add("Select Branch Name");
        spinnerbranchloca = (Spinner) findViewById(R.id.spinnerbranchloca);
        status = (Spinner) findViewById(R.id.invo_status);
        /*bill_number = (EditText) findViewById(R.id.invo_billno);
        bill_date = (EditText) findViewById(R.id.invo_date);*/

        p_nametext = (TextView) findViewById(R.id.textinvo_patient_name);
        text_bill__GST = (TextView) findViewById(R.id.text_bill__GST);
        bill_amounttext = (TextView) findViewById(R.id.textinvo_bill_amount);
        paid_amounttext = (TextView) findViewById(R.id.textinvo_paid_amount);
        due_amounttext = (TextView) findViewById(R.id.textinvo_due_amount);
        payment_modetext = (TextView) findViewById(R.id.textinvo_payment_mode);
        statustext = (TextView) findViewById(R.id.textinvo_status);
        bill_numbertext = (TextView) findViewById(R.id.textinvo_billno);
        bill_datetext = (TextView) findViewById(R.id.textinvo_date);
        staffnametext = (TextView) findViewById(R.id.textinvo_staff_name);
        pro_name = (TextView) findViewById(R.id.textinvo_pro_name);
        pro_quant = (TextView) findViewById(R.id.textinvo_pro_quan);
       final View linearLayout =  findViewById(R.id.add_billing_treatment);
        final View linearLayout1 =  findViewById(R.id.add_billing_treatment1);
        final View linearLayout2 =  findViewById(R.id.add_billing_treatment2);
        txt_ok  = (TextView) findViewById(R.id.txt_ok);
        new CatagoryUrlAsynTask().execute();
        new CatagoryUrlAsynTask1().execute();


        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")){
            spinnerbranchloca.setVisibility(View.VISIBLE);


        }
        else {
            item = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
            viewStaffApiCall();
            Log.e("item", item);
        }

        //LinearLayout layout = (LinearLayout) findViewById(R.id.info);

        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    try {
//                        patientaray.clear();
                        staffarray.clear();

                        patientspinneraray.clear();
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(), R.layout.dropsimpledown, staffarray);
                        staff_name.setAdapter(spinnerArrayAdapter);

                        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                                getApplicationContext(), R.layout.dropsimpledown, patientspinneraray);
                        p_name.setAdapter(spinnerArrayAdapter1);
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

try{
    selectedItemcost.clear();
    selectedItem.clear();
}
catch (Exception e){
    e.printStackTrace();
}

        /*ArrayList<String> name_list1=new ArrayList<>();
        for(InfoApps apps:ViewBillingFragment.patientspinneraray){
            name_list1.add(apps.getName());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, name_list1);
        p_name.setAdapter(spinnerArrayAdapter);

        ArrayList<String> name_list=new ArrayList<>();
        for(InfoApps apps:ViewBillingFragment.staffarray){
            name_list.add(apps.getName());
        }
        ArrayAdapter<String> staffArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, name_list);
        staff_name.setAdapter(staffArrayAdapter);*/

        /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Item_name.setAdapter(dataAdapter);*/
        Item_name.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        hour = cal.get(Calendar.HOUR);
        minute = cal.get(Calendar.MINUTE);

        /*bill_date.setText(year + "-" + (month + 1) + "-"
                + day);
        bill_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });*/

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buton.setVisibility(View.VISIBLE);
//                initProgressDialog("loading");
                int positionitem = Item_name.getSelectedItemPosition();
                int positionitemquantity = invo_item_quant.getSelectedItemPosition();
                Log.e("positionitem", String.valueOf(positionitem));
                Log.e("positionitemquantity", String.valueOf(positionitemquantity));
                switch (positionitem) {
                    case 0:
                        payamount_item = 150;
                        break;
                    case 1:
                        payamount_item = 150;
                        break;

                    case 2:
                        payamount_item = 150;
                        break;

                    case 3:
                        payamount_item = 100;
                        break;

                    case 4:
                        payamount_item = 200;
                        break;

                    case 5:
                        payamount_item = 300;
                        break;

                    case 6:
                        payamount_item = 100;
                        break;

                    case 7:
                        payamount_item = 500;
                        break;

                    case 8:
                        payamount_item = 300;
                        break;

                    case 9:
                        payamount_item = 100;
                        break;

                    case 10:
                        payamount_item = 400;
                        break;

                    case 11:
                        payamount_item = 400;
                        break;

                    case 12:
                        payamount_item = 300;
                        break;

                    case 13:
                        payamount_item = 400;
                        break;

                    case 14:
                        payamount_item = 600;
                        break;

                    case 15:
                        payamount_item = 400;
                        break;
                    default:
                        return;
                }
                switch (positionitemquantity) {
                    case 0:
                        payamount_itemquanitiy = 1;
                        break;
                    case 1:
                        payamount_itemquanitiy = 2;
                        break;

                    case 2:
                        payamount_itemquanitiy = 3;
                        break;

                    case 3:
                        payamount_itemquanitiy = 4;
                        break;

                    case 4:
                        payamount_itemquanitiy = 5;
                        break;
                    default:
                        return;

                }
                finalpayableamoun = (payamount_item) * (payamount_itemquanitiy);
                Log.e("finalpayableamoun", String.valueOf(finalpayableamoun));
//                viewbilling();
                Intent intent = new Intent(BillingActivity.this, BillAmountDialog.class);
//                String payablestringvalue=Double.toString(finalpayableamoun);
                intent.putExtra("finalpayableamoun", sum + "");
                startActivity(intent);
            }
        });
        /*setListener();
        viewStaffApiCall();*/
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    viewbilling();
                }
                catch (Exception e){
                    Toast.makeText(BillingActivity.this,"fill all filels.",Toast.LENGTH_LONG).show();
                }

            }
        });
        txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int positionitem = Item_name.getSelectedItemPosition();
                if (positionitem == 0) {
Toast.makeText(getApplicationContext(),"Please select treatment",Toast.LENGTH_SHORT).show();
                } else {
                    String selecteddata = Item_name.getSelectedItem().toString();
                    int positionitemquantity = invo_item_quant.getSelectedItemPosition();
                    Log.e("positionitem", String.valueOf(positionitem));
                    Log.e("positionitemquantity", String.valueOf(positionitemquantity));
                    switch (positionitem) {
                        case 0:
                            payamount_item = 150;
                            break;
                        case 1:
                            payamount_item = 150;
                            break;

                        case 2:
                            payamount_item = 150;
                            break;

                        case 3:
                            payamount_item = 100;
                            break;

                        case 4:
                            payamount_item = 200;
                            break;

                        case 5:
                            payamount_item = 300;
                            break;

                        case 6:
                            payamount_item = 100;
                            break;

                        case 7:
                            payamount_item = 500;
                            break;

                        case 8:
                            payamount_item = 300;
                            break;

                        case 9:
                            payamount_item = 100;
                            break;

                        case 10:
                            payamount_item = 400;
                            break;

                        case 11:
                            payamount_item = 400;
                            break;

                        case 12:
                            payamount_item = 300;
                            break;

                        case 13:
                            payamount_item = 400;
                            break;

                        case 14:
                            payamount_item = 600;
                            break;

                        case 15:
                            payamount_item = 400;
                            break;
                        default:
                            return;
                    }
                    switch (positionitemquantity) {
                        case 0:
                            payamount_itemquanitiy = 1;
                            break;
                        case 1:
                            payamount_itemquanitiy = 2;
                            break;

                        case 2:
                            payamount_itemquanitiy = 3;
                            break;

                        case 3:
                            payamount_itemquanitiy = 4;
                            break;

                        case 4:
                            payamount_itemquanitiy = 5;
                            break;
                        default:
                            return;

                    }
                    finalpayableamoun = (payamount_item) * (payamount_itemquanitiy);
                    Log.e("finalpayableamoun", String.valueOf(finalpayableamoun));
                    String payableamount = finalpayableamoun + "";
                    selectedItemcost.add(payableamount);
                    sum = 0;
                    for (int i = 0; i < selectedItemcost.size(); i++) {
                        sum += Integer.parseInt(selectedItemcost.get(i));
                    }
               /* String cost =selectedItemcost.toString();
                String cost1[] =cost.split(",");
                for (String pos: cost1){
                    Log.e("finalpayable", String.valueOf(pos));
                }
                Log.e("finalpayableamoun", String.valueOf(selectedItemcost));*/
                    try {
                        TextView valueTV = new TextView(getApplicationContext());
                        valueTV.setText(String.valueOf(selecteddata));
                        valueTV.setTextColor(Color.BLACK);
                        valueTV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        String invo_itemquant = invo_item_quant.getSelectedItem().toString();
                        TextView valueTV1 = new TextView(getApplicationContext());
                        valueTV1.setTextColor(Color.BLACK);
                        valueTV1.setText(String.valueOf(finalpayableamoun) + "Rs.");
                        valueTV1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                        String positionitem1_ = Item_name.getSelectedItem().toString()+ "-"+ invo_item_quant.getSelectedItem().toString();
                        String positionitem2_ = Item_name.getSelectedItem().toString();
                        ((LinearLayout) linearLayout).addView(valueTV);
                        ((LinearLayout) linearLayout1).addView(valueTV1);
//                        ((LinearLayout) linearLayout2).addView(valueTV);
                        selectedItem.add(positionitem2_);
                        int quantity = Integer.parseInt(invo_item_quant.getSelectedItem().toString());
                        selequantity.add(invo_item_quant.getSelectedItem().toString());
                        total_quant = total_quant+ quantity;
                        Log.e("values", selectedItem.toString());
                    } catch (Exception e) {
                        Log.e("exceptionn", e.toString());
                    }
                }
            }
        });
        /*Item_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                if (position>0) {
                    selectedItem = new ArrayList<String>();
                    String positionitem = Item_name.getSelectedItem().toString();
                    TextView valueTV = new TextView(getApplicationContext());
                    valueTV.setText(positionitem);
                    valueTV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    String invo_itemquant = invo_item_quant.getSelectedItem().toString();
                    TextView valueTV1 = new TextView(getApplicationContext());
                    valueTV1.setText(invo_itemquant);
                    valueTV1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                    String positionitem = Item_name.getSelectedItem().toString();
                    TextView valueTV = new TextView(getApplicationContext());
                    valueTV.setText(positionitem);
                    valueTV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    ((LinearLayout) linearLayout).addView(valueTV);
                    ((LinearLayout) linearLayout1).addView(valueTV1);
                    ((LinearLayout) linearLayout2).addView(valueTV);
                    selectedItem.add(positionitem);
                    Log.e("values", selectedItem.toString());
                }
                }
                catch (Exception e){
                    Log.e("exceptionn", e.toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
        /*invo_item_quant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if (position>0) {
                        selectedItem = new ArrayList<String>();
                        String invo_itemquant = invo_item_quant.getSelectedItem().toString();
                        int posi=invo_item_quant.getSelectedItemPosition();
                        String positionitem = Item_name.getSelectedItem().toString();
                        switch (posi){
                            case 0:
                                payamount_itemquanitiy = 1;
                                break;
                            case 1:
                                payamount_itemquanitiy = 2;
                                break;

                            case 2:
                                payamount_itemquanitiy = 3;
                                break;

                            case 3:
                                payamount_itemquanitiy = 4;
                                break;

                            case 4:
                                payamount_itemquanitiy = 5;
                                break;
                            default:
                                return;

                        }
                        TextView valueTV1 = new TextView(getApplicationContext());
                        valueTV1.setText(invo_itemquant);
                        valueTV1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                        String positionitem = Item_name.getSelectedItem().toString();
                        TextView valueTV = new TextView(getApplicationContext());
                        valueTV.setText(positionitem);
                        valueTV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        ((LinearLayout) linearLayout).addView(valueTV);
                        ((LinearLayout) linearLayout1).addView(valueTV1);
                        ((LinearLayout) linearLayout2).addView(valueTV);
                        selectedItem.add(positionitem);
                        Log.e("values", selectedItem.toString());
                    }
                }
                catch (Exception e){
                    Log.e("exceptionn", e.toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main1, menu);

        if (!flag) {
            menu.setGroupVisible(R.id.action_about,false);
        }
        else {
            menu.setGroupVisible(R.id.action_about,true);
        }
        return true;
    }
    private void viewStaffApiCall() {

        try {
            patientaray.clear();
            staffarray.clear();



        }
        catch (Exception e){
            Log.e("kabe",e.toString());
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_PATIENT_STAFF_FILTERATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            try {
                                patientaray.clear();
                                staffarray.clear();
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
                                    String last_name = jsonObject2.getString("last_name");
                                    staffarray.add(sfirst_name+ " "+ last_name);
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                            getApplicationContext(), R.layout.dropsimpledown, staffarray);
                                    staff_name.setAdapter(spinnerArrayAdapter);
                                }
                                if (type.equals("0")){
                                    String id = jsonObject2.getString("id");
                                    String sfirst_name = jsonObject2.getString("first_name");
                                    String last_name = jsonObject2.getString("last_name");
                                    Detailapp = new InfoApps();
                                    Detailapp.setName(sfirst_name+" "+ last_name);
                                    Detailapp.setId(id);

                                    patientspinneraray.add(Detailapp.getName());
//                                    contactDetails.add(Detailapp);
//                                    patientdarray.add(id);
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                            getApplicationContext(), R.layout.dropsimpledown, patientspinneraray);
                                    p_name.setAdapter(spinnerArrayAdapter);
                                }
                            }
                            else{
                                    staffarray.clear();

                                    patientspinneraray.clear();
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                            getApplicationContext(), R.layout.dropsimpledown, staffarray);
                                    staff_name.setAdapter(spinnerArrayAdapter);

                                    ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                                            getApplicationContext(), R.layout.dropsimpledown, patientspinneraray);
                                    p_name.setAdapter(spinnerArrayAdapter1);
                                }
                            }

                        }

                        catch(JSONException e){
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

    private class CatagoryUrlAsynTask extends AsyncTask<String, String, String> {
        String id, catagoryName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            initProgressDialog("Please Wait..");
        }

        @Override
        protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
            String content = HttpULRConnect.getData(ApiConfig.BASE_URL+"users/admingstlist");
            Log.e("DoInBackGround ---->", String.valueOf(content));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            try {

                Log.e("DoInBackGroundtr", String.valueOf(s));
//                pDialog.dismiss();
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray jsonArray = new JSONArray(s);
                Log.e("Post Method", jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    admingst_tax = jsonObject2.getString("admingst_tax");
                }

            }catch(Exception e){
                e.printStackTrace();

            }
            //  CatagoryMetod();
        }}


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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_about:
               Toast.makeText(BillingActivity.this,"saved to gallery",Toast.LENGTH_LONG).show();
                CreatePDF();
                return true;
               default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(BillingActivity.this);
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void viewbilling() {
        final String name = p_name.getSelectedItem().toString().trim();
//        final String billamount = bill_amount.getText().toString().trim();
        int index4=p_name.getSelectedItemPosition();
        try {
            patient_id = ViewBillingFragment.patientspinneraray.get(index4).getNumber();
            Log.e("pid",patient_id);
        }
        catch (Exception e){
            e.toString();
        }
        int index=staff_name.getSelectedItemPosition();
        try {
            id = ViewBillingFragment.staffarray.get(index).getId();
            Log.e("staffid",id);
        }
        catch (Exception e){
            e.toString();
        }
        final String dueamount = BillAmountDialog.pay.toString().trim();
//        final String paidamount = paid_amount.getText().toString().trim();
        final String payment = payment_mode.getSelectedItem().toString().trim();
        final String bill_status = status.getSelectedItem().toString().trim();
        final String date = Utils.getCurrentDate();
        final String staff = staff_name.getSelectedItem().toString().trim();
        Log.e("amount", selectedItemcost.toString());
        /*String staffid = staff_name.getSelectedItem().toString();
        String id[]=staff.split(" ");
        final  String staff_id = id[0];*/
        final String product_name = selectedItem.toString().replace("[", "").replace("]", "");
        final String individual_quantity = selequantity.toString().replace("[", "").replace("]", "");
        final String individual_cost = selectedItemcost.toString().replace("[", "").replace("]", "");
        Log.d("total_price",product_name+" " +individual_quantity +" "+individual_cost);
        final String product_quan = invo_item_quant.getSelectedItem().toString().trim();
//        final String billnumber = bill_number.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_BILLING_URL,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        try {
                            Log.e("result", response);
//                            String result = response.substring(response.lastIndexOf(">") + 1);
//                            Log.e("resultwithsplit",result);
//                            pDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            String name = jsonObject.getString("invo_patient_name");
                            String invo_bill_amount = jsonObject.getString("invo_bill_amount");
                            String invo_paid_amount = jsonObject.getString("invo_paid_amount");
                            String invo_due_amount = jsonObject.getString("invo_due_amount");
                            String invo_payment_mode = jsonObject.getString("invo_payment_mode");
                            String invo_status = jsonObject.getString("invo_status");
                            String invo_billno = jsonObject.getString("invo_code");
                            String invo_date = jsonObject.getString("invo_date");
                            String invo_pro_quanty = jsonObject.getString("invo_pro_quanty");
                            String invo_product_name = jsonObject.getString("invo_product_name");
                            String invo_staff_name = jsonObject.getString("invo_staff_name");

                            Toast.makeText(getApplicationContext(), "Invoice generated succeessfully", Toast.LENGTH_LONG).show();
                            finish();
                            /*linearLayout.setVisibility(View.GONE);
                            btn.setVisibility(View.GONE);
                            buton.setVisibility(View.GONE);
                            linearLayout1.setVisibility(View.VISIBLE);*/
                            flag=true;
                            p_nametext.setText(name);
                            bill_amounttext.setText(invo_bill_amount);
                            paid_amounttext.setText(invo_paid_amount);
                            due_amounttext.setText(invo_due_amount);
                            payment_modetext.setText(invo_payment_mode);
                            statustext.setText(invo_status);
                            bill_numbertext.setText(invo_billno);
                            bill_datetext.setText(invo_date);
                            staffnametext.setText(invo_staff_name);
                            pro_name.setText(invo_product_name);
                            pro_quant.setText(invo_pro_quanty);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        startActivity(new Intent(StmtActivity.this, PinDoMatch.class));
                        Log.e("Postdat", "" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                    }
                }) {


            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("invo_patient_name", name);
                params.put("invo_bill_amount", individual_cost.toString());
                params.put("invo_due_amount", individual_cost.toString());
                params.put("invo_branch_code", item);
                params.put("invo_expence_amount", "0");
                params.put("invo_balance_amount", "0");
                params.put("invo_paid_amount", "0");
                params.put("invo_payment_mode", payment);
                params.put("invo_date", date);
                params.put("invo_status", bill_status);
                params.put("invo_product_name", product_name.toString());
                params.put("invo_staff_name", staff);
                params.put("invo_staff_id", id);
                params.put("invo_billno", "0");
                params.put("invo_Patient_id", patient_id);
                params.put("gst", admingst_tax);
                params.put("discount", BillAmountDialog.ed_disc_amount.getText().toString());
                params.put("invo_pro_quanty", individual_quantity+ "");
                params.put("status", "success");
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//            Toast.makeText(parent.getContext(),
//                    "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
//                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:

                // set time picker as current time
                return new TimePickerDialog(this, timePickerListener, hour, minute, false);

            case 0:
                return new DatePickerDialog(this, datePickerListener, year, month, day);

        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            if (selectedMonth + 1 >= 10) {
                bill_date.setText(selectedYear + "-" + (selectedMonth + 1) + "-"
                        + selectedDay);

            } else {

                bill_date.setText(selectedYear + "-" + "0" + (selectedMonth + 1) + "-"
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
    public Bitmap overlay(FrameLayout relativeLayout){
        relativeLayout.setDrawingCacheEnabled(true);
        /*relativeLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        relativeLayout.layout(0, 0, relativeLayout.getMeasuredWidth(), relativeLayout.getMeasuredHeight());*/
        System.gc();
        relativeLayout.buildDrawingCache();
        return relativeLayout.getDrawingCache();
    }

    public void saveImage(){
        Bitmap bitmap=overlay(frameLayout);
        try{
            String path = Environment.getExternalStorageDirectory().toString()+File.separator+"save_img.png";
            OutputStream fOut = null;
            File file = new File(path); // the File to save to
            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            fOut.flush();
            fOut.close();
            Log.d("sun","saved");
        }
        catch (Exception e){
            Log.d("sun",e.toString());
        }
    }
    public void CreatePDF(){

        try {
            Document document = new Document();
//            String file_name=new File(INPUTFILE).getName();
            PdfWriter.getInstance(document, new FileOutputStream(Environment.getExternalStorageDirectory().toString()+File.separator+"bill.pdf"));
            document.open();
            int saveimg=0;
//            for(int i=0;i<noofpages;i++){

                Bitmap bitmap = overlay(frameLayout);
                try {
//                    Bitmap bmp=Bitmap.createScaledBitmap(bitmap, page_width, page_height, false);
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
//                    byte[] b = baos.toByteArray();
//                    Image myImg = Image.getInstance(b);
//                    myImg.scaleToFit(PageSize.A4);
//
//                    myImg.setAlignment(Image.MIDDLE);
//                    document.setMargins(2, 2, 2, 2);
//                    int nh = (int) ( page_height * (512.0 / page_width) );
//                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
//                    String path = Environment.getExternalStorageDirectory().toString()+File.separator+"SIGNTOUCH"+File.separator+"temp.png";
//                    try{
//
//                        OutputStream fOut = null;
//                        File file = new File(path); // the File to save to
//                        fOut = new FileOutputStream(file);
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
//                        fOut.flush();
//                        fOut.close();
//                        Log.d("sun","saved");
//                    }
//                    catch (Exception e){
//                        Log.d("sun",e.toString());
//                    }
//                    Bitmap bmp=get_Resized_Bitmap(bitmap,page_height,page_width);
//                    Bitmap bmp1=BitmapSizeHelper.getBitmapFromPath(path,page_width,page_height, BitmapSizeHelper.ScalingLogic.FIT);
//                    Bitmap bmp1=BitmapSizeHelper.createScaledBitmap(bitmap,page_width,page_height, BitmapSizeHelper.ScalingLogic.FIT);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , stream);
                    Image myImg = Image.getInstance(stream.toByteArray());
//                    myImg.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
                    myImg.setAlignment(Image.ORIGINAL_BMP);
                    document.add(myImg);
//                    bitmap.recycle();
                    Log.d("sun","saved");
                } catch (Exception e) {
                    Log.d("sun",e.toString());
                }
                try{
                    String path = Environment.getExternalStorageDirectory().toString()+File.separator+"physio_bill.png";
                    OutputStream fOut = null;
                    File file = new File(path); // the File to save to
                    fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                    fOut.flush();
                    fOut.close();
                    Log.d("sun","saved");
                }
                catch (Exception e){
                    Log.d("sun",e.toString());
                }

            document.close();

            finish();
//            LoadPdf();
        }
        catch (Exception e){
            Log.d("sun","Error:-"+e.toString());

        }

    }
}