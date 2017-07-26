package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.R;
import com.capri4physio.activity.SplashActivity;
import com.capri4physio.fragment.BaseFragment;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.AppPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import multipleimageselect.activities.AlbumSelectActivity;
import multipleimageselect.helpers.Constants;
import multipleimageselect.models.Image;

/**
 * Created by emobi on 17-03-2017.
 */

public class StudentUploadFragmentView extends BaseFragment {
    InfoApps infoApps;
    byte[] byteArray;
    ArrayList<String> arrayListbitmap = new ArrayList<>();
    ProgressDialog pDialog;
    private String mImgBase64;
    RecyclerView recycler_view;
    public ArrayList<InfoApps> arrayList=new ArrayList<>();
    StudentsPaymentsAdapter mAdapter;
    public static StudentUploadFragmentView newInstance() {
        StudentUploadFragmentView fragment = new StudentUploadFragmentView();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openGallery();
//        initProgressDialog("Please wait...");
//        getpnotes();
//        arrayList=new ArrayList<>();
//        Toast.makeText(getActivity(), "mapfragment", Toast.LENGTH_SHORT).show();
//        new ViewAllClinic().execute();
    }

    @Nullable
    private void openGallery() {
        Intent intent = new Intent(getActivity(), AlbumSelectActivity.class);
//set limit on number of images that can be selected, default is 10
        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 3);
        startActivityForResult(intent, Constants.REQUEST_CODE);
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        File photoFile = null;
//        try {
//            photoFile = createImageFile();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//        if (photoFile != null) {
//            SplashActivity.mImageCaptureUri = Uri.fromFile(photoFile);
//            try {
//                startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
//            } catch (ActivityNotFoundException e) {
//                e.printStackTrace();
//            }
//        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.uploadpic,container,false);
        recycler_view = (RecyclerView) root.findViewById(R.id.listview);
//        recycler_view.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recycler_view.setLayoutManager(layoutManager);
        return root;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != SplashActivity.RESULT_OK)
            return;

        Intent intent = null;

        switch (requestCode) {
            case Constants.REQUEST_CODE:
                ArrayList<String> arrayList = new ArrayList<>();
                ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0, l = images.size(); i < l; i++) {
                    stringBuffer.append(images.get(i).path + "\n");
                }
                for (Image image : images) {

                    arrayList.add(image.path.toString());
                    Bitmap bitmap = BitmapFactory.decodeFile(image.path);
//                    mImgProfile.setVisibility(View.GONE);
                    Log.e("images1", image.path);
                    Log.e("images1", arrayList.toString());
                    Log.e("images1", bitmap.toString());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
                    byteArray = stream.toByteArray();
                    mImgBase64 = Base64.encodeToString(byteArray, 0);

                    AppLog.e("Capri4Physio", "Base64 IMG - " + arrayListbitmap);
                    arrayListbitmap.add(mImgBase64.replace("[","").replace("]",""));
//                Log.e("images",arrayList+"");
                    recycler_view.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(),stringBuffer.toString(),Toast.LENGTH_LONG).show();
                    HorizontalAdapter adapter = new HorizontalAdapter(getActivity(), arrayList);
                    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
                    recycler_view.setHasFixedSize(true);
                    recycler_view.setLayoutManager(layoutManager);
//                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recycler_view.setAdapter(adapter);
//                    StringToBitMap(image.path);
//                    Uri filepath =image.path;
                }
        }
    }
    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<String> horizontalList;
        Boolean allvalue;
        private Context context;
        Activity activity;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public ImageView tv_time;
            public ImageView tv_time_cross;
            public LinearLayout ll_time;

            public MyViewHolder(View view) {
                super(view);
                tv_time = (ImageView) view.findViewById(R.id.tv_time);
                tv_time_cross = (ImageView) view.findViewById(R.id.tv_time_cross);
                ll_time = (LinearLayout) view.findViewById(R.id.ll_time);

                allvalue = false;


            }
        }


        public HorizontalAdapter(Context context, List<String> horizontalList) {
            this.horizontalList = horizontalList;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.inflate_monday_time, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            Bitmap bitmap=BitmapFactory.decodeFile(horizontalList.get(position));
            holder.tv_time.setImageBitmap(bitmap);
            holder.tv_time_cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    horizontalList.remove(position);
//                    listview.noti
//                    listview.notify();
                }
            });


        }
        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }
        private void getpnotes(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.VIEW_PAYMENT_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
//                        try {
//                            Log.e("result", response);
//
                        try {

                                JSONObject jsonObject = new JSONObject(response);
                            String Success =jsonObject.getString("Success");
                            if (Success.equalsIgnoreCase("true")){
                                JSONArray jsonArray=jsonObject.getJSONArray("Result");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                    String date =jsonObject1.getString("date");
                                    String std_amount =jsonObject1.getString("std_amount");
                                    infoApps = new InfoApps();
                                    infoApps.setData(std_amount);
                                    infoApps.setSend_date(date);//date
                                    arrayList.add(infoApps);
                                    mAdapter = new StudentsPaymentsAdapter(arrayList,getActivity());
                                    recycler_view.setAdapter(mAdapter);
                                }

                            }
                        }
                        catch (JSONException e){
                            Log.e("catch",e.toString());
                        }
//                        }
//                        catch (Exception e) {
//                            Log.e("catchExce",e.toString());
//                        }
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
                objresponse.put("user_id", AppPreferences.getInstance(getActivity()).getUserID());
                return objresponse;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    private void initProgressDialog(String loading) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(loading);
        pDialog.setCancelable(false);
        pDialog.show();
    }
}
