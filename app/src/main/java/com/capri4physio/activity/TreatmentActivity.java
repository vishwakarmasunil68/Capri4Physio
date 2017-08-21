package com.capri4physio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.capri4physio.R;
import com.capri4physio.Services.GetWebServices;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.adapter.TreatmentAdapter;
import com.capri4physio.model.treatment.TreatmentPOJO;
import com.capri4physio.model.treatment.TreatmentResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TreatmentActivity extends AppCompatActivity implements WebServicesCallBack{

    private static final String GET_ALL_TREATMENT = "get_all_treatment";
    private static final String DELETE_TREATMENT = "delete_treatment";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_treatment)
    RecyclerView rv_treatment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Treatment");
    }

    @Override
    protected void onResume() {
        super.onResume();
        callalltreatment();
    }

    public void callalltreatment(){
        new GetWebServices(this,GET_ALL_TREATMENT).execute(ApiConfig.get_all_admin_treatment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_treatment, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_add_treatment:
                startActivity(new Intent(TreatmentActivity.this,AddAdminTreatmentActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case GET_ALL_TREATMENT:
                parseAllTreatment(response);
                break;
            case DELETE_TREATMENT:
                parseDeleteTreatment(response);
                break;
        }
    }

    public void parseDeleteTreatment(String response){
        Log.d(TagUtils.getTag(),"delete response:-"+response);
        try{
            if(new JSONObject(response).optString("success").equals("true")){
                treatmentResultPOJOList.remove(position);
                treatmentAdapter.notifyDataSetChanged();
            }else{
                ToastClass.showShortToast(getApplicationContext(),"Failed to delete");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
        }
    }

    public void updateTreatment(TreatmentResultPOJO treatmentResultPOJO){
        Intent intent=new Intent(this,AddAdminTreatmentActivity.class);
        intent.putExtra("treatmentpojo",treatmentResultPOJO);
        startActivity(intent);
    }

    int position=0;
    public void deleteTreatment(String treat_id,int position){
        this.position=position;
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("id",treat_id));
        new WebServiceBase(nameValuePairs,this,DELETE_TREATMENT).execute(ApiConfig.delete_admin_treatment);
    }

    List<TreatmentResultPOJO> treatmentResultPOJOList;
    TreatmentAdapter treatmentAdapter;
    public void parseAllTreatment(String response){
        Log.d(TagUtils.getTag(),"response:-"+response);
        try{
            Gson gson=new Gson();
            TreatmentPOJO treatmentPOJO=gson.fromJson(response,TreatmentPOJO.class);
            if(treatmentPOJO.getSuccess().equals("true")){
                treatmentResultPOJOList=treatmentPOJO.getTreatmentResultPOJOList();
                treatmentAdapter = new TreatmentAdapter(this, treatmentResultPOJOList);
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_treatment.setLayoutManager(horizontalLayoutManagaer);
                rv_treatment.setHasFixedSize(true);
                rv_treatment.setItemAnimator(new DefaultItemAnimator());
                rv_treatment.setAdapter(treatmentAdapter);
            }else{
                ToastClass.showShortToast(getApplicationContext(),"No Treatment Found");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
        }
    }
}
