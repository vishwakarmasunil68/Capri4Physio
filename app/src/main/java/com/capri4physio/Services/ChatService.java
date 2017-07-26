package com.capri4physio.Services;

import android.app.Activity;
import android.os.AsyncTask;

import com.capri4physio.database.DatabaseHelper;
import com.capri4physio.model.chat.ChatConPOJO;
import com.capri4physio.util.AppPreferences;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by sunil on 26-07-2017.
 */

public class ChatService extends AsyncTask<String,Void,String> {
    Activity activity;
    String user_id;
    String jResult="";
    public ChatService(Activity activity, String user_id){
        this.activity=activity;
        this.user_id=user_id;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }
    @Override
    protected String doInBackground(String... params) {
        try {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
            jResult = WebServiceBase.httpCall(params[0], nameValuePairs);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return jResult;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try{
            Gson gson1=new Gson();
            ChatConPOJO chatConPOJO=gson1.fromJson(s,ChatConPOJO.class);
            if(chatConPOJO.getSuccess().equals("true")){
                DatabaseHelper databaseHelper=new DatabaseHelper(activity);
                databaseHelper.insertchatdataList(chatConPOJO.getChatPOJOList());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        AppPreferences.getInstance(activity).setChatSync(true);

    }
}
