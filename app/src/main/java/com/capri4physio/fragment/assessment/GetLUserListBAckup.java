package com.capri4physio.fragment.assessment;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 7/13/2016.
 */
public class GetLUserListBAckup extends AppCompatActivity{
    public static GetLUserListBAckup getLUserList;
    TextView results;
    LocationAdapter contactAdapter;
    public static ArrayList<String> contactDetails;
    InfoApps Detailapp,Detailapp1;
    public static ListView contactList;
    // URL of object to be parsed
    String JsonURL = "http://www.caprispine.in/users/getuserlist";
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



        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")){
            spinnerbranchloca.setVisibility(View.VISIBLE);
        }

        item = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
        Log.e("item",item);

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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_PATIENT_STAFF_FILTERATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            try {
                                patientaray.clear();
                                contactDetails.clear();
                                contactList.setVisibility(View.INVISIBLE);
                            }
                            catch (Exception e){
                                e.toString();
                            }
                            Log.e("result", response);
                            JSONArray jsonArray = new JSONArray(response);
                            Log.e("Post Method", jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                Log.e("2", jsonObject2.toString());
                                userid = jsonObject2.getString("id");
                                String username = jsonObject2.getString("first_name");
                                usertype=jsonObject2.getString("user_type");
                                String patient_code = jsonObject2.getString("patient_code");

                                if (usertype.equals("0")){
                                    Detailapp = new InfoApps();
                                    Detailapp.setName(username);
                                    Detailapp.setNumber(userid);
                                    Detailapp.setPatientid(patient_code);
                                    patientaray.add(Detailapp);
                                    Log.e("patientaray",patientaray.toString());
                                    contactDetails.add(username);
                                    contactAdapter = new LocationAdapter(getApplicationContext(), R.layout.contactlistadap);

                                    contactList.setVisibility(View.VISIBLE);
                                    contactList.setAdapter(contactAdapter);
                                }
                            } }catch(JSONException e){
                            Log.e("Postdat", "" + e.toString());
                        }


//                        Toast.makeText(getApplicationContext(),
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
            protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
                String content = HttpULRConnect.getData(JsonURL);
                Log.e("DoInBackGround ---->", String.valueOf(content));
                return content;
            }

            @Override
            protected void onPostExecute(String s) {

                try {
                    Log.e("DoInBackGroundtr", String.valueOf(s));
                    ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                    Log.e("Post Method", jsonObject1.toString());
                    for (int i = 1; i <= jsonObject1.length(); i++) {

                        JSONObject jsonObject2 = jsonObject1.getJSONObject(i + "");
                        Log.e("2", jsonObject2.toString());
                         userid = jsonObject2.getString("id");
                        String username = jsonObject2.getString("first_name");
                        usertype=jsonObject2.getString("usertype");
                        String patient_code = jsonObject2.getString("patient_code");
                        String added_by = jsonObject2.getString("added_by");

                        if (usertype.equals("0")){
                            Detailapp = new InfoApps();
                            Detailapp.setName(username);
                            Detailapp.setNumber(userid);
                            Detailapp.setPatientid(patient_code);
                            Detailapp.setPatientcode(added_by);
                            patientaray.add(Detailapp);
                            Log.e("patientaray",patientaray.toString());
                        }
                        else if (usertype.equals("1")){
                            staffaray.add(username);
                            Log.e("staffaray",staffaray.toString());
                        }
                        else if (usertype.equals("2")){
                            dcotoraray.add(username);
                            Log.e("dcotoraray",dcotoraray.toString());
                        }
                        else if (usertype.equals("3")){
                            brancharay.add(username);
                            Log.e("brancharay",brancharay.toString());
                        }
//                        int userid = Integer.parseInt(jsonObject2.getInt((i+1) + ""));
//                        int userid=Integer.parseInt(jsonObject2.getString((i+1) + ""));
                        Log.e("usertype", usertype.toString());
                        /*Detailapp1 = new InfoApps();
                        Detailapp1.setName(username);*/
                        /*Detailapp.setAppname(trnsamount);
                        Detailapp.setDataAdd(trnsamounttype);*/
                        contactDetails.add(username);
                        contactAdapter = new LocationAdapter(getApplicationContext(), R.layout.contactlistadap);
                        contactList.setAdapter(contactAdapter);
//                        Toast.makeText(getApplicationContext(),usertype,Toast.LENGTH_LONG).show();
//                        Toast.makeText(getApplicationContext(),userid,Toast.LENGTH_LONG).show();
//                        int value = jsonObject1.getInt(i + 1);
                    }

                    }catch(Exception e){
                        e.printStackTrace();

                    }
                    //  CatagoryMetod();
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
//        }}
}
