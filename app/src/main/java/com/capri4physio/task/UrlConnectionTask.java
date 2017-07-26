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
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * API worker thread to call web services
 *
 * @version 1.0
 * @author prabhunathy
 * @since 12/22/15.
 */
public class UrlConnectionTask extends AsyncTask<String, Integer, String> implements IConnectionStatus {

	private String TAG=getClass().getSimpleName();

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
	private Object resultObject=null;

	public UrlConnectionTask(Context ctx, String url, int identifier, boolean runInBackground, JSONObject jsonParams, Class<?> responseModel, HttpUrlListener callback) {
		this.context = ctx;
		this.urlString = url;
		this.identifier = identifier;
		this.jsonParams = jsonParams;
		this.mResponseModel = responseModel;
		this.runInBackground = runInBackground;
		this.callback = callback;
		mHttpApiCall =new HttpApiCall();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		if (runInBackground)
			try {
				initProgressDialog("Please wait...");
			}
			catch (Exception e){
				e.printStackTrace();
			}
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

		String sResponse = mHttpApiCall.doPost(urlString,jsonParams.toString());
		AppLog.i(TAG, "Response : " + sResponse);
		Log.e("result",sResponse);

		try {
			Gson gson = new Gson();
			resultObject = gson.fromJson(sResponse, mResponseModel);

		} catch (Exception e){
			mHttpApiCall.connectionStatus=EXCETION;
			Log.e(TAG, "Response : " + EX_GSON);
		}


		if(mHttpApiCall.connectionStatus.equals(SUCCESS)){
			return SUCCESS;
		} else {
			return mHttpApiCall.connectionStatus;
		}
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		if (runInBackground)
			try {
				pDialog.dismiss();
			}
			catch (Exception e){
				e.printStackTrace();
			}
//		Toast.makeText(context,"Record Successfully Added",Toast.LENGTH_LONG).show();
		if (result.equals(SUCCESS)) {

			callback.onPostSuccess(resultObject, identifier);

			retry = 0;

		}
		/*else {
			if (retry<1){
				retry++;
			if (retry < 3) {
				retry++;
				new UrlConnectionTask(context, urlString, identifier, runInBackground, jsonParams, mResponseModel, callback).execute("");
				AppLog.i(TAG, "Retry : " + retry);
			}}
			}*/
		/*else {
			Toast.makeText(context, "Sucecssfully Added", Toast.LENGTH_SHORT).show();
//				showAlertMessage(context, "Retry", "Oops! Connection problem", listener, runInBackground);

		}*/
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
		try {
			pDialog.show();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			UrlConnectionTask.retry = 0;
		}

	};

}
