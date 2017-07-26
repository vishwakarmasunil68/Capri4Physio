package com.capri4physio.fragment.assessment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.Invoice.InfoApps1;
import com.capri4physio.R;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 7/13/2016.
 */
public class GetLUserList extends AppCompatActivity{
    public static GetLUserList getLUserList;
    TextView results;
    LocationAdapter contactAdapter;
    public static ArrayList<String> contactDetails;
    InfoApps Detailapp,Detailapp1;
    public static ListView contactList;
    // URL of object to be parsed
    String JsonURL = "http://www.caprispine.in/users/appointpatientlist";
    public static String usertype,userid;
    public static ArrayList<InfoApps> patientaray;
    ArrayList<String> arrayList;
    String item;
    InfoApps1 detailApps;
    Spinner spinnerbranchloca;
    ArrayList<String> staffaray;
    ArrayList<String> dcotoraray;
    Map<String,String> objresponse;
    ArrayList<String> brancharay;
    JSONObject jsonObject = new JSONObject();
    public JSONParser jsonParser;
    // This string will hold the results
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
//    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.userlist);
        contactDetails=new ArrayList<String>();


        contactList=(ListView)findViewById(R.id.contactlist);
        arrayList = new ArrayList<String>();
        arrayList.add("Select Branch Name");
        spinnerbranchloca = (Spinner) findViewById(R.id.spinnerbranchloca);
        patientaray=new ArrayList<InfoApps>();
        staffaray=new ArrayList<String>();
        dcotoraray=new ArrayList<String>();
        brancharay=new ArrayList<String>();

//        new CatagoryUrlAsynTask().execute();
        new CatagoryUrlAsynTask1().execute();
// Creates the Volley request queue


        // Casts results into the TextView found within the main layout XML with id jsonData
        results = (TextView) findViewById(R.id.jsonData);

        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("0")){
            startActivity(new Intent(GetLUserList.this,Viewpatientinlist.class));
            finish();
        }

        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")){
            spinnerbranchloca.setVisibility(View.VISIBLE);
        }
else {
            item = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
            viewStaffApiCall();
            Log.e("item", item);
        }

        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    try {
                        String address = spinnerbranchloca.getSelectedItem().toString();
                        if (address.equalsIgnoreCase("Select Branch Name")){
                            contactList.setVisibility(View.INVISIBLE);

                        }
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
    }



    private void viewStaffApiCall() {

        try {
            patientaray.clear();
        }
        catch (Exception e){
            e.toString();
        }
        /*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JsonURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            /*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*/

                            JSONArray objresponse = new JSONArray(response);

                            for (int i = 0;  i <objresponse.length() ; i++) {

                                JSONObject jsonObject = objresponse.getJSONObject(i);
                                String booking_date=jsonObject.getString("booking_date");
                                String first_name=jsonObject.getString("first_name");
                                String last_name= jsonObject.getString("last_name");
                                String getuniqueid=jsonObject.getString("id");
                                String booking_starttime = jsonObject.getString("booking_starttime");
                                String patientid = jsonObject.getString("patient_id");
                                String doctorid = jsonObject.getString("doctor_id");
                                String reason= jsonObject.getString("reason");
                                String visit_type =jsonObject.getString("visit_type");
                                String patient_code =jsonObject.getString("patient_code");
                                String appointment_branch_code =jsonObject.getString("appointment_branch_code");
                                String added_by = jsonObject.getString("added_by");
                                Detailapp = new InfoApps();
                                Detailapp.setName(first_name+ " "+last_name);
                                Detailapp.setNumber(getuniqueid);
                                Detailapp.setPatientid(patientid);
                                Detailapp.setPatientcode(appointment_branch_code);
                                Detailapp.setCity(patient_code);
                                Detailapp.setAppname(added_by);
                                patientaray.add(Detailapp);
                                Log.e("patientaray",patientaray.toString());
                                contactAdapter = new LocationAdapter(getApplicationContext(), R.layout.contactlistadap);

                                contactList.setVisibility(View.VISIBLE);
                                contactList.setAdapter(contactAdapter);

                            }
                                    /*else {
                                        Toast.makeText(getApplicationContext(),"Pin number is incorrect",Toast.LENGTH_LONG).show();
                                    }*/
//                            }
                            /*else {
                                Toast.makeText(getApplicationContext(),"Phone_no. or password is incorrect",Toast.LENGTH_LONG).show();
                            }*/
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
                params.put("appointment_branch_code",item);

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
//        }}
}
