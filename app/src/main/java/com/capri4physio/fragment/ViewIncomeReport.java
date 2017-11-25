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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.capri4physio.R;
import com.capri4physio.Services.GetWebServices;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.model.branch.BranchPOJO;
import com.capri4physio.model.doctor.DoctorResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.commons.lang.time.DateUtils;
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
 * Created by Emobi-Android-002 on 10/12/2016.
 */
public class ViewIncomeReport extends AppCompatActivity implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener, WebServicesCallBack {


    @BindView(R.id.rl_branch)
    RelativeLayout rl_branch;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    @BindView(R.id.et_from)
    EditText et_from;
    @BindView(R.id.iv_from)
    ImageView iv_from;
    @BindView(R.id.et_to)
    EditText et_to;
    @BindView(R.id.iv_to)
    ImageView iv_to;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.fram_main)
    FrameLayout fram_main;
    @BindView(R.id.ll_branch)
    LinearLayout ll_branch;


    String branch_code = "";
    List<BranchPOJO> branchPOJOList = new ArrayList<>();
    List<DoctorResultPOJO> doctorResultPOJOList = new ArrayList<>();
    private static final String GET_ALL_BRANCHES = "get_all_branches";
    boolean is_from = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incomerepo);
        ButterKnife.bind(this);


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        et_from.setText(sdf.format(new Date()));
        et_to.setText(sdf.format(new Date()));
        iv_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_from = true;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ViewIncomeReport.this,
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
                        ViewIncomeReport.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "select to date");
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String from_date = et_from.getText().toString();
                String to_date = et_to.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date from_d = sdf.parse(from_date);
                    Date to_d = sdf.parse(to_date);


                    if (from_d.before(new Date())) {
                        if (DateUtils.isSameDay(to_d, new Date()) || to_d.after(new Date())) {
                            if (branch_code.length() > 0) {
                                viewReportFragment(branch_code, from_date, to_date);
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

        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")) {
            new GetWebServices(this, GET_ALL_BRANCHES).execute(ApiConfig.GetURL);
        } else {
            ll_branch.setVisibility(View.GONE);
            branch_code = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
        }
    }

    public void viewReportFragment(String branch_code, String from_date, String to_date) {
        IncomeBranchWiseFragment incomeBranchWiseFragment = new IncomeBranchWiseFragment(branch_code, from_date, to_date);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fram_main, incomeBranchWiseFragment, "incomeBranchWiseFragment");
        transaction.addToBackStack(null);
        transaction.commit();
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

            if (branchPOJOList.size() > 0) {
                branch_code = branchPOJOList.get(0).getBranch_code();
            }


            spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    branch_code = branchPOJOList.get(position).getBranch_code();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GET_ALL_BRANCHES:
                parseAllBranches(response);
                break;
        }
    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
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
        } else {
            et_to.setText(day + "-" + month + "-" + year);
        }
    }
}
