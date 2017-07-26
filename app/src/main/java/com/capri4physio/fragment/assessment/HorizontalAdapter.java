/*
package com.capri4physio.fragment.assessment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.capri4physio.R;

import java.util.ArrayList;

*/
/**
 * Created by emobi5 on 6/23/2016.
 *//*



    public class HorizontalAdapter extends ArrayAdapter {

        Context context;
        SharedPreferences sp;
        ArrayList<String> stringArrayList= new ArrayList<String>();;
    public static String numberview;
        int vgreso;
//    String bitmapgallery=sp.getString("accepted", null);
//    stringArrayList = new ArrayList<String>();
//    stringArrayList.add(bitmapgallery);
//    Log.e("selected", stringArrayList.toString());


        public HorizontalAdapter(Context context, int resource) {

            super(context, resource);
            this.context=context;
            this.vgreso=resource;
        }

        @Override
        public int getCount() {

            return AddTreatmentGivenFragment.sports.size();
        }

        @Override
        public Object getItem(int position) {
            return  AddTreatmentGivenFragment.sports.get(position);

        }


        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(vgreso, parent, false);
//        final ProductBooen p = getProduct(position);
            TextView textcontac = (TextView) itemView.findViewById(R.id.textcontact);
            final ImageView view = (ImageView) itemView.findViewById(R.id.view);
            ImageView edit = (ImageView) itemView.findViewById(R.id.edit);
            ImageView cancel = (ImageView) itemView.findViewById(R.id.cancel);
            textcontac.setText(GetLUserList.patientaray.get(position).getName().toString());

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
//                    view.setBackgroundResource(R.drawable.btnselector);
                     numberview=GetLUserList.patientaray.get(position).getNumber().toString();
                    String patientname=GetLUserList.patientaray.get(position).getName().toString();
                    Intent msglocationintent = new Intent(context, Viewpatientinlist.class);
                    msglocationintent.putExtra("phone_no", numberview);
                    msglocationintent.putExtra("patient_name", patientname);
                    msglocationintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(msglocationintent);
                }
            });
//            edit.setBackgroundResource(R.drawable.btnselector);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String numberedit=GetLUserList.patientaray.get(position).getNumber().toString();
                    String nameedit=GetLUserList.patientaray.get(position).getName().toString();
                    Intent editIntent = new Intent(context,ApiActivity.class);
                    editIntent.putExtra("phone_no", numberedit);
                    editIntent.putExtra("nameedit", nameedit);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(editIntent);
                }
            });
//            cancel.setBackgroundResource(R.drawable.btnselector);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String numbercancel=GetLUserList.patientaray.get(position).getNumber().toString();
                    String nameedit=GetLUserList.patientaray.get(position).getName().toString();
                    Intent cancelIntent = new Intent(context,CancelAppointmentPatient.class);
                    cancelIntent.putExtra("cancel_no", numbercancel);
                    cancelIntent.putExtra("nameedit", nameedit);
                    cancelIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(cancelIntent);
                }
            });
            return itemView;
        }


    }

*/
