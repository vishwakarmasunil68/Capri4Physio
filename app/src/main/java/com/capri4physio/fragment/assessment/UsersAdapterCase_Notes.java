package com.capri4physio.fragment.assessment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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
import com.capri4physio.model.assessment.MotorItem;
import com.capri4physio.net.ApiConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *Create to bind jobs in list
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 */

public class UsersAdapterCase_Notes extends RecyclerView.Adapter<UsersAdapterCase_Notes.MyViewHolder> {
    Boolean flag=false;
    private List<InfoApps> moviesList;
    CoordinatorLayout mSnackBarLayout;
    Dialog dialog;
    EditText editTextcontents;
    String posn,contents,id,note_iid;
    private List<MotorItem> mList;
    private ViewItemClickListener<MotorItem> mCallback;
    Context ctx;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, genre,year;
        ImageView edit,medication,prescription;
        private ImageView mImg;
        private ImageView mImgAttachemnt;
        Button status;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_patient_name);
            mImg = (ImageView)itemView.findViewById(R.id.img);
            mImgAttachemnt = (ImageView)itemView.findViewById(R.id.img_attachment);
            genre = (TextView) view.findViewById(R.id.txt_patient_id);

        }
    }


    public UsersAdapterCase_Notes(List<InfoApps> moviesList, Context ctx, CoordinatorLayout mSnackBarLayout) {
        this.moviesList = moviesList;
        this.ctx = ctx;
        this.mSnackBarLayout = mSnackBarLayout;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_progress_note_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        InfoApps movie = moviesList.get(position);
//        String date=movie.getNumber();

        holder.title.setText(movie.getNumber());
        Log.d("sunil", "" + movie.getData());
        holder.genre.setText(movie.getName());


        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoApps positiin = (CaseNotesFragment.contactDetails1.get(position));
                 posn = CaseNotesFragment.contactDetails1.get(position).toString();
                note_iid = CaseNotesFragment.contactDetails1.get(position).getSend_date().toString();
                deleteAlert(positiin);
//                Toast.makeText(ctx, "sgfdkjs"+positiin, Toast.LENGTH_LONG).show();
            }
        });
        holder.mImgAttachemnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posn = CaseNotesFragment.contactDetails1.get(position).toString();
                contents = CaseNotesFragment.contactDetails1.get(position).getName().toString();
                note_iid = CaseNotesFragment.contactDetails1.get(position).getSend_date().toString();
                dialog = new Dialog(ctx, android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.dialog_edit_casenotes);
                dialog.setTitle("Edit - case note");

                //adding text dynamically
                editTextcontents= (EditText) dialog.findViewById(R.id.edtxt_old_note);
                editTextcontents.setText(contents);


                //adding button click event
                Button dismissButton = (Button) dialog.findViewById(R.id.btn_save);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getpnotes(holder.genre);

                    }
                });
                dialog.show();
            }
        });
    }

    private void deleteAlert(final InfoApps textView) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage("Are you sure, you want to delete");
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                delepnotes(textView);

            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.create();
        builder.show();
    }


    private void getpnotes(final TextView textView){
        final String casedesc =editTextcontents.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.EDIT_CASE_NOTES  ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            textView.setText(editTextcontents.getText().toString());
                            dialog.dismiss();

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
                objresponse.put("moter_exam_casedesc", casedesc);
                objresponse.put("moter_exam_case_id", note_iid);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);
    }


    private void delepnotes(final InfoApps textView){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.DELETE_CASE_NOTES  ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            moviesList.remove(textView);
                            notifyDataSetChanged();
//                            Toast.makeText(ctx,"Record successfully deleted",Toast.LENGTH_SHORT).show();
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
                objresponse.put("moter_exam_case_id", note_iid);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);
    }

    private void showSnackMessage(String msg) {
        Snackbar snack = Snackbar.make(mSnackBarLayout, msg, Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snack.getView();
        group.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorBtnNormal));
        snack.setActionTextColor(Color.WHITE);
        snack.show();
    }

    @Override
    public int getItemCount() {
        Log.e("sizepre",String.valueOf(moviesList.size()));
        return moviesList.size();
    }

}
