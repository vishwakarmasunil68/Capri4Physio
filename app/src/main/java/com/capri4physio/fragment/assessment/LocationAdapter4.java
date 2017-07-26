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

/**
 * Created by emobi5 on 6/23/2016.
 */


    public class LocationAdapter4 extends ArrayAdapter {

        Context context;
        SharedPreferences sp;
        ArrayList<String> stringArrayList= new ArrayList<String>();;
    public static String numberview;
        int vgreso;
//    String bitmapgallery=sp.getString("accepted", null);
//    stringArrayList = new ArrayList<String>();
//    stringArrayList.add(bitmapgallery);
//    Log.e("selected", stringArrayList.toString());


        public LocationAdapter4(Context context, int resource) {

            super(context, resource);
            this.context=context;
            this.vgreso=resource;
        }

        @Override
        public int getCount() {

            return GetLUserList1.patientaray.size();
        }

        @Override
        public Object getItem(int position) {
            return  GetLUserList1.patientaray.get(position);

        }


        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(vgreso, parent, false);
//        final ProductBooen p = getProduct(position);
            TextView textcontac = (TextView) itemView.findViewById(R.id.textcontact);
             TextView view = (TextView) itemView.findViewById(R.id.textnumber);
            ImageView edit = (ImageView) itemView.findViewById(R.id.edit);
            textcontac.setText(GetLUserList1.patientaray.get(position).getName().toString());
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     numberview=GetLUserList1.patientaray.get(position).getNumber().toString();
                    String nameedit=GetLUserList1.patientaray.get(position).getName().toString();
                    Intent editIntent = new Intent(context,ApiActivity.class);
                    editIntent.putExtra("phone_no", numberview);
                    editIntent.putExtra("nameedit", nameedit);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(editIntent);
                }
            });


            return itemView;
        }


    }

