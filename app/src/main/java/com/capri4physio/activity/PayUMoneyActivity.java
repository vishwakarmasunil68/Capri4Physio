package com.capri4physio.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.model.cources.CourcesResultPOJO;
import com.capri4physio.model.studentcourse.StudentCourseResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayUMoneyActivity extends AppCompatActivity implements WebServicesCallBack{

    private static final String UPDATE_STUDENT = "update_student";
    private static final String UPDATE_COURCE = "update_course";
    @BindView(R.id.webview)
    WebView webView;
    String url,type;
    StudentCourseResultPOJO studentCourseResultPOJO;
    CourcesResultPOJO courcesResultPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_umoney);
        ButterKnife.bind(this);

        studentCourseResultPOJO= (StudentCourseResultPOJO) getIntent().getSerializableExtra("studentcourseresultpojo");
        courcesResultPOJO= (CourcesResultPOJO) getIntent().getSerializableExtra("courseresultpojo");
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            url=bundle.getString("url");
            type=bundle.getString("type");

            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
//            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webView.getSettings().setSupportMultipleWindows(true);
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webView.setWebViewClient(new AppWebViewClients(this));
            Log.d(TagUtils.getTag(),"url:-"+url);
            webView.loadUrl(url);
        }else{
            finish();
        }

    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case UPDATE_STUDENT:
                parseUpdateStudent(response);
                break;
            case UPDATE_COURCE:
                parseUpdateCourseActivity(response);
        }
    }

    public void parseUpdateCourseActivity(String response){
        Log.d(TagUtils.getTag(),"response:-"+response);
        try{
            if(new JSONObject(response).optString("Success").equals("true")){
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",studentCourseResultPOJO);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }else{
                ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
        }
    }
    public void parseUpdateStudent(String response){
        Log.d(TagUtils.getTag(),"update student:-"+response);
        try{
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("Success").equals("true")) {
                String result = jsonObject.optJSONObject("Result").toString();
                Gson gson = new Gson();
                studentCourseResultPOJO = gson.fromJson(result, StudentCourseResultPOJO.class);

                if(studentCourseResultPOJO.getScFullfees().length()>0){
                    updateCourse();
                }else{
                    if(studentCourseResultPOJO.getScRegFees().length()>0&&studentCourseResultPOJO.getScRemFees().length()>0){
                        updateCourse();
                    }else{
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result",studentCourseResultPOJO);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }
                }

            } else {
                ToastClass.showShortToast(getApplicationContext(), "Something went wrong");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
        }
    }

    public void updateCourse(){
        try {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("c_name", courcesResultPOJO.getC_name()));
            nameValuePairs.add(new BasicNameValuePair("c_comt", courcesResultPOJO.getC_comt()));
            nameValuePairs.add(new BasicNameValuePair("c_from_date", courcesResultPOJO.getC_from_date()));
            nameValuePairs.add(new BasicNameValuePair("c_to_date", courcesResultPOJO.getC_to_date()));
            nameValuePairs.add(new BasicNameValuePair("c_place", courcesResultPOJO.getC_place()));
            nameValuePairs.add(new BasicNameValuePair("c_sheet_available", courcesResultPOJO.getC_sheet_available()));
            int seat_rem=Integer.parseInt(courcesResultPOJO.getC_rem_seat());
            seat_rem=seat_rem-1;
            nameValuePairs.add(new BasicNameValuePair("c_rem_seat", String.valueOf(seat_rem)));
            int showing_seat=Integer.parseInt(courcesResultPOJO.getC_showing_sheet());
            showing_seat=showing_seat-1;
            nameValuePairs.add(new BasicNameValuePair("c_showing_sheet", String.valueOf(showing_seat)));
            nameValuePairs.add(new BasicNameValuePair("c_reg_fees", courcesResultPOJO.getC_reg_fees()));
            nameValuePairs.add(new BasicNameValuePair("c_rem_fees", courcesResultPOJO.getC_rem_fees()));
            nameValuePairs.add(new BasicNameValuePair("c_fees", courcesResultPOJO.getC_fees()));
            nameValuePairs.add(new BasicNameValuePair("c_pno", courcesResultPOJO.getC_pno()));
            nameValuePairs.add(new BasicNameValuePair("c_id", courcesResultPOJO.getC_id()));
            new WebServiceBase(nameValuePairs, PayUMoneyActivity.this, UPDATE_COURCE).execute(ApiConfig.update_course_api);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public class AppWebViewClients extends WebViewClient {
        private ProgressDialog progressDialog;
        Activity activity;

        public AppWebViewClients(Activity activity) {
            this.activity = activity;
//            progressDialog = new ProgressDialog(activity);
//            progressDialog.setMessage("Please Wait...");
//            progressDialog.setCancelable(true);
//            progressDialog.show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            Log.d(TagUtils.getTag(), "url:-" + url);
            view.loadUrl(url);
            if(url.contains("payumoney/success.php")){
                ToastClass.showShortToast(getApplicationContext(),"successx ");
                Log.d(TagUtils.getTag(),"getting pgs response");
//                callCashOnDeliveryAPI(addressDataPOJO);
            }else{
                if(url.contains("Paytmgetway/fail.php")){
//                    ToastClass.showLongToast(getApplicationContext(),"Payment Failed");
//                    startActivity(new Intent(OnlinePaymentActivity.this, HomeActivity.class));
//                    finishAffinity();
                }
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
//                view.loadUrl(url);
            if(url.equals("http://www.oldmaker.com/fijiyo/payumoney/success.php")){
                updateStudent();
            }
            Log.d(TagUtils.getTag(),"url done:-"+url);
            Log.d(TagUtils.getTag(), "done loading");

        }
    }
    public void updateStudent() {


        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("sc_sid", studentCourseResultPOJO.getScSid()));
        nameValuePairs.add(new BasicNameValuePair("sc_sname", studentCourseResultPOJO.getScSname()));
        nameValuePairs.add(new BasicNameValuePair("sc_semail", studentCourseResultPOJO.getScSemail()));
        nameValuePairs.add(new BasicNameValuePair("sc_sapplicationform_fill", studentCourseResultPOJO.getScSapplicationformFill()));
        nameValuePairs.add(new BasicNameValuePair("sc_photo_upload", studentCourseResultPOJO.getScPhotoUpload()));
        nameValuePairs.add(new BasicNameValuePair("sc_cerifiato_upload", studentCourseResultPOJO.getScCerifiatoUpload()));
        nameValuePairs.add(new BasicNameValuePair("sc_idcard_upload", studentCourseResultPOJO.getScIdcardUpload()));
        if(type.equals("reg")){
            nameValuePairs.add(new BasicNameValuePair("sc_reg_fees", "online"));
        }else {
            nameValuePairs.add(new BasicNameValuePair("sc_reg_fees", studentCourseResultPOJO.getScRegFees()));
        }
        if(type.equals("rem")){
            nameValuePairs.add(new BasicNameValuePair("sc_rem_fees", "online"));
        }else {
            nameValuePairs.add(new BasicNameValuePair("sc_rem_fees", studentCourseResultPOJO.getScRemFees()));
        }
        if(type.equals("full")){
            nameValuePairs.add(new BasicNameValuePair("sc_fullfees", "online"));
        }else {
            nameValuePairs.add(new BasicNameValuePair("sc_fullfees", studentCourseResultPOJO.getScFullfees()));
        }
        nameValuePairs.add(new BasicNameValuePair("sc_cid", studentCourseResultPOJO.getScCid()));
        nameValuePairs.add(new BasicNameValuePair("sc_id", studentCourseResultPOJO.getScId()));
        nameValuePairs.add(new BasicNameValuePair("sc_date", studentCourseResultPOJO.getSc_date()));
        nameValuePairs.add(new BasicNameValuePair("sc_time", studentCourseResultPOJO.getSc_time()));
        nameValuePairs.add(new BasicNameValuePair("admin_application_form", studentCourseResultPOJO.getAdminApplicationForm()));
        nameValuePairs.add(new BasicNameValuePair("admin_photo_upload", studentCourseResultPOJO.getAdminPhotoUpload()));
        nameValuePairs.add(new BasicNameValuePair("admin_certificate_upload", studentCourseResultPOJO.getAdminCertificateUpload()));
        nameValuePairs.add(new BasicNameValuePair("admin_icard", studentCourseResultPOJO.getAdminIcard()));
        nameValuePairs.add(new BasicNameValuePair("admin_status", studentCourseResultPOJO.getAdminStatus()));
        nameValuePairs.add(new BasicNameValuePair("admin_reg_fees", studentCourseResultPOJO.getAdminRegFees()));
        nameValuePairs.add(new BasicNameValuePair("sc_admin_rem_fees", studentCourseResultPOJO.getSc_admin_rem_fees()));
        nameValuePairs.add(new BasicNameValuePair("admin_full_fees", studentCourseResultPOJO.getAdminFullFees()));
        new WebServiceBase(nameValuePairs, this, UPDATE_STUDENT).execute(ApiConfig.update_student_course);

    }

}
