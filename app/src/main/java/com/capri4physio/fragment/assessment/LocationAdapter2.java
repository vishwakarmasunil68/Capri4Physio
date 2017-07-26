package com.capri4physio.fragment.assessment;

import android.content.Context;
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
import com.capri4physio.net.ApiConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by emobi5 on 6/23/2016.
 */


    public class LocationAdapter2 extends ArrayAdapter {
    String numbercancel;
        Context context;
        SharedPreferences sp;
        ArrayList<String> stringArrayList= new ArrayList<String>();;
        int vgreso;
//    String bitmapgallery=sp.getString("accepted", null);
//    stringArrayList = new ArrayList<String>();
//    stringArrayList.add(bitmapgallery);
//    Log.e("selected", stringArrayList.toString());


        public LocationAdapter2(Context context, int resource) {

            super(context, resource);
            this.context=context;
            this.vgreso=resource;
        }

        @Override
        public int getCount() {

            return ApiActivity.deatilsarraylist.size();
        }

        @Override
        public Object getItem(int position) {
            return  ApiActivity.deatilsarraylist.get(position);

        }


        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(vgreso, parent, false);
//        final ProductBooen p = getProduct(position);
            ImageView img = (ImageView) itemView.findViewById(R.id.img);
            TextView textcontac = (TextView) itemView.findViewById(R.id.textcontact);
            TextView view = (TextView) itemView.findViewById(R.id.view);
            TextView edit = (TextView) itemView.findViewById(R.id.edit);
            textcontac.setText(ApiActivity.deatilsarraylist.get(position).getName().toString());
            view.setText(ApiActivity.deatilsarraylist.get(position).getAppname().toString());
            edit.setText(ApiActivity.deatilsarraylist.get(position).getNumber().toString());


            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final InfoApps positiin = (ApiActivity.deatilsarraylist.get(position));
                    final  String  posn = ApiActivity.deatilsarraylist.get(position).getId().toString();
                    delepnotes(positiin,posn);
//                    note_iid = CaseNotesFragment.contactDetails1.get(position).getSend_date().toString();
                   /* AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure, you want to delete");
                    builder.setCancelable(false);
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {



                        }
                    });
                    builder.setNegativeButton(android.R.string.no, null);
                    builder.create();
                    builder.show();*/


                }
            });

            return itemView;
        }
    private void delepnotes(final InfoApps textView,final String ID){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.DELETE_CANCELAPPOINTMENTS  ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            ApiActivity.deatilsarraylist.remove(textView);
                            notifyDataSetChanged();
                            Toast.makeText(context,"Record successfully deleted",Toast.LENGTH_SHORT).show();
//                            showSnackMessage("Record successfully removed");

                        }

                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.e("Postdat", "" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                    }
                }){


            protected Map<String,String> getParams(){
                Map<String,String> objresponse = new HashMap<String, String>();
                objresponse.put("appointment_id", ID);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


}

