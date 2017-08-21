package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.capri4physio.R;
import com.capri4physio.activity.MoterExamViewActivity;
import com.capri4physio.fragment.MyClinicPatientFragment;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.motorexamreport.InfoAppsElbow;
import com.capri4physio.motorexamreport.LocationAdapterElbow;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.pojo.MotorExamPOJO;
import com.capri4physio.task.UrlConnectionTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.Utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import viewreport.Services.WebServiceBase;
import viewreport.Services.WebServiceCallBack;

/**
 * Created by emobi5 on 7/2/2016.
 */
public class AddMotorExamFragment extends Activity implements HttpUrlListener, WebServiceCallBack {
    private static final String KEY_PATIENT_ID = "patient_id";
    String jsonfile;
    private String patientId = MyClinicPatientFragment.patient_id;
    String json;
    private Button mSave;
    ProgressDialog progressDialog;
    LocationAdapterElbow locationAdapterElbow;
    public static ArrayList<InfoAppsElbow> arrylist;
    ListView list;
    Spinner listView1;
    InfoAppsElbow DetailApp;
//    String[] items = {"Select Joint", "Head,Neck and trunk", "Combined Movement Assesment of spine", "Cervical spine", "Thoraccic spine", "Lumbar spine", "Hip", "Knee", "Ankle", "Toes"
//            , "Shoulder", "Elbow", "Fore Arm", "Wrist", "Fingers", "Sacroilic Joint"};
    String[] items = {"Select Joint",  "Combined Movement Assesment of spine", "Cervical spine", "Thoraccic spine", "Lumbar spine", "Hip", "Knee", "Ankle", "Toes"
            , "Shoulder", "Elbow", "Fore Arm", "Wrist", "Fingers", "Sacroilic Joint"};
    ArrayAdapter<String> adapter;
    Intent intent;
    ImageView iv_exam;
    RecyclerView rv_images;
    Button btn_skip;
    private final static String BASE_IMAGE_URL = ApiConfig.IMAGE_BASE_URL + "app/webroot/upload/";
    int motor_case=-1;
    @Override
    public void onPostSuccess(Object response, int id) {
        switch (id) {

            case ApiConfig.ID1:
                BaseModel baseModel = (BaseModel) response;
                AppLog.i("Capri4Physio", "Patient Response : " + baseModel.getStatus());
                getFragmentManager().popBackStack();
                break;
        }
    }

    @Override
    public void onPostError(String errMsg, int id) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public AddMotorExamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_motor);

        arrylist = new ArrayList<InfoAppsElbow>();
        mSave = (Button) findViewById(R.id.btn);
        btn_skip = (Button) findViewById(R.id.btn_skip);
        iv_exam = (ImageView) findViewById(R.id.iv_exam);
        list = (ListView) findViewById(R.id.list);
        listView1 = (Spinner) findViewById(R.id.listView1);
        rv_images = (RecyclerView) findViewById(R.id.rv_images);

//        mSave = (Button) findViewById(R.id.btn);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, items);
        listView1.setAdapter(spinnerArrayAdapter);
//        loadJSONFromAsset();

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intent != null) {
//                    progressDialog = new ProgressDialog(AddMotorExamFragment.this);
//                    progressDialog.setMessage("Please Wait...");
//                    progressDialog.setCancelable(false);
//                    progressDialog.show();
                    startActivity(intent);
//                    finish();
                }

            }
        });

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result","5");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
//                HandlerConstant.POP_BACK_HANDLER.sendMessage(HandlerConstant.POP_BACK_HANDLER.obtainMessage(0,"5"));
            }
        });


        listView1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    progressDialog.dismiss();
                } catch (Exception e) {
                    Log.d("nullpointer", e.toString());
                }
                switch (i+1) {
//                    case 1:
//                        intent = new Intent(getApplicationContext(), FragementActi.class);
//                        intent.putExtra("patient_id", patientId);
//                        rv_images.setVisibility(View.INVISIBLE);
//                        showImage(1);
//                        motor_case=1;
//                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(), MotorActivity1.class);
                        intent.putExtra("patient_id", patientId);
                        rv_images.setVisibility(View.INVISIBLE);
                        showImage(2);
                        motor_case=2;
                        break;
                    case 3:
                        intent = new Intent(getApplicationContext(), MotorActivity2.class);
                        intent.putExtra("patient_id", patientId);
                        rv_images.setVisibility(View.INVISIBLE);
                        showImage(3);
                        motor_case=3;
                        break;
                    case 4:
                        intent = new Intent(getApplicationContext(), MotorActivity3.class);
                        intent.putExtra("patient_id", patientId);
                        rv_images.setVisibility(View.INVISIBLE);
                        showImage(4);
                        motor_case=4;
                        break;
                    case 5:
                        intent = new Intent(getApplicationContext(), MotorActivity4.class);
                        intent.putExtra("patient_id", patientId);
                        rv_images.setVisibility(View.INVISIBLE);
                        showImage(5);
                        motor_case=5;
                        break;
                    case 6:
                        intent = new Intent(getApplicationContext(), FragementActi1.class);
                        intent.putExtra("patient_id", patientId);
                        rv_images.setVisibility(View.INVISIBLE);
                        showImage(6);
                        motor_case=6;
                        break;
                    case 7:
                        intent = new Intent(getApplicationContext(), FragementActi2.class);
                        intent.putExtra("patient_id", patientId);
                        rv_images.setVisibility(View.INVISIBLE);
                        showImage(7);
                        motor_case=7;
                        break;
                    case 8:
                        intent = new Intent(getApplicationContext(), FragementActi3.class);
                        intent.putExtra("patient_id", patientId);
                        rv_images.setVisibility(View.INVISIBLE);
                        showImage(8);
                        motor_case=8;
                        break;
                    case 9:
                        intent = new Intent(getApplicationContext(), FragementActi4.class);
                        intent.putExtra("patient_id", patientId);
                        rv_images.setVisibility(View.INVISIBLE);
                        showImage(9);
                        motor_case=9;
                        break;
                    case 10:
                        intent = new Intent(getApplicationContext(), FragementActi7.class);
                        intent.putExtra("patient_id", patientId);
                        rv_images.setVisibility(View.INVISIBLE);
                        showImage(10);
                        motor_case=10;
                        break;
                    case 11:
                        intent = new Intent(getApplicationContext(), FragementActi8.class);
                        intent.putExtra("patient_id", patientId);
                        rv_images.setVisibility(View.INVISIBLE);
                        showImage(11);
                        motor_case=11;
                        break;
                    case 12:
                        intent = new Intent(getApplicationContext(), FragementActi9.class);
                        intent.putExtra("patient_id", patientId);
                        rv_images.setVisibility(View.INVISIBLE);
                        showImage(12);
                        motor_case=12;
                        break;
                    case 13:
                        intent = new Intent(getApplicationContext(), FragementActi10.class);
                        intent.putExtra("patient_id", patientId);
                        rv_images.setVisibility(View.INVISIBLE);
                        showImage(13);
                        motor_case=13;
                        break;
                    case 14:
                        intent = new Intent(getApplicationContext(), FragementActi11.class);
                        intent.putExtra("patient_id", patientId);
                        rv_images.setVisibility(View.INVISIBLE);
                        showImage(14);
                        motor_case=14;
                        break;

                    case 15:
                        intent = new Intent(getApplicationContext(), SacrolicMotorActivity.class);
                        intent.putExtra("patient_id", patientId);
                        rv_images.setVisibility(View.INVISIBLE);
                        showImage(15);
                        motor_case=15;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case "case1":
                parseImageView(response, "moterhltrp_image", 1, "moter_trp_id");
                break;
            case "case2":
                parseImageView(response, "moterexams_images", 2, "moter_exam_id");
                break;
            case "case3":
                parseImageView(response, "moterexamcervical_images", 3, "moter_rvical_id");
                break;
            case "case4":
                parseImageView(response, "moterexamthoraccic_images", 4, "moter_raccic_id");
                break;
            case "case5":
                parseImageView(response, "moterexamlumbar_images", 5, "moter_lumbar_id");
                break;
            case "case6":
                parseImageView(response, "moterexamship_image", 6, "moter_examhip_id");
                break;
            case "case7":
                parseImageView(response, "moterexamsknee_images", 7, "moter_exam_knee_id");
                break;
            case "case8":
                parseImageView(response, "moterexamsankle_image", 8, "moter_examankle_id");
                break;
            case "case9":
                parseImageView(response, "moterexamstoes_images", 9, "moter_exam_toes_id");
                break;
            case "case10":
                parseImageView(response, "moterexamsshoulder_image", 10, "moter_examshoulder_id");
                break;
            case "case11":
                parseImageView(response, "moterexamselbow_image", 11, "moter_examelbow_id");
                break;
            case "case12":
                parseImageView(response, "moterexamsforearm_image", 12, "moter_examfore_id");
                break;
            case "case13":
                parseImageView(response, "moterexamswrist_image", 13, "moter_examwrist_id");
                break;
            case "case14":
                parseImageView(response, "moterexamsfingers_images", 14, "moter_examfinger_id");
                break;

            case "case15":
                parseImageView(response, "moterexamsacroilic_image", 15, "moterexamsacroilic_id");
                break;
            case "case16":
//                parseImageView(response,"moterexamsacroilic_image",15,"moterexamsacroilic_id");
                break;
            case "delete1":
                parseDeleteData(response, 1);
                break;
            case "delete2":
                parseDeleteData(response, 2);
                break;
            case "delete3":
                parseDeleteData(response, 3);
                break;
            case "delete4":
                parseDeleteData(response, 4);
                break;
            case "delete5":
                parseDeleteData(response, 5);
                break;
            case "delete6":
                parseDeleteData(response, 6);
                break;
            case "delete7":
                parseDeleteData(response, 7);
                break;
            case "delete8":
                parseDeleteData(response, 8);
                break;
            case "delete9":
                parseDeleteData(response, 9);
                break;
            case "delete10":
                parseDeleteData(response, 10);
                break;
            case "delete11":
                parseDeleteData(response, 11);
                break;
            case "delete12":
                parseDeleteData(response, 12);
                break;
            case "delete13":
                parseDeleteData(response, 13);
                break;
            case "delete14":
                parseDeleteData(response, 14);
                break;

            case "delete15":
                Log.d(TAG, "delete parse response:-" + response);
                parseDeleteData(response, 15);
                break;

        }
    }

    private final String TAG = getClass().getSimpleName();

    public void showImage(int index) {
        rv_images.setVisibility(View.VISIBLE);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//        nameValuePairs.add(new BasicNameValuePair("patient_id", ViewCaseReport.patient_id));
        nameValuePairs.add(new BasicNameValuePair("patient_id", patientId));
        Log.d(TagUtils.getTag(),"namevalue:-"+nameValuePairs.toString());
        switch (index) {
            case 1:
                new WebServiceBase(nameValuePairs, this, "case1").execute(ApiConfig.BASE_URL + "users/moterexamhltrpview");
                break;
            case 2:
//                rv_images.setVisibility(View.INVISIBLE);
                new WebServiceBase(nameValuePairs, this, "case2").execute(ApiConfig.BASE_URL + "users/moterexamsview");
                break;
            case 3:
                new WebServiceBase(nameValuePairs, this, "case3").execute(ApiConfig.BASE_URL + "users/moterexamcervicalview");
                break;
            case 4:
                new WebServiceBase(nameValuePairs, this, "case4").execute(ApiConfig.BASE_URL + "users/moterexamthoraccicview");
                break;
            case 5:
                new WebServiceBase(nameValuePairs, this, "case5").execute(ApiConfig.BASE_URL + "users/moterexamlumbarview");
                break;
            case 6:
                new WebServiceBase(nameValuePairs, this, "case6").execute(ApiConfig.BASE_URL + "users/moterexamshipview");
                break;
            case 7:
                new WebServiceBase(nameValuePairs, this, "case7").execute(ApiConfig.BASE_URL + "users/moterexamkneeview");
                break;
            case 8:
                new WebServiceBase(nameValuePairs, this, "case8").execute(ApiConfig.BASE_URL + "users/moterexamsankleview");
                break;
            case 9:
                new WebServiceBase(nameValuePairs, this, "case9").execute(ApiConfig.BASE_URL + "users/moterexamstoesview");
                break;
            case 10:
                new WebServiceBase(nameValuePairs, this, "case10").execute(ApiConfig.BASE_URL + "users/moterexamsshoulderview");
                break;
            case 11:
                new WebServiceBase(nameValuePairs, this, "case11").execute(ApiConfig.BASE_URL + "users/moterexamselbowview");
                break;
            case 12:
                new WebServiceBase(nameValuePairs, this, "case12").execute(ApiConfig.BASE_URL + "users/moterexamsforearmview");
                break;
            case 13:
                new WebServiceBase(nameValuePairs, this, "case13").execute(ApiConfig.BASE_URL + "users/moterexamswristview");
                break;
            case 14:
                new WebServiceBase(nameValuePairs, this, "case14").execute(ApiConfig.BASE_URL + "users/moterexamsfingersview");
                break;

            case 15:
                new WebServiceBase(nameValuePairs, this, "case15").execute(ApiConfig.BASE_URL + "users/moterexamsacroilicview");
                break;
        }
    }

    public void parseImageView(String response, String parameter, int position, String parse_id) {
        Log.d(TagUtils.getTag(),"response:-"+response);
        List<MotorExamPOJO> list_urls = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(response);

            if (array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.optJSONObject(i);
                    String moterhltrp_image = object.optString(parameter);
                    String moter_exam_date = object.optString("moter_exam_date");
                    String parse_id1 = object.optString(parse_id);
                    MotorExamPOJO pojo = new MotorExamPOJO();
                    pojo.setDate(moter_exam_date);
                    pojo.setId(parse_id1);
                    if (moterhltrp_image.length() > 0) {
//                        Picasso.with(this).load(BASE_IMAGE_URL + moterhltrp_image).into(iv_exam);
                        pojo.setUrl(BASE_IMAGE_URL + moterhltrp_image);
                    } else {
                        pojo.setDate("");
                    }
                    if (pojo.getDate().equals("")) {

                    } else {
                        list_urls.add(pojo);
                    }
                }
                Log.d(TAG, list_urls.toString());

            }
        } catch (Exception e) {

        }
        HorizontalAdapter adapter = new HorizontalAdapter(this, list_urls, position);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_images.setHasFixedSize(true);
        rv_images.setLayoutManager(layoutManager);

        rv_images.setAdapter(adapter);
    }

    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<MotorExamPOJO> horizontalList;
        private Context context;
        public int api_position;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView tv_date;
            public ImageView iv_view;
            public ImageView iv_delete;
            public CardView cv_task;


            public MyViewHolder(View view) {
                super(view);
                tv_date = (TextView) view.findViewById(R.id.tv_date);
                iv_view = (ImageView) view.findViewById(R.id.iv_view);
                iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
                cv_task = (CardView) view.findViewById(R.id.cv_task);
            }
        }


        public HorizontalAdapter(Context context, List<MotorExamPOJO> horizontalList, int api_position) {
            this.horizontalList = horizontalList;
            this.context = context;
            this.api_position = api_position;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.inflate_imageadapter, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
//            Picasso.with(context).load(horizontalList.get(position)).into(holder.iv_motor);
            holder.tv_date.setText(horizontalList.get(position).getDate());

            holder.iv_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MoterExamViewActivity.class);
                    Log.d(TagUtils.getTag(),"imageurl:-"+ horizontalList.get(position).getUrl());
                    intent.putExtra("image_url", horizontalList.get(position).getUrl());
                    startActivity(intent);
                }
            });
            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    deleteScreen(api_position);
                    deleteScreenBase(api_position, horizontalList.get(position).getId());
                }
            });

        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }

    public void parseDeleteData(String response, int position) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String Result = jsonObject.optString("Result");
            showImage(position);
            Toast.makeText(getApplicationContext(), Result, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteScreenBase(int position, String value) {
        Log.d("deletepos", position + "");
        switch (position) {
            case 1:
                deleteScreen(position, "moter_trp_id", value);
                break;

            case 2:
                deleteScreen(position, "moter_exam_id", value);
                break;
            case 3:
                deleteScreen(position, "moter_rvical_id", value);
                break;
            case 4:
                deleteScreen(position, "moter_raccic_id", value);
                break;
            case 5:
                deleteScreen(position, "moter_lumbar_id", value);

                break;
            case 6:
                deleteScreen(position, "moter_examhip_id", value);

                break;
            case 7:
                deleteScreen(position, "moter_exam_knee_id", value);

                break;
            case 8:
                deleteScreen(position, "moter_examankle_id", value);

                break;
            case 9:
                deleteScreen(position, "moter_exam_toes_id", value);

                break;
            case 10:
                deleteScreen(position, "moter_examshoulder_id", value);

                break;
            case 11:
                deleteScreen(position, "moter_examelbow_id", value);

                break;
            case 12:
                deleteScreen(position, "moter_rvical_id", value);

                break;
            case 13:
                deleteScreen(position, "moter_examwrist_id", value);

                break;
            case 14:
                deleteScreen(position, "moter_examfinger_id", value);
                break;

            case 15:
                deleteScreen(position, "moterexamsacroilic_id", value);
                break;
            case 16:
                break;
        }
    }

    public void deleteScreen(int position, String id_key, String id_value) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(id_key, id_value));
        Log.d("position", position + "");
        switch (position) {
            case 1:
                new WebServiceBase(nameValuePairs, this, "delete1").execute(ApiConfig.BASE_URL + "users/deletemoterexamhltrp");
                break;
            case 2:
                new WebServiceBase(nameValuePairs, this, "delete2").execute(ApiConfig.BASE_URL + "users/deletemoterexams");
                break;
            case 3:
                new WebServiceBase(nameValuePairs, this, "delete3").execute(ApiConfig.BASE_URL + "users/deletemoterexamcervical");
                break;
            case 4:
                new WebServiceBase(nameValuePairs, this, "delete4").execute(ApiConfig.BASE_URL + "users/deletemoterexamthoraccic");
                break;
            case 5:
                new WebServiceBase(nameValuePairs, this, "delete5").execute(ApiConfig.BASE_URL + "users/deletemoterexamlumbar");
                break;
            case 6:
                new WebServiceBase(nameValuePairs, this, "delete6").execute(ApiConfig.BASE_URL + "users/deletemoterexamship");
                break;
            case 7:
                new WebServiceBase(nameValuePairs, this, "delete7").execute(ApiConfig.BASE_URL + "users/deletemoterexamknee");
                break;
            case 8:
                new WebServiceBase(nameValuePairs, this, "delete8").execute(ApiConfig.BASE_URL + "users/deletemoterexamsankle");
                break;
            case 9:
                new WebServiceBase(nameValuePairs, this, "delete9").execute(ApiConfig.BASE_URL + "users/deletemoterexamstoes");
                break;
            case 10:
                new WebServiceBase(nameValuePairs, this, "delete10").execute(ApiConfig.BASE_URL + "users/deletemoterexamsshoulder");
                break;
            case 11:
                new WebServiceBase(nameValuePairs, this, "delete11").execute(ApiConfig.BASE_URL + "users/deletemoterexamselbow");
                break;
            case 12:
                new WebServiceBase(nameValuePairs, this, "delete3").execute(ApiConfig.BASE_URL + "users/deletemoterexamcervical");
                break;
            case 13:
                new WebServiceBase(nameValuePairs, this, "delete13").execute(ApiConfig.BASE_URL + "users/deletemoterexamswrist");
                break;
            case 14:
                new WebServiceBase(nameValuePairs, this, "delete14").execute(ApiConfig.BASE_URL + "users/deletemoterexamsfingers");
                break;
            case 15:
                new WebServiceBase(nameValuePairs, this, "delete15").execute(ApiConfig.BASE_URL + "users/deletemoterexamsacroilic");
                break;
            case 16:
                break;
        }
    }

    public String loadJSONFromAsset() {

        try {
            InputStream is = getApplicationContext().getAssets().open("jsonfile.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
//            loginUser();
            Log.e("json", json);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    private void MoterApi() {

        if (Utils.isNetworkAvailable(getApplicationContext())) {

            try {

//                Url
                JSONObject objresponse = new JSONObject();
//                Toast.makeText(getActivity(), "button is clickable now for try block ..", Toast.LENGTH_LONG).show();
                Log.e("News Data", objresponse.toString());

                objresponse.put(KEY_PATIENT_ID, patientId);
//                    objresponse.put(ApiConfig.ASSESSMENT_TYPE, assessmentType);
                objresponse.put("moter_exam_date", Utils.getCurrentDate());
                objresponse.put("moter_exam_data", "hello");
                objresponse.put("moter_exam_data1", "hello1");
                objresponse.put("moter_exam_data2", "hello2");
                Log.e("patientId", patientId);
                Log.e("moter_exam_date", Utils.getCurrentDate());
                Log.e("objresponse", objresponse.toString());
                new UrlConnectionTask(AddMotorExamFragment.this, ApiConfig.SIGNIN_URL, ApiConfig.ID1, true, objresponse, BaseModel.class, this).execute("");
            } catch (JSONException e) {
                Log.e("error", "JsonException");
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(getApplicationContext(), getResources().getString(R.string.err_network));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(motor_case!=-1){
            showImage(motor_case);
        }
    }



}
