package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.capri4physio.fragment.ViewClinic;
import com.capri4physio.net.ApiConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by emobi5 on 6/23/2016.
 */


    public class LocationAdapterClinic extends ArrayAdapter {
    String numbercancel;
    Activity context;
    String CANCEL_CLIINIC1 = ApiConfig.BASE_URL+"users/deleteclinihead";
    String CANCEL_URLCLIINIC =ApiConfig.BASE_URL+"users/updateclinic";
    public static final String KEY_ID = "user_id";
        SharedPreferences sp;
        ArrayList<String> stringArrayList= new ArrayList<String>();;
        int vgreso;
//    String bitmapgallery=sp.getString("accepted", null);
//    stringArrayList = new ArrayList<String>();
//    stringArrayList.add(bitmapgallery);
//    Log.e("selected", stringArrayList.toString());


        public LocationAdapterClinic(Activity context, int resource) {

            super(context, resource);
            this.context=context;
            this.vgreso=resource;
        }

        @Override
        public int getCount() {

            return ViewClinic.contactDetails1.size();
        }

        @Override
        public Object getItem(int position) {
            return  ViewClinic.contactDetails1.get(position);

        }


        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(vgreso, parent, false);
//        final ProductBooen p = getProduct(position);
            TextView textcontac = (TextView) itemView.findViewById(R.id.textcontact);
            final ImageView view = (ImageView) itemView.findViewById(R.id.view);
            final ImageView getcancelview = (ImageView) itemView.findViewById(R.id.getcancel);
            final TextView position_tv = (TextView) itemView.findViewById(R.id.position_tv);
            ImageView edit = (ImageView) itemView.findViewById(R.id.edit);
            ImageView cancel = (ImageView) itemView.findViewById(R.id.cancel);

            /*edit.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);*/
            textcontac.setText(ViewClinic.contactDetails1.get(position).getName().toString());
            position_tv.setText((position + 1) + "");
//            view.setText(ViewClinic.contactDetails1.get(position).getNumber().toString());
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
//                    view.setBackgroundResource(R.drawable.btnselector);
                    String staffid = ViewClinic.contactDetails1.get(position).getId();
                    String staffemail  = ViewClinic.contactDetails1.get(position).getStr4();
                    String staffname= ViewClinic.contactDetails1.get(position).getName();
                    String staffphone_no = ViewClinic.contactDetails1.get(position).getNumber();
                    String dob = ViewClinic.contactDetails1.get(position).getDob();
                    String Age = ViewClinic.contactDetails1.get(position).getGender();
                    String doj = ViewClinic.contactDetails1.get(position).getDoj();
                    String exp = ViewClinic.contactDetails1.get(position).getExp();
                    String Quali = ViewClinic.contactDetails1.get(position).getQualification();
                    String pc = ViewClinic.contactDetails1.get(position).getPc();
                    String city = ViewClinic.contactDetails1.get(position).getCity();
                    String maried_status = ViewClinic.contactDetails1.get(position).getMarital_status();
                    String saddress = ViewClinic.contactDetails1.get(position).getDataAdd();
                    String desi = ViewClinic.contactDetails1.get(position).getDesig();
                    String Staffcode = ViewClinic.contactDetails1.get(position).getStaffcode();
                    String send_date = ViewClinic.contactDetails1.get(position).getSend_date();
                    final Dialog dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Light_Dialog);

                    //setting custom layout to dialog
                    dialog.setContentView(R.layout.cusotm_clinic_view);
                    dialog.setTitle("Clinic - Detail");

                    TextView patient_name = (TextView) dialog.findViewById(R.id.Patient_name);
                    patient_name.setText(staffname);

                    TextView clinic_email = (TextView) dialog.findViewById(R.id.clinic_email);
                    clinic_email.setText(staffemail);
                    /*
                    final RelativeLayout relativeLayout = (RelativeLayout)dialog.findViewById(R.id.print_layout);*/
                    Button dismissButton = (Button) dialog.findViewById(R.id.button);
                    dismissButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                        getBitmapByView(parent_scroll_view);
                            dialog.dismiss();
                        }
                    });
                    TextView bill_number = (TextView) dialog.findViewById(R.id.Bill_number);
                    bill_number.setText(Staffcode);

                    dialog.show();
                }
            });
//            edit.setBackgroundResource(R.drawable.btnselector);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String staffid = ViewClinic.contactDetails1.get(position).getId();
                    final String staffemail  = ViewClinic.contactDetails1.get(position).getStr4();
                    final String staffname= ViewClinic.contactDetails1.get(position).getName();
                    String staffphone_no = ViewClinic.contactDetails1.get(position).getNumber();
//                    String lat = ViewClinic.contactDetails1.get(position).getLat();
//                    String lng = ViewClinic.contactDetails1.get(position).getLng();
                    String doj = ViewClinic.contactDetails1.get(position).getDoj();
                    String exp = ViewClinic.contactDetails1.get(position).getExp();
                    String Quali = ViewClinic.contactDetails1.get(position).getQualification();
                    String pc = ViewClinic.contactDetails1.get(position).getPc();
                    String city = ViewClinic.contactDetails1.get(position).getCity();
                    String maried_status = ViewClinic.contactDetails1.get(position).getMarital_status();
                    String saddress = ViewClinic.contactDetails1.get(position).getDataAdd();
                    String desi = ViewClinic.contactDetails1.get(position).getDesig();
                    String Staffcode = ViewClinic.contactDetails1.get(position).getStaffcode();
                    final String send_date = ViewClinic.contactDetails1.get(position).getSend_date();
                    final Dialog dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Light_Dialog);

                    //setting custom layout to dialog
                    dialog.setContentView(R.layout.cusotm_clinic_edit);
                    dialog.setTitle("Clinic - Detail");

                    final EditText patient_name = (EditText) dialog.findViewById(R.id.Patient_name);
                    patient_name.setText(staffname);

                    final EditText clinic_email = (EditText) dialog.findViewById(R.id.clinic_email);
                    clinic_email.setText(staffemail);
                    final EditText lati = (EditText) dialog.findViewById(R.id.lat);
                    lati.setText("lat");
                    final EditText lngi = (EditText) dialog.findViewById(R.id.lng);
                    lngi.setText("lng");


                    /*
                    final RelativeLayout relativeLayout = (RelativeLayout)dialog.findViewById(R.id.print_layout);*/
                    Button dismissButton = (Button) dialog.findViewById(R.id.button);
                    final EditText etxtpassword = (EditText) dialog.findViewById(R.id.etxtpassword);
                    etxtpassword.setText(send_date);
                    dismissButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancelAppointment(position,dialog,staffid,clinic_email.getText().toString(),patient_name.getText().toString(),etxtpassword.getText().toString()
                            ,lati.getText().toString(),lngi.getText().toString());
//                        getBitmapByView(parent_scroll_view);

                        }
                    });
                    EditText bill_number = (EditText) dialog.findViewById(R.id.Bill_number);
                    bill_number.setText(Staffcode);

                    dialog.show();
                }
            });
            edit.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
//            cancel.setBackgroundResource(R.drawable.btnselector);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                final String staffid= ViewClinic.contactDetails1.get(position).getId();

                   final  AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setMessage("Delete Clinic");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            //get gps
//                Toast.makeText(CancelAppointmentPatient.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                            cancelclin(staffid,dialog);
                        }
                    });
                    dialog.setNegativeButton(android.R.string.no, null);
                    dialog.show();
                /*Intent intent = new Intent(context,CancelAppointmentStaff.class);
                intent.putExtra("staffid", staffid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);*/

//                ((Activity)context).finish();
            }
        });


                    /*Intent cancelIntent = new Intent(context,CancelAppointmentPatient.class);
                    cancelIntent.putExtra("cancel_no", numbercancel);
                    cancelIntent.putExtra("nameedit", nameedit);
                    cancelIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.finish();
                    context.startActivityForResult(cancelIntent, 1);*//*


                }
            });*/
            /*getcancelview.setOnClickListener(new View.OnClickListener() {

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
            });*/
            return itemView;
        }

    private void dismiss(AlertDialog.Builder dialog) {
//        dialog.dismiss();
    }


    private void cancelclin(final String staffID,final AlertDialog.Builder dialog){
        /*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CANCEL_CLIINIC1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            dialog.dismiss();
                            context.finish();
                            Toast.makeText(context, "Record updated Successfully.", Toast.LENGTH_LONG).show();
//                            ViewClinic.contactDetails1.remove(position);
                            /*JSONObject jsonObject =new JSONObject(response);
                            String result = jsonObject.getString("Result");
                            if (result.equals("Recorded Deleted Successfully")) {

                                Toast.makeText(context, "Record Deleted Successfully.", Toast.LENGTH_LONG).show();
                            }
                            else{

                            }
                            *//*Intent returnIntent = new Intent();
                            returnIntent.putExtra("result","ok");
                            setResult(Activity.RESULT_OK,returnIntent);*//*
                            context.finish();*/
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
                                    startActivity(intent1);*///                            }
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
                params.put("user_id",staffID);

//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    private void cancelAppointment(final int position,final Dialog dialog,final String staffID
    ,final String email ,final String name ,final String password
    ,final String lat,final String lng){
        /*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CANCEL_URLCLIINIC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            dialog.dismiss();
                            context.finish();
                            Toast.makeText(context, "Record updated Successfully.", Toast.LENGTH_LONG).show();
//                            ViewClinic.contactDetails1.remove(position);
                            /*JSONObject jsonObject =new JSONObject(response);
                            String result = jsonObject.getString("Result");
                            if (result.equals("Recorded Deleted Successfully")) {

                                Toast.makeText(context, "Record Deleted Successfully.", Toast.LENGTH_LONG).show();
                            }
                            else{

                            }
                            *//*Intent returnIntent = new Intent();
                            returnIntent.putExtra("result","ok");
                            setResult(Activity.RESULT_OK,returnIntent);*//*
                            context.finish();*/
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
                                    startActivity(intent1);*///                            }
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
                params.put("user_id",staffID);
                params.put("first_name",name);
                params.put("last_name",name);
                params.put("email",email);
                params.put("password",password);
                params.put("lat",lat);
                params.put("lng",lng);
                params.put("address","");
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

