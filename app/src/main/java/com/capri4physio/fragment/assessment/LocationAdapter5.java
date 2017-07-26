package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.capri4physio.fragment.ViewStaff;
import com.capri4physio.net.ApiConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by emobi5 on 6/23/2016.
 */


    public class LocationAdapter5 extends ArrayAdapter {
    String numbercancel;
    Activity context;
    String CANCEL_URL_STAFF= ApiConfig.BASE_URL+"users/deletestaff";
    public static final String KEY_ID = "id";
        SharedPreferences sp;
        ArrayList<String> stringArrayList= new ArrayList<String>();;
        int vgreso;
//    String bitmapgallery=sp.getString("accepted", null);
//    stringArrayList = new ArrayList<String>();
//    stringArrayList.add(bitmapgallery);
//    Log.e("selected", stringArrayList.toString());


        public LocationAdapter5(Activity context, int resource) {

            super(context, resource);
            this.context=context;
            this.vgreso=resource;
        }

        @Override
        public int getCount() {

            return ViewStaff.contactDetails1.size();
        }

        @Override
        public Object getItem(int position) {
            return  ViewStaff.contactDetails1.get(position);

        }


        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(vgreso, parent, false);
//        final ProductBooen p = getProduct(position);
            TextView textcontac = (TextView) itemView.findViewById(R.id.textcontact);
            final ImageView view = (ImageView) itemView.findViewById(R.id.view);
            final ImageView getcancelview = (ImageView) itemView.findViewById(R.id.getcancel);
            final TextView position_tv= (TextView) itemView.findViewById(R.id.position_tv);
            ImageView edit = (ImageView) itemView.findViewById(R.id.edit);
            ImageView cancel = (ImageView) itemView.findViewById(R.id.cancel);

            /*edit.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);*/
            textcontac.setText(ViewStaff.contactDetails1.get(position).getName().toString());
            position_tv.setText((position+1)+"");
//            view.setText(ViewStaff.contactDetails1.get(position).getNumber().toString());
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
//                    view.setBackgroundResource(R.drawable.btnselector);
                    String staffid= ViewStaff.contactDetails1.get(position).getId();
                    String staffname= ViewStaff.contactDetails1.get(position).getStr4();
                    String staffemail= ViewStaff.contactDetails1.get(position).getName();
                    String staffphone_no= ViewStaff.contactDetails1.get(position).getNumber();
                    String dob= ViewStaff.contactDetails1.get(position).getDob();
                    String Age= ViewStaff.contactDetails1.get(position).getGender();
                    String doj= ViewStaff.contactDetails1.get(position).getDoj();
                    String exp= ViewStaff.contactDetails1.get(position).getExp();
                    String Quali= ViewStaff.contactDetails1.get(position).getQualification();
                    String pc= ViewStaff.contactDetails1.get(position).getPc();
                    String city= ViewStaff.contactDetails1.get(position).getCity();
                    String maried_status= ViewStaff.contactDetails1.get(position).getMarital_status();
                    String saddress= ViewStaff.contactDetails1.get(position).getDataAdd();
                    String desi= ViewStaff.contactDetails1.get(position).getDesig();
                    String desi1= ViewStaff.contactDetails1.get(position).getDesignation1();
                    String Staffcode= ViewStaff.contactDetails1.get(position).getStaffcode();
                    String send_date= ViewStaff.contactDetails1.get(position).getSend_date();

                    String work= ViewStaff.contactDetails1.get(position).getWork();
                    String organisation= ViewStaff.contactDetails1.get(position).getOrganisation();
                    String degree= ViewStaff.contactDetails1.get(position).getDegree();
                    String End= ViewStaff.contactDetails1.get(position).getEnd();
                    String institution= ViewStaff.contactDetails1.get(position).getInstitution();
                    String university= ViewStaff.contactDetails1.get(position).getUniversity();
                    String duration= ViewStaff.contactDetails1.get(position).getDuration();
                    String average= ViewStaff.contactDetails1.get(position).getAverage();

                    Intent editIntent = new Intent(context,ViewDetailStaff.class);
                    editIntent.putExtra("staffid", staffid);
                    editIntent.putExtra("staffname", staffname);
                    editIntent.putExtra("staffemail", staffemail);
                    editIntent.putExtra("staffphone_no", staffphone_no);
                    editIntent.putExtra("dob", dob);
                    editIntent.putExtra("maried_status", maried_status);
                    editIntent.putExtra("Age", Age);
                    editIntent.putExtra("doj", doj);
                    editIntent.putExtra("exp", exp);
                    editIntent.putExtra("Quali", Quali);
                    editIntent.putExtra("pc", pc);
                    editIntent.putExtra("saddress", saddress);
                    editIntent.putExtra("city", city);
                    editIntent.putExtra("desi", desi);
                    editIntent.putExtra("designation1", desi1);
                    editIntent.putExtra("Staffcode", Staffcode);
                    editIntent.putExtra("send_date", send_date);

                    editIntent.putExtra("work", work);
                    editIntent.putExtra("organisation", organisation);
                    editIntent.putExtra("degree", degree);
                    editIntent.putExtra("End", End);
                    editIntent.putExtra("average", average);
                    editIntent.putExtra("university", university);
                    editIntent.putExtra("duration", duration);
                    editIntent.putExtra("institution", institution);

                    editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(editIntent);
                }
            });
//            edit.setBackgroundResource(R.drawable.btnselector);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String staffid= ViewStaff.contactDetails1.get(position).getId();
                    String staffname= ViewStaff.contactDetails1.get(position).getStr4();
                    String staffemail= ViewStaff.contactDetails1.get(position).getName();
                    String staffphone_no= ViewStaff.contactDetails1.get(position).getNumber();
                    String dob= ViewStaff.contactDetails1.get(position).getDob();
                    String Age= ViewStaff.contactDetails1.get(position).getGender();
                    String doj= ViewStaff.contactDetails1.get(position).getDoj();
                    String exp= ViewStaff.contactDetails1.get(position).getExp();
                    String Quali= ViewStaff.contactDetails1.get(position).getQualification();
                    String pc= ViewStaff.contactDetails1.get(position).getPc();
                    String city= ViewStaff.contactDetails1.get(position).getCity();
                    String maried_status= ViewStaff.contactDetails1.get(position).getMarital_status();
                    String saddress= ViewStaff.contactDetails1.get(position).getDataAdd();
                    String desi= ViewStaff.contactDetails1.get(position).getDesig();
                    String send_date= ViewStaff.contactDetails1.get(position).getSend_date();
                    String desi1= ViewStaff.contactDetails1.get(position).getDesignation1();

                    String work= ViewStaff.contactDetails1.get(position).getWork();
                    String organisation= ViewStaff.contactDetails1.get(position).getOrganisation();
                    String degree= ViewStaff.contactDetails1.get(position).getDegree();
                    String End= ViewStaff.contactDetails1.get(position).getEnd();
                    String institution= ViewStaff.contactDetails1.get(position).getInstitution();
                    String password= ViewStaff.contactDetails1.get(position).getPassword();
                    String university= ViewStaff.contactDetails1.get(position).getUniversity();
                    String duration= ViewStaff.contactDetails1.get(position).getDuration();
                    String average= ViewStaff.contactDetails1.get(position).getAverage();

                    Intent editIntent = new Intent(context,UpdateStaff.class);
                    editIntent.putExtra("staffid", staffid);
                    editIntent.putExtra("staffname", staffname);
                    editIntent.putExtra("staffemail", staffemail);
                    editIntent.putExtra("staffphone_no", staffphone_no);
                    editIntent.putExtra("dob", dob);
                    editIntent.putExtra("maried_status", maried_status);
                    editIntent.putExtra("Age", Age);
                    editIntent.putExtra("doj", doj);
                    editIntent.putExtra("exp", exp);
                    editIntent.putExtra("Quali", Quali);
                    editIntent.putExtra("pc", pc);
                    editIntent.putExtra("saddress", saddress);
                    editIntent.putExtra("city", city);
                    editIntent.putExtra("desi", desi);
                    editIntent.putExtra("designation1", desi1);
                    editIntent.putExtra("send_date", send_date);

                    editIntent.putExtra("work", work);
                    editIntent.putExtra("organisation", organisation);
                    editIntent.putExtra("degree", degree);
                    editIntent.putExtra("End", End);
                    editIntent.putExtra("password", password);
                    editIntent.putExtra("average", average);
                    editIntent.putExtra("university", university);
                    editIntent.putExtra("duration", duration);
                    editIntent.putExtra("institution", institution);

                    editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(editIntent);
                    context.finish();
                }
            });
//            cancel.setBackgroundResource(R.drawable.btnselector);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                final String staffid= ViewStaff.contactDetails1.get(position).getId();

                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setMessage("Are you sure to Cancel ");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            //get gps
//                Toast.makeText(CancelAppointmentPatient.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                            cancelAppointment(staffid);
                        }
                    });
                    dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            context.finish();
                        }
                    });
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

    private void cancelAppointment(final String staffID){
        /*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CANCEL_URL_STAFF,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(context, "Cancel request has been accpeted successfully.", Toast.LENGTH_LONG).show();

                            /*Intent returnIntent = new Intent();
                            returnIntent.putExtra("result","ok");
                            setResult(Activity.RESULT_OK,returnIntent);*/
                            context.finish();
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
                params.put("users_id",staffID);
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

