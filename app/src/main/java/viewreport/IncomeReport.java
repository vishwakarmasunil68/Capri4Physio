package viewreport;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.net.ApiConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 10/12/2016.
 */
public class IncomeReport extends AppCompatActivity {
    String fromdate,to_date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewincomerepo);

//        fromdate=ViewIncomeReport.ed1.getText().toString();
//        to_date=ViewIncomeReport.ed2.getText().toString();
        report8();
    }

    private void report8(){
        /*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_INCOME_STATEMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            /*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*/

//                            JSONObject objresponse = new JSONObject(response);


                            Log.e("response", "" + response);
                            JSONArray jsonArray = new JSONArray(response);


                            JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length()-1);
                            String status = jsonObject.getString("status");
                            if (status.equalsIgnoreCase("1")) {
//                                checkTextView7(jsonObject.getString("complaints"), complaints2,complaints,complaints1);
//                                checkTextView7(jsonObject.getString("problem_duration"), problem_duration2,problem_duration,problem_duration1);//startTime
//                                checkTextView7(jsonObject.getString("problem_before"),problem_before2, problem_before,problem_before1);//reason
//                                checkTextView7(jsonObject.getString("date"), date_tg2,date_tg,date_tg1);


                            }
                            else {
                                Toast.makeText(getApplicationContext(),"no record avaliable",Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.e("error",e.toString());
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
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("invo_start_date", fromdate);
                params.put("invo_end_date", to_date);
//                Toast.makeText(UserStatement.this, username +"success", Toast.LENGTH_LONG).show();
//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
