package com.capri4physio.adapter.assessment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
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
import com.bumptech.glide.Glide;
import com.capri4physio.R;
import com.capri4physio.listener.ViewItemClickListener;
import com.capri4physio.model.assessment.InvestigationItem;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.Constants;
import com.capri4physio.util.TagUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
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

public class InvestigationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    Dialog dialog;
    private Context context;
    private List<InvestigationItem> mList;
    String bitmap, bitmap1;
    private ViewItemClickListener<InvestigationItem> mCallback;

    public InvestigationAdapter(Context context, List<InvestigationItem> mList, ViewItemClickListener<InvestigationItem> callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mList = mList;
        mCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.row_investigation, parent, false);
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
        private ImageView mImg, img_edit;
        private ImageView mImgAttachemnt;
        private TextView mTxtTitle, mTxtDesc, txt_date;

        public UserViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.img);
            img_edit = (ImageView) itemView.findViewById(R.id.img_edit);
            mImgAttachemnt = (ImageView) itemView.findViewById(R.id.img_attachment);
            mTxtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            mTxtDesc = (TextView) itemView.findViewById(R.id.txt_desc);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
        }
    }

    private void configureItemViewHolder(final UserViewHolder holder, final int position) {

        holder.mTxtTitle.setText(capsFirstLetter(mList.get(position).getReportType()));
        holder.mTxtDesc.setText(mList.get(position).getDate());
        holder.txt_date.setText(mList.get(position).getDescription());

        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onViewItemClick(mList.get(position), position, Constants.ClickIDConst.ID_DELETE_CLICK);
            }
        });

        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Light_Dialog);
                dialog.setContentView(R.layout.investigation_dialog_edit);
                dialog.setTitle("Edit - Investigation ");
                dialog.setCancelable(true);
//                //adding text dynamically
                final EditText blood_pressure = (EditText) dialog.findViewById(R.id.patient_name);
                final EditText heart_rate = (EditText) dialog.findViewById(R.id.Staff_Name);
                final ImageView respiratory_rate = (ImageView) dialog.findViewById(R.id.Bill_number);
                respiratory_rate.setVisibility(View.INVISIBLE);
//                EditText built_patient = (EditText) dialog.findViewById(R.id.blood_pressure);

                final String Therapy = (mList.get(position).getReportType().toString());
                blood_pressure.setText(Therapy);
                final String Goal = (mList.get(position).getDescription().toString());
                heart_rate.setText(Goal);
                final String Dose = (mList.get(position).getAttachment().toString());
                Button dismissButton = (Button) dialog.findViewById(R.id.button);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("ge", mList.get(position).getAttachment().toString());
                        getpnotes(mList.get(position).getId(), blood_pressure.getText().toString(), heart_rate.getText().toString(), Dose);


                    }
                });
                dialog.show();
            }
        });
        holder.mImgAttachemnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

//                    bitmap1 = mList.get(position).getAttachment();
//                    int endIndex = bitmap1.lastIndexOf(".");
////                    if (endIndex != -1) {
////                        String newstr = bitmap1.substring(00, endIndex); // not forgot to put check if(endIndex != -1)
////
////                        String bmp[] = newstr.split("00");
////                        String imgorpdf = bmp[1];
////                        Log.e("bit1", imgorpdf);
//                    if (bitmap1.contains("pdf") || bitmap1.contains("doc")) {
//                        new DownloadFile().execute();
//                    } else {
                        dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Light_Dialog);

                        //setting custom layout to dialog
                        dialog.setContentView(R.layout.inve_dialog_edit);
                        dialog.setTitle("View - Investigation photo");
//

                    bitmap = ApiConfig.IMAGE_BASE_URL + "app/webroot/upload/" + mList.get(position).getAttachment();
                    Log.d(TagUtils.getTag(), "image url:-" + bitmap);

                    ImageView patient_name = (ImageView) dialog.findViewById(R.id.img);

                    Glide.with(context).load(bitmap).into(patient_name);
////                //adding text dynamically
//                        ImageView patient_name = (ImageView) dialog.findViewById(R.id.img);
//                        Picasso.with(context.getApplicationContext()).load(bitmap).resize(500, 500).into(patient_name);
//                    }
//                    Log.e("bitmap", bitmap);
                    Button dismissButton = (Button) dialog.findViewById(R.id.button);
                    dismissButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }
                    });
                    dialog.show();
                } catch (Exception e) {

                }
            }
        });
        //adding button click event


    }

    private String capsFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    class DownloadFile extends AsyncTask<String, Integer, Long> {
        ProgressDialog mProgressDialog = new ProgressDialog(context);// Change Mainactivity.this with your activity name.
        String strFolderName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                mProgressDialog.setMessage("Downloading");
                //            mProgressDialog.setIndeterminate(false);
                //            mProgressDialog.setMax(100);
                mProgressDialog.setCancelable(true);
                //            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Long doInBackground(String... aurl) {
            int count;
            try {
                URL url = new URL(bitmap);
                URLConnection conexion = url.openConnection();
                conexion.connect();
                String targetFileName = "Name" + ".pdf";//Change name and subname
                int lenghtOfFile = conexion.getContentLength();
                String PATH = Environment.getExternalStorageDirectory() + File.separator + "Physio";
                File folder = new File(PATH);
                if (!folder.exists()) {
                    folder.mkdir();//If there is no folder it will be created.
                }

                InputStream input = conexion.getInputStream();
                //                Toast.makeText(context, "File Downloaded", Toast.LENGTH_SHORT).show();

                //                BufferedInputStream bis = new BufferedInputStream(input);
                OutputStream output = new FileOutputStream(PATH + File.separator + bitmap1);
                int read = 0;
                byte[] buffer = new byte[32768];
                while ((read = input.read(buffer)) > 0) {
                    output.write(buffer, 0, read);
                }
                output.close();
                input.close();
                output.flush();
                output.close();
                input.close();
                try {
                    mProgressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                showPdf();
            } catch (Exception e) {

                Log.e("progress", e.toString());
            }
            return null;
        }
        protected void onPostExecute(String result) {
            Toast.makeText(context, "File Downloaded", Toast.LENGTH_SHORT).show();
            showPdf();

        }
    }

    public void showPdf() {
        Log.d("file", "showfile");

        String PATH1 = Environment.getExternalStorageDirectory() + File.separator + "Physio";
        String PATH = PATH1 + File.separator + bitmap1;
        File folder = new File(PATH);
        if (!folder.exists()) {
            folder.mkdir();//If there is no folder it will be created.
        }
        File file = new File(PATH);
        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        context.startActivity(intent);

    }


    private void getpnotes(final String Id, final String therapy, final String trea_amount, final String comment) {

//        final String casedesc =editTextcontents.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.EDIT_INVESTIGATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result", response);
                            dialog.dismiss();
                            mCallback.onViewItemClick(mList.get(0), 0, Constants.ClickIDConst.ID_ATTACHMENT_CLICK);

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

//        params.put("attachment", comment);
                params.put("report_type", therapy);
                params.put("description", trea_amount);
                params.put("investigation_id", Id);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
