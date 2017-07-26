package com.capri4physio.motorexamreport;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.capri4physio.R;
import com.capri4physio.fragment.ViewClinic;
import com.capri4physio.fragment.assessment.AddMotorExamFragment;
import com.capri4physio.fragment.assessment.UpdateStaff;
import com.capri4physio.net.ApiConfig;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by emobi5 on 6/23/2016.
 */


public class LocationAdapterElbow extends ArrayAdapter {
    String numbercancel;
    Activity context;
    String CANCEL_URLCLIINIC = ApiConfig.BASE_URL + "users/deleteclinichead";
    public static final String KEY_ID = "user_id";
    SharedPreferences sp;
    ArrayList<String> stringArrayList = new ArrayList<String>();
    ;
    int vgreso;
//    String bitmapgallery=sp.getString("accepted", null);
//    stringArrayList = new ArrayList<String>();
//    stringArrayList.add(bitmapgallery);
//    Log.e("selected", stringArrayList.toString());


    public LocationAdapterElbow(Activity context, int resource) {

        super(context, resource);
        this.context = context;
        this.vgreso = resource;
    }

    @Override
    public int getCount() {

        return ViewClinic.contactDetails1.size();
    }

    @Override
    public Object getItem(int position) {
        return ViewClinic.contactDetails1.get(position);

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
        textcontac.setText(AddMotorExamFragment.arrylist.get(position).getMoter_examelbow_left_tone1().toString());
        position_tv.setText((position + 1) + "");
//            view.setText(ViewClinic.contactDetails1.get(position).getNumber().toString());
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                    view.setBackgroundResource(R.drawable.btnselector);
                String staffid = ViewClinic.contactDetails1.get(position).getId();
                String staffname = ViewClinic.contactDetails1.get(position).getStr4();
                String staffemail = ViewClinic.contactDetails1.get(position).getName();
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
                patient_name.setText(staffemail);
                    /*TextView staff_name = (TextView) dialog.findViewById(R.id.Staff_Name);
                    staff_name.setText(movie.getStaff_name());
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
                String staffid = ViewClinic.contactDetails1.get(position).getId();
                String staffname = ViewClinic.contactDetails1.get(position).getStr4();
                String staffemail = ViewClinic.contactDetails1.get(position).getName();
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
                String send_date = ViewClinic.contactDetails1.get(position).getSend_date();
                Intent editIntent = new Intent(context, UpdateStaff.class);
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
                editIntent.putExtra("send_date", send_date);
                editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(editIntent);
                context.finish();
            }
        });
        edit.setVisibility(View.GONE);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String staffid = ViewClinic.contactDetails1.get(position).getId();

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("Are you sure to Cancel ");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub
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

            }
        });


        return itemView;
    }

    private void cancelAppointment(final String staffID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CANCEL_URLCLIINIC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("Result");
                            if (result.equals("Recorded Deleted Successfully")) {
                                Toast.makeText(context, "Record Deleted Successfully.", Toast.LENGTH_LONG).show();
                            } else {

                            }
                            context.finish();
                            Log.e("result", response);

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
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_ID, staffID);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}

