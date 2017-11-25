package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import com.capri4physio.R;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.pojo.notes.ADLResultPOJO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create to bind jobs in list
 *
 * @author prabhunathy
 * @version 1.0
 * @since 1/4/16.
 */

public class UsersAdapterADL extends RecyclerView.Adapter<UsersAdapterADL.MyViewHolder> {
    private List<ADLResultPOJO> adlResultPOJOList;
    Dialog dialog;
    Activity activity;
    ADLExamFragment adlExamFragment;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date;
        ImageView edit, medication, prescription;
        private ImageView iv_delete;
        private ImageView iv_edit, img_view;
        Button status;

        public MyViewHolder(View view) {
            super(view);
            img_view = (ImageView) itemView.findViewById(R.id.img_view);
            iv_delete = (ImageView) itemView.findViewById(R.id.img);
            iv_edit = (ImageView) itemView.findViewById(R.id.img_attachment);
            tv_date = (TextView) view.findViewById(R.id.txt_patient_id);

        }
    }


    public UsersAdapterADL(List<ADLResultPOJO> adlResultPOJOList, Activity activity, ADLExamFragment adlExamFragment) {
        this.adlResultPOJOList = adlResultPOJOList;
        this.activity = activity;
        this.adlExamFragment = adlExamFragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_sensoryadapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        String movie = moviesList.get(position);
//        String date=movie.getNumber();

        holder.tv_date.setText(adlResultPOJOList.get(position).getAdldate());

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlert(position, adlResultPOJOList.get(position).getAdlid());
            }
        });
        holder.img_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    adlExamFragment.ViewNeuroFragment(adlResultPOJOList.get(position));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    adlExamFragment.updateNeuroReport(adlResultPOJOList.get(position));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void deleteAlert(final int position, final String ID) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure, you want to delete");
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                delepnotes(position, ID);

            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.create();
        builder.show();
    }


    private void delepnotes(final int position, final String ID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.DELETE_ADL_EXAM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result", response);
                            adlResultPOJOList.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(activity, "Record successfully deleted", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
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
                }) {


            protected Map<String, String> getParams() {
                Map<String, String> objresponse = new HashMap<String, String>();
                objresponse.put("id", ID);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        Log.e("sizepre", String.valueOf(adlResultPOJOList.size()));
        return adlResultPOJOList.size();
    }

}
