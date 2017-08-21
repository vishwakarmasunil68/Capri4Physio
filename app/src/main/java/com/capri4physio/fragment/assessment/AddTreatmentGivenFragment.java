package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.capri4physio.R;
import com.capri4physio.Services.GetWebServicesFragment;
import com.capri4physio.Services.WebServiceUploadFragment;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.activity.SignActivity;
import com.capri4physio.fragment.BaseFragment;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.model.treatment.TreatmentPOJO;
import com.capri4physio.model.treatment.TreatmentResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.HandlerConstant;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.capri4physio.util.Utils;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 *
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTreatmentGivenFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author prabhunathy
 * @version 1.0
 * @since 2014-03-31
 */
public class AddTreatmentGivenFragment extends BaseFragment implements HttpUrlListener,WebServicesCallBack,TimePickerDialog.OnTimeSetListener {

    private static final String ADD_TREATMENT_GIVEN_API = "add_treatment_given_api";
    private static final String GET_DOSES = "get_doses";
    private static final String GET_ALL_TREATMENT = "get_all_treatment_api";
    private Button mBtnSave;
    private Spinner mEdtxtTherapist;
    private Spinner mEdtxtTherapy;
    private EditText mEdtxtComment;
    private TextView mEdtxtTimeIn;
    private TextView mEdtxtTimeOut;
    private AutoCompleteTextView et_doses;
    private ImageView iv_signature;
    private Button btn_add_signature;

    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
    private String patientId = "";
    private String assessmentType = "";

    HorizontalListView listview_timein,listview_timeout;
    TextView tv_select_time_out,tv_select_time_in;
    String[] sports;
    String pos,trea_staff_name;
    int payamount_item;
    String listview_time_in,listview_time_out;

    public ArrayList<String> patientaray;
    InfoApps Detailapp;
    ArrayList<String> stringArrayList;
    public static ArrayList<InfoApps> contactDetails;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddTreatmentAdviceFragment.
     */
    public static AddTreatmentGivenFragment newInstance(String patientId, String assessmentType) {
        AddTreatmentGivenFragment fragment = new AddTreatmentGivenFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);

        fragment.setArguments(bundle);
        return fragment;
    }

    public AddTreatmentGivenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            patientId = getArguments().getString(KEY_PATIENT_ID);
            assessmentType = getArguments().getString(KEY_TYPE);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_treatment_given, container, false);

        sports = getResources().getStringArray(R.array.Time_Pickersheet);
        initView(rootView);
        contactDetails = new ArrayList<InfoApps>();
        stringArrayList=new ArrayList<String>();
        patientaray=new ArrayList<String>();
//        patientaray.add("Select Staff name");
        new CatagoryUrlAsynTask().execute();
        setListener();
        return rootView;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);

        mBtnSave = (Button) view.findViewById(R.id.btn_save);
        mEdtxtTherapy = (Spinner) view.findViewById(R.id.edtxt_therapy_prodoctitem);
        mEdtxtTherapist = (Spinner) view.findViewById(R.id.edtxt_therapist);
        mEdtxtComment = (EditText) view.findViewById(R.id.edtxt_comment);
        mEdtxtTimeIn = (TextView) view.findViewById(R.id.edtxt_time_in);
        mEdtxtTimeOut = (TextView) view.findViewById(R.id.edtxt_time_out);
        tv_select_time_out = (TextView) view.findViewById(R.id.tv_select_time_out);
        tv_select_time_in = (TextView) view.findViewById(R.id.tv_select_time_in);
        et_doses = (AutoCompleteTextView) view.findViewById(R.id.et_doses);
        iv_signature = (ImageView) view.findViewById(R.id.iv_signature);
        btn_add_signature = (Button) view.findViewById(R.id.btn_add_signature);

//        listview_timein=(HorizontalListView)view.findViewById(R.id.listview_timein);
//        listview_timeout=(HorizontalListView)view.findViewById(R.id.listview_timeout);


        arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_item, list_doses);
        //Used to specify minimum number of
        //characters the user has to type in order to display the drop down hint.
        et_doses.setThreshold(1);
        //Setting adapter
        et_doses.setAdapter(arrayAdapter);

        tv_select_time_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time="in";
                TimePickerDialog tpd = TimePickerDialog
                        .newInstance(AddTreatmentGivenFragment.this, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);

                tpd.show(getFragmentManager(), "Time In Time Picker");
            }
        });

        tv_select_time_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time="out";
                TimePickerDialog tpd = TimePickerDialog
                        .newInstance(AddTreatmentGivenFragment.this, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);

                tpd.show(getFragmentManager(), "Time Out Time Picker");
            }
        });

        new GetWebServicesFragment(getActivity(),this,GET_ALL_TREATMENT,true).execute(ApiConfig.get_all_admin_treatment);
    }
    List<String> list_doses=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    String time="in";
    @Override
    protected void setListener() {
        super.setListener();

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    int position = mEdtxtTherapy.getSelectedItemPosition();
                    if (position != -1) {
                        payamount_item=Integer.parseInt(listadminTreatments.get(position).getTreatment_price());
                        addSignatureTreatment(signature_image_path);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
//                int positionitem = mEdtxtTherapy.getSelectedItemPosition();
//                Log.e("mEdtxtTherapy", String.valueOf(positionitem));
//
//                switch (positionitem) {
//                    case 1:
//                        payamount_item = 150;
//                        break;
//                    case 2:
//                        payamount_item = 150;
//                        break;
//
//                    case 3:
//                        payamount_item = 150;
//                        break;
//
//                    case 4:
//                        payamount_item = 100;
//                        break;
//
//                    case 5:
//                        payamount_item = 200;
//                        break;
//
//                    case 6:
//                        payamount_item = 300;
//                        break;
//
//                    case 7:
//                        payamount_item = 100;
//                        break;
//
//                    case 8:
//                        payamount_item = 500;
//                        break;
//
//                    case 9:
//                        payamount_item = 300;
//                        break;
//
//                    case 10:
//                        payamount_item = 100;
//                        break;
//
//                    case 11:
//                        payamount_item = 400;
//                        break;
//
//                    case 12:
//                        payamount_item = 400;
//                        break;
//
//                    case 13:
//                        payamount_item = 300;
//                        break;
//
//                    case 14:
//                        payamount_item = 400;
//                        break;
//
//                    case 15:
//                        payamount_item = 600;
//                        break;
//
//                    case 16:
//                        payamount_item = 400;
//                        break;
//                    default:
//                        return;
//                }

//                addApiCall();

            }
        });

        mEdtxtTherapist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InfoApps obj = contactDetails.get(position);
                pos = obj.getId();
                trea_staff_name = obj.getName();
                Log.d("patient_id", pos.toString()+ " "+trea_staff_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        listview_timein.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),
//                android.R.layout.simple_dropdown_item_1line, sports));
//        listview_timein.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                listview_time_in = (String) parent.getItemAtPosition(position);
//                Toast.makeText(getActivity(), listview_time_in, Toast.LENGTH_LONG).show();
//            }
//        });


//        listview_timeout.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),
//                android.R.layout.simple_dropdown_item_1line, sports));
//        listview_timeout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                listview_time_out = (String) parent.getItemAtPosition(position);
//                Toast.makeText(getActivity(), listview_time_out, Toast.LENGTH_LONG).show();
//            }
//        });


        btn_add_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), SignActivity.class),1001);
            }
        });


        getDoses();
    }

    void getDoses(){
        new GetWebServicesFragment(getActivity(),this,GET_DOSES,true).execute(ApiConfig.get_doses);
    }

    String signature_image_path="";
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            Log.d(TagUtils.getTag(), "on activity result");
            String file_path=data.getStringExtra("result");
            try{
                Glide.with(getActivity().getApplicationContext())
                        .load(file_path)
                        .into(iv_signature);

                signature_image_path=file_path;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    public void addSignatureTreatment(String image_path) {

        if (!isValid(image_path))
            return;

        if (image_path.length() > 0) {
            try {
                Date d = new Date();
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                FileBody bin1 = new FileBody(new File(image_path));


                reqEntity.addPart("patient_id", new StringBody(String.valueOf(patientId)));
                reqEntity.addPart("therapy", new StringBody(String.valueOf(mEdtxtTherapy.getSelectedItem().toString())));
                reqEntity.addPart("therapist", new StringBody(pos));
                reqEntity.addPart("trea_staff_name", new StringBody(String.valueOf(trea_staff_name)));
                reqEntity.addPart("comment", new StringBody(String.valueOf(mEdtxtComment.getText().toString().trim())));
                reqEntity.addPart("time_in", new StringBody(String.valueOf(listview_time_in)));
                reqEntity.addPart("time_out", new StringBody(String.valueOf(listview_time_out)));
                reqEntity.addPart("date", new StringBody(String.valueOf(Utils.getCurrentDate())));
                reqEntity.addPart("trea_amount", new StringBody(String.valueOf(et_doses.getText().toString().trim())));
                reqEntity.addPart("trea_status", new StringBody("success"));
                reqEntity.addPart("trea_staff_name", new StringBody(String.valueOf(trea_staff_name)));
                reqEntity.addPart("signature", bin1);

                new WebServiceUploadFragment(reqEntity, getActivity(),this, ADD_TREATMENT_GIVEN_API).execute(ApiConfig.add_treatment_api);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Enter message");
        }
    }

    private void addApiCall() {
//        if (!isValid())
//            return;

        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.PATIENT_ID, patientId);
                params.put(ApiConfig.ASSESSMENT_TYPE, assessmentType);
                params.put(ApiConfig.TREATMENT_THERAPY, all_treatment.get(mEdtxtTherapy.getSelectedItemPosition()));
                params.put(ApiConfig.THERAPY_THERAPIST, pos);
                params.put(ApiConfig.TREATMENT_COMMENT, mEdtxtComment.getText().toString().trim());
                params.put(ApiConfig.TREATMENT_TIME_IN, listview_time_in);
                params.put(ApiConfig.TREATMENT_TIME_OUT, listview_time_out);
                params.put(ApiConfig.DATE, Utils.getCurrentDate());
                params.put("trea_amount", et_doses.getText().toString().trim());
                params.put("status", "success");
                params.put("trea_staff_name", trea_staff_name);

                Log.d(TagUtils.getTag(),"Treatment given params:-"+params.toString());
                new UrlConnectionTask(getActivity(), ApiConfig.ADD_ASSESSMENT_URL, ApiConfig.ID1, true, params, BaseModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }


    @Override
    public void onPostSuccess(Object response, int id) {
        switch (id) {

            case ApiConfig.ID1:
                BaseModel baseModel = (BaseModel) response;
                AppLog.i("Capri4Physio", "Patient Response : " + baseModel.getStatus());
                getFragmentManager().popBackStack();
                HandlerConstant.POP_INNER_BACK_HANDLER.sendMessage(HandlerConstant.POP_INNER_BACK_HANDLER.obtainMessage(0, ""));
                break;
        }

    }

    @Override
    public void onPostError(String errMsg, int id) {

    }


    /**
     * Validation to check user inputs
     *
     * @return
     */
    private boolean isValid(String image_path) {

        String stherapy = mEdtxtTherapy.getSelectedItem().toString().trim();
        String sTherapist = mEdtxtTherapist.getSelectedItem().toString().trim();
//        String quant = edtxt_therapy_proquant.getSelectedItem().toString().trim();
        String sComment = mEdtxtComment.getText().toString().trim();
        String sTimeIN = listview_time_in.toString().trim();
        String sTimeOut = listview_time_out.toString().trim();

        if (stherapy.isEmpty()) {
            mEdtxtTherapy.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_therapy));
            return false;
        }
        if (sTherapist.isEmpty()) {
            mEdtxtTherapist.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_therapist));
            return false;
        }

        if (sComment.isEmpty()) {
            mEdtxtComment.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_comment));
            return false;
        }
        if (sTimeIN.isEmpty()) {
            mEdtxtTimeIn.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_time_in));
            return false;
        }
        if (sTimeOut.isEmpty()) {
            mEdtxtTimeOut.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_time_out));
            return false;
        }

        if(!new File(image_path).exists()){
            Utils.showError(getActivity(),"Error","Please Add Signature");
            return false;
        }

        return true;
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case ADD_TREATMENT_GIVEN_API:
                parseAddtreatmentApi(response);
                break;
            case GET_DOSES:
                parseGetDoses(response);
                break;
            case GET_ALL_TREATMENT:
                parseGetAllTreatment(response);
                break;
        }
    }
    List<String> all_treatment;
    List<TreatmentResultPOJO> listadminTreatments;
    public void parseGetAllTreatment(String response){
        Log.d(TagUtils.getTag(),"admin treatment response:-"+response);
        try{
            Gson gson=new Gson();
            TreatmentPOJO treatmentPOJO=gson.fromJson(response,TreatmentPOJO.class);
            if(treatmentPOJO.getSuccess().equals("true")){
                all_treatment=new ArrayList<>();
                listadminTreatments=treatmentPOJO.getTreatmentResultPOJOList();
                for(TreatmentResultPOJO treatmentResultPOJO:treatmentPOJO.getTreatmentResultPOJOList()){
                    all_treatment.add(treatmentResultPOJO.getTreatment_name());
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, all_treatment);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mEdtxtTherapy.setAdapter(dataAdapter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void parseGetDoses(String response){
        Log.d(TagUtils.getTag(),"doses response:-"+response);
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("Success").equals("true")){
                list_doses.clear();
                JSONArray res=jsonObject.optJSONArray("Result");
                for(int i=0;i<res.length();i++){
                    JSONObject json=res.optJSONObject(i);
                    String dose=json.optString("dose");
                    list_doses.add(dose);
                }

                arrayAdapter.notifyDataSetChanged();
            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void parseAddtreatmentApi(String response){
        Log.d(TagUtils.getTag(),"add treatment api response:-"+response);
        try{

            getFragmentManager().popBackStack();
            HandlerConstant.POP_INNER_BACK_HANDLER.sendMessage(HandlerConstant.POP_INNER_BACK_HANDLER.obtainMessage(0, ""));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hour = "";
        if (hourOfDay < 10) {
            hour = "0" + hourOfDay;
        } else {
            hour = String.valueOf(hourOfDay);
        }

        String minutes = "";

        if (minute < 10) {
            minutes = "0" + minute;
        } else {
            minutes = String.valueOf(minute);
        }

        String time = hour + ":" + minutes;
        Log.d(TagUtils.getTag(), "time selected:-" + time);
        if (this.time.equals("in")) {
            tv_select_time_in.setText(time);
            listview_time_in=time;
        } else {
            tv_select_time_out.setText(time);
            listview_time_out=time;
        }
    }


    private class CatagoryUrlAsynTask extends AsyncTask<String, String, String> {
        String id, catagoryName;


        @Override
        protected String doInBackground(String... params) {
//            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
                /*String json = Holder.CATAGOARY_URL;
                String cont = Html.fromHtml(json).toString();*/
            String content = HttpULRConnect.getData(ApiConfig.VIEW_STAFF);
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
                Log.e("Post Method", jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    Log.e("2", jsonObject2.toString());
                    String id = jsonObject2.getString("id");
                    String smobile = jsonObject2.getString("mobile");
                    String semail = jsonObject2.getString("email");
                    String sfirst_name = jsonObject2.getString("first_name");
                    String dob = jsonObject2.getString("dob");
                    stringArrayList.add(sfirst_name);
                    Detailapp = new InfoApps();
                    Detailapp.setName(sfirst_name);
                    Detailapp.setStr4(semail);//date
                    Detailapp.setId(id);
                    Detailapp.setNumber(smobile);//startTime
                    contactDetails.add(Detailapp);
                    patientaray.add(Detailapp.getName());


//                List
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getActivity(), android.R.layout.simple_dropdown_item_1line, patientaray);
                    mEdtxtTherapist.setAdapter(spinnerArrayAdapter);
                    Log.e("patientaray", patientaray.toString());
                /*contactAdapter = new ReportAdapter(getActivity(), R.layout.contactlistadap);
                contactList.setAdapter(contactAdapter);*/
                }
            }catch(Exception e){
                e.printStackTrace();

            }
            //  CatagoryMetod();
        }
    }




}