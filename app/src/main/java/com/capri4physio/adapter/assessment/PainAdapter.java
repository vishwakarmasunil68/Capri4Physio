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
import com.capri4physio.model.assessment.PainItem;
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

public class PainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private LayoutInflater inflater;
    Dialog dialog;
    private Context context;
    EditText pain_side,severity_pain,pressure_pain,pain_nature,pain_onset,pain_duration,location,fever_and_chill,
            diurnal_variations,trigger_point,aggravating_factors,relieving_factors,pregnancy,htn,tb,cancer,hiv_aids
            ,past_surgery,allergies,osteoporotic,depression,Hepatitis,hereditary_disease;
    private List<PainItem> mList;
    private ViewItemClickListener<PainItem> mCallback;

    public PainAdapter(Context context, List<PainItem> mList, ViewItemClickListener<PainItem> callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mList = mList;
        mCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.row_pain, parent, false);
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
        private ImageView mImg,mImg_view,img_edit;
        private TextView mTxtPainSite,mTxtNature,mTxtPainOnset, mTxtServerity, mTxtPainLocation;

        public UserViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView)itemView.findViewById(R.id.img);
            img_edit = (ImageView)itemView.findViewById(R.id.img_edit);
            mImg_view = (ImageView)itemView.findViewById(R.id.img_view);
            mTxtPainSite = (TextView)itemView.findViewById(R.id.date_history);
        }
    }

    private void configureItemViewHolder(final UserViewHolder holder, final int position) {

        holder.mTxtPainSite.setText(capsFirstLetter(mList.get(position).getDate()));

        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onViewItemClick(mList.get(position), position, Constants.ClickIDConst.ID_DELETE_CLICK);
            }
        });

        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(context,android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.pain_dialogedit);
                dialog.setTitle("Edit - history exam");

//                //adding text dynamically
                 pain_side = (EditText) dialog.findViewById(R.id.pain_side);
                Button  button= (Button) dialog.findViewById(R.id.button);
                 severity_pain = (EditText) dialog.findViewById(R.id.severity_pain);
                pressure_pain   = (EditText) dialog.findViewById(R.id.pressure_pain);
                 pain_nature = (EditText) dialog.findViewById(R.id.pain_nature);
                 pain_onset = (EditText) dialog.findViewById(R.id.pain_onset);
                 pain_duration = (EditText) dialog.findViewById(R.id.pain_duration);
                  location = (EditText) dialog.findViewById(R.id.location);
                 diurnal_variations = (EditText) dialog.findViewById(R.id.diurnal_variations);
                 trigger_point = (EditText) dialog.findViewById(R.id.trigger_point);
                 aggravating_factors = (EditText) dialog.findViewById(R.id.aggravating_factors);
                 relieving_factors = (EditText) dialog.findViewById(R.id.relieving_factors);

                pain_side.setText(mList.get(position).getPainSide());
                severity_pain.setText(mList.get(position).getSeverityPain());
                pressure_pain.setText(mList.get(position).getPressurePain());
                pain_nature.setText(mList.get(position).getPainNature());
                pain_onset.setText(mList.get(position).getPainOnset());
                pain_duration.setText(mList.get(position).getPainDuration());
                location.setText(mList.get(position).getLocation());
                diurnal_variations.setText(mList.get(position).getDiurnalVariations());
                trigger_point.setText(mList.get(position).getTriggerPoint());
                aggravating_factors.setText(mList.get(position).getAggravatingFactors());
                relieving_factors.setText(mList.get(position).getRelievingFactors());

                //adding button click event
                Button dismissButton = (Button) dialog.findViewById(R.id.button);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getpnotes(mList.get(position).getId());


                    }
                });
                dialog.show();
            }
        });


        holder.mImg_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(context,android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.pain_dialog_edit);
                dialog.setTitle("View - pain exam");

//                //adding text dynamically
                TextView pain_side = (TextView) dialog.findViewById(R.id.pain_side);
                TextView button= (Button) dialog.findViewById(R.id.button);
                TextView severity_pain = (TextView) dialog.findViewById(R.id.severity_pain);
                TextView pressure_pain = (TextView) dialog.findViewById(R.id.pressure_pain);
                TextView pain_nature = (TextView) dialog.findViewById(R.id.pain_nature);
                TextView pain_onset = (TextView) dialog.findViewById(R.id.pain_onset);
                TextView pain_duration = (TextView) dialog.findViewById(R.id.pain_duration);
                TextView  location = (TextView) dialog.findViewById(R.id.location);
                TextView diurnal_variations = (TextView) dialog.findViewById(R.id.diurnal_variations);
                TextView trigger_point = (TextView) dialog.findViewById(R.id.trigger_point);
                TextView aggravating_factors = (TextView) dialog.findViewById(R.id.aggravating_factors);
                TextView relieving_factors = (TextView) dialog.findViewById(R.id.relieving_factors);

                pain_side.setText(mList.get(position).getPainSide());
                severity_pain.setText(mList.get(position).getSeverityPain());
                pressure_pain.setText(mList.get(position).getPressurePain());
                pain_nature.setText(mList.get(position).getPainNature());
                pain_onset.setText(mList.get(position).getPainOnset());
                pain_duration.setText(mList.get(position).getPainDuration());
                location.setText(mList.get(position).getLocation());
                diurnal_variations.setText(mList.get(position).getDiurnalVariations());
                trigger_point.setText(mList.get(position).getTriggerPoint());
                aggravating_factors.setText(mList.get(position).getAggravatingFactors());
                relieving_factors.setText(mList.get(position).getRelievingFactors());
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
    }
    private String capsFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
    private void getpnotes(final String Id){

//        final String casedesc =editTextcontents.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.EDIT_PAIN_  ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            dialog.dismiss();
                            mCallback.onViewItemClick(mList.get(0), -2, Constants.ClickIDConst.ID_DELETE_CLICK);
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

                params.put(ApiConfig.PAIN_SIDE, pain_side.getText().toString());
                params.put(ApiConfig.PAIN_DURATION, pain_duration.getText().toString());
                params.put(ApiConfig.TRIGGER_POINT, trigger_point.getText().toString());
                params.put(ApiConfig.PAIN_LOCATION, location.getText().toString());
                params.put(ApiConfig.SEVERITY_PAIN, severity_pain.getText().toString());
                params.put(ApiConfig.PRESURE_PAIN, pressure_pain.getText().toString());
                params.put(ApiConfig.PAIN_NATURE, pain_nature.getText().toString());
                params.put(ApiConfig.PAIN_ONSET, pain_onset.getText().toString());
                params.put("threshold_site", "");
                params.put(ApiConfig.DIURNAL_VARIATION, diurnal_variations.getText().toString());
                params.put(ApiConfig.AGGRAVATING_FACTORS, aggravating_factors.getText().toString());
                params.put(ApiConfig.RELIEVING_FACTORS, relieving_factors.getText().toString());
                params.put("pain_id", Id);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
