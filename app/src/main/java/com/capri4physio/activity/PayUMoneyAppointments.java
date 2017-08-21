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
import com.capri4physio.model.studentcourse.StudentCourseResultPOJO;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 14-08-2017.
 */

public class PayUMoneyAppointments extends AppCompatActivity{
    @BindView(R.id.webview)
    WebView webView;
    String url,time;
    StudentCourseResultPOJO studentCourseResultPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_umoney);
        ButterKnife.bind(this);

        studentCourseResultPOJO= (StudentCourseResultPOJO) getIntent().getSerializableExtra("studentcourseresultpojo");
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            url=bundle.getString("url");
            time=bundle.getString("time");

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
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",time);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
            Log.d(TagUtils.getTag(),"url done:-"+url);
            Log.d(TagUtils.getTag(), "done loading");

        }
    }
}
