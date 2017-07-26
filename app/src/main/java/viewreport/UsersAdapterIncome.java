package viewreport;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
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

public class UsersAdapterIncome extends RecyclerView.Adapter<UsersAdapterIncome.MyViewHolder> {
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
            genre = (TextView) view.findViewById(R.id.txt_patient_id);
            View= (ImageView) view.findViewById(R.id.view);
            edit= (ImageView) view.findViewById(R.id.edit);
            cancel= (ImageView) view.findViewById(R.id.cancel);
            mail= (ImageView) view.findViewById(R.id.dialog_mail);
//            cv = (CardView) view.findViewById(R.id.cv);

            edit.setVisibility(view.GONE);
            cancel.setVisibility(view.GONE);
            mail.setVisibility(view.GONE);
        }
    }


    public UsersAdapterIncome(List<InfoApps1> moviesList, Activity ctx) {
        this.moviesList = moviesList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_patient_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final InfoApps1 movie = moviesList.get(position);
        holder.title.setText(movie.getName());
//        holder.title.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "AlexBrush-Regular.ttf"));
        holder.title.setTypeface(Typeface.create("Montez-Regular.ttf", Typeface.BOLD));
//        holder.genre.setText(movie.getBill_amount());


        holder.View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ctx, android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.cusotm_dialog_edit);
                dialog.setTitle("Invoice - Detail");

                //adding text dynamically
                TextView patient_name = (TextView) dialog.findViewById(R.id.Patient_name);
                patient_name.setText(movie.getName());
                TextView staff_name = (TextView) dialog.findViewById(R.id.Staff_Name);
                staff_name.setText(movie.getStaff_name());
                /*ImageView image = (ImageView)dialog.findViewById(R.id.image);
                image.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.ic_dialog_email));*/
                TextView bill_number = (TextView) dialog.findViewById(R.id.Bill_number);
                bill_number.setText(movie.getNumber());
                TextView bill_amount = (TextView) dialog.findViewById(R.id.Bill_amount);
                bill_amount.setText(movie.getBill_amount());
                TextView paid_amount = (TextView) dialog.findViewById(R.id.Paid_amount);
                paid_amount.setText(movie.getPaid_amount());
                TextView due_amount = (TextView) dialog.findViewById(R.id.Due_amount);
                due_amount.setText(movie.getDue_amount());
                TextView pro_name = (TextView) dialog.findViewById(R.id.Pro_name);
                pro_name.setText(movie.getPro_name());
                TextView pro_quantity = (TextView) dialog.findViewById(R.id.Pro_quantity);
                pro_quantity.setText(movie.getPro_quantity());
                TextView invodate = (TextView) dialog.findViewById(R.id.Invoice_date);
                invodate.setText(movie.getInvo_date());
                TextView invo_staaus = (TextView) dialog.findViewById(R.id.Invoice_status);
                invo_staaus.setText(movie.getStatus());

                //adding button click event
                Button dismissButton = (Button) dialog.findViewById(R.id.button);
                Button button_print = (Button) dialog.findViewById(R.id.button_print);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                button_print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                dialog.show();
            }
        });
        holder.mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ctx, android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.cusotm_dialog_mail);
                dialog.setTitle("Income - Statement");

                //adding text dynamically
                TextView txt = (TextView) dialog.findViewById(R.id.textView);
                txt.setText("Income - Statement");
                TextView name = (TextView) dialog.findViewById(R.id.patient_name);
                name.setText(movie.getName());
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                image.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.ic_dialog_email));

                //adding button click event
                Button dismissButton = (Button) dialog.findViewById(R.id.button);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ctx, android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.cusotm_dialog_view);
                dialog.setTitle("Invoice - Detail");

                //adding text dynamically
                invo=movie.getId();
                TextView patient_name = (TextView) dialog.findViewById(R.id.Patient_name);
                patient_name.setText(movie.getName());
               invo_patient_name=patient_name.getText().toString();

                TextView staff_name = (TextView) dialog.findViewById(R.id.Staff_Name);
                staff_name.setText(movie.getStaff_name());
                invo_staff=staff_name.getText().toString();
                /*ImageView image = (ImageView)dialog.findViewById(R.id.image);
                image.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.ic_dialog_email));*/
                TextView bill_number = (TextView) dialog.findViewById(R.id.Bill_number);
                bill_number.setText(movie.getNumber());
                invo_bill_number=bill_number.getText().toString();
                TextView bill_amount = (TextView) dialog.findViewById(R.id.Bill_amount);
                bill_amount.setText(movie.getBill_amount());
                invo_bill_amount=bill_amount.getText().toString();
                TextView paid_amount = (TextView) dialog.findViewById(R.id.Paid_amount);
                paid_amount.setText(movie.getPaid_amount());
                invo_paid_amount=paid_amount.getText().toString();
                TextView due_amount = (TextView) dialog.findViewById(R.id.Due_amount);
                due_amount.setText(movie.getDue_amount());
                invo_due_amount=due_amount.getText().toString();
                TextView pro_name = (TextView) dialog.findViewById(R.id.Pro_name);
                pro_name.setText(movie.getPro_name());
                invo_pro_name=pro_name.getText().toString();
                TextView pro_quantity = (TextView) dialog.findViewById(R.id.Pro_quantity);
                pro_quantity.setText(movie.getPro_quantity());
                invo_pro_quantity=pro_quantity.getText().toString();
                TextView invodate = (TextView) dialog.findViewById(R.id.Invoice_date);
                invodate.setText(movie.getInvo_date());
                invo_date=invodate.getText().toString();
                TextView invo_staaus = (TextView) dialog.findViewById(R.id.Invoice_status);
                invo_staaus.setText(movie.getStatus());
                invo_status=invo_staaus.getText().toString();

                //adding button click event
                Button dismissButton = (Button) dialog.findViewById(R.id.button);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        dialog.dismiss();
                        editAppointment();
                    }
                });
                dialog.show();
            }
        });
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invo=movie.getId();
//                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
//                builder.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(ctx,"enter a text here", Toast.LENGTH_LONG).show();
//                    }
//                })
//                        .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                ctx.finish();
//                            })
//                            // Create the AlertDialog object and return it
//                            return builder.create();
//                        });

               final android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(ctx);
                dialog.setMessage("Are you sure to Cancel ");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub
                        //get gps
//                Toast.makeText(CancelAppointmentPatient.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                        cancelAppointment();
                    }
                });
                dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub
                        dialog.setCancelable(true);;
                    }
                });
                dialog.show();


/*                                                *//*invo_id = movie.getId();
                                                invo_staff = movie.getStaff_name();
                                                patient_name = movie.getName();
                                                patient_name = movie.getName();
                                                Intent intent = new Intent(ctx, ViewDetailBill.class);
//                                                reason=ViewPatientList.contactDetails1.get(position).getId().toString();
//                                                patient_name=ViewPatientList.contactDetails1.get(position).getName().toString();
//                                                patient_Email=ViewPatientList.contactDetails1.get(position).getEmail_id().toString();
                                                intent.putExtra("reason", reason);
                                                intent.putExtra("patient_name", patient_name);
                                                intent.putExtra("patient_Email", patient_Email);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                ctx.startActivity(intent);*//*
                                            }
                                        });
//                holder.title.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        device_token = movie.getDevice_token();
//                        patient_name = movie.getName();
//
//                        Intent intent = new Intent(ctx, ChatActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        ctx.startActivity(intent);
//                    }
//                });

//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                 reason=ViewPatientList.contactDetails1.get(position).getId().toString();
//                 patient_name=ViewPatientList.contactDetails1.get(position).getName().toString();
//                 patient_Email=ViewPatientList.contactDetails1.get(position).getEmail_id().toString();
//                Log.e("login_id",reason);
//                Intent msgintent=new Intent(ctx, Updatepatientinlist.class);
//                msgintent.putExtra("reason", reason);
//                msgintent.putExtra("patient_name", patient_name);
//                msgintent.putExtra("patient_Email", patient_Email);
//                msgintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                ctx.startActivity(msgintent);
//
//
//            }
//        });
//        holder.medication.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String reason=ViewPatientList.contactDetails1.get(position).getId().toString();
//                *//*String patient_name=ViewPatientList.contactDetails1.get(position).getName().toString();
//                String patient_Email=ViewPatientList.contactDetails1.get(position).getEmail_id().toString();*//*
//                Log.e("login_id",reason);
//                Intent msgintent=new Intent(ctx, Medication.class);
//                msgintent.putExtra("reason", reason);
//                *//*msgintent.putExtra("patient_name", patient_name);
//                msgintent.putExtra("patient_Email", patient_Email);*//*
//                msgintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                ctx.startActivity(msgintent);
//
//
//            }
//        });
//        holder.prescription.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String reason=ViewPatientList.contactDetails1.get(position).getId().toString();
//                *//*String patient_name=ViewPatientList.contactDetails1.get(position).getName().toString();
//                String patient_Email=ViewPatientList.contactDetails1.get(position).getEmail_id().toString();*//*
//                Log.e("login_id",reason);
//                Intent msgintent=new Intent(ctx, Prescription.class);
//                msgintent.putExtra("reason", reason);
//                *//*msgintent.putExtra("patient_name", patient_name);
//                msgintent.putExtra("patient_Email", patient_Email);*//*
//                msgintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                ctx.startActivity(msgintent);
//
//
//            }
//        });
//        holder.status.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ViewPatientList.savechange.setVisibility(View.VISIBLE);
//                if (flag==false){
//                    flag=true;
//                    holder.status.setText("Deactive");
//                    holder.status.setTextColor(holder.status.getContext().getResources().getColor(R.color.white));
//                    holder.status.setBackgroundColor(holder.status.getContext().getResources().getColor(R.color.colorBtnPressed));
//                }
//                else if (flag==true){
//                    flag=false;
//                holder.status.setText("Active");
//                holder.status.setBackgroundColor(holder.status.getContext().getResources().getColor(R.color.green_500));
//                }
//            }
//        });*/
            }
        });
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
