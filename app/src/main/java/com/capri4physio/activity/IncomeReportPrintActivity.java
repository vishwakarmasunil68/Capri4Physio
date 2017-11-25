package com.capri4physio.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ScrollView;

import com.capri4physio.R;
import com.capri4physio.util.TagUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IncomeReportPrintActivity extends AppCompatActivity {
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.btn_print)
    Button btn_print;

    String bundle_data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_case_report);
        ButterKnife.bind(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                Log.d(TagUtils.getTag(), "url loaded:-" + url);
//            }
//        });
        webView.setWebViewClient(new AppWebViewClients(this));

//        webView.loadUrl("http://caprispine.in/casereport/casereport.php");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bundle_data = bundle.getString("type");

            switch (bundle_data) {
                case "incomestatement":
                    webView.loadUrl("http://caprispine.in/invoice_report/report/report/incomestatement.php?branch_code=" + bundle.getString("branch_code") + "&start_date=" + bundle.getString("start_date") + "&end_date=" + bundle.getString("end_date"));
                    break;
                case "incometreatmentwise":
                    webView.loadUrl("http://caprispine.in/invoice_report/report/report/incometreatmentwise.php?branch_code=" + bundle.getString("branch_code") + "&trea_treatment_id=" + bundle.getString("trea_treatment_id"));
                    break;
                case "incometherapistwise":
                    webView.loadUrl("http://caprispine.in/invoice_report/report/report/incomestatementtherapistwise.php?branch_code=" + bundle.getString("branch_code") + "&therapist=" + bundle.getString("therapist") + "&start_date=" + bundle.getString("start_date") + "&end_date=" + bundle.getString("end_date"));
                    break;
                case "patientwise":
                    webView.loadUrl("http://caprispine.in/invoice_report/report/report/incomepatientwise.php?branch_code=" + bundle.getString("branch_code") + "&patient_id=" + bundle.getString("patient_id") + "&start_date=" + bundle.getString("start_date") + "&end_date=" + bundle.getString("end_date"));
                    break;
                case "expensereport":
                    webView.loadUrl("http://caprispine.in/invoice_report/report/expensereport.php?branch_code=" + bundle.getString("branch_code") + "&start_date=" + bundle.getString("start_date") + "&end_date=" + bundle.getString("end_date"));
                    break;
                case "balancesheet":
                    webView.loadUrl("http://caprispine.in/casereport/casereport.php");
                    break;
                case "productivity":
                    webView.loadUrl("http://caprispine.in/invoice_report/productivitytherapist.php?branch_code=" + bundle.getString("branch_code") + "&start_date=" + bundle.getString("start_date") + "&end_date=" + bundle.getString("end_date") + "&therapist_id=" + bundle.getString("therapist_id"));
                    break;
                case "patientbill":
                    webView.loadUrl("http://caprispine.in/invoice_report/report/report/patientreport.php?patient_id=" + bundle.getString("patient_id")+"&branch_code="+bundle.getString("branch_code"));
                    break;
                case "patientreceipt":
                    webView.loadUrl("http://caprispine.in/invoice_report/report/report/patientreceipt.php?pt_id=" + bundle.getString("pt_id") + "&patient_id=" + bundle.getString("patient_id")+"&branch_code="+bundle.getString("branch_code"));
                    break;
                case "studentreport":
                    webView.loadUrl("http://caprispine.in/invoice_report/report/report/studentbill.php?full_fees="+bundle.getString("full_fees")+"&date="+bundle.getString("date")+"&name="+bundle.getString("name")+"&course="+bundle.getString("course")+"&reg_fees="+bundle.getString("reg_fees")+"&rem_fees="+bundle.getString("rem_fees")+"&rem_gst="+bundle.getString("rem_gst")+"&reg_gst="+bundle.getString("reg_gst")+"&full_gst="+bundle.getString("full_gst")+"&total="+bundle.getString("total")+"&comt="+bundle.getString("comt")+"&id="+bundle.getString("id")+"&stu_id="+bundle.getString("stu_id"));
                    break;
                case "incomefrompatient":
                    webView.loadUrl("http://caprispine.in/invoice_report/incomefrompatient.php?branch_code="+bundle.getString("branch_code")+"&mode="+bundle.getString("mode")+"&start_date="+bundle.getString("start_date")+"&end_date="+bundle.getString("end_date"));
                    break;
                case "patientcasereport":
                    webView.loadUrl("http://caprispine.in/invoice_report/newcase/case/casereport.php?patient_id="+bundle.getString("patient_id")+"&branch_code="+bundle.getString("branch_code"));
                    break;
                case "studentlist":
                    webView.loadUrl("http://caprispine.in/invoice_report/report/report/studentlist.php?course_id="+bundle.getString("course_id"));
                    break;
                case "CASE":
                    webView.loadUrl("http://caprispine.in/invoice_report/report/report/addcasereport.php?branch_code="+bundle.getString("branch_code")+"&patient_id="+bundle.getString("patient_id")+"&date="+bundle.getString("date"));
                    break;
                case "PROGRESS":
                    webView.loadUrl("http://caprispine.in/invoice_report/report/report/addprogressreport.php?branch_code="+bundle.getString("branch_code")+"&patient_id="+bundle.getString("patient_id")+"&date="+bundle.getString("date"));
                    break;
                case "REMARK":
                    webView.loadUrl("http://caprispine.in/invoice_report/report/report/addremarkreport.php?branch_code="+bundle.getString("branch_code")+"&patient_id="+bundle.getString("patient_id")+"&date="+bundle.getString("date"));
                    break;


                default:
                    webView.loadUrl("http://caprispine.in/invoice_report/newcase/case/casereport.php?patient_id=190&branch_code=SPHCAP");
                    break;
            }
            btn_print.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaveBitmapPDF1(bundle_data);
                }
            });

        } else {
            finish();
        }
    }

    public class AppWebViewClients extends WebViewClient {
        private ProgressDialog progressDialog;
        Activity activity;
        public AppWebViewClients(Activity activity) {
            this.activity=activity;
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            Log.d(TagUtils.getTag(),"loaded url:-"+url);
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
        }
    }


    private void SaveBitmapPDF1(final String type) {
        new AsyncTask<Void, Void, Void>() {
            File f;
            ProgressDialog pd;
            ArrayList<Bitmap> list;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pd = new ProgressDialog(IncomeReportPrintActivity.this);
                pd.setMessage("Please Wait...");
                pd.setCancelable(false);
                pd.show();

                Bitmap bitmap = getBitmapByView(scrollView);
//                SaveBitmapPDF(bitmap);
                int height = bitmap.getHeight();
                Log.d(TagUtils.getTag(), "height:-" + height);

                int chunks = height / 220;
                Log.d(TagUtils.getTag(), "chunks:-" + chunks);
                if(!type.equals("studentreport")) {
                    list = splitImage1(bitmap, chunks);
                }else{
                    list=new ArrayList<Bitmap>();
                    list.add(bitmap);
                }
            }

            @Override
            protected Void doInBackground(Void... params) {

//                SaveBitmapPDF1(list);
                try {
                    switch (bundle_data) {
                        case "incomestatement":
                            f = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio" + File.separator + "income_branch_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "incometreatmentwise":
                            f = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio" + File.separator + "income_treat_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "expensereport":
                            f = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio" + File.separator + "expense_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "balancesheet":
                            f = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio" + File.separator + "balance_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "productivity":
                            f = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio" + File.separator + "productivity_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "patientbill":
                            f = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio" + File.separator + "patient_bill_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "patientreceipt":
                            f = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio" + File.separator + "patient_receipt_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "incomefrompatient":
                            f = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio" + File.separator + "patientwallet_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "patientcasereport":
                            f = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio" + File.separator + "casereport_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "studentlist":
                            f = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio" + File.separator + "studentlist_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "CASE":
                            f = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio" + File.separator + "case_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "PROGRESS":
                            f = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio" + File.separator + "progress_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "REMARK":
                            f = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio" + File.separator + "remark_" + System.currentTimeMillis() + ".pdf");
                            break;

                        default:
                            f = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio" + File.separator + "case_report.pdf");
                            break;
                    }
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(f));
                    document.open();
                    for (Bitmap bmp : list) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);


                        Image image = Image.getInstance(stream.toByteArray());
                        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                                - document.rightMargin() - 0) / image.getWidth()) * 100;
//                        image.scaleAbsolute(PageSize.A4.rotate());
//                        image.setAbsolutePosition(image.getWidth(), image.getHeight());
                        image.scalePercent(scaler);
                        document.add(image);
                    }
                    document.close();
                } catch (Exception e) {
                    pd.dismiss();
                    Log.d(TagUtils.getTag(), e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (pd != null) {
                    pd.dismiss();
                }
                if (f != null) {
                    if (f.exists()) {
                        Log.d(TagUtils.getTag(), "file path:-" + f.getPath());
                        MimeTypeMap mime = MimeTypeMap.getSingleton();
                        String ext = f.getName().substring(f.getName().lastIndexOf(".") + 1);
                        String type = mime.getMimeTypeFromExtension(ext);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.capri4physio.fileProvider", f);
                            intent.setDataAndType(contentUri, type);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            grantAllUriPermissions(intent, contentUri);
                        } else {
                            intent.setDataAndType(Uri.fromFile(f), type);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            grantAllUriPermissions(intent, Uri.fromFile(f));
                        }


                        startActivity(intent);
                    }
                }
            }
        }.execute();
    }

    public Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        //get the actual height of scrollview
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundResource(R.color.white);
        }
        Log.d(TagUtils.getTag(), "bitmap width:-" + scrollView.getWidth());
        Log.d(TagUtils.getTag(), "bitmap height:-" + h);
        // create bitmap with target size
        if (h > 0) {
            bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                    Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmap);
            scrollView.draw(canvas);
        } else {

        }
        return bitmap;
    }

    private ArrayList<Bitmap> splitImage1(Bitmap bitmap, int chunkNumbers) {

        //For the number of rows and columns of the grid to be displayed
        int rows, cols = 1;

        //For height and width of the small image chunks
        int chunkHeight, chunkWidth;

        //To store all the small image chunks in bitmap format in this list
        ArrayList<Bitmap> chunkedImages = new ArrayList<Bitmap>(chunkNumbers);

        //Getting the scaled bitmap of the source image
//        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
//        Bitmap bitmap = drawable.getBitmap();
//        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        rows = (int) Math.sqrt(chunkNumbers);
        chunkHeight = bitmap.getHeight() / rows;
        chunkWidth = bitmap.getWidth() / cols;

        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;
        for (int x = 0; x < rows; x++) {
            int xCoord = 0;
            for (int y = 0; y < cols; y++) {
                chunkedImages.add(Bitmap.createBitmap(bitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }
        return chunkedImages;
    }

    private void grantAllUriPermissions(Intent intent, Uri uri) {
        List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }
}
