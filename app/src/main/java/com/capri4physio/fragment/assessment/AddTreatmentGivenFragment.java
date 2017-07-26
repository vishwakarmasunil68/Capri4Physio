package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.capri4physio.R;
import com.capri4physio.fragment.BaseFragment;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


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
public class AddTreatmentGivenFragment extends BaseFragment implements HttpUrlListener {

    private Button mBtnSave;
    private Spinner mEdtxtTherapist,edtxt_therapy_proquant;
    private Spinner mEdtxtTherapy;
    private EditText mEdtxtComment;
    private TextView mEdtxtTimeIn;
    private TextView mEdtxtTimeOut;

    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
    private String patientId = "";
    private String assessmentType = "";

    HorizontalListView listview_timein,listview_timeout;
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
        edtxt_therapy_proquant = (Spinner) view.findViewById(R.id.edtxt_therapy_proquant);
        mEdtxtComment = (EditText) view.findViewById(R.id.edtxt_comment);
        mEdtxtTimeIn = (TextView) view.findViewById(R.id.edtxt_time_in);
        mEdtxtTimeOut = (TextView) view.findViewById(R.id.edtxt_time_out);

        listview_timein=(HorizontalListView)view.findViewById(R.id.listview_timein);
        listview_timeout=(HorizontalListView)view.findViewById(R.id.listview_timeout);

    }


    @Override
    protected void setListener() {
        super.setListener();

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int positionitem = mEdtxtTherapy.getSelectedItemPosition();
                Log.e("mEdtxtTherapy", String.valueOf(positionitem));

                switch (positionitem) {
                    case 1:
                        payamount_item = 150;
                        break;
                    case 2:
                        payamount_item = 150;
                        break;

                    case 3:
                        payamount_item = 150;
                        break;

                    case 4:
                        payamount_item = 100;
                        break;

                    case 5:
                        payamount_item = 200;
                        break;

                    case 6:
                        payamount_item = 300;
                        break;

                    case 7:
                        payamount_item = 100;
                        break;

                    case 8:
                        payamount_item = 500;
                        break;

                    case 9:
                        payamount_item = 300;
                        break;

                    case 10:
                        payamount_item = 100;
                        break;

                    case 11:
                        payamount_item = 400;
                        break;

                    case 12:
                        payamount_item = 400;
                        break;

                    case 13:
                        payamount_item = 300;
                        break;

                    case 14:
                        payamount_item = 400;
                        break;

                    case 15:
                        payamount_item = 600;
                        break;

                    case 16:
                        payamount_item = 400;
                        break;
                    default:
                        return;
                }

                addApiCall();
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

        listview_timein.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, sports));
        listview_timein.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listview_time_in = (String) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), listview_time_in, Toast.LENGTH_LONG).show();
            }
        });

        listview_timeout.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, sports));
        listview_timeout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listview_time_out = (String) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), listview_time_out, Toast.LENGTH_LONG).show();
            }
        });

    }


    private void addApiCall() {
        if (!isValid())
            return;

        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.PATIENT_ID, patientId);
                params.put(ApiConfig.ASSESSMENT_TYPE, assessmentType);
                params.put(ApiConfig.TREATMENT_THERAPY, mEdtxtTherapy.getSelectedItem().toString().trim());
                params.put(ApiConfig.THERAPY_THERAPIST, pos);
                params.put(ApiConfig.TREATMENT_COMMENT, mEdtxtComment.getText().toString().trim());
                params.put(ApiConfig.TREATMENT_TIME_IN, listview_time_in);
                params.put(ApiConfig.TREATMENT_TIME_OUT, listview_time_out);
                params.put(ApiConfig.DATE, Utils.getCurrentDate());
                params.put("trea_amount", edtxt_therapy_proquant.getSelectedItem().toString().trim());
                params.put("status", "success");
                params.put("trea_staff_name", trea_staff_name);
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
    private boolean isValid() {

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
        return true;
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