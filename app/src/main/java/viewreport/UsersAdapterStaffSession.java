package viewreport;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.Invoice.InfoApps1;
import com.capri4physio.R;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *Create to bind jobs in list
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 */

public class UsersAdapterStaffSession extends RecyclerView.Adapter<UsersAdapterStaffSession.MyViewHolder> {
    Boolean flag=false;
    private List<InfoApps1> moviesList;
    String invo;
    public static String reason,patient_name,patient_Email;
    Context con;
    Activity ctx;
    public  static String invo_id,invo_patient_name,invo_bill_amount,invo_due_amount,invo_paid_amount,
            invo_status,invo_bill_number,invo_pro_name,invo_pro_quantity,invo_staff,invo_date,invo_pay_mode;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,year, genre;
        ImageView edit,mail,cancel,prescription,View;
        Button status;
        CardView cv;



        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_patient_id);
            genre = (TextView) view.findViewById(R.id.txt_patient_name);


        }
    }


    public UsersAdapterStaffSession(List<InfoApps1> moviesList, Activity ctx) {
        this.moviesList = moviesList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_patient_adapsession, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final InfoApps1 movie = moviesList.get(position);
        holder.title.setText(movie.getName());
//        holder.title.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "AlexBrush-Regular.ttf"));
//        holder.title.setTypeface(Typeface.create("Montez-Regular.ttf", Typeface.BOLD));
//        holder.genre.setText(movie.getBill_amount());



    }
    @Override
    public int getItemCount() {
        return moviesList.size();
    }
    private void editAppointment(){
        final String name = invo_patient_name.toString().trim();
        final String billamount = invo_bill_amount.toString().trim();
        final String dueamount = invo_due_amount.toString().trim();
        final String paidamount = invo_paid_amount.toString().trim();
//        final String payment = invo_pay_mode.toString().trim();
        final String bill_status = invo_status.toString().trim();
        final String date = Utils.getCurrentDate();
        final String staff = invo_staff.toString().trim();
        final String product_name = invo_pro_name.toString().trim();
        final String product_quan = invo_pro_quantity.toString().trim();
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_EDITABLE_INVO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(ctx, "Information has been updated successfully.", Toast.LENGTH_LONG).show();

                            /*Intent returnIntent = new Intent();
                            returnIntent.putExtra("result","ok");
                            setResult(Activity.RESULT_OK,returnIntent);*/
                            ctx.finish();
//                            startActivity(new Intent(CancelAppointmentPatient.this,ViewpatientActivity.class));
//                            finish();

                            Log.e("result",response);
//                            viewpatientinlist.finish();
//                            GetLUserList.getLUserList.finish();
                            /*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*/

                            /*JSONObject objresponse = new JSONObject(response);
                            //					Toast.makeText(getApplicationContext(), "Could not retreive Data2!", Toast.LENGTH_LONG).show();

                            String success = objresponse.getString("isSuccess");
                            String success_msg = objresponse.getString("success_msg");

                            if (success.equalsIgnoreCase("true") || success_msg.equalsIgnoreCase("true")) {

                                Log.e("Postdat", "" + response);
                                jsonArray = objresponse.getJSONArray("result");


                                //Log.i("News Data", jsonArray.toString());

//                    JSONArray cast = jsonArray.getJSONArray("result");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    blnc_id = jsonObject.getString("receiver_name");
                                    trnsdtime = jsonObject.getString("transaction_datetime");
                                    trnsamount= jsonObject.getString("balance_amount");
                                    trnsamounttype= jsonObject.getString("transaction_transfer_type");
//                                     balance_id=new ArrayList<String>();
//                                    balance_id.add(blnc_id);
                                    Detailapp = new InfoApps();
                                    Detailapp.setName(blnc_id);
                                    Detailapp.setNumber(trnsdtime);
                                    Detailapp.setAppname(trnsamount);
                                    Detailapp.setDataAdd(trnsamounttype);
                                    Log.e("account_blnc_id", blnc_id);
                                    Log.e("account_balance_id", contactDetails.toString());
//                                    if (BalanceDetail.password.equals(pinpassword)) {
                                    pass.setVisibility(View.GONE);
                                    linear.setVisibility(View.VISIBLE);
                                    contactAdapter = new LocationAdapter(getApplicationContext(), R.layout.contactlistadap);
                                    contactList.setAdapter(contactAdapter);
//                                    Double user_long = jsonObject.getDouble("user_long");
//                                    Double user_lat = jsonObject.getDouble("user_lat");
//                                    UserType = "UserType: " + jsonObject.getString("usertype");
                                    *//*Intent intent1 = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent1);*//*
                                    *//*}
                                    else {
                                        Toast.makeText(getApplicationContext(),"Pin number is incorrect",Toast.LENGTH_LONG).show();
                                    }*//*
                                }*/
//                            }
                           /* else {
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
                params.put("invo_id",invo);
                params.put("invo_patient_name", name);
                params.put("invo_bill_amount", billamount);
                params.put("invo_due_amount", dueamount);
                params.put("invo_paid_amount",paidamount );
                params.put("invo_payment_mode", "cash");
                params.put("invo_date", date);
                params.put("invo_status", bill_status);
                params.put("invo_product_name", product_name);
                params.put("invo_staff_name", staff);
                params.put("invo_pro_quanty", product_quan);
                /*params.put(KEY_PATIENTID,patientname);
                params.put(KEY_ID,uniqueid);
                params.put(KEY_DOCTORID,GetDcotorid);
                params.put(KEY_DATEBOOKING, booking_date);
                params.put(KEY_BOOKINGSTARTTIME, booking_starttime);
                params.put(KEY_BOOKINGENDTIME, booking_Endtime);
                params.put(KEY_REASON, reason);*/

//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);
    }

            private void cancelAppointment(){
        /*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
                StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.CANCEL_INVO,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Toast.makeText(ctx, "Cancel request has been accpeted successfully.", Toast.LENGTH_LONG).show();

                            /*Intent returnIntent = new Intent();
                            returnIntent.putExtra("result","ok");
                            setResult(Activity.RESULT_OK,returnIntent);*/
                                    ctx.finish();
//                            startActivity(new Intent(CancelAppointmentPatient.this,ViewpatientActivity.class));
//                            finish();

                                    Log.e("result",response);
//                            viewpatientinlist.finish();
//                            GetLUserList.getLUserList.finish();
                            /*Intent intent=new Intent(StmtActivity.this,HomeActivity.class);
                                startActivity(intent);*/

                            /*JSONObject objresponse = new JSONObject(response);
                            //					Toast.makeText(getApplicationContext(), "Could not retreive Data2!", Toast.LENGTH_LONG).show();

                            String success = objresponse.getString("isSuccess");
                            String success_msg = objresponse.getString("success_msg");

                            if (success.equalsIgnoreCase("true") || success_msg.equalsIgnoreCase("true")) {

                                Log.e("Postdat", "" + response);
                                jsonArray = objresponse.getJSONArray("result");


                                //Log.i("News Data", jsonArray.toString());

//                    JSONArray cast = jsonArray.getJSONArray("result");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    blnc_id = jsonObject.getString("receiver_name");
                                    trnsdtime = jsonObject.getString("transaction_datetime");
                                    trnsamount= jsonObject.getString("balance_amount");
                                    trnsamounttype= jsonObject.getString("transaction_transfer_type");
//                                     balance_id=new ArrayList<String>();
//                                    balance_id.add(blnc_id);
                                    Detailapp = new InfoApps();
                                    Detailapp.setName(blnc_id);
                                    Detailapp.setNumber(trnsdtime);
                                    Detailapp.setAppname(trnsamount);
                                    Detailapp.setDataAdd(trnsamounttype);
                                    Log.e("account_blnc_id", blnc_id);
                                    Log.e("account_balance_id", contactDetails.toString());
//                                    if (BalanceDetail.password.equals(pinpassword)) {
                                    pass.setVisibility(View.GONE);
                                    linear.setVisibility(View.VISIBLE);
                                    contactAdapter = new LocationAdapter(getApplicationContext(), R.layout.contactlistadap);
                                    contactList.setAdapter(contactAdapter);
//                                    Double user_long = jsonObject.getDouble("user_long");
//                                    Double user_lat = jsonObject.getDouble("user_lat");
//                                    UserType = "UserType: " + jsonObject.getString("usertype");
                                    *//*Intent intent1 = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent1);*//*
                                    *//*}
                                    else {
                                        Toast.makeText(getApplicationContext(),"Pin number is incorrect",Toast.LENGTH_LONG).show();
                                    }*//*
                                }*/
//                            }
                           /* else {
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
                        params.put("invo_id",invo);
                /*params.put(KEY_PATIENTID,patientname);
                params.put(KEY_ID,uniqueid);
                params.put(KEY_DOCTORID,GetDcotorid);
                params.put(KEY_DATEBOOKING, booking_date);
                params.put(KEY_BOOKINGSTARTTIME, booking_starttime);
                params.put(KEY_BOOKINGENDTIME, booking_Endtime);
                params.put(KEY_REASON, reason);*/

//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(ctx);
                requestQueue.add(stringRequest);
            }
}
