package com.capri4physio.Services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.capri4physio.activity.SplashActivity;
import com.capri4physio.database.DatabaseHelper;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.BundleConst;
import com.capri4physio.util.ToastClass;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sunil on 25-07-2017.
 */

public class LogoutService extends AsyncTask<String,Void,String>{
    Activity activity;
    String user_id;
    String jResult="";
    ProgressDialog progressDialog;
    public LogoutService(Activity activity,String user_id){
        this.activity=activity;
        this.user_id=user_id;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
            jResult = WebServiceBase.httpCall(params[0], nameValuePairs);
        } catch (Exception e) {
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
            e.printStackTrace();
        }
        return jResult;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(progressDialog!=null){
            progressDialog.dismiss();
        }

        try{
            JSONObject jsonObject=new JSONObject(jResult);
            if(jsonObject.optString("success").equals("true")){
                AppPreferences.getInstance(activity).clearAllData();
                DatabaseHelper databaseHelper=new DatabaseHelper(activity);
                databaseHelper.deleteAllChats();
                AppPreferences.getInstance(activity).setUserLogin(false);
                Intent intent = new Intent(activity, SplashActivity.class);
                intent.putExtra(BundleConst.IS_LOGOUT, true);
                try {
                    activity.stopService(new Intent(activity,LocationService.class));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                activity.startActivity(intent);
                activity.finish();
            }else{
                ToastClass.showShortToast(activity.getApplicationContext(),"Something went wrong");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
