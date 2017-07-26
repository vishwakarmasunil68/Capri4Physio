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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.assessment.PhysicalItem;
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

public class PhysicalExamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private LayoutInflater inflater;
    Dialog dialog;
    private Context context;
    private EditText blood_pressure,temperature,heart_rate,respiratory_rate,built_patient,posture,galt,scare_type,
            description,edtxt_swelling,recent_infection,bleeding_disorder,any_implants,pregnancy,htn,tb,cancer,hiv_aids
            ,past_surgery,allergies,osteoporotic,depression,Hepatitis,hereditary_disease;
    private List<PhysicalItem> mList;
    private ViewItemClickListener<PhysicalItem> mCallback;

    public PhysicalExamAdapter(Context context, List<PhysicalItem> mList, ViewItemClickListener<PhysicalItem> callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mList = mList;
        mCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.row_physica_fragment, parent, false);
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
        private TextView mTxtTitle,mTxtDuration,mTxtPast;

        public UserViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView)itemView.findViewById(R.id.img);
            img_edit = (ImageView)itemView.findViewById(R.id.img_edit);
            mImg_view = (ImageView)itemView.findViewById(R.id.img_view);
            mTxtTitle = (TextView)itemView.findViewById(R.id.txt_title);
            mTxtDuration = (TextView)itemView.findViewById(R.id.txt_duration);
            mTxtPast = (TextView)itemView.findViewById(R.id.txt_past);
        }
    }

    private void configureItemViewHolder(final UserViewHolder holder, final int position) {

//        holder.mTxtTitle.setText(capsFirstLetter(mList.get(position).getBloodPressure()));
        holder.mTxtDuration.setText(mList.get(position).getDate());
//        holder.mTxtPast.setText(mList.get(position).getSwelling());

        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mCallback.onViewItemClick(mList.get(position), position, Constants.ClickIDConst.ID_DELETE_CLICK);
                PhysicalItem pos=mList.get(position);
                Log.e("exam",String.valueOf(pos));
                delepnotes(mList.get(position).getId().toString(),mList.get(position));
            }
        });

        holder.mImg_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(context,android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.phys_history_dialog_edit);
                dialog.setTitle("View - Physical exam");

//                //adding text dynamically
                TextView patient_name = (TextView) dialog.findViewById(R.id.Patient_name);
                TextView diabetes= (TextView) dialog.findViewById(R.id.diabetes);
                TextView staff_name = (TextView) dialog.findViewById(R.id.Staff_Name);
                TextView bill_number = (TextView) dialog.findViewById(R.id.Bill_number);
                TextView blood_pressure = (TextView) dialog.findViewById(R.id.blood_pressure);
                TextView paid_amount = (TextView) dialog.findViewById(R.id.Paid_amount);
                TextView smoking = (TextView) dialog.findViewById(R.id.smoking);
                TextView  fever_and_chill = (TextView) dialog.findViewById(R.id.fever_and_chill);
                TextView heart_diseases = (TextView) dialog.findViewById(R.id.heart_diseases);
                TextView edtxt_swelling = (TextView) dialog.findViewById(R.id.edtxt_swelling);

                paid_amount.setText(mList.get(position).getRespiratoryRate());
                bill_number.setText(mList.get(position).getHeartRate());
                staff_name.setText(mList.get(position).getBloodPressure());
                patient_name.setText(mList.get(position).getTemperature());

                diabetes.setText(mList.get(position).getBuiltPatient());
                blood_pressure.setText(mList.get(position).getPosture());
                smoking.setText(mList.get(position).getGalt());
                fever_and_chill.setText(mList.get(position).getScareType());

                heart_diseases.setText(mList.get(position).getDescription());
                edtxt_swelling.setText(mList.get(position).getSwelling());


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
        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(context,android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.phys_history_dialogedit);
                dialog.setTitle("Edit - history exam");

//                //adding text dynamically
                blood_pressure = (EditText) dialog.findViewById(R.id.Patient_name);
                temperature= (EditText) dialog.findViewById(R.id.diabetes);
                heart_rate = (EditText) dialog.findViewById(R.id.Staff_Name);
                respiratory_rate = (EditText) dialog.findViewById(R.id.Bill_number);
                built_patient = (EditText) dialog.findViewById(R.id.blood_pressure);
                posture = (EditText) dialog.findViewById(R.id.Paid_amount);
                galt = (EditText) dialog.findViewById(R.id.smoking);
                scare_type = (EditText) dialog.findViewById(R.id.fever_and_chill);
                description = (EditText) dialog.findViewById(R.id.heart_diseases);
                edtxt_swelling = (EditText) dialog.findViewById(R.id.edtxt_swelling);


                posture.setText(mList.get(position).getRespiratoryRate());
                respiratory_rate.setText(mList.get(position).getHeartRate());
                heart_rate.setText(mList.get(position).getBloodPressure());
                blood_pressure.setText(mList.get(position).getTemperature());

                temperature.setText(mList.get(position).getBuiltPatient());
                built_patient.setText(mList.get(position).getPosture());
                galt.setText(mList.get(position).getGalt());
                scare_type.setText(mList.get(position).getScareType());

                description.setText(mList.get(position).getDescription());
                edtxt_swelling.setText(mList.get(position).getSwelling());

                /*blood_pressure.setText(mList.get(position).getBloodPressure());
                temperature.setText(mList.get(position).getTemperature());
                heart_rate.setText(mList.get(position).getHeartRate());
                respiratory_rate.setText(mList.get(position).getRespiratoryRate());

                built_patient.setText(mList.get(position).getBuiltPatient());
                posture.setText(mList.get(position).getPosture());
                galt.setText(mList.get(position).getGalt());
                scare_type.setText(mList.get(position).getScareType());
                description.setText(mList.get(position).getDescription());*/

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
    }
    private void getpnotes(final String Id){

//        final String casedesc =editTextcontents.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.EDIT_PHYSICAL_  ,
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

                params.put("blood_pressure", heart_rate.getText().toString());
                params.put("temperature",blood_pressure .getText().toString());
                params.put("heart_rate", respiratory_rate.getText().toString());
                params.put("respiratory_rate", posture.getText().toString());
                params.put("built_patient",temperature.getText().toString());
                params.put("posture", built_patient.getText().toString());
                params.put("galt", galt.getText().toString());
                params.put("scare_type", scare_type.getText().toString());
                params.put("description", description.getText().toString());
                params.put("swelling", edtxt_swelling.getText().toString());
                params.put("physical_id", Id);
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
    private void delepnotes(final String  textView,final PhysicalItem posi){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.DELETE_PHYSICAL_  ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            mList.remove(posi);
                            notifyDataSetChanged();
                            Toast.makeText(context,"Record successfully deleted",Toast.LENGTH_SHORT).show();
                            showSnackMessage("Record successfully removed");

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
                objresponse.put("physical_id", textView);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    private void showSnackMessage(String msg) {
        /*Snackbar snack = Snackbar.make(mSnackBarLayout, msg, Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snack.getView();
        group.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBtnNormal));
        snack.setActionTextColor(Color.WHITE);
        snack.show();*/
    }
}
