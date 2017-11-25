package com.capri4physio.adapter.assessment;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.capri4physio.R;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.assessment.TreatmentGivenItem;
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

public class TreatmentGivenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    Dialog dialog;
    private List<TreatmentGivenItem> mList;
    private ViewItemClickListener<TreatmentGivenItem> mCallback;

    public TreatmentGivenAdapter(Context context, List<TreatmentGivenItem> mList, ViewItemClickListener<TreatmentGivenItem> callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mList = mList;
        mCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.row_treatment_given, parent, false);
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
        private ImageView img_edit, mImg;
        private TextView mTxtTherapy, mTxtTherapist, mTxtComment, mTxtTimeIn, mTxtTimeOut;
        private TextView txt_date;
        private RelativeLayout layout_row;

        public UserViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.img);
            img_edit = (ImageView) itemView.findViewById(R.id.img_edit);
            mTxtTherapy = (TextView) itemView.findViewById(R.id.txt_therapy);
            mTxtTherapist = (TextView) itemView.findViewById(R.id.txt_therapist);
            mTxtComment = (TextView) itemView.findViewById(R.id.txt_comment);
            mTxtTimeIn = (TextView) itemView.findViewById(R.id.txt_time_in);
            mTxtTimeOut = (TextView) itemView.findViewById(R.id.txt_time_out);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            layout_row = (RelativeLayout) itemView.findViewById(R.id.layout_row);
        }
    }

    private void configureItemViewHolder(final UserViewHolder holder, final int position) {

        holder.mTxtTherapy.setText(capsFirstLetter(mList.get(position).getTherapy()));
        holder.mTxtTherapist.setText(mList.get(position).getTrea_staff_name());
        holder.mTxtComment.setText(mList.get(position).getComment());
        holder.mTxtTimeIn.setText("Time IN: " + mList.get(position).getTimeIn());
        holder.mTxtTimeOut.setText("Time OUT: " + mList.get(position).getTimeOut());
        holder.txt_date.setText(mList.get(position).getDate());

        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.treat_dialog_edit);
                dialog.setTitle("Edit - Treatment");

//                //adding text dynamically
                final EditText blood_pressure = (EditText) dialog.findViewById(R.id.Patient_name);
//                EditText temperature= (EditText) dialog.findViewById(R.id.diabetes);
                final EditText heart_rate = (EditText) dialog.findViewById(R.id.Staff_Name);
                final EditText quantity = (EditText) dialog.findViewById(R.id.quantity);
                final EditText respiratory_rate = (EditText) dialog.findViewById(R.id.Bill_number);
                final EditText built_patient = (EditText) dialog.findViewById(R.id.blood_pressure);

                final String Therapy = (mList.get(position).getTherapy().toString());
                blood_pressure.setText(Therapy);
                final String Treatmentamount = (mList.get(position).getQuan_item().toString());
                quantity.setText(Treatmentamount);
                final String Comment = (mList.get(position).getComment().toString());
                heart_rate.setText(Comment);
                final String Timein = mList.get(position).getTimeIn().toString();
                respiratory_rate.setText(Timein);
                final String Time_out = mList.get(position).getTimeOut().toString();
                built_patient.setText(Time_out);


                //adding button click event
                Button dismissButton = (Button) dialog.findViewById(R.id.button);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getpnotes(mList.get(position).getId(), blood_pressure.getText().toString(), quantity.getText().toString(), heart_rate.getText().toString(), respiratory_rate.getText().toString(), built_patient.getText().toString());
                    }
                });
                dialog.show();
            }
        });
        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final int pos =Integer.parseInt(mList.get(position).getId());
                mCallback.onViewItemClick(mList.get(position), position, Constants.ClickIDConst.ID_DELETE_CLICK);
            }
        });

        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTreatmentGivenReport(mList.get(position));
            }
        });

    }

    public void showTreatmentGivenReport(TreatmentGivenItem treatmentGivenItem){
        final Dialog dialog1 = new Dialog(context, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_treatment_report);
        dialog1.setTitle("Treatment");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btn_ok= (Button) dialog1.findViewById(R.id.btn_ok);
        TextView tv_therapist_name= (TextView) dialog1.findViewById(R.id.tv_therapist_name);
        TextView tv_comment= (TextView) dialog1.findViewById(R.id.tv_comment);
        TextView tv_time_in= (TextView) dialog1.findViewById(R.id.tv_time_in);
        TextView tv_time_out= (TextView) dialog1.findViewById(R.id.tv_time_out);
        ImageView iv_signature= (ImageView) dialog1.findViewById(R.id.iv_signature);

        tv_comment.setText(treatmentGivenItem.getComment());
        tv_therapist_name.setText(treatmentGivenItem.getTrea_staff_name());
        tv_time_in.setText(treatmentGivenItem.getTimeIn());
        tv_time_out.setText(treatmentGivenItem.getTimeOut());

        Glide.with(context)
                .load(ApiConfig.IMAGE_BASE_URL+"app/webroot/upload/"+treatmentGivenItem.getSignature())
                .into(iv_signature);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

    }

    private String capsFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    private void getpnotes(final String Id, final String therapy, final String trea_amount, final String comment, final String time_in, final String time_out) {

//        final String casedesc =editTextcontents.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.EDIT_TREATMENTGIVEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result", response);
                            dialog.dismiss();
                            mCallback.onViewItemClick(mList.get(0), -2, Constants.ClickIDConst.ID_DELETE_CLICK);

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
                Map<String, String> params = new HashMap<String, String>();

                params.put("therapy", therapy);
                params.put("trea_amount", trea_amount);
                params.put("comment", comment);
                params.put("time_in", time_in);
                params.put("time_out", time_out);
                params.put("treamentgiven_id", Id);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
