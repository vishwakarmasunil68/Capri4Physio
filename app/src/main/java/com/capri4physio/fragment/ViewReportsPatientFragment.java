package com.capri4physio.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capri4physio.R;
import com.capri4physio.fragment.assessment.HttpULRConnect;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.patientappo.PatientCaseReport;

import org.json.JSONObject;

/**
 * Created by Emobi-Android-002 on 7/13/2016.
 */
public class ViewReportsPatientFragment extends BaseFragment{
    CardView cardView;
    public static  String status;
    String GetURL = ApiConfig.BASE_URL+"users/servervalueget";
    private final String TAG=getClass().getSimpleName();
    public static ViewReportsPatientFragment newInstance() {
        ViewReportsPatientFragment fragment = new ViewReportsPatientFragment();
        return fragment;
    }
    public ViewReportsPatientFragment() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.viewallreports, container, false);
        Log.d(TAG,"in patient view profile fragment");
        cardView= (CardView) rootView.findViewById(R.id.cardview_report);
        new CatagoryViewAsynTask().execute();
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PatientCaseReport.class));
            }
        });
        return rootView;
    }

    private class CatagoryViewAsynTask extends AsyncTask<String, String, String> {
        String id, catagoryName;


        @Override
        protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
            String content = HttpULRConnect.getData(GetURL);
            Log.e("DoInBackGround ---->", String.valueOf(content));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                Log.e("DoInBackGroundtr", String.valueOf(s));
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONObject jsonobject = new JSONObject(s);
                status = jsonobject.getString("serv_value");
                Log.e("status",status);
                }


        catch(Exception e){
                Log.e("error",e.toString());

            }
        }
    }
}
