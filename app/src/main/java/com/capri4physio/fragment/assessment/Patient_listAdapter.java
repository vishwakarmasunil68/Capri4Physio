package com.capri4physio.fragment.assessment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.capri4physio.model.UserItem;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

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

public class Patient_listAdapter extends RecyclerView.Adapter<Patient_listAdapter.MyViewHolder> {
    Boolean flag=false;
    private List<UserItem> moviesList;
   private CoordinatorLayout mSnackBarLayout;
    private DisplayImageOptions options;
    int p_id;
    Context ctx;
    private ViewItemClickListener<UserItem> mCallback;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        ImageView edit,medication,prescription;
        private ImageView mImgLogo, mImgOption,mImgOption_of_cancel;
        private RelativeLayout layout_row;
        private LinearLayout ll_patient;
        Button status;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_title);
            genre = (TextView) view.findViewById(R.id.txt_email);
            mImgLogo = (ImageView)itemView.findViewById(R.id.img_profile);
            mImgOption = (ImageView)itemView.findViewById(R.id.img_option);
            mImgOption_of_cancel = (ImageView)itemView.findViewById(R.id.img_option_of_cancel);
            layout_row= (RelativeLayout) itemView.findViewById(R.id.layout_row);
            ll_patient= (LinearLayout) itemView.findViewById(R.id.ll_patient);

            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.ic_action_person)
                    .showImageForEmptyUri(R.drawable.ic_action_person)
                    .showImageOnFail(R.drawable.ic_action_person)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .displayer(new RoundedBitmapDisplayer(50))
                    .build();
        }
    }


    public Patient_listAdapter(List<UserItem> moviesList, Context ctx, ViewItemClickListener<UserItem> callback,CoordinatorLayout mSnackBarLayout) {
        this.moviesList = moviesList;
        this.ctx = ctx;
        this.mSnackBarLayout = mSnackBarLayout;
        mCallback = callback;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_row_patient, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final UserItem movie = moviesList.get(position);
//        String date=movie.getNumber();
//http://caprispine.in/app/webroot/upload/
        ImageLoader.getInstance().displayImage(ApiConfig.PROFILE_PIC_BASE_URL+movie.getProfilePic(), holder.mImgLogo, options);
        holder.title.setText(movie.getName());
        holder.genre.setText(movie.getEmail());

        holder.mImgOption_of_cancel.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             final UserItem pos=moviesList.get(position);
             AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
             builder.setMessage("Are you sure, you want to delete");
             builder.setCancelable(false);
             builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {

                     deletepnotes(pos,moviesList.get(position).getId());

                 }
             });
             builder.setNegativeButton(android.R.string.no, null);
             builder.create();
             builder.show();


           }
          });
        holder.ll_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p_id=Integer.parseInt(moviesList.get(position).getId());
                mCallback.onViewItemClick(moviesList.get(position), position, p_id);
            }
        });
        holder.mImgOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p_id=Integer.parseInt(moviesList.get(position).getId());
                mCallback.onViewItemClick(moviesList.get(position), position, p_id);
            }
        });

        Log.d("sunil","user_type:-"+ AppPreferences.getInstance(ctx).getUserType());
        if( AppPreferences.getInstance(ctx).getUserType().equals("3")||AppPreferences.getInstance(ctx).getUserType().equals("1")){
            holder.mImgOption_of_cancel.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        Log.e("sizepre",String.valueOf(moviesList.size()));
        return moviesList.size();
    }
    private void deletepnotes(final UserItem textView,final String ID){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.DELETE_PATIENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            moviesList.remove(textView);
                            notifyDataSetChanged();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
//                        mCallback.onViewItemClick(moviesList.get(0), 0, Constants.ClickIDConst.ID_ATTACHMENT_CLICK);
                            Toast.makeText(ctx,"Record successfully deleted",Toast.LENGTH_SHORT).show();
//                        showSnackMessage("Record successfully removed");
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
                objresponse = new HashMap<String, String>();
                objresponse.put("patient_id", ID);
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

}
