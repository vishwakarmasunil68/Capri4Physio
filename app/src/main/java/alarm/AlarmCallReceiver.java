package alarm;
//import com.android.internal.telephony.ITelephony;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.util.AppPreferences;

import java.util.HashMap;
import java.util.Map;

//import com.android.internal.telephony;


public class AlarmCallReceiver extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			Log.e("datain","Recieve"+AppPreferences.getInstance(getApplicationContext()).getDeviceToken());
			StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://caprispine.in/users/patientnotification",
					new Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							try {
								try{
								}
								catch (Exception e){
									e.printStackTrace();
								}
								Vibrator vibrator;
								long patter1n[] = { 0, 100, 200, 300, 400 };
								vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
								vibrator.vibrate(800);
								Log.e("audio","RINGER_MODE_VIBRATE");
//                    pDialog.hide();

								Log.e("response", "" + response);
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
					params.put("title", "Alarm Call");
					params.put("message", "alarm msg. call");
					params.put("devicetoken", AppPreferences.getInstance(getApplicationContext()).getDeviceToken());
//                        params.put("noti_p_id", "");
//                        params.put("noti_doc_id", AppPreferences.getInstance(getActivity()).getDeviceToken());
					return params;
				}

			};

			RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
			requestQueue.add(stringRequest);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.e("onStart","onStart");
	}
}


