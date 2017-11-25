package com.capri4physio.activity;

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

public class WebCaseReport extends AppCompatActivity {
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.btn_print)
    Button btn_print;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_case_report);
        ButterKnife.bind(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveBitmapPDF1();
            }
        });

        webView.loadUrl("http://caprispine.in/casereport/casereport.php");
    }

    private void SaveBitmapPDF1() {
        new AsyncTask<Void, Void, Void>() {
            File f;
            ProgressDialog pd;
            ArrayList<Bitmap> list;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pd = new ProgressDialog(WebCaseReport.this);
                pd.setMessage("Please Wait...");
                pd.setCancelable(false);
                pd.show();

                Bitmap bitmap = getBitmapByView(scrollView);
//                SaveBitmapPDF(bitmap);
                int height = bitmap.getHeight();
                Log.d(TagUtils.getTag(), "height:-" + height);

                int chunks = height / 200;
                Log.d(TagUtils.getTag(), "chunks:-" + chunks);
                list = splitImage1(bitmap, chunks);
            }

            @Override
            protected Void doInBackground(Void... params) {

//                SaveBitmapPDF1(list);
                try {
                    f = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio" + File.separator + "case_report.pdf");
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
                        Log.d(TagUtils.getTag(),"file path:-"+f.getPath());
                        MimeTypeMap mime = MimeTypeMap.getSingleton();
                        String ext = f.getName().substring(f.getName().lastIndexOf(".") + 1);
                        String type = mime.getMimeTypeFromExtension(ext);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.capri4physio.fileProvider", f);
                            intent.setDataAndType(contentUri, type);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            grantAllUriPermissions(intent,contentUri);
                        } else {
                            intent.setDataAndType(Uri.fromFile(f), type);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            grantAllUriPermissions(intent,Uri.fromFile(f));
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
        Log.d(TagUtils.getTag(),"bitmap width:-"+scrollView.getWidth());
        Log.d(TagUtils.getTag(),"bitmap height:-"+h);
        // create bitmap with target size
        if(h>0) {
            bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                    Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmap);
            scrollView.draw(canvas);
        }else{

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
