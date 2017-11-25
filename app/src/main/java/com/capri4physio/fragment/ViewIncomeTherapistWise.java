package com.capri4physio.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.capri4physio.R;
import com.capri4physio.Services.GetWebServicesFragment;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.model.branch.BranchPOJO;
import com.capri4physio.model.doctor.DoctorPOJO;
import com.capri4physio.model.doctor.DoctorResultPOJO;
import com.capri4physio.model.treatment.TreatmentResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.commons.lang.time.DateUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Emobi-Android-002 on 9/2/2016.
 */
public class ViewIncomeTherapistWise extends AppCompatActivity implements WebServicesCallBack, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    private static final String GET_ALL_BRANCHES = "get_all_branches";
    List<String> all_treatment;
    List<TreatmentResultPOJO> listadminTreatments;
    @BindView(R.id.spinner_therapist)
    Spinner spinner_therapist;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    List<BranchPOJO> branchPOJOList = new ArrayList<>();
    @BindView(R.id.btn_submit)
    Button btn_submit;
    String branch_code = "";
    String treatment_id = "";
    private static final String GET_DOCTORS_API = "get_doctors_api";
    String therapist_id = "";
    String start_date = "", end_date = "";
    @BindView(R.id.et_from)
    EditText et_from;
    @BindView(R.id.iv_from)
    ImageView iv_from;
    @BindView(R.id.et_to)
    EditText et_to;
    @BindView(R.id.iv_to)
    ImageView iv_to;
    @BindView(R.id.ll_branch)
    LinearLayout ll_branch;
    @BindView(R.id.ll_therapist)
    LinearLayout ll_therapist;

    boolean is_from = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_income_therapist_wise);
        ButterKnife.bind(this);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TagUtils.getTag(), "branc_code:-" + branch_code);
                Log.d(TagUtils.getTag(), "therapist_id:-" + therapist_id);
                Log.d(TagUtils.getTag(), "start_date:-" + start_date);
                Log.d(TagUtils.getTag(), "end_date:-" + end_date);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date from_d = sdf.parse(start_date);
                    Date to_d = sdf.parse(end_date);

                    if (from_d.before(new Date())) {
                        if (DateUtils.isSameDay(to_d, new Date()) || to_d.after(new Date())) {
                            if (branch_code.length() > 0 && therapist_id.length() > 0) {
                                viewReportFragment(branch_code, therapist_id, start_date, end_date);
                            } else {
                                ToastClass.showShortToast(getApplicationContext(), "Invalid Branch code");
                                return;
                            }
                        } else {
                            ToastClass.showShortToast(getApplicationContext(), "you have selected passed to date");
                        }
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), "You have selected coming from date");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastClass.showShortToast(getApplicationContext(), "Invalid Date Formats");
                }
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        et_from.setText(sdf.format(new Date()));
        start_date = sdf.format(new Date());
        end_date = sdf.format(new Date());
        et_to.setText(sdf.format(new Date()));
        iv_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_from = true;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ViewIncomeTherapistWise.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Select from date");
            }
        });

        iv_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_from = false;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ViewIncomeTherapistWise.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "select to date");
            }
        });

        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")) {
            new GetWebServicesFragment(this, GET_ALL_BRANCHES).execute(ApiConfig.GetURL);
        } else {
            ll_branch.setVisibility(View.GONE);
            branch_code = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
            if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("2")) {
                ll_therapist.setVisibility(View.GONE);
                therapist_id = AppPreferences.getInstance(getApplicationContext()).getUserID();
            } else {
                getTherapists(AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE());
            }
        }
    }

    public void getTherapists(String branch_code) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("bracch_code", branch_code));
        new WebServiceBase(nameValuePairs, this, GET_DOCTORS_API).execute(ApiConfig.get_branch_doctor);
    }

    public void viewReportFragment(String branch_code, String treatment_id, String start_date, String end_date) {
        IncomeTherapistWiseFragment incomeBranchWiseFragment = new IncomeTherapistWiseFragment(branch_code, therapist_id, start_date, end_date);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fram_main, incomeBranchWiseFragment, "incomeBranchWiseFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GET_DOCTORS_API:
                parseGetDoctorsResponse(response);
                break;
            case GET_ALL_BRANCHES:
                parseAllBranches(response);
                break;
        }
    }


    public void parseAllBranches(String response) {
        Log.d(TagUtils.getTag(), "response:-" + response);
        branchPOJOList.clear();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                BranchPOJO branchPOJO = new BranchPOJO(jsonObject.optString("branch_id"),
                        jsonObject.optString("branch_name"),
                        jsonObject.optString("branch_code"),
                        jsonObject.optString("branch_status"));
                branchPOJOList.add(branchPOJO);
            }
            List<String> braStringList = new ArrayList<>();
            for (BranchPOJO branchPOJO : branchPOJOList) {
                braStringList.add(branchPOJO.getBranch_name() + " (" + branchPOJO.getBranch_code() + ")");
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.dropsimpledown, braStringList);
            spinner_branch.setAdapter(spinnerArrayAdapter);

            if (!AppPreferences.getInstance(getApplicationContext()).getUserType().equals("2")) {
                if (branchPOJOList.size() > 0) {
                    branch_code = branchPOJOList.get(0).getBranch_code();
                    getTherapists(branchPOJOList.get(0).getBranch_code());
                }
            }


            spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    branch_code = branchPOJOList.get(position).getBranch_code();
                    getTherapists(branchPOJOList.get(position).getBranch_code());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<DoctorResultPOJO> doctorResultPOJOList = new ArrayList<>();

    public void parseGetDoctorsResponse(String response) {
        Log.d(TagUtils.getTag(), "get doctor response:-" + response);
        try {
            Gson gson = new Gson();
            DoctorPOJO doctorPOJO = gson.fromJson(response, DoctorPOJO.class);
            doctorResultPOJOList.clear();
            if (doctorPOJO.getSuccess().equals("true")) {
                final List<String> doctorStringList = new ArrayList<>();
                doctorResultPOJOList.addAll(doctorPOJO.getDoctorResultPOJOList());
                for (DoctorResultPOJO doctorResultPOJO : doctorResultPOJOList) {
                    doctorStringList.add(doctorResultPOJO.getFirstName() + " " + doctorResultPOJO.getLastName());
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(), R.layout.dropsimpledown, doctorStringList);
                spinner_therapist.setAdapter(spinnerArrayAdapter);

//                getAllInvoices(doctorResultPOJOList.get(0).getId());
                therapist_id = doctorResultPOJOList.get(0).getId();
                spinner_therapist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        getAllInvoices(doctorResultPOJOList.get(0).getId());
                        therapist_id = doctorResultPOJOList.get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            } else {
                ToastClass.showShortToast(getApplicationContext(), "No Doctor Found. Please Select another branch");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Log.d(TagUtils.getTag(), "date:-" + dayOfMonth + "-" + monthOfYear + "-" + year);
        String day = "";
        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = String.valueOf(dayOfMonth);
        }
        String month = "";
        if ((monthOfYear + 1) < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf(monthOfYear + 1);
        }
        if (is_from) {
            et_from.setText(day + "-" + month + "-" + year);
            start_date = day + "-" + month + "-" + year;
        } else {
            et_to.setText(day + "-" + month + "-" + year);
            end_date = day + "-" + month + "-" + year;
        }
    }
}
