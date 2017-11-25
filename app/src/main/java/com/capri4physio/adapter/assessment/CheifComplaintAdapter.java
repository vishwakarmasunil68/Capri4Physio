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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.assessment.CheifComplaint;
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

public class CheifComplaintAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    RadioGroup mRadioGroup;
    RadioButton yes, no;
    String hadProblemBefore = "NO";
    Dialog dialog;
    EditText editTextcontents, editTextprobbefore;
    private String posn, contents, id, note_iid;
    private List<CheifComplaint> mList;
    private ViewItemClickListener<CheifComplaint> mCallback;

    public CheifComplaintAdapter(Context context, List<CheifComplaint> mList, ViewItemClickListener<CheifComplaint> callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mList = mList;
        mCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.row_cheif_complaint, parent, false);
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
        private ImageView mImg, mImgattact;
        private TextView mTxtTitle, txt_title1, mTxtDuration, mTxtPast;

        public UserViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.img);
            mImgattact = (ImageView) itemView.findViewById(R.id.img_edit);
            txt_title1 = (TextView) itemView.findViewById(R.id.txt_title1);
            mTxtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            mTxtDuration = (TextView) itemView.findViewById(R.id.txt_duration);
            mTxtPast = (TextView) itemView.findViewById(R.id.txt_past);
        }
    }

    private void configureItemViewHolder(final UserViewHolder holder, final int position) {

        holder.mTxtTitle.setText(capsFirstLetter(mList.get(position).getComplaints()));
        holder.txt_title1.setText(capsFirstLetter(mList.get(position).getDate()));
        holder.mTxtDuration.setText(mList.get(position).getProblemDuration());
        holder.mTxtPast.setText(mList.get(position).getProblemBefore());

        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onViewItemClick(mList.get(position), position, Constants.ClickIDConst.ID_DELETE_CLICK);
            }
        });


        holder.mImgattact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posn = mList.get(position).getComplaints();
                contents = mList.get(position).getProblemDuration().toString();
                id = mList.get(position).getProblemBefore().toString();
                note_iid = mList.get(position).getId().toString();
                String prob = mList.get(position).getProblemBefore().toString();


                Log.e("notes", posn + note_iid);
                dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.dialog_edit_chief);
                dialog.setTitle("Edit - " +
                        " Chief compalints");

                //adding text dynamically
//            radioGroup= (RadioGroup) dialog.findViewById(R.id.radio_group);
//            radioGroup.set(contents);
                editTextcontents = (EditText) dialog.findViewById(R.id.edtxt_how_long);
                editTextcontents.setText(contents);
                editTextprobbefore = (EditText) dialog.findViewById(R.id.edtxt_problem);
                editTextprobbefore.setText(posn);
                mRadioGroup = (RadioGroup) dialog.findViewById(R.id.radio_group);
                yes = (RadioButton) dialog.findViewById(R.id.radio_yes);
                no = (RadioButton) dialog.findViewById(R.id.radio_no);
                if (prob.equalsIgnoreCase("YES")) {
                    yes.setChecked(true);
                } else {
                    no.setChecked(true);
                }

                mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                        RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);
                        hadProblemBefore = rb.getText().toString();
                        Log.e("hadProblemBefore", hadProblemBefore);
                    }
                });
                Button dismissButton = (Button) dialog.findViewById(R.id.btn_save);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getpnotes(holder.mTxtTitle, holder.mTxtDuration, hadProblemBefore);

                    }
                });
                dialog.show();
            }
        });
    }

    private void getpnotes(final TextView textView1, final TextView textView2, final String textView3) {


        final String casedesc = editTextcontents.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.EDIT_CHIF_NOTES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result", response);
                            textView1.setText(editTextprobbefore.getText().toString());
                            textView2.setText(editTextcontents.getText().toString());
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
                Map<String, String> objresponse = new HashMap<String, String>();
                objresponse.put("complaints", editTextprobbefore.getText().toString());
                objresponse.put("problem_duration", editTextcontents.getText().toString());
                objresponse.put("problem_before", textView3);
                objresponse.put("chif_id", note_iid);
                return objresponse;
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
