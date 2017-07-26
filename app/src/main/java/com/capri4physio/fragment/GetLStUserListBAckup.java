package com.capri4physio.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.fragment.assessment.InfoApps;
import com.capri4physio.fragment.assessment.JSONParser;
import com.capri4physio.fragment.assessment.LocationAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Emobi-Android-002 on 7/27/2016.
 */
public class GetLStUserListBAckup extends AppCompatActivity {


    public static GetLStUserListBAckup getLUserList;
    TextView results;
    LocationAdapter contactAdapter;
    public static ArrayList<String> contactDetails;
    InfoApps Detailapp,Detailapp1;
    public static ListView contactList;
    // URL of object to be parsed
    String JsonURL = "http://www.caprispine.in/users/getuserlist";
    public static String usertype,userid;
    public static ArrayList<InfoApps> patientaray;
    ArrayList<String> staffaray;
    ArrayList<String> dcotoraray;
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

        patientaray=new ArrayList<InfoApps>();
        staffaray=new ArrayList<String>();
        dcotoraray=new ArrayList<String>();
        brancharay=new ArrayList<String>();

        new CatagoryUrlAsynTask().execute();
// Creates the Volley request queue


        // Casts results into the TextView found within the main layout XML with id jsonData
        results = (TextView) findViewById(R.id.jsonData);
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
                    userid = jsonObject2.getString("userid");
                    String username = jsonObject2.getString("username");
                    usertype=jsonObject2.getString("usertype");

                    if (usertype.equals("0")){
                        Detailapp = new InfoApps();
                        Detailapp.setName(username);
                        Detailapp.setNumber(userid);
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
//        }}






}
