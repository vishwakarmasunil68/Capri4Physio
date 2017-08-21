package com.capri4physio.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.model.treatment.TreatmentResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAdminTreatmentActivity extends AppCompatActivity implements WebServicesCallBack{
    private static final String ADD_TREATMENT_API = "add_treatment_api";
    private static final String UPDATE_TREATMENT_API = "update_treatment_api";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.et_treatment_name)
    EditText et_treatment_name;
    @BindView(R.id.et_treatment_price)
    EditText et_treatment_price;
    TreatmentResultPOJO treatmentResultPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin_treatment);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Treatment");


        treatmentResultPOJO= (TreatmentResultPOJO) getIntent().getSerializableExtra("treatmentpojo");
        if(treatmentResultPOJO!=null){
            btn_submit.setText("UPDATE");
            et_treatment_name.setText(treatmentResultPOJO.getTreatment_name());
            et_treatment_price.setText(treatmentResultPOJO.getTreatment_price());
        }else{
            btn_submit.setText("ADD");
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid(et_treatment_name,et_treatment_price)){
                    callAddTreatmentApi();
                }else{
                    ToastClass.showShortToast(getApplicationContext(),"Please Fill All Fields");
                }
            }
        });
    }

    public void callAddTreatmentApi(){
        ArrayList<NameValuePair> nameValuePairList=new ArrayList<>();
        nameValuePairList.add(new BasicNameValuePair("treatment_name",et_treatment_name.getText().toString()));
        nameValuePairList.add(new BasicNameValuePair("treatment_price",et_treatment_price.getText().toString()));
        if(treatmentResultPOJO!=null){
            nameValuePairList.add(new BasicNameValuePair("id",treatmentResultPOJO.getId()));
            new WebServiceBase(nameValuePairList,this,UPDATE_TREATMENT_API).execute(ApiConfig.update_admin_treatment);   
        }else{
            new WebServiceBase(nameValuePairList,this,ADD_TREATMENT_API).execute(ApiConfig.add_admin_treatment);
        }
    }


    public boolean isValid(EditText... editText){
        for(EditText editText1:editText){
            if(editText1.getText().toString().length()==0){
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case ADD_TREATMENT_API:
                parseAddTreatmentResponse(response);
                break;
            case UPDATE_TREATMENT_API:
                updateTreatmentResponse(response);
                break;
        }
    }

    public void updateTreatmentResponse(String response){
        Log.d(TagUtils.getTag(),"update response:-"+response);
        try{
            if(new JSONObject(response).optString("Success").equals("true")){
                ToastClass.showShortToast(getApplicationContext(),"Treatment Updated Successfully");
                finish();
            }else{
                ToastClass.showShortToast(getApplicationContext(),"Failed To Updated treatment");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
        }
    }

    public void parseAddTreatmentResponse(String response){
        Log.d(TagUtils.getTag(),"add_treatment_response:-"+response);
        try{
            if(new JSONObject(response).optString("Success").equals("true")){
                ToastClass.showShortToast(getApplicationContext(),"Treatment Added Successfully");
                finish();
            }else{
                ToastClass.showShortToast(getApplicationContext(),"Failed To add treatment");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
        }
    }
}
