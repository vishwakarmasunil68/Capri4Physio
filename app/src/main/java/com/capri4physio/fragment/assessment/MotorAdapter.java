package com.capri4physio.fragment.assessment;

import android.app.Activity;
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
import com.capri4physio.fragment.ViewStaff;
import com.capri4physio.model.assessment.MotorExamActivity;

import java.util.ArrayList;

/**
 * Created by emobi5 on 6/23/2016.
 */


    public class MotorAdapter extends ArrayAdapter {
    String numbercancel;
        Activity context;
        SharedPreferences sp;
        ArrayList<String> stringArrayList= new ArrayList<String>();;
        int vgreso;
//    String bitmapgallery=sp.getString("accepted", null);
//    stringArrayList = new ArrayList<String>();
//    stringArrayList.add(bitmapgallery);
//    Log.e("selected", stringArrayList.toString());


        public MotorAdapter(Activity context, int resource) {

            super(context, resource);
            this.context=context;
            this.vgreso=resource;
        }

        @Override
        public int getCount() {

            return MotorExamActivity.contactDetails1.size();
        }

        @Override
        public Object getItem(int position) {
            return  MotorExamActivity.contactDetails1.get(position);

        }


        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(vgreso, parent, false);
//        final ProductBooen p = getProduct(position);
            TextView textcontac = (TextView) itemView.findViewById(R.id.motor_text);
            TextView textcontactdate = (TextView) itemView.findViewById(R.id.motor_textdate);
//            TextView textcontactdate = (TextView) itemView.findViewById(R.id.motor_textdate);
            TextView textcontactdate1 = (TextView) itemView.findViewById(R.id.motor_textdate1);
            TextView textcontactdate2 = (TextView) itemView.findViewById(R.id.motor_textdate2);
            TextView textcontactdate3 = (TextView) itemView.findViewById(R.id.motor_textdate3);
            TextView textcontactdate4 = (TextView) itemView.findViewById(R.id.motor_textdate4);

            textcontac.setText(MotorExamActivity.contactDetails1.get(position).getPatientid().toString());
            textcontactdate.setText(MotorExamActivity.contactDetails1.get(position).getNumber().toString());
            textcontactdate1.setText(MotorExamActivity.contactDetails1.get(position).getStr4().toString());
//            textcontactdate2.setText(MotorExamActivity.contactDetails1.get(position).getData().toString());
            textcontactdate3.setText(MotorExamActivity.contactDetails1.get(position).getExp().toString());
            textcontactdate4.setText(MotorExamActivity.contactDetails1.get(position).getAppname().toString());
            return itemView;
        }


    }

