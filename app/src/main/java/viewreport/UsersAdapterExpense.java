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
import com.capri4physio.Invoice.InfoApps1;
import com.capri4physio.R;
import com.capri4physio.net.ApiConfig;

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

public class UsersAdapterExpense extends RecyclerView.Adapter<UsersAdapterExpense.MyViewHolder> {
    Boolean flag=false;
    private List<InfoApps1> moviesList;
    String invo;
    public static String reason,patient_name,patient_Email;
Context con;
    Activity ctx;
    public  static String invo_id,invo_patient_name,invo_bill_amount,invo_due_amount,invo_paid_amount,
            invo_status,invo_bill_number,invo_pro_name,invo_pro_quantity,invo_staff,invo_date,invo_pay_mode;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,year, genre,txt_total;
        ImageView edit,mail,cancel,prescription,View;
        Button status;
        CardView cv;



        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_patient_id);
            genre = (TextView) view.findViewById(R.id.txt_patient_id);
            txt_total = (TextView) view.findViewById(R.id.txt_total);
            View= (ImageView) view.findViewById(R.id.view);
            edit= (ImageView) view.findViewById(R.id.edit);
            cancel= (ImageView) view.findViewById(R.id.cancel);
            mail= (ImageView) view.findViewById(R.id.dialog_mail);
//            cv = (CardView) view.findViewById(R.id.cv);

            edit.setVisibility(view.VISIBLE);
            cancel.setVisibility(view.VISIBLE);
            mail.setVisibility(view.GONE);
        }
    }


    public UsersAdapterExpense(List<InfoApps1> moviesList, Activity ctx) {
        this.moviesList = moviesList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_patientexpense, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final InfoApps1 movie = moviesList.get(position);
        holder.title.setText(movie.getName());
//        holder.title.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "AlexBrush-Regular.ttf"));
        holder.title.setTypeface(Typeface.create("Montez-Regular.ttf", Typeface.BOLD));


        holder.View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ctx, android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.cusotm_dialogexpense_view);
                dialog.setTitle("Expense - Report");

                //adding text dynamically
                TextView patient_name = (TextView) dialog.findViewById(R.id.Patient_name);
                patient_name.setText(movie.getName());

                TextView bill_number = (TextView) dialog.findViewById(R.id.Bill_number);
                bill_number.setText(movie.getNumber());

                TextView bill_amount = (TextView) dialog.findViewById(R.id.Bill_amount);
                bill_amount.setText(movie.getBill_amount());


                TextView invodate = (TextView) dialog.findViewById(R.id.Invoice_date);
                invodate.setText(movie.getInvo_date());


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
        holder.mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ctx, android.R.style.Theme_DeviceDefault_Dialog);

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
                dialog.setContentView(R.layout.cusotm_dialogexpense_edit);
                dialog.setTitle("Expense - Report");

                //adding text dynamically
                final EditText patient_name = (EditText) dialog.findViewById(R.id.Patient_name);
                patient_name.setText(movie.getName());

                final  EditText bill_number = (EditText) dialog.findViewById(R.id.Bill_number);
                bill_number.setText(movie.getNumber());

                final  EditText bill_amount = (EditText) dialog.findViewById(R.id.Bill_amount);
                bill_amount.setText(movie.getBill_amount());


                final  EditText invodate = (EditText) dialog.findViewById(R.id.Invoice_date);
                invodate.setText(movie.getInvo_date());

                final String ID = ViewIncomeExpensetReport.contactDetails1.get(position).getId().toString();

                //adding button click event
                Button dismissButton = (Button) dialog.findViewById(R.id.button);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editAppointment(patient_name.getText().toString(),bill_number.getText().toString(),bill_amount.getText().toString(),ID);
                        dialog.dismiss();
                    }
                });
                dialog.show();

                    }
        });
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ID = ViewIncomeExpensetReport.contactDetails1.get(position).getId().toString();
                final InfoApps1 posn = ViewIncomeExpensetReport.contactDetails1.get(position);
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
                        cancelAppointment(ID,posn);
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
    private void editAppointment(final String name , final String bill_Number, final String bill_Amount , final String ID){

        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_EDITABLEEXPENSE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(ctx, "Information has been updated successfully.", Toast.LENGTH_LONG).show();


                            Log.e("result",response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w("Postdat", "" + error);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("exp_id",ID);
                params.put("exp_vendor", name);
                params.put("exp_billno", bill_Number);
                params.put("exp_total", bill_Amount);

//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);
    }

            private void cancelAppointment(final String invo , final InfoApps1 posn){
        /*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
                StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.DELETE_EXPENSE_,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    moviesList.remove(posn);
                                    notifyDataSetChanged();
                                    Toast.makeText(ctx, "Record deleted successfully.", Toast.LENGTH_LONG).show();

                                    Log.e("result",response);
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
                        params.put("exp_id",invo);

                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(ctx);
                requestQueue.add(stringRequest);
            }
}
