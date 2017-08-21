package com.capri4physio.task;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.net.HttpApiCall;
import com.capri4physio.net.IConnectionStatus;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.AppPreferences;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * API worker thread to call web services
 *
 * @author prabhunathy
 * @version 1.0
 * @since 12/22/15.
 */
public class UrlConnectionAuthTask extends AsyncTask<String, Integer, String> implements IConnectionStatus {

    private String TAG = getClass().getSimpleName();

    private Context context;
    private String urlString;
    private int identifier;
    private JSONObject jsonParams;
    private boolean runInBackground;
    private HttpUrlListener callback;
    private ProgressDialog pDialog;
    private HttpApiCall mHttpApiCall;

    private static int retry = 0;
    private Class<?> mResponseModel;
    private Object resultObject = null;
    private String sResponse = "";

    public UrlConnectionAuthTask(Context ctx, String url, int identifier, boolean runInBackground, JSONObject jsonParams, Class<?> responseModel, HttpUrlListener callback) {
        this.context = ctx;
        this.urlString = url;
        this.identifier = identifier;
        this.jsonParams = jsonParams;
        this.mResponseModel = responseModel;
        this.runInBackground = runInBackground;
        this.callback = callback;
        mHttpApiCall = new HttpApiCall();
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        if (runInBackground)
            initProgressDialog("Please wait...");
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... params) {

        AppLog.i(TAG, "Request URL: " + urlString);
        AppLog.i(TAG, "Request Params : " + jsonParams.toString());

        sResponse = mHttpApiCall.doPost(urlString, jsonParams.toString());
        AppLog.i(TAG, "Response : " + sResponse);

        try {
            JSONObject jsonObject = new JSONObject(sResponse);

            JSONObject jsonObject1 = jsonObject.getJSONObject("result");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("User");
            String first_name = jsonObject2.getString("first_name");
            String last_name = jsonObject2.getString("last_name");
            String p_age = jsonObject2.getString("age");
            String user_type = jsonObject2.getString("user_type");
            String p_height = jsonObject2.getString("height");
            String p_weight = jsonObject2.getString("weight");
            String p_gender = jsonObject2.getString("gender");
            String p_marital = jsonObject2.getString("marital_status");

			/*AppPreferences.getInstance(context).setFirstName(first_name);
            AppPreferences.getInstance(context).setLastName(last_name);
			AppPreferences.getInstance(context).setAge(p_age);
			AppPreferences.getInstance(context).setUserType(user_type);
			AppPreferences.getInstance(context).setHeight(p_height);
			AppPreferences.getInstance(context).setWeight(p_weight);
			AppPreferences.getInstance(context)
			.setGender(p_gender);
			AppPreferences.getInstance(context).setMarital(p_marital);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Gson gson = new Gson();
            resultObject = gson.fromJson(sResponse, mResponseModel);
        } catch (Exception e) {
            mHttpApiCall.connectionStatus = EXCETION;
            AppLog.e(TAG, "Response : " + EX_GSON);
        }


        if (mHttpApiCall.connectionStatus.equals(SUCCESS)) {
            return SUCCESS;
        } else {
            return mHttpApiCall.connectionStatus;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        if (runInBackground)
            pDialog.dismiss();
        Log.d("sunil", result.toString());
        if (result.equals(SUCCESS)) {

            AppPreferences.getInstance(context).setUserDetails(sResponse);
            callback.onPostSuccess(resultObject, identifier);
            retry = 0;
        } else {

            if (retry < 3) {
                retry++;
                new UrlConnectionAuthTask(context, urlString, identifier, runInBackground, jsonParams, mResponseModel, callback).execute("");
                AppLog.i(TAG, "Retry : " + retry);

            } else {
                showAlertMessage(context, "Retry", "Oops! Connection problem", listener, runInBackground);
            }

        }
        super.onPostExecute(result);
    }


    //Retry
    public void showAlertMessage(Context c, String titile, String content, DialogInterface.OnClickListener listener, boolean isbg) {
        if (isbg) {
            AlertDialog.Builder alert = new AlertDialog.Builder(c);
            if (titile != null && !titile.equals(""))
                alert.setTitle(titile);
            alert.setMessage(content);
            alert.setPositiveButton("Ok", listener);
            alert.show();
        } else {
            AppLog.e(TAG, "Error in webservice calling");
        }

    }

    private void initProgressDialog(String loadingText) {
        pDialog = new ProgressDialog(this.context);
        pDialog.setMessage(loadingText);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub
            UrlConnectionAuthTask.retry = 0;
        }

    };

}
