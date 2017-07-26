package com.capri4physio.adapter.assessment;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.assessment.MedicalItem;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create to bind jobs in list
 *
 * @author prabhunathy
 * @version 1.0
 * @see RecyclerView.Adapter
 * @since 1/4/16.
 */

public class MedicalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    Dialog dialog;
    private Context context;
    private List<MedicalItem> mList;
    private ViewItemClickListener<MedicalItem> mCallback;

    public MedicalAdapter(Context context, List<MedicalItem> mList, ViewItemClickListener<MedicalItem> callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mList = mList;
        mCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.row_medical_dignosis, parent, false);
        viewHolder = new UserViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        UserViewHolder stateViewHolder = (UserViewHolder) viewHolder;
        configureItemViewHolder(stateViewHolder, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImg;
        private ImageView mImg_edit;
        private TextView mTxtTitle,mTxtTitle1;

        public UserViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.img);
            mTxtTitle1 = (TextView) itemView.findViewById(R.id.txt_title1);
            mImg_edit = (ImageView) itemView.findViewById(R.id.img_edit);
            mTxtTitle = (TextView) itemView.findViewById(R.id.txt_title);
        }
    }

    private void configureItemViewHolder(final UserViewHolder holder, final int position) {

        holder.mTxtTitle.setText(capsFirstLetter(mList.get(position).getDescription()));
        holder.mTxtTitle1.setText(capsFirstLetter(mList.get(position).getDate()));
        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onViewItemClick(mList.get(position), position, Constants.ClickIDConst.ID_DELETE_CLICK);
            }
        });
        holder.mImg_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(context,android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.medicaldialog_edit);
                dialog.setTitle("Edit - Medical diagnosis");

//                //adding text dynamically
                final EditText blood_pressure = (EditText) dialog.findViewById(R.id.patient_name);

                final String Therapy = (mList.get(position).getDescription().toString());
                blood_pressure.setText(Therapy);


                //adding button click event
                Button dismissButton = (Button) dialog.findViewById(R.id.button);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getpnotes(holder.mTxtTitle,mList.get(position).getId(),blood_pressure.getText().toString());


                    }
                });
                dialog.show();
            }
        });

    }

    private String capsFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
    private void getpnotes(final TextView text,final String Id,final String therapy){

//        final String casedesc =editTextcontents.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.EDIT_MEDICAL  ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            dialog.dismiss();
                            text.setText(therapy);
                            mCallback.onViewItemClick(mList.get(0), 0, Constants.ClickIDConst.ID_ATTACHMENT_CLICK);

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
                Map<String,String> params = new HashMap<String, String>();

                params.put("description", therapy);
                params.put("medical_id", Id);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
