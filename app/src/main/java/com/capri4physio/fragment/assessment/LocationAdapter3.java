package com.capri4physio.fragment.assessment;

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

import com.capri4physio.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emobi5 on 6/23/2016.
 */


    public class LocationAdapter3 extends ArrayAdapter {

        Context context;
        SharedPreferences sp;
        ArrayList<String> stringArrayList= new ArrayList<String>();;
    public static String numberview;
        int vgreso;
    List<InfoApps> lst;
//    String bitmapgallery=sp.getString("accepted", null);
//    stringArrayList = new ArrayList<String>();
//    stringArrayList.add(bitmapgallery);
//    Log.e("selected", stringArrayList.toString());


        public LocationAdapter3(Context context, int resource, List<InfoApps> lst) {

            super(context, resource);
            this.context=context;
            this.vgreso=resource;
            this.lst=lst;
            for(InfoApps infoApps:lst){
                Log.e("sun",infoApps.toString());
            }
        }

        @Override
        public int getCount() {

            return lst.size();
        }

        @Override
        public Object getItem(int position) {
            return  lst.get(position);

        }


        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(vgreso, parent, false);
//        final ProductBooen p = getProduct(position);
            TextView textcontac = (TextView) itemView.findViewById(R.id.textcontact);
             TextView view = (TextView) itemView.findViewById(R.id.view);
            TextView edit = (TextView) itemView.findViewById(R.id.edit);
            TextView textcontact1 = (TextView) itemView.findViewById(R.id.textcontact1);
            TextView textcontact2 = (TextView) itemView.findViewById(R.id.textcontact2);
            TextView textcontact3 = (TextView) itemView.findViewById(R.id.textcontact3);
            TextView textcontact4 = (TextView) itemView.findViewById(R.id.textcontact4);
            TextView textcontact5 = (TextView) itemView.findViewById(R.id.textcontact5);
            TextView textcontact6 = (TextView) itemView.findViewById(R.id.textcontact6);
            textcontac.setText(lst.get(position).getName().toString());
            view.setText(lst.get(position).getAppname().toString());
            edit.setText(lst.get(position).getNumber().toString());
//            textcontact6.setText(GetFeedbackActivity.feebacklist.get(position).getData().toString());
            textcontact1.setText(lst.get(position).getStr4().toString());
            textcontact2.setText(lst.get(position).getPatientid().toString());
            textcontact3.setText(lst.get(position).getDoctorid().toString());
            textcontact4.setText(lst.get(position).getDataAdd().toString());
            textcontact5.setText(lst.get(position).getExp().toString());


            return itemView;
        }


    }

