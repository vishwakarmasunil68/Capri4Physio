package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.capri4physio.R;

import java.util.ArrayList;

/**
 * Created by emobi5 on 6/23/2016.
 */


    public class EnquiryAdapter extends ArrayAdapter {

        Context context;
    Activity activity;
    public static String added_by;
    SharedPreferences sp;
        ArrayList<String> stringArrayList= new ArrayList<String>();;
    public static String patientCode,numberview,numberrr;
        int vgreso;
//    String bitmapgallery=sp.getString("accepted", null);
//    stringArrayList = new ArrayList<String>();
//    stringArrayList.add(bitmapgallery);
//    Log.e("selected", stringArrayList.toString());


        public EnquiryAdapter(Context context, int resource) {

            super(context, resource);
            this.context=context;
            this.vgreso=resource;
        }

        @Override
        public int getCount() {

            return ViewEnquiryList.patientaray.size();
        }

        @Override
        public Object getItem(int position) {
            return  ViewEnquiryList.patientaray.get(position);

        }


        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(vgreso, parent, false);
//        final ProductBooen p = getProduct(position);
            TextView textcontac = (TextView) itemView.findViewById(R.id.textcontact);
            TextView textnumber = (TextView) itemView.findViewById(R.id.textnumber);
            final ImageView view = (ImageView) itemView.findViewById(R.id.view);
            ImageView edit = (ImageView) itemView.findViewById(R.id.edit);
            ImageView cancel = (ImageView) itemView.findViewById(R.id.cancel);
            textcontac.setText(ViewEnquiryList.patientaray.get(position).getPc().toString());
            textnumber.setText(ViewEnquiryList.patientaray.get(position).getName().toString());
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Log.d("dialog", "viewdialog");
                    final Dialog dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Light_Dialog);

                    //setting custom layout to dialog
                    dialog.setContentView(R.layout.viewenquiry);
                    dialog.setTitle("Enquiry - Detail");
                    TextView patient_name=(TextView)dialog.findViewById(R.id.patient_name);
                    TextView patient_id= (TextView)dialog. findViewById(R.id.patient_id);
                    TextView date=(TextView)dialog.findViewById(R.id.booking_date);
                    TextView visit =(TextView)dialog.findViewById(R.id.vist);
                    TextView  _starttime =(TextView)dialog.findViewById(R.id.booking_starttime);
                    TextView  Consultants =(TextView)dialog.findViewById(R.id.Consultants);
                    TextView  edit_subject =(TextView)dialog.findViewById(R.id.enquiry_subject);
                    TextView  enquiry_message =(TextView)dialog.findViewById(R.id.enquiry_message);
                    TextView  booking_reason=(TextView)dialog.findViewById(R.id.reason);



                    patient_id.setText(ViewEnquiryList.patientaray.get(position).getName().toString());
                    patient_name.setText(ViewEnquiryList.patientaray.get(position).getPc().toString());
                    date.setText(ViewEnquiryList.patientaray.get(position).getSend_date().toString());
                    visit.setText(ViewEnquiryList.patientaray.get(position).getAppname().toString());
                    _starttime.setText(ViewEnquiryList.patientaray.get(position).getEnqtime().toString());
                    Consultants.setText(ViewEnquiryList.patientaray.get(position).getStaffname().toString());
                    edit_subject.setText(ViewEnquiryList.patientaray.get(position).getSubject().toString());
                    enquiry_message.setText(ViewEnquiryList.patientaray.get(position).getMsg().toString());
//                    booking_reason.setText(ViewEnquiryList.patientaray.get(position).getName().toString());



                    Button  btn=(Button)dialog.findViewById(R.id.savebtn);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });

            return itemView;
        }


    }

