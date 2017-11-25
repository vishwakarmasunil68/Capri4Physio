package com.capri4physio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class APITestingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apitesting);
    }


//    private void addMotorAPi(final String main_base64){
//        final String name =ValidateEdit(ed1);
//        final String lastName =ValidateEdit(ed2);
//        final String dob =ValidateEdit(ed3);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.SIGNIN_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            try{
//                                pDialog.dismiss();
//                            }
//                            catch (Exception e){
//                                e.toString();
//                            }
//                            Log.e("result",response);
//                            Toast.makeText(MotorActivity1.this,"successfully added", Toast.LENGTH_LONG).show();
//                            finish();
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        Log.e("Postdat", "" + response.toString());
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.w("Postdat", "" + error);
//                    }
//                }){
//
//
//            protected Map<String,String> getParams(){
//                Map<String,String> objresponse = new HashMap<String, String>();
//                objresponse.put("moter_exam_date", Utils.getCurrentDate());
//                objresponse.put("patient_id", patient_id);
//                objresponse.put("moter_exam_data", name);
//                objresponse.put("moter_exam_data1", lastName);
//                objresponse.put("moter_exam_data2", dob);
//                objresponse.put("moterexams_images", main_base64);
//                Log.d(TagUtils.getTag(),"params:-"+objresponse.toString());
//                return objresponse;
//            }
//
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }
}
