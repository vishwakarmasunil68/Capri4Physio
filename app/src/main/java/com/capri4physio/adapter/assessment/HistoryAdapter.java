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
import com.capri4physio.fragment.assessment.HistoryFragment;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.assessment.HistoryItem;
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

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    Dialog dialog;
    private Context context;
    private EditText patient_name, diabetes, staff_name, bill_number, blood_pressure, paid_amount, smoking, fever_and_chill,
            heart_diseases, recent_infection, bleeding_disorder, any_implants, pregnancy, htn, tb, cancer, hiv_aids, past_surgery, allergies, osteoporotic, depression, Hepatitis, hereditary_disease, et_past_illness, et_present_illness;

    private List<HistoryItem> mList;
    private HistoryFragment historyFragment;
    private ViewItemClickListener<HistoryItem> mCallback;

    public HistoryAdapter(Context context, HistoryFragment historyFragment, List<HistoryItem> mList, ViewItemClickListener<HistoryItem> callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mList = mList;
        this.historyFragment=historyFragment;
        mCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.row_history, parent, false);
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
        private ImageView mImg, mImg_view, img_edit;
        private TextView mTxtTitle, mTxtSurgical, mTxtMedicine;

        public UserViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.img);
            img_edit = (ImageView) itemView.findViewById(R.id.img_edit);
            mImg_view = (ImageView) itemView.findViewById(R.id.img_view);
            mTxtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            mTxtSurgical = (TextView) itemView.findViewById(R.id.date_history);
            mTxtMedicine = (TextView) itemView.findViewById(R.id.txt_medicine);
        }
    }

    private void configureItemViewHolder(final UserViewHolder holder, final int position) {

        holder.mTxtTitle.setText(mList.get(position).getMedicalHistory());
        holder.mTxtSurgical.setText(mList.get(position).getDate());
        holder.mTxtMedicine.setText(mList.get(position).getMedicineUsed());
        holder.mImg_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.history_dialog_edit);
                dialog.setTitle("View - history exam");

//                //adding text dynamically
                TextView patient_name = (TextView) dialog.findViewById(R.id.Patient_name);
                TextView diabetes = (TextView) dialog.findViewById(R.id.diabetes);
                TextView staff_name = (TextView) dialog.findViewById(R.id.Staff_Name);
                TextView bill_number = (TextView) dialog.findViewById(R.id.Bill_number);
                TextView blood_pressure = (TextView) dialog.findViewById(R.id.blood_pressure);
                TextView paid_amount = (TextView) dialog.findViewById(R.id.Paid_amount);
                TextView smoking = (TextView) dialog.findViewById(R.id.smoking);
                TextView fever_and_chill = (TextView) dialog.findViewById(R.id.fever_and_chill);
                TextView heart_diseases = (TextView) dialog.findViewById(R.id.heart_diseases);
                TextView recent_infection = (TextView) dialog.findViewById(R.id.recent_infection);
                TextView bleeding_disorder = (TextView) dialog.findViewById(R.id.bleeding_disorder);

                TextView any_implants = (TextView) dialog.findViewById(R.id.any_implants);
                TextView pregnancy = (TextView) dialog.findViewById(R.id.pregnancy);
                TextView htn = (TextView) dialog.findViewById(R.id.htn);
                TextView tb = (TextView) dialog.findViewById(R.id.tb);
                TextView cancer = (TextView) dialog.findViewById(R.id.cancer);
                TextView hiv_aids = (TextView) dialog.findViewById(R.id.hiv_aids);
                TextView past_surgery = (TextView) dialog.findViewById(R.id.past_surgery);
                TextView allergies = (TextView) dialog.findViewById(R.id.allergies);
                TextView osteoporotic = (TextView) dialog.findViewById(R.id.osteoporotic);
                TextView depression = (TextView) dialog.findViewById(R.id.depression);
                TextView Hepatitis = (TextView) dialog.findViewById(R.id.hepatitis);
                TextView hereditary_disease = (TextView) dialog.findViewById(R.id.hereditary_disease);
                TextView et_present_illness = (TextView) dialog.findViewById(R.id.et_present_illness);
                TextView et_past_illness = (TextView) dialog.findViewById(R.id.et_past_illness);
                paid_amount.setText(mList.get(position).getMedicineUsed());
                bill_number.setText(mList.get(position).getOtherHistory());
                staff_name.setText(mList.get(position).getMedicalHistory());
                patient_name.setText(mList.get(position).getSurgicalHistory());

                diabetes.setText(mList.get(position).getDiabetes());
                blood_pressure.setText(mList.get(position).getBp());
                smoking.setText(mList.get(position).getSmoking());
                fever_and_chill.setText(mList.get(position).getFever_and_chill());

                heart_diseases.setText(mList.get(position).getHeart_diseases());
                bleeding_disorder.setText(mList.get(position).getBleeding_disorder());
                recent_infection.setText(mList.get(position).getRecent_infection());
                pregnancy.setText(mList.get(position).getPregnancy());

                htn.setText(mList.get(position).getHtn());
                tb.setText(mList.get(position).getTb());
                cancer.setText(mList.get(position).getCancer());
                hiv_aids.setText(mList.get(position).getHiv_aids());

                past_surgery.setText(mList.get(position).getPast_surgery());
                allergies.setText(mList.get(position).getAllergies());
                osteoporotic.setText(mList.get(position).getOsteoporotic());
                depression.setText(mList.get(position).getDepression());

                Hepatitis.setText(mList.get(position).getHepatitis());
                any_implants.setText(mList.get(position).getAny_implants());
                hereditary_disease.setText(mList.get(position).getHereditary_disease());
                et_past_illness.setText(mList.get(position).getPast_illness());
                et_present_illness.setText(mList.get(position).getPresent_illness());

                //adding button click event
                Button dismissButton = (Button) dialog.findViewById(R.id.button);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

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
        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    historyFragment.callHistoryEdit(mList.get(position));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void getpnotes(final String Id) {

//        final String casedesc =editTextcontents.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.EDIT_HISTORY_,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result", response);
                            dialog.dismiss();
                            mCallback.onViewItemClick(mList.get(0), 0, Constants.ClickIDConst.ID_VIEW_CLICK);
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
                params.put("medical_history", staff_name.getText().toString());
                params.put("surgical_history", patient_name.getText().toString());
                params.put("other_history", bill_number.getText().toString());
                params.put("medicine_used", paid_amount.getText().toString());
                params.put("diabetes", diabetes.getText().toString());
                params.put("blood_pressure", blood_pressure.getText().toString());
                params.put("smoking", smoking.getText().toString());
                params.put("fever_and_chill", fever_and_chill.getText().toString());
                params.put("heart_diseases", heart_diseases.getText().toString());
                params.put("bleeding_disorder", bleeding_disorder.getText().toString());
                params.put("recent_infection", recent_infection.getText().toString());
                params.put("pregnancy", pregnancy.getText().toString());
                params.put("htn", htn.getText().toString());
                params.put("tb", tb.getText().toString());
                params.put("cancer", cancer.getText().toString());
                params.put("hiv_aids", hiv_aids.getText().toString());
                params.put("past_surgery", past_surgery.getText().toString());
                params.put("allergies", allergies.getText().toString());
                params.put("osteoporotic", osteoporotic.getText().toString());
                params.put("depression", depression.getText().toString());
                params.put("hepatitis", Hepatitis.getText().toString());
                params.put("any_implants", any_implants.getText().toString());
                params.put("hereditary_disease", hereditary_disease.getText().toString());
                params.put("history_id", Id);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private String capsFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}
