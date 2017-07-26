package com.capri4physio.fragment.assessment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capri4physio.Invoice.InfoApps1;
import com.capri4physio.R;
import com.capri4physio.activity.FeedBackShowActivity;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.pojo.PatientFeedBack;
import com.capri4physio.pojo.PatientFeedBackResult;
import com.capri4physio.util.AppPreferences;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Emobi-Android-002 on 7/18/2016.
 */
public class DoFeedbackActivity extends AppCompatActivity implements View.OnClickListener{
    private final static String TAG="DoFeedbackActivity";

    String GetFeedbackURL = "http://www.caprispine.in/feedbacks/patientfeedback";
    String GetFeedbackURLBYBRANCH = "http://www.caprispine.in/feedbacks/patientfeedbackbranchwise";
    LinearLayout linear;
    Button btn_add;
    RecyclerView feedback_recycler_view;
    ArrayList<String> arrayList;
    String item;
    InfoApps1 detailApps;
    Spinner spinnerbranchloca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedbackofpatient);
        arrayList = new ArrayList<String>();
        arrayList.add("Select Branch Name");
        spinnerbranchloca = (Spinner) findViewById(R.id.spinnerbranchloca);
        linear= (LinearLayout) findViewById(R.id.linear);
        btn_add= (Button) findViewById(R.id.btn_add);
        feedback_recycler_view= (RecyclerView) findViewById(R.id.feedback_recycler_view);
        btn_add.setOnClickListener(this);
        if(AppPreferences.getInstance(getApplicationContext()).getUserType().equals("0")){
            btn_add.setVisibility(View.VISIBLE);
            Feedback();
        }
        if(AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")){
            spinnerbranchloca.setVisibility(View.VISIBLE);
            btn_add.setVisibility(View.GONE);
            new CatagoryUrlAsynTask1().execute();
        }
        else{
            btn_add.setVisibility(View.VISIBLE);
        }
        Log.d(TAG,"patient_id:-"+AppPreferences.getInstance(getApplicationContext()).getUserID());


        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    try {
                        String address = spinnerbranchloca.getSelectedItem().toString();
                        if (address.equalsIgnoreCase("Select Branch Name")){

//                            contactList.setVisibility(View.INVISIBLE);

                        }
//                        patientaray.clear();
                    }
                    catch (Exception e){
                        Log.e("eror",e.toString());
                    }
                }
                else {
                    String address = spinnerbranchloca.getSelectedItem().toString();

                    String ad[]=address.split("\\(");
                    item = ad[1];
                    item =item.replace(")", "");
//                    edtxt_branchcode.setText(newaddress);
                    Feedback1();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                Intent intent=new Intent(DoFeedbackActivity.this, FeedBackShowActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void Feedback1(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GetFeedbackURLBYBRANCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG,response);
//                            pDialog.dismiss();
                            ParseJSON4(response);
                            Log.d(TAG, "" + response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.d(TAG, "" + error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("feedback_branch_code",item);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void ParseJSON2(String response){
        Gson gson=new Gson();
        PatientFeedBack patientFeedBack= new Gson().fromJson(response, PatientFeedBack.class);
        if(patientFeedBack!=null){
            Log.d(TAG,patientFeedBack.toString());
            List<PatientFeedBackResult> list=patientFeedBack.getResult();
            Log.d("size","all movies:-"+list.size());
            FeedBackAdapter adapter=new FeedBackAdapter(getApplicationContext(),list);

            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            feedback_recycler_view.setLayoutManager(horizontalLayoutManagaer);

            feedback_recycler_view.setAdapter(adapter);
        }

    }

    private void Feedback(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GetFeedbackURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG,response);
//                            pDialog.dismiss();
                                ParseJSON(response);
                            Log.d(TAG, "" + response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.d(TAG, "" + error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("feedback_p_id",AppPreferences.getInstance(getApplicationContext()).getUserID());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void ParseJSON(String response){
        Gson gson=new Gson();
        PatientFeedBack patientFeedBack= new Gson().fromJson(response, PatientFeedBack.class);
        if(patientFeedBack!=null){
            Log.d(TAG,patientFeedBack.toString());
            List<PatientFeedBackResult> list=patientFeedBack.getResult();
            Log.d("size","all movies:-"+list.size());
            FeedBackAdapter adapter=new FeedBackAdapter(getApplicationContext(),list);

            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            feedback_recycler_view.setLayoutManager(horizontalLayoutManagaer);

            feedback_recycler_view.setAdapter(adapter);
        }

    }
    public void ParseJSON4(String response){
        Gson gson=new Gson();
        PatientFeedBack patientFeedBack= new Gson().fromJson(response, PatientFeedBack.class);
        if(patientFeedBack!=null){
            Log.d(TAG,patientFeedBack.toString());
            List<PatientFeedBackResult> list=patientFeedBack.getResult();
            Log.d("size","all movies:-"+list.size());
            FeedBackAdapter adapter=new FeedBackAdapter(getApplicationContext(),list);

            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            feedback_recycler_view.setLayoutManager(horizontalLayoutManagaer);

            feedback_recycler_view.setAdapter(adapter);
        }

    }

    public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.MyViewHolder> {

        private List<PatientFeedBackResult> horizontalList;
        private Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tv_feedback;
            public TextView tv_feedback_time;
            public LinearLayout feedback_ll;
            public MyViewHolder(View view) {
                super(view);
                tv_feedback = (TextView) view.findViewById(R.id.tv_feedback);
                tv_feedback_time = (TextView) view.findViewById(R.id.tv_feedback_time);
                feedback_ll= (LinearLayout) view.findViewById(R.id.feedback_ll);
            }
        }


        public FeedBackAdapter(Context context, List<PatientFeedBackResult> horizontalList) {
            this.horizontalList = horizontalList;
            this.context=context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.inflate_feedback, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.tv_feedback.setText(horizontalList.get(position).getFirst_name());
            holder.tv_feedback_time.setText(horizontalList.get(position).getFeedback_dateadded().split(" ")[0]);
            holder.feedback_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(context,horizontalList.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }


        public void showDialog(Context context,PatientFeedBackResult result){
            final Dialog dialog1 = new Dialog(DoFeedbackActivity.this,android.R.style.Theme_DeviceDefault_Light_Dialog);
            dialog1.setContentView(R.layout.dialog_feedback);
            dialog1.setTitle("FeedBack");
            dialog1.show();
            dialog1.setCancelable(true);


            TextView tv_feedback_id= (TextView) dialog1.findViewById(R.id.tv_feedback_id);
            TextView tv_threapist_time= (TextView) dialog1.findViewById(R.id.tv_threapist_time);
            TextView tv_threapist_knowledge= (TextView) dialog1.findViewById(R.id.tv_threapist_knowledge);
            TextView tv_threapist_handling_skill= (TextView) dialog1.findViewById(R.id.tv_threapist_handling_skill);
            TextView tv_threapist_behaviour= (TextView) dialog1.findViewById(R.id.tv_threapist_behaviour);
            TextView tv_threapist_overall= (TextView) dialog1.findViewById(R.id.tv_threapist_overall);
            TextView tv_center_time= (TextView) dialog1.findViewById(R.id.tv_center_time);
            TextView tv_center_facilities= (TextView) dialog1.findViewById(R.id.tv_center_facilities);
            TextView tv_centercleannes= (TextView) dialog1.findViewById(R.id.tv_centercleannes);
            TextView tv_exp= (TextView) dialog1.findViewById(R.id.tv_exp);
            TextView tv_feedback_total= (TextView) dialog1.findViewById(R.id.tv_feedback_total);
            TextView tv_feedback_date_added= (TextView) dialog1.findViewById(R.id.tv_feedback_date_added);
            TextView tv_feedback_p_id= (TextView) dialog1.findViewById(R.id.tv_feedback_p_id);

            tv_feedback_id.setText(result.getFirst_name());
            tv_threapist_time.setText(result.getCentre_time());
            tv_threapist_knowledge.setText(result.getTherapist_knowledge());
            tv_threapist_handling_skill.setText(result.getTherapist_handlingskill());
            tv_threapist_behaviour.setText(result.getTherapist_behavior());
            tv_threapist_overall.setText(result.getTherapist_overal());
            tv_center_time.setText(result.getCentre_time());
            tv_center_facilities.setText(result.getCentre_facilities());
            tv_centercleannes.setText(result.getCentre_cleanness());
            tv_exp.setText(result.getExp());
            tv_feedback_total.setText(result.getFeedback_total()+ "/32");
            try {
                tv_feedback_date_added.setText(result.getFeedback_dateadded().split(" ")[0]);
            }catch (Exception e){
                e.printStackTrace();
            }
            tv_feedback_p_id.setText(result.getFeedback_p_id());

            Button ok= (Button) dialog1.findViewById(R.id.btn_ok);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();
                }
            });
        }
    }

    private class CatagoryUrlAsynTask1 extends AsyncTask<String, String, String> {
        String id, catagoryName;


        @Override
        protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
            String content = HttpULRConnect.getData(ApiConfig.GetURL);
            Log.e("DoInBackGround ---->", String.valueOf(content));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                Log.e("DoInBackGroundtr", String.valueOf(s));
                ///     pDialog.dismiss();
//                Log.e("Post Method Call  here ....", "Method ...");
                JSONArray jsonArray = new JSONArray(s);
                for (int i =0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String branch_name = jsonObject2.getString("branch_name");
                    String bracch_code = jsonObject2.getString("branch_code");
                    //branch_code
//                    arrayList.add(bracch_code);

                    detailApps = new InfoApps1();
                    detailApps.setName(branch_name);
                    detailApps.setId(bracch_code);
                    arrayList.add(detailApps.getName() + "  "+ "(" + detailApps.getId()+ ")");

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.dropsimpledown, arrayList);
                    spinnerbranchloca.setAdapter(spinnerArrayAdapter);

                }

            }catch(Exception e){
                Log.e("error",e.toString());

            }
        }
    }
}