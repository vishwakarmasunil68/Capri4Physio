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
import com.capri4physio.fragment.assessment.PhysiotheraputicFragment;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.assessment.PhysiotheraputicItem;
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

public class PhysiotheraputicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private LayoutInflater inflater;
    Dialog dialog;
    EditText editTextcontents;
    private Context context;
    String posn,contents,id,note_iid;
    private List<PhysiotheraputicItem> mList;
    private ViewItemClickListener<PhysiotheraputicItem> mCallback;

    public PhysiotheraputicAdapter(Context context, List<PhysiotheraputicItem> mList, ViewItemClickListener<PhysiotheraputicItem> callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mList = mList;
        mCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.row_theraputic_dignosis, parent, false);
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
        private ImageView mImg,mImgattact;
        private TextView mTxtTitle,txt_title1;

        public UserViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView)itemView.findViewById(R.id.img);
            txt_title1 = (TextView)itemView.findViewById(R.id.txt_title1);
            mImgattact = (ImageView)itemView.findViewById(R.id.img_attachment);
            mTxtTitle = (TextView)itemView.findViewById(R.id.txt_title);
        }
    }

    private void configureItemViewHolder(final UserViewHolder holder, final int position) {

        holder.mTxtTitle.setText(capsFirstLetter(mList.get(position).getDescription()));
        holder.txt_title1.setText(capsFirstLetter(mList.get(position).getDate()));




        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mCallback.onViewItemClick(mList.get(position), position, Constants.ClickIDConst.ID_DELETE_CLICK);
            }
        });




        holder.mImgattact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posn = PhysiotheraputicFragment.mList.get(position).toString();
                contents = PhysiotheraputicFragment.mList.get(position).getDescription().toString();
                note_iid = PhysiotheraputicFragment.mList.get(position).getId().toString();

                Log.e("notes",posn + note_iid);
                dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.dialog_edit_casenotes);
                dialog.setTitle("Edit - " +
                        " note");

                //adding text dynamically
                editTextcontents= (EditText) dialog.findViewById(R.id.edtxt_old_note);
                editTextcontents.setText(contents);


                //adding button click event
                Button dismissButton = (Button) dialog.findViewById(R.id.btn_save);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getpnotes(holder.mTxtTitle);

                    }
                });
                dialog.show();
            }
        });
    }
    private void getpnotes(final TextView textView){



        final String casedesc =editTextcontents.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.EDIT_PYSIOTHERAPUTIC_  ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            textView.setText(editTextcontents.getText().toString());
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
                Map<String,String> objresponse = new HashMap<String, String>();
                objresponse.put("description", casedesc);
                objresponse.put("id", note_iid);
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
