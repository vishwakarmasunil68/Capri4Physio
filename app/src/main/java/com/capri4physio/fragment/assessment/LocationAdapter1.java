package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by emobi5 on 6/23/2016.
 */


    public class LocationAdapter1 extends ArrayAdapter {
    String CANCEL_URL = "http://www.caprispine.in/users/cancelappointment";
    public static final String KEY_ID = "id";
    String numbercancel;
        Activity context;
        SharedPreferences sp;
        ArrayList<String> stringArrayList= new ArrayList<String>();;
        int vgreso;
//    String bitmapgallery=sp.getString("accepted", null);
//    stringArrayList = new ArrayList<String>();
//    stringArrayList.add(bitmapgallery);
//    Log.e("selected", stringArrayList.toString());


        public LocationAdapter1(Activity context, int resource) {

            super(context, resource);
            this.context=context;
            this.vgreso=resource;
        }

        @Override
        public int getCount() {

            return Viewpatientinlist.contactDetails1.size();
        }

        @Override
        public Object getItem(int position) {
            return  Viewpatientinlist.contactDetails1.get(position);

        }


        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(vgreso, parent, false);
//        final ProductBooen p = getProduct(position);
            TextView textcontac = (TextView) itemView.findViewById(R.id.textcontact);
            final ImageView view = (ImageView) itemView.findViewById(R.id.view);
            final ImageView getcancelview = (ImageView) itemView.findViewById(R.id.getcancel);
            ImageView edit = (ImageView) itemView.findViewById(R.id.edit);
            ImageView cancel = (ImageView) itemView.findViewById(R.id.cancel);
            textcontac.setText(Viewpatientinlist.contactDetails1.get(position).getName().toString());

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
//                    view.setBackgroundResource(R.drawable.btnselector);
                    String numberview=Viewpatientinlist.contactDetails1.get(position).getStr4().toString();
                    String Appname =Viewpatientinlist.contactDetails1.get(position).getPatientid().toString();
                    String date=Viewpatientinlist.contactDetails1.get(position).getName().toString();
                    String doctorname=Viewpatientinlist.contactDetails1.get(position).getDataAdd().toString();
                    String startTime= Viewpatientinlist.contactDetails1.get(position).getNumber().toString();
                    String reason=Viewpatientinlist.contactDetails1.get(position).getAppname().toString();
                    String vist=Viewpatientinlist.contactDetails1.get(position).getExp().toString();

                    Intent msglocationintent = new Intent(context, ViewpatientActivity.class);
                    msglocationintent.putExtra("phone_no", Appname);
                    msglocationintent.putExtra("patient_name", numberview);
                    msglocationintent.putExtra("date", date);
                    msglocationintent.putExtra("startTime", startTime);
                    msglocationintent.putExtra("doctorname", doctorname);
                    msglocationintent.putExtra("vist", vist);
                    msglocationintent.putExtra("reason", reason);
                    msglocationintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(msglocationintent);
                }
            });
//            edit.setBackgroundResource(R.drawable.btnselector);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String numberedit=Viewpatientinlist.contactDetails1.get(position).getStr4().toString();
                    String reason=Viewpatientinlist.contactDetails1.get(position).getAppname().toString();
                    String bookingstarttimeedit=Viewpatientinlist.contactDetails1.get(position).getNumber().toString();
                    String doctoridedit=Viewpatientinlist.contactDetails1.get(position).getDoctorid().toString();
                    String bookingdateedit=Viewpatientinlist.contactDetails1.get(position).getName().toString();
                    String paientidedit=Viewpatientinlist.contactDetails1.get(position).getPatientid().toString();
                    String vist=Viewpatientinlist.contactDetails1.get(position).getExp().toString();
                    Intent editIntent = new Intent(context,EditpatientActivity.class);
                    editIntent.putExtra("phone_no1", numberedit);
                    editIntent.putExtra("reason", reason);
                    editIntent.putExtra("bookingstarttimeedit", bookingstarttimeedit);
                    editIntent.putExtra("doctoridedit", doctoridedit);
                    editIntent.putExtra("bookingdateedit", bookingdateedit);
                    editIntent.putExtra("nameedit", paientidedit);
                    editIntent.putExtra("vist", vist);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(editIntent);
                    context.finish();
                }
            });
//            cancel.setBackgroundResource(R.drawable.btnselector);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     numbercancel=Viewpatientinlist.contactDetails1.get(position).getDoctorid().toString();//id
                    String nameedit=Viewpatientinlist.contactDetails1.get(position).getName().toString();
//                    Intent cancelIntent = new Intent(context,CancelAppointmentPatient.class);
//                    cancelIntent.putExtra("cancel_no", numbercancel);
//                    cancelIntent.putExtra("nameedit", nameedit);
//                    cancelIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(cancelIntent);


                    Intent cancelIntent = new Intent(context,CancelAppointmentPatient.class);
                    cancelIntent.putExtra("cancel_no", numbercancel);
                    cancelIntent.putExtra("nameedit", nameedit);
                    cancelIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.finish();
                    context.startActivityForResult(cancelIntent, 1);

                    /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder((Activity) v.getContext());
                    alertDialogBuilder.setMessage("Are you sure,You wanted to make decision");

                    alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(context,"You clicked yes button",Toast.LENGTH_LONG).show();
                            cancelAppointment();
                        }
                    });

                    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((Activity)context).finish();
//                            finish();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();*/
                }
            });
            getcancelview.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
//                    view.setBackgroundResource(R.drawable.btnselector);
                    String numberview=Viewpatientinlist.contactDetails1.get(position).getStr4().toString();
                    String reason=Viewpatientinlist.contactDetails1.get(position).getPatientid().toString();
                    Intent msglocationintent = new Intent(context, ApiActivity.class);
                    msglocationintent.putExtra("phone_no", reason);
                    msglocationintent.putExtra("patient_name", numberview);
                    msglocationintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(msglocationintent);
                }
            });
            return itemView;
        }
    private void cancelAppointment(){
        /*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CANCEL_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(context, "Appointment request has been cancel successfully.", Toast.LENGTH_LONG).show();
//                            finish();
                            Log.e("result",response);
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
                params.put(KEY_ID,numbercancel);
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

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    }

