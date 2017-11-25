package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.capri4physio.R;
import com.capri4physio.Services.GetWebServicesFragment;
import com.capri4physio.Services.WebServiceBaseFragment;
import com.capri4physio.Services.WebServiceUploadFragment;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.activity.SignActivity;
import com.capri4physio.fragment.BaseFragment;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.model.patient.PatientAmountPOJO;
import com.capri4physio.model.treatment.TreatmentPOJO;
import com.capri4physio.model.treatment.TreatmentResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.HandlerConstant;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.capri4physio.util.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.capri4physio.net.ApiConfig.GET_LAST_PATIENT_TRANSACTION_AMOUNT;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTreatmentGivenFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author prabhunathy
 * @version 1.0
 * @since 2014-03-31
 */
public class AddTreatmentGivenFragment extends BaseFragment implements HttpUrlListener, WebServicesCallBack, TimePickerDialog.OnTimeSetListener {

    private static final String ADD_TREATMENT_GIVEN_API = "add_treatment_given_api";
    private static final String GET_DOSES = "get_doses";
    private static final String GET_ALL_TREATMENT = "get_all_treatment_api";
    private static final String GET_PATIENT_TRANSACTION_AMOUNT = "get_patient_amount";
    private static final String GET_DOCTORS_API = "get_doctors_api";
    private static final String ADD_AMOUNT_API = "add_amount_api";
    private Button mBtnSave;
    private Spinner mEdtxtTherapist;
    private Spinner mEdtxtTherapy;
    private EditText mEdtxtComment;
    private TextView mEdtxtTimeIn;
    private TextView mEdtxtTimeOut;
    private AutoCompleteTextView et_doses;
    private ImageView iv_signature;
    private Button btn_add_signature;
    private RelativeLayout rl_therapist;
    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_PATIENT_BRANCH_CODE = "patient_branch_code";
    private String patientId = "";
    private String assessmentType = "";
    private String patient_branch_code = "";

    HorizontalListView listview_timein, listview_timeout;
    TextView tv_select_time_out, tv_select_time_in;
    String[] sports;
    String therapist_id, trea_staff_name;
    int payamount_item;
    String listview_time_in, listview_time_out;

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
    public static AddTreatmentGivenFragment newInstance(String patientId, String assessmentType, String patient_branch_code) {
        AddTreatmentGivenFragment fragment = new AddTreatmentGivenFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);
        bundle.putString(KEY_PATIENT_BRANCH_CODE, patient_branch_code);

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
            patient_branch_code = getArguments().getString(KEY_PATIENT_BRANCH_CODE);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_treatment_given, container, false);

        sports = getResources().getStringArray(R.array.Time_Pickersheet);
        root = FirebaseDatabase.getInstance().getReference().getRoot();
        initView(rootView);
        contactDetails = new ArrayList<InfoApps>();
        stringArrayList = new ArrayList<String>();
        patientaray = new ArrayList<String>();
//        patientaray.add("Select Staff name");
//        new CatagoryUrlAsynTask().execute();
        if (AppPreferences.getInstance(getActivity().getApplicationContext()).getUserType().equals("2")) {
            therapist_id = AppPreferences.getInstance(getActivity().getApplicationContext()).getUserID();
        } else {
            getAllTherapist(patient_branch_code);
        }
        setListener();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (AppPreferences.getInstance(getActivity().getApplicationContext()).getUserType().equals("2")) {
            rl_therapist.setVisibility(View.GONE);
        }


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
        rl_therapist = (RelativeLayout) view.findViewById(R.id.rl_therapist);

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
                time = "in";
                TimePickerDialog tpd = TimePickerDialog
                        .newInstance(AddTreatmentGivenFragment.this, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);

                tpd.show(getFragmentManager(), "Time In Time Picker");
            }
        });

        tv_select_time_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = "out";
                TimePickerDialog tpd = TimePickerDialog
                        .newInstance(AddTreatmentGivenFragment.this, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);

                tpd.show(getFragmentManager(), "Time Out Time Picker");
            }
        });

        new GetWebServicesFragment(getActivity(), this, GET_ALL_TREATMENT, true).execute(ApiConfig.get_all_admin_treatment);


        root.child("dose").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_doses.clear();
                set_doses.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Log.d(TagUtils.getTag(), "doses datashapshot:-" + postSnapshot.getValue());
                    set_doses.add(postSnapshot.getValue().toString());
                }
                list_doses.addAll(set_doses);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TagUtils.getTag(), "Failed to read app title value.", databaseError.toException());
            }
        });
    }

    List<String> list_doses = new ArrayList<>();
    Set<String> set_doses = new HashSet<>();

    ArrayAdapter<String> arrayAdapter;
    String time = "in";

    @Override
    protected void setListener() {
        super.setListener();

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPatientAmount();
            }
        });

        mEdtxtTherapist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InfoApps obj = contactDetails.get(position);
                therapist_id = obj.getId();
                trea_staff_name = obj.getName();
                Log.d("patient_id", therapist_id.toString() + " " + trea_staff_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_add_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), SignActivity.class), 1001);
            }
        });


//        getDoses();
    }

    void getDoses() {
        new GetWebServicesFragment(getActivity(), this, GET_DOSES, true).execute(ApiConfig.get_doses);
    }

    String signature_image_path = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d(TagUtils.getTag(), "on activity result");
                String file_path = data.getStringExtra("result");
                try {
                    Glide.with(getActivity().getApplicationContext())
                            .load(file_path)
                            .into(iv_signature);

                    signature_image_path = file_path;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void checkPatientAmount() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("p_id", patientId));
        new WebServiceBaseFragment(nameValuePairs, getActivity(), AddTreatmentGivenFragment.this, GET_PATIENT_TRANSACTION_AMOUNT).execute(GET_LAST_PATIENT_TRANSACTION_AMOUNT);
    }

    int treatment_amount = 0;
    int total_patient_amount = 0;

    public void addSignatureTreatment(String image_path, int amount, String total_amount) {
        int total = 0;
        try {
            total = Integer.parseInt(total_amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        total_patient_amount = total;
        if (!isValid(image_path))
            return;

        if (image_path.length() > 0) {
            try {
                treatment_amount = amount;
                Date d = new Date();
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                FileBody bin1 = new FileBody(new File(image_path));


                reqEntity.addPart("patient_id", new StringBody(String.valueOf(patientId)));
                reqEntity.addPart("therapy", new StringBody(String.valueOf(mEdtxtTherapy.getSelectedItem().toString())));
                if (AppPreferences.getInstance(getActivity().getApplicationContext()).getUserType().equals("2")) {
                    reqEntity.addPart("therapist", new StringBody(AppPreferences.getInstance(getActivity().getApplicationContext()).getUserID()));
                } else {
                    reqEntity.addPart("therapist", new StringBody(therapist_id));
                }
                if (AppPreferences.getInstance(getActivity().getApplicationContext()).getUserType().equals("2")) {
                    reqEntity.addPart("trea_staff_name", new StringBody(AppPreferences.getInstance(getActivity().getApplicationContext()).getFirstName() + " " + AppPreferences.getInstance(getActivity().getApplicationContext()).getLastName()));
                } else {
                    reqEntity.addPart("trea_staff_name", new StringBody(String.valueOf(trea_staff_name)));
                }
                reqEntity.addPart("comment", new StringBody(String.valueOf(mEdtxtComment.getText().toString().trim())));
                reqEntity.addPart("time_in", new StringBody(String.valueOf(listview_time_in)));
                reqEntity.addPart("time_out", new StringBody(String.valueOf(listview_time_out)));
                reqEntity.addPart("date", new StringBody(String.valueOf(Utils.getCurrentDate())));
//                reqEntity.addPart("trea_amount", new StringBody(String.valueOf(et_doses.getText().toString().trim())));
                reqEntity.addPart("trea_amount", new StringBody(String.valueOf(amount)));
                reqEntity.addPart("trea_status", new StringBody("success"));
                reqEntity.addPart("trea_staff_name", new StringBody(String.valueOf(trea_staff_name)));
                reqEntity.addPart("trea_branch_code", new StringBody(String.valueOf(patient_branch_code)));
                reqEntity.addPart("trea_treatment_id", new StringBody(String.valueOf(listadminTreatments.get(mEdtxtTherapy.getSelectedItemPosition()).getId())));
                reqEntity.addPart("trea_dose", new StringBody(String.valueOf(et_doses.getText().toString())));
                reqEntity.addPart("signature", bin1);

                String mGroupId = root.push().getKey();
                root.child("dose").child(mGroupId).setValue(et_doses.getText().toString());
                new WebServiceUploadFragment(reqEntity, getActivity(), this, ADD_TREATMENT_GIVEN_API).execute(ApiConfig.add_treatment_api);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Enter message");
        }
    }

    private DatabaseReference root;

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

    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Treatment Given");
    }

    /**
     * Validation to check user inputs
     *
     * @return
     */
    private boolean isValid(String image_path) {

        String stherapy = mEdtxtTherapy.getSelectedItem().toString().trim();
        if (!AppPreferences.getInstance(getActivity().getApplicationContext()).getUserType().equals("2")) {
            String sTherapist = mEdtxtTherapist.getSelectedItem().toString().trim();
            if (sTherapist.isEmpty()) {
                mEdtxtTherapist.requestFocus();
                Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_therapist));
                return false;
            }
        }
//        String quant = edtxt_therapy_proquant.getSelectedItem().toString().trim();
        String sComment = mEdtxtComment.getText().toString().trim();
        String sTimeIN = listview_time_in.toString().trim();
        String sTimeOut = listview_time_out.toString().trim();

        if (stherapy.isEmpty()) {
            mEdtxtTherapy.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_therapy));
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

        if (!new File(image_path).exists()) {
            Utils.showError(getActivity(), "Error", "Please Add Signature");
            return false;
        }

        return true;
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case ADD_TREATMENT_GIVEN_API:
                parseAddtreatmentApi(response);
                break;
            case GET_DOSES:
                parseGetDoses(response);
                break;
            case GET_ALL_TREATMENT:
                parseGetAllTreatment(response);
                break;
            case GET_PATIENT_TRANSACTION_AMOUNT:
                parseGetPatientAmount(response);
                break;
            case GET_DOCTORS_API:
                parseDoctorsAPI(response);
                break;
            case ADD_AMOUNT_API:
                parseAddAmountResponse(response);
                break;
        }
    }

    public void parseAddAmountResponse(String response) {
        try {
            Log.d(TagUtils.getTag(), "add amount response:-" + response);
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {

                ToastClass.showShortToast(getActivity().getApplicationContext(), "Amount deducted successfully");
            } else {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "Failed to add Amount");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Server Error");
        }
        getFragmentManager().popBackStack();
        HandlerConstant.POP_INNER_BACK_HANDLER.sendMessage(HandlerConstant.POP_INNER_BACK_HANDLER.obtainMessage(0, ""));

    }

    public void parseDoctorsAPI(String response) {
        Log.d(TagUtils.getTag(), "doctor response:-" + response);
        try {
            contactDetails.clear();
            patientaray.clear();
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("Success").equals("true")) {
                JSONArray jsonArray = jsonObject.optJSONArray("Result");
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
                }

//                List
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                        getActivity(), android.R.layout.simple_dropdown_item_1line, patientaray);
                mEdtxtTherapist.setAdapter(spinnerArrayAdapter);
                Log.e("patientaray", patientaray.toString());

            } else {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "No Doctor Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void parseGetPatientAmount(String response) {
        Log.d(TagUtils.getTag(), "response:-" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {
                PatientAmountPOJO patientAmountPOJO = new Gson().fromJson(jsonObject.optJSONArray("result").optJSONObject(0).toString(), PatientAmountPOJO.class);
                int position = mEdtxtTherapy.getSelectedItemPosition();
                if (position != -1) {
                    payamount_item = Integer.parseInt(listadminTreatments.get(position).getTreatment_price());
                    addSignatureTreatment(signature_image_path, payamount_item, patientAmountPOJO.getPt_total_amount());
                }
            } else {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "No Amount in Patient Wallet");
            }

        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Something went wrong");
        }
    }


    List<String> all_treatment;
    List<TreatmentResultPOJO> listadminTreatments;

    public void parseGetAllTreatment(String response) {
        Log.d(TagUtils.getTag(), "admin treatment response:-" + response);
        try {
            Gson gson = new Gson();
            TreatmentPOJO treatmentPOJO = gson.fromJson(response, TreatmentPOJO.class);
            if (treatmentPOJO.getSuccess().equals("true")) {
                all_treatment = new ArrayList<>();
                listadminTreatments = treatmentPOJO.getTreatmentResultPOJOList();
                for (TreatmentResultPOJO treatmentResultPOJO : treatmentPOJO.getTreatmentResultPOJOList()) {
                    all_treatment.add(treatmentResultPOJO.getTreatment_name());
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, all_treatment);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mEdtxtTherapy.setAdapter(dataAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseGetDoses(String response) {
        Log.d(TagUtils.getTag(), "doses response:-" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("Success").equals("true")) {
                list_doses.clear();
                JSONArray res = jsonObject.optJSONArray("Result");
                for (int i = 0; i < res.length(); i++) {
                    JSONObject json = res.optJSONObject(i);
                    String dose = json.optString("dose");
                    list_doses.add(dose);
                }

                arrayAdapter.notifyDataSetChanged();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void parseAddtreatmentApi(String response) {
        Log.d(TagUtils.getTag(), "add treatment api response:-" + response);
        try {
            callPatientWallAddAPI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callPatientWallAddAPI() {
        int preamount = total_patient_amount;
        int curr_amount = treatment_amount;

        if (preamount > curr_amount) {
            ArrayList<NameValuePair> nameValuePairArrayList = new ArrayList<>();
            nameValuePairArrayList.add(new BasicNameValuePair("pt_amount", String.valueOf(treatment_amount)));
            nameValuePairArrayList.add(new BasicNameValuePair("pt_submittedby", ""));
            nameValuePairArrayList.add(new BasicNameValuePair("pt_deduetedby", AppPreferences.getInstance(getActivity().getApplicationContext()).getUserID()));
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String date = sdf.format(d).split(" ")[0];
            String time = sdf.format(d).split(" ")[1];
            nameValuePairArrayList.add(new BasicNameValuePair("pt_date", date));
            nameValuePairArrayList.add(new BasicNameValuePair("pt_time", time));
            nameValuePairArrayList.add(new BasicNameValuePair("pt_reason", ""));
            nameValuePairArrayList.add(new BasicNameValuePair("pt_total_amount", String.valueOf(preamount - curr_amount)));
            nameValuePairArrayList.add(new BasicNameValuePair("pt_p_id", patientId));
            new WebServiceBaseFragment(nameValuePairArrayList, getActivity(), this, ADD_AMOUNT_API).execute(ApiConfig.ADD_PATIENT_AMOUNT_API);
        } else {
            ToastClass.showShortToast(getActivity().getApplicationContext(), "No Sufficient balance in patient wallet");
            getFragmentManager().popBackStack();
            HandlerConstant.POP_INNER_BACK_HANDLER.sendMessage(HandlerConstant.POP_INNER_BACK_HANDLER.obtainMessage(0, ""));
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
            listview_time_in = time;
        } else {
            tv_select_time_out.setText(time);
            listview_time_out = time;
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
            } catch (Exception e) {
                e.printStackTrace();

            }
            //  CatagoryMetod();
        }
    }

    public void getAllTherapist(String patient_branch_code) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("bracch_code", patient_branch_code));
        new WebServiceBaseFragment(nameValuePairs, getActivity(), this, GET_DOCTORS_API).execute(ApiConfig.get_branch_doctor);
    }

}