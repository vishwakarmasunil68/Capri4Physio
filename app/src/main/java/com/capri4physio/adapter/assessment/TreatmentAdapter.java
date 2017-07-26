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
import com.capri4physio.model.assessment.TreatmentItem;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *Create to bind jobs in list
 * @see RecyclerView.Adapter
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 */

public class TreatmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private LayoutInflater inflater;
    Dialog dialog;
    private Context context;
    private List<TreatmentItem> mList;
    private ViewItemClickListener<TreatmentItem> mCallback;

    public TreatmentAdapter(Context context, List<TreatmentItem> mList, ViewItemClickListener<TreatmentItem> callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mList = mList;
        mCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.row_treatment_advice, parent, false);
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
        private ImageView img_edit,mImg;
        private TextView mTxtGoal,mTxtTherapy,mTxtDoses,txt_title1;

        public UserViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView)itemView.findViewById(R.id.img);
            img_edit = (ImageView)itemView.findViewById(R.id.img_edit);
            txt_title1 = (TextView)itemView.findViewById(R.id.txt_title1);
            mTxtGoal = (TextView)itemView.findViewById(R.id.txt_goal);
            mTxtTherapy = (TextView)itemView.findViewById(R.id.txt_therapy);
            mTxtDoses = (TextView)itemView.findViewById(R.id.txt_doses);
        }
    }

    private void configureItemViewHolder(final UserViewHolder holder, final int position) {

        holder.mTxtGoal.setText(mList.get(position).getGoal());
        holder.txt_title1.setText((mList.get(position).getDate()));
        holder.mTxtTherapy.setText(mList.get(position).getTherapy());
        holder.mTxtDoses.setText(mList.get(position).getDose());

        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(context,android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.treatadvi_dialog_edit);
                dialog.setTitle("Edit - Treatment advice");

//                //adding text dynamically
                final EditText blood_pressure = (EditText) dialog.findViewById(R.id.patient_name);
                final  EditText heart_rate = (EditText) dialog.findViewById(R.id.Staff_Name);
                final EditText respiratory_rate = (EditText) dialog.findViewById(R.id.Bill_number);
//                EditText built_patient = (EditText) dialog.findViewById(R.id.blood_pressure);

                final String Therapy = (mList.get(position).getTherapy().toString());
                blood_pressure.setText(Therapy);
                final  String Goal =(mList.get(position).getGoal().toString());
                heart_rate.setText(Goal);
                final String Dose =(mList.get(position).getDose().toString());
                respiratory_rate.setText(Dose);


                //adding button click event
                Button dismissButton = (Button) dialog.findViewById(R.id.button);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getpnotes(mList.get(position).getId(),blood_pressure.getText().toString(),respiratory_rate.getText().toString(),heart_rate.getText().toString());


                    }
                });
                dialog.show();
            }
        });


        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onViewItemClick(mList.get(position), position, Constants.ClickIDConst.ID_DELETE_CLICK);
            }
        });


    }
    private void getpnotes(final String Id,final String therapy,final String trea_amount,final String comment){

//        final String casedesc =editTextcontents.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.EDIT_TREATMENTADVICE  ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            dialog.dismiss();

                            mCallback.onViewItemClick(mList.get(0), -2, Constants.ClickIDConst.ID_ATTACHMENT_CLICK);

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

                params.put("therapy", therapy);
                params.put("goal", comment);
                params.put("dose",trea_amount);
                params.put("treatmentadvice_id", Id);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}
