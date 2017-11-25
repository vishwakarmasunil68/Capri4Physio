package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.capri4physio.R;
import com.capri4physio.Services.GetWebServicesFragment;
import com.capri4physio.Services.WebServicesCallBack;
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
import com.capri4physio.util.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTreatmentAdviceFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author prabhunathy
 * @version 1.0
 * @since 2014-03-31
 */
public class AddTreatmentAdviceFragment extends BaseFragment implements HttpUrlListener,WebServicesCallBack {

    private static final String GET_ALL_TREATMENT = "get_all_treatment";
    private Button mBtnSave;
    private AutoCompleteTextView mEdtxtGoal;
    private Spinner mEdtxtTherapy;
    private Spinner mEdtxtDoses;

    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
    private String patientId = "";
    private String assessmentType = "";
    public ArrayList<String> patientaray;
    InfoApps Detailapp;
    AutoCompleteTextView et_select_dowses;
    ArrayList<String> stringArrayList;
    private DatabaseReference root;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddTreatmentAdviceFragment.
     */
    public static AddTreatmentAdviceFragment newInstance(String patientId, String assessmentType) {
        AddTreatmentAdviceFragment fragment = new AddTreatmentAdviceFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);

        fragment.setArguments(bundle);
        return fragment;
    }

    public AddTreatmentAdviceFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_add_treatment_advice, container, false);
//        patientaray.add("Select Staff name");
        root = FirebaseDatabase.getInstance().getReference().getRoot();
        initView(rootView);
        stringArrayList = new ArrayList<String>();
        patientaray = new ArrayList<String>();
//        patientaray.add("Select Staff name");
//        new CatagoryUrlAsynTask().execute();


        setListener();
        return rootView;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);

        mBtnSave = (Button) view.findViewById(R.id.btn_save);
        mEdtxtGoal = (AutoCompleteTextView) view.findViewById(R.id.edtxt_goal);
        mEdtxtTherapy = (Spinner) view.findViewById(R.id.edtxt_therapy);
        mEdtxtDoses = (Spinner) view.findViewById(R.id.edtxt_therapy_proquant);


        et_select_dowses = (AutoCompleteTextView) view.findViewById(R.id.et_select_dowses);

        arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_item, list_doses);
        //Used to specify minimum number of
        //characters the user has to type in order to display the drop down hint.
        et_select_dowses.setThreshold(1);
        //Setting adapter
        et_select_dowses.setAdapter(arrayAdapter);


        goalarrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_item, list_goal);
        //Used to specify minimum number of
        //characters the user has to type in order to display the drop down hint.
        mEdtxtGoal.setThreshold(1);
        //Setting adapter
        mEdtxtGoal.setAdapter(goalarrayAdapter);


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
        root.child("goal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_goal.clear();
                set_goal.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Log.d(TagUtils.getTag(), "doses datashapshot:-" + postSnapshot.getValue());
                    set_goal.add(postSnapshot.getValue().toString());
                }
                list_goal.addAll(set_goal);
                goalarrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TagUtils.getTag(), "Failed to read app title value.", databaseError.toException());
            }
        });
    }

    ArrayAdapter<String> arrayAdapter;
    List<String> list_doses = new ArrayList<>();
    Set<String> set_doses= new HashSet<>();
    ArrayAdapter<String> goalarrayAdapter;
    List<String> list_goal = new ArrayList<>();
    Set<String> set_goal = new HashSet<>();

    @Override
    protected void setListener() {
        super.setListener();
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addApiCall();
            }
        });
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
//                getActivity(), android.R.layout.simple_dropdown_item_1line);
//        mEdtxtTherapy.setAdapter(spinnerArrayAdapter);


    }


    private void addApiCall() {
        if (!isValid())
            return;

        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.PATIENT_ID, patientId);
                params.put(ApiConfig.ASSESSMENT_TYPE, assessmentType);
                params.put(ApiConfig.TREATMENT_GOAL, mEdtxtGoal.getText().toString().trim());
                params.put(ApiConfig.TREATMENT_THERAPY, mEdtxtTherapy.getSelectedItem().toString().trim());
                params.put(ApiConfig.TREATMENT_DOSES, et_select_dowses.getText().toString());
                params.put(ApiConfig.DATE, Utils.getCurrentDate());
                String mGroupId = root.push().getKey();
                root.child("dose").child(mGroupId).setValue(et_select_dowses.getText().toString());
                root.child("goal").child(mGroupId).setValue(mEdtxtGoal.getText().toString());
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
//        AppLog.i("Capri4Physio", "Patient Response : " + baseModel.getStatus());
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
    public void onStart() {
        super.onStart();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Treatment Plan");
    }

    @Override
    public void onPostError(String errMsg, int id) {

    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case GET_ALL_TREATMENT:
                parseGetAllTreatment(response);
                break;
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


    /**
     * Validation to check user inputs
     *
     * @return
     */

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
                    String smobile = jsonObject2.getString("smobile");
                    String semail = jsonObject2.getString("semail");
                    String sfirst_name = jsonObject2.getString("sfirst_name");
                    String dob = jsonObject2.getString("sdob");
                    String age = jsonObject2.getString("sage");
                    String doj = jsonObject2.getString("sdatejoing");
                    String senddate = jsonObject2.getString("senddate");
                    String gen = jsonObject2.getString("sgender");
                    String marital_status = jsonObject2.getString("smarital_status");
                    String desig = jsonObject2.getString("sdesignation");
                    String addr = jsonObject2.getString("saddress");
                    String city = jsonObject2.getString("scity");
                    String pin_code = jsonObject2.getString("spincode");
                    String qual = jsonObject2.getString("squalifation");
                    String exp = jsonObject2.getString("sexprience");
                    stringArrayList.add(sfirst_name);
                    Detailapp = new InfoApps();
                    Detailapp.setName(sfirst_name);
                    Detailapp.setStr4(semail);//date
                    Detailapp.setId(id);
                    Detailapp.setNumber(smobile);//startTime
                    Detailapp.setDob(dob);//reason
                    Detailapp.setAge(age);
                    Detailapp.setDoj(doj);
                    Detailapp.setSend_date(senddate);
                    Detailapp.setGender(gen);
                    Detailapp.setMarital_status(marital_status);
                    Detailapp.setDesig(desig);
                    Detailapp.setDataAdd(addr);
                    Detailapp.setCity(city);
                    Detailapp.setPc(pin_code);
                    Detailapp.setQualification(qual);
                    Detailapp.setExp(exp);
                    patientaray.add(Detailapp.getName());


//                List
                    /*ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getActivity(), android.R.layout.simple_dropdown_item_1line, patientaray);
                    mEdtxtTherapy.setAdapter(spinnerArrayAdapter);*/
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

    private boolean isValid() {

        String sgoal = mEdtxtGoal.getText().toString().trim();
        String stherapy = mEdtxtTherapy.getSelectedItem().toString().trim();
        String sDoses = et_select_dowses.getText().toString().trim();

        if (sgoal.isEmpty()) {
            mEdtxtGoal.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_goal));
            return false;
        }

        if (stherapy.equals("Select Treatment")) {
            mEdtxtTherapy.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_therapy));
            return false;
        }

        if (sDoses.isEmpty()) {
            mEdtxtDoses.requestFocus();
            Utils.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_doses));
            return false;
        }
        return true;
    }
}