package viewreport;

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
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.capri4physio.R;
import com.capri4physio.Services.GetWebServicesFragment;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.fragment.IncomeBranchTreatWiseFragment;
import com.capri4physio.model.branch.BranchPOJO;
import com.capri4physio.model.treatment.TreatmentPOJO;
import com.capri4physio.model.treatment.TreatmentResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Emobi-Android-002 on 9/2/2016.
 */
public class ViewIncomeTreatmentwiseReport extends AppCompatActivity implements WebServicesCallBack {

    private static final String GET_ALL_TREATMENT = "get_all_treatment_api";
    private static final String GET_ALL_BRANCHES = "get_all_branches";
    List<String> all_treatment;
    List<TreatmentResultPOJO> listadminTreatments;
    @BindView(R.id.spinner_treatment)
    Spinner spinner_treatment;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    List<BranchPOJO> branchPOJOList = new ArrayList<>();
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.ll_branch)
    LinearLayout ll_branch;

    String branch_code="";
    String treatment_id="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_income_treat_wise);
        ButterKnife.bind(this);

        new GetWebServicesFragment(this, GET_ALL_TREATMENT).execute(ApiConfig.get_all_admin_treatment);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(branch_code.length()>0&&treatment_id.length()>0){
                    viewReportFragment(branch_code,treatment_id);
                }else{
                    ToastClass.showShortToast(getApplicationContext(),"Please select fields properly");
                    return;
                }
            }
        });

        if(AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")){
            new GetWebServicesFragment(this, GET_ALL_BRANCHES).execute(ApiConfig.GetURL);
        }else{
            ll_branch.setVisibility(View.GONE);
            branch_code=AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
        }
    }

    public void viewReportFragment(String branch_code,String treatment_id){
        IncomeBranchTreatWiseFragment incomeBranchWiseFragment = new IncomeBranchTreatWiseFragment(branch_code,treatment_id);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fram_main, incomeBranchWiseFragment, "incomeBranchWiseFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case GET_ALL_TREATMENT:
                parseGetAllTreatment(response);
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
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, all_treatment);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_treatment.setAdapter(dataAdapter);

                if(all_treatment.size()>0){
                    treatment_id=listadminTreatments.get(0).getId();
                }


                spinner_treatment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        treatment_id=listadminTreatments.get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
