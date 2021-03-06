package com.capri4physio.Invoice;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v4.print.PrintHelper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.TagUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {
    Boolean flag=false;
    private List<InfoApps1> moviesList;
    String invo;

    public static String reason,patient_name,patient_Email;
Context con;
    Activity ctx;
    public  static String invo_id,invo_patient_name,invo_bill_amount,invo_due_amount,invo_paid_amount,
            invo_status,invo_bill_number,invo_pro_name,invo_pro_quantity,invo_staff,invo_date,invo_pay_mode;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,year, genre;
        ImageView edit,mail,cancel,prescription,View;
        Button status;
        CardView cv;



        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_patient_id);
            genre = (TextView) view.findViewById(R.id.txt_patient_id);
            View= (ImageView) view.findViewById(R.id.view);
            edit= (ImageView) view.findViewById(R.id.edit);
            cancel= (ImageView) view.findViewById(R.id.cancel);
            mail= (ImageView) view.findViewById(R.id.dialog_mail);
//            cv = (CardView) view.findViewById(R.id.cv);
        }
    }


    public UsersAdapter(List<InfoApps1> moviesList, Activity ctx) {
        this.moviesList = moviesList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_patient_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final InfoApps1 movie = moviesList.get(position);
        holder.title.setText(movie.getName());
//        holder.title.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "AlexBrush-Regular.ttf"));
        holder.title.setTypeface(Typeface.create("Montez-Regular.ttf", Typeface.BOLD));
//        holder.genre.setText(movie.getBill_amount());


        holder.View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ctx, android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.cusotm_dialog_edit);
                dialog.setTitle("Invoice - Detail");
                final ScrollView parent_scroll_view= (ScrollView) dialog.findViewById(R.id.scrollview);
                //adding text dynamically
                TextView patient_name = (TextView) dialog.findViewById(R.id.Patient_name);
                patient_name.setText(movie.getName());
                TextView staff_name = (TextView) dialog.findViewById(R.id.Staff_Name);
                staff_name.setText(movie.getStaff_name());
                final RelativeLayout relativeLayout = (RelativeLayout)dialog.findViewById(R.id.print_layout);
                TextView bill_number = (TextView) dialog.findViewById(R.id.Bill_number);
                bill_number.setText(movie.getNumber());
                TextView bill_amount = (TextView) dialog.findViewById(R.id.Bill_amount);
                bill_amount.setText(movie.getBill_amount());
                TextView paid_amount = (TextView) dialog.findViewById(R.id.Paid_amount);
                paid_amount.setText(movie.getPaid_amount());
                TextView due_amount = (TextView) dialog.findViewById(R.id.Due_amount);
                due_amount.setText(movie.getDue_amount());
                TextView pro_name = (TextView) dialog.findViewById(R.id.Pro_name);
                pro_name.setText(movie.getPro_name());
                TextView pro_quantity = (TextView) dialog.findViewById(R.id.Pro_quantity);
                pro_quantity.setText(movie.getPro_quantity());
                TextView invodate = (TextView) dialog.findViewById(R.id.Invoice_date);
                invodate.setText(movie.getInvo_date());
                TextView invo_staaus = (TextView) dialog.findViewById(R.id.Invoice_status);
                invo_staaus.setText(movie.getStatus());

                //adding button click event
                Button dismissButton = (Button) dialog.findViewById(R.id.button);
                final Button button_print = (Button) dialog.findViewById(R.id.button_print);
                button_print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        button_print.setVisibility(View.INVISIBLE);

                        showPrintView(getBitmapByView(parent_scroll_view));
                        dialog.dismiss();
                    }
                });
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        getBitmapByView(parent_scroll_view);
                        dialog.dismiss();
                    }
                });
                /*button_print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        print(relativeLayout);
                    }
                });*/
                dialog.show();
            }
        });


        holder.mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i =new Intent(ctx,ViewPrint.class);
//                i.putExtra("p_name",movie.getName());
//                i.putExtra("staff_name",movie.getStaff_name());
//                i.putExtra("number",movie.getNumber());
//                i.putExtra("bill_amount",movie.getBill_amount());
//                i.putExtra("paid_amount",movie.getPaid_amount());
//                i.putExtra("due_amount",movie.getDue_amount());
//                i.putExtra("pro_name",movie.getPro_name());
//                i.putExtra("pro_quantity",movie.getPro_quantity());
//                i.putExtra("invodate",movie.getInvo_date());
//                i.putExtra("status",movie.getStatus());
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                ctx.startActivity(i);
                final Dialog dialog = new Dialog(ctx, android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.cusotm_dialog_mail);
                dialog.setTitle("Invoice - Email");

                //adding text dynamically
                TextView txt = (TextView) dialog.findViewById(R.id.email);
                txt.setText(movie.getEmail_id());
                TextView name = (TextView) dialog.findViewById(R.id.patient_name);
                name.setText(movie.getName());
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                image.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.ic_dialog_email));

                //adding button click event
                Button dismissButton = (Button) dialog.findViewById(R.id.button);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String id =  ViewInvoice.contactDetails1.get(position).getDesignation();
                        delepnotes1(dialog,id);

                    }
                });
                dialog.show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ctx, android.R.style.Theme_DeviceDefault_Light_Dialog);

                //setting custom layout to dialog
                dialog.setContentView(R.layout.cusotm_dialog_view);
                dialog.setTitle("Invoice - Detail");

                //adding text dynamically
                invo=movie.getId();
                final String id =  movie.getStatus();
                final TextView patient_name = (TextView) dialog.findViewById(R.id.Patient_name);
                patient_name.setText(movie.getName());
               invo_patient_name=patient_name.getText().toString();

                final TextView staff_name = (TextView) dialog.findViewById(R.id.Staff_Name);
                staff_name.setText(movie.getStaff_name());
                invo_staff=staff_name.getText().toString();
                /*ImageView image = (ImageView)dialog.findViewById(R.id.image);
                image.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.ic_dialog_email));*/
                TextView bill_number = (TextView) dialog.findViewById(R.id.Bill_number);
                bill_number.setText(movie.getNumber());
                invo_bill_number=bill_number.getText().toString();
                final TextView bill_amount = (TextView) dialog.findViewById(R.id.Bill_amount);
                bill_amount.setText(movie.getBill_amount());
                invo_bill_amount=bill_amount.getText().toString();
                final TextView paid_amount = (TextView) dialog.findViewById(R.id.Paid_amount);
                paid_amount.setText(movie.getPaid_amount());
                invo_paid_amount=paid_amount.getText().toString();
                final TextView due_amount = (TextView) dialog.findViewById(R.id.Due_amount);
                due_amount.setText(movie.getDue_amount());
                invo_due_amount=due_amount.getText().toString();
                final TextView pro_name = (TextView) dialog.findViewById(R.id.Pro_name);
                pro_name.setText(movie.getPro_name());
                invo_pro_name=pro_name.getText().toString();
                final TextView pro_quantity = (TextView) dialog.findViewById(R.id.Pro_quantity);
                pro_quantity.setText(movie.getPro_quantity());
                invo_pro_quantity=pro_quantity.getText().toString();
                final TextView invodate = (TextView) dialog.findViewById(R.id.Invoice_date);
                invodate.setText(movie.getInvo_date());
                invo_date=invodate.getText().toString();
                final Spinner invo_staaus = (Spinner) dialog.findViewById(R.id.Invoice_status);
//                invo_staaus.setText(movie.getStatus());
                invo_status=invo_staaus.getSelectedItem().toString();

                //adding button click event
                Button dismissButton = (Button) dialog.findViewById(R.id.button);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        dialog.dismiss();
                        editAppointment(patient_name.getText().toString(),staff_name.getText().toString(),
                                bill_amount.getText().toString(),due_amount.getText().toString(),
                                paid_amount.getText().toString(),pro_name.getText().toString(),
                                pro_quantity.getText().toString(),invodate.getText().toString(),
                                invo_staaus.getSelectedItem().toString(),
                                id);
                    }
                });
                dialog.show();
            }
        });
        if(AppPreferences.getInstance(ctx).getUserType().equals("3")
                ||AppPreferences.getInstance(ctx).getUserType().equals("1")){
            holder.cancel.setVisibility(View.GONE);
        }
        else{
            holder.cancel.setVisibility(View.VISIBLE);
        }
        if(AppPreferences.getInstance(ctx).getUserType().equals("1")){
            holder.edit.setVisibility(View.GONE);
        }
        else{
            holder.edit.setVisibility(View.VISIBLE);
        }
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoApps1 positiin = (ViewInvoice.contactDetails1.get(position));
                String note_iid =ViewInvoice.contactDetails1.get(position).getId();
                Log.e("id",note_iid);
                delepnotes(positiin,note_iid);
            }
        });
    }
    private final String TAG=getClass().getName();
    public void showPrintView(final Bitmap bitmap){
            new AsyncTask<Void, Void, Void>() {
                File f;
                ProgressDialog pd;
                ArrayList<Bitmap> list;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    pd = new ProgressDialog(ctx);
                    pd.setMessage("Please Wait...");
                    pd.setCancelable(false);
                    pd.show();

//                SaveBitmapPDF(bitmap);
                    int height = bitmap.getHeight();
                    Log.d(TAG, "height:-" + height);

                    int chunks = height / 40;
                    Log.d(TAG, "chunks:-" + chunks);
                    list = splitImage1(bitmap, chunks);
                }

                @Override
                protected Void doInBackground(Void... params) {

//                SaveBitmapPDF1(list);
                    try {
                        f = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio" + File.separator + "view_invoice.pdf");
                        Document document = new Document();
                        PdfWriter.getInstance(document, new FileOutputStream(f));
                        document.open();
                        for (Bitmap bmp : list) {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            Image image = Image.getInstance(stream.toByteArray());
                            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                                    - document.rightMargin() - 0) / image.getWidth()) * 100;
                            image.scalePercent(scaler);
//                            Display display = ctx.getWindowManager().getDefaultDisplay();
//                            Point size = new Point();
//                            display.getSize(size);
//
//                            int width = size.x;
//                            int height = size.y;
////                            image.getScaledWidth();
//                            image.setAlignment(Image.DEFAULT);
                            document.add(image);
                        }
                        document.close();
                    } catch (Exception e) {
                        pd.dismiss();
                        Log.d(TAG, e.toString());
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if (pd != null) {
                        pd.dismiss();
                    }
                    if (f != null) {
                        if (f.exists()) {
//                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/example.pdf");
//                            Intent intent = new Intent(Intent.ACTION_VIEW);
//                            intent.setDataAndType(Uri.fromFile(f), "application/pdf");
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                            ctx.startActivity(intent);


                            Log.d(TagUtils.getTag(),"file path:-"+f.getPath());
                            MimeTypeMap mime = MimeTypeMap.getSingleton();
                            String ext = f.getName().substring(f.getName().lastIndexOf(".") + 1);
                            String type = mime.getMimeTypeFromExtension(ext);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                Uri contentUri = FileProvider.getUriForFile(ctx.getApplicationContext(), "com.capri4physio.fileProvider", f);
                                intent.setDataAndType(contentUri, type);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                grantAllUriPermissions(intent,contentUri);
                            } else {
                                intent.setDataAndType(Uri.fromFile(f), type);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                grantAllUriPermissions(intent,Uri.fromFile(f));
                            }
                        }
                    }
                }
            }.execute();
    }

    private void grantAllUriPermissions(Intent intent, Uri uri) {
        List<ResolveInfo> resInfoList = ctx.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            ctx.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    private ArrayList<Bitmap> splitImage1(Bitmap bitmap, int chunkNumbers) {

        //For the number of rows and columns of the grid to be displayed
        int rows, cols = 1;

        //For height and width of the small image chunks
        int chunkHeight, chunkWidth;

        //To store all the small image chunks in bitmap format in this list
        ArrayList<Bitmap> chunkedImages = new ArrayList<Bitmap>(chunkNumbers);

        //Getting the scaled bitmap of the source image
//        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
//        Bitmap bitmap = drawable.getBitmap();
//        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        rows = (int) Math.sqrt(chunkNumbers);
        chunkHeight = bitmap.getHeight() / rows;
        chunkWidth = bitmap.getWidth() / cols;

        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;
        for (int x = 0; x < rows; x++) {
            int xCoord = 0;
            for (int y = 0; y < cols; y++) {
                chunkedImages.add(Bitmap.createBitmap(bitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }
        return chunkedImages;
    }

    public  Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        //get the actual height of scrollview
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundResource(R.color.white);
        }
        // create bitmap with target size
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        FileOutputStream out = null;
        try {
            File file=new File(Environment.getExternalStorageDirectory()+ File.separator+"Physio");
            if(!file.exists()){
                file.mkdirs();
            }
            out = new FileOutputStream(file.toString()+File.separator+System.currentTimeMillis()+".png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (null != out) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
                Toast.makeText(ctx,"File saved to gallery",Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            // TODO: handle exception
            Toast.makeText(ctx,"File not saved to gallery",Toast.LENGTH_SHORT).show();
        }
        return bitmap;
    }
    private void delepnotes(final InfoApps1 textView,final String note_iid){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.DELETE_INVOICE  ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
                            moviesList.remove(textView);
                            notifyDataSetChanged();
                            Toast.makeText(ctx,"Record successfully deleted",Toast.LENGTH_SHORT).show();
//                            showSnackMessage("Record successfully removed");

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
                objresponse.put("invo_id", note_iid);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);
    }

    private void delepnotes1(final Dialog dialog,final String note_iid){

Log.e("noteid",note_iid);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.SEND_INVOICE  ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("result",response);
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
                objresponse.put("admin_permisstion_status", "1");
                objresponse.put("user_id", note_iid);
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);
    }

    private void print(RelativeLayout relativeLayout) {
        // Get the print manager.
        PrintHelper printHelper = new PrintHelper(ctx);
        // Set the desired scale mode.
        printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        // Get the bitmap for the ImageView's drawable.
        Bitmap bitmap = overlay(relativeLayout);
        // Print the bitmap.
        printHelper.printBitmap("Print Bitmap", bitmap);
    }

    public Bitmap overlay(RelativeLayout frame_layout){
        Bitmap bitmap1 = Bitmap.createBitmap(frame_layout.getWidth(), frame_layout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        frame_layout.draw(canvas);
        frame_layout.setDrawingCacheEnabled(true);
        frame_layout.getDrawingCache();
        return frame_layout.getDrawingCache();
        /*image.setImageBitmap(bitmap1);
		*//*bitmap.setDrawingCacheEnabled(true);
		bitmap.buildDrawingCache();*//*
        return bitmap1.copy(Bitmap.Config.ARGB_8888,true);*/
    }
    @Override
    public int getItemCount() {
        return moviesList.size();
    }
    private void editAppointment(final String pname , final String staffnaame , final String bill  ,
                                 final String  due , final String paidamount  , final String  pro_name , final String pro_quan,
                                 final String  date , final String status ,final String id ){
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_EDITABLE_INVO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(ctx, "Information has been updated successfully.", Toast.LENGTH_LONG).show();
            ctx.finish();
                            Log.e("result",response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        startActivity(new Intent(StmtActivity.this, PinDoMatch.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("invo_id",invo);
                params.put("invo_patient_name", pname);
//                params.put("invo_Patient_id", id);
                params.put("invo_bill_amount", bill);
                params.put("invo_due_amount", due);
                params.put("invo_paid_amount", paidamount );
                params.put("invo_payment_mode", "cash");
                params.put("invo_date", date);
                params.put("invo_status", status);
                params.put("invo_product_name", pro_name);
                params.put("invo_staff_name", staffnaame);
                params.put("invo_pro_quanty", pro_quan);
//                params.put("moter_examneuro_right_Median", "xjkl");
//                params.put("moter_examneuro_left_Ulnar", "ss");
                /*params.put(KEY_PATIENTID,patientname);
                params.put(KEY_ID,uniqueid);
                params.put(KEY_DOCTORID,GetDcotorid);
                params.put(KEY_DATEBOOKING, booking_date);
                params.put(KEY_BOOKINGSTARTTIME, booking_starttime);
                params.put(KEY_BOOKINGENDTIME, booking_Endtime);
                params.put(KEY_REASON, reason);*/

//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);
    }

            private void cancelAppointment(final String pos){
        /*final String date = edit_date.getText().toString().trim();
        final String time = edit_time.getText().toString().trim();
        final String reason = edit_reason.getText().toString().trim();*/
        /*Log.e("date",date);
        Log.e("time",time);
        Log.e("reason",reason);*/
                StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.CANCEL_INVO,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Toast.makeText(ctx, "record deleted successfully.", Toast.LENGTH_LONG).show();
                                    moviesList.remove(pos);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//                        startActivity(new Intent(StmtActivity.this, PinDoMatch.class));
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                                Log.w("Postdat", "" + error);
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("invo_id",invo);
                /*params.put(KEY_PATIENTID,patientname);
                params.put(KEY_ID,uniqueid);
                params.put(KEY_DOCTORID,GetDcotorid);
                params.put(KEY_DATEBOOKING, booking_date);
                params.put(KEY_BOOKINGSTARTTIME, booking_starttime);
                params.put(KEY_BOOKINGENDTIME, booking_Endtime);
                params.put(KEY_REASON, reason);*/

//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(ctx);
                requestQueue.add(stringRequest);
            }
}
