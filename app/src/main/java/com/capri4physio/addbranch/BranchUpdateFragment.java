package com.capri4physio.addbranch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.model.branch.BranchPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 25-09-2017.
 */

public class BranchUpdateFragment extends AppCompatActivity implements WebServicesCallBack {
    private static final String CALL_GET_BRANCH = "call_get_branch";
    private static final String CALL_BRANCH_DELTE_API = "call_branch_delete_api";
    private static final String CALL_UPDATE_BRANCH_API = "call_branch_update_api";
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_mobile)
    EditText et_mobile;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_city)
    EditText et_city;
    @BindView(R.id.et_state)
    EditText et_state;
    @BindView(R.id.et_country)
    EditText et_country;
    @BindView(R.id.et_pincode)
    EditText et_pincode;
    @BindView(R.id.et_website)
    EditText et_website;
    BranchPOJO branchPOJO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_branch_update);
        ButterKnife.bind(this);

        branchPOJO= (BranchPOJO) getIntent().getSerializableExtra("branchpojo");
        if(branchPOJO!=null){
            et_name.setText(branchPOJO.getBranch_name());
            et_code.setText(branchPOJO.getBranch_code());
            et_mobile.setText(branchPOJO.getMobile());
            et_phone.setText(branchPOJO.getPhone());
            et_email.setText(branchPOJO.getEmail());
            et_address.setText(branchPOJO.getAddress());
            et_city.setText(branchPOJO.getCity());
            et_state.setText(branchPOJO.getState());
            et_country.setText(branchPOJO.getCountry());
            et_pincode.setText(branchPOJO.getPincode());
            et_website.setText(branchPOJO.getWebsite());
        }else{
            finish();
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidateEdits(et_name,et_code,et_mobile,et_phone,et_email,et_address,et_city,et_state,et_country,et_pincode,et_website)){
                    callUpdateAPI();
                }else{
                    ToastClass.showShortToast(getApplicationContext(),"Please Enter All Fields");
                }
            }
        });
    }

    public void callUpdateAPI(){
        ArrayList<NameValuePair> nameValuePairArrayList=new ArrayList<>();
        nameValuePairArrayList.add(new BasicNameValuePair("branch_id",branchPOJO.getBranch_id()));
        nameValuePairArrayList.add(new BasicNameValuePair("branch_name",et_name.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("branch_code",branchPOJO.getBranch_code()));
        nameValuePairArrayList.add(new BasicNameValuePair("branch_status",branchPOJO.getBranch_status()));
        nameValuePairArrayList.add(new BasicNameValuePair("mobile",et_mobile.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("phone",et_phone.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("email",et_email.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("address",et_address.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("city",et_city.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("state",et_state.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("country",et_country.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("pincode",et_pincode.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("website",et_website.getText().toString()));

        new WebServiceBase(nameValuePairArrayList,this,CALL_UPDATE_BRANCH_API).execute(ApiConfig.UPDATE_BRANCH);
    }

    public boolean ValidateEdits(EditText... editTexts){
        for(EditText editText:editTexts){
            if(editText.getText().toString().length()==0){
                return false;
            }
        }
        return true;
    }
    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case CALL_UPDATE_BRANCH_API:
                parseGetBranches(response);
                break;
        }
    }

    public void parseGetBranches(String response){
        Log.d(TagUtils.getTag(),"response:-"+response);
        try{
            if(new JSONObject(response).optString("success").equals("true")){
                ToastClass.showShortToast(getApplicationContext(),"Branch Updated");
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result","ok");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }else{
                ToastClass.showShortToast(getApplicationContext(),"Failed to update Branch");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Server Down");
        }
    }
}
